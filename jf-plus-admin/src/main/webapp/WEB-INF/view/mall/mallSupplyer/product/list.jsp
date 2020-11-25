<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商品表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
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
	$.post('${ctxS}/product/getStock',{productNo:productNo},function(result){
		if(result.success){
			$(tdId).html(result.obj.stockNum);
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
                            <div class="widget-title am-fl">商品管理</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-2 am-u-lg-2">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs">
                                        <button type="button" class="am-btn am-btn-default am-btn-success"
                                                onclick="openModel('创建商品','${ctxS}/product/create')"><span class="am-icon-plus"></span> 新增
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12 am-u-md-10 am-u-lg-10">
                                <form id="searchForm" action="${ctxS}/product/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="pageSize" name="orderBy" type="hidden" value="a.id desc"/>
                                    <input id="source" name="source" type="hidden" value="2"/>
                                    <input id="supplyId" name="supplyId" type="hidden" value="${session_supply_user.supplyId}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                    	<div class="am-u-sm-12 am-u-md-2 am-u-lg-2">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">ID</span>
											  <input type="text" class="am-form-field"  name="itemCode"  value="${page.entity.itemCode }">
											</div>
                                        </div>
                                    	<div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">名称</span>
											  <input type="text" class="am-form-field"  name="itemName"  value="${page.entity.itemName }">
											</div>
                                        </div>
                                    	<div class="am-u-sm-12 am-u-md-4 am-u-lg-4">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">分类</span>
										      <input type="hidden" class="am-form-field" id="itemCate" name="itemCate" value="${page.entity.itemCate }">
										      <input type="text" class="am-form-field" id="itemCateName" name="itemCateName" value="${page.entity.itemCateName }" onkeyup="if($.trim(this.value) == ''){$('#itemCate').val('')}">
										      <span class="am-input-group-btn">
										        <button class="am-btn am-btn-xs am-btn-primary" type="button" onclick="openModelAdjust('选择商品分类','${ctx}/mall/mallChannelCate/select?channelType=2')">选择</button>
										      </span>
										    </div>
										    <script>
										    	function itemCateCallback(data){
									    			$('#itemCate').val(data.catId).blur();
									    			$('#itemCateName').val(data.name).blur();
										    	}
										    </script>
                                        </div>
                                        <div class="am-u-sm-12 am-u-md-1 am-u-lg-1">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">状态</span>
											  <select class="am-input-sm" name="operStatus" data="${page.entity.operStatus}" style="min-width:100px;">
													<option value=" ">全部</option>
													<option value="3">上架</option>
													<option value="4">下架</option>
												</select>
											</div>
                                        </div>
                                        <span class="am-input-group-btn">
                                            <button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search" type="submit" onclick="initSearchForm()"></button>
                                        </span>
                                    </div>
                                </form>
                            </div>
                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                            <th>商品编码</th>
                                            <th width="35%">商品名称</th>
                                            <th>市场价</th>
                                            <th>供应价</th>
                                            <th>库存</th>
                                            <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="product" varStatus="status">
                                        <tr>
                                                <td><a onclick="openModelAdjust('商品详情','${ctx }/product/detail?productNo=${product.productNo}',false,false)">${product.itemCode}</a></td>
                                                <td>
                                                	<div class="am-u-md-4"><img class="" width="80" height="80" src="${product.photoUrl }"></div>
                                                	<div class="am-u-md-7">${product.itemName }</div>
                                                </td>
                                                <td>${product.markPrice}</td>
                                                <td>${product.supplyPrice}</td>
                                                <td id="stock${product.id }" class="error">
                                                	<script>
                                                		getStock('#stock${product.id }','${product.productNo }','${product.itemCode}');
                                                	</script>
								                </td>
                                                <td>
                                                	<c:if test="${product.operStatus == 3 }">
                                                		<span class="am-badge am-badge-success am-radius">${fnc:getEnumText("com.jf.plus.common.core.enums.ProductStatus",product.operStatus)}</span>
                                                	</c:if>
                                                	<c:if test="${product.operStatus == 4 }">
                                                		<span class="am-badge am-badge-danger am-radius">${fnc:getEnumText("com.jf.plus.common.core.enums.ProductStatus",product.operStatus)}</span>
                                                	</c:if>
                                                </td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                	<a role="oper" href="javascript:;" onclick="openModel('修改','${ctxS}/product/update?id=${product.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
	                                                <c:if test="${product.operStatus eq 3 }">
							                      		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary"  onclick="changeStatusFun('${product.id}','${prodcut.itemCode }','4')"><span class="am-icon-pencil-square-o"></span> 下架</a>
							                    	</c:if>
							                    	<c:if test="${product.operStatus eq 4 }">
							                    		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary"  onclick="changeStatusFun('${product.id}','${product.itemCode }','3')"><span class="am-icon-pencil-square-o"></span> 上架</a>
							                    	</c:if>
                                                </div>
                                            </td>
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
    
    
    function changeStatusFun(id,itemCode,operStatus){
    	var cfmMsg = "确定要下架吗？";
    	if(operStatus == 3){
    		cfmMsg = "确定要上架吗？";
    	}
    	
    	var index = layer.confirm(cfmMsg, {
    	    btn: ['确定','取消']
    	}, function(){
    		$.post('${ctxS}/product/changeStatus',{ids:id,itemCodes:itemCode,operStatus:operStatus},function(result){
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
