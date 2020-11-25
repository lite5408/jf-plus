<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>兑换订单</title>
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
                            <div class="widget-title am-fl">批量下单</div>
                        </div>
                        <div class="widget-body am-fr">
                            <%-- <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
	                                <tr>
										<td class="am-padding-sm">
											<a href="${ctx }/export/site/order/excel?pageNo=0&operStatus=${page.entity.operStatus}&itemName=${page.entity.itemName}&receiver=${page.entity.receiver}&orderNo=${page.entity.orderNo}" 
												class="am-btn am-btn-xs am-btn-success am-icon-file-excel-o" type="button"> 导出</a>
										</td>
	                                </tr>
                                </table>
                            </div> --%>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/site/orderBatch/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">收货人</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="receiveName"  value="${page.entity.receiveName }">
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">手机号</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="mobile"  value="${page.entity.mobile }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">批次号</td>
												<td>
													<input type="text" class="am-input-sm"  name="batchNo"  value="${page.entity.batchNo }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">订单状态</td>
												<td>
													<select class="am-input-xs" name="operStatus" data="${page.entity.operStatus}">
														<option value="">请选择订单状态</option>
												 		<option value="1">下单成功</option>
														<option value="2">下单失败</option>
													</select>
												</td>
												<td class="am-padding-sm"><button class="am-btn am-btn-xs am-btn-success am-icon-search"> 查询</button></td>
                                    		</tr>
                                    		<tr>
                                    			<td>
                                    				<a href="${ctx}/site/orderBatch/orderBatchConfirm" class="am-btn am-btn-xs am-btn-success">批量下单</a>
                                    			</td>
												<td>
                                    				<div class="am-btn-group am-btn-group-xs">
			                           					<a href="${ctx}/export/orderBatchModel/excel" class="am-btn">订单模板下载</a>
			                        				</div>
			                        			</td>
			                        			<td>
			                        				<div class="am-btn-group am-btn-group-xs">
			                           					<a href="${ctxStatic }/fmt/苏宁全国地址信息.xls" class="am-btn">苏宁全国地址信息下载</a>
			                        				</div>
			                        			</td>
			                        			<td>
			                                 		<div class="am-btn-group am-btn-group-xs">
			                           					<a href="${ctxStatic }/fmt/其他渠道全国地址信息.xls" class="am-btn">其他渠道全国地址信息下载</a>
			                        				</div>
			                        			</td>
                                    		</tr>
                                    	</table>
                                    </div>
                                </form>
                            </div>

                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                    		<th>下单人</th>
                                            <th width="20%">收货人</th>
                                        	<th width="25%">商品名称</th>
                                        	<th>商品数量</th>
                                        	<th>订单渠道</th>
                                        	<th>批次号</th>
                                        	<th>订单状态</th>
                                        	<th>下单失败原因</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="row" varStatus="status">
                                        <tr>
                                                <td>${row.tradePerson}</td>
                                                <td>
                                                	${row.receiveName}-${fnc:replaceMask(row.mobile,4,"*") }
                                                	<p>${fnc:replaceMask(row.address,4,"*")}</p>
                                                </td>
                                                <td>${row.itemName}</td>
                                                <td>${row.itemNum}</td>
                                                <td>${row.supplyName}</td>
                                                <td>${row.batchNo}</td>
                                                <td><c:if test="${row.operStatus eq 0}">待下单</c:if><c:if test="${row.operStatus eq 1}">下单完成</c:if><c:if test="${row.operStatus eq 2}">下单失败</c:if></td>
                                                <td>${row.failureReason}</td>
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
</script>
</body>
</html>
