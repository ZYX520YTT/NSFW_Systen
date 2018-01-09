package zhongfucheng.test.entity;

/**
 * Created by ozc on 2017/6/5.
 */
public class TPrivilegeEntity {
    private String privilegeId;
    private String name;

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TPrivilegeEntity that = (TPrivilegeEntity) o;

        if (privilegeId != null ? !privilegeId.equals(that.privilegeId) : that.privilegeId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = privilegeId != null ? privilegeId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
