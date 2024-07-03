package lj.model.lawcase;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

@Alias(value = "FileTemplate")
public class FileTemplate extends ClientRequest{
	private Long	fileTemplateId;		//文档模板标识
	private String	templateName;		//模板名称
	private String	templateCategory;		//模板分类
	private String	fileName;		//文件名称
	private Date	opTime;		//操作时间
	private Long	opUserId;		//操作用户标识
    //下面是视图字段定义
    private String opUserName;     //操作用户姓名
    //下面是查询字段定义
    
	public Long getFileTemplateId() {
		return fileTemplateId;
	}
	public void setFileTemplateId(Long fileTemplateId) {
		this.fileTemplateId = fileTemplateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateCategory() {
		return templateCategory;
	}
	public void setTemplateCategory(String templateCategory) {
		this.templateCategory = templateCategory;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
	public  FileTemplate() {
		super();
	}
	
	public  FileTemplate(
	    Long fileTemplateId,String templateName,String templateCategory,String fileName,Date opTime,Long opUserId){
		super();
		this.fileTemplateId= fileTemplateId;
		this.templateName= templateName;
		this.templateCategory= templateCategory;
		this.fileName= fileName;
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