package zhongfucheng.complain.dao;

import zhongfucheng.complain.entity.Complain;
import zhongfucheng.core.dao.BaseDao;

import java.util.List;

/**
 * Created by ozc on 2017/6/12.
 */
public interface ComplainDao extends BaseDao<Complain> {

    List<Object[]> getAnnualStatisticByYear(int year);
}
