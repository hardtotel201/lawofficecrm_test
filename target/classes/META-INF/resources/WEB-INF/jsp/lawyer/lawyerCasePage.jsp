<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.alibaba.excel.EasyExcel"%>

<!-- 查询条件 -->
<div class="bg-query-head" style="font-size:16px;color:#00BFF4">&nbsp;查询条件</div>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
			<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryLawyerName">律师姓名</label>
				<div class="col-sm-2">
					<input type="text" id="queryLawyerName" name="queryLawyerName" class="input-text col-xs-12" />
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
				<div style="text-align: right; float: right;">
					<button id="btnQuery" class="btn btn-purple btn-sm">查询</button>
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
			</th >
			<th class="bg-table-header">律师姓名</th>
			<th class="bg-table-header">案件编号</th>
			<th class="bg-table-header">案件名称</th>
			<th class="bg-table-header">案件类型</th>
			</th>
			<th class="bg-table-header">案件状态</th>
			<th class="bg-table-header">操作时间</th>
			<th class="bg-table-header">操作用户</th>
			</th>
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
			"data" : "caseId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		},
		
		{
			"data" : "lawyerName",
			"sClass":"font-title",
			"orderable"  : false,
			"searchable" : false
		}
		,
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
		}];
		
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
					if($("#queryLawyerName").val()!="")
						req.lawyerName=$("#queryLawyerName").val();
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

	
	$('#inputAcceptTime').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});
</script>