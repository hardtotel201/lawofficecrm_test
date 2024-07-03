package lj.model.survey;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;
import org.springframework.format.annotation.DateTimeFormat;

@Alias(value = "SurveyQuestionnaire")
public class SurveyQuestionnaire extends ClientRequest{
	private Long	surveyQuestionnaireId;   //问卷回答标识
	private Long	clientId;		        //客户标识
	private String	sourceMemo;				//来源渠道
	private Long	surveyQuestionId;		//问卷问题标识
	private Long    caseId;                 //案件标识
	private String	questionnaireState;		//问卷状态(创建/已回答)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	answerTime;		//回答时间
	private String	answerContent;		//回答内容
	private String answerFileName;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	opTime;		//操作时间
	private Long	opUserId;		//操作用户标识
    //下面是视图字段定义
	private String clientName;
	private String surveyTitle;
	private String surveyType;
	private Date   surveyTime;
	private String surveyMethod;
	private String questionTitle;
	private String questionType;
	private String opUserName;
	//下面是查询字段定义
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	answerTimeBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	answerTimeEnd;

	public String getOpUserName() {
		return opUserName;
	}

	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
	

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	

	public String getSurveyTitle() {
		return surveyTitle;
	}

	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}

	public String getSourceMemo() {
		return sourceMemo;
	}

	public void setSourceMemo(String sourceMemo) {
		this.sourceMemo = sourceMemo;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
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

	public Long getSurveyQuestionnaireId() {
		return surveyQuestionnaireId;
	}
	public void setSurveyQuestionnaireId(Long surveyQuestionnaireId) {
		this.surveyQuestionnaireId = surveyQuestionnaireId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getSurveyQuestionId() {
		return surveyQuestionId;
	}
	public void setSurveyQuestionId(Long surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}
	
	
	
	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getQuestionnaireState() {
		return questionnaireState;
	}
	public void setQuestionnaireState(String questionnaireState) {
		this.questionnaireState = questionnaireState;
	}
	public Date getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
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

	public String getAnswerFileName() {
		return answerFileName;
	}

	public void setAnswerFileName(String answerFileName) {
		this.answerFileName = answerFileName;
	}

	public  SurveyQuestionnaire() {
		super();
	}
	
	public  SurveyQuestionnaire(
	    Long surveyQuestionnaireId,Long clientId,Long surveyQuestionId,String questionnaireState,Date answerTime,String answerContent,Date opTime,Long opUserId){
		super();
		this.surveyQuestionnaireId= surveyQuestionnaireId;
		this.clientId= clientId;
		this.sourceMemo= sourceMemo;
		this.surveyQuestionId= surveyQuestionId;
		this.questionnaireState= questionnaireState;
		this.answerTime= answerTime;
		this.answerContent= answerContent;
		this.opTime= opTime;
		this.opUserId= opUserId;
	}

	public Date getAnswerTimeBegin() {
		return answerTimeBegin;
	}

	public void setAnswerTimeBegin(Date answerTimeBegin) {
		this.answerTimeBegin = answerTimeBegin;
	}

	public Date getAnswerTimeEnd() {
		return answerTimeEnd;
	}

	public void setAnswerTimeEnd(Date answerTimeEnd) {
		this.answerTimeEnd = answerTimeEnd;
	}
	
	
}