package zhongfucheng.role.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by ozc on 2017/5/26.
 */
public class Role implements Serializable {
    private String roleId;
    private String state;
    private String name;
    private Set<RolePrivilege> rolePrivilegeSet;

    public static String USER_STATE_VALID = "1";//有效，
    public static String USER_STATE_INVALID = "0";//无效

    public Role() {
    }

    public Role(String roleId, String state, String name, Set<RolePrivilege> rolePrivilegeSet) {
        this.roleId = roleId;
        this.state = state;
        this.name = name;
        this.rolePrivilegeSet = rolePrivilegeSet;
    }

    public Role(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<RolePrivilege> getRolePrivilegeSet() {
        return rolePrivilegeSet;
    }

    public void setRolePrivilegeSet(Set<RolePrivilege> rolePrivilegeSet) {
        this.rolePrivilegeSet = rolePrivilegeSet;
    }

    public static String getUserStateValid() {
        return USER_STATE_VALID;
    }

    public static void setUserStateValid(String userStateValid) {
        USER_STATE_VALID = userStateValid;
    }

    public static String getUserStateInvalid() {
        return USER_STATE_INVALID;
    }

    public static void setUserStateInvalid(String userStateInvalid) {
        USER_STATE_INVALID = userStateInvalid;
    }
}
