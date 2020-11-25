<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单发货表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <style>
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
                            <div class="widget-title am-fl">订单发货</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="orderDelivery" action="${ctx}/order/orderDelivery/<c:choose><c:when test="${empty orderDelivery.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${orderDelivery.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品编码</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemCode" placeholder="商品编码" value="${orderDetail.itemCode }" readonly/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品名称</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemName" placeholder="商品名称" value="${orderDetail.itemName }" readonly/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">包裹号</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="ckNo" placeholder="" value="" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">发货数量</label>
                                        <div class="am-u-sm-9">
                                            <input type="number" name="deliveryNum" placeholder="" value="" required/>
                                        </div>
                                    </div>
                            <div class="am-form-group">
                                <div class="am-u-sm-9 am-u-sm-push-3">
                                    <button type="button" class="am-btn am-btn-xs am-btn-primary" onclick="submitForm()">保存</button>
                                    <button type="button" class="am-btn am-btn-xs am-btn-danger" onclick="closeModel(false)">关闭</button>
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
<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
    });
    
    function submitForm(){
    	if(isNull($('input[name=ckNo]').val()) || isNull($('input[name=deliveryNum]').val())){
    		alert('请填写正确的信息');
    		return false;
    	}
    	
    	var postdata = {};
    	postdata.orderNo = '${orderDetail.orderNo}';
    	postdata.orderId = '${orderDetail.orderId}';
    	postdata.deliveryType = 1;
    	postdata.deliveryExpressType = 1;
    	postdata.deliveryExpress = '物流';
    	postdata.deliveryExpressNo = $('input[name=ckNo]').val();
    	postdata.ckNo = $('input[name=ckNo]').val();
    	
    	var orderDeliveryDetails = new Array();
   		var orderDeliveryDetail = {
   				orderDetailId:'${orderDetail.id}',
   				deliveryNum:$('input[name=deliveryNum]').val()
   		}
   		orderDeliveryDetails.push(orderDeliveryDetail);
    	
    	postdata.orderDeliveryDetails = orderDeliveryDetails;
    	$.ajax({
    	    method: "POST",
    	    url: "${ctx}/order/orderDelivery/delivery",
    	    contentType: 'application/json',
    	    data:JSON.stringify(postdata),
    	    success: function( result ) {
    	    	if(result.success){
        			showMsg(result.msg);
        			closeModel(true);
        		}else{
        			alert(result.msg);
        		}
    	   }
    	});
    }
</script>
</body>
</html>
