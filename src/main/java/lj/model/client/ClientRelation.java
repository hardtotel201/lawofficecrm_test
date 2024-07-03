package lj.model.client;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

@Alias(value = "ClientRelation")
public class ClientRelation extends ClientRequest{
	private Long	clientRelationId;		//客户关联标识
	private String 	clientName;
	private String 	clientName1;
	private String 	clientName2;
	private Long	clientId1;		//客户标识1
	private Long	clientId2;		//客户标识2
	private String	relationType;   //关联类型
	private String	relationContent;//关联说明
    //下面是视图字段定义
    
    //下面是查询字段定义
	private Long queryClientId;
    
	public Long getClientRelationId() {
		return clientRelationId;
	}
	public void setClientRelationId(Long clientRelationId) {
		this.clientRelationId = clientRelationId;
	}
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientName1() {
		return clientName1;
	}

	public void setClientName1(String clientName1) {
		this.clientName1 = clientName1;
	}

	public String getClientName2() {
		return clientName2;
	}

	public void setClientName2(String clientName2) {
		this.clientName2 = clientName2;
	}
	public Long getClientId1() {
		return clientId1;
	}
	public void setClientId1(Long clientId1) {
		this.clientId1 = clientId1;
	}
	public Long getClientId2() {
		return clientId2;
	}
	public void setClientId2(Long clientId2) {
		this.clientId2 = clientId2;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public String getRelationContent() {
		return relationContent;
	}
	public void setRelationContent(String relationContent) {
		this.relationContent = relationContent;
	}
	
	public  ClientRelation() {
		super();
	}
	
	public  ClientRelation(
	    Long clientRelationId,Long clientId1,Long clientId2,String relationType,String relationContent){
		super();
		this.clientRelationId= clientRelationId;
		this.clientId1= clientId1;
		this.clientId2= clientId2;
		this.relationType= relationType;
		this.relationContent= relationContent;
	}
	public Long getQueryClientId() {
		return queryClientId;
	}
	public void setQueryClientId(Long queryClientId) {
		this.queryClientId = queryClientId;
	}
	
	
}