package zhongfucheng.core.exception;

/**
 * Created by ozc on 2017/5/26.
 */

/****
 * 这是我们自定义的总系统异常类
 *
 * */
public class SysException extends Exception {

    //用来记录错误的信息！
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public SysException() {
    }

    public SysException(String message) {
        super(message);
        this.errorMsg= message;
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
        this.errorMsg= message;
    }

    public SysException(Throwable cause) {
        super(cause);
    }
}
