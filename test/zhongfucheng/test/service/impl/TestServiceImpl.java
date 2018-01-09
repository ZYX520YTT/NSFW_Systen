package zhongfucheng.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhongfucheng.test.dao.TestDao;

/**
 * Created by ozc on 2017/5/22.
 */

@Service
public class TestServiceImpl implements zhongfucheng.test.service.TestService {


    @Autowired
    private TestDao testDaoImpl;
    @Override
    public void save(Person person) {
        testDaoImpl.save(person);
        int i = 1 / 0;
    }
}
