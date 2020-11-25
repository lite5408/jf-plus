<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>确认收货平台</title>
<%@ include file="/WEB-INF/view/mall/mallSupplyer/front/common/head.jsp"%>
<link rel="stylesheet" href="${ctxStatic}/css/amazeui.min.css"
	type="text/css" />
<link rel="stylesheet" href="${ctxStatic}/css/common_login.css" />
</head>
<body>
	<form id="submitLoginForm">
		<div class="zswap">
			<div class="zslogo">
				<img src="${ctxStatic}/images/zlogo.jpg" width="160" />
			</div>

			<div class="zslogin">
				<div class="zsrows">
					<span class="zsicon"></span> <input type="text" id="username"
						name="username" placeholder="输入账号" />
				</div>
				<div class="zsrows">
					<span class="zsicon zspassword-icon"></span> <input type="password"
						id="password" name="password" placeholder="输入密码" />
				</div>
				<div class="m-t-30">
					<button type="button" class="btnlogin" onclick="loginFun()">登录</button>
				</div>
			</div>

		</div>
	</form>
<script type="text/javascript">
	function loginFun() {
		if ($.trim($('#username').val()) == '') {
			alert('请输入用户名');
			return false;
		}
		if ($.trim($('#password').val()) == '') {
			alert('请输入密码');
			return false;
		}
	
		$.post("${ctxS}/loginSupply", {
			username : $.trim($('#username').val()),
			password : $.trim($('#password').val()),
			loginType : "SUPPLY"
		}, function(result) {
			if (result.success) {
				window.location.href = "${ctxS}/mobile/index";
			} else {
				alert(result.msg);
			}
		});
	}
</script>
</body>