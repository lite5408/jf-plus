<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>财务对账簿表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
        .am-tabs-default .am-tabs-nav a{
        	height:40px!important;
        }
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
                            <div class="widget-title am-fl">订单结算查询</div>
                        </div>
                        <div class="widget-body am-fr">
                        	<div class="am-u-sm-12 am-u-md-2 am-u-lg-2">
                                <table class="am-u-sm-12">
	                                <tr>
										<td class="am-padding-sm">
											<a href="${ctx }/export/financeOrder/excelOrder?pageNo=0&operStatus=${page.entity.operStatus }&batchNo=${page.entity.batchNo}&startTime=${page.entity.startTime}&endTime=${page.entity.endTime}&orderNo=${page.entity.orderNo}&supplyName=${page.entity.supplyName}" 
												class="am-btn am-btn-xs am-btn-success am-icon-file-excel-o" type="button"> 导出</a>
										</td>
	                                </tr>
                                </table>
                            </div>
                            <div class="am-u-sm-12 am-u-md-10 am-u-lg-10">
                                <form id="searchForm" action="${ctx}/order/financeOrder/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="operStatus" name="operStatus" type="hidden" value="${page.entity.operStatus}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                            <tr>
                                            	<td class="am-padding-sm am-text-sm am-text-right">批次号</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="batchNo"  value="${page.entity.batchNo}">
                                                </td>
                                                <td class="am-padding-sm am-text-sm am-text-right">时间</td>
                                                <td>
												  	<input type="datetime" style="width:100px;" class="am-u-sm-5" name="startTime" placeholder="开始时间" data-am-datepicker value="${page.entity.startTime }"/>
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">
												  	<input type="datetime"  style="width:100px;" class="am-u-sm-5" name="endTime" placeholder="结束时间" data-am-datepicker value="${page.entity.endTime }"/>
												</td>
                                                <td class="am-padding-sm am-text-sm am-text-right">供应商</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="supplyName"  value="${page.entity.supplyName}">
                                                </td>
                                                <td class="am-padding-sm am-text-sm am-text-right">订单号</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="orderNo"  value="${page.entity.orderNo}">
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
                                            <th class="table-check"><input type="checkbox" id="checkAll"/></th>
                                            <th>订单号</th>
                                            <th>订单人</th>
                                            <th>机构</th>
                                            <th>订单金额</th>
                                            <th>采购事由</th>
                                            <th>渠道单号</th>
                                            <th>供应商</th>
                                            <th>结算日期</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="financeOrder" varStatus="status">
                                        <tr>
                                                <td><input type="checkbox" name="rowId" value="${financeOrder.order.id }"/></td>
                                                <td>${fn:substring(financeOrder.order.orderNo,12,21)}</td>
                                                <td>${financeOrder.order.userNo}-${financeOrder.order.userName}</td>
                                                <td>${financeOrder.order.orgNo}</td>
                                                <td>${financeOrder.order.payAmount}</td>
                                                <td>${financeOrder.auditReason}</td>
                                                <td><c:if test="${financeOrder.order.supplyId <= 100 }">${financeOrder.outTradeNo }</c:if></td>
                                                <td>${ financeOrder.order.supplyName}</td>
                                            	<td><fmt:formatDate value="${financeOrder.createDate }" pattern="yyyy-MM-dd"/></td>
                                            <td>
                                            	<c:if test="${page.entity.operStatus eq 0 || empty page.entity.operStatus}">
	                                                <div class="am-btn-group am-btn-group-xs">
	                                                    <a role="oper" href="javascript:" name="batchFinanceBtn" onclick="financeOrder(${financeOrder.order.id },1);" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 标记结算</a>
	                                                </div>
                                                </c:if>
                                                <c:if test="${financeOrder.operStatus eq 1}">
	                                                <div class="am-btn-group am-btn-group-xs">
	                                                    <a role="oper" href="javascript:" name="batchFinanceBtn" onclick="javascript:financeOrder(${financeOrder.order.id },2);" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 取消结算</a>
	                                                </div>
                                                </c:if>
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
    });
    
    //复选框所有
	$('#checkAll').click(function(){
		var checked = $(this).prop('checked') ? true:false;
		$('input[name="rowId"]').prop('checked',checked);
	});
    
    $('[name=operStatusBtn]').click(function(){
    	$(this).siblings().removeClass('am-btn-primary');
    	$(this).addClass('am-btn-primary');
    	$('#operStatus').val($(this).attr('data'));
    	$('#searchForm').submit();
    })
    
    $('#batchBtn').click(function(){
    	if($('input[name="rowId"]:checked').length == 0){
    		alert('请勾选数据');
    		return false;
    	}
    	
    	var postdata = {};
    	var checkedRows = $('input[name="rowId"]:checked');
    	
    	var ids = "";
    	$.each(checkedRows,function(){
    		ids += $(this).val() + ',';
    	});
    	if(ids.length > 0){
    		ids = ids.substring(0,ids.length - 1);
    	}
    	
    	postdata.ids = ids;
    	postdata.bussType = 2;
    	postdata.operStatus = 1;
    	$.post('${ctx}/order/financeOrder/batchFinanceOrder',postdata,function(result){
            if (result.success) {
            	layer.msg('操作成功',{icon: 1,time: 1000},function(){
            		$.each(checkedRows,function(){
            			$(this).parents('tr').find('a[name="batchFinanceBtn"]').text('已操作').attr('disabled','disabled');
    		    		$(this).remove();
    		    	});
            	});
            }else{
                window.alert(result.msg);
            }
		});
    	
    })
    
    function financeOrder(orderId,operStatus){
    	var target = event.target || event.srcElement;
    	$.post('${ctx}/order/financeOrder/batchFinanceOrder',{ids:orderId,bussType:1,operStatus:operStatus},function(data){
    		if(data.success){
    			layer.msg('操作成功',{icon: 1,time: 1000},function(){
	    			$(target).text('已操作').attr('disabled','disabled');
					$(target).parents('tr').find('input[type="checkbox"]').remove();
    			});
    		}else{
    			window.alert(data.msg);
    		}
    	})
    }
</script>
</body>
</html>
