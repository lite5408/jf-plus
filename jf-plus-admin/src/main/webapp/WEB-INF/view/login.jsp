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
		<img src="${ctxStatic }/login/images/logo2.jpg" style="height: 50px;vertical-align: -15px;" alt="">
		<span class="logo-name">${fnc:getConfig("iutils.name")}</span> <span class="fr toptel"></span>
	</div>
	<div class="content loginWarp">
		<div id="loginbg" style="background: url(${ctxStatic }/login/images/abc.jpg) center center; 
				background-size:100% 100%;height:600px;">
			<div class="main w1200 clearfix">
				<div class="login-box">
					<h3>
						<span class="on">管理员登录</span><span>站点登录</span>
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
						<div class="row" id="orgRow">
							<i class="jg-icon"></i> <span>机构</span> <select id="orgId"
								name="orgId">
								<option value="">选择机构</option>
							</select>
						</div>
						<div class="clear"></div>
						<button type="button" onclick="loginFun()">登 录</button>
					</div>
					<!--商户登陆-->
					<div class="tabbox" style="display: none;">
						<div class="row">
							<i class="user-icon"></i> <span>账号</span> <input type="text"
								placeholder="用户名" id="siteUserName">
						</div>
						<div class="row">
							<i class="pwd-icon"></i> <span>密码</span> <input type="password"
								placeholder="密码" id="siteUserPassword">
						</div>
						<input type="hidden" id="siteId" value="0" />
						<input type="hidden" id="siteName" value="" />
						<input type="hidden" id="siteOrgId" value="0" />
						<div class="clear"></div>
						<button type="button" onclick="loginSite()">登 录</button>
					</div>

				</div>
			</div>
		</div>
	</div>
	<div class="footer ">
		<div class="w1200 clearfix">
			<div class="info fl">
				<p class="name">武汉市曼天雨科技有限公司</p>
				<p class="url">官方网站：http://www.mantianyu.com</p>
				<p class="address">详细地址：XXXX</p>
			</div>
			<div class="tellnum fr">
				<p class="service">
					<i></i>服务热线：
				</p>
				<p class="num">027-233292993</p>
			</div>
		</div>
	</div>
	<script>
// 		$('#loginbg').css("height",window.screen.height - 70);
		
		$(".tabbox").hide()

		$('.loginWarp .login-box h3 span').click(function() {
			var index = $(this).index()
			$(this).addClass('on').siblings().removeClass('on')
			$('.tabbox').eq(index).show().siblings('.tabbox').hide()
		}).eq(0).click();

		function loginFun() {
			if ($.trim($('#username').val()) == '') {
				alert('请输入用户名');
				return false;
			}
			if ($.trim($('#password').val()) == '') {
				alert('请输入密码');
				return false;
			}

			if ($.trim($('#orgId').val()) == '') {
				alert('请输入机构');
				return false;
			}

			$.post("${ctx}/login", {
				username : $.trim($('#username').val()),
				password : $.trim($('#password').val()),
				orgId : $.trim($('#orgId').val()),
				loginType : "USER"
			}, function(result) {
				if (result.success) {
					window.location.href = "${ctx}/";
				} else {
					alert(result.msg);
				}
			});
		}
		
		$('#username').blur(function() {
			if ($.trim($('#username').val()) == '') {
				return false;
			}
			$.post('${ctx}/mall/mallSite/getMyOrg', {
				username : $.trim($('#username').val())
			}, function(result) {
				if (result.success) {
					var orgList = result.obj;
					var orgOption = '';
					for(var i in orgList){
						orgOption += '<option value="'+ orgList[i].id +'">' + orgList[i].name + '</option>';
					}
					$('#orgId').html(orgOption);
					if(orgList.length > 1){
						$('#orgRow').show();
					}
					
				}
			})
		});

		function loginSite() {
			if ($.trim($('#siteUserName').val()) == '') {
				alert('请输入用户名');
				return false;
			}
			if ($.trim($('#siteUserPassword').val()) == '') {
				alert('请输入密码');
				return false;
			}

			$.post("${ctx}/loginSite", {
				username : $.trim($('#siteUserName').val()),
				password : $.trim($('#siteUserPassword').val()),
				siteOrgId : $.trim($('#siteOrgId').val()),
				siteId : $.trim($('#siteId').val()),
				siteName : $.trim($('#siteName').val()),
				loginType : "USER"
			}, function(result) {
				if (result.success) {
					window.location.href = "${ctx}/";
				} else {
					alert(result.msg);
				}
			});
		}

		$('#siteUserName').blur(function() {
			if ($.trim($('#siteUserName').val()) == '') {
				return false;
			}
			$.post('${ctx}/mall/mallSite/getMySite', {
				username : $.trim($('#siteUserName').val())
			}, function(result) {
				if (result.success) {
					$('#siteId').val(result.obj.id);
					$('#siteName').val(result.obj.siteName);
					$('#siteOrgId').val(result.obj.orgId);
				}
			})
		});
		
		$("body").keydown(function() {
		  if (event.keyCode == "13") {//keyCode=13是回车键
		    loginFun();
		  }
		});  
	</script>
</body>
</html>
