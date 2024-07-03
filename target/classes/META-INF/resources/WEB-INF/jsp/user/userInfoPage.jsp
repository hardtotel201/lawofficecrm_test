<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 查询条件 -->
<div class="bg-query-head" style="font-size:16px;color:#00BFF4">&nbsp;查询条件</div>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
				<label class="font-title col-sm-1 control-label no-padding-right"
					for="textQueryuserName">姓名</label>
				<div class="col-sm-2">
					<input type="text" id="queryUserName" name="queryUserName" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right"
					for="textQueryuserCode">登录名</label>
				<div class="col-sm-2">
					<input type="text" id="queryUserCode" name="queryUserCode" class="input-text col-xs-12" />
				</div>
				<label class="font-title col-sm-1 control-label no-padding-right" 
				       for="queryRegisterTimeBegin">注册时间</label>
				<div class="col-sm-3 input-append date form_datetime">
					<input type="text" id="queryRegisterTimeBegin" name="queryRegisterTimeBegin" size="20" type="text" /> 
					<span class="addon"> <i class="fa fa-clock-o"></i></span> 到 
					<input type="text" id="queryRegisterTimeEnd" name="queryRegisterTimeEnd"" size="20" type="text" /> 
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
<div class="clearfix">
	<div class="pull-right tableTools-container"></div>
</div>
<!-- div.dataTables_borderWrap -->
<!-- 数据表格 -->
<div class="bg-table-header table-header" >查询结果</div>
<table id="dynamic-table" class="table table-hover">
	<thead>
		<tr>
			<th class="bg-table-header center">
				<label class="pos-rel"> 
				    <input type="checkbox" class="ace" /><span class="lbl"></span>
				</label>
			</th>
			<th class="bg-table-header">姓名</th>
			<th class="bg-table-header">登录名</th>
			<th class="bg-table-header">用户手机</th>
			<th class="bg-table-header">微信</th>
			<th class="bg-table-header">注册时间</th>
			<th class="bg-table-header">最近登录时间</th>
			<th class="bg-table-header">用户状态</th>
			<th class="bg-table-header">操作时间</th>
			<th class="bg-table-header">操作人</th>
			<th class="bg-table-header center">用户角色</th>
			<th class="bg-table-header center">操作</th>
		</tr>
	</thead>
	<tfoot></tfoot>
</table>
<div id="divInputForm" class="modal" tabindex="-1">
	<div class="modal-dialog" style="width:1000px;height:750px" >
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="blue bigger">请输入</h4>
			</div>
			<div class="modal-body">
				<div class="row widget-main">
					<form id="formInput" class="form-horizontal" role="form">
						<input type="hidden" id="hiddenUserId" />
						<div class="form-group">
							<label class="dialog-label col-sm-2 control-label no-padding-right"
								for="textUserName">姓名(*)</label>
							<div class="col-sm-4">
								<input type="text" id="textUserName" name="textUserName"  
								        class="input-text dialog-control"  required />
							</div>
							<label class="dialog-label col-sm-2 control-label no-padding-right"
								for="textuserCode">登录名(*)</label>
							<div class="col-sm-4">
								<input type="text" id="textUserCode" name="textUserCode"
								        class="input-text dialog-control"  required />
							</div>
						</div>
						<div class="form-group">
							<label class="dialog-label col-sm-2 control-label no-padding-right"
								for="selectUserSex">用户性别</label>
							<div class="col-sm-4">
								<select id="selectUserSex" name="selectUserSex" 
								        class="input-text dialog-control" >
									<option>男</option>
									<option>女</option>
								</select>
							</div>
							<label class="dialog-label col-sm-2 control-label no-padding-right"
								for="inputRoleIds">用户角色</label>
							<div class="col-sm-4">
								<select  id="inputRoleIds" name="inputRoleIds"  class="multiselect" multiple="" required >
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="dialog-label col-sm-2 control-label no-padding-right"
								for="textUserMobile">用户手机</label>
							<div class="col-sm-4">
								<input type="text" id="textUserMobile" name="textUserMobile"
								        class="input-text dialog-control" />
							</div>
							<label class="dialog-label col-sm-2 control-label no-padding-right"
								for="textUserWeixin">用户微信</label>
							<div class="col-sm-4">
								<input type="text" id="textUserWeixin" name="textUserWeixin"
								        class="input-text dialog-control" />
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="footer-dialog modal-footer">
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
<div id="divUserRoles" class="modal" tabindex="-1">
	<div class="modal-dialog" style="width: 600px; height: 800px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-5">
						<form id="formUserRoles" class="form-horizontal" role="form">
							<h5 class="header smaller lighter blue">可选角色</h5>
							<div class="form-group">
								<c:forEach var="item" items="${roleInfos}">
									<div class="checkbox">
										<label> <input name="checkboxRoleId"
											value="${item.roleId}" type="checkbox" class="ace" /> <span
											class="lbl">${item.roleName}</span>
										</label>
									</div>
								</c:forEach>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button id="btnUserRolesCancel" class="btn btn-sm"
					data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i> 取消
				</button>
				<button id="btnUserRolesOk" class="btn btn-success btn-sm">
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
	    var columnSettings= [ {
			"data" : "userId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		}, 
		{
			"data" : "userName",
			"sClass":"font-title",
			"searchable" : false
		}, 
		 {
			"data" : "userCode",
			"sClass":"font-title",
			"searchable" : false
		}, 
		{
			"data" : "userMobile",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "userWeixin",
			"sClass":"font-title",
			"searchable" : false
		},
		{
			"data" : "registerTime",
			"sClass":"font-title",
			"searchable" : false
		}, {
			"data" : "lastLoginTime",
			"sClass":"font-title",
			"searchable" : false
		}, {
			"data" : "userState",
			"sClass":"font-title",
			"searchable" : false
		}, {
			"data" : "opTime",
			"sClass":"font-title",
			"searchable" : false
		}, {
			"data" : "opUserName",
			"sClass":"font-title",
			"searchable" : false
		}, {
			"data" : "userId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		}, {
			"data" : "userId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		} ];
	    //2-定义特殊选择列和超链接列
	    var columnDefs=[
			{
				"targets" : 0,
				"data" : "userId",
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
				"data" : "userId",
				"render" : function(data, type,row, meta) {
				    var userRoleNames=row["userRoleNames"];
				    if(userRoleNames==null || userRoleNames=="")
						userRoleNames="未定义";
					var str = '<div class="bg-table-header center" >';
					str = str
							+ '<a href="javascript:userRoles('
							+ meta.row
							+ ')" >'+userRoleNames+'</a>';
					str = str
							+ '</div>'
					return str;
				}
			},
			{
				"targets" :  columnSettings.length-1,
				"data" : "userId",
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
	    	
	    //3-初始化数据表格
		dataTableObj = $('#dynamic-table').DataTable({
			"serverSide" : true, //服务器处理：过滤、分页、排序
			"processing" : true, //是否显示处理状态
			"ajax" : {
				url : '../user/userInfo/',
				type : 'get',
				data : function(req) {
				    //1-过滤参数(设置分页参数)
				    filterRequest(req);
				    //2-设置查询条件
				    if($("#queryUserName").val()!="")
						req.userName=$("#queryUserName").val();
				    if($("#queryUserCode").val()!="")
						req.userCode=$("#queryUserCode").val();
				    if($("#queryRegisterTimeBegin").val()!="")
					    req.registerTimeBegin=$("#queryRegisterTimeBegin").val();
				    if($("#queryRegisterTimeEnd").val()!="")
					    req.registerTimeEnd=$("#queryRegisterTimeEnd").val();
				},
			}, //获取数据的处理函数 
			"searching" : false,//搜索功能
			"bSort" : true, //排序功能
			"aaSorting" : [],
			"columns" :columnSettings,
			"columnDefs" : columnDefs,
			"bFilter" : false, //过滤功能
			"bPaginate" : true, //翻页功能
			"sPaginationType" : "full_numbers", //分页的格式
			//"iDisplayLength" : 5, //每页显示的数据行数
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
	    
		//5-设置窗口验证
		$("#formInput").validate();
		
	});
	

	//重新加载数据
	function refreshData() {
	    console.log("userInfo.refreshData");
		dataTableObj.ajax.reload(null, true);
	}
	
	//初始化多选控件
	function initInputRoleIds(){
	    $('#inputRoleIds').empty();
	    var strRoleIds="${sessionScope.userInfo.roleIds}";
	    console.log("initInputRoleIds() strRoleIds:"+strRoleIds);
	    <c:forEach var="item" items="${roleInfos}">
			$('#inputRoleIds').append($('<option></option>').text("${item.roleName}").val(${item.roleId}));
		</c:forEach>
	    $('#inputRoleIds').multiselect('rebuild');
        $('#inputRoleIds').multiselect('refresh');
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
			$("#hiddenUserId").val(-1);
			$("#textUserName").val("");
			$("#textUserCode").val("");
			$("#textUserMobile").val("");
			$("#textUserWeixin").val("");
			$("#selectUserSex").val("");
			initInputRoleIds();
	        $('#inputRoleIds').multiselect('rebuild');
	        $('#inputRoleIds').multiselect('refresh');
			$('#divInputForm').modal();
			e.preventDefault();
		});
		
		//保存信息
		$('#btnOk').on('click', function(e) {
			//2-窗口验证,成功提交数据
			if ($("#formInput").valid() == true) {
			    if($("#inputRoleIds").val()==null || $("#inputRoleIds").val()==""){
					showError("请选择用户角色!");
					$("#inputRoleIds").focus();
					return;
			    }
			    console.log("inputRoleIds:"+$("#inputRoleIds").val());
				postObject();
				e.preventDefault(); //阻止默认事件Post
			}
		});
		//保存用户权限信息
		$('#btnUserRolesOk').on('click', function(e) {
			postUserRoles();
			e.preventDefault(); //阻止默认事件Post
		});
	});

	//修改对象
	function editObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		$("#labelRoleIds").hide();
	 	$("#divRoleIds").hide();
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenUserId").val(row["userId"]);
		$("#textUserName").val(row["userName"]);
		$("#textUserCode").val(row["userCode"]);
		$("#textUserMobile").val(row["userMobile"]);
		$("#textUserWeixin").val(row["userWeixin"]);
		$("#selectUserSex").val(row["userSex"]);     
		initInputRoleIds();
        if(row["roleIds"]!=null && row["roleIds"]!=""){
            var roleIds=row["roleIds"].split(",");
            for(var index in roleIds){
        	    $("#inputRoleIds option[value='" + roleIds[index] + "'] ").attr("selected", true);
            }
        }
        $('#inputRoleIds').multiselect('rebuild');
        $('#inputRoleIds').multiselect('refresh');
		$('#divInputForm').modal();
	}

	//提交对象
	function postObject() {
		var obj=new Object();
		obj.userId=$("#hiddenUserId").val();
		obj.userName=$("#textUserName").val();
		obj.userCode=$("#textUserCode").val();
		obj.userSex=$("#selectUserSex").val();
		obj.userMobile=$("#textUserMobile").val();
		obj.userWeixin=$("#textUserWeixin").val();
		obj.roleIds=$("#inputRoleIds").val().join(",");       //用户角色标识赋值
		var jsonStr = JSON.stringify(obj);
		//console.log(jsonStr);
		var httpMethod="post";
		if($("#hiddenUserId").val()<=0)
		  httpMethod="post";
		else
		  httpMethod="put";
		$.ajax({
			type: httpMethod,
			url : "../user/userInfo",
			data : jsonStr,
			contentType : "application/json; charset=utf-8",
			async : false, //默认为true 异步   
			error : function() {
				alert('提交失败!');
			},
			success : function(data) {
				$('#divInputForm').modal("hide");
				if (data == null || data == "") {
				    console.log("success----------------");
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
			url : "../user/userInfo/"+deleteId,
			error : function() {
				alert('提交失败!');
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

	$('#textRegisterTime').datetimepicker({
		format : "YYYY-MM-DD HH:mm:ss"
	}).on('changeDate', function(ev) {
		$(this).datetimepicker('hide');
	});

	function userRoles(rowIndex) {
		if (dataTableObj.data().length <= 0)
			return;
		if (rowIndex < 0)
			return;
		var row = dataTableObj.data()[rowIndex];
		var strData = "userId=" + row["userId"];
		//alert(strData);
		$.ajax({
			type : "post",
			url : "../user/userRoles/getAllByUserId",
			data : strData,
			datatype : "json",
			async : false, //默认为true 异步   
			error : function() {
				alert('提交失败!');
			},
			success : function(data) {
				var optionalRoleIds = $("input[name='checkboxRoleId']");
				for ( var i in optionalRoleIds) {
					optionalRoleIds[i].checked = false;
					for ( var j in data)
						if (data[j]["roleId"] == optionalRoleIds[i].value) {
							optionalRoleIds[i].checked = true;
							break;
						}
				}
				$("#hiddenUserId").val(row["userId"]);
				$("#divUserRoles").modal();
			}
		});
	}
	
	function postUserRoles() {
		var strData = "userId=" + $("#hiddenUserId").val();
		var optionalRoleIds= $("input[name='checkboxRoleId']");
		for ( var index in optionalRoleIds)
			if (optionalRoleIds[index].checked == true) {
				strData = strData + "&roleIds="+ optionalRoleIds[index].value;
			}
		//alert(strData);
		$.ajax({
			type : "post",
			url : "../user/userRoles/postUserRoles",
			data : strData,
			datatype : "json",
			async : false, //默认为true 异步   
			error : function() {
				alert('提交失败!');
			},
			success : function(data) {
				$('#divUserRoles').modal("hide");
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
					//刷新数据
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
</script>