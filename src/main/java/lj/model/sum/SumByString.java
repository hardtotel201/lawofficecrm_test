package lj.model.sum;

import org.apache.ibatis.type.Alias;

/**
 * 统计汇总模型
 * @author samlv
 *
 */
@Alias(value = "SumByString")
public class SumByString {
	private String sumCategory;
	private String sumTitle;
	private double sumCount;   //统计1
	
	//视图字段
	private String siteName;
	private double sumCount2;  //统计2
	private double sumCount3;  //统计3
	
	public String getSumCategory() {
		return sumCategory;
	}
	public void setSumCategory(String sumCategory) {
		this.sumCategory = sumCategory;
	}
	public String getSumTitle() {
		return sumTitle;
	}
	public void setSumTitle(String sumTitle) {
		this.sumTitle = sumTitle;
	}
	public double getSumCount() {
		return sumCount;
	}
	public void setSumCount(double sumCount) {
		this.sumCount = sumCount;
	}
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	
	
	public double getSumCount2() {
		return sumCount2;
	}
	public void setSumCount2(double sumCount2) {
		this.sumCount2 = sumCount2;
	}
	public double getSumCount3() {
		return sumCount3;
	}
	public void setSumCount3(double sumCount3) {
		this.sumCount3 = sumCount3;
	}
	public SumByString() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SumByString(String sumCategory, String sumTitle, double sumCount) {
		super();
		this.sumCategory = sumCategory;
		this.sumTitle = sumTitle;
		this.sumCount = sumCount;
	}
	
	
	public SumByString(String sumTitle, double sumCount) {
		super();
		this.sumTitle = sumTitle;
		this.sumCount = sumCount;
	}
	
	public SumByString(String sumTitle, double sumCount, double sumCount2, double sumCount3) {
		super();
		this.sumTitle = sumTitle;
		this.sumCount = sumCount;
		this.sumCount2=sumCount2;
		this.sumCount3=sumCount2;
	}
	
}
