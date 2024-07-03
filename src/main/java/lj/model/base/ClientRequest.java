package lj.model.base;

import java.io.Serializable;

/**
 * 模型基类
 * @author samlv
 *
 */
public class ClientRequest implements Serializable{
	//平台分页参数
	private Integer pageIndex;
	private Integer pageSize;
	
	//jquery datatable分页参数
	private Integer draw;         
	private Integer start;   
	private Integer length;
	
	//排序参数
	private String sortType;
	private String sortField;

	
	
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	
	
}
