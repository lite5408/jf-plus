<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户编辑</title>
	<%@ include file="../../include/head.jsp"%>
	<style type="text/css">
		.tpl-content-wrapper{margin-left:0}
	</style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<div class="am-g tpl-g">
	<!-- 内容区域 -->
	<div class="tpl-content-wrapper">
		<div class="row-content am-cf">
			<div class="row">
				<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
					<div class="widget am-cf">
						<div class="widget-head am-cf">
							<div class="widget-title am-fl">修改密码</div>
						</div>
						<div class="widget-body am-fr">
							<form class="am-form tpl-form-border-form" data-am-validator action="${ctx}/user/${id}/changePassword" method="post">
								<div class="am-form-group">
									<label class="am-u-sm-3 am-form-label">新密码：</label>
									<div class="am-u-sm-9">
										<input type="password" name="newPassword" placeholder="新密码" required />
									</div>
								</div>
								<div class="am-form-group">
									<div class="am-u-sm-9 am-u-sm-push-3">
										<button type="submit" class="am-btn am-btn-xs am-btn-primary">修改密码</button>
									</div>
								</div>
							</form>
						</div>
						<div class="widget-head am-cf">
							<div class="widget-title am-fl">微信绑定</div>
						</div>
						<div class="widget-body am-fr">
							<form class="am-form tpl-form-border-form" data-am-validator action="${ctx}/user/${id}/cancelWxBind" method="post">
								<div class="am-form-group">
									<label class="am-u-sm-3 am-form-label">是否绑定：</label>
									<div class="am-u-sm-9 am-text-warning">
										${empty isWxBind ? '否':'是'}
									</div>
								</div>
								<div class="am-form-group">
									<div class="am-u-sm-9 am-u-sm-push-3">
										<c:if test="${not empty isWxBind }">
											<button type="submit" class="am-btn am-btn-xs am-btn-primary">解除绑定</button>
										</c:if>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		var msg = '${msg}';
		if(msg!=''){
			showMsg(msg);
			closeModel(true);//关闭窗口
		}
	});
</script>
</body>
</html>