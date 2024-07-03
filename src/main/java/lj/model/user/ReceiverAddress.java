package lj.model.user;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

/**
 * 收货人地址
 * @author samlv
 *
 */
@Alias(value = "ReceiverAddress")
public class ReceiverAddress extends ClientRequest{
	public final static String ADDRESS_STATE_NORMAL  ="正常";
	public final static String ADDRESS_STATE_WRITEOFF="注销";
	
	private Long  receiverAddressId;
	private Long  userId;
	private Long  schoolAddressId;
	private String  isDefault;
	private String  detailAddress;
	private String  receiverName;
	private String  receiverMobile;
	private String  addressState;        //地址状态(正常/注销)
    //下面是视图字段定义
    private String userName;             //用户姓名
    private Long   schoolId;             //学校标识
    private String schoolName;           //学校名称
    private String schoolAddress;        //学校地址
    
	public Long getReceiverAddressId() {
		return receiverAddressId;
	}
	public void setReceiverAddressId(Long receiverAddressId) {
		this.receiverAddressId = receiverAddressId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSchoolAddressId() {
		return schoolAddressId;
	}
	public void setSchoolAddressId(Long schoolAddressId) {
		this.schoolAddressId = schoolAddressId;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	
	
	public String getAddressState() {
		return addressState;
	}
	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}
	public  ReceiverAddress() {
		super();
	}
	
	public  ReceiverAddress(
	    Long receiverAddressId,Long userId,Long schoolAddressId,String isDefault,String detailAddress,
	    String receiverName,String receiverMobile,String addressState){
		super();
		this.receiverAddressId= receiverAddressId;
		this.userId= userId;
		this.schoolAddressId= schoolAddressId;
		this.isDefault= isDefault;
		this.detailAddress= detailAddress;
		this.receiverName= receiverName;
		this.receiverMobile= receiverMobile;
		this.addressState=addressState;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolAddress() {
		return schoolAddress;
	}
	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}
	
	
}