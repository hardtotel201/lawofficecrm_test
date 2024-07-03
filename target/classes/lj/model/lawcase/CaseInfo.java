package lj.model.lawcase;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;

import lj.model.base.ClientRequest;

public class CaseInfo extends ClientRequest{
	private Long	caseId;		//案件标识
	private Long	userId;		//用户标识
	private Long	lawyerId;		//律师标识
	private Long	clientId;		//客户标识
	private String userName;
	private String	lawyerName;		//律师姓名
	private String	clientName;		//客户名称
	private String	caseCode;		//案件编号
	private String	caseName;		//案件名称
	private String	caseType;		//案件类型
	private String	caseConent;		//委托内容
	private String	caseSource;		//案件来源
	private Double	casePrice;		//案件标的
	private Double	lawyerFee;		//律师费
	private String	feeType;		//收费方式
	private String	payType;		//支付方式

	private String caseProgram;
	private String	otherClient;
	private String	casePhrase;
	private String	predealMatters;
	private String	dealUnit;
	private String	dealJudge;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date	acceptTime;		//收案时间
	private String	contractCode;		//合同编号
	private String	caseState;		//案件状态
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	opTime;		//操作时间
	private Long	opUserId;		//操作用户标识
	private String opUserName;


    //下面是视图字段定义
    
    //下面是查询字段定义

	public Long getCaseId() {
		return caseId;
	}
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLawyerName() {
		return lawyerName;
	}
	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}

	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(Long lawyerId) {
		this.lawyerId = lawyerId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getCaseConent() {
		return caseConent;
	}
	public void setCaseConent(String caseConent) {
		this.caseConent = caseConent;
	}
	public String getCaseSource() {
		return caseSource;
	}
	public void setCaseSource(String caseSource) {
		this.caseSource = caseSource;
	}
	public Double getCasePrice() {
		return casePrice;
	}
	public void setCasePrice(Double casePrice) {
		this.casePrice = casePrice;
	}
	public Double getLawyerFee() {
		return lawyerFee;
	}
	public void setLawyerFee(Double lawyerFee) {
		this.lawyerFee = lawyerFee;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Date getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCaseState() {
		return caseState;
	}
	public void setCaseState(String caseState) {
		this.caseState = caseState;
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
	public String getOpUserName() {
		return opUserName;
	}
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}

	public String getCaseProgram() {
		return caseProgram;
	}

	public void setCaseProgram(String caseProgram) {
		this.caseProgram = caseProgram;
	}

	public String getOtherClient() {
		return otherClient;
	}

	public void setOtherClient(String otherClient) {
		this.otherClient = otherClient;
	}

	public String getCasePhrase() {
		return casePhrase;
	}

	public void setCasePhrase(String casePhrase) {
		this.casePhrase = casePhrase;
	}

	public String getPredealMatters() {
		return predealMatters;
	}

	public void setPredealMatters(String predealMatters) {
		this.predealMatters = predealMatters;
	}

	public String getDealUnit() {
		return dealUnit;
	}

	public void setDealUnit(String dealUnit) {
		this.dealUnit = dealUnit;
	}

	public String getDealJudge() {
		return dealJudge;
	}

	public void setDealJudge(String dealJudge) {
		this.dealJudge = dealJudge;
	}

	public  CaseInfo() {
		super();
	}
	
	public  CaseInfo(
	    Long caseId,Long userId,Long lawyerId,Long clientId,String caseCode,
		String caseName,String caseType,String caseConent,String caseSource,Double casePrice,
		Double lawyerFee,String feeType,String payType,Date acceptTime,String contractCode,
		String caseState,Date opTime,Long opUserId, String lawyerName,
		String caseProgram,String otherClient,String casePhrase,String predealMatters,String dealUnit,String dealJudge){
		super();
		this.caseId= caseId;
		this.userId= userId;
		this.lawyerId= lawyerId;
		this.clientId= clientId;
		this.caseCode= caseCode;
		this.caseName= caseName;
		this.caseType= caseType;
		this.caseConent= caseConent;
		this.caseSource= caseSource;
		this.casePrice= casePrice;
		this.lawyerFee= lawyerFee;
		this.feeType= feeType;
		this.payType= payType;
		this.acceptTime= acceptTime;
		this.contractCode= contractCode;
		this.caseState= caseState;
		this.opTime= opTime;
		this.opUserId= opUserId;
		this.lawyerName=lawyerName;
		this.casePhrase=casePhrase;
		this.otherClient=otherClient;
		this.predealMatters=predealMatters;
		this.dealUnit=dealUnit;
		this.caseProgram=caseProgram;
		this.dealJudge=dealJudge;
	}

}