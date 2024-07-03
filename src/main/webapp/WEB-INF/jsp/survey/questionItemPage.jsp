<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					<!-- 隐藏HTML元素,作为分页查询条件-->
					<input type="hidden" id="queryQuestionId" value="" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right"
				       for="queryItemIndex">选择项序号</label>
				<div class="col-sm-2">
					<input type="text" id="queryItemIndex" name="queryItemIndex" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryItemConent">选择项内容</label>
				<div class="col-sm-2">
					<input type="text" id="queryItemConent" name="queryItemConent" class="input-text col-xs-12" />
				</div>
				<div style="text-align: right; float: right;">
					<button id="btnQuery" class="btn-op btn btn-sm btn-primary">查询</button>
					<button id="btnAdd"   class="btn-op btn btn-sm btn-primary">新增</button>
					<button id="btnReturn" class="btn-op btn btn-sm btn-primary">返回</button>
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
			<th class="bg-table-header">问题</th>
			<th class="bg-table-header">选择项序号</th>
			<th class="bg-table-header">选择项内容</th>
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
						<input type="hidden" id="hiddenQuestionItemId" name="hiddenQuestionItemId" />
						<div class="form-group">
						    <label class="font-title col-sm-2 control-label no-padding-right"
								for="inputQuestionId">问题(*)</label>
							<div class="col-sm-4">
								<select class="chosen-select form-control" id="inputQuestionId" name="inputQuestionId" data-placeholder="选择题目..." required >
									<option></option>
									<c:forEach var="item" items="${questionInfos}">
										<option value="${item.questionId}" >${item.questionTitle}</option>
									</c:forEach>
								</select>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputItemIndex">选择项序号</label>
							<div class="col-sm-4">
								<input type="text" id="inputItemIndex" name="inputItemIndex"  
								       class="dialog-control col-xs-10" required />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputItemConent">选择项内容</label>
							<div class="col-sm-10">
								<input type="text" id="inputItemConent" name="inputItemConent"  
								       class="dialog-control col-xs-11" required />
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
<script type="text/javascript">
	//0-预处理客户查询条件(其他页面传入)
	<c:if test="${questionInfo!=null}">
	   console.log("request questionId:"+${questionInfo.questionId});
	    $("#queryQuestionTitle").val("${questionInfo.questionTitle}");
		$("#queryQuestionId").val(   "${questionInfo.questionId}" );
	</c:if>
	//用户重新输入客户姓名清空queryClientId
	$("#queryQuestionTitle").change(function(){
		$("#queryQuestionId").val("");
	});

    //1-声明表格对象
	var dataTableObj = null;
	
	$(function() {
		//1-设置DataTable列
		var columnSettings=[ {
			"data" : "questionItemId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		}, 
		{
			"data" : "questionTitle",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "itemIndex",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "itemConent",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "questionItemId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		} ];
		
		//2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "questionItemId",
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
				"data" : "questionItemId",
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
				url : '../survey/questionItem/',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    //2-设置请求参数
				    if($("#queryQuestionId").val()!="")
						req.questionId=$("#queryQuestionId").val();
				    if($("#queryQuestionTitle").val()!="")
						req.questionTitle=$("#queryQuestionTitle").val();
					if($("#queryItemIndex").val()!="")
						req.itemIndex=$("#queryItemIndex").val();
					if($("#queryItemConent").val()!="")
						req.itemConent=$("#queryItemConent").val();
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
	    
		
		//5-查询按钮
		$('#btnQuery').on('click', function(e) {
		    dataTableObj.ajax.reload(null, true);
			e.preventDefault();
		});
		
		//6-新增信息
		$('#btnAdd').on('click', function(e) {
			$("#hiddenQuestionItemId").val(-1);
			$("#inputQuestionId").val($("#queryQuestionId").val());
			$("#inputQuestionId").trigger("chosen:updated");
		    $("#inputItemIndex").val("");
		    $("#inputItemConent").val("");
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
		    var strUrl="../survey/questionInfoPage";
		    showPageContent(strUrl,"问题信息");
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
		$("#hiddenQuestionItemId").val(row["questionItemId"]);
		$("#inputQuestionId").val(row["questionId"]);
		$("#inputQuestionId").trigger("chosen:updated");
		$("#inputItemIndex").val(row["itemIndex"]);
		$("#inputItemConent").val(row["itemConent"]);
		$('#divInputForm').modal();
	}
	
	//提交对象
	function postObject() {
	    var obj=new Object();
		obj.questionItemId =$("#hiddenQuestionItemId").val();
		obj.questionId=$("#inputQuestionId").val();
		obj.itemIndex =$("#inputItemIndex").val();
		obj.itemConent=$("#inputItemConent").val();
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		var httpMethod="post";
		if($("#hiddenQuestionItemId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		$.ajax({
			type : httpMethod,
			url : "../survey/questionItem/",
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
			url : "../survey/questionItem/"+deleteId,
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
</script>