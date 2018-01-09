package zhongfucheng.core.service.impl;

import zhongfucheng.core.dao.BaseDao;
import zhongfucheng.core.service.BaseService;
import zhongfucheng.core.utils.PageResult;
import zhongfucheng.core.utils.QueryHelper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ozc on 2017/6/7.
 */

public abstract class BaseServiceImpl <T> implements BaseService <T>{

    //通过BaseDao来操作数据库
    private BaseDao<T> baseDao;
    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public void save(T entity) {
        baseDao.save(entity);

    }

    @Override
    public void update(T entity) {
        baseDao.update(entity);

    }

    @Override
    public void delete(Serializable id) {
        baseDao.delete(id);

    }
    @Override
    public T findObjectById(Serializable id) {
        return baseDao.findObjectById(id);
    }

    @Override
    public List<T> findObjects() {
        return baseDao.findObjects();

    }

    @Override
    public List<T> findObjects(String sql, List<Object> objectList) {
        return baseDao.findObjects(sql, objectList);
    }

    @Override
    public List<T> findObjects(QueryHelper queryHelper) {
        return baseDao.findObjects(queryHelper);
    }

    public PageResult getPageResult(QueryHelper queryHelper, int currentPage) {
        return baseDao.getPageResult(queryHelper, currentPage);
    }

}
