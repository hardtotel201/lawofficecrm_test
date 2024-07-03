package lj.model.user;

import java.util.Date;

/**
 * 验证数量
 * 
 * @author samlv
 *
 */
public class VerifyingCount {
	private Date lastTime;
	private int latestCount;

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public int getLatestCount() {
		return latestCount;
	}

	public void setLatestCount(int latestCount) {
		this.latestCount = latestCount;
	}

	public VerifyingCount(Date lastTime, int latestCount) {
		super();
		this.lastTime = lastTime;
		this.latestCount = latestCount;
	}

	public VerifyingCount() {
		super();
		this.lastTime=new Date();
		this.latestCount=0;
	}

}
