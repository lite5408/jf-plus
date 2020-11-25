<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>机构品牌表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/jquery-ztree/css/zTreeStyle.css">
    <style>
		ul.ztree {
			margin-top: 10px;
			border: 1px solid #ddd;
			background: #fff;
			width: 198px;
			height: 200px;
			overflow-y: auto;
			overflow-x: auto;
		}
        .theme-black ul.ztree{background:transparent;border:1px solid rgba(255, 255, 255, 0.2);}
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
                            <div class="widget-title am-fl">品牌管理</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="mallProductBrand" action="${ctx}/product/mallProductBrand/<c:choose><c:when test="${empty mallProductBrand.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${mallProductBrand.id}"/>
                               <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>归属机构：</label>
                                    <div class="am-u-sm-9">
                                        <div class="am-input-group" style="width: 200px;">
                                            <input type="text" id="parentName" class="am-form-field" minlength="1" placeholder="归属机构（必选）" value="${mallProductBrand.orgName}" required readonly/>
                                            <input type="hidden" id="orgId" name="orgId" value="${mallProductBrand.orgId}" />
										    <span class="am-input-group-btn">
												<button class="am-btn am-btn-default" id="menuBtn" type="button">选择</button>
										    </span>
                                        </div>
                                    </div>
                                </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">品牌编码：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="brandCode" placeholder="品牌编码" value="${mallProductBrand.brandCode}" required unique/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">品牌名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="brandName" placeholder="品牌名称" value="${mallProductBrand.brandName}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">类型：</label>
                                        <div class="am-u-sm-9">
                                            <select class="am-input-sm" name="type" data="${mallProductBrand.type }" >
                                            	<option value="PRIVATE">供应商私有</option>
                                            	<option value="PUBLIC">平台公有</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">排序：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="sort" placeholder="排序" value="${mallProductBrand.sort}"/>
                                        </div>
                                    </div>
                            <div class="am-form-group">
                                <div class="am-u-sm-9 am-u-sm-push-3">
                                    <button type="submit" class="am-btn am-btn-xs am-btn-primary">保存</button>
                                    <button type="button" class="am-btn am-btn-xs am-btn-danger" onclick="closeModel(false)">关闭</button>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="menuContent" class="menuContent" style="display:none;position: absolute;z-index: 10000;">
    <ul id="tree" class="ztree" style="margin-top:0;"></ul>
</div>
<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
<script src="${ctxStatic}/3rd-lib/jquery-ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctxStatic}/custom/js/ztree.select.js"></script>
<script src="${ctxStatic}/custom/js/ztree.org.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
        
        initSelectValue(true)
        
      	//机构树选择
        var zNodes =[<c:forEach items="${organizationList}" var="o" varStatus="status">{ id:${o.id}, pId:${o.parentId}, name:'${o.name}', open:${o.rootNode}}<c:if test="${!status.last}">,</c:if></c:forEach>];
        $.fn.ztreeSelect($("#tree"),zNodes);
    });
    
  	//ztree资源点击回调
    function ztreeOnClickCall(treeNode){
        $("#orgId").val(treeNode.id);
    }
  	
    $('[unique]').blur(function(){
    	if(isNull($(this).val()))
    		return true;
    	
		$.ajaxSetup({aysnc:false});
		post("${ctx}/product/mallProductBrand/unique",{brandCode:$(this).val(),id:'${mallProductBrand.id}'},function(result){
			if(result.obj >= 1){
				alert('编码重复，请重新输入');
				$('[unique]').val('');
				return false;
			}else{
				return true;
			}
		});
	});
</script>
</body>
</html>
