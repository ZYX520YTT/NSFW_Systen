package zhongfucheng.user.service.impl;

import org.springframework.stereotype.Service;
import zhongfucheng.core.service.impl.BaseServiceImpl;
import zhongfucheng.core.utils.ExcelUtils;
import zhongfucheng.role.entity.Role;
import zhongfucheng.user.dao.UserDao;
import zhongfucheng.user.entity.User;
import zhongfucheng.user.entity.UserRole;
import zhongfucheng.user.entity.UserRoleId;
import zhongfucheng.user.service.UserService;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by ozc on 2017/5/23.
 */

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

    private UserDao userDaoImpl;

    @Resource
    public void setUserDaoImpl(UserDao userDaoImpl) {
        super.setBaseDao(userDaoImpl);
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    public void exportExcel(List<User> list, ServletOutputStream outputStream) {
        ExcelUtils.exportExcel(list, outputStream);
    }

    @Override
    public void importExcel(File userExcel, String userExcelFileName) {
        List<User> users = ExcelUtils.importExcel(userExcel, userExcelFileName);
        for (User user : users) {
            save(user);
        }

    }

    @Override
    public List<User> findAccount(String id, String account) {
        return userDaoImpl.findAccount(id, account);
    }

    @Override
    public void saveUserAndRole(User user, String... userRoleIds) {

        //保存或更新用户
        if (user.getId() != null) {
            userDaoImpl.update(user);
        } else {
            userDaoImpl.save(user);
        }
        //判断有没有把id带过来
        if (userRoleIds != null) {
            for (String userRoleId : userRoleIds) {
                System.out.println(userRoleId);
                userDaoImpl.saveUserRole(new UserRole(new UserRoleId(user.getId(), new Role(userRoleId))));
            }
        }
    }

    @Override
    public List<UserRole> findRoleById(String id) {
        return userDaoImpl.findRoleById(id);
    }

    @Override
    public void deleteUserRoleById(String[] userRoleIds) {
        userDaoImpl.deleteUserRoleById(userRoleIds);
    }

    @Override
    public List<User> findUserByAccountAndPassword(String account, String password) {

        return userDaoImpl.findUserByAccountAndPassword(account, password);
    }


    @Override
    public List<User> findObjects(String sql, List<Object> objectList) {
        return null;
    }
}
