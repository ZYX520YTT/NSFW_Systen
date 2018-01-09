package zhongfucheng.user.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import zhongfucheng.core.action.BaseAction;
import zhongfucheng.core.exception.ServiceException;
import zhongfucheng.core.utils.QueryHelper;
import zhongfucheng.role.service.RoleService;
import zhongfucheng.user.entity.User;
import zhongfucheng.user.entity.UserRole;
import zhongfucheng.user.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * Created by ozc on 2017/5/23.
 */

/**
 * 1.提供新增页面
 * 2.确定新增用户方法
 * 3.提供修改页面
 * 4.确定修改用户方法
 * 5.删除用户
 * 7.批量删除用户
 * 8.提供列表展示页面
 */
public class UserAction extends BaseAction {

    /*************注入Service************************/
    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private RoleService roleServiceImpl;

    /*************上传头像************************/
    private File headImg;
    private String headImgFileName;
    private String headImgContentType;
    public void setHeadImg(File headImg) {
        this.headImg = headImg;
    }

    public void setHeadImgFileName(String headImgFileName) {
        this.headImgFileName = headImgFileName;
    }

    public void setHeadImgContentType(String headImgContentType) {
        this.headImgContentType = headImgContentType;
    }


    /*************上传Excel************************/
    private File userExcel;
    private String userExcelFileName;
    private String userExcelContentType;

    public void setUserExcel(File userExcel) {
        this.userExcel = userExcel;
    }

    public void setUserExcelFileName(String userExcelFileName) {
        this.userExcelFileName = userExcelFileName;
    }

    public void setUserExcelContentType(String userExcelContentType) {
        this.userExcelContentType = userExcelContentType;
    }

    /************数据自动封装，给出setter和getter*************************/
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /************得到Service返回的数据*************************/
    //这里一定要给setter和getter方法，这样JSP才能够得到属性。不然就得不到了！！！我在这里弄了很久！！！！
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /************得到用户对应的角色id*************************/
    private String[] userRoleIds;

    public String[] getUserRoleIds() {
        return userRoleIds;
    }

    public void setUserRoleIds(String[] userRoleIds) {
        this.userRoleIds = userRoleIds;
    }

    /************Action中7大方法*************************/

    //抛出Action异常
    public String listUI() throws ServiceException, UnsupportedEncodingException {
        QueryHelper queryHelper = new QueryHelper(User.class, "u");
        //根据info是否为null来判断是否是条件查询。如果info为空，那么是查询所有。
        if (user != null) {
            if (org.apache.commons.lang.StringUtils.isNotBlank(user.getName())) {
                selectCondition =  URLDecoder.decode(user.getName(),"UTF-8");
                user.setName(selectCondition);
                queryHelper.addCondition(" u.name like ? ", "%" + user.getName() + "%");
            }
        }
        //当前页数没有值，那么赋值为1
        if (currentPageCount == 0) {
            currentPageCount = 1;
        }
        pageResult = userServiceImpl.getPageResult(queryHelper,currentPageCount);
        return "listUI";
    }

    public String addUI() {

        //把所有的角色查询出来，带过去给JSP页面显示
        ActionContext.getContext().getContextMap().put("roleList", roleServiceImpl.findObjects());

        return "addUI";
    }

    public String editUI() {

        //把所有的角色查询出来，带过去给JSP页面显示
        ActionContext.getContext().getContextMap().put("roleList", roleServiceImpl.findObjects());

        //外边已经传了id过来了，我们要找到id对应的User
        if (user != null &&user.getId() != null  ) {

            //得到查询条件
            selectCondition = user.getName();

            //直接获取出来，后面JSP会根据User有getter就能读取对应的信息！
            user = userServiceImpl.findObjectById(user.getId());

            //通过用户的id得到所拥有UserRole
            List<UserRole> roles = userServiceImpl.findRoleById(user.getId());
            //把用户拥有角色的id填充到数组中，数组最后回显到JSP页面
            int i=0;
            userRoleIds =  new String[roles.size()];
            for (UserRole role : roles) {
                userRoleIds[i++] = role.getUserRoleId().getRole().getRoleId();
            }

        }
        return "editUI";
    }

    public String edit() throws IOException {
        //Struts2会自动把JSP带过来的数据封装到User对象上
        if (user.getId() != null && user != null) {
            if (headImg != null) {

                //得到要把头像上传到服务器的路径
                javax.servlet.ServletContext servletContext = ServletActionContext.getServletContext();
                String realPath = servletContext.getRealPath("upload/user");
                //由于用户上传的名字可能会相同，如果相同就被覆盖掉，因此我们要修改上传文件的名字【独一无二】
                headImgFileName = UUID.randomUUID().toString() + headImgFileName.substring(headImgFileName.lastIndexOf("."));
                FileUtils.copyFile(headImg, new File(realPath, headImgFileName));

                //设置图片与用户的关系
                user.setHeadImg(headImgFileName);
            }
            if (userRoleIds != null) {

                //删除用户与角色之间的关系【历史遗留问题】
                userServiceImpl.deleteUserRoleById(userRoleIds);
                //保存用户与角色。
                userServiceImpl.saveUserAndRole(user,userRoleIds);
            }
        }
        return "list";
    }

    //删除
    public String delete() {
        if (user != null && user.getId() != null) {

            //记载着查询条件
            selectCondition = user.getName();

            userServiceImpl.delete(user.getId());
        }
        return "list";
    }

    public String add() throws IOException {
        if (user != null) {

            //判断用户有没有传入头像
            if (headImg != null) {
                //得到要把头像上传到服务器的路径
                javax.servlet.ServletContext servletContext = ServletActionContext.getServletContext();
                String realPath = servletContext.getRealPath("upload/user");

                //由于用户上传的名字可能会相同，如果相同就被覆盖掉，因此我们要修改上传文件的名字【独一无二】
                headImgFileName = UUID.randomUUID().toString() + headImgFileName.substring(headImgFileName.lastIndexOf("."));
                FileUtils.copyFile(headImg, new File(realPath, headImgFileName));
            }

            //设置图片与用户的关系
            user.setHeadImg(headImgFileName);

            //保存用户与角色。
            userServiceImpl.saveUserAndRole(user, userRoleIds);

            //跳转到列表显示页面
            return "list";
        }
        return null;
    }

    public String deleteSelect() {
        for (String s : selectedRow) {
            userServiceImpl.delete(s);
        }
        return "list";
    }

    /************导出Excel*************************/
    public void exportExcel() throws IOException, ServiceException {

        //查找出列表的全部数据
        List<User> list = userServiceImpl.findObjects();

        //导出其实就是让用户下载该Excel文件
        HttpServletResponse response = ServletActionContext.getResponse();

        //设置头和指定名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("列表展示.xls", "UTF-8"));
        //指定返回的类容数据
        response.setContentType("application/x-execl");

        ServletOutputStream outputStream = response.getOutputStream();

        //给Service层做导出Excel操作
        userServiceImpl.exportExcel(list, outputStream);

    }

    /************导入Excel*************************/
    public String importExcel() throws IOException {

        //1、获取excel文件
        if (userExcel != null) {
            //是否是excel
            if (userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
                //2、导入
                userServiceImpl.importExcel(userExcel, userExcelFileName);
            }
        }
        return "list";
    }

    /************账户一次性校验*************************/
    public void doVerify() {


        try {
            //默认不存在
            String exist = "false";

            //判断账户是否为空，已经用户输入的数据。【我们使用StringUtils这个类来判断】
            //isNotBlank封装了不为null和不为""
            if (user != null && StringUtils.isNotBlank(user.getAccount())) {
                List<User> list = userServiceImpl.findAccount(user.getId(), user.getAccount());

                //如果查询到数据，那么说明该账户已经存在了。
                if (list != null && list.size() > 0) {
                    exist = "true";
                }
            }
            ServletActionContext.getResponse().getWriter().write(exist);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
