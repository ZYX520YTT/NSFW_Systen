package zhongfucheng.role.dao;

/**
 * Created by ozc on 2017/5/26.
 */

import zhongfucheng.core.dao.BaseDao;
import zhongfucheng.role.entity.Role;

/**
 * RoleDao接口，继承着BaseDao
 * */
public interface RoleDao extends BaseDao<Role> {

    void deleteRolePrivilegeByRoleId(String roleId);

}
