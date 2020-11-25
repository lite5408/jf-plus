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
                            				<td class="am-text-sm am-link-muted">订单号:</td><td class="am-text-sm">${order.orderNo }</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm am-link-muted">代理商:</td><td class="am-text-sm">${order.orgName }</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm am-link-muted">状态:</td><td class="am-text-sm am-text-warning">${fnc:getEnumText('com.jf.plus.common.core.enums.OrderAuditStatus',order.operStatus) }</td>
                            			</tr>
                            		</table>
                            	</div>
                            	<div class="am-u-sm-6">
                            		<table class="detailTable">
                            			<tr>
                            				<td class="am-text-sm am-link-muted">下单时间:</td><td class="am-text-sm"><fmt:formatDate value="${order.cashtime }" pattern="yyyy-MM-dd HH:mm"/></td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm am-link-muted">下单人:</td><td class="am-text-sm">${order.userName }</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm am-link-muted">订单金额:</td><td class="am-text-sm am-text-danger">${order.payAmount }</td>
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
                            				<td class="am-text-sm am-link-muted">收货人:</td><td colspan="3" class="am-text-sm">${order.receiver } ${order.receiverPhone }</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm am-link-muted">收货地址:</td><td class="am-text-sm">${order.address }</td>
                            			</tr>
                            		</table>
                            	</div>
                            </div>
                            
                            <div class="am-u-sm-12 am-margin-sm">
                        		<div>订单明细</div>
                        	</div>
                            <div class="am-u-sm-12">
                            	<div class="am-u-sm-12">
                            		<table class="detailTable am-table am-table-bordered">
                            			<tr>
                            				<th class="am-text-sm">序号</th>
                            				<th class="am-text-sm">商品编码</th>
                            				<th class="am-text-sm" width="35%">商品</th>
                            				<th class="am-text-sm">单价</th>
                            				<th class="am-text-sm">数量</th>
                            				<th class="am-text-sm">总价</th>
                            			</tr>
                            			<c:forEach items="${order.orderDetailList }" var="orderDetail" varStatus="status">
	                            			<tr>
	                            				<td class="am-text-sm">${status.index+1 }</td>
	                            				<td class="am-text-sm">${orderDetail.itemCode }</td>
	                            				<td class="am-text-sm">
	                            					<div class="am-u-md-4"><img class="" width="80" height="80" src="${fn:split(orderDetail.productPic, ',')[0]}"></div>
	                            					<div class="am-u-md-7">${orderDetail.itemName }
	                            						<c:forEach items="${orderDetail.orderDetailSkus }" var="detailSku">
	                            							<div style="margin-top:2px;border:1px solid rgb(221,221,221);">${detailSku.specColorText } ${detailSku.specSizeText } * ${detailSku.saleNum }</div>
	                            						</c:forEach>
	                            					</div>
	                            				</td>
	                            				<td class="am-text-sm">${orderDetail.salePrice }</td>
	                            				<td class="am-text-sm">${orderDetail.saleNum }</td>
	                            				<td class="am-text-sm">${orderDetail.amount }</td>
	                            			</tr>	
                            			</c:forEach>
                            			<tr>
                            				<td colspan="6" class="am-input-sm am-text-right">共 <span class="am-text-warning">${order.totalNum }</span> 件商品</td>
                            			</tr>
                            		</table>
                            	</div>
                            </div>

                        </div>
                       	<div class="am-u-sm-12 am-text-center">
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
    });
    
</script>
</body>
</html>
