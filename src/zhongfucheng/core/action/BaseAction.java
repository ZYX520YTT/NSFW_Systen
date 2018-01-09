package zhongfucheng.core.action;

import com.opensymphony.xwork2.ActionSupport;
import zhongfucheng.core.utils.PageResult;

/**
 * Created by ozc on 2017/5/26.
 */
public class BaseAction extends ActionSupport {

    protected String[] selectedRow;

    public String[] getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(String[] selectedRow) {
        this.selectedRow = selectedRow;
    }

    /************分页属性*************************/
    protected int currentPageCount;
    protected PageResult pageResult;
    public int getCurrentPageCount() {
        return currentPageCount;
    }
    public void setCurrentPageCount(int currentPageCount) {
        this.currentPageCount = currentPageCount;
    }
    public PageResult getPageResult() {
        return pageResult;
    }
    public void setPageResult(PageResult pageResult) {
        this.pageResult = pageResult;
    }

    /************获取查询条件的值*************************/
    protected String selectCondition;
    public String getSelectCondition() {
        return selectCondition;
    }
    public void setSelectCondition(String selectCondition) {
        this.selectCondition = selectCondition;
    }
}
