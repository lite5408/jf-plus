<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>我的工作台</title>
<%@ include file="/WEB-INF/view/mall/mallSupplyer/front/common/head.jsp"%>
</head>
<body style="padding-bottom: 55px;">
	<div class="xysc-my p-b-10">
		<div class="toux">
			<div class="t-img-box">
				<img src="${ctxStatic }/custom/images/toux.png" alt=""></a>
			</div>
			<p>${session_supply_user.adminLoginName}</p>
			<p>${session_supply_user.supplyName}</p>
		</div>
	</div>
	<div class="content mt5">
		<div class="bgw  f14 pt10">
			<div class="geren-wrap">
				<a href="${ctxS}/mobile/order/list?operStatus=22" class="wbox">
					<div class="wfl  m-l-10">待收货订单</div>
				</a>
			</div>
			<div class="geren-wrap">
				<a href="${ctxS }/mobile/order/list?operStatus=23" class="wbox">
					<div class="wfl m-l-10">已妥投订单</div>
				</a>
			</div>
			<div class="geren-wrap">
				<a href="${ctxS }/mobile/logout" class="wbox">
					<div class="wfl  m-l-10">安全退出</div>
				</a>
			</div>
		</div>
	</div>
</body>
</html>