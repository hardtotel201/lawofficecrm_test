<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 查询条件 -->
<div class="bg-query-head" style="font-size:20px;color:#00BFF4">&nbsp;查询条件</div>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
   				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryQuestionTitle">问题题目</label>
				<div class="col-sm-2">
					<input type="text" id="queryQuestionTitle" name="queryQuestionTitle" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryQuestionType">问题类型</label>
				<div class="col-sm-2">
					<select id="queryQuestionType" name="queryQuestionType" class="form-control col-xs-6" >
						<option value=""></option>
						<option>选择题</option>
						<option>问答题</option>
					</select>
				</div>
			</div>
			<div style="text-align: right; float: right;">
				<button id="btnQuery" class="btn-op btn btn-sm btn-primary">查询</button>
				<button id="btnAdd"   class="btn-op btn btn-sm btn-primary">新增</button>
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
			<th class="bg-table-header">问题题目</th>
			<th class="bg-table-header">问题类型(选择题/问答题)</th>
			<th class="bg-table-header">操作时间</th>
			<th class="bg-table-header">操作用户</th>
			<th class="bg-table-header center">问题项</th>
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
						<input type="hidden" id="hiddenQuestionId" name="hiddenQuestionId" />
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputQuestionTitle">问题题目(*)</label>
							<div class="col-sm-4">
								<input type="text" id="inputQuestionTitle" name="inputQuestionTitle"  
								       class="dialog-control col-xs-8" required />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="selectQuestionType">问题类型(*)</label>
							<div class="col-sm-4">
								<select id="selectQuestionType" name="selectQuestionType"
										class="dialog-control col-xs-8" required>
									<option>选择题</option>
									<option>问答题</option>
								</select>
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
			"data" : "questionId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		}, 
		{
			"data" : "questionTitle",
			"sClass":"font-title",
			"orderable"  : true,
			"searchable" : false
		},
		{
			"data" : "questionType",
			"sClass":"font-title",
			"orderable"  : true,
			"searchable" : false
		},
		{
			"data" : "opTime",
			"sClass":"font-title",
			"orderable"  : true,
			"searchable" : false
		},
		{
			"data" : "opUserName",
			"sClass":"font-title",
			"orderable"  : true,
			"searchable" : false
		},
		{
			"data" : "questionId",
			"sClass":"font-title",
			"orderable" : true,
			"searchable" : false
		},
		{
			"data" : "questionId",
			"sClass":"font-title",
			"orderable" : true,
			"searchable" : false
		} ];
		
		//2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "questionId",
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
				"targets" :columnSettings.length-2,
				"data" : "questionId",
				"render" : function(
						data, type,
						row, meta) {
					var str = '<div class="bg-table-header center" >';
					if(row["questionType"]=="选择题"){
						str = str
							+ '<a href="javascript:questionItem('
							+ meta.row
							+ ')" >问题项</a>';
					}
					str = str+ '</div>'
					return str;
				}
			},
			{
				"targets" :columnSettings.length-1,
				"data" : "questionId",
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
				url : '../survey/questionInfo/',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    //2-设置请求参数
					if($("#queryQuestionTitle").val()!="")
						req.questionTitle=$("#queryQuestionTitle").val();
					if($("#queryQuestionType").val()!="")
						req.questionType=$("#queryQuestionType").val();
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
			$("#hiddenQuestionId").val(-1);
		    $("#inputQuestionTitle").val("");
		    $("#selectQuestionType").val("");
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
		$("#hiddenQuestionId").val(row["questionId"]);
		$("#inputQuestionTitle").val(row["questionTitle"]);
		$("#selectQuestionType").val(row["questionType"]);
		$("#inputOpTime").val(row["opTime"]);
		$("#inputOpUserId").val(row["opUserId"]);
		$('#divInputForm').modal();
	}
	
	//提交对象
	function postObject() {
	    var obj=new Object();
		obj.questionId =$("#hiddenQuestionId").val();
		obj.questionTitle=$("#inputQuestionTitle").val();
		obj.questionType=$("#selectQuestionType").val();
		obj.opTime=$("#inputOpTime").val();
		obj.opUserId=$("#inputOpUserId").val();
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		var httpMethod="post";
		if($("#hiddenQuestionId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		$.ajax({
			type : httpMethod,
			url : "../survey/questionInfo/",
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
			url : "../survey/questionInfo/"+deleteId,
			error : function() {
			    showError('提交失败!');
			},
			success : function(data) {
				const r = confirm("确定删除此信息？ ");
				$('#divInputForm').modal("hide");
				if(r){
					if (data == null || data == "") {
					showSuccess('已经删除成功!');
					//重新加载数据
					dataTableObj.ajax.reload(null, true);
				} else
					showError(data);
			}
				}
		});
	}
	
	function questionItem(rowIndex){
		console.log("questionItem("+rowIndex+") is clicked");
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		var row = dataTableObj.data()[rowIndex];
	    var strUrl="../survey/questionItemPage/"+row["questionId"];
	    console.log(strUrl+" is open");
	    showPageContent(strUrl,row["questionTitle"]+"问题项");
	}
</script>