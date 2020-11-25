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
                            <div class="widget-title am-fl"><c:if test="${page.entity.operStatus == 3 }">上架的商品</c:if><c:if test="${page.entity.operStatus == 4 }">下架的商品</c:if></div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/product/list/my?source=${page.entity.source}&operStatus=${page.entity.operStatus}" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="source" name="source" type="hidden" value="${page.entity.source}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                    	<table class="am-u-sm-12">
                                    		<tr>
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
						            <button id="batchOutShevlesProduct" type="button" class="am-btn am-btn-warning"><span class="am-icon-check"></span> 批量下架</button>
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
								                <td><span class="am-badge ${product.saleType eq '砍货' ? 'am-badge-success':'am-badge-primary'} am-radius">${product.saleType}</span></td>
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
	                                                <c:if test="${product.operStatus eq 3 }">
							                      		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-danger am-icon-pencil-square-o" onclick="changeStatusFun('${product.id}',4,'${product.itemCode }')"> 下架</a>
							                    	</c:if>
							                    	<c:if test="${product.operStatus eq 4 }">
							                    		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary am-icon-pencil-square-o" onclick="changeStatusFun('${product.id}',3,'${product.itemCode }')"> 上架</a>
							                    		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-icon-history" onclick="openModelAdjust('操作日志','${ctx }/product/productStatusLog/view?productId=${product.id}',false,false)"> 日志</a>
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
<button
  id="startProgress"
  type="button"
  class="am-btn am-btn-success"
  data-am-modal="{target: '#my-modal-loading'}">
  加载进度条
</button>

<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="my-modal-loading">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">正在批量下架中请勿刷新页面！</div>
    <div class="am-modal-bd">
      <span class="am-icon-spinner am-icon-spin"></span>
    </div>
  </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
	$("#startProgress").hide();
	
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
        }
        
        $("#batchOutShevlesProduct").click(function(){
        	var rowList = document.getElementsByName("rowId");
        	var productIdArr = [];
            for(r in rowList){
            	if(rowList[r].checked)
            		productIdArr.push(rowList[r].value);
            }
            if(productIdArr.length == 0){
            	alert("请勾选商品后进行下架操作");
            	return false;
            }
        	var target = event.srcElement;
        	var cfmMsg = "确定要下架勾选的商品吗？";
        	var index = layer.confirm(cfmMsg, {
        		offset: $(target).offset().top,
        	    btn: ['确定','取消']
        	}, function(){
        		layer.close(index);
        		$("#startProgress").click();
        		$.get("${ctx}/product/batchOutShevlesProduct?productIdArr="+productIdArr,function(result){
        			if(result.success){
        				layer.close(index);
        				layer.msg('操作成功',{icon: 1,time: 1000},function(){
        					$('#searchForm').submit();
                    	});
        			}else{
        				alert(result.msg);
        				$("#my-modal-loading").modal('close');
        			}
            	},"json");
        	}, function(){
        	    layer.close(index);
        	});
        	
        })
    });
    
    
    function changeStatusFun(id,operStatus,itemCode){
    	var target = event.srcElement;
    	var cfmMsg = "确定要下架吗？";
    	if(operStatus == 3){
    		cfmMsg = "确定要上架吗？";
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
    
    function updatePriceFun(id,itemName,supplyPrice){
    	var target = event.srcElement;
    	var index = layer.prompt({
    		  shade:0.01,
    		  offset: $(target).offset().top,
    		  title:  "  分销价",
    		  formType: 0,
    		  value:supplyPrice
    		}, function(value, index, elem){
    			if(!isMoney(value)){
    				window.alert('请输入正确的金额');
    				return;
    			}
    			$.post('${ctx}/product/ajaxSave',{id:id,supplyPrice:value},function(result){
    				if(result.success){
    					layer.close(index);
    					layer.msg('操作成功',{icon: 1,time: 1000},function(){
    						$("#searchForm").submit();
    		           	});
    				}else{
    					window.alert(result.msg);
    				}
    			});
    		});
    }
</script>
</body>
</html>
