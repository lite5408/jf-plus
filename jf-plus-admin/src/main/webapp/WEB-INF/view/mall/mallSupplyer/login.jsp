<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head lang="en">
<script>
	if (window.top !== window.self) {
		window.top.location = window.location;
	}
</script>
<meta charset="UTF-8">
<title>${fnc:getConfig("iutils.name")}</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css"
	href="${ctxStatic }/login/css/style.css?v=1.0.1">
<script src="${ctxStatic }/3rd-lib/jquery/1.9.0/jquery.min.js"></script>
</head>

<body>
	<div class="header w1200">
		<img src="${ctxStatic }/login/images/logo1.png" style="height: 30px;" alt="">
		<span class="logo-name"></span> <img
			src="${ctxStatic }/login/images/logo2.png" style="height: 30px;" alt="">
		<span class="logo-name">招银云采平台</span> <span class="fr toptel">客服电话：400-9699-027</span>
	</div>
	<div class="content loginWarp">
		<div
			style="background: url(${ctxStatic }/login/images/abc.jpg) no-repeat center center; height: 500px;">
			<div class="main w1400 clearfix">
				<div class="login-box">
					<h3>
						<span style="border:none;">供应商登录</span>
					</h3>

					<!--机构登陆-->
					<div class="tabbox" style="display: block;">
						<div class="row">
							<i class="user-icon"></i> <span>账号</span> <input type="text"
								placeholder="用户名" id="username" name="username">
						</div>
						<div class="row">
							<i class="pwd-icon"></i> <span>密码</span> <input type="password"
								placeholder="密码" id="password" name="password">
						</div>
						<div class="clear"></div>
						<button type="button" onclick="loginFun()">登 录</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="w1400 menu clearfix">
		<ul>
			<li><i class="icon1"></i>
				<p>企业级商城管理平台</p></li>
			<li><i class="icon2"></i>
				<p>商户账务清结算管理</p></li>
			<li><i class="icon3"></i>
				<p>渠道商对接管理平台</p></li>
			<li><i class="icon4"></i>
				<p>企业级礼赠平台</p></li>
			<li><i class="icon5"></i>
				<p>百万商品优选</p></li>
			<li><i class="icon6"></i>
				<p>移动支付网关</p></li>
		</ul>
	</div>
	<div class="w1400  partner">
		<p>合作伙伴</p>
		<a href="#"><img src="${ctxStatic }/login/images/02.png" alt=""></a> <a
			href="#"><img src="${ctxStatic }/login/images/03.png" alt=""></a> <a
			href="#"><img src="${ctxStatic }/login/images/01.png" alt=""></a> <a
			href="#"><img src="${ctxStatic }/login/images/04.png" alt=""></a> <a
			href="#"><img src="${ctxStatic }/login/images/05.png" alt=""></a>
	</div>
	<div class="footer ">
		<div class="w1400 clearfix">
			<div class="ewm fl">
				<img src="${ctxStatic }/login/images/ewm.png" alt="">
				<p>微信公众号二维码</p>
			</div>
			<div class="info fl">
				<p class="name">武汉市驿宝通网络科技有限公司</p>
				<p class="url">官方网站：http://www.ycb51.com</p>
				<p class="address">详细地址：湖北武汉洪山区街道口长江传媒大厦2204</p>
			</div>
			<div class="tellnum fr">
				<p class="service">
					<i></i>服务热线：
				</p>
				<p class="num">400-9699-027</p>
			</div>
		</div>
	</div>
	<script>
// 		$(".tabbox").hide()

// 		$('.loginWarp .login-box h3 span').click(function() {
// 			var index = $(this).index()
// 			$(this).addClass('on').siblings().removeClass('on')
// 			$('.tabbox').eq(index).show().siblings('.tabbox').hide()
// 		}).eq(0).click();

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
					window.location.href = "${ctxS}";
				} else {
					alert(result.msg);
				}
			});
		}
		
	</script>
</body>
</html>
