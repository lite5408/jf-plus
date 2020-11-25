<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商品订单</title>
    <%@ include file="../../include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
        .detailTable td{padding:5px 10px;}
        input{
        	height:30px;
        }
        input[readonly]{
        	background:#f5f5f5;
        }
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
                        <div class="widget-body am-fr">
                        	<div class="am-u-sm-12 am-margin-sm">
                        		<div>订单分配</div>
                        	</div>
                            <div class="am-u-sm-12">
                            	<div class="am-u-sm-6">
                            		<table class="detailTable">
                            			<tr>
                            				<td class="am-text-sm am-link-muted">商品编码:</td><td class="am-text-sm">${siteProduct.itemCode }</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm am-link-muted">名称:</td><td class="am-text-sm">${siteProduct.itemName }</td>
                            			</tr>
                            			<tr>
                            				<td class="am-text-sm am-link-muted">状态:</td><td class="am-text-sm am-text-warning">
                            					<c:if test="${empty page.entity.isDist or page.entity.isDist eq 0}">
                                                		待分配
                                                	</c:if>
                                                	<c:if test="${page.entity.isDist eq 1}">
                                                		已分配
                                                	</c:if>
                            				</td>
                            			</tr>
                            		</table>
                            	</div>
                            	<div class="am-u-sm-6">
                            	</div>
                            </div>
                            
                            <div class="am-u-sm-12 am-margin-sm">
                        		<div>订单分配</div>
                        	</div>
                            <div class="am-u-sm-12">
                            	<div class="am-u-sm-12">
                            		<table class="detailTable am-table am-table-bordered">
                            			<tr>
                            				<th class="am-text-sm"><input type="checkbox" id="checkAll" checked="true"></th>
                            				<th class="am-text-sm">代理商</th>
                            				<th class="am-text-sm" width="35%">订购数量</th>
                            				<th class="am-text-sm">分配数量</th>
                            			</tr>
                            			<tr>
                            				<td colspan="2"></td>
                            				<td colspan="1">${saleNum }</td>
                            				<td colspan="1" class="am-form-group">
											      <input type="number" name="distStock" class="am-input-sm"/>
											      <button class="am-btn am-btn-warning am-btn-xs" type="button" onclick="distFun()">分配</button>
                            				</td>
                            			</tr>
                            			<c:forEach items="${page.list }" var="row" varStatus="status">
	                            			<tr>
	                            				<td class="am-text-sm"><input name="checkRow" type="checkbox" checked="true" onclick="readonlyInput()"></td>
	                            				<td class="am-text-sm">${row.orgName }</td>
	                            				<td class="am-text-sm">${row.saleNum }</td>
	                            				<td class="am-text-sm">
	                            					<input type="number" org-id="${row.orgId }" sale-num="${row.saleNum }" name="distNum" class="am-input-sm"/>
	                            				</td>
	                            			</tr>	
                            			</c:forEach>
                            		</table>
                            	</div>
                            </div>

                        </div>
                       	<div class="am-u-sm-12 am-text-center">
                            <button type="button" class="am-btn am-btn-xs am-btn-primary" onclick="submitForm()">提交</button>
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
    
    $('#checkAll').click(function(){
    	$('input[name=checkRow]').prop('checked',$(this).prop('checked'))
    	readonlyInput();
    });
    
    function readonlyInput(){
    	$('input[name=checkRow]').each(function(){
    		if($(this).prop('checked')){
    			$(this).parent().parent().find('input[name=distNum]').removeAttr('readonly');
    		}else{
    			$(this).parent().parent().find('input[name=distNum]').attr('readonly','readonly').val('');
    		}
    	})
    }
    
    readonlyInput();
    
    function distFun(){
    	var distStock = $('input[name=distStock]').val();
    	if(isNull(distStock) || distStock < 0){
    		alert('请输入正确的数量');
    		return false;
    	}
    	
    	var totalNum = 0;
    	$('input[name=checkRow]:checked').each(function(){
    		totalNum += parseInt($(this).parent().parent().find('input[name=distNum]:first').attr('sale-num'));
    	});
    	
    	var i = 0;
    	var hasDistStock = 0;
    	$('input[name=checkRow]:checked').each(function(){
    		i++;
    		var $saleNumInput = $(this).parent().parent().find('input[name=distNum]:first');
    		var perfect = parseInt($saleNumInput.attr('sale-num'))/totalNum;
    		var distNum = parseInt(perfect*distStock);
    		if(i < $('input[name=checkRow]:checked').length){
	    		hasDistStock += distNum;
    			$saleNumInput.val(distNum);
    		}else{
    			$saleNumInput.val(distStock - hasDistStock);
    		}
    	});
    	
    	
    }
    
    function submitForm(){
    	var postdata = new Array();
    	
    	var totalNum = 0;
    	$('input[name=checkRow]:checked').each(function(){
    		var $saleNumInput = $(this).parent().parent().find('input[name=distNum]:first');
    		totalNum+= parseInt($saleNumInput.val());
    		postdata.push({
    			distNum:parseInt($saleNumInput.val()),
    			orgId:$saleNumInput.attr('org-id'),
    			itemId:'${siteProduct.id}',
    		})
    	});
    	
    	if(totalNum <= 0 || isNull($('input[name=distStock]').val())){
    		alert('请输入正确分配数量');
    		return false;
    	}
    	
    	$.ajax({
    	    method: "POST",
    	    url: "${ctx}/order/distOrderDetail/autoDist",
    	    contentType: 'application/json',
    	    data:JSON.stringify({orderDetailList:postdata,distStock: $('input[name=distStock]').val()}),
    	    success: function( result ) {
    	    	if(result.success){
        			showMsg(result.msg);
        			closeModel(true);
        		}else{
        			alert(result.msg);
        		}
    	   }
    	});
    }
    
</script>
</body>
</html>
