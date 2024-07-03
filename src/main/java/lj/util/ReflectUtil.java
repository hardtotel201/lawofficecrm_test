package lj.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 * 
 * @author samlv
 *
 */
public class ReflectUtil {
	
	/**
	 * 禁止实例对象
	 */
	private ReflectUtil() {
	}

	/**
	 * 获得泛型类对象的参数类型数组
	 * @param obj:泛型类对象
	 * @return
	 */
	public static Type[] getGenericParamTypes(Object obj){
		ParameterizedType parameterizedType =  (ParameterizedType) obj.getClass().getGenericSuperclass();
		Type[] types = parameterizedType.getActualTypeArguments();
		return types;
	}

}
