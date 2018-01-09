package zhongfucheng.core.dao;

import zhongfucheng.core.utils.PageResult;
import zhongfucheng.core.utils.QueryHelper;

import java.io.Serializable;
import java.util.List;


/**
 * @param <T> 定义Dao层的基本操作。使用泛型，避免了强转。
 * @author ozc
 */
public interface BaseDao<T> {
    //新增
    void save(T entity);

    //更新
    void update(T entity);

    //根据id删除
    void delete(Serializable id);

    //根据id查找
    T findObjectById(Serializable id);

    //查找列表
    List<T> findObjects();

    //根据条件查询列表
    @Deprecated
    List<T> findObjects(String sql, List<Object> objectList);

    //根据查询助手查询
    List<T> findObjects(QueryHelper queryHelper);

    /**
     *
     * @param queryHelper 查询助手，条件查询都交给查询助手来干
     * @param currentPage 当前页数
     * @return
     */
    PageResult getPageResult(QueryHelper queryHelper, int currentPage);

}
