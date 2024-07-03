(:method ${methodHead}
	<#if methodBranches?exists> 
	<#list methodBranches as item>
	    ${item.branchName}
	    ( ${item.precondition} )
	    ( ${item.taskList} )
	</#list>
	</#if>
)