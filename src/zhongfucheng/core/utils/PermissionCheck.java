package zhongfucheng.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import zhongfucheng.role.entity.Role;
import zhongfucheng.role.entity.RolePrivilege;
import zhongfucheng.user.entity.User;
import zhongfucheng.user.entity.UserRole;
import zhongfucheng.user.service.UserService;

import java.util.List;
import java.util.Set;

/**
 * Created by ozc on 2017/6/4.
 */

public class PermissionCheck {

    private User user;
    private String code;

    @Autowired
    private UserService userServiceImpl;


    public boolean check(User user, String code) {
        this.user = user;
        this.code = code;

        //得到该用户的所有权限
        List<UserRole> userRoles = user.getUserRoles();

        if (userRoles == null) {
            userRoles = userServiceImpl.findRoleById(user.getId());
        }

        //遍历初用户拥有的角色，看看有没有对应的权限
        for (UserRole userRole : userRoles) {
            Role role = userRole.getUserRoleId().getRole();

            //得到角色所拥有的权限
            Set<RolePrivilege> rolePrivilegeSet = role.getRolePrivilegeSet();

            //遍历权限，看看有没有nsfw的权限
            for (RolePrivilege privilege : rolePrivilegeSet) {
                String code1 = privilege.getCompositeKey().getCode();
                if (code1.equals(code)) {//如果该用户有权限
                    return true;
                }
            }
        }
        //遍历完都没有return true，那么就是没有权限了。
        return false;
    }
}
