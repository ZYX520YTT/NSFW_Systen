package zhongfucheng.role.service.impl;

import org.springframework.stereotype.Service;
import zhongfucheng.core.service.impl.BaseServiceImpl;
import zhongfucheng.role.dao.RoleDao;
import zhongfucheng.role.entity.Role;
import zhongfucheng.role.service.RoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ozc on 2017/5/26.
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    private RoleDao roleDaoImpl;
    @Resource
    public void setRoleDaoImpl(RoleDao roleDaoImpl) {
        super.setBaseDao(roleDaoImpl);
        this.roleDaoImpl = roleDaoImpl;
    }
    @Override
    public void update(Role role) {
        //在修改之前，把角色的所有权限给删除了。不然会遗留之前的权限下来。
        roleDaoImpl.deleteRolePrivilegeByRoleId(role.getRoleId());
        roleDaoImpl.update(role);
    }

    @Override
    public List<Role> findObjects(String sql, List<Object> objectList) {
        return null;
    }


}
