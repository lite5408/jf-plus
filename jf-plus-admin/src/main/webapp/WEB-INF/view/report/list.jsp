<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单报表</title>
    <%@ include file="../include/head.jsp"%>
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
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/report/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="0"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">时间:</td>
                                    			<td>
												  	<input type="datetime" style="width:100px;" class="am-u-sm-5" name="startTime" placeholder="开始时间" data-am-datepicker readonly required value="<fmt:formatDate pattern="yyyy-MM-dd" value="${page.entity.startTime }"/>"/>
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">
												  	<input type="datetime"  style="width:100px;" class="am-u-sm-5" name="endTime" placeholder="结束时间" data-am-datepicker readonly required value="<fmt:formatDate pattern="yyyy-MM-dd" value="${page.entity.endTime }"/>"/>
												</td>
												<td class="am-padding-sm am-text-sm">
													<div class="am-form-group>
														<label class="am-checkbox-line am-text-sm">统计列：</label>
												  		<label class="am-checkbox-inline">
													      <input type="checkbox" name="query" value="orderNo" data-am-ucheck <c:if test="${fn:contains(query, 'orderNo')}" > checked</c:if>>订单
													    </label>
													    <label class="am-checkbox-inline">
													      <input type="checkbox" name="query" value="itemCateName" data-am-ucheck <c:if test="${fn:contains(query, 'itemCateName')}" > checked</c:if>>品类
													    </label>
													    <label class="am-checkbox-inline">
													      <input type="checkbox" name="query" value="buyerName" data-am-ucheck <c:if test="${fn:contains(query, 'buyerName')}" > checked</c:if>>买手
													    </label>
													    <label class="am-checkbox-inline">
													      <input type="checkbox" name="query" value="itemName" data-am-ucheck <c:if test="${fn:contains(query, 'itemName')}" > checked</c:if>>商品
													    </label>
													    <label class="am-checkbox-inline">
													      <input type="checkbox" name="query" value="supplyName" data-am-ucheck <c:if test="${fn:contains(query, 'supplyName')}" > checked</c:if>>供应商
													    </label>
													    <label class="am-checkbox-inline">
													      <input type="checkbox" name="query" value="orgName" data-am-ucheck <c:if test="${fn:contains(query, 'orgName')}" > checked</c:if>>代理商
													    </label>
													</div>
												</td>
												<td class="am-padding-sm">
													<button class="am-btn am-btn-xs am-btn-success am-icon-search" type="button" onclick="searchFun()"> 查询</button>
													<button class="am-btn am-btn-xs am-btn-success am-icon-file-excel-o" type="button" onclick="exportFun()"> 导出</button>
												</td>
											</tr>
										</table>
										<table class="am-u-sm-12">
											<tr>
												<td class="am-padding-sm am-text-sm am-text-right">筛选：</td>
                                    			<td class="am-padding-sm">
													<div class="am-input-group am-input-group-sm">
													  <span class="am-input-group-label">代理商</span>
													  <input type="text" name="orgName" value="${page.entity.orgName }" class="am-form-field" placeholder="代理商编码或名称" <c:if test="${isDL == 1 }">readonly</c:if>>
													</div>
												</td>
                                    			<td class="am-padding-sm">
													<div class="am-input-group am-input-group-sm">
													  <span class="am-input-group-label">买手名称</span>
													  <input type="text" name="buyerName" value="${page.entity.buyerName }" class="am-form-field" placeholder="买手名称">
													</div>
												</td>
                                    			<td class="am-padding-sm">
													<div class="am-input-group am-input-group-sm">
													  <span class="am-input-group-label">品类</span>
													  <input type="text" name="itemCateName" value="${page.entity.itemCateName }" class="am-form-field" placeholder="品类">
													</div>
												</td>
												<td class="am-padding-sm">
													<div class="am-input-group am-input-group-sm">
													  <span class="am-input-group-label">供应商</span>
													  <input type="text" name="supplyName" value="${page.entity.supplyName }" class="am-form-field" placeholder="供应商名称">
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
                                            <c:if test="${fn:contains(query, 'orgName')}" ><th>代理商</th></c:if>
                                            <c:if test="${fn:contains(query, 'orderNo')}" ><th>订单号</th></c:if>
                                            <c:if test="${fn:contains(query, 'orderNo')}" ><th>下单人</th></c:if>
                                            <c:if test="${fn:contains(query, 'orderNo')}" ><th>订单时间</th></c:if>
                                            <c:if test="${fn:contains(query, 'itemCateName')}" ><th>品类</th></c:if>
                                            <c:if test="${fn:contains(query, 'buyerName')}" ><th>买手</th></c:if>
                                            <c:if test="${fn:contains(query, 'itemName')}" ><th>商品编码</th></c:if>
                                            <c:if test="${fn:contains(query, 'itemName')}" ><th width="35%">商品名称</th></c:if>
                                            <c:if test="${(fn:contains(query, 'itemName') || fn:contains(query, 'supplyName') || fn:contains(query, 'orgName')) && !fn:contains(query,'orderNo') }" ><th>订货数量</th></c:if>
                                            <th>总金额</th>
                                            <c:if test="${fn:contains(query, 'supplyName')}" ><th>供应商</th></c:if>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="row" varStatus="status">
                                        <tr>
                                            <c:if test="${fn:contains(query, 'orgName')}" ><td>${row.orgName}</td></c:if>
                                            <c:if test="${fn:contains(query, 'orderNo')}" ><td>${row.orderNo}</td></c:if>
                                            <c:if test="${fn:contains(query, 'orderNo')}" ><td>${row.userName}</td></c:if>
                                            <c:if test="${fn:contains(query, 'orderNo')}" ><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${row.cashtime }"/></td></c:if>
                                            <c:if test="${fn:contains(query, 'itemCateName')}" ><td>${row.itemCateName}</td></c:if>
                                            <c:if test="${fn:contains(query, 'buyerName')}" ><td>${row.buyerName}</td></c:if>
                                            <c:if test="${fn:contains(query, 'itemName')}" ><td>${row.itemCode}</td></c:if>
                                            <c:if test="${fn:contains(query, 'itemName')}" ><td>${row.itemName}</td></c:if>
                                            <c:if test="${(fn:contains(query, 'itemName') || fn:contains(query, 'supplyName') || fn:contains(query, 'orgName')) && !fn:contains(query,'orderNo') }" ><td>${row.totalNum }</td></c:if>
                                            <td>${row.payAmount}</td>
                                            <c:if test="${fn:contains(query, 'supplyName')}" ><td>${row.supplyName }</td></c:if>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="am-u-lg-12 am-cf">
                                <%@ include file="../utils/pagination.jsp"%>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../include/bottom.jsp"%>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
        }
        
    });
    
    function validFun(){
    	if($('input[name=startTime]').val() == '' || $('input[name=endTime]').val() == ''){
    		alert('请选择时间范围');
    		return false;
    	}
    	if($('input[name=query]:checked').length == 0){
    		alert('请选择统计列');
    		return false;
    	}
    	
    	return true;
    }
    
    function searchFun(){
    	if(validFun()){
    		$('#searchForm').submit();
    	}
    }
    
    function exportFun(){
    	if(validFun()){
    		location.href = encodeURI('${ctx }/export/report/excel?pageNo=0&pageSize=${page.pageSize}&query=${query}&supplyName=${page.entity.supplyName }&orgName=${page.entity.orgName }&buyerName=${page.entity.buyerName }&itemCateName=${page.entity.itemCateName }&'+
    		'startTime='+ $('input[name=startTime]').val() +'&endTime=' + $('input[name=endTime]').val());
    	}
    }
</script>
</body>
</html>
