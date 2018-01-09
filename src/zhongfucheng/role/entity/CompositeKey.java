package zhongfucheng.role.entity;

import java.io.Serializable;

/**
 * Created by ozc on 2017/5/26.
 */
public class CompositeKey implements Serializable {


    public CompositeKey() {
    }
    private Role role;
    private String code;

    public CompositeKey(Role role, String code) {
        this.role = role;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKey that = (CompositeKey) o;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return role != null ? role.equals(that.role) : that.role == null;

    }
    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
