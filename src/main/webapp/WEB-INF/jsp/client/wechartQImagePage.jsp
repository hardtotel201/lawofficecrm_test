<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="divInputForm" class="modal" tabindex="-1">
	<div class="modal-dialog" style="width: 800px; height: 600px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="blue bigger">请输入投放区域</h4>
			</div>
			<div class="modal-body">
				<div class="row widget-main">
					<form id="formInput" class="form-horizontal" role="form">
						<input type="hidden" id="hiddenUserId" />
						<div class="form-group">
							<label
								class="dialog-label col-sm-2 control-label no-padding-right"
								for="selectLocationName">投放区域(*)</label>
							<div class="col-sm-4">
								<select class="form-control col-xs-6"
									id="selectLocationName" name="selectLocationName" required>
									<option value=""></option>
									<c:forEach var="item" items="${locationNames}">
										<option value="${item}">${item}</option>
									</c:forEach>
								</select>
							</div>
							<label
								class="dialog-label col-sm-2 control-label no-padding-right"
								for="textuserCode">二 维 码</label>
							<div class="col-sm-4">
								<a id="linkQImage"> <img id="imgQImage" width="100px"
									height="100px" title="点击下载" style="display: none" />
								</a>
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
					<i class="ace-icon fa fa-check"></i> 生成
				</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$('#divInputForm').modal();
	});

	//保存信息
	$('#btnOk').on('click', function(e) {
		//窗口验证,成功提交数据
		if ($("#formInput").valid() == true) {
			var locationName = $("#selectLocationName").val();
			var strUrl = "../client/wechartQImage?locationName=" + locationName;
			console.log("request:" + strUrl);
			$.post(strUrl, function(fileName, status) {
				console.log(status + ":" + fileName);
				$("#linkQImage").attr("href", "../upload/" + fileName);
				$("#imgQImage").attr("src", "../upload/" + fileName);
				$("#imgQImage").show();
			});
		} else
			$("#imgQImage").hide();
		e.preventDefault(); //阻止默认事件Post
	});
</script>