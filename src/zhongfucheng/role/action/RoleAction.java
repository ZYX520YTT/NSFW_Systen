package zhongfucheng.role.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import zhongfucheng.core.action.BaseAction;
import zhongfucheng.core.constant.Constant;
import zhongfucheng.core.utils.QueryHelper;
import zhongfucheng.role.entity.CompositeKey;
import zhongfucheng.role.entity.Role;
import zhongfucheng.role.entity.RolePrivilege;
import zhongfucheng.role.service.RoleService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ozc on 2017/5/26.
 */
public class RoleAction extends BaseAction {

    /*************注入Service************************/
    @Autowired
    private RoleService roleServiceImpl;

    /************数据自动封装，给出setter和getter*************************/
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }




    /************Action中7大方法*************************/
    public String listUI() throws UnsupportedEncodingException {

        QueryHelper queryHelper = new QueryHelper(Role.class, "r");

        //根据role是否为null来判断是否是条件查询。如果role为空，那么是查询所有。
        if (role != null) {
            if (StringUtils.isNotBlank(role.getName())) {
                selectCondition =  URLDecoder.decode(role.getName(),"UTF-8");
                role.setName(selectCondition);
                queryHelper.addCondition(" r.name like ? ", "%" + role.getName() + "%");
            }
        }
        //当前页数没有值，那么赋值为1
        if (currentPageCount == 0) {
            currentPageCount = 1;
        }
        pageResult = roleServiceImpl.getPageResult(queryHelper,currentPageCount);

        ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
        return "listUI";
    }

    public String addUI() {

        ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
        return "addUI";
    }

    public String editUI() {


        //得到所有的权限
        ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);

        //外边已经传了id过来了，我们要找到id对应的Role
        if (role != null && role.getRoleId() != null) {
            //直接获取出来，后面JSP会根据Role有getter就能读取对应的信息！
            role = roleServiceImpl.findObjectById(role.getRoleId());

            //得到角色所有的权限，把它封装到privilegeIds字符数组中。
            //处理权限回显
            if (role.getRolePrivilegeSet() != null) {
                privilegeIds = new String[role.getRolePrivilegeSet().size()];

                int i = 0;
                for (RolePrivilege rp : role.getRolePrivilegeSet()) {
                    privilegeIds[i++] = rp.getCompositeKey().getCode();
                }
            }

        }
        return "editUI";
    }


    public String edit() throws IOException {
        //Struts2会自动把JSP带过来的数据封装到Role对象上
        if (role.getRoleId() != null && role != null) {

            Set<RolePrivilege> set = new HashSet<>();
            //得到修改的权限id，封装到set集合中。
            for (String privilegeId : privilegeIds) {
                set.add(new RolePrivilege(new CompositeKey(role, privilegeId)));
            }
            role.setRolePrivilegeSet(set);

            roleServiceImpl.update(role);
        }
        return "list";
    }

    //删除
    public String delete() {

        String id = role.getRoleId();
        System.out.println(id);

        roleServiceImpl.delete(id);
        return "list";
    }

    public String add() throws IOException {
        if (role != null) {
            //处理角色与权限的关系
            if (privilegeIds != null) {
                HashSet<RolePrivilege> set = new HashSet<>();
                //得到每一个权限的值--->entity给出对应的构造方法...
                for (int i = 0; i < privilegeIds.length; i++) {
                    set.add(new RolePrivilege(new CompositeKey(role, privilegeIds[i])));
                }
                role.setRolePrivilegeSet(set);
            }
            roleServiceImpl.save(role);
            //跳转到列表显示页面
            return "list";
        }
        return null;
    }

    /*批量删除*/
    public String deleteSelect() {
        for (String s : selectedRow) {

            roleServiceImpl.delete(s.trim());
        }
        return "list";
    }

}
