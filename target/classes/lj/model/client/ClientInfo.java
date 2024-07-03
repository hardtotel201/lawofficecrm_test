package lj.model.client;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;
import lj.util.StringUtils;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 客户信息类
 * @author samlvThinkpad
 *
 */
@Data
@Alias(value = "ClientInfo")
public class ClientInfo extends ClientRequest{
	private Long	clientId;		//客户标识
	@ExcelProperty("客户名称")
	private String	clientName;		//客户名称
	@ExcelProperty("微信")
	private String	clientWeixin;		//客户微信
	@ExcelProperty("地址")
	private String	clientAddress;		//客户地址
	@ExcelProperty("联系方式（*）")
	private String	clientPhone;		//联系方式
	@ExcelProperty("邮政编码")
	private String	clientZipCode;		//客户邮政编码
	@ExcelProperty("联系人")
	private String	clientContacts;		//联系人
	@ExcelProperty("客户类型（个人/公司）")
	private String	clientType;		//客户类型(个人/公司)
	@ExcelProperty("邮箱")
	private String	clientEmail;		//客户邮箱
	@ExcelProperty("决策人")
	private String  ceo;				//决策人
	@ExcelProperty("联系方式")
	private String  ceoPhone;			//联系方式（决策人电话）
	@ExcelProperty("账号")
	private String	clientAccount;		//客户账号
	@ExcelProperty("开户行")
	private String	clientBank;		//客户开户行
	@ExcelProperty("客户等级")
	private String	clientLevel;		//客户等级
	@ExcelIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	registerTime;		//注册时间
	@ExcelProperty("客户备注")
	private String	clientMemo;		//客户备注
	@ExcelProperty("状态")
	private String	clientState;		//客户状态
	@ExcelProperty("来源数据表")
	private String sourceTable;
	@ExcelProperty("来源标识")
	private Long sourceId;
	@ExcelProperty("客户来源")
	private String sourceMemo;
	@ExcelIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	opTime;		//操作时间
	@ExcelIgnore
	private Long	opUserId;		//opUserId
	private String opUserName;            //操作用户姓名
	@ExcelProperty("法定代表人")
	private String legalRepresentative;   //法定代表人
	@ExcelProperty("注册金额/实缴金额")
	private String legalMoney;		//注册金额/实缴金额
	@ExcelProperty("公司注册时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date legalTime;		//公司注册时间
	@ExcelProperty("参保人数")
	private Integer insuredNumber;		//参保人数
	@ExcelProperty("经营范围")
	private String businessScope;		//经营范围
	@ExcelProperty("股权构成")
	private String equityConstituent;		//股权构成
	@ExcelProperty("企业名下资产/股东和企业对外投资任职情况")
	private String parttimeMemo;		//企业名下资产/股东和企业对外投资任职情况
	@ExcelProperty("招标信息")
	private String biddingMemo;		//招标信息
	@ExcelProperty("诉讼情况")
	private String lawMemo;		//诉讼情况
	private Long assignUserId;		//被分配用户id
	@ExcelProperty("被分配用户")
	private String assignUserName;		//被分配用户名称
    //下面是视图字段定义
    
    //下面是查询字段定义
	private Boolean isRightJoinClientAssign=true;   //是否右连接ClientAssign表
	private Boolean isQueryMonthNew  =false;        //是否查询待跟进用户
	private Boolean isQueryMonthTransacted=false;        //是否查询30天内未跟进用户
	private Boolean isQueryUnrevisit =false;        //是否查询待回访用户
	private Long    loginUserId;                    //登录用户标识
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerTimeBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerTimeEnd;

    
	
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientWeixin() {
		return clientWeixin;
	}
	public void setClientWeixin(String clientWeixin) {
		this.clientWeixin = clientWeixin;
	}

	public String getCeo() {
		return ceo;
	}

	public void setCeo(String ceo) {
		this.ceo = ceo;
	}

	public String getCeoPhone() {
		return ceoPhone;
	}

	public void setCeoPhone(String ceoPhone) {
		this.ceoPhone = ceoPhone;
	}

	public String getLawMemo() {
		return lawMemo;
	}

	public void setLawMemo(String lawMemo) {
		this.lawMemo = lawMemo;
	}

	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	public String getClientZipCode() {
		return clientZipCode;
	}
	public void setClientZipCode(String clientZipCode) {
		this.clientZipCode = clientZipCode;
	}
	public String getClientContacts() {
		return clientContacts;
	}
	public void setClientContacts(String clientContacts) {
		this.clientContacts = clientContacts;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public String getClientAccount() {
		return clientAccount;
	}
	public void setClientAccount(String clientAccount) {
		this.clientAccount = clientAccount;
	}
	public String getClientBank() {
		return clientBank;
	}
	public void setClientBank(String clientBank) {
		this.clientBank = clientBank;
	}
	public String getClientLevel() {
		return clientLevel;
	}
	public void setClientLevel(String clientLevel) {
		this.clientLevel = clientLevel;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Long getAssignUserId() {
		return assignUserId;
	}

	public void setAssignUserId(Long assignUserId) {
		this.assignUserId = assignUserId;
	}

	public String getAssignUserName() {
		return assignUserName;
	}

	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}

	public String getRegisterTimeStr() {
		if(registerTime==null)
			return "";
		String str=StringUtils.timeToString(registerTime,StringUtils.DATETIME_FORMAT);
		return str;
	}
	
	public String getClientMemo() {
		return clientMemo;
	}
	public void setClientMemo(String clientMemo) {
		this.clientMemo = clientMemo;
	}
	public String getClientState() {
		return clientState;
	}
	public void setClientState(String clientState) {
		this.clientState = clientState;
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
	public String getSourceTable() {
		return sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceMemo() {
		return sourceMemo;
	}

	public void setSourceMemo(String sourceMemo) {
		this.sourceMemo = sourceMemo;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getLegalMoney() {
		return legalMoney;
	}

	public void setLegalMoney(String legalMoney) {
		this.legalMoney = legalMoney;
	}

	public Date getLegalTime() {
		return legalTime;
	}

	public void setLegalTime(Date legalTime) {
		this.legalTime = legalTime;
	}

	public Integer getInsuredNumber() {
		return insuredNumber;
	}

	public void setInsuredNumber(Integer insuredNumber) {
		this.insuredNumber = insuredNumber;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getEquityConstituent() {
		return equityConstituent;
	}

	public void setEquityConstituent(String equityConstituent) {
		this.equityConstituent = equityConstituent;
	}

	public String getParttimeMemo() {
		return parttimeMemo;
	}

	public void setParttimeMemo(String parttimeMemo) {
		this.parttimeMemo = parttimeMemo;
	}

	public String getBiddingMemo() {
		return biddingMemo;
	}

	public void setBiddingMemo(String biddingMemo) {
		this.biddingMemo = biddingMemo;
	}

	public  ClientInfo() {
		super();
	}
	
	public  ClientInfo(
	    Long clientId,String clientName,String clientWeixin,String clientAddress,String clientPhone,String clientZipCode,String clientContacts,String clientType,String clientEmail,String clientAccount,String clientBank,String clientLevel,Date registerTime,String clientMemo,String clientState,Date opTime,Long opUserId, Long assignUserId){
		super();
		this.clientId= clientId;
		this.clientName= clientName;
		this.clientWeixin= clientWeixin;
		this.clientAddress= clientAddress;
		this.clientPhone= clientPhone;
		this.clientZipCode= clientZipCode;
		this.clientContacts= clientContacts;
		this.clientType= clientType;
		this.clientEmail= clientEmail;
		this.clientAccount= clientAccount;
		this.clientBank= clientBank;
		this.clientLevel= clientLevel;
		this.registerTime= registerTime;
		this.clientMemo= clientMemo;
		this.clientState= clientState;
		this.opTime= opTime;
		this.opUserId= opUserId;
		this.assignUserId= assignUserId;
	}
	public Boolean getIsRightJoinClientAssign() {
		return isRightJoinClientAssign;
	}
	public void setIsRightJoinClientAssign(Boolean isRightJoinClientAssign) {
		this.isRightJoinClientAssign = isRightJoinClientAssign;
	}
	public Boolean getIsQueryMonthNew() {
		return isQueryMonthNew;
	}
	public void setIsQueryMonthNew(Boolean isQueryMonthNew) {
		this.isQueryMonthNew = isQueryMonthNew;
	}
	public Boolean getIsQueryMonthTransacted() {
		return isQueryMonthTransacted;
	}
	public void setIsQueryMonthTransacted(Boolean isQueryMonthTransacted) {
		this.isQueryMonthTransacted = isQueryMonthTransacted;
	}
	public Boolean getIsQueryUnrevisit() {
		return isQueryUnrevisit;
	}
	public void setIsQueryUnrevisit(Boolean isQueryUnrevisit) {
		this.isQueryUnrevisit = isQueryUnrevisit;
	}
	public Long getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}
	public Date getRegisterTimeBegin() {
		return registerTimeBegin;
	}
	public void setRegisterTimeBegin(Date registerTimeBegin) {
		this.registerTimeBegin = registerTimeBegin;
	}
	public Date getRegisterTimeEnd() {
		return registerTimeEnd;
	}
	public void setRegisterTimeEnd(Date registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}
	
}