<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="formQuery" name="formQuery" >
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right"
					for="textQueryroleName">角色名称</label>
				<div class="col-sm-4">
					<input type="text" id="textQueryRoleName" name="textQueryRoleName"
						class="input-text col-xs-12" />
				</div>
				<div style="text-align: right; float: right;">
					<button id="btnQuery" class="btn btn-purple btn-sm">查询</button>
				</div>
			</div>
		</form>
	</div>
</div>
<div class="hr hr-18 dotted hr-double"></div>
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
				<th>角色名称</th>
				<th>角色说明</th>
				<th class="center">角色权限定义</th>
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
						<input type="hidden" id="hiddenRoleId" />
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right"
								for="textroleName">角色名称</label>
							<div class="col-sm-4">
								<div class="form-group col-md-12">
									<input type="text" id="textRoleName" name="textRoleName"
										required />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right"
								for="textroleMemo">角色说明</label>
							<div class="col-sm-4">
								<textarea id="textRoleMemo" name="textRoleMemo"
									style="width: 300px"></textarea>
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

<div id="divRolPrivs" class="modal" tabindex="-1">
	<div class="modal-dialog" style="width: 1200px; height: 800px">
		<div class="modal-content">
			<div class="modal-header">
				<label id="labelRoleName"></label><button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-5">
						<form id="formRolePrivs" class="form-horizontal" role="form">
							<c:forEach var="item" items="${parentModules}"  >
								<h5 class="header smaller lighter blue">${item.moduleTitle}</h5>
								<div class="form-group">
									<c:forEach var="child" items="${item.childModules}" varStatus="s" >
										<label> <input name="checkboxModuleId"
												value="${child.moduleId}" type="checkbox" class="ace" /> <span
												class="lbl">${child.moduleTitle}</span>
										</label>&nbsp;&nbsp;&nbsp;&nbsp;
									</c:forEach>
								</div>
							</c:forEach>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button id="btnRolPrivsCancel" class="btn btn-sm"
					data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i> 取消
				</button>
				<button id="btnRolPrivsOk" class="btn btn-success btn-sm">
					<i class="ace-icon fa fa-check"></i> 保存
				</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    //1-声明表格对象
    var dataTableObj = null;

    $(document).ready(function() {
    	//1-设置DataTable列
	    var columnSettings=[{
			"data" : "roleId",
			"sClass":"font-title",
			"orderable" : false,
			"searchable" : false
		    }, {
			"data" : "roleName",
			"searchable" : false
		    }, {
			"data" : "roleMemo",
			"searchable" : false
		    }, {
			"data" : "roleId",
			"orderable" : false,
			"searchable" : false
		    }, {
			"data" : "roleId",
			"orderable" : false,
			"searchable" : false
		    } 
		];
	    //2-定义特殊选择列和超链接列
	    var columnDefs=[{
				"targets" : 0,
				"data" : "roleId",
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
				"targets" : 3,
				"data" : "roleId",
				"render" : function(
					data, type,
					row, meta) {
				    var str = '<div class="center" >';
				    str = str
					    + '<a href="javascript:rolePrivs('
					    + meta.row
					    + ')" >角色权限</a>';
				    str = str
					    + '</div>'
				    return str;
				}
		    },
		    {
				"targets" : 4,
				"data" : "roleId",
				"render" : function(
					data, type,
					row, meta) {
				    var str = '<div class="center" >';
				    str = str
					    + '<a href="javascript:editObject('
					    + meta.row
					    + ')" >修改</a>';
				    str = str
					    + '</div>'
				    return str;
				}
		    }
	    	
	    ];
	    //3-初始化数据表格
		dataTableObj = $('#dynamic-table').DataTable({
			"serverSide" : true, //服务器处理：过滤、分页、排序
			"processing" : true, //是否显示处理状态
			"ajax" : {
					url : '../user/roleInfo',
					type : 'get',
					data : function(d) {
					    d.roleName = $("#textQueryRoleName").val();
					    //加上排序字段和排序方式
					    if(d.order!=null && d.order.length>0)
						{
						    var columnIndex=d.order[0].column;
						    d.sortField=d.columns[columnIndex].data;
						    d.sortType =d.order[0].dir;
						}
					    //默认排序字段和排序方式
					    else{
						    d.sortField="roleName";
						    d.sortType ="asc";
					    }
					},
			    },//获取数据的处理函数 
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
	    $("#hiddenRoleId").val(-1);
	    $("#textRoleName").val("");
	    $("#textRoleMemo").val("");
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

	//保存权限设置信息
	$('#btnRolPrivsOk').on('click', function(e) {
	    postRolePrivs();
	    e.preventDefault(); //阻止默认事件Post

	});
    });

    //修改对象
    function editObject(rowIndex) {
		if (dataTableObj.data().length <= 0)
		    return;
		if (rowIndex < 0)
		    return;
		var row = dataTableObj.data()[rowIndex];
		$("#hiddenRoleId").val(row["roleId"]);
		$("#textRoleName").val(row["roleName"]);
		$("#textRoleMemo").val(row["roleMemo"]);
		$('#divInputForm').modal();
    }

    //提交对象
    function postObject() {
    	var obj=new Object();
		obj.roleId  =$("#hiddenRoleId").val();
		obj.roleName=$("#textRoleName").val();
		obj.roleMemo=$("#textRoleMemo").val();
		var jsonStr = JSON.stringify(obj);
		console.log("postObject json:"+jsonStr);

		$.ajax({
		    type : "put",
		    url : "../user/roleInfo",
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
    function deleteObject(id) {
		var strData = "roleId=" + id;
		$.ajax({
		    type : "post",
		    url : "../user/roleInfo/delete",
		    data : strData,
		    datatype : "json",
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

    //角色权限定义
    function rolePrivs(rowIndex) {
		if (dataTableObj.data().length <= 0)
		    return;
		if (rowIndex < 0)
		    return;
		var row = dataTableObj.data()[rowIndex];
		var strData = "roleId=" + row["roleId"];
		$("#labelRoleName").html(row["roleName"]);
		$.ajax({
		    type : "post",
		    url : "../user/rolePrivs/getAllByRoleId",
		    data : strData,
		    datatype : "json",
		    async : false, //默认为true 异步   
		    error : function() {
				alert('提交失败!');
		    },
		    success : function(data) {
				var optionalModuleIds = $("input[name='checkboxModuleId']");
				for ( var i in optionalModuleIds) {
				    optionalModuleIds[i].checked = false;
				    for (var j in data)
						if (data[j]["moduleId"] == optionalModuleIds[i].value) {
						    optionalModuleIds[i].checked = true;
						    break;
						}
				}
				$("#hiddenRoleId").val(row["roleId"]);
				$("#divRolPrivs").modal();
		    }
		});
    }

    function postRolePrivs() {
		var strData = "roleId=" + $("#hiddenRoleId").val();
		var optionalModuleIds = $("input[name='checkboxModuleId']");
		for ( var index in optionalModuleIds)
		    if (optionalModuleIds[index].checked == true) {
			strData = strData + "&moduleIds="
				+ optionalModuleIds[index].value;
		    }
		//alert(strData);重复提交问题已经解决
		$.ajax({
		    type : "post",
		    url : "../user/rolePrivs/postRolePrivs",
		    data : strData,
		    datatype : "json",
		    async : false, //默认为true 异步   
		    error : function() {
			alert('提交失败!');
		    },
	
		    success : function(data) {
			$('#divRolPrivs').modal("hide");
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