(:- ${axiomHead}
	<#if axiomBranches?exists> 
	<#list axiomBranches as item>
	    ${item.branchName}
	    ( ${item.precondition} )
	</#list>
	</#if>
)