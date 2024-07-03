package lj.util;

import java.util.List;

/**
 * jQuery DataTable扩展返回对象
 * 
 * @author samlv
 *
 */
public class DatatablesReturnObject<T> {
	private int draw;
	private long recordsTotal;
	private long recordsFiltered;
	private List<T> data;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T>  getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public DatatablesReturnObject(int draw, long recordsTotal, long recordsFiltered,  List<T>  data) {
		super();
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}
	
	public DatatablesReturnObject(int draw, long recordsTotal,List<T>  data) {
		this(draw,recordsTotal,recordsTotal,data);
	}

	public DatatablesReturnObject() {
		super();
	}
}
