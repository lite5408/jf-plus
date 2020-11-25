<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>礼包卡券表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
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
                            <div class="widget-title am-fl">礼包卡券信息</div>
                        </div>
                        <div class="widget-body am-fr">
                            <%-- <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
                                    <tr>
                                        <td class="am-padding-sm">
                                            <shiro:hasPermission name="mallSetting:packsAccCash:create">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel(false,'${ctx}/mallSetting/packsAccCash/create')"><span class="am-icon-plus"></span> 新增
                                                </button>
                                            </shiro:hasPermission>
                                        </td>
                                    </tr>
                                </table>
                            </div> --%>

							<div class="am-g">
	                            <div class="am-margin-left-default am-padding-left-xl">
	                                <form class="am-form-inline" role="form" id="searchForm" action="${ctx}/mallSetting/packsAccCash/list" method="post">
	                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	                                    <input id="packsId" name="packsId" type="hidden" value="${page.entity.packsId}"/>
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
	                                                <td class="am-padding-sm am-text-sm am-text-right">礼包券卡号</td>
	                                                <td>
	                                                	<input type="text" name="couponAccount" class="am-input-sm am-form-field" value="${page.entity.couponAccount}">
	                                                </td>
	                                                <td class="am-padding-sm am-text-sm am-text-right">绑定人手机</td>
	                                                <td>
	                                                    <input type="text" name="mobile" class="am-input-sm am-form-field" value="${page.entity.mobile}">
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
						             	<a href="${ctx}/export/excel/packsAccCash?packsId=${page.entity.packsId}&operStatus=${page.entity.operStatus}&couponAccount=${page.entity.couponAccount}&mobile=${page.entity.mobile}" class="am-btn am-btn-success"><span class="am-icon-file-excel-o"></span> 导出</a>
						             	<a href="javascript:" class="am-btn am-btn-success" onclick="activeSelected()"><span class="am-icon-cog"></span> 激活选中</a>
						            </div>
						          </div>
						        </div>
					        </div>

                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                            <th class="table-check"><input type="checkbox" id="checkAll"/></th>
                                            <th>礼包卡号</th>
                                            <th>礼包券码</th>
                                            <th>绑定用户</th>
                                            <th>用户名</th>
                                            <th>手机</th>
                                            <th>已绑定订单</th>
                                            <th>是否已兑换</th>
                                            <th>绑定日期</th>
                                            <th>状态</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="packsAccCash" varStatus="status">
                                        <tr>
                                        		<td>
								              		<c:if test="${packsAccCash.operStatus eq 0}">
								              			<input type="checkbox" name="rowId" value="${packsAccCash.id}"/>
								              		</c:if>
												</td>
                                                <td>${packsAccCash.couponAccount}</td>
                                                <td>${packsAccCash.couponCode}</td>
                                                <td>${packsAccCash.bindUser}</td>
                                                <td>${packsAccCash.userName}</td>
                                                <td>${packsAccCash.mobile}</td>
                                                <td><c:choose><c:when test="${empty packsAccCash.bindOrder}">无</c:when><c:otherwise>${packsAccCash.bindOrder}</c:otherwise></c:choose></td>
                                                <td><c:choose><c:when test="${empty packsAccCash.bindOrder}">未兑换</c:when><c:otherwise>已兑换</c:otherwise></c:choose></td>
                                                <td><fmt:formatDate pattern='yyyy-MM-dd' value='${packsAccCash.bindDate}' /></td>
                                                <td><c:if test="${packsAccCash.operStatus eq 1 }">启用</c:if><c:if test="${packsAccCash.operStatus eq 0 }">禁用</c:if></td>
	                                            <td>
								                	<div class="am-btn-toolbar">
								                		<div class="am-btn-group am-btn-group-xs">
									                	<c:if test="${packsAccCash.operStatus eq 1 }"><button class="am-btn am-btn-default am-btn-sm am-hide-sm-only" title="点击禁用" type="button" onclick="changeStatus(${packsAccCash.id},0)"><i class="am-icon-cog"></i> 禁用</button></c:if>
									                	<c:if test="${packsAccCash.operStatus eq 0 }"><button class="am-btn am-btn-default am-btn-sm am-text-secondary" title="点击启用" type="button" onclick="changeStatus(${packsAccCash.id},1)"><i class="am-icon-cog"></i> 激活</button></c:if>
								                		</div>
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
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-modal">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">生成礼包卡券</div>
    <div class="am-modal-bd">
	    <div class="widget-body am-fr">
	      <form class="am-form tpl-form-border-form" data-am-validator id="batchCreateCash" action="${ctx}/mallSetting/packsAccCash/batchCreateCash" method="post">
			<input name="packsId" type="hidden" value="${page.entity.packsId}"/>
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
    
    function closeModal(){
    	$("#my-modal").modal('close');
    }
    
    function activeSelected(){
		var $checkedRows = $('input[name="rowId"]:checked');
		if($checkedRows.length == 0){
			window.alert('请勾选礼包卡券，再进行操作');
			return false;
		}
		
	    var packsAccCashArr = [];
		for (var i = 0; i < $checkedRows.length; i++) {
			var value = $($checkedRows[i]).val();
			packsAccCashArr.push(value);
		}
		
		$.post("${ctx}/mallSetting/packsAccCash/batchActiveStatus",{packsAccCashStr:packsAccCashArr.toString()},function(result){
	  		if(result.success){
	  			layer.msg('操作成功',{icon: 1,time: 1000},function(){
	              	$('#searchForm').submit();
	            });
	  		}else{
	  			alert(result.msg);
	  		}
	  	})
	};
    
    function changeStatus(packsAccCashId,status){
		$.post("${ctx}/mallSetting/packsAccCash/changeStatus",{packsAccCashId:packsAccCashId,status:status},function(result){
			if(result.success){
				layer.msg('操作成功',{icon: 1,time: 1000},function(){
            		$('#searchForm').submit();
            	});
			}else{
				alert(result.msg);
			}
	 	});
    }
</script>
</body>
</html>
