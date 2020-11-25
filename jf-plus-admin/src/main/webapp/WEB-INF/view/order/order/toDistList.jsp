<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单分配</title>
    <%@ include file="../../include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<script src="${ctxStatic}/3rd-lib/amazeui/js/amazeui.min.js"></script>
<script src="${ctxStatic}/3rd-lib/layDate-v5.0.9/laydate/laydate.js"></script>

<script>
//-------------------------------- 库存 ---------------------------------------//

function getStock(tdId,productNo){
	getSupplyStock(tdId,productNo);
}

function getSupplyStock(tdId,productNo){
	$.post('${ctx}/product/getStock',{productNo:productNo},function(result){
		if(result.success){
			if(result.obj.stockNum == -1){
				$(tdId).html('<span class="am-text-danger">不限制库存</span>');
			}else{
				$(tdId).html(result.obj.stockNum);
			}
		}else{
			$(tdId).html('请求超时');
		}
	});
}
//-------------------------------- 库存 ---------------------------------------//
</script>

<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">订单分配</div>
                        </div>
                        <div class="widget-body am-fr">

                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/order/toDistList" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">状态</td>
                                    			<td>
											  		<select class="am-input-sm" name="isDist" data="${page.entity.isDist}" style="min-width:100px;">
														<option value="0" <c:if test="${page.entity.isDist eq 0}">selected</c:if>>待分配</option>
														<option value="1" <c:if test="${page.entity.isDist eq 1}">selected</c:if>>已分配</option>
													</select>
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">商品编码</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="itemCode"  value="${page.entity.itemCode }">
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">商品名称</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="itemName"  value="${page.entity.itemName }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">发布时间</td>
                                    			<td>
											  		<input type="text" class="am-input-sm" id="shelvesDate" name="shelvesDateRange"  value="${page.entity.shelvesDateRange }">
												</td>
											<tr>
											<tr>
												<td class="am-padding-sm am-text-sm am-text-right">供应商</td>
												<td>
											  		<input type="text" class="am-input-sm"  name="supplyName"  value="${page.entity.supplyName }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">品牌</td>
												<td>
											  		<input type="text" class="am-input-sm"  name="brandName"  value="${page.entity.brandName }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">买手</td>
												<td>
											  		<input type="text" class="am-input-sm"  name="buyerName"  value="${page.entity.buyerName }">
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
                                            <th>商品编码</th>
                                            <th>商品名称</th>
                                            <th>类型</th>
                                            <th>剩余库存</th>
                                            <th>销量</th>
                                            <th>总金额</th>
                                            <th>供应商</th>
                                            <th>状态</th>
                                            <c:if test="${page.entity.isDist eq 1}">
                                            	<th>分配批次号</th>
                                            </c:if>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="row" varStatus="status">
                                        <tr>
                                                <td>${row.itemCode}</td>
                                                <td>
                                                	<div class="am-u-md-4"><img class="" width="80" height="80" src="${fn:split(row.productPic, ',')[0]}"></div>
                                                	<div class="am-u-md-8">${row.itemName }</div>
                                                </td>
                                                <td>
                                                	<div><span class="am-badge ${row.saleType eq '砍货' ? 'am-badge-success':'am-badge-primary'} am-radius">${row.saleType}</span></div>
                                                	<p><span class="am-badge ${row.pOperStatus eq 3 ? 'am-badge-secondary':'am-badge-danger'} am-radius">${fnc:getEnumText("com.jf.plus.common.core.enums.ProductStatus",row.pOperStatus)}</span>
                                                	</p>
                                                </td>
                                                <td id="stock${status.index}" class="error">
								                	<script>
								                		getStock('#stock${status.index }','${row.productNo }');
								                	</script>
								                </td>
                                                <td>${row.saleNum}</td>
                                                <td>${row.amount}</td>
                                                <td>
                                                	<div>买  手：${row.buyerName }</div>
                                                	<div>品  牌：${row.brandName }</div>
                                                	<div>供应商：${row.supplyName }</div>
                                                	<div>发布时间：${row.shelvesDate }</div>
                                                </td>
                                                <td class="am-text-warning" name="isDist">
                                                	<c:if test="${empty row.isDist or row.isDist eq 0}">
                                                		待分配
                                                	</c:if>
                                                	<c:if test="${row.isDist eq 1}">
                                                		<div>已分配</div>
                                                		<div><fmt:formatDate value="${row.distDate }" pattern="yyyy-MM-dd HH:mm"/></div>
                                                	</c:if>
                                                </td>
                                                <c:if test="${row.isDist eq 1}">
                                                	<td>${row.batchNo }</td>
                                                </c:if>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                	<c:if test="${empty row.isDist or row.isDist eq 0}">
	                                                		<a role="oper" href="javascript:;" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="exportRowFun(this,'${row.itemId}',0)">导出Excel</a>

                                                	</c:if>
                                                	<c:if test="${row.isDist eq 1}">
                                                		<a role="oper" href="javascript:;" onclick="openModelAdjust('订单分配','${ctx }/order/distListDetail?itemId=${row.itemId }&isDist=1&batchNo=${row.batchNo }',false,false)" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 查看</a>
                                                		<a role="oper" href="${ctx}/export/distOrderList?itemId=${row.itemId}&isDist=1&batchNo=${row.batchNo}" class="am-btn am-btn-default am-btn-xs am-text-secondary">导出Excel</a>
                                                	</c:if>
                                                </div>
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
        
        
        laydate.render({
       	  elem: '#shelvesDate'
       	  ,range: true
       	});
    });
    
    $('[name=isDist]').change(function(){
    	$('#searchForm').submit();
    });
    
    var isDist = {};batchNo = {};
    function dist(self,itemId){
    	post('${ctx}/order/orderDetailDist/autoDist',{itemId:itemId,isDist:0},function(result){
			if(result.success){
				isDist[itemId] = 1;
				batchNo[itemId] = result.obj;
				location.href = "${ctx}/export/distOrderList?idDist=1&itemId="+itemId+"&batchNo="+batchNo[itemId];
				
				$(self).parent().parent().parent().find('td[name=isDist]').text('已分配');
			}else{
				alert(result.msg);
			}
		});
    }
    
    function exportRowFun(self,itemId,izDist){
    	if(isDist[itemId] == null){
    		var index = layer.confirm('订单将会自动分配，确认导出吗？', {
    			shade:0.01,
    			offset: '30px',
    			btn : [ '确定', '取消' ]
    		}, function() {
    			dist(self,itemId)
    			layer.close(index);
    		});
    	}else{
    		location.href = "${ctx}/export/distOrderList?idDist=1&itemId="+itemId+"&batchNo="+batchNo[itemId];
    	}
    	
    	
    }
</script>
</body>
</html>
