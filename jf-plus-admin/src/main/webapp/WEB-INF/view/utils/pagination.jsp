<!-- 分页工具 -->
<%@ page contentType="text/html;charset=UTF-8"%>
<style>
	.am-pagination .am-disabled{display: none;}
</style>
<div class="am-pagination-tip">显示 ${page.pageNo+1}/${page.pageNumber} 页，共 ${page.total} 条 
<select id="pageSelect" style="margin-left:10px;">
	<option value="10" <c:if test="${page.pageSize == 10 }">selected</c:if>>10</option>
	<option value="30" <c:if test="${page.pageSize == 30 }">selected</c:if>>30</option>
	<option value="50" <c:if test="${page.pageSize == 50 }">selected</c:if>>50</option>
	<option value="100" <c:if test="${page.pageSize == 100 }">selected</c:if>>100</option>
</select>
跳转 <input type="number" id="searchPageNo" style="width:50px;" class="am-input-xs" min="1"/><button type="button" id="goPage">GO</button>
</div>
<script>
//分页查询
function page(n){
	$("#pageNo").val(n);
	$("#searchForm").submit();
	return false;
}

//调整页数
$('#pageSelect').change(function(){
	 var pageSize = $(this).val();
	 $('#pageSize').val(pageSize);
	 $("#searchForm").submit();
 	 return false;
});

$('#goPage').click(function(){
	var pageNo = ${page.pageNo + 1};
	var searchPageNo = $('#searchPageNo').val();
	
	if(searchPageNo == ''){
		searchPageNo = pageNo;
	}
	
	if(searchPageNo < 0){
		searchPageNo = 1;
	}
	
	page(searchPageNo - 1);
});
</script>
<c:if test="${page.total>page.pageSize}">
<ul class="am-pagination tpl-pagination am-pagination-right" style="float:right;max-width:256px;margin-top:0;margin-bottom:0;">
	<li class="<c:if test="${page.hasFirstPage}">am-disabled</c:if>"><a href="javascript:page(${page.first});"><i class="am-icon-fast-backward"></i></a></li>
	<li class="<c:if test="${page.hasFirstPage}">am-disabled</c:if>"><a href="javascript:page(${page.prev});"><i class="am-icon-backward"></i></a></li>
	<li class="<c:if test="${page.hasLastPage}">am-disabled</c:if>"><a href="javascript:page(${page.next});"><i class="am-icon-forward"></i></a></li>
	<li class="<c:if test="${page.hasLastPage}">am-disabled</c:if>"><a href="javascript:page(${page.last});"><i class="am-icon-fast-forward"></i></a></li>
</ul>
<script type="text/javascript">
     
	//排序方法
	function order(by){
		var orderBy = $("#orderBy").val();
		if(orderBy.indexOf(by)>-1){
			//改变排序规则
			if(orderBy.indexOf("desc")>-1){
				$("#orderBy").val(by+" asc");
			}else{
				$("#orderBy").val(by+" desc");
			}
		}else{
			//第一次进入
			$("#orderBy").val(by+" desc");
		}
		$("#pageNo").val(0);
		$("#searchForm").submit();
		return false;
	}
</script>
</c:if>