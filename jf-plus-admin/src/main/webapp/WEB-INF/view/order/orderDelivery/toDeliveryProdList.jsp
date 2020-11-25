<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>待发货订单</title>
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
											<a href="javascript:void(0)" name="impBtn"
												class="am-btn am-btn-xs am-btn-success am-icon-file-excel-o"> 导入</a>
											<a href="${ctxStatic }/fmt/发货导入模板.xls" class="am-link am-text-xs am-margin-left-xs">下载模板</a>
										</td>
	                                </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-10 am-u-lg-10">
                                <form id="searchForm" action="${ctx}/order/orderDelivery/toDeliveryProdList" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="operStatus" name="operStatus" type="hidden" value="${page.entity.operStatus}"/>
                                    <input id="isDist" name="isDist" type="hidden" value="${page.entity.isDist}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">商品编码</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="itemCode"  value="${page.entity.itemCode }">
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">商品名称</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="itemName"  value="${page.entity.itemName }">
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">代理商</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="orgName"  value="${page.entity.orgName }">
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
                                            <th>代理商</th>
                                            <th>分配数量</th>
                                            <th>订单号</th>
                                            <th>状态</th>
<!--                                         	<th>操作</th> -->
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="row" varStatus="status">
                                        <tr>
                                                <td>${row.itemCode}</td>
                                                <td>
                                                	<div class="am-u-md-4"><img class="am-img-circle" width="80" height="80" src="${fn:split(row.productPic, ',')[0]}"></div>
	                            					<div class="am-u-md-7">${row.itemName }</div>
                                                </td>
                                                <td>${row.orgName }</td>
                                                <td class="am-text-danger">${row.distNum }</td>
                                                <td>${row.orderNo }</td>
                                                <td class="am-text-warning">
                                                	待发货
                                                </td>
                                            <td>
<!--                                                 <div class="am-btn-group am-btn-group-xs"> -->
<!--                                                     	<a role="oper" href="javascript:;"  -->
<%--                                                     	onclick="openModelAdjust('发货','${ctx }/order/orderDelivery/toDeliveryProduct?productNo=${row.productNo }&itemName=${row.itemName }&itemCode=${row.itemCode }&orderNo=${row.orderNo }&id=${row.id }',false,false)" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-check"></span> 发货</a> --%>
<!--                                                 </div> -->
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
<form id="impForm" action="${ctx }/import/orderDelivery/excel" method="post" enctype="multipart/form-data">
    <input type="hidden" name="orgGroupId" value="0">
    <div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
      <div class="am-modal-dialog">
        <div class="am-modal-hd">导入发货单</div>
        <div class="am-modal-bd">
          <input onchange="submitImpForm()" type="file" name="file" id="uploadFile" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        </div>
        <div class="am-modal-footer">
          <span class="am-modal-btn" data-am-modal-cancel>取消</span>
          <span class="am-modal-btn" data-am-modal-confirm>提交</span>
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
        
        
		$('a[name=impBtn]').on('click', function() {
            $('#uploadFile').click();
        });
    });
    
    function submitImpForm(){
    	$('#impForm').submit();
    }
</script>
</body>
</html>
