<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 查询条件 -->
<div class="bg-query-head" style="font-size:20px;color:#00BFF4">&nbsp;查询条件</div>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
			    <label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryUserName">被分配用户</label>
				<div class="col-sm-1">
					<input type="text" id="queryUserName" name="queryUserName" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryClientName">被分配客户</label>
				<div class="col-sm-1">
					<input type="text" id="queryClientName" name="queryClientName" class="input-text col-xs-12" />
					<!-- 隐藏HTML元素,作为分页查询条件-->
					<input type="hidden" id="queryClientId" value="" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryAssignUserName">分配人</label>
				<div class="col-sm-1">
					<input type="text" id="queryAssignUserName" name="queryAssignUserName" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right"
					   for="queryAssignTimeBegin">分配时间</label>
				<div class="col-sm-4 input-append date form_datetime">
					<input type="text" id="queryAssignTimeBegin" name="queryAssignTimeBegin" size="20" type="text" />
					<span class="addon"> <i class="fa fa-clock-o"></i></span> 到
					<input type="text" id="queryAssignTimeEnd" name="queryAssignTimeEnd" size="20" type="text" />
					<span class="addon"> <i class="fa fa-clock-o"></i></span>
				</div>
				<div style="text-align: right; float: right;">
					<button id="btnQuery" class="btn btn-purple btn-sm">查询</button>
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
			<th class="bg-table-header">被分配用户</th>
			<th class="bg-table-header">被分配客户</th>
			<th class="bg-table-header">分配人</th>
			<th class="bg-table-header">分配时间</th>
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
						<input type="hidden" id="hiddenClientAssignId" name="hiddenClientAssignId" />
						<div class="form-group">
							<label class="font-title col-sm-2 control-label no-padding-right"
								for="inputUserIds">被分配用户(*)</label>
							<div class="col-sm-4">
								<select id="inputUserIds" name="inputUserIds" class="form-control col-xs-9" required >
									<option value=""></option>
									<c:forEach var="item" items="${userInfos}">
										<option value="${item.userId}">${item.userName}</option>
									</c:forEach>
								</select>
							</div>
							<label class="font-title col-sm-2 control-label no-padding-right"
								   for="inputClientIds">被分配客户(*)</label>
							<div class="col-sm-4">
								<select id="inputClientIds" name="inputClientIds" class="multiselect col-xs-9" multiple="" required >
								</select>
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
			"data" : "clientAssignId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		}, 
		{
			"data" : "userName",
			"render": function(data) {
				return data ? data : '未填写';
			},
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientName",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "assignUserName",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "assignTime",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "clientAssignId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		} ];
		
		//2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "clientAssignId",
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
				"data" : "clientAssignId",
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
				url : '../client/clientAssign/',
				type : 'get',
				data : function(req) {
					//1-过滤参数(设置分页参数)
				    filterRequest(req);
				    //2-设置请求参数
				    if($("#queryUserName").val()!="")
						req.userName=$("#queryUserName").val();
					if($("#queryClientId").val()!="")
						req.clientId=$("#queryClientId").val();
					else if($("#queryClientName").val()!="")
						req.clientName=$("#queryClientName").val();
					if($("#queryClientName").val()!="")
						req.clientName=$("#queryClientName").val();
					if($("#queryAssignUserName").val()!="")
						req.assignUserName=$("#queryAssignUserName").val();
					if($("#queryAssignTimeBegin").val()!="")
						req.assignTimeBegin=$("#queryAssignTimeBegin").val();
					if($("#queryAssignTimeEnd").val()!="")
						req.assignTimeEnd=$("#queryAssignTimeEnd").val();
				},
			//"dataSrc" : function(json) {
			//	return json.data;
			//}
			}, //获取数据的处理函数 
			"searching" : false,//搜索功能
			"bSort" : true, //排序功能
			"order" : [4,'desc'], //默认排序的指标
			"aaSorting" : [],
			"columns" : columnSettings,
			"columnDefs" : columnDefs,
			"bFilter" : false, //过滤功能
			"bPaginate" : true, //翻页功能
			"pageLength": 30,
			"sPaginationType" : "full_numbers", //分页的格式
			"bLengthChange" : false, //改变每页显示数据数量
			"bInfo" : true, //页脚信息
			"bAutoWidth" : true,//自动宽度
			"language" : dataTableLanguage,
			"select": {
				style: 'multi'
			},
		});
        
		
		//4-初始化多项选择组件
        initMultiSelect();
		
		//4-设置TableTools
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

	function initInputClientIds(){
		$('#inputClientIds').empty();
		var strClientIds="${sessionScope.clientAssign.clientIds}";
		console.log("initInputClientIds() strClientIds:"+strClientIds);
		<c:forEach var="item" items="${clientInfos}">
			$('#inputClientIds').append($('<option></option>').text("${item.clientName}").val(${item.clientId}));
		</c:forEach>
		$('#inputClientIds').multiselect('rebuild');
		$('#inputClientIds').multiselect('refresh');
	}

    //查询、新增和保存按钮绑定事件
    jQuery(function($) {
        //5-查询按钮
        $('#btnQuery').on('click', function(e) {
            dataTableObj.ajax.reload(null, true);
            e.preventDefault();
        });

        //6-新增信息
        $('#btnAdd').on('click', function(e) {
            $("#hiddenClientAssignId").val(-1);
            initInputClientIds();
            $('#inputClientIds').multiselect('rebuild');
            $('#inputClientIds').multiselect('refresh');
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
    });

	//修改对象
	function editObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenClientAssignId").val(row["clientAssignId"]);
		$("#inputUserIds").val(row["userId"]);
		$("#inputAssignTime").val(row["assignTime"]);
		initInputClientIds();
		if(row["clientIds"]!=null && row["clientIds"]!=""){
			var clientIds=row["clientIds"].split(",");
			$("#inputClientIds option[value='" + clientIds + "'] ").attr("selected", true);
		}
		$('#inputClientIds').multiselect('rebuild');
		$('#inputClientIds').multiselect('refresh');
		$('#divInputForm').modal();
	}
	
	//提交对象
	function postObject() {
	    var obj=new Object();
		obj.clientAssignId =$("#hiddenClientAssignId").val();
		obj.userId      =$("#inputUserIds").val();
		obj.clientIds    =$("#inputClientIds").val().join(";");  //用;分割clientId
		obj.assignTime  =$("#inputAssignTime").val();
		var jsonStr = JSON.stringify(obj);
		console.log("post ClientAssign:"+jsonStr);  
		var httpMethod="post";
		if($("#hiddenClientAssignId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		$.ajax({
			type : httpMethod,
			url : "../client/clientAssign/",
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
			url : "../client/clientAssign/"+deleteId,
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

	//初始化时间控件
	$('#queryAssignTimeBegin').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});
	$('#queryAssignTimeEnd').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});

</script>