package zhongfucheng.core.service;

import zhongfucheng.core.utils.PageResult;
import zhongfucheng.core.utils.QueryHelper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ozc on 2017/6/7.
 */
public interface BaseService<T> {
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
    List<T> findObjects(String sql, List<Object> objectList);

    List<T> findObjects(QueryHelper queryHelper);

    PageResult getPageResult(QueryHelper queryHelper, int currentPage);

}
