package lj.model.survey;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

import lj.model.base.ClientRequest;
import org.springframework.format.annotation.DateTimeFormat;

@Alias(value = "SurveyInfo")
public class SurveyInfo extends ClientRequest{
	private Long	surveyId;		//调研标识
	private String	surveyTitle;		//调研标题
	private String	surveyType;		//调研类型(调研/回访)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date	surveyTime;		//调研时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date	surveyTimeBegin;		//调研时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date	surveyTimeEnd;		//调研时间
	private String	surveyMethod;		//调研方法(微信/电话)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	opTime;		//操作时间
	private Long	opUserId;		//操作用户标识

	public Date getSurveyTimeBegin() {
		return surveyTimeBegin;
	}

	public Date getSurveyTimeEnd() {
		return surveyTimeEnd;
	}

	public void setSurveyTimeEnd(Date surveyTimeEnd) {
		this.surveyTimeEnd = surveyTimeEnd;
	}

	public void setSurveyTimeBegin(Date surveyTimeBegin) {
		this.surveyTimeBegin = surveyTimeBegin;
	}

	//下面是视图字段定义
    private List<QuestionInfo> questionInfos;
	private String opUserName;
    //下面是查询字段定义
    
	public Long getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}
	public String getSurveyTitle() {
		return surveyTitle;
	}
	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}
	public String getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}
	public Date getSurveyTime() {
		return surveyTime;
	}
	public void setSurveyTime(Date surveyTime) {
		this.surveyTime = surveyTime;
	}
	public String getSurveyMethod() {
		return surveyMethod;
	}
	public void setSurveyMethod(String surveyMethod) {
		this.surveyMethod = surveyMethod;
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

	public List<QuestionInfo> getQuestionInfos() {
		return questionInfos;
	}

	public void setQuestionInfos(List<QuestionInfo> questionInfos) {
		this.questionInfos = questionInfos;
	}

	public  SurveyInfo() {
		super();
	}
	
	public  SurveyInfo(
	    Long surveyId,String surveyTitle,String surveyType,Date surveyTime,String surveyMethod,Date opTime,Long opUserId){
		super();
		this.surveyId= surveyId;
		this.surveyTitle= surveyTitle;
		this.surveyType= surveyType;
		this.surveyTime= surveyTime;
		this.surveyMethod= surveyMethod;
		this.opTime= opTime;
		this.opUserId= opUserId;
	}
	public String getOpUserName() {
		return opUserName;
	}
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
}