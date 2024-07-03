<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 查询条件 -->
<div class="bg-query-head" style="font-size:20px;color:#00BFF4">&nbsp;查询条件</div>
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
				       for="queryLawyerPhone">电话</label>
				<div class="col-sm-2">
					<input type="text" id="queryLawyerPhone" name="queryLawyerPhone" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryRegisterTimeBegin">注册时间</label>
				<div class="col-sm-4 input-append date form_datetime">
					<input type="text" id="queryRegisterTimeBegin" name="queryRegisterTimeBegin" size="20" type="text" /> 
					<span class="addon"> <i class="fa fa-clock-o"></i></span> 到 
					<input type="text" id="queryRegisterTimeEnd" name="queryRegisterTimeEnd"" size="20" type="text" /> 
					<span class="addon"> <i class="fa fa-clock-o"></i></span>
				</div>
				<div style="text-align: right; float: right;">
					<button id="btnQuery" class="btn-op btn btn-sm btn-primary">查询</button>
					<button id="btnAdd"   class="btn-op btn btn-sm btn-primary">新增</button>
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
			<th class="bg-table-header">律师姓名</th>
			<th class="bg-table-header">律所名称</th>
			<th class="bg-table-header">律所地址</th>
			<th class="bg-table-header">律师电话</th>
			<th class="bg-table-header">律师地址</th>
			<th class="bg-table-header">律师邮箱</th>
			<th class="bg-table-header">注册时间</th>
			<th class="bg-table-header">律师状态</th>
			<th class="bg-table-header">操作时间</th>
			<th class="bg-table-header">操作用户</th>
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
						<input type="hidden" id="hiddenLawyerId" name="hiddenLawyerId" />
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputLawyerName">律师姓名（*）</label>
							<div class="col-sm-4">
								<input type="text" id="inputLawyerName" name="inputLawyerName"  
								       class="dialog-control col-xs-10" required />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputLawyerPhone">律师电话（*）</label>
							<div class="col-sm-4">
								<input type="text" id="inputLawyerPhone" name="inputLawyerPhone"  
								       class="dialog-control col-xs-10" required />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputOfficeName">律所名称（*）</label>
							<div class="col-sm-4">
								<input type="text" id="inputOfficeName" name="inputOfficeName"  
								       class="dialog-control col-xs-10" required />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputOfficeAddress">律所地址</label>
							<div class="col-sm-4">
								<input type="text" id="inputOfficeAddress" name="inputOfficeAddress"  
								       class="dialog-control col-xs-10"  />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputLawyerAddress">律师地址</label>
							<div class="col-sm-4">
								<input type="text" id="inputLawyerAddress" name="inputLawyerAddress"  
								       class="dialog-control col-xs-10"  />
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputLawyerEmail">律师邮箱</label>
							<div class="col-sm-4">
								<input type="text" id="inputLawyerEmail" name="inputLawyerEmail"  
								       class="dialog-control col-xs-10"  />
							</div>
						</div>
							
						<div class="form-group">
		                   
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputLawyerState">律师状态（*）</label>
							<div class="col-sm-4">
								<select id="inputLawyerState" class="dialog-control col-xs-5" required>
									<option value=""></option>
									<c:forEach var="item" items="${lawyerStates}">
										<option>${item}</option>
									</c:forEach>
								</select>
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
    //1-声明表格对象
	var dataTableObj = null;
	
	$(function() {
		//1-设置DataTable列
		var columnSettings=[ {
			"data" : "lawyerId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		}, 
		{
			"data" : "lawyerName",
			"sClass":"font-title",
		
			"searchable" : false
		},
		{
			"data" : "officeName",
			"sClass":"font-title",
		
			"searchable" : false
		},
		{
			"data" : "officeAddress",
			"sClass":"font-title",
		
			"searchable" : false
		},
		{
			"data" : "lawyerPhone",
			"sClass":"font-title",
	
			"searchable" : false
		},
		{
			"data" : "lawyerAddress",
			"sClass":"font-title",
			
			"searchable" : false
		}
		,
		{
			"data" : "lawyerEmail",
			"sClass":"font-title",
		
			"searchable" : false
		},
		{
			"data" : "registerTime",
			"sClass":"font-title",
			
			"searchable" : false
		},
		{
			"data" : "lawyerState",
			"sClass":"font-title",
			
			"searchable" : false
		},
		{
			"data" : "opTime",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "opUserName",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "lawyerId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		} ];
		
		//2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "lawyerId",
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
				"data" : "lawyerId",
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
				url : '../lawyer/lawyerInfo/',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    //2-设置请求参数
					if($("#queryLawyerName").val()!="")
						req.lawyerName=$("#queryLawyerName").val();
					if($("#queryLawyerPhone").val()!="")
						req.lawyerPhone=$("#queryLawyerPhone").val();
				    if($("#queryRegisterTimeBegin").val()!="")
					    req.registerTimeBegin=$("#queryRegisterTimeBegin").val();
				    if($("#queryRegisterTimeEnd").val()!="")
					    req.registerTimeEnd=$("#queryRegisterTimeEnd").val();
				    //console.log("page request:"+JSON.stringify(req));
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
			$("#hiddenLawyerId").val(-1);
		    $("#inputLawyerName").val("");
		    $("#inputOfficeName").val("");
		    $("#inputOfficeAddress").val("");
		    $("#inputLawyerPhone").val("");
		    $("#inputLawyerAddress").val("");
		    $("#inputLawyerEmail").val("");
		    $("#inputRegisterTime").val("");
		    $("#inputLawyerState").val("");
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
		$("#hiddenLawyerId").val(row["lawyerId"]);
		$("#inputLawyerName").val(row["lawyerName"]);
		$("#inputOfficeName").val(row["officeName"]);
	    $("#inputOfficeAddress").val(row["officeAddress"]);
		$("#inputLawyerPhone").val(row["lawyerPhone"]);
		$("#inputLawyerAddress").val(row["lawyerAddress"]);
		$("#inputLawyerEmail").val(row["lawyerEmail"]);
	
		$("#inputLawyerState").val(row["lawyerState"]);
		$('#divInputForm').modal();
	}
	
	//提交对象
	function postObject() {
	    var obj=new Object();
		obj.lawyerId =$("#hiddenLawyerId").val();
		obj.lawyerName=$("#inputLawyerName").val();
		obj.officeName=$("#inputOfficeName").val();
		obj.officeAddress=$("#inputOfficeAddress").val();
		obj.lawyerPhone=$("#inputLawyerPhone").val();
		obj.lawyerAddress=$("#inputLawyerAddress").val();
		obj.lawyerEmail=$("#inputLawyerEmail").val();
		
		obj.lawyerState=$("#inputLawyerState").val();
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		var httpMethod="post";
		if($("#hiddenLawyerId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		$.ajax({
			type : httpMethod,
			url : "../lawyer/lawyerInfo/",
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
			url : "../lawyer/lawyerInfo/"+deleteId,
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
	$('#queryRegisterTimeBegin').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
			$(this).datetimepicker('hide');
	});
	$('#queryRegisterTimeEnd').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
			$(this).datetimepicker('hide');
	});
	$('#inputRegisterTime').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
			$(this).datetimepicker('hide');
	});
</script>