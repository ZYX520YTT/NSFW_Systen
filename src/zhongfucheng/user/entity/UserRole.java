package zhongfucheng.user.entity;

import java.io.Serializable;

/**
 * Created by ozc on 2017/5/31.
 */
public class UserRole implements Serializable {

    private UserRoleId userRoleId;

    public UserRole() {
    }

    public UserRole(UserRoleId userRoleId) {
        this.userRoleId = userRoleId;
    }

    public UserRoleId getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UserRoleId userRoleId) {
        this.userRoleId = userRoleId;
    }
}
