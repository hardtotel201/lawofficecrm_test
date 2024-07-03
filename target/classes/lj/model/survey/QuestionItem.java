package lj.model.survey;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

@Alias(value = "QuestionItem")
public class QuestionItem extends ClientRequest{
	private Long	questionItemId;		//问题选择项标识
	private Long	questionId;		//问题标识
	private Integer	itemIndex;		//选择项序号
	private String	itemConent;		//选择项内容
    //下面是视图字段定义
	private String  questionTitle;   //问题题目
    //下面是查询字段定义
    
	public Long getQuestionItemId() {
		return questionItemId;
	}
	public void setQuestionItemId(Long questionItemId) {
		this.questionItemId = questionItemId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Integer getItemIndex() {
		return itemIndex;
	}
	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}
	public String getItemConent() {
		return itemConent;
	}
	public void setItemConent(String itemConent) {
		this.itemConent = itemConent;
	}
	
	public  QuestionItem() {
		super();
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public  QuestionItem(
	    Long questionItemId,Long questionId,Integer itemIndex,String itemConent){
		super();
		this.questionItemId= questionItemId;
		this.questionId= questionId;
		this.itemIndex= itemIndex;
		this.itemConent= itemConent;
	}
}