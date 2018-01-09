package zhongfucheng.core.exception;

/**
 * Created by ozc on 2017/5/26.
 */

/**
 * Action的异常类
 * */
public class ActionException extends SysException {

    public ActionException() {

        super("请求操作失败了！");
    }

    public ActionException(String message) {
        super(message);
    }
}
