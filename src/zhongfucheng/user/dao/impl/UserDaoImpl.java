package zhongfucheng.user.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import zhongfucheng.core.dao.impl.BaseDaoImpl;
import zhongfucheng.user.dao.UserDao;
import zhongfucheng.user.entity.User;
import zhongfucheng.user.entity.UserRole;

import java.util.List;

/**
 * Created by ozc on 2017/5/23.
 */

/**
 * UserDao继承了BaseDao，而UserDaoImpl实现了UserDao，那么UserDaoImpl就有了BaseDaoCRUD的方法了。
 * 并且，如果User模块有特有的对数据库操作，就在UserDao中定义出来，UserDaoImpl对其实现，就重写它的方法。
 *
 * 继承着BaseDAoImpl<User>把baseDaoImpl的泛型T确定下来。baseDaoImpl是具体的实现类，
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {


    @Override
    public List<User> findAccount(String id, String account) {

        String hql = "FROM User WHERE account = ? ";

        //判断有没有id,如果有id，多加个条件【本账户不算】
        if (StringUtils.isNotBlank(id)) {

            hql = hql + "  AND id!=?";
        }
        Query query = getSession().createQuery(hql);
        query.setParameter(0, account);
        if (StringUtils.isNotBlank(id)) {
            query.setParameter(1, id);
        }
        List list = query.list();
        return list;

    }

    @Override
    public void saveUserRole(UserRole userRole) {
        getHibernateTemplate().save(userRole);
    }

    @Override
    public List<UserRole> findRoleById(String id) {

        //要想通过用户id查找角色
        String sql = "FROM UserRole WHERE userRoleId.user_id=?";
        return getSession().createQuery(sql).setParameter(0, id).list();

    }

    @Override
    public void deleteUserRoleById(String[] userRoleIds) {

        //在更新操作的时候，会有历史遗留的问题。因此先要把用户对应的角色先删除了。
        for (String userRoleId : userRoleIds) {
            String sql = "DELETE FROM UserRole WHERE userRoleId.role.id=?";
            getSession().createQuery(sql).setParameter(0, userRoleId).executeUpdate();
        }
    }

    @Override
    public List<User> findUserByAccountAndPassword(String account, String password) {
        //通过账户和密码查找对象
        String sql = "FROM User WHERE account=? AND password=?";
        return getSession().createQuery(sql).setParameter(0, account).setParameter(1,password).list();
    }
}
