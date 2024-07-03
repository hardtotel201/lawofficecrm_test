<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 查询条件 -->
<div class="bg-query-head" style="font-size:20px;color:#00BFF4">&nbsp;查询条件</div>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
			    				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientName">客户名称</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientName" name="queryClientName" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientWeixin">客户微信</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientWeixin" name="queryClientWeixin" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientAddress">客户地址</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientAddress" name="queryClientAddress" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientPhone">客户电话</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientPhone" name="queryClientPhone" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientZipCode">客户邮政编码</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientZipCode" name="queryClientZipCode" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientFaxCode">客户传真</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientFaxCode" name="queryClientFaxCode" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientType">客户类型(个人/公司)</label>
				<div class="col-sm-2">
					<select type="text" id="queryClientType" name="queryClientType" class="input-text col-xs-12" >
						<option></option>
						<option>个人</option>
						<option>公司</option>
					</select>
				</div>
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientEmail">客户邮箱</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientEmail" name="queryClientEmail" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientAccount">客户账号</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientAccount" name="queryClientAccount" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientBank">客户开户行</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientBank" name="queryClientBank" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientLevel">客户等级</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientLevel" name="queryClientLevel" class="input-text col-xs-12" />
				</div>

				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientMemo">客户备注</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientMemo" name="queryClientMemo" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientState">客户状态</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientState" name="queryClientState" class="input-text col-xs-12" />
				</div>

				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryOpUserId">opUserId</label>
				<div class="col-sm-2">
					<input type="text" id="queryOpUserId" name="queryOpUserId" class="input-text col-xs-12" />
				</div>
			</div>
			<div>
				<label class="font-title col-sm-1 control-label no-padding-right"
						 for="queryRegisterTime">注册时间</label>
				<div class="col-sm-3 input-append date form_datetime">
					<input type="text" id="queryRegisterTime" name="queryRegisterTime"  type="text" />
					<span class="addon"> <i class="fa fa-clock-o"></i></span>
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right"
				   		for="queryOpTime">操作时间</label>
				<div class="col-sm-3 input-append date form_datetime">
					<input type="text" id="queryOpTime" name="queryOpTime" type="text" />
					<span class="addon"> <i class="fa fa-clock-o"></i></span>
				</div>
			</div>
			<div style="text-align: right; float: right;">
				<button id="btnQuery" class="btn btn-purple btn-sm">查询</button>
				<button id="btnAdd"   class="btn-op btn btn-sm btn-primary">新增</button>
				<button id="btnReturn" class="btn-op btn btn-sm btn-primary">返回</button>
			</div>

		</form>
	</div>
</div>
<div class="hr hr-18 dotted hr-double"></div>
<!-- 表格工具栏 -->
<div class="clearfix">
	<div class="pull-right tableTools-container"></div>
</div>
<!-- div.dataTables_borderWrap -->
<!-- 数据表格 -->
<div class="bg-table-header table-header">查询结果</div>
<table id="dynamic-table" class="table table-hover">
	<thead>
		<tr>
			<th class="bg-table-header center">
				<label class="pos-rel"> 
					<input type="checkbox" class="ace" />
					<span class="lbl"></span>
				</label>
			</th>
			<th class="bg-table-header">客户名称</th>
			<th class="bg-table-header">客户微信</th>
			<th class="bg-table-header">客户地址</th>
			<th class="bg-table-header">客户电话</th>
			<th class="bg-table-header">客户邮政编码</th>
			<th class="bg-table-header">客户传真</th>
			<th class="bg-table-header">客户类型(个人/公司)</th>
			<th class="bg-table-header">客户邮箱</th>
			<th class="bg-table-header">客户账号</th>
			<th class="bg-table-header">客户开户行</th>
			<th class="bg-table-header">客户等级</th>
			<th class="bg-table-header">注册时间</th>
			<th class="bg-table-header">客户备注</th>
			<th class="bg-table-header">客户状态</th>
			<th class="bg-table-header">操作时间</th>
			<th class="bg-table-header">操作用户标识</th>
			<th class="bg-table-header center">操作</th>
		</tr>
	</thead>
	<tfoot></tfoot>
</table>

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
				<div class="row">
					<form id="formInput" class="form-horizontal" role="form" >
						<input type="hidden" id="hiddenClientId" name="hiddenClientId" />
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientName">客户名称(*)</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientName" name="inputClientName"
									   class="dialog-control col-xs-10" required />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientWeixin">客户微信</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientWeixin" name="inputClientWeixin"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientAddress">客户地址</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientAddress" name="inputClientAddress"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientPhone">客户电话(*)</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientPhone" name="inputClientPhone"
									   class="dialog-control col-xs-10" required />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientZipCode">客户邮政编码</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientZipCode" name="inputClientZipCode"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientFaxCode">客户传真</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientFaxCode" name="inputClientFaxCode"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<div class="form-group">
							<%--							<label class="font-title col-sm-3 control-label no-padding-right"--%>
							<%--								for="inputClientType">客户类型(个人/公司)</label>--%>
							<%--							<div class="col-sm-9">--%>
							<%--								<input type="text" id="inputClientType" name="inputClientType"--%>
							<%--								       class="dialog-control col-xs-10" required />--%>
							<%--							</div>--%>

							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="selectClientType">客户类型(*)</label>
							<div class="col-sm-9">
								<select id="selectClientType" name="selectClientType"
										class="dialog-control col-xs-10" required>
									<option>个人</option>
									<option>公司</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientEmail">客户邮箱</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientEmail" name="inputClientEmail"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientAccount">客户账号</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientAccount" name="inputClientAccount"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientBank">客户开户行</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientBank" name="inputClientBank"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientLevel">客户等级</label>
							<div class="col-sm-9">
								<input type="number" id="inputClientLevel" name="inputClientLevel"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<%--						<div class="form-group">--%>
						<%--							<label class="font-title col-sm-3 control-label no-padding-right"--%>
						<%--								for="inputRegisterTime">注册时间</label>--%>
						<%--							<div class="col-sm-9">--%>
						<%--								<input type="text" id="inputRegisterTime" name="inputRegisterTime"  --%>
						<%--								       class="dialog-control col-xs-10" required />--%>
						<%--							</div>--%>
						<%--						</div>--%>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientMemo">客户备注</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientMemo" name="inputClientMemo"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-3 control-label no-padding-right"
								   for="inputClientState">客户状态</label>
							<div class="col-sm-9">
								<input type="text" id="inputClientState" name="inputClientState"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
						<%--						<div class="form-group">--%>
						<%--							<label class="font-title col-sm-3 control-label no-padding-right"--%>
						<%--								for="inputOpTime">操作时间</label>--%>
						<%--							<div class="col-sm-9">--%>
						<%--								<input type="text" id="inputOpTime" name="inputOpTime"  --%>
						<%--								       class="dialog-control col-xs-10" required />--%>
						<%--							</div>--%>
						<%--						</div>--%>
						<%--						<div class="form-group">--%>
						<%--							<label class="font-title col-sm-3 control-label no-padding-right"--%>
						<%--								for="inputOpUserId">操作用户标识</label>--%>
						<%--							<div class="col-sm-9">--%>
						<%--								<input type="text" id="inputOpUserId" name="inputOpUserId"  --%>
						<%--								       class="dialog-control col-xs-10" required />--%>
						<%--							</div>--%>
						<%--						</div>--%>
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
<script type="text/javascript">
    //1-声明表格对象
	var dataTableObj = null;
	
	$(function() {
		//1-设置DataTable列
		var columnSettings=[ {
			"data" : "clientId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		}, 
		{
			"data" : "clientName",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientWeixin",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientAddress",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientPhone",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientZipCode",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientFaxCode",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientType",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientEmail",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientAccount",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientBank",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientLevel",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "registerTime",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientMemo",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientState",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "opTime",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "opUserId",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		} ];
		
		//2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "clientId",
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
				"targets" :columnSettings.length-1,
				"data" : "clientId",
				"render" : function(
						data, type,
						row, meta) {
					var str = '<div class="bg-table-header center" >';
					str = str
							+ '<a href="javascript:editObject('
							+ meta.row
							+ ')" >修改</a>';
					str = str
							+ '<a href="javascript:deleteObject('
							+ data
							+ ')" >删除</a>';
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
				url : '../client/clientInfo/',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    //2-设置请求参数
					if($("#queryClientName").val()!="")
						req.clientName=$("#queryClientName").val();
					if($("#queryClientWeixin").val()!="")
						req.clientWeixin=$("#queryClientWeixin").val();
					if($("#queryClientAddress").val()!="")
						req.clientAddress=$("#queryClientAddress").val();
					if($("#queryClientPhone").val()!="")
						req.clientPhone=$("#queryClientPhone").val();
					if($("#queryClientZipCode").val()!="")
						req.clientZipCode=$("#queryClientZipCode").val();
					if($("#queryClientFaxCode").val()!="")
						req.clientFaxCode=$("#queryClientFaxCode").val();
					if($("#queryClientType").val()!="")
						req.clientType=$("#queryClientType").val();
					if($("#queryClientEmail").val()!="")
						req.clientEmail=$("#queryClientEmail").val();
					if($("#queryClientAccount").val()!="")
						req.clientAccount=$("#queryClientAccount").val();
					if($("#queryClientBank").val()!="")
						req.clientBank=$("#queryClientBank").val();
					if($("#queryClientLevel").val()!="")
						req.clientLevel=$("#queryClientLevel").val();
					if($("#queryRegisterTime").val()!="")
						req.registerTime=$("#queryRegisterTime").val();
					if($("#queryClientMemo").val()!="")
						req.clientMemo=$("#queryClientMemo").val();
					if($("#queryOpTime").val()!="")
						req.opTime=$("#queryOpTime").val();
					if($("#queryOpUserId").val()!="")
						req.opUserId=$("#queryOpUserId").val();

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
		
		
		//4-设置TableTools
		initTableTools(dataTableObj);
	    
		
		//5-查询按钮
		$('#btnQuery').on('click', function(e) {
		    dataTableObj.ajax.reload(null, true);
			e.preventDefault();
		});
		
		//6-新增信息
		$('#btnAdd').on('click', function(e) {
			$("#hiddenClientId").val(-1);
		    $("#inputClientName").val("");
		    $("#inputClientWeixin").val("");
		    $("#inputClientAddress").val("");
		    $("#inputClientPhone").val("");
		    $("#inputClientZipCode").val("");
		    $("#inputClientFaxCode").val("");
		    $("#selectClientType").val("");
		    $("#inputClientEmail").val("");
		    $("#inputClientAccount").val("");
		    $("#inputClientBank").val("");
		    $("#inputClientLevel").val("");
		    $("#inputRegisterTime").val("");
		    $("#inputClientMemo").val("");
		    $("#inputClientState").val("");
		    $("#inputOpTime").val("");
		    $("#inputOpUserId").val("");
			$('#divInputForm').modal();
			e.preventDefault();
		});
		
		
		//7-保存信息
		$('#btnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if($("#formInput").valid()==true){
				postObject();
				e.preventDefault(); //阻止默认事件Post
			}
		});
		

		//8-返回主页面
		$('#btnReturn').on('click', function(e) {
		    e.preventDefault(); //阻止默认事件Post
		    var strUrl="../client/clientBasicPage/all";
		    showPageContent(strUrl,"客户信息");
		});
		
		//9-设置窗口输入验证
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
		
	});
	

	//修改对象
	function editObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenClientId").val(row["clientId"]);
		$("#inputClientName").val(row["clientName"]);
		$("#inputClientWeixin").val(row["clientWeixin"]);
		$("#inputClientAddress").val(row["clientAddress"]);
		$("#inputClientPhone").val(row["clientPhone"]);
		$("#inputClientZipCode").val(row["clientZipCode"]);
		$("#inputClientFaxCode").val(row["clientFaxCode"]);
		$("#selectClientType").val(row["clientType"]);
		$("#inputClientEmail").val(row["clientEmail"]);
		$("#inputClientAccount").val(row["clientAccount"]);
		$("#inputClientBank").val(row["clientBank"]);
		$("#inputClientLevel").val(row["clientLevel"]);
		$("#inputRegisterTime").val(row["registerTime"]);
		$("#inputClientMemo").val(row["clientMemo"]);
		$("#inputClientState").val(row["clientState"]);
		$("#inputOpTime").val(row["opTime"]);
		$("#inputOpUserId").val(row["opUserId"]);
		$('#divInputForm').modal();
	}
	
	//提交对象
	function postObject() {
	    var obj=new Object();
		obj.clientId =$("#hiddenClientId").val();
		obj.clientName=$("#inputClientName").val();
		obj.clientWeixin=$("#inputClientWeixin").val();
		obj.clientAddress=$("#inputClientAddress").val();
		obj.clientPhone=$("#inputClientPhone").val();
		obj.clientZipCode=$("#inputClientZipCode").val();
		obj.clientFaxCode=$("#inputClientFaxCode").val();
		obj.clientType=$("#selectClientType").val();
		obj.clientEmail=$("#inputClientEmail").val();
		obj.clientAccount=$("#inputClientAccount").val();
		obj.clientBank=$("#inputClientBank").val();
		obj.clientLevel=$("#inputClientLevel").val();
		obj.registerTime=$("#inputRegisterTime").val();
		obj.clientMemo=$("#inputClientMemo").val();
		obj.clientState=$("#inputClientState").val();
		obj.opTime=$("#inputOpTime").val();
		obj.opUserId=$("#inputOpUserId").val();
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		var httpMethod="post";
		if($("#hiddenClientId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		$.ajax({
			type : httpMethod,
			url : "../client/clientInfo/",
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

	//删除对象
	function deleteObject(deleteId) {
		if(deleteId<=0)
			return;
		$.ajax({
			type : "delete",
			url : "../client/clientInfo/"+deleteId,
			error : function() {
			    showError('提交失败!');
			},
			success : function(data) {
				$('#divInputForm').modal("hide");
				if (data == null || data == "") {
					showSuccess('已经删除成功!');
					//重新加载数据
					 dataTableObj.ajax.reload(null, true);
				} else
					showError(data);
			}
		});
	}

	//初始化时间控件
	$('#queryRegisterTime').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});
	$('#queryOpTime').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});

</script>