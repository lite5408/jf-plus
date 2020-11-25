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
var province = '${page.condition.province}',
city = '${page.condition.city}',
county = '${page.condition.county}';

function getDistrict(selectInx,parent_id){
	//console.log(selectInx+" "+parent_id)
	$.post("${ctx}/setting/district/districtList",{parentId:parent_id,source:'${page.condition.source}'},function(result){
		districtCallback(selectInx,result);
	});
}

function districtCallback(selectInx,result){
	if(result.success){
		var $select = $('#disctrict_'+selectInx);
		$select.find('option[value!="-1"]').remove();
		
		var $nextSelect = $('#disctrict_'+(selectInx+1));
		if($nextSelect.length > 0){
			$nextSelect.find('option[value!="-1"]').remove();
		}
		
		var shtml = '';
		var distList = result.obj;
		var i=0,len=distList.length;
		for(;i<len;i++){
			shtml+='<option value="'+distList[i].channelId+'">'+distList[i].name+'</option>';
		}
		$select.append(shtml);
		//绑定change
		$select.change(function(){
			if($nextSelect.length > 0 && $(this).children('option:selected').val() > -1){
				getDistrict((selectInx+1),$(this).children('option:selected').val());
			}
		});
		
		//加载默认地址
		if(selectInx == 1 && province != '' && province != '-1'){
			$('#disctrict_1').val(province);
			getDistrict((selectInx+1),province);
		}else if(selectInx == 2 && city != '' && city != '-1'){
			$('#disctrict_2').val(city);
			getDistrict((selectInx+1),city);
		}else if(selectInx == 3 && county != '' && county != '-1'){
			$('#disctrict_3').val(county);
		}
		
	}
}

$(function(){
	$district1 = $('#disctrict_1');
	if($district1.length > 0){
		getDistrict(1,0);
	}
})

function getStock(tdId,itemId,skuId){
	var source = '${page.condition.source}';
	if(source == '1'){
		getJDStock(tdId,itemId,skuId);
	}else if(source == '3'){
		getSNStock(tdId,itemId,skuId);
	}else if(source == '4'){
		getYXStock(tdId,itemId,skuId);
	}else if(source == '5'){
		getQXStock(tdId,itemId,skuId);
	}
}

function getJDStock(tdId,itemId,skuId){
	if(county <= 0){
		$(tdId).html('渠道库存');
		return;
	}
	var postdata = {source:"${page.condition.source}","itemId":itemId,"skuId":skuId,"num":1};
	postdata.provinceId = province;
	postdata.cityId = city;
	postdata.countyId = county;
	
	checkStock(tdId,postdata);
}

function getSNStock(tdId,itemId,skuId){
	if(county <= 0){
		$(tdId).html('渠道库存');
		return;
	}
	var postdata = {source:"${page.condition.source}","itemId":itemId,"skuId":skuId,"num":1};
	postdata.provinceId = province;
	postdata.cityId = city;
	postdata.countyId = county;
	
	checkStock(tdId,postdata);
}

function getYXStock(tdId,itemId,skuId){
	var postdata = {source:"${page.condition.source}","itemId":itemId,"skuId":skuId,"num":1};
	checkStock(tdId,postdata);
}

function getQXStock(tdId,itemId,skuId){
	var postdata = {source:"${page.condition.source}","itemId":itemId,"skuId":skuId,"num":1};
	checkStock(tdId,postdata);
}

function checkStock(tdId,postdata){
	$.post('${ctx}/product/productStock/getStock',postdata,function(result){
		if(result.success){
			if(result.obj[0].status == 1){
				$(tdId).html('有货');
			}else{
				$(tdId).html('缺货');
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
                            <div class="widget-title am-fl"><c:if test="${page.condition.operStatus == 3 }">上架的商品</c:if><c:if test="${page.condition.operStatus == 4 }">下架的商品</c:if></div>
                        </div>
                        <div data-am-widget="tabs" class="am-tabs am-tabs-default am-u-sm-7" >
					      <ul class="am-tabs-nav am-cf">
					          <li class="${page.condition.source == 1 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list/my?source=1&operStatus=${page.condition.operStatus}'">京东商品</a></li>
					          <li class="${page.condition.source == 3 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list/my?source=3&operStatus=${page.condition.operStatus}'">苏宁商品</a></li>
					          <li class="${page.condition.source == 5 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list/my?source=5&operStatus=${page.condition.operStatus}'">齐心商品</a></li>
					          <li class="${page.condition.source == 4 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list/my?source=4&operStatus=${page.condition.operStatus}'">严选商品</a></li>
					          <li class="${page.condition.source == 2 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list/my?source=2&operStatus=${page.condition.operStatus}'">供应商商品</a></li>
					      </ul>
					  </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/site/siteProduct/list/my?source=${page.condition.source}&operStatus=${page.condition.operStatus}" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="source" name="source" type="hidden" value="${page.condition.source}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                    	<table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">分类</td>
                                    			<td>
											  		<div class="am-input-group am-input-group-sm am-input-group-primary">
												      <input type="hidden" class="am-form-field" id="itemCate" name="itemCate" value="${page.condition.channelItemCate }">
												      <input type="text" class="am-form-field" id="itemCateName" name="itemCateName" value="${page.condition.channelItemCateName }" onkeyup="if($.trim(this.value) == ''){$('#itemCate').val('')}">
												      <span class="am-input-group-btn">
												        <button class="am-btn am-btn-xs am-btn-primary" type="button" onclick="openModelAdjust('选择商品分类','${ctx}/mall/mallChannelCate/select?channelType=${page.condition.source }')">选择</button>
												      </span>
												    </div>
												    <script>
												    	function itemCateCallback(data){
											    			$('#itemCate').val(data.catId);
											    			$('#itemCateName').val(data.name);
												    	}
												    </script>
												</td>
												<c:if test="${page.condition.source == 1 or page.condition.source == 3 }">
													<td class="am-padding-sm am-text-sm am-text-right">库存</td>
	                                    			<td colspan="5">
	                                    				<select id="disctrict_1" name="province" data-am-selected="{btnSize:'sm'}">
														  <option value="-1">省份</option>
														</select>
	                                    				<select id="disctrict_2" name="city" data-am-selected="{btnSize:'sm'}">
														  <option value="-1">市/区</option>
														</select>
	                                    				<select id="disctrict_3" name="county" data-am-selected="{btnSize:'sm'}">
														  <option value="-1">区/县</option>
														</select>
	                                    			</td>
                                    			</c:if>
                                    		</tr>
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">供应价</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="supplyPriceStart"  value="${page.condition.supplyPriceStart }" placeholder="最小价格">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">-</td>
												<td>
													<input type="text" class="am-input-sm"  name="supplyPriceEnd"  value="${page.condition.supplyPriceEnd }" placeholder="最大价格">
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">利润比(%)</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="profitStart"  value="${page.condition.profitStart }" placeholder="最小价格差">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">-</td>
												<td>
													<input type="text" class="am-input-sm"  name="profitEnd"  value="${page.condition.profitEnd }" placeholder="最大价格差">
												</td>
                                    		</tr>
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
                                    			<c:if test="${ page.condition.operStatus == 3 }">
                                    				<td class="am-padding-sm"><input class="am-btn am-btn-xs am-btn-warning" id="batchOutShevlesProduct" type="button" value="批量下架"/></td>
												</c:if>
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
                                                <td><a onclick="openModelAdjust('商品详情','${ctx }/product/detail?productNo=${product.productNo}',false,false)">${product.itemCode}</a></td>
                                                <td>
                                                	<div class="am-u-md-4"><img class="am-img-thumbnail" width="140" height="140" src="${product.photo }"></div>
                                                	<div class="am-u-md-8">${product.itemName }</div>
                                                </td>
                                                <td>${product.supplyName}</td>
                                                <td>${product.supplyPrice}</td>
                                                <td>${product.salePrice}</td>
                                                <td>${product.profitPercent}</td>
                                                <td id="stock${product.id }" class="error">
								                	<script>
								                		getStock('#stock${product.id }','${product.productId }','${product.itemCode}');
								                	</script>
								                </td>
                                                <td>${fnc:getEnumText("com.jf.plus.common.core.enums.ProductStatus",product.operStatus)}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                	<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="updatePriceFun('${product.id}','${product.itemName }','${product.salePrice }')"><span class="am-icon-jpy"></span> 改价</a>
	                                                <c:if test="${product.operStatus eq 3 }">
							                      		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="changeStatusFun('${product.id}',4)"> 下架</a>
							                    	</c:if>
							                    	<c:if test="${product.operStatus eq 4 }">
							                    		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="changeStatusFun('${product.id}',3)"> 上架</a>
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
        
      //复选框所有
    	$('#checkAll').click(function(){
    		var checked = $(this).prop('checked') ? true:false;
    		$('input[name="rowId"]').prop('checked',checked);
    	});
        
        $("#batchOutShevlesProduct").click(function(){
        	var rowList = document.getElementsByName("rowId");
        	var siteProductIdArr = [];
            for(r in rowList){
            	if(rowList[r].checked)
            		siteProductIdArr.push(rowList[r].value);
            }
            if(siteProductIdArr.length == 0){
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
        		$.get("${ctx}/site/siteProduct/batchOutShevlesProduct?siteProductIdArr="+siteProductIdArr,function(result){
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
    
    
    function changeStatusFun(id,operStatus){
    	var cfmMsg = "确定要下架吗？";
    	if(operStatus == 3){
    		cfmMsg = "确定要上架吗？";
    	}
    	
    	var index = layer.confirm(cfmMsg, {
    	    btn: ['确定','取消']
    	}, function(){
    		$.post('${ctx}/site/siteProduct/changeStatus',{ids:id,operStatus:operStatus},function(result){
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
    
    function updatePriceFun(id,itemName,salePrice){
    	var target = event.srcElement;
    	var index = layer.prompt({
    		  shade:0.01,
    		  offset: $(target).offset().top,
    		  title:  "  销售价",
    		  formType: 0,
    		  value:salePrice
    		}, function(value, index, elem){
    			if(!isMoney(value)){
    				window.alert('请输入正确的金额');
    				return;
    			}
    			$.post('${ctx}/site/siteProduct/ajaxSave',{id:id,salePrice:value},function(result){
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
