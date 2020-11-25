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
                        <div data-am-widget="tabs" class="am-tabs am-tabs-default am-u-sm-7" >
					      <ul class="am-tabs-nav am-cf">
					          <li class="${page.condition.source == 1 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/mallSetting/packsInfo/selectProduct?operStatus=3&source=1&packsId=${packsId }'">京东商品</a></li>
					          <li class="${page.condition.source == 3 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/mallSetting/packsInfo/selectProduct?operStatus=3&source=3&packsId=${packsId }'">苏宁商品</a></li>
					          <li class="${page.condition.source == 5 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/mallSetting/packsInfo/selectProduct?operStatus=3&source=5&packsId=${packsId }'">齐心商品</a></li>
					          <li class="${page.condition.source == 4 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/mallSetting/packsInfo/selectProduct?operStatus=3&source=4&packsId=${packsId }'">严选商品</a></li>
					          <li class="${page.condition.source == 2 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/mallSetting/packsInfo/selectProduct?operStatus=3&source=2&packsId=${packsId }'">供应商商品</a></li>
					      </ul>
					  </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/mallSetting/packsInfo/selectProduct?operStatus=3&source=${page.condition.source}&packsId=${packsId }" method="post">
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
                                                	<div class="am-u-md-4"><img class="am-img-thumbnail" width="140" height="140" src="${product.photo }"></div>
                                                	<div class="am-u-md-8">${product.itemName }</div>
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

<button
  id="startProgress"
  type="button"
  class="am-btn am-btn-success"
  data-am-modal="{target: '#my-modal-loading'}">
  加载进度条
</button>

<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="my-modal-loading">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">正在将商品加入到该礼包中，请稍等！</div>
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
    });
    
    
    function pickToSpecial(itemId,operStatus,source){
    	$("#startProgress").click();
    	var target = event.srcElement;
   		$.post('${ctx}/mallSetting/packsInfo/pick',{id:itemId,operStatus:operStatus,source:source,packsId:'${packsId}'},function(result){
   			if(result.success){
   				layer.msg('操作成功',{icon: 1,time: 1000},function(){
					$(target).text('已加入').attr('disabled',true);
					$(target).parents('tr').find('input[type="checkbox"]').remove();
					$("#my-modal-loading").modal('close');
	           	});
   			}else{
   				window.alert(result.msg);
   			}
   		})
    }
</script>
</body>
</html>
