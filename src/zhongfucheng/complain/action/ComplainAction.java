package zhongfucheng.complain.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import zhongfucheng.complain.entity.Complain;
import zhongfucheng.complain.entity.ComplainReply;
import zhongfucheng.complain.service.ComplainService;
import zhongfucheng.core.action.BaseAction;
import zhongfucheng.core.utils.QueryHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by ozc on 2017/5/23.
 */
public class ComplainAction extends BaseAction {

    /*************注入Service************************/
    @Autowired
    private ComplainService complainServiceImpl;
    /************数据自动封装，给出setter和getter*************************/
    private Complain complain;
    private ComplainReply reply;
    public ComplainReply getReply() {
        return reply;
    }
    public void setReply(ComplainReply reply) {
        this.reply = reply;
    }
    public Complain getComplain() {
        return complain;
    }
    public void setComplain(Complain complain) {
        this.complain = complain;
    }

    /************数据自动封装，投诉条件的时间*************************/
    private String startTime;
    private String endTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /************三圈问题数据回显*************************/
    private String compTitle;
    private String state;
    public String getCompTitle() {
        return compTitle;
    }
    public void setCompTitle(String compTitle) {
        this.compTitle = compTitle;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    /************返回的JSON数据*************************/
    private Map<String, Object> map = new HashedMap();
    public Map<String, Object> getMap() {
        return map;
    }
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /************Action中7大方法*************************/
    //抛出Action异常
    public String listUI() throws UnsupportedEncodingException, ParseException {

        //把状态的集合带过去
        QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
            //先判断时间，通过时间进行筛选后，再进行like模糊查询。那么性能会好一些
            if (StringUtils.isNotBlank(startTime)) {
                startTime =  URLDecoder.decode(startTime,"UTF-8");
                queryHelper.addCondition(" c.compTime >= ? ", DateUtils.parseDate(startTime, new String[]{"yyyy-MM-dd HH:mm"}));
            }

            if (StringUtils.isNotBlank(endTime)) {
                endTime =  URLDecoder.decode(endTime,"UTF-8");
                queryHelper.addCondition(" c.compTime <= ? ", DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd HH:mm"}));
            }

            //根据complain标题是否为null来判断是否是条件查询。如果complain为空，那么是查询所有。
            if (complain != null) {
                if (StringUtils.isNotBlank(complain.getCompTitle())) {
                    selectCondition =  URLDecoder.decode(complain.getCompTitle(),"UTF-8");
                    complain.setCompTitle(selectCondition);
                    queryHelper.addCondition(" c.compTitle like ? ", "%" + complain.getCompTitle() + "%");
                }
                if (StringUtils.isNotBlank(complain.getState())) {
                    queryHelper.addCondition(" c.state like ? ", "%" + complain.getState() + "%");
                }
            }
        //按照未受理排列
        queryHelper.orderBy(" c.state", QueryHelper.ORDER_BY_ASC);
            //当前页数没有值，那么赋值为1
        if (currentPageCount == 0) {
            currentPageCount = 1;
        }
            //把状态带过去给JSP页面
            ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);

            pageResult = complainServiceImpl.getPageResult(queryHelper,currentPageCount);
            return "listUI";

        }

    //提供受理的UI
    public String dealUI() {

        //把状态传递过去
        ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);

        //得到想要受理的记录
        if (complain != null) {
            //把查询条件带过去给JSP页面
            ActionContext.getContext().getContextMap().put("compTitle", complain.getCompTitle());
            ActionContext.getContext().getContextMap().put("state", complain.getState());
            ActionContext.getContext().getContextMap().put("startTime", startTime);
            ActionContext.getContext().getContextMap().put("endTime", endTime);

            complain = complainServiceImpl.findObjectById(complain.getCompId());
        }
        return "dealUI";
    }

    //受理
    public String deal() {

        //修改投诉信息的处理状态
        if (complain != null) {
            //查找到信息
            complain = complainServiceImpl.findObjectById(complain.getCompId());
            //如果状态是已处理了，那么就不用再修改了
            if (!complain.getState().equals(Complain.COMPLAIN_STATE_DONE)) {
                complain.setState(Complain.COMPLAIN_STATE_DONE);
            }
        }
        //保存回复的信息
        if (reply != null) {
            //更新回复的日期
            reply.setReplyTime(new Timestamp(new Date().getTime()));

            //把回复信息添加到投诉信息中【关联关系】
            reply.setComplain(complain);
            complain.getComplainReplies().add(reply);
        }
        //级联更新
        complainServiceImpl.update(complain);

        return "list";
    }

    //跳转到统计页面
    public String annualStatisticChartUI() {

        return "annualStatisticChartUI";
    }


    //返回JSON格式的数据，这里我们就直接用Struts2框架来返回对应的数据就行了。
    public String getAnnualStatisticData() {

        //获取用户传递过来的年份
        String str_year = ServletActionContext.getRequest().getParameter("year");
        if (str_year != null) {
            int year = Integer.valueOf(str_year);
            //根据年份去获取每个月的投诉数
            map.put("msg", "success");
            map.put("chartData", complainServiceImpl.getAnnualStatisticByYear(year));
        }
        return "getAnnualStatisticData";
    }
}
