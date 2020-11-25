<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>管理电子券</title>
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
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">电子券卡券信息</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-md-4">
                            	<div class="am-panel am-panel-default">
					            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-1'}">电子券信息<span class="am-icon-chevron-down am-fr"></span></div>
					            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-1">
					            	<form class="am-form" id="submitForm" onsubmit="return false">
							    		<input type="hidden" name="id" value="${voucher.id }">
							    		<div class="am-g am-margin-top">
								            <div class="am-u-md-5 am-text-right">电子券图片</div>
								            <div class="am-u-md-7 am-u-end col-end am-u-end col-end">
								              <img src="" class="am-img-responsive am-img-thumbnail" alt="" id="filePreview" width="120" height="120">
								              <input type="hidden" class="am-input-sm" id="photo" name="photoUrl" value="${voucher.photoUrl }" required>
								            </div>
							            </div>
										<div class="am-g am-margin-top">
							              <div class="am-u-md-5 am-text-right">电子券名称</div>
							              <div class="am-u-md-7 am-u-end col-end am-u-end col-end">
							                ${voucher.name}
							              </div>
							            </div>
							            <div class="am-g am-margin-top">
							              <div class="am-u-md-5 am-text-right">电子券面值（元）</div>
							              <div class="am-u-md-7 am-u-end col-end am-u-end col-end">
							               <c:choose><c:when test='${voucher.price==-1}'>无</c:when><c:otherwise>${voucher.price}</c:otherwise></c:choose>
							              </div>
							            </div>
							            <div class="am-g am-margin-top">
							              <div class="am-u-md-5 am-text-right">开始时间</div>
							              <div class="am-u-md-7 am-u-end col-end am-u-end col-end">
							                <fmt:formatDate value='${voucher.validStartDate}' pattern='yyyy-MM-dd' />
							              </div>
							            </div>
							            <div class="am-g am-margin-top">
							              <div class="am-u-md-5 am-text-right">失效时间</div>
							              <div class="am-u-md-7 am-u-end col-end am-u-end col-end">
							                <fmt:formatDate value='${voucher.validEndDate}' pattern='yyyy-MM-dd' />
							              </div>
							            </div>
							            <div class="am-g am-margin-top">
							              <div class="am-u-md-5 am-text-right">状态</div>
							              <div class="am-u-md-7 am-u-end col-end am-u-end col-end">
							              	<c:choose><c:when test='${empty voucher.operStatus or voucher.operStatus==0}'>禁用</c:when><c:otherwise>启用</c:otherwise></c:choose>
							              </div>
							            </div>
							         </form>
					            </div>
					          </div>
                            </div>
                            
                            <div class="am-u-md-8">
                        		<div class="am-g">
							        <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
							          <form class="am-form-inline" role="form" action="${ctx}/account/voucher/cardPage" method="post" id="searchForm">
							          	  <input id="id" name="voucherId" type="hidden" value="${voucher.id}"/>
							          	  <input id="page" name="pageNo" type="hidden" value="${page.pageNo}"/>
										  <input id="rows" name="pageSize" type="hidden" value="${page.pageSize}"/>
										  <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
	                                        <table class="am-u-sm-12">
	                                            <tr>
	                                                <td class="am-padding-sm am-text-sm am-text-right">激活状态</td>
	                                                <td>
	                                                    <select name="operStatus" data="${page.entity.operStatus}">
		                                                    <option value="">请选择激活状态</option>
															<option value="1">已激活</option>
															<option value="0">未激活</option>
														</select>
	                                                </td>
	                                                <td class="am-padding-sm am-text-sm am-text-right">电子券卡号</td>
	                                                <td>
	                                                    <input type="text" name="couponAccount" class="am-input-sm am-form-field" value="${page.entity.couponAccount }">
	                                                </td>
	                                                <td class="am-padding-sm am-text-sm am-text-right">绑定人手机</td>
	                                                <td>
	                                                    <input type="text" name="mobile" class="am-input-sm am-form-field" value="${page.entity.mobile }">
	                                                </td>
	                                                <td class="am-padding-sm"><button class="am-btn am-btn-xs am-btn-success am-icon-search"> 查询</button></td>
	                                            </tr>
	                                        </table>
		                                   </div>
									  </form>
							        </div>
							        <div class="am-margin-left-default am-padding-left-xl">
							          <div class="am-btn-toolbar">
							            <div class="am-btn-group am-btn-group-xs">
								            <button type="button" class="am-btn am-btn-success" data-am-modal="{target: '#my-modal', closeViaDimmer: 0, width: 600, height: 300}"><span class="am-icon-plus"></span> 生成卡号</button>
							             	<a href="${ctx}/export/excel/voucherAccCash?voucherId=${voucher.id}&operStatus=${page.entity.operStatus}&couponAccount=${page.entity.couponAccount}&mobile=${page.entity.mobile}" class="am-btn am-btn-success"><span class="am-icon-file-excel-o"></span> 导出</a>
							             	<a href="javascript:" class="am-btn am-btn-success" onclick="activeSelected()"><span class="am-icon-cog"></span> 激活选中</a>
							            </div>
							          </div>
							        </div>
							      </div>
							      
							      <div class="am-g">
							        <div class="am-u-sm-12">
							            <table class="am-table am-table-striped am-table-hover table-main">
							              <thead>
							              	<tr>
							              		<th class="table-check"><input type="checkbox" id="checkAll"/></th>
												<th class="table-title">电子券卡号</th>
												<th class="table-title">电子券兑换码</th>
												<th class="table-title">余额</th>
												<th class="table-title">最新绑定用户</th>
												<th class="table-title">用户名</th>
												<th class="table-title">手机</th>
												<th class="table-title">最新绑定订单</th>
												<th class="table-title">最新绑定时间</th>
												<th class="table-title">状态</th>
												<th class="table-title">操作</th>
											</tr>
							              </thead>
							              <tbody>
								              <c:forEach items="${page.list}" var="row">
									              <tr>
									              	<td>
									              		<c:if test="${row.operStatus eq 0}">
									              			<input type="checkbox" name="rowId" value="${row.id }"/>
									              		</c:if>
													</td>
									                <td>${row.couponAccount }</td>
									                <td>${row.couponCode}</td>
									                <td>${row.blance }</td>
									                <td>${row.bindUser }</td>
									                <td>${row.userName }</td>
									                <td>${row.mobile }</td>	                
									                <td>${row.bindOrder }</td>
									                <td><fmt:formatDate pattern='yyyy-MM-dd' value='${row.bindDate }' /></td>
									                <td><c:if test="${row.operStatus eq 1 }">启用</c:if><c:if test="${row.operStatus eq 0 }">禁用</c:if></td>
									                <td>
									                	<div class="am-btn-toolbar">
									                		<div class="am-btn-group am-btn-group-xs">
										                	<c:if test="${row.operStatus eq 1 }"><button class="am-btn am-btn-default am-btn-sm am-hide-sm-only" title="点击禁用" type="button" onclick="changeStatus(${row.id},0)"><i class="am-icon-cog"></i> 禁用</button></c:if>
										                	<c:if test="${row.operStatus eq 0 }"><button class="am-btn am-btn-default am-btn-sm am-text-secondary" title="点击启用" type="button" onclick="changeStatus(${row.id},1)"><i class="am-icon-cog"></i> 激活</button></c:if>
									                		</div>
									                	</div>
									                </td>
									              </tr>
								              </c:forEach>
							              </tbody>
							            </table>
							          </div>
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
</div>
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-modal">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">生成电子券卡券</div>
    <div class="am-modal-bd">
	    <div class="widget-body am-fr">
	      <form class="am-form tpl-form-border-form" data-am-validator id="batchCreateCash" action="${ctx}/account/voucherAccCash/batchCreateCash" method="post">
			<input name="voucherId" type="hidden" value="${voucher.id }"/>
			    <div class="am-form-group">
	                <label class="am-u-sm-4 am-form-label">卡券前缀：</label>
	                <div class="am-u-sm-8">
	                    <input type="text" name="prefix" placeholder="例如：ISDN" required/>
	                </div>
	            </div>
	            <div class="am-form-group">
	                <label class="am-u-sm-4 am-form-label">生成数量：</label>
	                <div class="am-u-sm-8">
	                    <input type="number" name="num" placeholder="请输入生成卡券数量" required/>
	                </div>
	            </div>
	            <div class="am-form-group">
	                <div class="am-u-sm-9 am-u-sm-push-3">
	                	<button type="submit" class="am-btn am-btn-xs am-btn-primary">确认生成</button>
	                    <a href="javascrip:" onclick="closeModal()" class="am-btn am-btn-xs am-btn-danger">取消</a>
	                </div>
	            </div>
		  </form>
		</div>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
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
      
        initSelectValue(true);//初始化下拉框的值
    });
    
    function activeSelected(){
		var $checkedRows = $('input[name="rowId"]:checked');
		if($checkedRows.length == 0){
			window.alert('请勾选电子卡券，再进行操作');
			return false;
		}
		
	    var voucherAccCashArr = [];
		for (var i = 0; i < $checkedRows.length; i++) {
			var value = $($checkedRows[i]).val();
			voucherAccCashArr.push(value);
		}
		
		$.post("${ctx}/account/voucher/batchActiveStatus",{voucherAccCashStr:voucherAccCashArr.toString()},function(result){
	  		  if(result.success){
	  			  layer.msg('操作成功',{icon: 1,time: 1000},function(){
	              		$('#searchForm').submit();
	              	});
	  		  }else{
	  			  alert(result.msg);
	  		  }
	  	  })
	}
    
    function closeModal(){
    	$("#my-modal").modal('close');
    }
    
    function changeStatus(voucherAccCashId,status){
  	  $.post("${ctx}/account/voucher/changeStatus",{voucherAccCashId:voucherAccCashId,status:status},function(result){
  		  if(result.success){
  			  layer.msg('操作成功',{icon: 1,time: 1000},function(){
              		$('#searchForm').submit();
              	});
  		  }else{
  			  alert(result.msg);
  		  }
  	  })
    }
</script>
</body>
</html>
