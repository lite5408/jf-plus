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
<script>
//-------------------------------- 库存 ---------------------------------------//

function getStock(tdId,productNo,skuId){
	getSupplyStock(tdId,productNo,skuId);
}

function getSupplyStock(tdId,productNo,itemId){
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
                            <div class="widget-title am-fl">待审核商品</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/product/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="source" name="source" type="hidden" value="${page.entity.source}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                    	<table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">是否审核</td>
												<td>
													<select name="operStatus" data-sm-selected class="am-input-sm" data="${page.entity.operStatus }">
														<option value="11">待审核</option>
														<option value="41">审核不通过</option>
													</select>
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">编码</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="itemCode"  value="${page.entity.itemCode }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">名称</td>
												<td>
													<input type="text" class="am-input-sm"  name="itemName"  value="${page.entity.itemName }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">类型</td>
												<td>
													<select name="saleType" data="${page.condition.saleType }">
														<option value="">全部</option>
														<option value="正价" <c:if test="${page.condition.saleType eq '正价' }">selected</c:if>>正价</option>
														<option value="砍货" <c:if test="${page.condition.saleType eq '砍货' }">selected</c:if>>砍货</option>
													</select>
												</td>
                                    		</tr>
                                    		<tr>
												<td class="am-padding-sm am-text-sm am-text-right">买手</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="buyerName"  value="${page.entity.buyerName }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">品牌</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="brandName"  value="${page.entity.brandName }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">供应商</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="supplyName"  value="${page.entity.supplyName }">
												</td>
												<td class="am-padding-sm"><button class="am-btn am-btn-xs am-btn-success am-icon-search"> 查询</button></td>
											</tr>
                                    	</table>
                                    </div>
                                </form>
                            </div>
					        <div class="am-u-sm-12" style="display:none;">
					          <div class="am-btn-toolbar">
					            <div class="am-btn-group am-btn-group-xs">
						            <button id="batchPickBtn" onclick="batchPick()" class="am-btn am-btn-warning"><span class="am-icon-check"></span> 批量审核</button>
					            </div>
					          </div>
					        </div>
                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                    		<th class="table-check"><input type="checkbox" id="checkAll"/></th>
                                            <th>品类</th>
                                            <th>商品编码</th>
                                            <th width="25%">商品名称</th>
                                            <th>类型</th>
                                            <th>买手</th>
                                            <th>价格</th>
                                            <th>库存</th>
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
                                                <td>${product.itemCateName }</td>
                                                <td><a onclick="openModelAdjust('商品详情','${ctx }/product/detail?productNo=${product.productNo}',false,false)">${product.itemCode}</a></td>
                                                <td>
                                                	<div class="am-u-md-4"><img class="" width="80" height="80" src="${fn:split(product.photoUrl, ',')[0]}"></div>
                                                	<div class="am-u-md-7">${product.itemName }</div>
                                                </td>
								                <td>
								                	<div><span class="am-badge ${product.saleType eq '砍货' ? 'am-badge-success':'am-badge-primary'} am-radius">${product.saleType}</span></div>
								                </td>
                                                <td>
                                                	<div>买手：${product.buyerName }</div>
                                                	<div>品牌：${product.brandName }</div>
                                                </td>
                                                <td>
                                                	<div>进货价：${product.inPrice }</div>
                                                	<div>中介费：${product.zjPrice }</div>
                                                	<div>管理费：${product.mgrPrice }</div>
                                                	<div>成本价：<span class="am-text-danger">${product.salePrice }</span></div>
                                                </td>
                                                <td id="stock${product.id }" class="error">
								                	<script>
								                		getStock('#stock${product.id }','${product.productNo }','${product.itemCode}');
								                	</script>
								                </td>
                                                <td>${fnc:getEnumText("com.jf.plus.common.core.enums.ProductStatus",product.operStatus)}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
	                                                <c:if test="${product.operStatus eq 11 }">
							                      		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary"  name="pickBtn" onclick="changeStatusFun('${product.id}',3,'${product.itemCode }')"><span class="am-icon-pencil-square-o"></span> 通过</a>
							                      		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-danger"  name="pickBtn" onclick="changeStatusFun('${product.id}',41,'${product.itemCode }')"><span class="am-icon-pencil-square-o"></span> 不通过</a>
							                    	
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
      //复选框所有
        $('#checkAll').click(function(){
    		var checked = $(this).prop('checked') ? true:false;
    		$('input[name="rowId"]').prop('checked',checked);
    	});
	});
    
    function changeStatusFun(id,operStatus,itemCode){
    	var target = event.srcElement;
    	var cfmMsg = "确定要不通过吗？";
    	if(operStatus == 3){
    		cfmMsg = "确定要通过吗？";
    	}
    	
    	var index = layer.confirm(cfmMsg, {
    		offset: $(target).offset().top,
    	    btn: ['确定','取消']
    	}, function(){
    		$.post('${ctx}/product/changeStatus',{ids:id,operStatus:operStatus,itemCodes:itemCode},function(result){
    			if(result.success){
    				layer.close(index);
    				layer.msg('操作成功',{icon: 1,time: 1000},function(){
    					$('#searchForm').submit();
                	});
    			}else{
    				alert(result.msg);
    			}
    		})
    	}, function(){
    	    layer.close();
    	});
    }
</script>
</body>
</html>
