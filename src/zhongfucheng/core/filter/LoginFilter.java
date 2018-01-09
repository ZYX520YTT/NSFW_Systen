package zhongfucheng.core.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import zhongfucheng.core.utils.PermissionCheck;
import zhongfucheng.user.entity.User;
import zhongfucheng.user.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ozc on 2017/6/4.
 */

public class LoginFilter implements Filter {

    //注入userService
    @Autowired
    private UserService userServiceImpl;

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //得到用户访问的路径
        String uri = request.getRequestURI();

        //登陆路径
        String loginPath = request.getContextPath() + "/sys/login_login.action";

        //提示页面
        String warningPath = request.getContextPath() + "/sys/login_noPermissionUI.action";

        //定义User变量
        User user;
        //判断用户访问的是哪里
        if (!uri.contains("login_")) {//如果不是访问我们的登陆模块

            //判断该用户是否登陆了。
             user = (User) request.getSession().getAttribute("SYS_USER");
            if (user == null) {//如果在session找不到，那么就是没有登陆
                //没有登陆，跳转到登陆页面
                response.sendRedirect(loginPath);
                return;

            } else {//有用户信息，就是登陆了。

                if (uri.contains("nsfw")) {//如果访问纳税服务系统，就要有对应的权限

                    //用户已经登陆了，判断用户有没有权限访问子系统

                    //得到IOC容器中的对象
                    WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
                    PermissionCheck permissionCheck = (PermissionCheck)applicationContext.getBean("permissionCheck");

                    if (permissionCheck.check(user, "nsfw")) {//有权限
                        //放行
                        chain.doFilter(request, response);
                    } else {//没有权限

                        //返回到提示页面
                        response.sendRedirect(warningPath);
                    }

                } else {//可以不用权限，直接放行
                    //放行
                    chain.doFilter(request, response);
                }
            }

        } else {//如果是访问我们的登陆模块，放行
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }


}
