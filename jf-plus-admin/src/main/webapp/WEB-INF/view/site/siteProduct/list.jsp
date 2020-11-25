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
	$.post("${ctx}/setting/district/districtList",{parentId:parent_id,source:'${page.entity.source}'},function(result){
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
	var source = '${page.entity.source}';
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
	var postdata = {source:"${page.entity.source}","itemId":itemId,"skuId":skuId,"num":1};
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
	var postdata = {source:"${page.entity.source}","itemId":itemId,"skuId":skuId,"num":1};
	postdata.provinceId = province;
	postdata.cityId = city;
	postdata.countyId = county;
	
	checkStock(tdId,postdata);
}

function getYXStock(tdId,itemId,skuId){
	var postdata = {source:"${page.entity.source}","itemId":itemId,"skuId":skuId,"num":1};
	checkStock(tdId,postdata);
}

function getQXStock(tdId,itemId,skuId){
	var postdata = {source:"${page.entity.source}","itemId":itemId,"skuId":skuId,"num":1};
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
                            <div class="widget-title am-fl">待挑选商品</div>
                        </div>
                        <div data-am-widget="tabs" class="am-tabs am-tabs-default am-u-sm-7" >
					      <ul class="am-tabs-nav am-cf">
					          <li class="${page.entity.source == 1 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list?source=1&operStatus=3'">京东商品</a></li>
					          <li class="${page.entity.source == 3 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list?source=3&operStatus=3'">苏宁商品</a></li>
					          <li class="${page.entity.source == 5 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list?source=5&operStatus=3'">齐心商品</a></li>
					          <li class="${page.entity.source == 4 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list?source=4&operStatus=3'">严选商品</a></li>
					          <li class="${page.entity.source == 2 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/site/siteProduct/list?source=2&operStatus=3'">供应商商品</a></li>
					      </ul>
					  </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/site/siteProduct/list?operStatus=${page.entity.operStatus}" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="source" name="source" type="hidden" value="${page.entity.source}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                    	<table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">分类</td>
                                    			<td>
											  		<div class="am-input-group am-input-group-sm am-input-group-primary">
												      <input type="hidden" class="am-form-field" id="itemCate" name="itemCate" value="${page.entity.itemCate }">
												      <input type="text" class="am-form-field" id="itemCateName" name="itemCateName" value="${page.entity.itemCateName }" onkeyup="if($.trim(this.value) == ''){$('#itemCate').val('')}">
												      <span class="am-input-group-btn">
												        <button class="am-btn am-btn-xs am-btn-primary" type="button" onclick="openModelAdjust('选择商品分类','${ctx}/mall/mallChannelCate/select?channelType=${page.entity.source }')">选择</button>
												      </span>
												    </div>
												    <script>
												    	function itemCateCallback(data){
											    			$('#itemCate').val(data.catId);
											    			$('#itemCateName').val(data.name);
												    	}
												    </script>
												</td>
												<c:if test="${page.entity.source == 1 or page.entity.source == 3 }">
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
											  		<input type="text" class="am-input-sm"  name="itemCode"  value="${page.entity.itemCode }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">名称</td>
												<td>
													<input type="text" class="am-input-sm"  name="itemName"  value="${page.entity.itemName }">
												</td>
												<td class="am-padding-sm"><button class="am-btn am-btn-xs am-btn-success am-icon-search"> 查询</button></td>
                                    		</tr>
                                    	</table>
                                    </div>
                                </form>
                            </div>
							<div class="am-u-sm-12">
					          <div class="am-btn-toolbar">
					            <div class="am-btn-group am-btn-group-xs">
						            <button id="batchPickBtn" class="am-btn am-btn-success"><span class="am-icon-plus"></span> 批量挑选</button>
					            </div>
					          </div>
					        </div>
                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                    		<th class="table-check"><input type="checkbox" id="checkAll"/></th>
                                            <th>商品编码</th>
                                            <th width="35%">商品名称</th>
                                            <th>供应商</th>
                                            <th>市场价</th>
                                            <th>供应价</th>
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
                                                	<c:if test="${product.operStatus eq 1 }">
									                	<input type="checkbox" name="rowId" value="${product.id }"/>
									                </c:if>
								                </td>
                                                <td><a onclick="openModelAdjust('商品详情','${ctx }/product/detail?productNo=${product.productNo}',false,false)">${product.itemCode}</a></td>
                                                <td>
                                                	<div class="am-u-md-4"><img class="am-img-thumbnail" width="140" height="140" src="${product.photoUrl }"></div>
                                                	<div class="am-u-md-8">${product.itemName }</div>
                                                </td>
                                                <td>${product.supplyName}</td>
                                                <td>${product.markPrice}</td>
                                                <td>${product.supplyPrice}</td>
                                                <td>${product.profitPercent}</td>
                                                <td id="stock${product.id }" class="error">
								                	<script>
								                		getStock('#stock${product.id }','${product.id }','${product.itemCode}');
								                	</script>
								                </td>
                                                <td>${fnc:getEnumText("com.jf.plus.common.core.enums.ProductStatus",product.operStatus)}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
	                                                <c:if test="${product.operStatus eq 1 }">
							                      		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary"  name="pickBtn" onclick="prePick('${product.id}','${product.itemName }',${product.supplyPrice })"><span class="am-icon-pencil-square-o"></span> 挑选商品</a>
							                    	</c:if>
							                    	<c:if test="${product.operStatus eq 2 }">
							                    		已加入
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
<div class="am-modal am-modal-no-btn" tabindex="-1" id="price-modal">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">批量挑选加入
      <a href="javascript:void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd" id="priceDiv" style="text-align:left;">
    	<div class="am-panel-group" id="accordion">
		  <div class="am-panel am-panel-success">
		    <div class="am-panel-hd">
		      	销售价
		    </div>
		  </div>
		 </div>
			 <form class="am-form-inline" role="form" id="batchPickForm" onsubmit="return false">
			  <div class="am-radio">
			    <input type="radio" name="priceWay" value="adjust" checked="checked" required > 按照
			  </div>
			  <div class="am-form-group am-form-select">
			     <select class="am-form-field" name="priceType" data-am-selected="{btnSize:'sm',btnWidth:'100px'}">
			    	<option value="supplyPrice">供应价</option>
			    	<option value="markPrice">市场价</option>
			    </select>
			  </div>
			  <div class="am-form-group am-form-select">
			    <select class="am-form-field" name="priceAdjust" data-am-selected="{btnSize:'sm',btnWidth:'100px'}">
			    	<option value="incr" selected="selected">上调</option>
			    	<option value="desc">下降</option>
			    </select>
			  </div>
			  <div class="am-form-group">
			    <input type="number" min="0" class="am-form-field" name="priceVal" style="width:100px;border:1px #eee solid;" placeholder="输入值">
			  </div>
			  <div class="am-form-group am-form-select">
			    <select class="am-form-field" name="priceUnit" data-am-selected="{btnSize:'sm',btnWidth:'100px'}">
			    	<option value="profit" selected="selected">%</option>
			    	<option value="rmb">元</option>
			    </select>
			  </div>
			  <p></p>
			  <div class="am-radio">
			    <input type="radio" name="priceWay" value="eq" required> 等于
			  </div>
			  <div class="am-form-group am-form-select">
			    <select class="am-form-field" name="priceType1" data-am-selected="{btnSize:'sm',btnWidth:'100px'}">
			    	<option value="supplyPrice" selected="selected">供应价</option>
			    	<option value="markPrice">市场价</option>
			    </select>
			  </div>
			  <div style="text-align:center;margin-top:10px;">
	  		  	<button class="am-btn am-btn-xs am-btn-primary" type="submit">保存</button>
	  			<button class="am-btn am-btn-default" type="button" onclick="javascript:$('#price-modal').modal('close');">取消</button>
	  		  </div>
			</form>
			
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
    <div class="am-modal-hd">正在挑选上架该商品，请稍等！</div>
    <div class="am-modal-bd">
      <span class="am-icon-spinner am-icon-spin"></span>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
	$("#startProgress").hide();

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
	
	$('#batchPickBtn').click(function(){
		var $checkedRows = $('input[name="rowId"]:checked');
		if($checkedRows.length == 0){
			window.alert('请勾选商品，再进行操作');
			return false;
		}
		
		$('#batchPickForm')[0].reset();
		var offset = $(event.srcElement).offset().top;
		$('#price-modal').modal('open').css({'margin-top':-offset*2 + "px"});
	});
	
	$('#batchPickForm').validator({
		submit:function() {
			var formValidity = this.isFormValid();
		    if(formValidity){
		    	if($('input[name="priceWay"]:checked').val() == 'adjust' && $.trim($('input[name="priceVal"]').val()) == ''){
		    		alert('请输入调整价格');
		    		return false;
		    	}
		    	
		    	var postdata = {};
		    	var checkedRows = $('input[name="rowId"]:checked');
		    	
		    	var productIds = "";
		    	$.each(checkedRows,function(){
		    		productIds += $(this).val() + ',';
		    	});
		    	if(productIds.length > 0){
		    		productIds = productIds.substring(0,productIds.length - 1);
		    	}
		    	postdata.productIds = productIds;
		    	postdata.priceWay = $('input[name="priceWay"]:checked').val();
		    	postdata.priceType = $('input[name="priceWay"]:checked').val() == 'eq' ? $('select[name="priceType1"]').val() : $('select[name="priceType"]').val();
		    	postdata.priceAdjust = $('select[name="priceAdjust"]').val();
		    	postdata.priceVal = $('input[name="priceVal"]').val();
		    	postdata.priceUnit = $('select[name="priceUnit"]').val();
		    	
		    	$("#startProgress").click();
				
		    	$.post('${ctx}/site/siteProduct/batchPrePick',postdata,function(result){
	                if (result.success) {
	                	layer.msg('操作成功',{icon: 1,time: 1000},function(){
	                		$.each(checkedRows,function(){
	                			$(this).parents('tr').find('a[name="pickBtn"]').text('已加入').attr('disabled','disabled');
	        		    		$(this).remove();
	        		    	});
	                		
	                		$('#price-modal').modal('close');
	                		$("#my-modal-loading").modal('close');
	                	});
	                }else{
	                	$("#my-modal-loading").modal('close');
	                    window.alert(result.msg);
	                }
				});
		   }else{
			   return formValidity;
		   }
		}
	});
    
    function prePick(id,itemName,supplyPrice){
    	var target = event.srcElement;
    	var index = layer.prompt({
    		  shade:0.01,
    		  offset: $(target).offset().top,
    		  title: "  销售价",
    		  formType: 0,
    		  value:supplyPrice
    		}, function(value, index, elem){
    			if(!isMoney(value)){
    				alert('请输入正确的金额');
    				return;
    			}
    			$("#startProgress").click();
    			$.post('${ctx}/site/siteProduct/prePick',{productId:id,salePrice:value},function(result){
    				if(result.success){
    					layer.close(index);
    					layer.msg('操作成功',{icon: 1,time: 1000},function(){
    						$("#my-modal-loading").modal('close');
    						$(target).text('已加入').attr('disabled',true);
    						$(target).parents('tr').find('input[type="checkbox"]').remove();
    		           	});
    				}else{
    					alert(result.msg);
    					$("#my-modal-loading").modal('close');
    				}
    			});
    		});
    }
</script>
</body>
</html>
