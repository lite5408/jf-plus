<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>分类属性定义表</title>
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
                            <div class="widget-title am-fl">分类属性</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="categoryAttr" action="${ctx}/product/categoryAttr/<c:choose><c:when test="${empty categoryAttr.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${categoryAttr.id}"/>
                                    <div class="am-form-group">
	                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>归属分类：</label>
	                                    <div class="am-u-sm-9">
	                                        <div class="am-input-group" style="width: 200px;">
	                                            <input type="text" id="parentName" class="am-form-field" minlength="1" placeholder="归属分类（必选）" value="${categoryAttr.cateName}" required readonly/>
	                                            <input type="hidden" id="cateId" name="cateId" value="${categoryAttr.cateId}" />
											    <span class="am-input-group-btn">
													<button class="am-btn am-btn-default" id="menuBtn" type="button">选择</button>
											    </span>
	                                        </div>
	                                    </div>
	                                </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">属性名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="attrText" placeholder="属性名称" value="${categoryAttr.attrText}" style="width:200px" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none;">
                                        <label class="am-u-sm-3 am-form-label">父属性ID（级联属性）：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="parentId" placeholder="父属性ID（级联属性）" value="${categoryAttr.parentId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                    	<label class="am-u-sm-3 am-form-label">属性值：</label>
                                    	<div class="am-u-sm-9">
	                                    	<table style="width:300px;" id="childsTable">
	                                    		<c:if test="${empty categoryAttr.categoryAttrInfos }">
											        <tr>
											            <td><input type="text" name="categoryAttrInfos[0].attrInfo" style="width:200px"/></td>
											            <td>
											            	<a href="javascript:;" onclick="addRow(this)" title="添加"><span class="am-text-success am-icon-plus"></span></a>
											            	<a href="javascript:;" onclick="delRow(this)" title="删除"><span class="am-text-danger am-icon-trash-o"></span></a>
											            </td>
											        </tr>
											   </c:if>
											   <c:if test="${not empty categoryAttr.categoryAttrInfos }">
											   		<c:forEach items="${categoryAttr.categoryAttrInfos }" var="attrInfo" varStatus="ind">
											   			<tr>
												            <td><input type="text" name="categoryAttrInfos[${ind.index }].attrInfo" style="width:200px" value="${attrInfo.attrInfo }"/></td>
												            <td>
												            	<a href="javascript:;" onclick="addRow(this)" title="添加"><span class="am-text-success am-icon-plus"></span></a>
												            	 <a href="javascript:;" onclick="delRow(this)" title="删除"><span class="am-text-danger am-icon-trash-o"></span></a>
												            </td>
												        </tr>
											   		</c:forEach>
											        
											   </c:if>
											</table>
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
        
      //机构树选择
        var zNodes =[<c:forEach items="${categoryList}" var="o" varStatus="status">{ id:${o.id}, pId:${o.catPid}, name:'${o.catName}', open:${o.rootNode}}<c:if test="${!status.last}">,</c:if></c:forEach>];
        $.fn.ztreeSelect($("#tree"),zNodes);
    });
    
  //ztree资源点击回调
    function ztreeOnClickCall(treeNode){
        $("#cateId").val(treeNode.id);
        $('#parentId').val(treeNode.pId);
    }
  
  function addRow(srcEle){
	  var shtml = '<tr>';
	      shtml += '<td><input type="text" name="categoryAttrInfos[0].attrInfo" style="width:200px"/></td>';
	      shtml += '<td>';
		  shtml += '<a href="javascript:;" onclick="addRow(this)" title="添加"><span class="am-text-success am-icon-plus"></span></a>';
		  shtml += '  <a href="javascript:;" onclick="delRow(this)" title="删除"><span class="am-text-danger am-icon-trash-o"></span></a>';
		  shtml += '</td>';
		  shtml += '</tr>';
	$(srcEle).parents('tr').after(shtml);

	sortRow();
  }
  
  function delRow(srcEle){
	  $(srcEle).parents('tr').remove();
	  //重新排序
	  sortRow();
  }
  
  function sortRow(){

	  $('#childsTable input[type=text]').each(function(index,item){
		  $(item).attr('name','categoryAttrInfos['+index+'].attrInfo');
	  });
  }
</script>
</body>
</html>
