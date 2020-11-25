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
                            <div class="widget-title am-fl">电子券管理</div>
                        </div>
                        <div class="widget-body am-fr">
                            <%-- <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs">
                                        <shiro:hasPermission name="account:voucher:create">
                                        <button type="button" class="am-btn am-btn-default am-btn-success"
                                                onclick="openModel(false,'${ctx}/account/voucher/create')"><span class="am-icon-plus"></span> 新增
                                        </button>
                                        </shiro:hasPermission>
                                    </div>
                                </div>
                            </div> --%>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/account/voucher/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                            <tr>
                                                <td class="am-padding-sm am-text-sm am-text-right">名称</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="name"  value="${page.entity.name}">
                                                </td>
                                                <td class="am-padding-sm am-text-sm am-text-right">开始时间</td>
                                                <td>
                                                    <input type="text" class="am-input-sm" name="validStartDate" 
				                                    value="<fmt:formatDate value='${page.entity.validStartDate }' pattern='yyyy-MM-dd' />" 
				                                    placeholder="请选择开始时间" data-am-datepicker/>
                                                </td>
                                                 <td class="am-padding-sm am-text-sm am-text-right">结束时间</td>
                                                <td>
                                                	<input type="text" class="am-input-sm" name="validEndDate" 
				                                    value="<fmt:formatDate value='${page.entity.validEndDate }' pattern='yyyy-MM-dd' />" 
				                                    placeholder="请选择失效时间" data-am-datepicker/>    
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
                                            <th>电子券名称</th>
                                            <th>电子券面值(元)</th>
                                            <th>截止日期</th>
                                            <th>状态</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="voucher" varStatus="status">
                                        <tr>
	                                        <td>${voucher.name}</td>
	                                        <td><c:choose><c:when test='${voucher.price==-1}'>无</c:when><c:otherwise>${voucher.price}</c:otherwise></c:choose></td>
	                                        <td><fmt:formatDate value='${voucher.validEndDate }' pattern='yyyy-MM-dd' /></td>
	                                        <td><c:choose><c:when test='${empty voucher.operStatus or voucher.operStatus==0}'>禁用</c:when><c:otherwise>启用</c:otherwise></c:choose></td>
		                                    <td>
		                                        <div class="am-btn-group am-btn-group-xs">
		                                        	<a role="oper" href="javascript:;" onclick="openModel('修改电子券信息表','${ctx}/account/voucher/update?id=${voucher.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"> 编辑</a>
		                                        	<c:choose>
		                                        		<c:when test='${empty voucher.operStatus or voucher.operStatus==1}'>
		                                        			<a role="oper" href="javascrip:" onclick="distribute(${voucher.id},${voucher.price})" class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-modal="{target: '#my-modal', closeViaDimmer: 0, width: 600, height: 300}">分发</a>
		                                        			<a role="oper" href="${ctx}/account/voucher/cardPage?voucherId=${voucher.id}" class="am-btn am-btn-default am-btn-xs am-text-secondary">查看卡号</a>
		                                        			<a role="oper" href="${ctx}/account/voucher/changeOperStatus?id=${voucher.id}" onclick="return confirm('确认要下架这个电子券吗？', this.href)" class="am-btn am-btn-default am-btn-xs am-text-danger">下架</a>
		                                        		</c:when>
		                                        		<c:otherwise>
		                                        			<a role="oper" href="${ctx}/account/voucher/changeOperStatus?id=${voucher.id}" onclick="return confirm('确认要上架这个电子券吗？', this.href)" class="am-btn am-btn-default am-btn-xs am-text-danger">上架</a>
		                                        		</c:otherwise>
		                                        	</c:choose>
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
    <div class="am-modal-hd">电子券分发</div>
    <div class="am-modal-bd">
	    <div class="widget-body am-fr">
	      <form class="am-form tpl-form-border-form" data-am-validator action="${ctx}/account/voucher/distribute" method="post">
			<input type="hidden" name="voucherId" id="voucherId" />
			    <div class="am-form-group">
	                <label class="am-u-sm-4 am-form-label">手机号：</label>
	                <div class="am-u-sm-8">
	                    <input type="number" name="mobile" id="mobile" required/>
	                </div>
	            </div>
	            <div class="am-form-group">
	                <label class="am-u-sm-4 am-form-label">分发金额（元）：</label>
	                <div class="am-u-sm-8">
	                    <input type="number" name="distAmount" id="distAmount" required/>
	                </div>
	            </div>
	            <div class="am-form-group">
	                <label class="am-u-sm-4 am-form-label">分发事由：</label>
	                <div class="am-u-sm-8">
	                    <input type="text" name="remarks" id="remarks" required/>
	                </div>
	            </div>
	            <div class="am-form-group">
	                <div class="am-u-sm-9 am-u-sm-push-3">
	                	<button type="submit" class="am-btn am-btn-xs am-btn-primary">分发</button>
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
        initSelectValue(true);//初始化下拉框的值
        
    });
    
    function closeModal(){
    	$("#my-modal").modal('close');
    }
    
    function distribute(voucherId,price){
    	$("#voucherId").val(voucherId);
    	$("#distAmount").val(null);
    	$("#remarks").val(null);
    	$("#mobile").val(null);
    	if(-1 == price){
    		$("#distAmount").removeAttr("disabled");
    	}else{
    		$("#distAmount").attr("disabled","disabled");
    	}
    }
    
    $("#mobile").blur(function(){
    	if(!isPoneAvailable($("#mobile").val())){
    		alert("手机号格式不正确！");
    		$("#mobile").val(null);
    	}
    });
    
    function isPoneAvailable(str) {  
        var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
        if (!myreg.test(str)) {  
            return false;  
        } else {  
            return true;  
        }  
    } 
</script>
</body>
</html>
