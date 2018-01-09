package zhongfucheng.core.dao.impl;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import zhongfucheng.core.dao.BaseDao;
import zhongfucheng.core.utils.PageResult;
import zhongfucheng.core.utils.QueryHelper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * 该类是抽象类，实现了BaseDao接口
 * @param <T>
 *
 *
 *     在该实现类也还是不知道泛型，于是还是给出T
 *
 */
public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	Class<T> clazz;


	//当继承它的子类使用的时候，都会把类型参数传递进来【通过构造方法】，于是我们就可以获取具体的类型了。
	public BaseDaoImpl(){
		ParameterizedType pt =  (ParameterizedType)this.getClass().getGenericSuperclass();//BaseDaoImpl<User>
		clazz = (Class<T>)pt.getActualTypeArguments()[0];
	}

	@Override
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findObjectById(id));
	}

	@Override
	public T findObjectById(Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findObjects() {
		Query query = getSession().createQuery("FROM " + clazz.getSimpleName());
		return query.list();
	}

	@Override
	public List<T> findObjects(String sql, List<Object> objectList) {
		Query query = getSession().createQuery(sql);
		if (objectList != null) {
			int i =0;
			for (Object o : objectList) {
				query.setParameter(i, o);
				i++;
			}
			return query.list();
		}
		return query.list();
	}

	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		Query query = getSession().createQuery(queryHelper.returnHQL());
		if (queryHelper.getObjectList() != null) {
			int i =0;
			for (Object o : queryHelper.getObjectList()) {
				query.setParameter(i, o);
				i++;
			}
			return query.list();
		}
		return query.list();
	}

	@Override
	public PageResult getPageResult(QueryHelper queryHelper, int currentPage) {

		//查询总记录数
		Query queryCount = getSession().createQuery(queryHelper.getTotalRecordSql());
		if (queryHelper.getObjectList() != null) {
			int i =0;
			for (Object o : queryHelper.getObjectList()) {
				queryCount.setParameter(i, o);
				i++;
			}
		}
		Long totalRecord = (Long) queryCount.uniqueResult();

		//初始化PageResult对象
		PageResult pageResult = new PageResult(currentPage, totalRecord);

		//查询具体模块的数据【有查询条件的也可以处理】
		Query query = getSession().createQuery(queryHelper.returnHQL());
		if (queryHelper.getObjectList() != null) {
			int i =0;
			for (Object o : queryHelper.getObjectList()) {
				query.setParameter(i, o);
				i++;
			}
		}

		//设置分页开始和末尾
		query.setFirstResult(pageResult.getStartIndex());
		query.setMaxResults(pageResult.getLineSize());
		List dataList = query.list();

		//将条件查询出来的数据设置到Page对象中
		pageResult.setList(dataList);
		return pageResult;
	}
}
