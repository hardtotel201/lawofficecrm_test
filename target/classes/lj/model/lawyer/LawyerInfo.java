package lj.model.lawyer;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

@Alias(value = "LawyerInfo")
public class LawyerInfo extends ClientRequest{
	private Long	lawyerId;		//律师标识
	private String	lawyerName;		//律师姓名
	private String	lawyerPhone;		//律师电话
	private String	lawyerAddress;		//律师地址
	private String	lawyerEmail;		//律师邮箱
	private String officeName;//律所名称
	private String officeAddress;//律所地址
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	registerTime;		//注册时间
	private String	lawyerState;		//律师状态
	private Date	opTime;		//操作时间
	private Long	opUserId;		//操作用户标识
    //下面是视图字段定义
	private String opUserName;            //操作用户姓名
    //下面是查询字段定义
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerTimeBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerTimeEnd;
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	public Long getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(Long lawyerId) {
		this.lawyerId = lawyerId;
	}
	public String getLawyerName() {
		return lawyerName;
	}
	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}
	public String getLawyerPhone() {
		return lawyerPhone;
	}
	public void setLawyerPhone(String lawyerPhone) {
		this.lawyerPhone = lawyerPhone;
	}
	public String getLawyerAddress() {
		return lawyerAddress;
	}
	public void setLawyerAddress(String lawyerAddress) {
		this.lawyerAddress = lawyerAddress;
	}
	public String getLawyerEmail() {
		return lawyerEmail;
	}
	public void setLawyerEmail(String lawyerEmail) {
		this.lawyerEmail = lawyerEmail;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getLawyerState() {
		return lawyerState;
	}
	public void setLawyerState(String lawyerState) {
		this.lawyerState = lawyerState;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public Long getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(Long opUserId) {
		this.opUserId = opUserId;
	}
	
	public  LawyerInfo() {
		super();
	}
	
	public  LawyerInfo(
	    Long lawyerId,String lawyerName,String lawyerPhone,String lawyerAddress,String lawyerEmail, String officeName,
		String officeAddress,Date registerTime,String lawyerState,Date opTime,Long opUserId){
		super();
		this.lawyerId= lawyerId;
		this.lawyerName= lawyerName;
		this.lawyerPhone= lawyerPhone;
		this.lawyerAddress= lawyerAddress;
		this.lawyerEmail= lawyerEmail;
		this.registerTime= registerTime;
		this.lawyerState= lawyerState;
		this.opTime= opTime;
		this.opUserId= opUserId;
		this.officeName=officeName;
		this.officeAddress=officeAddress;
	}
	public String getOpUserName() {
		return opUserName;
	}
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
	public Date getRegisterTimeBegin() {
		return registerTimeBegin;
	}
	public void setRegisterTimeBegin(Date registerTimeBegin) {
		this.registerTimeBegin = registerTimeBegin;
	}
	public Date getRegisterTimeEnd() {
		return registerTimeEnd;
	}
	public void setRegisterTimeEnd(Date registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}
	
	
}