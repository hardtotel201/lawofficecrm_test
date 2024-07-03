package lj.mapper.base;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis基本接口
 * @author samlv
 *
 * @param <T>
 */
public interface BaseMapper<T> {
	/**
	 * 新增
	 * @param params
	 * @return 影响行数
	 */
	int insert(T obj);
	
	
	
	/**
	 * 删除
	 * @param obj
	 * @return
	 */
	int delete(T obj);
	
	
	/**
	 * 删除
	 * @param obj
	 * @return
	 */
	int deleteById(long id);

	/**
	 * 修改
	 * @param obj
	 * @return
	 */
	int update(T obj);
	
	/**
	 * 根据对象标识查询
	 * @param id
	 * @return
	 */
	T findById(long id);
	
	
	/**
	 * 根据对象属性查询
	 * @param obj
	 * @return
	 */
	T find(T obj);
	
	/**
	 * 根据查询参数查询对象
	 * @param params
	 * @return
	 */
	T find(Map<String, Object> params);
	
	/**
	 * 根据对象属性查询
	 * @param obj
	 * @return
	 */
	T[] findAll(T obj);
	
	/**
	 * 根据查询参数查询对象列表
	 * @return
	 */
	T[] findAll();
	
	T[] findAllByField(@Param("fieldName") String fieldName, @Param("fieldValue")Object fieldValue);
	
}
