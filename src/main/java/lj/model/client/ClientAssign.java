package lj.model.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 客户分配
 * @author samlvThinkpad
 *
 */
@Alias(value = "ClientAssign")
public class ClientAssign extends ClientRequest{
	private Long	clientAssignId; //客户分配标识
	private Long	userId;		    //用户标识
	private Long	clientId;		//客户标识
	private String userName;		//用户名称
	private String clientName;		//客户名称
	private Long	assignUserId;   //分配用户标识
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	assignTime;		//分配时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	assignTimeBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	assignTimeEnd;
	private String assignUserName;	//分配人
    //下面是视图字段定义
	private String clientIds;
    //下面是查询字段定义
    
	public Long getClientAssignId() {
		return clientAssignId;
	}
	public void setClientAssignId(Long clientAssignId) {
		this.clientAssignId = clientAssignId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Long getAssignUserId() {
		return assignUserId;
	}
	public String getClientIds() {
		return clientIds;
	}
	public void setClientIds(String clientIds) {
		this.clientIds = clientIds;
	}
	public void setAssignUserId(Long assignUserId) {
		this.assignUserId = assignUserId;
	}
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	public Date getAssignTimeBegin() {
		return assignTimeBegin;
	}

	public Date getAssignTimeEnd() {
		return assignTimeEnd;
	}

	public void setAssignTimeBegin(Date assignTimeBegin) {
		this.assignTimeBegin = assignTimeBegin;
	}

	public void setAssignTimeEnd(Date assignTimeEnd) {
		this.assignTimeEnd = assignTimeEnd;
	}

	public String getAssignUserName() {
		return assignUserName;
	}

	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}
	public  ClientAssign() {
		super();
	}
	
	public  ClientAssign(
	    Long clientAssignId,Long userId,Long clientId,Long assignUserId,Date assignTime){
		super();
		this.clientAssignId= clientAssignId;
		this.userId= userId;
		this.clientId= clientId;
		this.assignUserId= assignUserId;
		this.assignTime= assignTime;
	}
}