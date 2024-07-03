<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right" for="selectQueryParentModuleId">父模块</label>
				<div class="col-sm-2">
					<select class="form-control col-xs-6"
						id="selectQueryParentModuleId" name="selectQueryParentModuleId">
						<option value=""></option>
						<c:forEach var="item" items="${parentModules}">
							<option value="${item.moduleId}">${item.moduleTitle}</option>
						</c:forEach>
					</select>
				</div>
				<label class="col-sm-2 control-label no-padding-right"
					for="queryModuleTitle">模块标题</label>
				<div class="col-sm-4">
					<input type="text" id="queryModuleTitle"
						name="queryModuleTitle" class="input-text col-xs-6" />
				</div>
				<div style="text-align: right; float: right;">
					<button id="btnQuery" class="btn btn-purple btn-sm">查询</button>
					<button id="btnAdd" class="btn btn-primary btn-sm">新增</button>
				</div>
			</div>
			
		</form>
	</div>
</div>
<div class="hr hr-18 dotted hr-double"></div>
<!-- 表格工具栏 -->
<div class="clearfix">
	<div class="pull-right tableTools-container"></div>
</div>
<div class="table-header">查询结果</div>
<!-- div.dataTables_borderWrap -->
<div>
	<table id="dynamic-table"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="center"><label class="pos-rel"> <input
						type="checkbox" class="ace" /> <span class="lbl"></span>
				</label></th>
				<th>父模块</th>
				<th>模块标题</th>
				<th>模块编码</th>
				<th>模块图标</th>
				<th>模块URL</th>
				<th class="center">操作</th>
			</tr>
		</thead>
		<tfoot></tfoot>
	</table>
</div>
<div id="divInputForm" class="modal" tabindex="-1">
	<div class="modal-dialog" style="width: 800px; height: 600px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="blue bigger">请输入</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<form id="formInput" class="form-horizontal" role="form">
						<input type="hidden" id="hiddenModuleId" />
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right"
								for="selectParentModuleId">父模块</label>
							<div class="col-sm-4">
								<select class="chosen-select form-control"
									id="selectParentModuleId" name="selectParentModuleId" data-placeholder="选择父模块..." >
								</select>
							</div>
							<label class="col-sm-2 control-label no-padding-right"
								for="textmoduleTitle">模块标题</label>
							<div class="col-sm-4">
								<input type="text" id="textModuleTitle" name="textModuleTitle"
									required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right"
								for="textModuleCode">模块编码</label>
							<div class="col-sm-4">
								<input type="text" id="textModuleCode" name="textModuleCode"
									required />
							</div>
							<label class="col-sm-2 control-label no-padding-right"
								for="textModuleIcon">实体图标</label>
							<div class="col-sm-4">
								<input type="text" id="textModuleIcon" name="textModuleIcon" />
							</div>
						</div>
						<div class="form-group">
							
							<label class="col-sm-2 control-label no-padding-right"
								for="textmoduleUrl">模块URL</label>
							<div class="col-sm-10">
								<input type="text" id="textModuleUrl" name="textModuleUrl" class="col-sm-10" />
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="modal-footer">
				<button id="btnCancel" class="btn btn-sm" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i> 取消
				</button>
				<button id="btnOk" class="btn btn-success btn-sm">
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
			"data" : "moduleId",
			"orderable" : false,
			"searchable" : false
		}, {
			"data" : "parentModuleTitle",
			"searchable" : false
		}, {
			"data" : "moduleTitle",
			"searchable" : false
		}, {
			"data" : "moduleCode",
			"searchable" : false
		}, {
			"data" : "moduleIcon",
			"searchable" : false
		}, {
			"data" : "moduleUrl",
			"searchable" : false
		}, {
			"data" : "moduleId",
			"orderable" : false,
			"searchable" : false
		} ];
		//2-定义特殊选择列和超链接列
	    var columnDefs=[
			{
				"targets" : 0,
				"data" : "moduleId",
				"render" : function(
						data, type,
						row, meta) {

					var str = '<div class="center" > <label class="pos-rel">';
					str = str
							+ '<input type="checkbox" class="ace" value="'+data+'" />';
					str = str
							+ '<span class="lbl"></span></label> </div>';
					return str;
				}
			},
			{
				"targets" : columnSettings.length-1 ,
				"data" : "moduleId",
				"render" : function(
						data, type,
						row, meta) {
					var str = '<div class="center" >';
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
			} ];
		//1-初始化数据表格
		dataTableObj = $('#dynamic-table').DataTable({
			"serverSide" : true, //服务器处理：过滤、分页、排序
			"processing" : true, //是否显示处理状态
			"ajax" : {
				url : '../sys/moduleInfo',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    if($("#selectQueryParentModuleId").val()!="")
				    	req.parentModuleId = $("#selectQueryParentModuleId").val();
				    if($("#queryModuleTitle").val()!="")
				    	req.moduleTitle = $("#queryModuleTitle").val();
				}
			}, //获取数据的处理函数 
			"searching" : false,//搜索功能
			"bSort" : true, //排序功能
			"aaSorting" : [],
			"columns" : columnSettings,
			"columnDefs" : columnDefs,
			"bFilter" : false, //过滤功能
			"bPaginate" : true, //翻页功能
			"iDisplayLength":10,
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
		
		
		//5-设置窗口验证
		$("#formInput").validate();
	});

	//重新加载数据
	function refreshData() {
		dataTableObj.ajax.reload(null, true);
	}

	//查询、新增和保存按钮绑定事件
	jQuery(function($) {
		//查询信息
		$('#btnQuery').on('click', function(e) {
			refreshData();
			e.preventDefault();
		});
		//新增信息
		$('#btnAdd').on('click', function(e) {
			refreshParentModules();
			$("#hiddenModuleId").val(-1);
			$("#selectParentModuleId").val("");
			$("#textModuleCode").val("");
			$("#textModuleTitle").val("");
			$("#textModuleIcon").val("");
			$("#textModuleUrl").val("");
			$('#divInputForm').modal();
			e.preventDefault();
		});
		//保存信息
		$('#btnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if ($("#formInput").valid() == true) {
				postObject();
				e.preventDefault(); //阻止默认事件Post
			}
		});
	});

	//修改对象
	function editObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		refreshParentModules();
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenModuleId").val(row["moduleId"]);
		$("#selectParentModuleId").val(row["parentModuleId"]);
		$("#textModuleCode").val(row["moduleCode"]);
		$("#textModuleTitle").val(row["moduleTitle"]);
		$("#textModuleIcon").val(row["moduleIcon"]);
		$("#textModuleUrl").val( row["moduleUrl"]);
		$('#divInputForm').modal();
	}

	//提交对象
	function postObject() {
		var obj=new Object();
		obj.moduleId      =$("#hiddenModuleId").val();
		obj.parentModuleId=$("#selectParentModuleId").val();
		obj.moduleCode    =$("#textModuleCode").val();
		obj.moduleTitle   =$("#textModuleTitle").val();
		obj.moduleIcon    =$("#textModuleIcon").val();
		obj.moduleUrl     =$("#textModuleUrl").val();
		var jsonStr = JSON.stringify(obj);
		console.log("postObject json:"+jsonStr);
		var httpMethod="post";
		if($("#hiddenModuleId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		$.ajax({
			type : httpMethod,
			url : "../sys/moduleInfo",
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
					refreshData();
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
			url : "../sys/moduleInfo/"+deleteId,
			async : false, //默认为true 异步   
			error : function() {
				alert('提交失败!');
			},

			success : function(data) {
				$('#divInputForm').modal("hide");
				if (data == null || data == "") {
					$.gritter.add({
						title : '成功',
						text : '已经删除成功!',
						//sticky: false,  
						time : 2000,
						//speed:500,  
						position : 'bottom-right',
						class_name : 'gritter-sucess'
					});
					//重新加载数据
					refreshData();
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

	//刷新父模块
	function refreshParentModules() {
	    $.ajax({
					type : "get",
					url : "../sys/moduleInfos/parentModuleInfos",
					datatype : "json",
					async : false, //默认为true 异步   
					error : function() {
						alert('提交失败!');
					},
					success : function(data) {
						$("#selectParentModuleId").empty();
						$("#selectParentModuleId").append(
								"<option value=''></option>");
						for ( var index in data) {
							var line = data[index];
							$("#selectParentModuleId")
									.append(
											"<option value='" +line["moduleId"]+ "'>"
													+ line["moduleTitle"]
													+ "</option>");
						}
					}
				});
	}
</script>