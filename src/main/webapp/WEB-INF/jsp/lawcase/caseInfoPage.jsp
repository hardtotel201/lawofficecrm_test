<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.alibaba.excel.EasyExcel"%>

<!-- 查询条件 -->
<div class="bg-query-head" style="font-size:16px;color:#00BFF4">&nbsp;查询条件</div>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
				<%--查询条件：律师标识、客户标识、案件编号、案件名称、案件类型--%>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientName">客户名称</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientName" name="queryClientName" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryCaseCode">案件编号</label>
				<div class="col-sm-2">
					<input type="text" id="queryCaseCode" name="queryCaseCode" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right"
				       for="queryCaseName">案件名称</label>
				<div class="col-sm-2">
					<input type="text" id="queryCaseName" name="queryCaseName" class="input-text col-xs-12" />
				</div>
			</div>
		</form>
		<div style="text-align: right; float: right;">
			<button id="btnQuery" class="btn btn-purple btn-sm">查询</button>
			<button id="btnAdd"   class="btn-op btn btn-sm btn-primary">新增</button>
			<button id="btnExcel" class="btn btn-pink btn-sm">导出Excel</button>
<%--			<a href="../lawcase/caseInfoDownload" rel="noopener noreferrer">--%>
<%--				<button id="btnExcel" class="btn btn-pink btn-sm">导出Excel</button>--%>
<%--			</a>--%>
		</div>
	</div>
</div>
<div class="hr hr-18 dotted hr-double"></div>
<!-- 表格工具栏 -->
<div class="clearfix">
	<div class="pull-right tableTools-container"></div>
</div>
<!-- div.dataTables_borderWrap -->
<!-- 数据表格 -->
<div class="bg-table-header table-header">查询结果
</div>
<table id="dynamic-table" class="table table-hover">
	<thead>
		<tr>
			<th class="bg-table-header center">
				<label class="pos-rel">
					<input type="checkbox" class="ace" id="selectAll" />
					<span class="lbl"></span>
				</label>
			</th>
			<th class="bg-table-header">客户名称</th>
			<th class="bg-table-header">案件编号</th>
			<th class="bg-table-header">案件名称</th>
			<th class="bg-table-header">案件类型</th>
			<th class="bg-table-header">程序员</th>
			<th class="bg-table-header">律师</th>
			<th class="bg-table-header">案件状态</th>
			<th class="bg-table-header">操作时间</th>
			<th class="bg-table-header">操作人</th>
			<th class="bg-table-header">制定方案</th>
			<th class="bg-table-header">确定价格</th>
			<th class="bg-table-header">项目确定</th>
			<th class="bg-table-header center">操作</th>
		</tr>
	</thead>
	<tfoot></tfoot>
</table>

<%--继承信息录入页面--%>
<div id="divInputForm" class="modal" tabindex="-1">
	<div class="modal-dialog" style="width:1000px;height:750px">
		<div class="modal-content">
			<div class="modal-header" style="border:none" >
				<button type="button" class="close" data-dismiss="modal">
				   <span style="color:#93ADE2">&times;</span>&nbsp;&nbsp;&nbsp;&nbsp;
				</button>
				<h3 class="blue bigger" style="color:#DFE9FE">&nbsp;&nbsp;请输入</h3>
			</div>
			<div class="modal-body">
				<div class="row ">
					<form id="formInput" class="form-horizontal" role="form" >
						<input type="hidden" id="hiddenCaseId" name="hiddenCaseId" />
						<div class="form-group">
								<label class="dialog-label col-sm-2 control-label no-padding-right"
									   for="inputClientId">客户名称(*)</label>
								<div class="col-sm-4">
									<select  id="inputClientId" name="inputClientId" class="chosen-select form-control" data-placeholder="选择客户..." required >
										<option value=""></option>
										<c:forEach var="item" items="${clientInfos}">
											<option value="${item.clientId}">${item.clientName}</option>
										</c:forEach>
									</select>
								</div>
								<label class="font-title col-sm-2 control-label no-padding-right"
									   for="inputOtherClient">其他当事人</label>
								<div class="col-sm-4">
									<input type="text" id="inputOtherClient" name="inputOtherClient"
										   class="input-text col-xs-7 dialog-control" />
								</div>
						</div>
						<div class="form-group">
								<label class="font-title col-sm-2 control-label no-padding-right"
									   for="inputCaseCode">案件编号(*)</label>
								<div class="col-sm-4">
									<input type="text" id="inputCaseCode" name="inputCaseCode"
										   class="input-text col-xs-7 dialog-control" required />
								</div>
								<label class="font-title col-sm-2 control-label no-padding-right"
									   for="inputCaseName">案件名称(*)</label>
								<div class="col-sm-4">
									<input type="text" id="inputCaseName" name="inputCaseName"
										   class="input-text col-xs-10 dialog-control" required />
								</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputCaseProgram">案件程序</label>
							<div class="col-sm-4">
								<input type="text" id="inputCaseProgram" name="inputCaseProgram"
									   class="input-text col-xs-7 dialog-control" />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputCasePhrase">案件阶段</label>
							<div class="col-sm-4">
								<input type="text" id="inputCasePhrase" name="inputCasePhrase"
									   class="input-text col-xs-10 dialog-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputPredealMatters">待办事项</label>
							<div class="col-sm-4">
								<input type="text" id="inputPredealMatters" name="inputPredealMatters"
									   class="input-text col-xs-7 dialog-control" />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputDealUnit">受理单位</label>
							<div class="col-sm-4">
								<input type="text" id="inputDealUnit" name="inputDealUnit"
									   class="input-text col-xs-10 dialog-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputCaseType">案件类型(*)</label>
							<div class="col-sm-4">
								<select  id="inputCaseType" name="inputCaseType" class="col-xs-7" required >
									<option value=""></option>
									<c:forEach var="item" items="${caseTypes}">
										<option>${item}</option>
									</c:forEach>
							    </select>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputCaseSource">案件来源</label>
							<div class="col-sm-4">
								<input type="text" id="inputCaseSource" name="inputCaseSource"
									   class="input-text col-xs-7 dialog-control" <%--required--%> />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputAcceptTime">收案时间(*)</label>
							<div class="col-sm-4">
								<input type="text" id="inputAcceptTime" name="inputAcceptTime"
									   class="input-text dialog-control" required />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputDealJudge">承办人员</label>
							<div class="col-sm-4">
								<input type="text" id="inputDealJudge" name="inputDealJudge"
									   class="input-text col-xs-7 dialog-control" <%--required--%> />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputCaseConent">委托内容(*)</label>
							<div class="col-sm-8">
							    <textarea id="inputCaseConent" name="inputCaseConent" class="form-control col-xs-10" rows="3"></textarea>
							</div>
						</div>
						
					</form>
				</div>
			</div>
			<div class="footer-dialog modal-footer" style="">
				<button id="btnCancel" class="btn-op btn btn-sm btn-primary" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i> 取消
				</button>
				<button id="btnOk" class="btn-op btn btn-sm btn-primary">
					<i class="ace-icon fa fa-check"></i> 保存
				</button>
			</div>
		</div>
	</div>
</div>

<%--制定方案录入页面--%>
<div id="divPlanForm" class="modal" tabindex="-1">
	<div class="modal-dialog" style="width:1000px;height:750px">
		<div class="modal-content">
			<div class="modal-header" style="border:none" >
				<button type="button" class="close" data-dismiss="modal">
					<span style="color:#93ADE2">&times;</span>&nbsp;&nbsp;&nbsp;&nbsp;
				</button>
				<h3 class="blue bigger" style="color:#DFE9FE">&nbsp;&nbsp;制定方案</h3>
			</div>
			<div class="modal-body">
				<div class="row ">
					<form id="formPlan" class="form-horizontal" role="form" >
						<input type="hidden" id="hiddenCaseId" name="hiddenCaseId" />
						<div class="form-group">
							<label class="dialog-label col-sm-2 control-label no-padding-right"
								   for="inputUserId">选择程序员(*)</label>
							<div class="col-sm-4">
								<select  id="inputUserId" name="inputUserId" class="col-xs-7 form-control" required >
								    <option value=""></option>
									<c:forEach var="item" items="${userInfos}">
										<option value="${item.userId}">${item.userName}</option>
									</c:forEach>
								</select>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputLawyerId">选择律师(*)</label>
							<div class="col-sm-4">
								<select  id="inputLawyerId" name="inputLawyerId" class="chosen-select form-control" data-placeholder="选择律师..." required >
								    <option value=""></option>
									<c:forEach var="item" items="${lawyerInfos}">
										<option value="${item.lawyerId}">${item.lawyerName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputContractCode">合同编号(*)</label>
							<div class="col-sm-4">
								<input type="text" id="inputContractCode" name="inputContractCode"
									   class="dialog-control col-xs-7" required />
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="footer-dialog modal-footer" style="">
				<button id="btnCancel" class="btn-op btn btn-sm btn-primary" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i> 取消
				</button>
				<button id="PlanbtnOk" class="btn-op btn btn-sm btn-primary">
					<i class="ace-icon fa fa-check"></i> 保存
				</button>
			</div>
		</div>
	</div>
</div>

<%--确定价格--%>
<div id="divPriceForm" class="modal" tabindex="-1">
	<div class="modal-dialog" style="width:1000px;height:750px">
		<div class="modal-content">
			<div class="modal-header" style="border:none" >
				<button type="button" class="close" data-dismiss="modal">
					<span style="color:#93ADE2">&times;</span>&nbsp;&nbsp;&nbsp;&nbsp;
				</button>
				<h3 class="blue bigger" style="color:#DFE9FE">&nbsp;&nbsp;确定价格</h3>
			</div>
			<div class="modal-body">
				<div class="row ">
					<form id="formPrice" class="form-horizontal" role="form" >
						<input type="hidden" id="hiddenCaseId" name="hiddenCaseId" />
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputCasePrice">案件标的(*)</label>
							<div class="col-sm-4">
								<input type="text" id="inputCasePrice" name="inputCasePrice"
									   class="dialog-control col-xs-7" required />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputLawyerFee">律师费(*)</label>
							<div class="col-sm-4">
								<input type="text" id="inputLawyerFee" name="inputLawyerFee"
									   class="dialog-control col-xs-7" required />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputFeeType">收费方式</label>
							<div class="col-sm-4">
								<input type="text" id="inputFeeType" name="inputFeeType"
									   class="dialog-control col-xs-7" <%--required--%> />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputPayType">支付方式</label>
							<div class="col-sm-4">
								<input type="text" id="inputPayType" name="inputPayType"
									   class="dialog-control col-xs-7" <%--required--%> />
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="footer-dialog modal-footer" style="">
				<button id="btnCancel" class="btn-op btn btn-sm btn-primary" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i> 取消
				</button>
				<button id="PricebtnOk" class="btn-op btn btn-sm btn-primary">
					<i class="ace-icon fa fa-check"></i> 保存
				</button>
			</div>
		</div>
	</div>
</div>

<%--项目确定--%>
<div id="divEnsureForm" class="modal" tabindex="-1">
	<div class="modal-dialog" style="width:1000px;height:750px">
		<div class="modal-content">
			<div class="modal-header" style="border:none" >
				<button type="button" class="close" data-dismiss="modal">
					<span style="color:#93ADE2">&times;</span>&nbsp;&nbsp;&nbsp;&nbsp;
				</button>
				<h3 class="blue bigger" style="color:#DFE9FE">&nbsp;&nbsp;项目确认</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<form id="formEnsure" class="form-horizontal" role="form" >
						<input type="hidden" id="hiddenCaseId" name="hiddenCaseId" />
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelUserName">程序员(*)</label>
							<div class="col-sm-4">
								<label id="labelUserName" class="font-title control-label no-padding-left" ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelLawyerName">律师(*)</label>
							<div class="col-sm-4">
								<label id="labelLawyerName" class="font-title control-label no-padding-left" ></label>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelClientName">客户名称(*)</label>
							<div class="col-sm-4">
								<label id="labelClientName" class="font-title control-label no-padding-left" ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelOtherClient">其他当事人</label>
							<div class="col-sm-4">
								<label id="labelOtherClient" class="font-title control-label no-padding-left" ></label>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelCaseCode">案件编号(*)</label>
							<div class="col-sm-4">
								<label id="labelCaseCode" class="font-title control-label no-padding-left" ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelCaseName">案件名称</label>
							<div class="col-sm-4">
								<label id="labelCaseName" class="font-title control-label no-padding-left" ></label>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelCaseProgram">案件程序</label>
							<div class="col-sm-4">
								<label id="labelCaseProgram" class="font-title control-label no-padding-left" ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelCasePhrase">案件阶段</label>
							<div class="col-sm-4">
								<label id="labelCasePhrase" class="font-title control-label no-padding-left" ></label>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelPredealMatters">待办事项</label>
							<div class="col-sm-4">
								<label id="labelPredealMatters" class="font-title control-label no-padding-left" ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelDealUnit">受理单位</label>
							<div class="col-sm-4">
								<label id="labelDealUnit" class="font-title control-label no-padding-left" ></label>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelDealJudge">承办人员</label>
							<div class="col-sm-4">
								<label id="labelDealJudge" class="font-title control-label no-padding-left" ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelCaseConent">委托内容(*)</label>
							<div class="col-sm-4">
								<label id="labelCaseConent" class="font-title control-label no-padding-left" ></label>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelCaseType" >案件类型</label>
							<div class="col-sm-4">
								<label id="labelCaseType" class="font-title control-label no-padding-left" ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelCaseSource" >案件来源</label>
							<div class="col-sm-4">
								<label id="labelCaseSource" class="font-title control-label no-padding-left"></label>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelCasePrice">案件标的(*)</label>
							<div class="col-sm-4">
								<label id="labelCasePrice" class="font-title control-label no-padding-left" ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelLawyerFee">律师费(*)</label>
							<div class="col-sm-4">
								<label id="labelLawyerFee" class="font-title control-label no-padding-left" ></label>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelFeeType">收费方式</label>
							<div class="col-sm-4">
								<label id="labelFeeType" class="font-title control-label no-padding-left" ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelPayType">支付方式</label>
							<div class="col-sm-4">
								<label id="labelPayType" class="font-title control-label no-padding-left" ></label>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelAcceptTime">收案时间(*)</label>
							<div class="col-sm-4">
								<label id="labelAcceptTime" class="font-title control-label no-padding-left"  ></label>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelContractCode">合同编号(*)</label>
							<div class="col-sm-4">
								<label id="labelContractCode" class="font-title control-label no-padding-left"  ></label>
							</div>
						</div>
						<div class="from_group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="labelCaseState">案件状态(*)</label>
							<div class="col-sm-4">
								<label id="labelCaseState" class="font-title control-label no-padding-left" ></label>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="footer-dialog modal-footer" style="">
				<button id="btnCancel" class="btn-op btn btn-sm btn-primary" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i> 取消
				</button>
				<button id="EnsurebtnOk" class="btn-op btn btn-sm btn-primary">
					<i class="ace-icon fa fa-check"></i> 保存
				</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
    //1-声明表格对象
	var dataTableObj = null;

	
	$(function() {
		//1-设置DataTable列
		var columnSettings=[ {
			"data" : "caseId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		},
		{
			"data" : "clientName",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "caseCode",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "caseName",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "caseType",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "userName",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "lawyerName",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "caseState",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "opTime",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "opUserName",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "caseId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		},
		{
			"data" : "caseId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		},
		{
			"data" : "caseId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		},
		{
			"data" : "caseId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		} ];
		
		//2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "caseId",
				"render" : function(
						data, type,
						row, meta) {
		
					var str = '<div class="bg-table-header center" > <label class="pos-rel">';
					str = str
							+ '<input type="checkbox" class="ace" value="'+data+'" />';
					str = str
							+ '<span class="lbl"></span></label> </div>';
					return str;
				}
			},
			{
				"targets" :columnSettings.length-4,
				"data" : "caseId",
				"render" : function(
						data, type,
						row, meta) {
					var str = '<div class="bg-table-header center" >';
					str = str
							+ '<a href="javascript:PlanObject('
							+ meta.row
							+ ')" >制定方案</a>';
					str = str
							+ '</div>'
					return str;
				}
			},
			{
				"targets" :columnSettings.length-3,
				"data" : "caseId",
				"render" : function(
						data, type,
						row, meta) {
					var str = '<div class="bg-table-header center" >';
					str = str
							+ '<a href="javascript:PriceObject('
							+ meta.row
							+ ')" >确定价格</a>';
					str = str
							+ '</div>'
					return str;
				}
			},
			{
				"targets" :columnSettings.length-2,
				"data" : "caseId",
				"render" : function(
						data, type,
						row, meta) {
					var str = '<div class="bg-table-header center" >';
					str = str
							+ '<a href="javascript:EnsureObject('
							+ meta.row
							+ ')" >项目确定</a>';
					str = str
							+ '</div>'
					return str;
				}
			},
			{
				"targets" :columnSettings.length-1,
				"data" : "caseId",
				"render" : function(
						data, type,
						row, meta) {
					var str = '<div class="bg-table-header center" >';
					str = str
							+ '<a href="javascript:editObject('
							+ meta.row
							+ ')" >基础信息修改 </a>';
					str = str
							+ '<a href="javascript:deleteObject('
							+ data
							+ ')" > 删除</a>';
					str = str
							+ '</div>'
					return str;
				}
			}];
		//3-初始化数据表格
		dataTableObj = $('#dynamic-table').DataTable({
			"serverSide" : true, //服务器处理：过滤、分页、排序
			"processing" : true, //是否显示处理状态
			"ajax" : {
				url : '../lawcase/caseInfo/',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    //2-设置请求参数
					if($("#queryClientName").val()!="")
						req.clientName=$("#queryClientName").val();
					if($("#queryCaseCode").val()!="")
						req.caseCode=$("#queryCaseCode").val();
					if($("#queryCaseName").val()!="")
						req.caseName=$("#queryCaseName").val();
				},
			//"dataSrc" : function(json) {
			//	return json.data;
			//}
			}, //获取数据的处理函数 
			"searching" : false,//搜索功能
			"bSort" : true, //排序功能
			"aaSorting" : [],
			"columns" : columnSettings,
			"columnDefs" : columnDefs,
			"bFilter" : false, //过滤功能
			"bPaginate" : true, //翻页功能
			"pageLength": 10,
			"sPaginationType" : "full_numbers", //分页的格式
			"bLengthChange" : false, //改变每页显示数据数量
			"bInfo" : true, //页脚信息
			"bAutoWidth" : true,//自动宽度
			"language" : dataTableLanguage,
			"select": {
				style: 'multi'
			},
		});

		//2-初始化jquery chosen和bootstrap multiselect控件
		initMultiSelect();
		//初始化JQuery Chosen组件
		initJQueryChosen();
		
		
		//4-设置TableTools
		initTableTools(dataTableObj);

		//5-设置窗口验证
		$("#formInput").validate();
		
		//5-查询按钮
		$('#btnQuery').on('click', function(e) {
		    dataTableObj.ajax.reload(null, true);
			e.preventDefault();
		});
        //6-导出excel按钮
		function downloadExcel (blobPart, filename) {
			const blob = new Blob([blobPart], { type: 'application/vnd.ms-excel' });
			console.log('downloadExcel', blob.size);
			// 创建一个超链接，将文件流赋进去，然后实现这个超链接的单击事件
			const elink = document.createElement('a');
			elink.download = decodeURIComponent(filename);
			elink.style.display = 'none';
			elink.href = URL.createObjectURL(blob);
			document.body.appendChild(elink);
			elink.click();
			URL.revokeObjectURL(elink.href);// 释放URL 对象
			document.body.removeChild(elink);
		};

		$("#btnExcel").click(function(){
			var excelDownLoad = new Object();
			excelDownLoad.clientName=$("#queryClientName").val();
			excelDownLoad.caseCode=$("#queryCaseCode").val();
			excelDownLoad.caseName=$("#queryCaseName").val();
			var excelData = JSON.stringify(excelDownLoad)
			var xhr = new XMLHttpRequest();
			var url = '../lawcase/caseInfoDownload';
			xhr.open('POST', url, true);
			xhr.setRequestHeader('content-type','application/json');
			xhr.send(excelData);
			xhr.responseType = "blob";
			xhr.onload = () => {
				console.log(xhr);
				console.log(xhr.response);
				const blob = xhr.response;
				const blobUrl = URL.createObjectURL(blob);
				const a = document.createElement('a');
				a.style.display = 'none';
				a.download="收案登记信息表.xls"
				a.href = blobUrl;
				a.target = '_blank';
				a.click();
			}



		}); //ajax结尾

		//6-新增信息
		$('#btnAdd').on('click', function(e) {
			$("#hiddenCaseId").val(-1);
			$("#inputClientId").val("");
			$("#inputClientId").trigger("chosen:updated");
		    $("#inputCaseCode").val("");
		    $("#inputCaseName").val("");
		    $("#inputCaseType").val("");
		    $("#inputCaseConent").val("");
		    $("#inputCaseSource").val("");
		    $("#inputAcceptTime").val("");
			$("#inputCaseProgram").val("");
			$("#inputOtherClient").val("");
			$("#inputCasePhrase").val("");
			$("#inputPredealMatters").val("");
			$("#inputDealUnit").val("");
			$("#inputDealJudge").val("");
			$('#divInputForm').modal();
			e.preventDefault();
		});
		
		//7-保存信息
		$('#btnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if($("#formInput").valid()==true)
			{
				if($("#inputClientId").val()==null || $("#inputClientId").val()==""){
					showError("请选择客户!");
					$("#inputClientId").focus();
					return;
				}
				console.log("inputClientId:"+$("#inputClientId").val());
				postObject();
				e.preventDefault(); //阻止默认事件Post
			}
		});
		
		// 制定方案保存信息
		$('#PlanbtnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if($("#formPlan").valid()==true)
			{
				if($("#inputUserId").val()==null || $("#inputUserId").val()==""){
					showError("请选择程序员!");
					$("#inputUserId").focus();
					return;
				}
				console.log("inputUserId:"+$("#inputUserId").val());
				postPlan();
				e.preventDefault(); //阻止默认事件Post
			}
		});
		// 确定价格保存信息
		$('#PricebtnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if($("#formPrice").valid()==true)
			{
				postPrice();
				e.preventDefault(); //阻止默认事件Post
			}
		});
		//项目确定保存信息
		$('#EnsurebtnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if($("#formEnsure").valid()==true)
			{
				postEnsure();
				e.preventDefault(); //阻止默认事件Post
			}
		});
		// 项目修改保存信息
		$('#ChangebtnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if($("#formChange").valid()==true)
			{
				postObject();
				e.preventDefault(); //阻止默认事件Post
			}
		});

		$("#formInput").validate({
		    rules:{

		    },
		    messages:{
		    
		    },
		    errorElement: "em", 
		    success:function(em){
				$(em).text("").addClass("glyphicon glyphicon-ok-circle success");
		    },
		    showErrors:function (errorMap, errorList){
				for (var element in errorMap) {
		            $("#" + element).next("em").removeClass(); //去除success样式和字体图标,不去除会跟错误信息一起出现
		        }
		        this.defaultShowErrors();
		    }
		});

		$("#formPlan").validate({
			rules:{

			},
			messages:{

			},
			errorElement: "em",
			success:function(em){
				$(em).text("").addClass("glyphicon glyphicon-ok-circle success");
			},
			showErrors:function (errorMap, errorList){
				for (var element in errorMap) {
					$("#" + element).next("em").removeClass(); //去除success样式和字体图标,不去除会跟错误信息一起出现
				}
				this.defaultShowErrors();
			}
		});
	});

	//重新加载数据
	function refreshData() {
		console.log("caseInfo.refreshData");
		dataTableObj.ajax.reload(null, true);
	}


	// 制定方案
	function PlanObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenCaseId").val(     row["caseId"]);
		$("#inputUserId").val(      row["userId"]);
		$("#inputLawyerId").val(    row["lawyerId"]);
		$("#inputLawyerId").trigger("chosen:updated");
		$("#inputContractCode").val(row["contractCode"]);
		console.log("----------PlanObject----------------------");
		$('#divPlanForm').modal();
	}

	// 确定价格
	function PriceObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenCaseId").val(  row["caseId"]);
		$("#inputCasePrice").val(row["casePrice"]);
		$("#inputLawyerFee").val(row["lawyerFee"]);
		$("#inputFeeType").val(  row["feeType"]);
		$("#inputPayType").val(  row["payType"]);
		$('#divPriceForm').modal();
	}

	// 项目确定
	function EnsureObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenCaseId").val(row["caseId"]);
		$("#labelUserName").html(row["userName"]);
		$("#labelLawyerName").html(row["lawyerName"]);
		$("#labelClientName").html(row["clientName"]);
		$("#labelCaseCode").html(row["caseCode"]);
		$("#labelCaseName").html(row["caseName"]);
		$("#labelCaseType").html(row["caseType"]);
		$("#labelCaseConent").html(row["caseConent"]);
		$("#labelCaseSource").html(row["caseSource"]);
		$("#labelCasePrice").html(row["casePrice"]);
		$("#labelLawyerFee").html(row["lawyerFee"]);
		$("#labelFeeType").html(row["feeType"]);
		$("#labelPayType").html(row["payType"]);
		$("#labelAcceptTime").html(row["acceptTime"]);
		$("#labelCaseState").html(row["caseState"]);
		$("#labelContractCode").html(row["contractCode"]);

		$("#labelCaseProgram").html(row["caseProgram"]);
		$("#labelCasePhrase ").html(row["casePhrase"]);
		$("#labelOtherClient ").html(row["otherClient"]);
		$("#labelPredealMatters ").html(row["predealMatters"]);
		$("#labelDealUnit").html(row["dealUnit"]);
		$("#labelDealJudge ").html(row["dealJudge"]);

		$('#divEnsureForm').modal();
	}

	//修改对象
	function editObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenCaseId").val( row["caseId"]);
		$("#inputClientId").val(row["clientId"]);
		$("#inputClientId").trigger("chosen:updated");
		$("#inputCaseCode").val(row["caseCode"]);
		$("#inputCaseName").val(row["caseName"]);
		$("#inputCaseType").val(row["caseType"]);
		$("#inputCaseConent").val(row["caseConent"]);
		$("#inputCaseSource").val(row["caseSource"]);	
		$("#inputCaseProgram").val(row["caseProgram"]);
		$("#inputOtherClient").val(row["otherClient"]);
		$("#inputCasePhrase").val(row["casePhrase"]);
		$("#inputPredealMatters").val(row["predealMatters"]);
		$("#inputDealUnit").val(row["dealUnit"]);
		$("#inputDealJudge").val(row["dealJudge"]);
		$("#inputAcceptTime").val(row["acceptTime"]);
		$('#divInputForm').modal();
		
	}
	
	//提交基础信息
	function postObject() {
	    var obj=new Object();
		obj.caseId =$("#hiddenCaseId").val();
		obj.clientId=$("#inputClientId").val();
		obj.caseCode=$("#inputCaseCode").val();
		obj.caseName=$("#inputCaseName").val();
		obj.caseType=$("#inputCaseType").val();
		obj.caseConent=$("#inputCaseConent").val();
		obj.caseSource=$("#inputCaseSource").val();
		obj.acceptTime=$("#inputAcceptTime").val();
		obj.caseProgram=$("#inputCaseProgram").val();
		obj.otherClient=$("#inputOtherClient").val();
		obj.casePhrase=$("#inputCasePhrase").val();
		obj.predealMatters=$("#inputPredealMatters").val();
		obj.dealUnit=$("#inputDealUnit").val();
		obj.dealJudge=$("#inputDealJudge").val();
		var jsonStr = JSON.stringify(obj);
		var httpMethod="post";
		if($("#hiddenCaseId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		console.log("postObject() "+httpMethod+":"+jsonStr);
		$.ajax({
			type : httpMethod,
			url : "../lawcase/caseInfo/",
			data : jsonStr,
			contentType : "application/json; charset=utf-8",
			async : false, //默认为true 异步   
			error : function() {
				alert('提交失败!');
			},
			success : function(data) {
				$('#divInputForm').modal("hide");
				if (data == null || data == "") {
					$.gritter.add({
						title : '成功',
						text : '提交已经成功!',
						//sticky: false,  
						time : 2000,
						//speed:500,  
						position : 'bottom-right',
						class_name : 'gritter-sucess'
					});
					//重新加载数据
					 dataTableObj.ajax.reload(null, true);
				} else
					$.gritter.add({
						title : '失败',
						text : data,
						//sticky: false,  
						time : 2000,
						//speed:500,  
						position : 'bottom-right',
						class_name : 'gritter-error'
					});
			}
		});
	}
	
	//提交方案信息
	function postPlan(){
		var obj=new Object();
		obj.caseId      =$("#hiddenCaseId").val();
		obj.userId      =$("#inputUserId").val();
		obj.lawyerId    =$("#inputLawyerId").val();
		obj.contractCode=$("#inputContractCode").val();
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		$.ajax({
			type : "put",
			url : "../lawcase/casePlan/",
			data : jsonStr,
			contentType : "application/json; charset=utf-8",
			async : false, //默认为true 异步   
			error : function() {
				alert('提交失败!');
			},
			success : function(data) {
				$('#divPlanForm').modal("hide");
				if (data == null || data == "") {
					$.gritter.add({
						title : '成功',
						text : '提交已经成功!',
						//sticky: false,  
						time : 2000,
						//speed:500,  
						position : 'bottom-right',
						class_name : 'gritter-sucess'
					});
					//重新加载数据
					 dataTableObj.ajax.reload(null, true);
				} else
					$.gritter.add({
						title : '失败',
						text : data,
						//sticky: false,  
						time : 2000,
						//speed:500,  
						position : 'bottom-right',
						class_name : 'gritter-error'
					});
			}
		});
	}
	
	//提交价格信息
	function postPrice(){
		var obj=new Object();
		obj.caseId =$("#hiddenCaseId").val();
		obj.casePrice=$("#inputCasePrice").val();
		obj.lawyerFee=$("#inputLawyerFee").val();
		obj.feeType=$("#inputFeeType").val();
		obj.payType=$("#inputPayType").val();
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		$.ajax({
			type : "put",
			url : "../lawcase/casePrice/",
			data : jsonStr,
			contentType : "application/json; charset=utf-8",
			async : false, //默认为true 异步   
			error : function() {
				alert('提交失败!');
			},
			success : function(data) {
				$('#divPriceForm').modal("hide");
				if (data == null || data == "") {
					$.gritter.add({
						title : '成功',
						text : '提交已经成功!',
						//sticky: false,  
						time : 2000,
						//speed:500,  
						position : 'bottom-right',
						class_name : 'gritter-sucess'
					});
					//重新加载数据
					 dataTableObj.ajax.reload(null, true);
				} else
					$.gritter.add({
						title : '失败',
						text : data,
						//sticky: false,  
						time : 2000,
						//speed:500,  
						position : 'bottom-right',
						class_name : 'gritter-error'
					});
			}
		});
	}
	
	//提交项目确定信息
	function postEnsure(){
		var obj=new Object();
		obj.caseId      =$("#hiddenCaseId").val();
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		$.ajax({
			type : "put",
			url : "../lawcase/caseEnsure/",
			data : jsonStr,
			contentType : "application/json; charset=utf-8",
			async : false, //默认为true 异步   
			error : function() {
				alert('提交失败!');
			},
			success : function(data) {
				$('#divEnsureForm').modal("hide");
				if (data == null || data == "") {
					$.gritter.add({
						title : '成功',
						text : '提交已经成功!',
						//sticky: false,  
						time : 2000,
						//speed:500,  
						position : 'bottom-right',
						class_name : 'gritter-sucess'
					});
					//重新加载数据
					 dataTableObj.ajax.reload(null, true);
				} else
					$.gritter.add({
						title : '失败',
						text : data,
						//sticky: false,  
						time : 2000,
						//speed:500,  
						position : 'bottom-right',
						class_name : 'gritter-error'
					});
			}
		});
	}

	//删除对象
	function deleteObject(deleteId) {
		if(deleteId<=0)
			return;
		$.ajax({
			type : "delete",
			url : "../lawcase/caseInfo/"+deleteId,
			error : function() {
			    showError('提交失败!');
			},
			success : function(data) {
				const r = confirm("确定删除此信息？ ");
				$('#divInputForm').modal("hide");
				if (r) {
					if (data == null || data == "") {
						showSuccess('已经删除成功!');
						//重新加载数据
						dataTableObj.ajax.reload(null, true);
					} else
						showError('该客户仍涉及业务,无法删除');
				}
			}
		});
	}
	
	$('#inputAcceptTime').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});
</script>