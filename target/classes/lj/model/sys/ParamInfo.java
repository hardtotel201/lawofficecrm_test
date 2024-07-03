package lj.model.sys;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

/**
 * 参数信息
 * @author samlv
 *
 */
@Alias(value = "ParamInfo")
public class ParamInfo extends ClientRequest{
	public final static String PARAMTYPE_TIME="时间";
	public final static String PARAMTYPE_DATE="日期";
	public final static String PARAMTYPE_DATETIME="日期时间";
	public final static String PARAMTYPE_INT="整数";
	public final static String PARAMTYPE_STRING="字符串";
	public final static String PARAMTYPE_DOUBLE="浮点数";
	
	private Long  paramId;
	private String  paramName;
	private String  paramType;
	private String  paramValue;
	private String  paramMemo;
	private Long  opUserId;
	private Date  opTime;
    //下面是视图字段定义
	private String opUserName;
    
	public Long getParamId() {
		return paramId;
	}
	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamMemo() {
		return paramMemo;
	}
	public void setParamMemo(String paramMemo) {
		this.paramMemo = paramMemo;
	}
	public Long getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(Long opUserId) {
		this.opUserId = opUserId;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	
	public  ParamInfo() {
		super();
	}
	
	public  ParamInfo(
	    Long paramId,String paramName,String paramValue,String paramMemo,Long opUserId,Date opTime){
		super();
		this.paramId= paramId;
		this.paramName= paramName;
		this.paramValue= paramValue;
		this.paramMemo= paramMemo;
		this.opUserId= opUserId;
		this.opTime= opTime;
	}
	public String getOpUserName() {
		return opUserName;
	}
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
	
	public static String[] getParamTypes() {
		String[] paramTypes=new String[] {PARAMTYPE_TIME,PARAMTYPE_DATE,PARAMTYPE_DATETIME,PARAMTYPE_INT,
				PARAMTYPE_STRING,PARAMTYPE_DOUBLE};
		return paramTypes;
	}
}