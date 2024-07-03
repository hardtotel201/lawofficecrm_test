package lj.model.lawcase;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

@Alias(value = "CaseEvaluate")
public class CaseEvaluate extends ClientRequest{
	private Long	caseEvaluateId;		//案件评价标识
	private Long	caseId;		//案件标识
	private Date	evaluateTime;		//评价时间
	private String	evaluateContent;		//评价结果
	private Date	opTime;		//操作时间
	private Long	opUserId;		//操作用户标识
    //下面是视图字段定义
	private String caseName;
	private String lawyerName;
	private String opUserName;
    
    //下面是查询字段定义
    
	public Long getCaseEvaluateId() {
		return caseEvaluateId;
	}
	public void setCaseEvaluateId(Long caseEvaluateId) {
		this.caseEvaluateId = caseEvaluateId;
	}
	public Long getCaseId() {
		return caseId;
	}
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	public Date getEvaluateTime() {
		return evaluateTime;
	}
	public void setEvaluateTime(Date evaluateTime) {
		this.evaluateTime = evaluateTime;
	}
	public String getEvaluateContent() {
		return evaluateContent;
	}
	public void setEvaluateContent(String evaluateContent) {
		this.evaluateContent = evaluateContent;
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
	
	public  CaseEvaluate() {
		super();
	}
	
	public  CaseEvaluate(
	    Long caseEvaluateId,Long caseId,Date evaluateTime,String evaluateContent,Date opTime,Long opUserId){
		super();
		this.caseEvaluateId= caseEvaluateId;
		this.caseId= caseId;
		this.evaluateTime= evaluateTime;
		this.evaluateContent= evaluateContent;
		this.opTime= opTime;
		this.opUserId= opUserId;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	
	public String getLawyerName() {
		return lawyerName;
	}
	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}
	public String getOpUserName() {
		return opUserName;
	}
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
	
	
}