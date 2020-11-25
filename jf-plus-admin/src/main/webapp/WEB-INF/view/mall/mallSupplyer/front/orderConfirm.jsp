<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>确认订单</title>
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
			<a href="#title-link" class="">确认订单</a>
		</h1>
	</header>
	<div class="pd20">
		<form action="" class="am-form am-form-inline" id="editForm">
			<input type="hidden" id="refreshToken" value="${SMS_TOKEN}"/>
			<input type="hidden" id="orderNo" name="orderNo" value="${order.orderNo }">
			<div class="am-form-group">
				<input type="tel" class="am-form-field" name="receiver" id="receiver" value="${order.receiver }" placeholder="收货人" readonly>
			</div>
			<div class="am-form-group wbox">
				<input name="mobile" type="tel" class="wfl" id="doc-ipt-email-1" placeholder="手机号" value="${order.receiverPhone }" readonly>
				<button type="button" class="am-btn am-btn-warning am-radius m-l-15" id="yzmBtn">获取验证码</button>
			</div>

			<div class="am-form-group">
				<input name="yzm" type="text" class="" id="yzm" placeholder="验证码">
			</div>
			<div class="am-form-group">
				<button type="button" class="am-btn am-btn-warning am-radius am-btn-block" id="confirmBtn">确认送达</button>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		var $mobile = $('#doc-ipt-email-1');
		$('#yzmBtn').click(function(){
			if(!checkMobile($mobile.val())){
				tips('请输入正确的手机号');
				return;
			}
			sendYZM($mobile.val(),$('#refreshToken').val());
		});

		$('#confirmBtn').click(function(){
			$thiz = $(this);
			$(this).text("正在提交").attr("disabled",true);
			
			if($.trim($('#yzm').val()) == ''){
				alert('请输入验证码');
				return false;
			}
			
			$.post('${ctxS}/mobile/order/confirm', {
				orderNo : $('#orderNo').val(),
				receiverPhone : $('#doc-ipt-email-1').val(),
				yzm : $.trim($('#yzm').val())
			}, function(result) {
				if(result.success){
					alert('操作成功');
					setTimeout(function(){
						window.history.go(-1);
					}, 200);
				}else{
					alert(result.msg);
					$thiz.text("确认送达").removeAttr('disabled');
				}
			});
		});
		
		function sendYZM(mobile, token) {
			$.post('${ctx}/sms/sendCode', {
				mobile : mobile,
				token : token
			}, function(result) {
				if (!result.success) {
					tips(result.msg);
					return false;
				} else {
					tips('发送成功');
					sendCallback();
					return true;
				}
			});
		}

		function sendCallback() {
			settime(document.getElementById('yzmBtn'));
		}
	</script>
</body>
</html>