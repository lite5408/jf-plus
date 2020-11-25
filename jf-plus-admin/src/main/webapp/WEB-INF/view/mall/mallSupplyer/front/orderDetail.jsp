<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>订单详情</title>
<%@ include file="/WEB-INF/view/mall/mallSupplyer/front/common/head.jsp"%>
</head>
<body>
	<header data-am-widget="header"
		class="am-header am-header-default am-header-yellow">
		<div class="am-header-lefta am-header-nav">
			<a href="javascript:history.go(-1);"><i class="am-icon-angle-left am-icon-md"></i></a>
		</div>
		<h1 class="am-header-title">
			<a href="#title-link" class="">订单详情</a>
		</h1>
	</header>
	<div class="order-info">
		<div class="order-top">
			<div id="address" class="block">
				<div class="black">
					<span class="p-r-20">收货人：${order.receiver}</span>联系方式：${order.receiverPhone}
				</div>
				<div class="m-t-5">${order.address}</div>
			</div>
		</div>
		<div class="info-title">商品信息</div>
		<c:set var="itemCount" value="0"/>
		<c:forEach items="${order.orderDetailList }" var="orderDetailVO">
			<div class="wbox order-pro bgw borde-bottom">
				<img src="${orderDetailVO.productPic}">
				<div class="wfl m-l-15">
					<h2 class="f14">${orderDetailVO.itemName}</h2>
					<p class="m-t-5 yellow">
						金额：${orderDetailVO.salePrice}<span class="right">X${orderDetailVO.saleNum}</span>
					</p>
				</div>
				<c:set var="itemCount" value="${itemCount + orderDetailVO.saleNum }"/>
			</div>
		</c:forEach>
		<div class="wbox order-pro bgw borde-bottom text-right">
			共<span class="yellow">${itemCount }</span>件，合计<span class="yellow">${ order.totalAmount}</span>元
		</div>
		<div class="info-title m-t-15">支付信息</div>
		<div class="list-row">
			<ul>
				<li class="wbox vmiddle"><span class="block ">订单编号</span><span
					class="p-l-15">${order.orderNo}</span></li>
				<li class="wbox vmiddle"><span class="block ">订单时间</span><span
					class="p-l-15"><fmt:formatDate value="${order.cashtime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
				<li class="wbox vmiddle"><span class="block ">订单总额</span><span
					class="p-l-15">${order.totalAmount}</span></li>
				<li class="wbox vmiddle"><span class="block ">配送信息</span> <span
					class="p-l-15">
						<c:if test="${order.operStatus == '22' }">待收货</c:if>
						<c:if test="${order.operStatus == '23' }">已收货</c:if>
					</span>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>