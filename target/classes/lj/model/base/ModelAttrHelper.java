package lj.model.base;

import java.lang.reflect.Field;

/**
 * 模型属性工具类
 * @author samlv
 *
 */
public class ModelAttrHelper {

	/**
	 * 显示对象属性(必须两个参数，必须考虑多态)
	 * @param cls
	 * @param obj
	 * @return
	 */
	public static String toString(Class cls,Object obj)
	{
		String str = "";
		try {
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields)
				if (field.isAnnotationPresent(ModelAttr.class) == true) {
					boolean oldAccess=field.isAccessible();
					field.setAccessible(true);
					ModelAttr[] attrs = field.getAnnotationsByType(ModelAttr.class);
					if (attrs.length > 0) {
						str = str + attrs[0].title();
						if(field.get(obj)==null)
							str = str + "=null;";
						else
							str = str + "=" + field.get(obj) + ";";
					}
					field.setAccessible(oldAccess);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获得成员标题
	 * @param cls:类
	 * @param obj:对象
	 * @param attrName:属性名称(字段名称)
	 * @return
	 */
	public static String getFieldTitle(Class cls,String fieldName)
	{
		try {
			Field[] fields = cls.getFields();//本类和父类
			for (Field field : fields)
				if (field.getName().equals(fieldName) && field.isAnnotationPresent(ModelAttr.class) == true) {
					field.setAccessible(true);
					ModelAttr[] attrs = field.getAnnotationsByType(ModelAttr.class);
					if (attrs.length > 0)
						return attrs[0].title();
					else
						return fieldName;
				}
			return fieldName;
		} catch (Exception e) {
			e.printStackTrace();
			return fieldName;
		}
	}
}
