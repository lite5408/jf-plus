<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
   <title>商品分类</title>
   <%@ include file="../../include/head.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/jquery-ztree/css/zTreeStyle.css">
    <style type="text/css">
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
                            <div class="widget-title am-fl">商品分类树</div>
                        </div>
                        <div class="widget-body widget-body-lg am-fr">
                            <div class="am-scrollable-horizontal">
                                <ul id="tree" class="ztree"></ul><!-- 商品分类树 -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script src="${ctxStatic}/3rd-lib/jquery-ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script>
    $(function () {
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback : {
                onClick : function(event, treeId, treeNode) {
                    selectFun(treeNode.id,treeNode.name);
                }
            }
        };
        var zNodes =[
            <c:forEach items="${mallProductCateList}" var="o" varStatus="status">
            { id:${o.id}, pId:${o.catPid}, name:"${o.catName}", open:${o.rootNode}}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];
        $(document).ready(function(){
            var ztree = $.fn.zTree.init($("#tree"), setting, zNodes);
            //ztree.expandAll(true);
        });
    });
    
    function selectFun(id,name){
    	var data = {};
    	data.id = id;
    	data.name = name;
    	parent.itemCateCallback(data);
    	parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭
    }
</script>
<script>
  $(document).ready(function () {
      var msg = '${msg}';
      if(msg!=''){
          showMsg(msg);
      }
  });
</script>
</body>
</html>