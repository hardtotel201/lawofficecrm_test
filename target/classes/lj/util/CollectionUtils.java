package lj.util;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 集合工具类
 * 
 * @author samlv
 *
 */
public class CollectionUtils {

    /**
     * 交集
     * @param <T>
     * @param list1
     * @param list2
     * @return
     */
	public static <T> List<T> intersect(List<T> list1, List<T> list2) {
		List<T> ret = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
		return ret;
	}
	
	/**
	 * 差集
	 * @param <T>
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static <T> List<T> sub(List<T> list1, List<T> list2) {
		List<T> ret = list1.stream().filter(item -> list2.contains(item)==false).collect(Collectors.toList());
		return ret;
	}

}
