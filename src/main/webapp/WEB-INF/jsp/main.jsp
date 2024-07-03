<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="lj.global.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="cn">
<head>
<base href="<%=request.getContextPath()%>/resources/" />
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta http-equiv="Pragma" content="no-cache" />
 <meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
 <meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>主页</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/font-awesome/4.5.0/css/font-awesome.min.css" />

<!-- page specific plugin styles -->
<link rel="stylesheet" href="assets/css/jquery-ui.custom.min.css" />
<link rel="stylesheet" href="assets/css/chosen.min.css" />
<link rel="stylesheet" href="assets/css/jquery.gritter.min.css" />
<link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" href="assets/css/bootstrap-duallistbox.min.css" />
<link rel="stylesheet" href="assets/css/bootstrap-multiselect.min.css" />
<link rel="stylesheet" href="assets/css/select2.min.css" type="text/css" />
<link rel="stylesheet" href="assets/css/fileinput.css" type="text/css" />


<!-- text fonts -->
<link rel="stylesheet" href="assets/css/fonts.googleapis.com.css" />

<!-- ace styles -->
<link rel="stylesheet" href="assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->

<!--[if lte IE 9]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

<!-- inline styles related to this page -->

<!-- ace settings handler -->
<script src="assets/js/ace-extra.min.js"></script>

<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

<!--[if lte IE 8]>
		<script src="assets/js/html5shiv.min.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
</head>

<body class="no-skin">
	<div id="navbar" class="navbar navbar-default">
		<script type="text/javascript">
			try {
				ace.settings.check('navbar', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="navbar-container" id="navbar-container">
			<button type="button" class="navbar-toggle menu-toggler pull-left"
				id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>
				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			
			<div class="navbar-header pull-left">
				<a href="<%=request.getContextPath()%>/main" class="navbar-brand">
					<small>
						<i class="fa fa-leaf"></i>
						${AppVar.systemName}
					</small>
				</a>
			</div>

			

			<div class="navbar-buttons navbar-header pull-right"
				role="navigation">
				<ul class="nav ace-nav">
					<!-- 用户菜单 -->
					<li class="light-blue">
					   <a data-toggle="dropdown" href="#" class="dropdown-toggle"> <img class="nav-user-photo"
							src="assets/images/avatars/user.jpg" alt="Jason's Photo" /> <span
							class="user-info"> <small>欢迎,</small> <c:out value='${sessionScope.userInfo.userCode}' />
						</span> <i class="ace-icon fa fa-caret-down"></i>
						</a>
						<ul
							class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
							<li><a href="javascript:changePassword()"> <i
									class="ace-icon fa fa-cog"></i> 帐号设置
							</a></li>
							<li class="divider"></li>
							<li><a href="javascript:exitSystem()"><i
									class="ace-icon fa fa-power-off"></i> 退出系统 </a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
		<!-- /.navbar-container -->
	</div>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div id="sidebar" class="sidebar  responsive">
			<script type="text/javascript">
				try {
					ace.settings.check('sidebar', 'fixed')
				} catch (e) {

				}
			</script>
			<ul class="nav nav-list">
				<li class=""><a
					href='javascript:showPageContent("../user/dashboard","平台概况")'>
						<i class="menu-icon fa fa-tachometer"></i> <span class="menu-text">
							平台概况</span>
				</a> <b class="arrow"></b></li>
				<c:forEach var="item" items="${parentModules}">
						<li class="">
						    <a data-toggle="dropdown" href="#" class="dropdown-toggle"> 
							    <i class="menu-icon fa fa-pencil-square-o"></i> 
							    <span class="menu-text">${item.moduleTitle}</span> 
							    <b class="arrow fa fa-angle-down"></b>
							</a> 
							<b class="arrow"></b>
							<ul class="submenu">
								<c:forEach var="child" items="${item.childModules}">
									<li class=""><a
										href='javascript:showPageContent("${child.moduleUrl}","${child.moduleTitle}")'>
											<i class="menu-icon fa fa-caret-right"></i>${child.moduleTitle}
									</a> <b class="arrow"></b></li>
								</c:forEach>
							</ul>
						</li>
				</c:forEach>
			</ul>
			<!-- /.nav-list -->

			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i class="ace-icon fa fa-angle-double-left"
					data-icon1="ace-icon fa fa-angle-double-left"
					data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>

			<script type="text/javascript">
				try {
					ace.settings.check('sidebar', 'collapsed')
				} catch (e) {
				}
			</script>
		</div>

		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i>主页
						</li>
						<li id="liModuleTitle" class="active"></li>
					</ul>
					<!-- /.breadcrumb -->
					<!-- /.nav-search -->
				</div>
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12" id="divPageContent">
							<!-- PAGE CONTENT BEGINS -->
							<!-- PAGE CONTENT ENDS -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<div class="footer">
			<div class="footer-inner">
				<div class="footer-content">
					<span class="bigger-120"> 
<%-- 					<%=AppVar.staticConfig.getSystemName()%> --%>
					</span>
				</div>
			</div>
		</div>

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>

	<div id="divChangePassword" class="modal" tabindex="-1">
		<div class="modal-dialog" style="width: 400px; height: 300px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="blue bigger">修改密码</h4>
				</div>
				<div class="modal-body">
					<div class="row widget-main">
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<div class="col-sm-12">
									<input id="textPassword" name="textPassword" type="password"
										class="form-control" placeholder="输入新密码" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<input id="textPasswordSecond" name="textPasswordSecond"
										type="password" class="form-control" placeholder="重复输入密码" />
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button id="btnChangePassword" type="button"
						class="width-65  btn btn-sm btn-success">
						<span class="bigger-110">确定</span> <i
							class="ace-icon fa fa-arrow-right icon-on-right"></i>
					</button>
				</div>
			</div>
		</div>
		<!-- /.widget-body -->
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->
	<script src="assets/js/jquery-2.1.4.min.js"></script>

	<!-- <![endif]-->

	<!--[if IE]>
<script src="assets/js/jquery.1.11.1.min.js"></script>
<![endif]-->

	<!--[if !IE]> -->
	<script type="text/javascript">
		window.jQuery
				|| document.write("<script src='assets/js/jquery.min.js'>"
						+ "<"+"/script>");
	</script>

	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery1x.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document
					.write("<script src='assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="assets/js/bootstrap.min.js"></script>
	<!-- page specific plugin scripts -->
	<script src="assets/js/jquery-ui.custom.min.js"></script>
	<script src="assets/js/jquery.gritter.min.js"></script>
	<script src="assets/js/moment.min.js"></script>
	<script src="assets/js/localization/moment.zh-CN.js" charset="UTF-8"></script>
	<script src="assets/js/bootstrap-datetimepicker.min.js"></script>
	<script src="assets/js/jquery.bootstrap-duallistbox.min.js"></script>
	<script src="assets/js/bootstrap-multiselect.min.js"></script>
	<script src="assets/js/select2.min.js"></script>
    <script src="assets/js/chosen.jquery.min.js"></script>
    <script src="assets/layer/layer.js"></script>
    <script src="assets/js/fileinput.js"></script>
	<script src="assets/js/fileinput-app.js"></script>
	<script src="assets/js/localization/fileinput_locale_zh.js"></script>
	<script src="assets/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/jquery.dataTables.bootstrap.min.js"></script>
    <script src="assets/js/dataTables.buttons.min.js"></script>
	<script src="assets/js/buttons.flash.min.js"></script>
	<script src="assets/js/buttons.html5.min.js"></script>
	<script src="assets/js/buttons.print.min.js"></script>
	<script src="assets/js/buttons.colVis.min.js"></script>
	<script src="assets/js/dataTables.select.min.js"></script>
	<script src="assets/js/jquery.validate.min.js"></script>
	<script src="assets/js/localization/messages_zh.js"></script>
    <script src="assets/js/jquery.flot.min.js"></script>
	<script src="assets/js/jquery.flot.pie.min.js"></script>
	<script src="assets/js/jquery.flot.resize.min.js"></script>
	<script src="assets/js/jquery.flot.time.js"></script>
<!-- 	<script src="assets/gojs1.7.27/go-debug.js"></script> -->
<!-- 	<script src="assets/js/echarts.min.js"></script> -->
	<!-- ace scripts -->
	<script src="assets/js/ace-elements.min.js"></script>
	<script src="assets/js/ace.min.js"></script>
<!-- 	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RVEgyAItG3Nf7nfoKaRQ7NnNkGNjgz3E"></script>   -->
<!--     如果需要拖拽鼠标进行操作，可引入以下js文件 -->
<!--     <script type="text/javascript" src="http://api.map.baidu.com/library/RectangleZoom/1.2/src/RectangleZoom_min.js"></script>  -->
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	
	    var hisUrls=[];
	    var hisTitles=[];
		function showPageContent(url, title,isBack=false) {
			if(url.indexOf("siteViewPage")>=0){
			    var iTop = (window.screen.height-30-720)/2;         //获得窗口的垂直位置;
			    var iLeft = (window.screen.width-10-1366)/2;        //获得窗口的水平位置;
			    var newUrl="../task/siteViewPage?rand="+(new Date()).getTime();
			    window.open ( newUrl, "siteViewPage",
				    "width=1366, height=720, left="+iLeft+", top="+iTop);
			    return;
			}
			$.get(url, function(result) {
				//返回登录页面
				if (url.indexOf("login") >= 0) {
					console.log("return to login");
					window.location.href = "../login";
				}
				//填充模块页面
				else {
					//填充页面
					$("#liModuleTitle").html(title);
					$('#divPageContent').html(result);
					//替换历史
					if(isBack==false){
						var strUrl="<%=request.getContextPath()%>/main?random=" + Math.random();
						history.pushState({"lastUrl":url,"lastTitle":title},null,strUrl);
						hisUrls[hisUrls.length]=url;
						hisTitles[hisTitles.length]=title;
					}
				}
			});
		}

		//修改密码
		function changePassword() {
			$('#divChangePassword').modal();
		}

		jQuery(function($) {
		    console.log("request.getContextPath():<%=request.getContextPath()%>");
			//修改密码
			$('#btnChangePassword').on('click',
				function(e) {
					if ($("#textPassword").val() == "") {
						alert("新密码不能为空！")
						e.preventDefault();
						return;
					}
					if ($("#textPasswordSecond").val() == "") {
						alert("重复输入密码不能为空！")
						e.preventDefault();
						return;
					}
					if ($("#textPasswordSecond").val() != $(
							"#textPassword").val()) {
						alert("两个密码必须相同！")
						e.preventDefault();
						return;
					}

					var strData = "userPassword="
							+ $("#textPassword").val();
					//alert(strData);
					$.ajax({
						type : "post",
						url : "../user/userInfo/changeUserPassword",
						data : strData,
						datatype : "json",
						async : true, //默认为true 异步   
						error : function() {
							alert('提交失败!');
						},

						success : function(data) {
							$('#divChangePassword').modal(
									"hide");
							if (data == null || data == "") {
								$.gritter
										.add({
											title : '成功',
											text : '修改密码成功!',
											//sticky: false,  
											time : 2000,
											//speed:500,  
											position : 'bottom-right',
											class_name : 'gritter-sucess'
										});
							} else
								$.gritter
										.add({
											title : '修改密码失败',
											text : data,
											//sticky: false,  
											time : 2000,
											//speed:500,  
											position : 'bottom-right',
											class_name : 'gritter-error'
										});
						}
					});
				});
			//9-进入dashboard页面
			showPageContent("../user/dashboard","平台概况");
		});

		//退出系统
		function exitSystem() {
			$.ajax({
				type : "post",
				url : "../exitSystem",
				datatype : "json",
				async : true, //默认为true 异步   
				error : function() {
				    window.location.href = "<%=request.getContextPath()%>";
				},

				success : function(data) {
					$('#divChangePassword').modal("hide");
					if (data == null || data == "") {
						$.gritter.add({
							title : '成功',
							text : '退出系统成功!',
							//sticky: false,  
							time : 2000,
							//speed:500,  
							position : 'bottom-right',
							class_name : 'gritter-sucess'
						});
						//跳转到登录页面
						window.location.href = "<%=request.getContextPath()%>";
					} else
						$.gritter.add({
							title : '修改密码失败',
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
		
		var dataTableLanguage= {
			"sProcessing" : "处理中...",
			"sLengthMenu" : "显示 _MENU_ 项结果",
			"sZeroRecords" : "没有匹配结果",
			"sInfo" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
			"sInfoEmpty" : "显示第 0 至 0 项结果，共 0 项",
			"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
			"sInfoPostFix" : "",
			"sSearch" : "搜索:",
			"sUrl" : "",
			"sEmptyTable" : "表中数据为空",
			"sLoadingRecords" : "载入中...",
			"sInfoThousands" : ",",
			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : "上页",
				"sNext" : "下页",
				"sLast" : "末页"
			},
			"oAria" : {
				"sSortAscending" : ": 以升序排列此列",
				"sSortDescending" : ": 以降序排列此列"
			}
		};
		
		
		
		//过滤DataTable请求参数,附加分页参数
		function filterRequest(req)
		{
		    req.pageSize=req.length;
		    req.pageIndex=req.start/req.length;
		    req.sortType="";
		    req.sortField="";
		    if(req.order==null || req.order.length<=0)
				return;
		    req.sortType =req.order[0].dir;
		    req.sortField=req.columns[req.order[0].column].data;
		}
		
		//初始化TableTools
		function initTableTools(dataTableObj){
			//2-设置TableTools
			$.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';
			new $.fn.dataTable.Buttons( dataTableObj, {
				buttons: [
				  {
					"extend": "copy",
					"text": "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>Copy to clipboard</span>",
					"className": "btn btn-white btn-primary btn-bold"
				  },
				  {
					"extend": "csv",
					"text": "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>Export to CSV</span>",
					"className": "btn btn-white btn-primary btn-bold"
				  }	  
				]
			} );
			dataTableObj.buttons().container().appendTo( $('.tableTools-container') );
			//3-初始化TableTools extension
			dataTableObj.on( 'select', function ( e, dt, type, index ) {
				if ( type === 'row' ) {
					$( dataTableObj.row( index ).node() ).find('input:checkbox').prop('checked', true);
				}
			} );
			dataTableObj.on( 'deselect', function ( e, dt, type, index ) {
				if ( type === 'row' ) {
					$( dataTableObj.row( index ).node() ).find('input:checkbox').prop('checked', false);
				}
			} );
			/////////////////////////////////
			//table checkboxes
			$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);
			
			//select/deselect all rows according to table header checkbox
			$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				
				$('#dynamic-table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) dataTableObj.row(row).select();
					else  dataTableObj.row(row).deselect();
				});
			});
			//select/deselect a row when the checkbox is checked/unchecked
			$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
				var row = $(this).closest('tr').get(0);
				if(this.checked) dataTableObj.row(row).deselect();
				else dataTableObj.row(row).select();
			});
			$(document).on('click', '#dynamic-table .dropdown-toggle', function(e) {
				e.stopImmediatePropagation();
				e.stopPropagation();
				e.preventDefault();
			});
		}
		
		
		var datetimepickerSetting={
			format : "YYYY-MM-DD HH:mm:ss",
		};
		
		function showSuccess(msg){
		    $.gritter.add({
				title : '成功',
				text :msg,
				//sticky: false,  
				time : 2000,
				//speed:500,  
				position : 'bottom-right',
				class_name : 'gritter-sucess'
			});
		}
		
		function showError(msg){
// 		    $.gritter.add({
// 				title : '失败',
// 				text : msg,
// 				//sticky: false,  
// 				time : 2000,
// 				//speed:500,  
// 				position : 'bottom-right',
// 				class_name : 'gritter-error'
// 			});
		    layer.alert(msg);
		}
		
		//格式化时间
		function timeToString(fmt, date) {
		    let ret;
		    const opt = {
		        "Y+": date.getFullYear().toString(),        // 年
		        "M+": (date.getMonth() + 1).toString(),     // 月
		        "D+": date.getDate().toString(),            // 日
		        "H+": date.getHours().toString(),           // 时
		        "m+": date.getMinutes().toString(),         // 分
		        "s+": date.getSeconds().toString(),         // 秒
		        // 有其他格式化字符需求可以继续添加，必须转化成字符串
		        "y+": date.getFullYear().toString(),        // 年
		    };
		    for (let k in opt) {
		        ret = new RegExp("(" + k + ")").exec(fmt);
		        if (ret) {
		            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
		        };
		    };
		    return fmt;
		}
		
		
		
		//初始化bootstrap-multiselect控件
		function initMultiSelect(){
		    $('.multiselect').multiselect({
				 enableFiltering: true,
				 enableHTML: true,
				 buttonClass: 'btn btn-white btn-primary',
				 templates: {
				    nonSelectedText: '请选择',
				    button: '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown"><span class="multiselect-selected-text"></span> &nbsp;<b class="fa fa-caret-down"></b></button>',
					ul: '<ul class="multiselect-container dropdown-menu"></ul>',
					filter: '<li class="multiselect-item filter"><div class="input-group"><span class="input-group-addon"><i class="fa fa-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
					filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default btn-white btn-grey multiselect-clear-filter" type="button"><i class="fa fa-times-circle red2"></i></button></span>',
					li: '<li><a tabindex="0"><label></label></a></li>',
			        divider: '<li class="multiselect-item divider"></li>',
			        liGroup: '<li class="multiselect-item multiselect-group"><label></label></li>'
				 }
			});
		}
		
		//初始化JQuery Chosen组件
		function initJQueryChosen(){
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			//resize the chosen on window resize
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': 200});
				})
			}).trigger('resize.chosen');
		}
	
		
		//获得查询参数
		function getQueryVariable(variable){
	       var query = window.location.search.substring(1);
	       var vars = query.split("&");
	       for (var i=0;i<vars.length;i++) {
	               var pair = vars[i].split("=");
	               if(pair[0] == variable){return pair[1];}
	       }
	       return(false);
		}
		
		//回退事件处理
		$(window).on('popstate', function(event) {
			for(var i in hisTitles)
				console.log("history title:"+hisTitles[i]);
			var url  =hisUrls[hisUrls.length-2];
			var title=hisTitles[hisTitles.length-2];
			hisUrls.pop();
			hisTitles.pop();
			showPageContent(url, title,true);
		});

	</script>
</body>
</html>
