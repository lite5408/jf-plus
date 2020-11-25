<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商品表</title>
    <%@ include file="../../include/head.jsp"%>
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
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/site/siteProduct/select?operStatus=3&source=${page.condition.source}&advertId=${advertId }&items=${items }" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="source" name="source" type="hidden" value="${page.condition.source}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">ID</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="itemCode"  value="${page.condition.itemCode }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">名称</td>
												<td>
													<input type="text" class="am-input-sm"  name="itemName"  value="${page.condition.itemName }">
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
                                            <th>商品编码</th>
                                            <th width="35%">商品名称</th>
                                            <th>供应商</th>
                                            <th>供应价</th>
                                            <th>销售价</th>
                                            <th>利润比(%)</th>
                                            <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="product" varStatus="status">
                                        <tr>
                                                <td>
									                <input type="checkbox" name="rowId" value="${product.id }"/>
								                </td>
                                                <td>${product.itemCode}</td>
                                                <td>
                                                	<div class="am-u-md-4"><img class="" width="80" height="80" src="${fn:split(product.photo,',')[0] }"></div>
                                                	<div class="am-u-md-7">${product.itemName }</div>
                                                </td>
                                                <td>${product.supplyName}</td>
                                                <td>${product.supplyPrice}</td>
                                                <td>${product.salePrice}</td>
                                                <td>${product.profitPercent}</td>
                                                <td>${fnc:getEnumText("com.jf.plus.common.core.enums.ProductStatus",product.operStatus)}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
							                      	<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="pickToSpecial('${product.id}','3','${product.source }')"><span class="am-icon-check"></span> 挑选加入</a>
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
    
    
    function pickToSpecial(itemId,operStatus,source){
    	var target = event.srcElement;
   		$.post('${ctx}/site/siteAdvert/pick',{id:itemId,operStatus:operStatus,source:source,advertId:'${advertId}'},function(result){
   			if(result.success){
   				layer.msg('操作成功',{icon: 1,time: 1000},function(){
					$(target).text('已加入').attr('disabled',true);
					$(target).parents('tr').find('input[type="checkbox"]').remove();
	           	});
   			}else{
   				window.alert(result.msg);
   			}
   		})
    }
</script>
</body>
</html>
