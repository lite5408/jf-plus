<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单报表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
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
                            <div class="widget-title am-fl">统计报表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-1 am-u-lg-1">
                                <table class="am-u-sm-12">
	                                <tr>
										<td class="am-padding-sm">
											<a href="${ctxS}/order/report/excel?pageNo=${page.pageNo}&pageSize=${page.pageSize}&supplyId=${supplyId}&query=${query}&startTime=<fmt:formatDate pattern="yyyy-MM-dd" value="${page.entity.startTime }"/>&endTime=<fmt:formatDate pattern="yyyy-MM-dd" value="${page.entity.endTime }"/>" 
												class="am-btn am-btn-xs am-btn-success am-icon-file-excel-o" type="button"> 导出</a>
										</td>
	                                </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-11 am-u-lg-11">
                                <form id="searchForm" action="${ctxS}/order/report" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="supplyId" name="supplyId" type="hidden" value="${supplyId}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">开始时间:</td>
                                    			<td>
											  		<input type="datetime" name="startTime" placeholder="开始时间" data-am-datepicker readonly value="<fmt:formatDate pattern="yyyy-MM-dd" value="${page.entity.startTime }"/>"/>
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">结束时间:</td>
                                    			<td>
											  		<input type="datetime" name="endTime" placeholder="结束时间" data-am-datepicker readonly value="<fmt:formatDate pattern="yyyy-MM-dd" value="${page.entity.endTime }"/>"/>
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">查询维度:</td>
                                    			<td>
												<div class="am-form-group" style="padding-top: 20px;">
											  		<label class="am-checkbox-inline">
												      <input type="checkbox" name="query" value="jod.id" data-am-ucheck <c:if test="${fn:contains(query, 'jod.id')}" > checked</c:if>> 订 单
												    </label>
												    <label class="am-checkbox-inline">
												      <input type="checkbox" name="query" value="org.`name`" data-am-ucheck <c:if test="${fn:contains(query, 'org.`name`')}" > checked</c:if>> 机 构
												    </label>
												</div>
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
                                            <th>供应商</th>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><th>订单编号</th></c:if>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><th>下单人</th></c:if>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><th>商品编码</th></c:if>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><th width="35%">商品名称</th></c:if>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><th>销售单价</th></c:if>
                                            <c:if test="${fn:contains(query, 'org.`name`')}" ><th>机构</th></c:if>
                                            <th>销售数量</th>
                                            <th>销售总金额</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="row" varStatus="status">
                                        <tr>
                                            <td>${row.supplyName }</td>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><td>${row.orderNo}</td></c:if>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><td>${row.userName}</td></c:if>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><td>${row.itemCode}</td></c:if>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><td>${row.itemName}</td></c:if>
                                            <c:if test="${fn:contains(query, 'jod.id')}" ><td>${row.salePrice}</td></c:if>
                                            <c:if test="${fn:contains(query, 'org.`name`')}" ><td>${row.orgName}</td></c:if>
                                            <td>${row.totalNum}</td>
                                            <td>${row.totalAmount}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="am-u-lg-12 am-cf">
                                <%@ include file="/WEB-INF/view/utils/pagination.jsp"%>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
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
