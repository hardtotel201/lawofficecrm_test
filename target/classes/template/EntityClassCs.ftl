using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ${nameSpace}
{
    public class ${className} {
    <#list fields as e>
       public ${e.beanType}  ${e.beanName}{get;set;}
    </#list>
	
	public  ${className}() {
	}
	
	public  ${className}(
	  <#list fields as e>${e.beanType} ${e.beanName}<#if e_has_next>,</#if></#list>){
		<#list fields as e>
		this.${e.beanName}= ${e.beanName};
		</#list>
	}
	
	 public override bool Equals(object obj)
     {
         if (obj is ${className} == false)
            return false;
         ${className} other = (${className})obj;
         return this.${indexName}.Equals(other.${indexName});
     }
}
}

