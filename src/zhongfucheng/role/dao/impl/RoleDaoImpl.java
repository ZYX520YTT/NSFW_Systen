package zhongfucheng.role.dao.impl;

/**
 * Created by ozc on 2017/5/26.
 */

import org.hibernate.Query;
import zhongfucheng.core.dao.impl.BaseDaoImpl;
import zhongfucheng.role.dao.RoleDao;
import zhongfucheng.role.entity.Role;

/**
 * 继承着BaseDaoImpl实现类，就有了CRUD的方法
 * 又实现了RoleDao接口，那么RoleDao接口就可以对Role模块有相对应的补充
 */
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {


    /***
     * 根据角色id删除所有的权限
     * */
    @Override
    public void deleteRolePrivilegeByRoleId(String roleId) {

        String sql = "DELETE FROM RolePrivilege WHERE compositeKey.role.roleId= ?";

        Query query = getSession().createQuery(sql);
        query.setParameter(0, roleId);
        query.executeUpdate();
    }
}
