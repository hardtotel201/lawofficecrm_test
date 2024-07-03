package lj.model.base;

import java.io.Serializable;

/*
 * 查询上下文(线程共享分页对象管理)
 */
public class QueryContext implements Serializable{
	private static ThreadLocal<Integer> pageSizes=new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> pageIndexs=new ThreadLocal<Integer>();
	private static ThreadLocal<String> sortFields=new ThreadLocal<String>();
	private static ThreadLocal<String> sortTypes=new ThreadLocal<String>();
	private static ThreadLocal<Integer> pageDraws=new ThreadLocal<Integer>();
	
	public static Integer getPageSize() {
		return pageSizes.get();
	}
	public static void setPageSize(Integer pageSize) {
		QueryContext.pageSizes.set(pageSize);
	}
	public static void removePageSize(){
		QueryContext.pageSizes.remove();
	}
	
	public static Integer getPageIndex() {
		return pageIndexs.get();
	}
	public static void setPageIndex(Integer pageIndex) {
		QueryContext.pageIndexs.set(pageIndex);
	}
	public static void removePageIndex(){
		QueryContext.pageIndexs.remove();
	}
	
	public static String getSortField() {
		return sortFields.get();
	}
	public static void setSortField(String sortField) {
		QueryContext.sortFields.set(sortField);
	}
	public static void removeSortField(){
		QueryContext.sortFields.remove();
	}
	
	public static String getSortType() {
		return sortTypes.get();
	}
	public static void setSortType(String sortType) {
		QueryContext.sortTypes.set(sortType);
	}
	public static void removeSortType(){
		QueryContext.sortTypes.remove();
	}
	
	public static Integer getPageDraw(){
		return pageDraws.get();
	}
	public static void setPageDraw(Integer pageDraw){
		pageDraws.set(pageDraw);
	}
	public static void removePageDraw(){
		pageDraws.remove();
	}
	
}
