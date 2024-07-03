package lj.model.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;
import org.springframework.format.annotation.DateTimeFormat;

@Alias(value = "ClientFollow")
public class ClientFollow extends ClientRequest{
	private Long	clientFollowId;		//客户跟进标识
	private Long	clientId;		//客户标识
	private String clientName;		//客户名称
	private String	followType;		//跟进类型
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	followTime;		//跟进时间
	private Integer	followTimeSpan;		//跟进时长(分钟)
	private String  serviceItem;		//服务项
	private String followSales;		//
	private String followSalesPerson;	//跟进商务

	private String	followContent;		//跟进内容
	private String  contractOperator;	//供应商
	private String fileName;		//文件名称
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	opTime;		//操作时间
	private Long	opUserId;		//操作用户标识
	private String	opUserName;		//操作人
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	followTimeBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	followTimeEnd;
    //下面是视图字段定义
    
    //下面是查询字段定义
    
	public Long getClientFollowId() {
		return clientFollowId;
	}
	public void setClientFollowId(Long clientFollowId) {
		this.clientFollowId = clientFollowId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getFollowType() {
		return followType;
	}
	public void setFollowType(String followType) {
		this.followType = followType;
	}
	public Date getFollowTime() {
		return followTime;
	}
	public void setFollowTime(Date followTime) {
		this.followTime = followTime;
	}
	public Integer getFollowTimeSpan() {
		return followTimeSpan;
	}
	public void setFollowTimeSpan(Integer followTimeSpan) {
		this.followTimeSpan = followTimeSpan;
	}
	public String getFollowSales() {
		return followSales;
	}
	public void setFollowSales(String followSales) {
		this.followSales = followSales;
	}
	public String getFollowSalesPerson() {
		return followSalesPerson;
	}
	public void setFollowSalesPerson(String followSalesPerson) {
		this.followSalesPerson = followSalesPerson;
	}
	public String getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(String serviceItem) {
		this.serviceItem = serviceItem;
	}
	public String getFollowContent() {
		return followContent;
	}
	public void setFollowContent(String followContent) {
		this.followContent = followContent;
	}
	public String getContractOperator() {
		return contractOperator;
	}
	public void setContractOperator(String contractOperator) {
		this.contractOperator = contractOperator;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getOpUserName() {
		return opUserName;
	}

	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
	public Date getFollowTimeBegin() {
		return followTimeBegin;
	}

	public void setFollowTimeBegin(Date followTimeBegin) {
		this.followTimeBegin = followTimeBegin;
	}

	public Date getFollowTimeEnd() {
		return followTimeEnd;
	}

	public void setFollowTimeEnd(Date followTimeEnd) {
		this.followTimeEnd = followTimeEnd;
	}


	public  ClientFollow() {
		super();
	}
	
	public  ClientFollow(
	    Long clientFollowId,Long clientId,String followType,Date followTime,Integer followTimeSpan,String followSales,String followSalesPerson,String followContent,String contractOperator,Date opTime,Long opUserId){
		super();
		this.clientFollowId= clientFollowId;
		this.clientId= clientId;
		this.followType= followType;
		this.followTime= followTime;
		this.followTimeSpan= followTimeSpan;
		this.followSales= followSales;
		this.followSalesPerson= followSalesPerson;
		this.followContent= followContent;
		this.contractOperator= contractOperator;
		this.opTime= opTime;
		this.opUserId= opUserId;

	}
}