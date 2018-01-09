import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zhongfucheng.test.service.TestService;

/**
 * Created by ozc on 2017/5/22.
 */
public class TestMerge {

    private ClassPathXmlApplicationContext context;

    @Before
    public void load() {

        context = new ClassPathXmlApplicationContext("bean.xml");
    }

    @Test
    public void testSpring() {

        TestService testService = (TestService) context.getBean("testServiceImpl");
        System.out.println(testService);
    }

    @Test
    public void testHibernate() {

        SessionFactory factory = (SessionFactory) context.getBean("sessionFactory");

        Session session = factory.openSession();
        session.beginTransaction();

        session.save(new Person("人员1"));

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testMVC() {

        TestService service = (TestService) context.getBean("testServiceImpl");
        service.save(new Person("人员2"));
    }

    @Test
    public void testTx() {

        TestService service = (TestService) context.getBean("testServiceImpl");
        service.save(new Person("人员3"));
    }
}
