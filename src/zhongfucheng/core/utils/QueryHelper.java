package zhongfucheng.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozc on 2017/6/7.
 */
public class QueryHelper {

    private String fromClause = "";
    private String whereClause = "";
    private String orderbyClause = "";
    private List<Object> objectList;

    public static String ORDER_BY_ASC = "asc";
    public static String ORDER_BY_DESC = "desc";



    //FROM子句只出现一次
    /**
     * 构建FROM字句，并设置查询哪张表
     * @param aClass 用户想要操作的类型
     * @param alias  别名
     */
    public QueryHelper(Class aClass, String alias) {
        fromClause = "  FROM " + aClass.getSimpleName() + "  " + alias;
    }
    //WHERE字句可以添加多个条件，但WHERE关键字只出现一次
    /**
     * 构建WHERE字句
     * @param condition
     * @param objects
     * @return
     */
    public QueryHelper addCondition(String condition, Object... objects) {
        //如果已经有字符了，那么就说明已经有WHERE关键字了
        if (whereClause.length() > 1) {
            whereClause += " AND  " + condition;
        } else {
            whereClause += " WHERE" + condition;
        }
        //在添加查询条件的时候，?对应的查询条件值
        if (objectList == null) {
            objectList = new ArrayList<>();
        }
        for (Object object : objects) {
            objectList.add(object);
        }
        return this;
    }
    /**
     *
     * @param property 要排序的属性
     * @param order 是升序还是降序
     * @return
     */
    public QueryHelper orderBy(String property, String order) {

        //如果已经有字符了，那么就说明已经有ORDER关键字了
        if (orderbyClause.length() > 0) {
            orderbyClause += " ,  " + property +"   " + order;
        } else {
            orderbyClause += "  ORDER BY " + property+"   " + order;
        }
        return this;
    }

    /**
     * 返回HQL语句
     */
    public String returnHQL() {
        return fromClause + whereClause + orderbyClause;
    }

    /**
     * 得到参数列表
     * @return
     */
    public List<Object> getObjectList() {
        return objectList;
    }

    /**
     *
     * @return 返回查询总记录数的sql语句
     */
    public String getTotalRecordSql() {
        return "SELECT COUNT(*) " + fromClause + whereClause;
    }
}
