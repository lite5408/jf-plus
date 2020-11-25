<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商城批量下单</title>
    <%@ include file="../../include/head.jsp"%>
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
		.tpl-content-wrapper{margin-left:0}
		.am-selected{width: 100%;}
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
                            <div class="widget-title am-fl">批量下单</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form id="impForm" class="am-form tpl-form-border-form" data-am-validator action="${ctx}/import/order/batchConfirmImport" method="post" enctype="multipart/form-data">
                            	<input type="hidden" name="source" id="source" value="1"/>
                            	<div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>供应商：</label>
                                    <div class="am-u-sm-9">
                                       	<select class="am-input-xs" name="supplyId" id="supplyId" data="">
									 		<option value="1">京东商城</option>
									 		<option value="3">苏宁易购</option>
											<option value="4">网易严选</option>
											<option value="106">供应商-武汉市武昌区礼至和百货商行</option>
											<option value="110">供应商-武汉市瓦力商贸有限公司</option>
											<option value="111">供应商-湖北维仁科技发展有限公司</option>
											<option value="108">供应商-武汉康泰兴经贸有限公司</option>
										</select>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>采购事由：</label>
                                    <div class="am-u-sm-9">
                                    	<div class="am-form-group">
                                    		<select class="am-input-xs" name="remarks" data="">
                                    			<c:forEach items="${dictList}" var="dict" varStatus="status">
											 		<option value="${dict.val}">${dict.val}</option>
												</c:forEach>
											</select>
										</div>
									</div>
                                </div>
                            	<div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>下单文件：</label>
                                    <div class="am-u-sm-9">
                                        <input type="file" name="file" id="uploadFile">
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>下单站点：</label>
                                    <div class="am-u-sm-9">
                                    	<div class="am-form-group">
		                                    <!-- <select data-am-selected="{searchBox: 1}" data="true" name="no" id="selectOrg" placeholder="请输入编码或者名称筛选" required>
											</select> -->
											<select class="am-input-xs" name="siteId" data="">
												<c:forEach items="${mallSiteList}" var="mallSite" varStatus="status">
											 		<option value="${mallSite.id}">${mallSite.siteName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
                                </div>
								<div class="am-form-group">
	                                <div class="am-u-sm-9 am-u-sm-push-3">
	                                    <input type="button" class="am-btn am-btn-xs am-btn-primary btn-loading-example" data-am-loading="{spinner: 'circle-o-notch', loadingText: '下单中，请耐心等待一段时间', resetText: '下单成功'}" id="submitButton" value="开始下单" />
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
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript" src="${ctxStatic}/custom/js/ajaxfileupload.js"></script>
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
      	//初始化下拉框
        initSelectValue(true);
      	
      //上传excle卡券表格
      $("#submitButton").click(function(){
        	if($('#remarks').val() == ''){
        		alert('请输入本次下单的采购事由');
        		return false;
        	}
    	  	var fileName = $('#uploadFile').val();
        	if(fileName == ''){
        		alert('请选择要上传的文件');
        		return false;
        	}
          var ext = fileName.substring(fileName.lastIndexOf('.')+1).toUpperCase();
        	if(ext != 'XLS' && ext != 'XLSX'){
        		alert('请上传excel格式文件');
        		return false;
        	}
       	  $('#submitButton').button('loading');
          $('#impForm').submit();
      });
      
      $("#supplyId").change(function(){
    	  if($("#supplyId").val()!=1 && $("#supplyId").val()!=3 &&$("#supplyId").val()!=4){
    		  $("#source").val(2);
    	  }else{
    		  $("#source").val($("#supplyId").val());
    	  }
      })
      
    });
    
   /*  $(function () {
		var zNodes =[
			<c:forEach items="${userList}" var="user" varStatus="status">
				<c:if test="${not o.rootNode}">
					{ id:${user.organization.id}, pId:${user.organization.parentId}, no:"${user.no}",name:"${user.name}",checked:false,open:false}<c:if test="${!status.last}">,</c:if>
				</c:if>
			</c:forEach>
		];
		$.fn.ztreeOrg($("#treeOrg"),zNodes);
		
		for(var i =0 ;i < zNodes.length ; i ++){
			if(zNodes[i].checked){
				$('#selectOrg').append('<option value="' + zNodes[i].no + '" selected>' + zNodes[i].no+zNodes[i].name + "</option>");
			}else{
				$('#selectOrg').append('<option value="' + zNodes[i].no + '">' + zNodes[i].no+zNodes[i].name + "</option>");
			}
		}
		
		
		//展开2级
        var treeObj = $.fn.zTree.getZTreeObj("treeOrg");
        var nodes = treeObj.getNodes();
        for (var i = 0; i < nodes.length; i++) { //设置节点展开
            treeObj.expandNode(nodes[i], true, false, true);
        }
        
	}); */
</script>
</body>
</html>
