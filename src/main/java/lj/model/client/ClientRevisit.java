package lj.model.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;
import org.springframework.format.annotation.DateTimeFormat;

@Alias(value = "ClientRevisit")
public class ClientRevisit extends ClientRequest{
	private Long	clientRevisitId;		//客户回访标识
	private Long	clientId;		//客户标识
	private String clientName;		//客户名称
	private String	revisitType;		//回访类型
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	revisitTime;		//回访时间
	private Integer	revisitTimeSpan;		//回访时长(分钟)
	private String	revisitConent;		//回访内容
	private String fileName;		//文件名称
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	opTime;		//操作时间
	private Long	opUserId;		//操作用户标识
	private String	opUserName;		//操作人
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	revisitTimeBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	revisitTimeEnd;
	//下面是视图字段定义
    
    //下面是查询字段定义
    
	public Long getClientRevisitId() {
		return clientRevisitId;
	}
	public void setClientRevisitId(Long clientRevisitId) {
		this.clientRevisitId = clientRevisitId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getRevisitType() {
		return revisitType;
	}
	public void setRevisitType(String revisitType) {
		this.revisitType = revisitType;
	}
	public Date getRevisitTime() {
		return revisitTime;
	}
	public void setRevisitTime(Date revisitTime) {
		this.revisitTime = revisitTime;
	}
	public Integer getRevisitTimeSpan() {
		return revisitTimeSpan;
	}
	public void setRevisitTimeSpan(Integer revisitTimeSpan) {
		this.revisitTimeSpan = revisitTimeSpan;
	}
	public String getRevisitConent() {
		return revisitConent;
	}
	public void setRevisitConent(String revisitConent) {
		this.revisitConent = revisitConent;
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
	public Date getRevisitTimeBegin() {
		return revisitTimeBegin;
	}

	public void setRevisitTimeBegin(Date revisitTimeBegin) {
		this.revisitTimeBegin = revisitTimeBegin;
	}

	public Date getRevisitTimeEnd() {
		return revisitTimeEnd;
	}

	public void setRevisitTimeEnd(Date revisitTimeEnd) {
		this.revisitTimeEnd = revisitTimeEnd;
	}
	public  ClientRevisit() {
		super();
	}
	
	public  ClientRevisit(
	    Long clientRevisitId,Long clientId,String revisitType,Date revisitTime,Integer revisitTimeSpan,String revisitConent,Date opTime,Long opUserId){
		super();
		this.clientRevisitId= clientRevisitId;
		this.clientId= clientId;
		this.revisitType= revisitType;
		this.revisitTime= revisitTime;
		this.revisitTimeSpan= revisitTimeSpan;
		this.revisitConent= revisitConent;
		this.opTime= opTime;
		this.opUserId= opUserId;
	}
}