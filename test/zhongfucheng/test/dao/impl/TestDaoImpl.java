package zhongfucheng.test.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import zhongfucheng.test.dao.TestDao;

/**
 * Created by ozc on 2017/5/22.
 */


public class TestDaoImpl extends HibernateDaoSupport implements TestDao {

    @Override
    public void save(Person person) {
        getHibernateTemplate().save(person);
    }
}
