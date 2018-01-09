package zhongfucheng.user.dao;

import zhongfucheng.core.dao.BaseDao;
import zhongfucheng.user.entity.User;
import zhongfucheng.user.entity.UserRole;

import java.util.List;

/**
 * Created by ozc on 2017/5/23.
 */

/**
 * 继承着BaseDao接口，于是就有了BaseDao的方法了。
 * 继承了BaseDao<User>，于是就给出了泛型T的类型了。
 * */
public interface UserDao extends BaseDao<User> {


    //因为我们不是在查id，因此是不能保证只有一个User对象的，即使在AJAX已经做了检查。因此返回值是个List集合
    List<User> findAccount(String id, String account);

    void saveUserRole(UserRole userRole);

    //通过用户id查找所有userRole
    List<UserRole> findRoleById(String id);

    //更新user对象和userRole对象
    void deleteUserRoleById(String[] userRoleIds);
    List<User> findUserByAccountAndPassword(String account, String password);


}
