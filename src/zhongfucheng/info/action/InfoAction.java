package zhongfucheng.info.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import zhongfucheng.core.action.BaseAction;
import zhongfucheng.core.utils.QueryHelper;
import zhongfucheng.info.entity.Info;
import zhongfucheng.info.service.InfoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by ozc on 2017/5/26.
 */
public class InfoAction extends BaseAction {

    /*************注入Service************************/
    @Autowired
    private InfoService infoServiceImpl;

    /************数据自动封装，给出setter和getter*************************/
    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    /************数据自动封装权限的id*************************/
    private String[] privilegeIds;

    public String[] getPrivilegeIds() {
        return privilegeIds;
    }

    public void setPrivilegeIds(String[] privilegeIds) {
        this.privilegeIds = privilegeIds;
    }


    /************得到Service返回的数据*************************/
    //这里一定要给setter和getter方法，这样JSP才能够得到属性。不然就得不到了！！！我在这里弄了很久！！！！
    private List<Info> infoList;

    public List<Info> getInfoList() {
        return infoList;
    }
    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }



    /************Action中7大方法*************************/
    public String listUI() throws UnsupportedEncodingException {

        QueryHelper queryHelper = new QueryHelper(Info.class, "i");

        //根据info是否为null来判断是否是条件查询。如果info为空，那么是查询所有。
        if (info != null) {
            if (StringUtils.isNotBlank(info.getTitle())) {
                selectCondition =  URLDecoder.decode(info.getTitle(),"UTF-8");
                info.setTitle(selectCondition);
                queryHelper.addCondition(" i.title like ? ", "%" + info.getTitle() + "%");
            }
        }
        queryHelper.orderBy("i.createTime", QueryHelper.ORDER_BY_DESC);

        //当前页数没有值，那么赋值为1
        if (currentPageCount == 0) {
            currentPageCount = 1;
        }
        pageResult = infoServiceImpl.getPageResult(queryHelper,currentPageCount);

        ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
        return "listUI";
    }

    public String addUI() {

        ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
        info = new Info();
        info.setCreateTime(new Timestamp(new Date().getTime()));
        return "addUI";
    }

    public String editUI() {

        //得到所有的信息类型
        ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
        //外边已经传了id过来了，我们要找到id对应的Info
        if (info != null && info.getInfoId() != null) {

            //把查询条件发给JSP页面
            ActionContext.getContext().getContextMap().put("selectCondition", info.getTitle());

            //直接获取出来，后面JSP会根据Info有getter就能读取对应的信息！
            info = infoServiceImpl.findObjectById(info.getInfoId());
        }
        return "editUI";
    }


    public String edit() throws IOException {

        //Struts2会自动把JSP带过来的数据封装到Info对象上
        if (info.getInfoId() != null && info != null) {
            infoServiceImpl.update(info);
        }
        return "list";
    }

    //删除
    public String delete() throws UnsupportedEncodingException {
        selectCondition =  URLDecoder.decode(info.getTitle(),"UTF-8");
        info.setTitle(selectCondition);

        String id = info.getInfoId();
        infoServiceImpl.delete(id);
        return "list";
    }

    public String add() throws IOException {
        if (info != null) {
            //处理角色与权限的关系

            infoServiceImpl.save(info);
            //跳转到列表显示页面
            return "list";
        }
        return null;
    }

    /*批量删除*/
    public String deleteSelect() {
        for (String s : selectedRow) {
            infoServiceImpl.delete(s.trim());
        }
        return "list";
    }

    public void doPublic() {
        try {

            if (info != null) {
                //得到用户带过来的id查询出该对象
                Info objectById = infoServiceImpl.findObjectById(info.getInfoId());
                //设置它的状态
                objectById.setState(info.getState());

                //调用service更新数据库
                infoServiceImpl.update(objectById);

                //返回数据给浏览器
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setContentType("text/html charset=utf-8");
                response.getOutputStream().write("更新成功".getBytes("UTF-8"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
