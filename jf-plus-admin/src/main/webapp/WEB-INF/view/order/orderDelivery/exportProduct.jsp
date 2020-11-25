<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>发货详情</title>
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
<form action="${ctx }/order/orderDelivery/deliveryExport" id="submitForm" class="am-form tpl-form-border-form" data-am-validator>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-body am-fr">
                        	<div class="am-u-sm-12 am-margin-sm">
                        		<div>发货商品明细</div>
                        	</div>
                            	
                            <div class="am-u-sm-12">
                            	<div class="am-u-sm-12">
                            		<table class="detailTable am-table am-table-bordered">
                            			<tr>
                            				<th class="am-text-sm">包裹号</th>
                            				<th class="am-text-sm">商品编码</th>
                            				<th class="am-text-sm">商品名称</th>
                            				<th class="am-text-sm">颜色</th>
                            				<th class="am-text-sm">尺寸</th>
                            				<th class="am-text-sm">发货数量</th>
                            				<th class="am-text-sm">成本价</th>
                            				<th class="am-text-sm">设置销售价</th>
                            			</tr>
                            			<c:forEach items="${orderDeliveryDetailList }" var="orderDeliveryDetail" varStatus="status">
                            				<input type="hidden" name="orderDeliveryExports[${status.index }].deliveryId" value="${orderDeliveryDetail.deliveryId }"/>
                            				<input type="hidden" name="orderDeliveryExports[${status.index }].orderNo" value="${orderDeliveryDetail.orderNo }"/>
                            				<input type="hidden" name="orderDeliveryExports[${status.index }].itemCode" value="${orderDeliveryDetail.itemCode }"/>
                            				<input type="hidden" name="orderDeliveryExports[${status.index }].itemName" value="${orderDeliveryDetail.itemName }"/>
                            				<input type="hidden" name="orderDeliveryExports[${status.index }].specColorText" value="${orderDeliveryDetail.specColorText }"/>
                            				<input type="hidden" name="orderDeliveryExports[${status.index }].specSizeText" value="${orderDeliveryDetail.specSizeText }"/>
                            				<input type="hidden" name="orderDeliveryExports[${status.index }].deliveryNum" value="${orderDeliveryDetail.deliveryNum }"/>
	                            			<tr>
	                            				<td class="am-text-sm">${orderDeliveryDetail.ckNo }</td>
	                            				<td class="am-text-sm">${orderDeliveryDetail.itemCode }</td>
	                            				<td class="am-text-sm">${orderDeliveryDetail.itemName }</td>
	                            				<td class="am-text-sm">${orderDeliveryDetail.specColorText }</td>
	                            				<td class="am-text-sm">${orderDeliveryDetail.specSizeText }</td>
	                            				<td class="am-text-sm">${orderDeliveryDetail.deliveryNum }</td>
	                            				<td class="am-text-sm">${orderDeliveryDetail.salePrice }</td>
	                            				<td class="am-text-sm"><input type="number" name="orderDeliveryExports[${status.index }].exportPrice" min="0" required/></td>
	                            			</tr>	
                            			</c:forEach>
                            		</table>
                            	</div>
                            </div>

                        </div>
                       	<div class="am-u-sm-12 am-text-center">
                            <button type="submit" class="am-btn am-btn-xs am-btn-primary">设置销售价</button>
                            <button type="button" class="am-btn am-btn-xs am-btn-danger" onclick="closeModel(false)">关闭</button>
                        </div>
                       
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
 </form>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
        }
    });
    
</script>
</body>
</html>
