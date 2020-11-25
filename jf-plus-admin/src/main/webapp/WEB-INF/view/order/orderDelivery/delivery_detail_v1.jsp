<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商品订单</title>
    <%@ include file="../../include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
        .detailTable td{padding:5px 10px;}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<script src="${ctxStatic}/3rd-lib/amazeui/js/amazeui.min.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-body am-fr">
                        	<div class="am-u-sm-12 am-margin-sm">
                        		<div>订单明细</div>
                        	</div>
                            <div class="am-u-sm-12">
                            	<div class="am-u-sm-6">
                            		<table class="detailTable">
                            			<tr>
                            				<td class="am-text-sm">订单号:</td><td class="am-text-sm">${order.orderNo }</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm">代理商:</td><td class="am-text-sm">${order.orgName }</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm">状态:</td><td class="am-text-sm am-text-warning">${fnc:getEnumText('com.jf.plus.common.core.enums.OrderAuditStatus',order.operStatus) }</td>
                            			</tr>
                            		</table>
                            	</div>
                            	<div class="am-u-sm-6">
                            		<table class="detailTable">
                            			<tr>
                            				<td class="am-text-sm">下单时间:</td><td class="am-text-sm"><fmt:formatDate value="${order.cashtime }" pattern="yyyy-MM-dd HH:mm"/></td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm">下单人:</td><td class="am-text-sm">${order.userName }</td>
                            			</tr>
                            		</table>
                            	</div>
                            </div>
                        	<div class="am-u-sm-12 am-margin-sm">
                        		<div>收货人</div>
                        	</div>
                            <div class="am-u-sm-12">
                            	<div class="am-u-sm-12">
                            		<table class="detailTable">
                            			<tr>
                            				<td class="am-text-sm">收货人:</td><td colspan="3" class="am-text-sm">${order.receiver } ${order.receiverPhone }</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm">收货地址:</td><td class="am-text-sm">${order.address }</td>
                            			</tr>
                            		</table>
                            	</div>
                            </div>
                            
                            <div class="am-u-sm-12 am-margin-sm">
                        		<div>待发货商品</div>
                        	</div>
                            <div class="am-u-sm-12">
                            	<div class="am-u-sm-12">
                            		<table class="detailTable am-table am-table-bordered">
                            			<tr>
                            				<th class="am-text-sm">序号</th>
                            				<th class="am-text-sm" width="35%">商品</th>
                            				<th class="am-text-sm">待发货数量</th>
                            				<th class="am-text-sm am-primary">发货数量</th>
                            			</tr>
                            			<tr>
                            				<th class="am-text-sm">发货类型:</th>
                            				<th class="am-text-sm" colspan="3">
                            					<div class="am-form-group">
											      <label class="am-radio-inline">
											        <input type="radio" name="deliveryType" value="2" checked="checked"> 全部发货
											      </label>
											      <label class="am-radio-inline">
											        <input type="radio" name="deliveryType" value="1"> 部分发货
											      </label>
											    </div>
                            				</th>
                            			</tr>
                            			<c:forEach items="${order.orderDetailList }" var="orderDetail" varStatus="status">
	                            			<tr>
	                            				<td class="am-text-sm">
	                            					${status.index + 1 }
	                            				</td>
	                            				<td class="am-text-sm">
	                            					<div>${orderDetail.itemName }</div>
	                            					<div>
	                            						<c:forEach items="${orderDetail.orderDetailSkus }" var="detailSku">
	                            							<span style="padding:3px;border:1px solid #999;">${detailSku.specColorText } ${detailSku.specSizeText } * ${detailSku.saleNum }</span>
	                            						</c:forEach>
	                            					</div>
	                            				</td>
	                            				<td class="am-text-sm">${orderDetail.toTrackNum }</td>
	                            				<td class="am-text-sm am-primary">
	                            					<input name="trackNum" data-id="${orderDetail.id }" data-num="${orderDetail.toTrackNum }" type="number" class="am-form-input" value = "${orderDetail.toTrackNum }" readonly/>
	                            				</td>
	                            			</tr>	
                            			</c:forEach>
                            			<tr>
                            				<th colspan="5" class="am-input-lg am-text-right ">本次发货，共 <span id="totalTrackNum" class="am-text-danger">${order.totalNum }</span> 件商品</th>
                            			</tr>
                            		</table>
                            	</div>
                            </div>
                            
                            <div class="am-u-sm-12 am-margin-sm">
                        		<div>配送方式</div>
                        	</div>
                            <div class="am-u-sm-12">
                            	<div class="am-u-sm-12">
                            		<table class="detailTable">
                            			<tr>
                            				<td class="am-text-sm">配送方式:</td>
                            				<td class="am-text-sm" colspan="3">
                            					<div class="am-form-group">
											      <label class="am-radio-inline">
											        <input type="radio" name="deliveryExpressType" value="1" checked="checked"> 物流
											      </label>
											      <label class="am-radio-inline">
											        <input type="radio" name="deliveryExpressType" value="2"> 自配
											      </label>
											    </div>
                            				</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm">快递公司/配送员:</td>
                            				<td class="am-text-sm">
                            					<input type="text" class="am-input-sm" name="deliveryExpress"/>
                            				</td>
                            				<td class="am-text-sm">快递单号/配送员电话:</td>
                            				<td class="am-text-sm">
                            					<input type="text" class="am-input-sm" name="deliveryExpressNo"/>
                            				</td>
                            			</tr>
                            		</table>
                            	</div>
                            </div>

                        </div>
                       	<div class="am-u-sm-12 am-text-center">
                            <button type="button" class="am-btn am-btn-xs am-btn-primary" onclick="submitForm()">确认发货</button>
                            <button type="button" class="am-btn am-btn-xs am-btn-danger" onclick="closeModel(false)">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
        }
        
        $('input[name=deliveryType]').click(function(){
        	var delivery_type = $(this).val();
        		$('input[name=trackNum]').each(function(){
        			if(delivery_type == 2){
        				$(this).val($(this).attr('data-num')).attr('readonly','readonly');
        			}else{
        				$(this).val($(this).attr('data-num')).removeAttr('readonly');
        			}
        		})
        		
        		calcTotalNum();
        });
        
        function calcTotalNum(){
        	var totalNum = 0;
        	$('input[name=trackNum]').each(function(){
        		if($(this).val() > 0){
        			totalNum += parseInt($(this).val());
        		}
        	});
        	
        	$('#totalTrackNum').text(totalNum);
        }
        
        $('input[name=trackNum]').blur(function(){
        	var delivery_type = $('input[name=deliveryType]:checked').val();
        	var src_val = $(this).val();
        	var data_num = $(this).attr('data-num');
        	if(src_val > data_num){
        		$(this).val(data_num);
        	}
        	
        	if(src_val < 0){
        		$(this).val(0);
        	}
        	
        	calcTotalNum();
        });
        
    });
    
        function submitForm(){
        	if(parseInt($('#totalTrackNum').text()) <= 0){
        		alert('请填写发货数量');
        		return false;
        	}
        	
        	var postdata = {};
        	postdata.orderNo = '${order.orderNo}';
        	postdata.orderId = '${order.id}';
        	postdata.deliveryType = $('input[name=deliveryType]').val();
        	postdata.deliveryExpressType = $('input[name=deliveryExpressType]').val();
        	postdata.deliveryExpress = $('input[name=deliveryExpress]').val();
        	postdata.deliveryExpressNo = $('input[name=deliveryExpressNo]').val();
        	postdata.deliveryExpressType = $('input[name=deliveryExpressType]').val();
        	
        	var orderDeliveryDetails = new Array();
        	$('input[name=trackNum]').each(function(){
        		var orderDeliveryDetail = {
        				orderDetailId:$(this).attr('data-id'),
        				deliveryNum:$(this).val()
        		}
        		orderDeliveryDetails.push(orderDeliveryDetail);
        	});
        	
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
