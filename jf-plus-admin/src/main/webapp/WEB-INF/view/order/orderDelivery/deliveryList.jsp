<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>代发货订单</title>
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
                            <div class="widget-title am-fl">待发货订单</div>
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
                                <form id="searchForm" action="${ctx}/order/orderDelivery/toDeliveryList" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">订单号</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="orderNo"  value="${page.entity.orderNo }">
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">代理商</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="orgName"  value="${page.entity.orgName }">
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
                                            <th>代理商</th>
                                            <th>下单人</th>
                                            <th width="20%">收货人</th>
                                            <th>下单时间</th>
                                            <th>状态</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="row" varStatus="status">
                                        <tr>
                                                <td><a href="javascript:" onclick="openModelAdjust('订单详情','${ctx }/order/detail_v1?orderNo=${row.orderNo }',false,false)">${row.orderNo}</a></td>
                                                <td>${row.orgName}</td>
                                                <td>${row.userName }</td>
                                                <td>
                                                	${row.receiver}-${row.receiverPhone}
                                                	<p>${row.address}</p>
                                                </td>
                                                <td><fmt:formatDate value="${row.cashtime }" pattern="yyyy-MM-dd HH:mm"/></td>
                                                <td class="am-text-warning">
                                                	待发货
                                                </td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                    	<a role="oper" href="javascript:;" onclick="openModelAdjust('订单详情','${ctx }/order/detail_v1?orderNo=${row.orderNo }',false,false)" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 详情</a>
                                                    	<a role="oper" href="javascript:;" onclick="openModelAdjust('发货','${ctx }/order/orderDelivery/detail_v1?orderNo=${row.orderNo }',false,false)" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-check"></span> 发货</a>
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
    });
    
    function exportFun(){
    	if(G_EXPORT_LOCKED && G_EXPORT_LOCKED == 1){
    		var target = event.target || event.srcElement;
        	var index = layer.prompt({
        		  offset: $(target).offset().top,
        		  title: "校验管理员手机号",
        		  formType: 0
        		}, function(value, index, elem){
        			if(!checkMobile(value)){
        				alert('请输入正确的手机号');
        				return;
        			}
        			$.post('${ctx}/user/checkPhone',{phone:value},function(result){
        				if(result.success){
        					layer.close(index);
        					location.href = "${ctx }/export/order/excel?pageNo=0&operStatus=${page.entity.operStatus}" + 
        	    			"&orgName=${page.entity.orgName}&userName=${page.entity.userName}&orderNo=${page.entity.orderNo}";
        				}else{
        					alert(result.msg);
        				}
        			});
        		});
    	}else{
	    	location.href = "${ctx }/export/order/excel?pageNo=0&operStatus=${page.entity.operStatus}" + 
	    			"&orgName=${page.entity.orgName}&userName=${page.entity.userName}&orderNo=${page.entity.orderNo}";
    	}
    }
</script>
</body>
</html>
