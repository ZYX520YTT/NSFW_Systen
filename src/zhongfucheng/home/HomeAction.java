package zhongfucheng.home;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import zhongfucheng.complain.entity.Complain;
import zhongfucheng.complain.service.ComplainService;
import zhongfucheng.core.action.BaseAction;
import zhongfucheng.core.utils.PageResult;
import zhongfucheng.core.utils.QueryHelper;
import zhongfucheng.info.entity.Info;
import zhongfucheng.info.service.InfoService;
import zhongfucheng.user.entity.User;
import zhongfucheng.user.service.UserService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ozc on 2017/6/2.
 */
public class HomeAction extends BaseAction {
    /****************提供getter方法，Struts2根据它自动转成JSON数据**********************/
    private Map<String, Object> return_map;



    public Map<String, Object> getReturn_map() {
        return return_map;
    }
    public void setReturn_map(Map<String, Object> return_map) {
        this.return_map = return_map;
    }

    /****************封装投诉信息的数据*********************/
    private Complain comp;
    public Complain getComp() {
        return comp;
    }
    public void setComp(Complain comp) {
        this.comp = comp;
    }
    /****************调用userService方法得到数据*********************/
    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private ComplainService complainServiceImpl;
    @Autowired
    private InfoService infoServiceImpl;



    /**************方法**************/
    public String execute() {

        //信息
        ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
        QueryHelper queryHelper = new QueryHelper(Info.class, "i");
        queryHelper.orderBy(" i.createTime", QueryHelper.ORDER_BY_ASC);
        //根据时间来查询
        PageResult pageResult = infoServiceImpl.getPageResult(queryHelper, 1);
        List infoList = pageResult.getList();
        ActionContext.getContext().getContextMap().put("infoList", infoList);

        //投诉信息
        ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("SYS_USER");

        //根据当前用户的信息查询
        QueryHelper queryHelper2 = new QueryHelper(Complain.class, "c");
        queryHelper2.addCondition(" c.compName=?", user.getName());
        queryHelper2.orderBy(" c.compTime", QueryHelper.ORDER_BY_ASC);

        //根据时间来查询
        PageResult pageResult2 = complainServiceImpl.getPageResult(queryHelper2, 1);
        List complainList = pageResult2.getList();
        ActionContext.getContext().getContextMap().put("complainList", complainList);

        return "home";
    }

    public String complainAddUI() {
        return "complainAddUI";
    }
    public String getUserJson() {
        //得到带过来的dept
        String dept = ServletActionContext.getRequest().getParameter("dept");
        if (dept != null) {
            //根据部门查询所有的员工
            QueryHelper queryHelper = new QueryHelper(User.class, "u");
            queryHelper.addCondition(" u.dept like ? ", "%" +dept);

            //2、根据部门查询用户列表
            return_map = new HashMap();
            return_map.put("msg", "success");
            return_map.put("userList", userServiceImpl.findObjects(queryHelper));
        }

        return "success";
    }
    public void saveComplain() {
        try {
            if (comp != null) {
                //把投诉的缺少的信息补全
                comp.setState(Complain.COMPLAIN_STATE_UNDONE);
                comp.setCompTime(new Timestamp(new Date().getTime()));

                //调用service保存
                complainServiceImpl.save(comp);

                //告诉浏览器保存信息成功了。
                ServletActionContext.getResponse().getWriter().write("success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //把信息列表的数据带过去给主页
    public String infoViewUI() {


        return "home";
    }

}
