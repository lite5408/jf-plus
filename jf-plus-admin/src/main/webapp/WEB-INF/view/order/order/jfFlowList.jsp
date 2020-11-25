<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单支付记录</title>
    <%@ include file="../../include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
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
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">订单支付记录</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-2 am-u-lg-2">
                                <table class="am-u-sm-12">
	                                <tr>
										<td class="am-padding-sm">
											<a href="javascript:void(0)" onclick="exportFun()" 
												class="am-btn am-btn-xs am-btn-success am-icon-file-excel-o"> 导出</a>
										</td>
	                                </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-10 am-u-lg-10">
                                <form id="searchForm" action="${ctx}/order/jf/flowList" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">状态</td>
                                    			<td>
											  		<select class="am-input-sm" name="operStatus" data="${page.entity.operStatus}" style="min-width:100px;">
														<option value=" ">全部</option>
														<option value="22">待收货</option>
														<option value="23">已完成</option>
													</select>
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">订单号</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="orderNo"  value="${page.entity.orderNo }">
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">机构</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="orgName"  value="${page.entity.orgName }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">下单人</td>
												<td>
													<input type="text" class="am-input-sm"  name="userName"  value="${page.entity.userName }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">收货人</td>
												<td>
													<input type="text" class="am-input-sm"  name="receiver"  value="${page.entity.receiver }">
												</td>
												<td class="am-padding-sm"><button class="am-btn am-btn-xs am-btn-success am-icon-search"> 查询</button></td>
                                    		</tr>
                                    	</table>
                                    </div>
                                </form>
                            </div>
                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                            <th>订单号</th>
                                            <th width="10%">机构</th>
                                            <th>购买人</th>
                                            <th width="20%">商品信息</th>
                                            <th width="20%">收货人</th>
                                            <th>商品种数</th>
                                            <th>订单总价</th>
                                            <th>总成本价</th>
                                            <th>扣减成本价</th>
                                            <th>下单时间</th>
                                            <th>状态</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="row" varStatus="status">
                                        <tr>
	                                        <td>${row.orderNo}</td>
	                                        <td>${row.orgName}</td>
	                                        <td>${row.userName }</td>
	                                        <td>
	                                         <a onclick="openModelAdjust('商品详情','${ctx }/product/detail?productNo=${row.productNo}',false,false)">${row.itemName }</a>
	                                        	<a onclick="openModelAdjust('查看更多','${ctx}/order/orderAudit/myProductList?orderNo=${row.orderNo }',false,false)" class="am-radius am-btn am-btn-xs am-btn-default am-icon-search" title="更多"></a>
	                                        </td>
	                                        <td>
	                                        	${row.receiver}-${fnc:replaceMask(row.receiverPhone,4,'*') }
	                                        	<p>${fnc:replaceMask(row.address,8,'*')}</p>
	                                        </td>
	                                        <td>${row.totalNum}</td>
	                                        <td id="pay_${row.id }">
	                                        	${row.payAmount}
	                                        	<script>
	                                        		var content = "商品总额：${row.totalAmount}+运费：${row.freight}";
	                                        	 	$('#pay_${row.id}').popover({
	                                        		    content: content,
	                                        		    trigger:'hover focus'
	                                        		})
	                                        	</script>
	                                        </td>
	                                        <td id="sup_${row.id }">
	                                        	${row.paySupply}
	                                        	<script>
	                                        		var content = "商品总额：${row.paySupply}+运费：${row.freight}";
	                                        	 	$('#sup_${row.id}').popover({
	                                        		    content: content,
	                                        		    trigger:'hover focus'
	                                        		})
	                                        	</script>
	                                        </td>
	                                        <td>${row.rechargeAmount}</td>
	                                        <td><fmt:formatDate value="${row.cashtime }" pattern="yyyy-MM-dd HH:mm"/></td>
	                                        <td>
	                                        	${fnc:getEnumText('com.jf.plus.common.core.enums.OrderAuditStatus',row.operStatus) }
	                                        </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="am-u-lg-12 am-cf">
                                <%@ include file="../../utils/pagination.jsp"%>
                            </div>

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
        
        initSelectValue(true);
    });
    
    function exportFun(){
    	location.href = "${ctx }/export/jf/orderFlow/excel?pageNo=0&operStatus=${page.entity.operStatus}" + 
    			"&orgName=${page.entity.orgName}&userName=${page.entity.userName}&orderNo=${page.entity.orderNo}&receiver=${page.entity.receiver}";
    }
</script>
</body>
</html>
