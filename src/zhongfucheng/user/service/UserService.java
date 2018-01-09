package zhongfucheng.user.service;


import zhongfucheng.core.service.BaseService;
import zhongfucheng.user.entity.User;
import zhongfucheng.user.entity.UserRole;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.util.List;

/**
 * created by ozc on 2017/5/23.
 */

/**
 * 具体的
 */
public interface UserService extends BaseService<User> {

    //导出用户列表
    void exportExcel(List<User> userList, ServletOutputStream outputStream);

    //导入用户列表
    void importExcel(File userExcel, String userExcelFileName);

    /**
     * 根据帐号和用户id查询用户
     *
     * @param id      用户ID
     * @param account 用户帐号
     * @return 用户列表
     */
    List<User> findAccount(String id, String account);

    void saveUserAndRole(User user, String[] userRoleIds);

    //通过用户id得到该用户的角色
    List<UserRole> findRoleById(String id);

    void deleteUserRoleById(String[] userRoleIds);

    List<User> findUserByAccountAndPassword(String account, String password);
}
