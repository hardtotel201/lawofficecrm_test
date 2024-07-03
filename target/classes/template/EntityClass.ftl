package ${packageName};

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

@Alias(value = "${className}")
public class ${className} extends ClientRequest{
    <#list fields as e>
	private ${e.beanType}	${e.beanName};		//${e.beanTitle}
    </#list>
    //下面是视图字段定义
    
    //下面是查询字段定义
    
    <#list fields as e>
	public ${e.beanType} ${e.getterName}() {
		return ${e.beanName};
	}
	public void ${e.setterName}(${e.beanType} ${e.beanName}) {
		this.${e.beanName} = ${e.beanName};
	}
    </#list>
	
	public  ${className}() {
		super();
	}
	
	public  ${className}(
	    <#list fields as e>${e.beanType} ${e.beanName}<#if e_has_next>,</#if></#list>){
		super();
		<#list fields as e>
		this.${e.beanName}= ${e.beanName};
		</#list>
	}
}