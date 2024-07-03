<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 查询条件 -->
<div class="bg-query-head" style="font-size:20px;color:#00BFF4">&nbsp;查询条件</div>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
				<label class="font-title col-sm-1 control-label no-padding-right"
				       for="queryClientName">相关客户名称</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientName" name="queryClientName" class="input-text col-xs-12" />
					<!-- 隐藏HTML元素,作为分页查询条件-->
					<input type="hidden" id="queryClientId" value="" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryRelationType">关联类型</label>
				<div class="col-sm-2">
					<input type="text" id="queryRelationType" name="queryRelationType" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryRelationContent">关联说明</label>
				<div class="col-sm-2">
					<input type="text" id="queryRelationContent" name="queryRelationContent" class="input-text col-xs-12" />
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
			<th class="bg-table-header">客户名称（主）</th>
			<th class="bg-table-header">客户名称（从）</th>
			<th class="bg-table-header">关联类型</th>
			<th class="bg-table-header">关联说明</th>
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
				<h4 class="blue bigger" style="color:#DFE9FE">&nbsp;&nbsp;请输入</h4>
			</div>
			<div class="modal-body">
				<div class="row widget-main">
					<form id="formInput" class="form-horizontal" role="form" >
						<input type="hidden" id="hiddenClientRelationId" name="hiddenClientRelationId" />
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputClientId1">客户名称（主）</label>
							<div class="col-sm-4">
								<select class="chosen-select form-control" id="inputClientId1" name="inputClientId1" data-placeholder="选择客户..."  required >
									<option></option>
									<c:forEach var="item" items="${clientInfos}">
										<option value="${item.clientId}" >${item.clientName}</option>
									</c:forEach>
								</select>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputClientId2">客户名称（从）</label>
							<div class="col-sm-4">
								<select class="chosen-select form-control" id="inputClientId2" name="inputClientId2" data-placeholder="选择客户..." required >
									<option></option>
									<c:forEach var="item" items="${clientInfos}">
										<option value="${item.clientId}" >${item.clientName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputRelationType">关联类型</label>
							<div class="col-sm-4">
								<input type="text" id="inputRelationType" name="inputRelationType"  
								       class="dialog-control col-xs-10" required />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputRelationContent">关联说明</label>
							<div class="col-sm-4">
								<input type="text" id="inputRelationContent" name="inputRelationContent"
									   class="dialog-control col-xs-10" />
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="footer-dialog modal-footer" style="">
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
		//0-预处理客户查询条件(其他页面传入)
		<c:if test="${clientInfo!=null}">
		    console.log("request clientId:"+${clientInfo.clientId});
		    $("#queryClientName").val("${clientInfo.clientName}");
       		$("#queryClientId").val(  "${clientInfo.clientId}" );
        </c:if>
        //用户重新输入客户姓名清空queryClientId
        $("#queryClientName").change(function(){
        	$("#queryClientId").val("");
        });
		//1-设置DataTable列
		var columnSettings=[ {
			"data" : "clientRelationId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		},
		{
			"data" : "clientName1",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "clientName2",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "relationType",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "relationContent",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false,
			"render": function (data) {
				return data.replace(/\n/g, "<br>");
			}
		},
		{
			"data" : "clientRelationId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		} ];
		
		//2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "clientRelationId",
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
				"data" : "clientRelationId",
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
				url : '../client/clientRelation/',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    //2-设置请求参数
					if($("#queryClientId").val()!="")
						req.queryClientId=$("#queryClientId").val();
					else if($("#queryClientName").val()!="")
						req.clientName=$("#queryClientName").val();;
					if($("#queryRelationType").val()!="")
						req.relationType=$("#queryRelationType").val();
					if($("#queryRelationContent").val()!="")
						req.relationContent=$("#queryRelationContent").val();
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

		//4-初始化jquery chosen和bootstrap multiselect控件
		//初始化JQuery Chosen组件
		initJQueryChosen();

		//5-设置TableTools
		initTableTools(dataTableObj);
		
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


	jQuery(function($) {
		//5-查询按钮
		$('#btnQuery').on('click', function(e) {
			dataTableObj.ajax.reload(null, true);
			e.preventDefault();
		});

		//6-新增信息
		$('#btnAdd').on('click', function(e) {
			$("#hiddenClientRelationId").val(-1);
			$("#inputClientId1").val($("#queryClientId").val());
			$("#inputClientId1").trigger("chosen:updated");
			$("#inputClientId2").val("");
			$("#inputClientId2").trigger("chosen:updated");
			$("#inputRelationType").val("");
			$("#inputRelationContent").val("");
			$('#divInputForm').modal();
			e.preventDefault();
		});

		//7-保存信息
		$('#btnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if($("#formInput").valid()==true){
				var inputClientId1=$("#inputClientId1").val();
				var inputClientId2=$("#inputClientId2").val();
				if(inputClientId1==inputClientId2){
					showError("关联客户不能重复!");
					return;
				}
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
	});

	//修改对象
	function editObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenClientRelationId").val(row["clientRelationId"]);
		$("#inputClientId1").val(row["clientId1"]);
		$("#inputClientId1").trigger("chosen:updated");
		$("#inputClientId2").val(row["clientId2"]);
		$("#inputClientId2").trigger("chosen:updated");
		$("#inputRelationType").val(row["relationType"]);
		$("#inputRelationContent").val(row["relationContent"]);
		$('#divInputForm').modal();
	}
	
	//提交对象
	function postObject() {
	    var obj=new Object();
		obj.clientRelationId =$("#hiddenClientRelationId").val();
		obj.clientId1=$("#inputClientId1").val();
		obj.clientId2=$("#inputClientId2").val();
		obj.relationType=$("#inputRelationType").val();
		obj.relationContent=$("#inputRelationContent").val();
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		var httpMethod="post";
		if($("#hiddenClientRelationId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		$.ajax({
			type : httpMethod,
			url : "../client/clientRelation/",
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
			url : "../client/clientRelation/"+deleteId,
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
</script>