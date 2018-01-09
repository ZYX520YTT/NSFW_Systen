package zhongfucheng.complain.dao.impl;

import org.hibernate.SQLQuery;
import zhongfucheng.complain.dao.ComplainDao;
import zhongfucheng.complain.entity.Complain;
import zhongfucheng.core.dao.impl.BaseDaoImpl;

import java.util.List;

/**
 * Created by ozc on 2017/6/12.
 */
public class ComplainDaoImpl extends BaseDaoImpl<Complain> implements ComplainDao {

    /**
     *
     * @param year 根据年获取数据
     * @return  返回的是一个列表数组
     */
    @Override
    public List<Object[]> getAnnualStatisticByYear(int year) {


        //拼接SQL语句
        StringBuffer buffer = new StringBuffer();
        buffer.append(" SELECT imonth,c2 ")
        .append(" FROM t_month")
        .append(" LEFT JOIN (SELECT month(comp_time) c1, count(comp_id) c2 FROM complain WHERE YEAR(comp_time)=? GROUP BY MONTH(comp_time)) t")
        .append(" ON imonth = c1")
        .append(" ORDER BY imonth;");
        SQLQuery sqlQuery = getSession().createSQLQuery(buffer.toString());
        sqlQuery.setParameter(0, year);

        List<Object[]> list = sqlQuery.list();
        return list;
    }
}
