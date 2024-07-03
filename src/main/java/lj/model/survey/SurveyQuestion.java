package lj.model.survey;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

@Alias(value = "SurveyQuestion")
public class SurveyQuestion extends ClientRequest{
	private Long	surveyQuestionId;		//问卷问题标识
	private Long	questionId;		//问题标识
	private Long	surveyId;		//调研标识
	private Long    opUserId;       //操作用户标识
	private Date    opTime;         //操作时间
    //下面是视图字段定义
	private String questionTitle;    //问题题目
	private String questionType;    //问题类型
	private String surveyTitle;      //调研标题
	private String opUserName;       //操作用户名称
	//下面是查询字段定义
    
	public Long getSurveyQuestionId() {
		return surveyQuestionId;
	}
	public void setSurveyQuestionId(Long surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Long getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
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
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public  SurveyQuestion() {
		super();
	}
	
	public  SurveyQuestion(
	    Long surveyQuestionId,Long questionId,Long surveyId){
		super();
		this.surveyQuestionId= surveyQuestionId;
		this.questionId= questionId;
		this.surveyId= surveyId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	
	
	
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getSurveyTitle() {
		return surveyTitle;
	}
	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}

	
}