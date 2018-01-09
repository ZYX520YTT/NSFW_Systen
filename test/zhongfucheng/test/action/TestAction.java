package zhongfucheng.test.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by ozc on 2017/5/22.
 */
public class TestAction extends ActionSupport {


    @Qualifier("testServiceImpl")
    @Autowired
    public String test2() {
        return SUCCESS;
    }
}
