package zhongfucheng.core.exception;

/**
 * Created by ozc on 2017/5/26.
 */
public class ServiceException extends SysException {
    public ServiceException() {
        super("操作业务失败了！");

    }

    public ServiceException(String message) {
        super(message);
    }
}
