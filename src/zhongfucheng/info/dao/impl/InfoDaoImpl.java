package zhongfucheng.info.dao.impl;
import zhongfucheng.core.dao.impl.BaseDaoImpl;
import zhongfucheng.info.dao.InfoDao;
import zhongfucheng.info.entity.Info;

/**
 * Created by ozc on 2017/5/23.
 */

/**
 * 继承着BaseDaoImpl实现类，就有了CRUD的方法
 * 又实现了InfoDao接口，那么InfoDao接口就可以对Info模块有相对应的补充
 */
public class InfoDaoImpl extends BaseDaoImpl<Info> implements InfoDao {

}
