package zhongfucheng.login;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import zhongfucheng.core.action.BaseAction;
import zhongfucheng.core.constant.Constant;
import zhongfucheng.user.entity.User;
import zhongfucheng.user.entity.UserRole;
import zhongfucheng.user.service.UserService;

import java.util.List;

/**
 * Created by ozc on 2017/6/2.
 */
public class LoginAction extends BaseAction {

    /***************封装数据**********************/
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    /****************调用userService****************************/
    @Autowired
    private UserService userServiceImpl;

    public String loginUI() {
        return "loginUI";
    }

    /****************记载着登陆状态信息*********************/
    private String loginResult;

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    /*****************业务方法******************/

    public String login() {

        if (user != null) {
            List<User> list = userServiceImpl.findUserByAccountAndPassword(user.getAccount(), user.getPassword());

            //如果查到有值，那么就证明有该用户的,给他登陆
            if (list != null && list.size() > 0) {

                //查出用户所有的权限，设置到User中
                User user = list.get(0);
                List<UserRole> roles = userServiceImpl.findRoleById(user.getId());
                user.setUserRoles(roles);

                //保存到Session域中，为了更方便用，我们使用常量保存。
                ActionContext.getContext().getSession().put(Constant.USER, user);


                //保存到日志文件中Log
                Log log = LogFactory.getLog(getClass());
                log.info("用户名称为" + list.get(0).getName() + "登陆了系统!");

                //重定向到首页
                return "home";
            } else {
                //登陆失败，记载登陆信息
                loginResult = "登陆失败了，用户名或密码错误了";
            }
        }
        //只要不成功的，都回到登陆页面
        return loginUI();
    }

    public String logout() {

        //销毁session的值
        ActionContext.getContext().getSession().remove(Constant.USER);

        return loginUI();
    }

    public String noPermissionUI() {

        return "noPermissionUI";
    }
}
