package lj.model.user;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 验证码模型
 * @author samlv
 *
 */
public class VerifyingCode {
	private String userMobile;
	private String verifyingCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getVerifyingCode() {
		return verifyingCode;
	}
	public void setVerifyingCode(String verifyingCode) {
		this.verifyingCode = verifyingCode;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public VerifyingCode(String userMobile, String verifyingCode, Date sendTime) {
		super();
		this.userMobile = userMobile;
		this.verifyingCode = verifyingCode;
		this.sendTime = sendTime;
	}
	public VerifyingCode() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
