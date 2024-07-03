<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 查询条件 -->
<div class="bg-query-head" style="font-size:20px;color:#00BFF4">&nbsp;查询条件</div>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientName">客户姓名</label>
				<div class="col-sm-2">
					<input type="text" id="queryClientName" name="queryClientName" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="querySurveyTitle">调研标题</label>
				<div class="col-sm-2">
					<input type="text" id="querySurveyTitle" name="querySurveyTitle" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryQuestionTitle">调研问题</label>
				<div class="col-sm-2">
					<input type="text" id="queryQuestionTitle" name="queryQuestionTitle" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryAnswerContent">回答内容</label>
				<div class="col-sm-2">
					<input type="text" id="queryAnswerContent" name="queryAnswerContent" class="input-text col-xs-12" />
				</div>
			</div>
			<div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryAnswerTimeBegin">回答时间</label>
				<div class="col-sm-3">
					<input type="text" id="queryAnswerTimeBegin" name="queryAnswerTimeBegin" size="20" type="text" />
					<span class="addon"> <i class="fa fa-clock-o"></i></span> 到
					<input type="text" id="queryAnswerTimeEnd" name="queryAnswerTimeEnd" size="20" type="text" />
					<span class="addon"> <i class="fa fa-clock-o"></i></span>
				</div>
				<div style="text-align: right; float: right;">
					<button id="btnQuery" class="btn-op btn btn-sm btn-primary">查询</button>
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
			<th class="bg-table-header">客户名称</th>
			<th class="bg-table-header">渠道来源</th>
			<th class="bg-table-header">调研标题</th>
			<th class="bg-table-header">调研类型</th>
			<th class="bg-table-header">调研时间</th>
			<th class="bg-table-header">调研方法</th>
			<th class="bg-table-header">调研问题</th>
			<th class="bg-table-header">问题类型</th>
			<th class="bg-table-header">回答时间</th>
			<th class="bg-table-header">回答内容</th>
			<th class="bg-table-header">回答文件</th>
		</tr>
	</thead>
	<tfoot></tfoot>
</table>

<script type="text/javascript">
    //1-声明表格对象
	var dataTableObj = null;
	
	$(function() {
		//1-设置DataTable列
		var columnSettings=[ {
			"data" : "surveyQuestionnaireId",
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
			"data" : "sourceMemo",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "surveyTitle",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "surveyType",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "surveyTime",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "surveyMethod",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "questionTitle",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "questionType",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "answerTime",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "answerContent",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		},
		{
			"data" : "answerFileName",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		}];
		
		//2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "surveyQuestionnaireId",
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
				"data" : "answerFileName",
				"render" : function(
						data, type,
						row, meta) {
					var str = '<div class="bg-table-header center" >';
					if (data!=null && data!=""){
						console.log("<a href='../upload/"+data+"'>"+"浏览</a>");
						str = str+"<a href='../upload/"+data+"' target='_blank' >"+"浏览</a>";
					}
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
				url : '../survey/surveyQuestionnaire/',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    if($("#queryClientName").val()!="")
						req.clientName=$("#queryClientName").val();
				    if($("#querySurveyTitle").val()!="")
						req.surveyTitle=$("#querySurveyTitle").val();
				    if($("#queryQuestionTitle").val()!="")
						req.questionTitle=$("#queryQuestionTitle").val();
					if($("#queryAnswerContent").val()!="")
						req.answerContent=$("#queryAnswerContent").val();
					if($("#queryAnswerTimeBegin").val()!="")
						req.AnswerTimeBegin=$("#queryAnswerTimeBegin").val();
					if($("#queryAnswerTimeEnd").val()!="")
						req.AnswerTimeEnd=$("#queryAnswerTimeEnd").val();
				    //2-设置请求参数
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
	});
	
	//初始化时间控件
	$('#queryAnswerTimeBegin').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});
	$('#queryAnswerTimeEnd').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});

</script>