package lj.model.sys;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lj.model.base.ClientRequest;

@Alias(value = "LogInfo")
public class LogInfo  extends ClientRequest {
	public final static String OP_NAME_LOGIN="登陆";
	public final static String OP_NAME_CONTROL="控制";
	public final static String OP_NAME_INSERT="新增";
	public final static String OP_NAME_UPDATE="修改";
	public final static String OP_NAME_UPDATE_PASSWORD="修改密码";
	public final static String OP_NAME_DELETE="删除";
	public final static String OP_NAME_WRITEOFF="注销";
	public final static String OP_DEBUG="调试";
	
	private Long  logId;
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	private String  opName;
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	
	private String  opData;
	
	
	
	public String getOpData() {
		return opData;
	}
	public void setOpData(String opData) {
		this.opData = opData;
	}
	
	private String  opEntity;
	public String getOpEntity() {
		return opEntity;
	}
	public void setOpEntity(String opEntity) {
		this.opEntity = opEntity;
	}
	
	private Long  opUserId;
	public Long getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(Long opUserId) {
		this.opUserId = opUserId;
	}
	private Date  opTime;
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	private String  userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	

}