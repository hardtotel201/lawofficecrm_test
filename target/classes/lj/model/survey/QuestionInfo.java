package lj.model.survey;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

import lj.model.base.ClientRequest;
import org.springframework.format.annotation.DateTimeFormat;

@Alias(value = "QuestionInfo")
public class QuestionInfo extends ClientRequest{
	private Long	questionId;		//问题标识
	private String	questionTitle;		//问题题目
	private String	questionType;		//问题类型(选择题/问答题)
	private String questionMemo;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	opTime;		//操作时间
	private Long	opUserId;		//操作用户标识
    //下面是视图字段定义
    private List<QuestionItem> questionItems;
	private Long surveyQuestionId;
	private String opUserName;
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

	public List<QuestionItem> getQuestionItems() {
		return questionItems;
	}

	public void setQuestionItems(List<QuestionItem> questionItems) {
		this.questionItems = questionItems;
	}

	public String getQuestionMemo() {
		return questionMemo;
	}

	public void setQuestionMemo(String questionMemo) {
		this.questionMemo = questionMemo;
	}

	public  QuestionInfo() {
		super();
	}
	
	public  QuestionInfo(
	    Long questionId,String questionTitle,String questionType,Date opTime,Long opUserId){
		super();
		this.questionId= questionId;
		this.questionTitle= questionTitle;
		this.questionType= questionType;
		this.opTime= opTime;
		this.opUserId= opUserId;
	}

	public QuestionInfo(Long questionId, String questionTitle, String questionType, String questionMemo, Date opTime,
						Long opUserId, List<QuestionItem> questionItems, Long surveyQuestionId, String opUserName) {
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.questionType = questionType;
		this.questionMemo = questionMemo;
		this.opTime = opTime;
		this.opUserId = opUserId;
		this.questionItems = questionItems;
		this.surveyQuestionId = surveyQuestionId;
		this.opUserName = opUserName;
	}

	public String getOpUserName() {
		return opUserName;
	}
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
}