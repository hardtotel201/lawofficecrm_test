package lj.util.query;

import java.util.Date;

/**
 * 时间查询条件
 * @author samlv
 *
 */
public class DateCondition {
	public Date beginValue=null;
	public Date endValue=null;
	
	public DateCondition(){
		beginValue=null;
		endValue=null;
	}
	
	public DateCondition(Date beginValue,Date endValue){
		this.beginValue=beginValue;
		this.endValue=endValue;
	}
	
	@Override
	public String toString()
	{
		String str="begin time:"+(this.beginValue==null?"null":this.beginValue);
		str=str+"——end time:"+(this.endValue==null?"null":this.endValue);
		return str;
	}
}
