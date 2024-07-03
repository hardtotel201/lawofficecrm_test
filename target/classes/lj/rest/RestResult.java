package lj.rest;

/**
 * Rest返回包装类
 * @author samlv
 *
 */
public class RestResult {
	public final static int RESULTCODE_SUCCESS=1;
	public final static int RESULTCODE_FAIL=0;
	
	/**
	 * 返回操作结果(1:成功；0：失败)
	 */
	private int resultCode;
	
	/**
	 * 返回消息(例如失败原因)
	 */
	private String resultMessage;
	
	/**
	 * 返回数据(JSON)格式
	 */
	private String data;
	
	/**
	 * 构造方法(成功返回)
	 */
	public RestResult() {
		this.resultCode=RESULTCODE_SUCCESS;
		this.resultMessage="";
		this.data="";
	}
	
	/**
	 * 构造方法(成功返回)
	 * @param obj
	 */
	public RestResult(String jsonData) {
		this.resultCode=RESULTCODE_SUCCESS;
		this.resultMessage="";
		this.data = jsonData;
	}
	
	/**
	 * 构造方法
	 * @param resultCode
	 * @param resultMessage
	 */
	public RestResult(int resultCode, String resultMessage) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.data="";
	}
	
	/**
	 * 构造方法
	 * @param resultCode
	 * @param resultMessage
	 * @param data
	 */
	public RestResult(int resultCode, String resultMessage,String jsonData) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.data =jsonData;
	}

	public int getResultCode() {
		return resultCode;
	}
	
	

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getData() {
		return data;
	}

	public void setData(String jsonData) {
		this.data = jsonData;
	}
	
	
	
}
