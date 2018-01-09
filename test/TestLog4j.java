import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * Created by ozc on 2017/5/22.
 */
public class TestLog4j {
    Log log = LogFactory.getLog(getClass());

    @Test
    public void test4j() {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
        log.fatal("fatal");
    }
}
