<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>订单列表</title>
<%@ include file="/WEB-INF/view/mall/mallSupplyer/front/common/head.jsp"%>
</head>
<body>
	<header data-am-widget="header"
		class="am-header am-header-default am-header-yellow">
		<div class="am-header-lefta am-header-nav">
			<a href="javascript:history.go(-1);"><i
				class="am-icon-angle-left am-icon-md"></i></a>
		</div>
		<h1 class="am-header-title">
			<a href="#title-link" class="">
				<c:choose>
					<c:when test="${operStatus == '22' }">
						待收货订单
					</c:when>
					<c:otherwise>
						已妥投订单
					</c:otherwise>
				</c:choose>
			</a>
		</h1>
	</header>

	<div class="s-libao">
		<div class="am-tabs" data-am-tabs>
			<div class="am-tabs-bd">
				<div class="am-tab-panel am-fade am-in am-active" id="tab1">

					<ul id="orderListUL">
						<li class="bgw">
						</li>
					</ul>

				</div>
			</div>
		</div>
	</div>

	<div class="None wbox vmiddle" role='no-data' style="display: none">
		<div class="wfl">
			<i class="orderIcon"></i>
			<!-- 如果是订单 class为orderIcon -->
			<p class="f16 gery">暂无订单</p>
		</div>
	</div>

	<script>
		$(function() {
		
			var pageNo = 0, pageSize = 10, operStatus='${operStatus}';

			// dropload
			var dropload = $('#tab1').dropload({
				scrollArea : window,
				loadDownFn : function(me) {
					var _url = '${ctxS}/mobile/order/list';
					$.post(_url, {
						pageSize : pageSize,
						pageNo : pageNo,
						operStatus : operStatus
					}, function(result) {
						pageNo++;

						if (result.code == 408) {
							// 锁定，无数据
							me.lock();
							me.noData();
							me.resetload();
							
							return;
						}

						var shtml = '';
						if (result.success) {
							var orderList = result.obj.list;
							for(var i = 0;i<orderList.length ; i++){
								shtml+='<li class="bgw">';
								shtml+='<div class="l-title">'+ orderList[i].cashtime.substring(0,10) +'<span style="float: right;">'+ orderList[i].orderNo +'</span></div>';
								shtml+='<div class="wbox pd10">';
								shtml+='	<img src="'+ orderList[i].photo +'">';
								shtml+='	<div class="wfl m-l-10">';
								shtml+='		<div class="f14 dark" style="white-space: normal !important;">'+ orderList[i].itemName +'</div>';
								shtml+='	</div>';
								shtml+='</div>';
								shtml+='<div class="wfl unitPriceWarp text-right">';
								shtml+='	共<span class="yellow">'+ orderList[i].totalNum +'</span>件 合计:<span class="yellow">'+ orderList[i].totalAmount +'</span>元';
								shtml+='</div>';
								shtml+='<div class="text-right pd10">';
								shtml+='	<a href="${ctxS}/mobile/order/detail?orderNo='+ orderList[i].orderNo +'" class="am-btn order-item-btn"> 订单详情 </a>';
								if(operStatus == '22'){
									shtml+='	<a href="${ctxS}/mobile/order/confirm?orderNo='+ orderList[i].orderNo +'" class="am-btn order-item-btn" style="background-color:#fe5344;color:#fff;"> 确认送达 </a>';
								}
								shtml+='</div>';
								shtml+='</li>';
							}
							
							setTimeout(function() {
								$('#orderListUL').append(shtml);
								me.resetload();
							}, 500);
						} else {
							tips('系统繁忙，请稍后再试!');
						}
					});
				}
			});

			function confirmReceipt(orderNo) {
				$('#my-confirm').modal({
					relatedTarget : this,
					onConfirm : function(options) {
						jQuery.ajax({
							url : "${BASE_PATH}/order/receiptConfirm",
							data : {
								orderNo : orderNo
							},
							type : "POST",
							success : function(result) {
								if (result.success) {
									$('#' + orderNo + '').remove();
								}
							},
							error : function(json) {
								tips(result.msg);
							}
						});

					},
					onCancel : function() {

					}
				});
			}
		});
	</script>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">

			<div class="am-modal-bd">是否确认收货？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-confirm>确定</span> <span
					class="am-modal-btn" data-am-modal-cancel>取消</span>
			</div>
		</div>
	</div>
</body>

</html>