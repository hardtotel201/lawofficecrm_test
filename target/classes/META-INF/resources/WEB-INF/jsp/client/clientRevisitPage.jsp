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
					<!-- 隐藏HTML元素,作为分页查询条件-->
					<input type="hidden" id="queryClientId" value="" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryRevisitType">回访类型</label>
				<div class="col-sm-2">
					<select type="text" id="queryRevisitType" name="queryRevisitType" class="input-text col-xs-12" >
						<option></option>
						<option>电话联系</option>
						<option>微信联系</option>
						<option>上门拜访</option>
					</select>
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryRevisitConent">回访内容</label>
				<div class="col-sm-2">
					<input type="text" id="queryRevisitConent" name="queryRevisitConent" class="input-text col-xs-12" />
				</div>
			</div>
			<div class="form-group">
				<label class="font-title col-sm-1 control-label no-padding-right"
					   for="queryRevisitTimeBegin">回访时间</label>
				<div class="col-sm-3 input-append date form_datetime">
					<input type="text" id="queryRevisitTimeBegin" name="queryRevisitTimeBegin" size="20" type="text" />
					<span class="addon"> <i class="fa fa-clock-o"></i></span> 到
					<input type="text" id="queryRevisitTimeEnd" name="queryRevisitTimeEnd" size="20" type="text" />
					<span class="addon"> <i class="fa fa-clock-o"></i></span>
				</div>
				<div style="text-align: right; float: right;">
					<button id="btnQuery" class="btn btn-purple btn-sm">查询</button>
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
			<th class="bg-table-header">客户标识</th>
			<th class="bg-table-header">回访类型</th>
			<th class="bg-table-header">回访时间</th>
			<th class="bg-table-header">回访时长(分钟)</th>
			<th class="bg-table-header">回访内容</th>
			<th class="bg-table-header center">相关文件</th>
			<th class="bg-table-header">操作时间</th>
			<th class="bg-table-header">操作人</th>
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
						<input type="hidden" id="hiddenClientRevisitId" name="hiddenClientRevisitId" />
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputClientId">客户(*)</label>
							<div class="col-sm-4">
								<select class="chosen-select form-control" id="inputClientId" name="inputClientId" data-placeholder="选择回访客户..." required >
									<option></option>
									<c:forEach var="item" items="${clientInfos}">
										<option value="${item.clientId}" >${item.clientName}</option>
									</c:forEach>
								</select>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputRevisitTime">回访时间</label>
							<div class="col-sm-4">
								<input type="text" id="inputRevisitTime" name="inputRevisitTime"
									   class="input-text dialog-control" required />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="selectRevisitType">回访类型</label>
							<div class="col-sm-4">
								<select type="text" id="selectRevisitType" name="selectRevisitType"
										class="input-text dialog-control" required >
									<option>电话联系</option>
									<option>微信联系</option>
									<option>上门拜访</option>
								</select>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputRevisitTimeSpan">回访时长(分钟)</label>
							<div class="col-sm-4">
								<input type="text" id="inputRevisitTimeSpan" name="inputRevisitTimeSpan"
									   class="input-text dialog-control" required />
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputRevisitContent">回访内容</label>
							<div class="col-sm-10">
								<textarea id="inputRevisitContent" name="inputRevisitContent" class="form-control col-xs-10" rows="3" ></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="fileInputControl">相关文件</label>
							<div class="col-sm-4">

								<a id="fileDownload" >文件下载</a>
								<input type="file" id="fileInputControl" name="fileInputControl" class="form-control" placeholder="点击按钮选择文件" >

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
			"data" : "clientRevisitId",
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
			"data" : "revisitType",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "revisitTime",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "revisitTimeSpan",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "revisitConent",
			"sClass":"font-title",
			"searchable" : false,
			"render": function (data) {
				return data.replace(/\n/g, "<br>");
			}
		},
		{
			"data" : "fileName",
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
			"data" : "clientRevisitId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		} ];
		
		//2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "clientRevisitId",
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
			},{
				"targets" :columnSettings.length-4,
				"data" : "fileName",
				"render" : function(
						data, type,
						row, meta) {
					var str = '<div class="bg-table-header center" >';
					if(data!=null && data!=""){
						str = str
						+ '<a target="_blank" href="../upload/'
						+ data
						+ '" >浏览</a>';
					}
					str = str
							+ '</div>'
					return str;
				}
			},
			{
				"targets" :columnSettings.length-1,
				"data" : "clientRevisitId",
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
				url : '../client/clientRevisit/',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    //2-设置请求参数
					if($("#queryClientId").val()!="")
						req.clientId=$("#queryClientId").val();
					else if($("#queryClientName").val()!="")
						req.clientName=$("#queryClientName").val();
					if($("#queryRevisitType").val()!="")
						req.revisitType=$("#queryRevisitType").val();
					if($("#queryRevisitTimeSpan").val()!="")
						req.revisitTimeSpan=$("#queryRevisitTimeSpan").val();
					if($("#queryRevisitConent").val()!="")
						req.revisitConent=$("#queryRevisitConent").val();
					if($("#queryRevisitTimeBegin").val()!="")
						req.revisitTimeBegin=$("#queryRevisitTimeBegin").val();
					if($("#queryRevisitTimeEnd").val()!="")
						req.revisitTimeEnd=$("#queryRevisitTimeEnd").val();
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
		
	});

	jQuery(function($) {
		//5-查询按钮
		$('#btnQuery').on('click', function(e) {
			dataTableObj.ajax.reload(null, true);
			e.preventDefault();
		});

		//6-新增信息
		$('#btnAdd').on('click', function(e) {
			$("#hiddenClientRevisitId").val(-1);
			$("#inputClientId").val($("#queryClientId").val());
			$("#inputClientId").trigger("chosen:updated");
			$("#selectRevisitType").val("");
			$("#inputRevisitTime").val("");
			$("#inputRevisitTimeSpan").val("");
			$("#inputRevisitContent").val("");
			$("#inputFileName").val("");
			$("#fileDownload").hide();
			var fileExtensions=['image', 'html', 'text', 'video', 'audio', 'flash', 'object','png','jpg','db','txt','pdf'];
			initFileInput(1,[],fileExtensions,false);
			$('#divInputForm').modal();
			e.preventDefault();
		});

		//7-保存信息
		$('#btnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if($("#formInput").valid()==true){
				//上传文件
				if($("#fileInputControl").val()=="")
				{
					postObject("");
				}
				else{
					//设置同步上传事件(异步上传事件fileuploaded)
					$("#fileInputControl").one('filebatchuploadsuccess', function (event, data) {
						if(data.response==null || data.response=="" || data.response.length<=0)
						{
							showError("上传文件失败!");
							return;
						}
						postObject(data.response[0]);
					});
					$("#fileInputControl").fileinput('upload');
				}
				e.preventDefault(); //阻止默认事件Post
			}
		});

		//8-返回主页面
		$('#btnReturn').on('click', function(e) {
			e.preventDefault(); //阻止默认事件Post
			var strUrl="../client/clientBasicPage/all/${sessionScope.requestPageIndex}";
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
		$("#hiddenClientRevisitId").val(row["clientRevisitId"]);
		$("#inputClientId").val(row["clientId"]);
		$("#inputClientId").trigger("chosen:updated");
		$("#selectRevisitType").val(row["revisitType"]);
		$("#inputRevisitTime").val(row["revisitTime"]);
		$("#inputRevisitTimeSpan").val(row["revisitTimeSpan"]);
		$("#inputRevisitContent").val(row["revisitConent"]);

		oldFileName=row["fileName"];
		var fileExtensions=['image', 'html', 'text', 'video', 'audio', 'flash', 'object','png','jpg','db','txt','pdf'];
		if(oldFileName==null || oldFileName==""){
			$("#fileDownload").hide();
			initFileInput(1,[],fileExtensions,false);
		}
		else{
			$("#fileDownload").attr("href","../upload/"+row["fileName"]);
			$("#fileDownload").html(row["fileName"]+"(点击下载)");
			$("#fileDownload").show();
			initFileInput(1,[oldFileName],fileExtensions,false);
		}
		$('#divInputForm').modal();
	}
	
	//提交对象
	function postObject(fileName) {
	    var obj=new Object();
		obj.clientRevisitId =$("#hiddenClientRevisitId").val();
		obj.clientId=$("#inputClientId").val();
		obj.revisitType=$("#selectRevisitType").val();
		obj.revisitTime=$("#inputRevisitTime").val();
		obj.revisitTimeSpan=$("#inputRevisitTimeSpan").val();
		obj.revisitConent=$("#inputRevisitContent").val();
		obj.fileName=fileName;
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		var httpMethod="post";
		if($("#hiddenClientRevisitId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		$.ajax({
			type : httpMethod,
			url : "../client/clientRevisit/",
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
			url : "../client/clientRevisit/"+deleteId,
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

	//初始化时间控件
	$('#queryRevisitTimeBegin').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});
	$('#queryRevisitTimeEnd').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});

	$('#inputRevisitTime').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});
</script>