<#if domainMemo?exists>;${domainMemo} </#if>

(defdomain ${domainName} 
	(
	 ; ---------------------- Operators ----------------------
	 <#list operators as item>
	 	<#if item.modelMemo?exists>
		${item.modelMemo}
	 	</#if>
	 </#list>
	 ; ---------------------- Methods ----------------------
	 <#list methods as item>
	 	<#if item.modelMemo?exists>
	 	${item.modelMemo}
	 	</#if>
	 </#list>
	 ; ---------------------- Axioms ----------------------
	 <#list axioms as item>
	 	<#if item.modelMemo?exists>
	 	${item.modelMemo}
	 	</#if>
	 </#list>
	)
)