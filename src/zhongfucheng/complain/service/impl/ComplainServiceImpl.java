package zhongfucheng.complain.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import zhongfucheng.complain.dao.ComplainDao;
import zhongfucheng.complain.entity.Complain;
import zhongfucheng.complain.service.ComplainService;
import zhongfucheng.core.service.impl.BaseServiceImpl;
import zhongfucheng.core.utils.QueryHelper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by ozc on 2017/6/12.
 */
@Service
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements ComplainService {
    private ComplainDao complainDao;
    @Resource
    public void setComplainDao(ComplainDao complainDao) {
        super.setBaseDao(complainDao);
        this.complainDao = complainDao;
    }

    @Override
    public void doTask() {

        //查询所有待受理的信息
        QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
        queryHelper.addCondition("  c.state=?", Complain.COMPLAIN_STATE_UNDONE);

        //只要在本月之前
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        queryHelper.addCondition(" c.compTime <?", calendar.getTime());

        //拿到本月之前所有未处理的数据
        List<Complain> complains = findObjects(queryHelper);

        //将数据全部改成是失效的了。
        for (Complain complain : complains) {
            complain.setState(Complain.COMPLAIN_STATE_INVALID);
        }
    }

    @Override
    public List getAnnualStatisticByYear(int year) {

        List<Object[]> annualStatisticByYear = complainDao.getAnnualStatisticByYear(year);

        List<Map> returnList = new ArrayList<>();

        //得到本年度和本月份
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        int curMonth = Calendar.getInstance().get(Calendar.MONTH)+1;

        //使用Map集合装载着数据
        Map<String,Object> map = null;
        for (Object[] objects : annualStatisticByYear) {
            map = new HashedMap();
            //得到月份
            Integer month = Integer.valueOf(objects[0] + "");
            map.put("label", month + "月");
            if (curYear == year) { //是本年度，那么看看月份是否大于本月份
                if (month > curMonth) {
                    //将数据设置为""
                    map.put("value", "");
                } else {
                    if (objects[1] != null) {
                        map.put("value", objects[1]);
                    } else {
                        map.put("value", "0");
                    }
                }
            }else {//不是本年度
                if (objects[1] != null) {
                    map.put("value", objects[1]);
                } else {
                    map.put("value", "0");
                }
            }
            returnList.add(map);
        }
        return returnList;
    }
}
