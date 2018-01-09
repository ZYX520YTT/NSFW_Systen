package zhongfucheng.user.entity;

import zhongfucheng.role.entity.Role;

import java.io.Serializable;

/**
 * Created by ozc on 2017/5/31.
 */
public class UserRoleId implements Serializable {

    private String user_id;

    //在使用的时候，Role相关的数据会用得特别多。为了方便使用了Role对象。而user就不需要使用User对象了。
    private Role role;

    public UserRoleId() {
    }

    public UserRoleId(String user_id, Role role) {
        this.user_id = user_id;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleId that = (UserRoleId) o;

        if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;
        return role != null ? role.equals(that.role) : that.role == null;

    }

    @Override
    public int hashCode() {
        int result = user_id != null ? user_id.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
