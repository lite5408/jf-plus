<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><c:set var="iutilsName"
		value='${fnc:getConfig("iutils.name")}' />${iutilsName} - 首页</title>
<%@ include file="/WEB-INF/view/include/head.jsp"%>
<style>
.tpl-content-wrapper {
	margin-left: 0
}

.widget-body {
	padding: 13px 20px;
}

</style>
</head>
<body>
	<script src="${ctxStatic}/assets/js/theme.js"></script>
	<!-- 内容区域 -->
	<div class="tpl-content-wrapper">
		<div class="row-content am-cf">
			<div class="row  am-cf">
				<div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
					<div class="widget widget-default am-cf" style="padding-bottom:0px;">
						<div class="widget-statistic-header">订货数量</div>
						<div class="widget-statistic-body">
							<div class="widget-statistic-value" style="padding-bottom:0px;">
								<span role="rpt" role-id="orderCount">0</span>
								<div class="am-margin-xs am-text-xs" style="line-height:10px;font-size:16px;color:black!important;">今日订货数<span role="rpt" role-id="dayOrderCount">0</span></div>
							</div>
							<span class="widget-statistic-icon"></span>
						</div>
					</div>
				</div>
				<div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
					<div class="widget widget-primary am-cf" style="padding-bottom:0px;">
						<div class="widget-statistic-header">交易总额</div>
						<div class="widget-statistic-body">
							<div class="widget-statistic-value" style="padding-bottom:0px;">
								<span role="rpt" role-id="orderAmount">0</span>
								<div class="am-margin-xs am-text-xs" style="line-height:10px;font-size:16px;color:black!important;">今日交易额<span role="rpt" role-id="dayOrderAmount">0</span></div>
							</div>
							<span class="widget-statistic-icon am-icon-support"></span>
						</div>
					</div>
				</div>
				<div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
					<div class="widget widget-purple am-cf" style="padding-bottom:0px;">
						<div class="widget-statistic-header">发布商品数</div>
						<div class="widget-statistic-body">
							<div class="widget-statistic-value" style="padding-bottom:0px;">
								<span role="rpt" role-id="prodCount">0</span>
								<div class="am-margin-xs am-text-xs" style="line-height:10px;font-size:16px;color:white!important;">今日商品数<span role="rpt" role-id="dayProdCount">0</span></div>
							</div>
							<span class="widget-statistic-icon am-icon-credit-card-alt"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="row am-cf">
              <div class="am-u-sm-12 am-u-md-6">
                  <div class="widget am-cf widget-body-lg">
                      <div class="widget-head am-cf">
                          <div class="widget-title am-fl">订货品交易额趋势报表</div>
                          <div class="widget-function am-fr">
                              <button type="button" onclick="searchReport(12,'tpl-echarts-month-sales')" class="am-btn am-btn-default am-round am-btn-xs">近一年</button>
                              <button type="button" onclick="searchReport(6,'tpl-echarts-month-sales')" class="am-btn am-btn-secondary am-round am-btn-xs">近半年</button>
                          </div>
                      </div>
                      <div class="widget-body-md widget-body tpl-amendment-echarts am-fr" id="tpl-echarts-month-sales">

                      </div>
                  </div>
              </div>
              <div class="am-u-sm-12 am-u-md-6">
                  <div class="widget am-cf widget-body-lg">
                      <div class="widget-head am-cf">
                          <div class="widget-title am-fl">订货品交易榜单报表</div>
                          <div class="widget-function am-fr">
                              <button type="button" onclick="searchReport(12,'tpl-echarts-top-sales')" class="am-btn am-btn-default am-round am-btn-xs">近一年</button>
                              <button type="button" onclick="searchReport(6,'tpl-echarts-top-sales')" class="am-btn am-btn-secondary am-round am-btn-xs">近半年</button>
                          </div>
                      </div>
                      <div class="widget-body-md widget-body tpl-amendment-echarts am-fr" id="tpl-echarts-top-sales">

                      </div>
                  </div>
              </div>
           </div>
           <div class="row am-cf">
              <div class="am-u-sm-12 am-u-md-6">
                  <div class="widget am-cf widget-body-lg">
                      <div class="widget-head am-cf">
                          <div class="widget-title am-fl">订货品交易占比报表</div>
                          <div class="widget-function am-fr">
                              <button type="button" onclick="searchReport(12,'tpl-echarts-top-sales-pie')" class="am-btn am-btn-default am-round am-btn-xs">近一年</button>
                              <button type="button" onclick="searchReport(6,'tpl-echarts-top-sales-pie')" class="am-btn am-btn-secondary am-round am-btn-xs">近半年</button>
                          </div>
                      </div>
                      <div class="widget-body-md widget-body tpl-amendment-echarts am-fr" id="tpl-echarts-top-sales-pie">

                      </div>
                  </div>
              </div>
              <div class="am-u-sm-12 am-u-md-6">
                  <div class="widget am-cf widget-body-lg">
                      <div class="widget-head am-cf">
                          <div class="widget-title am-fl">供应商交易占比报表</div>
                          <div class="widget-function am-fr">
                            <button type="button" onclick="searchReport(12,'tpl-echarts-top-supply-sales-pie')" class="am-btn am-btn-default am-round am-btn-xs">近一年</button>
                            <button type="button" onclick="searchReport(6,'tpl-echarts-top-supply-sales-pie')" class="am-btn am-btn-secondary am-round am-btn-xs">近半年</button>
                          </div>
                      </div>
                      <div class="widget-body-md widget-body tpl-amendment-echarts am-fr" id="tpl-echarts-top-supply-sales-pie">
							
                      </div>
                  </div>
              </div>
           </div>
		</div>
	</div>
	<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
	<script type="text/javascript">
		function getTotalFun(){
			post('${ctx}/zh/home/goodsOrderRPT', {}, function(result){
				if(result.success){
					$('span[role-id=orderCount]').text(result.obj.orderCount);
					$('span[role-id=orderAmount]').text(result.obj.orderAmount);
					$('span[role-id=prodCount]').text(result.obj.prodCount);
				}
			})
			post('${ctx}/zh/home/reportCount', {}, function(result){
				if(result.success){
					$('span[role-id=dayOrderCount]').text(result.obj.orderCount);
					$('span[role-id=dayOrderAmount]').text(result.obj.orderAmount);
					$('span[role-id=dayProdCount]').text(result.obj.prodCount);
				}
			})
		}
		
		$(function(){
			getTotalFun();
			
			searchReport(6,'tpl-echarts-month-sales');
			searchReport(6,'tpl-echarts-top-sales');
			searchReport(6,'tpl-echarts-top-sales-pie');
			searchReport(6,'tpl-echarts-top-supply-sales-pie');
			
		})
		
		function searchReport(month,compent_id){
			month = month;
			var date = new Date();
			date.setMonth(date.getMonth() + 1 - month);
			var v_startTime = date.getFullYear() + "-" + date.getMonth();
			
			var date1 = new Date();
			var v_endTime = date1.getFullYear()+"-"+(date1.getMonth() + 1);
			
			console.log(v_startTime)
			console.log(v_endTime)
			
			var evtTarget = event.target || event.srcElement;
			$(evtTarget).addClass('am-btn-secondary').siblings('button').removeClass('am-btn-secondary')
			
			if(compent_id == 'tpl-echarts-month-sales'){
				post('${ctx}/report/salesReport',{
					startTime:v_startTime,
					endTime:v_endTime,
					operStatus:2
				},function(data){
					if(data.success){
						loadSaleReport(data.obj);
					}
				})
			}
			
			if(compent_id == 'tpl-echarts-top-sales'){
				post('${ctx}/report/topCateSaleReport',{
					startTime:v_startTime,
					endTime:v_endTime,
					operStatus:2
				},function(data){
					if(data.success){
						loadTopCateReport(data.obj);
					}
				})
			}
			
			
			if(compent_id == 'tpl-echarts-top-sales-pie'){
				post('${ctx}/report/topCateSalePieReport',{
					startTime:v_startTime,
					endTime:v_endTime,
					operStatus:2
				},function(data){
					if(data.success){
						loadTopCatePieReport(data.obj);
					}
				})
			}
			
			if(compent_id == 'tpl-echarts-top-supply-sales-pie'){
				post('${ctx}/report/topSupplySalePieReport',{
					startTime:v_startTime,
					endTime:v_endTime,
					operStatus:2
				},function(data){
					if(data.success){
						loadTopSupplyPieReport(data.obj);
					}
				})
			}
		}
		
		/**
		 *	销售额报表
		 */
		function loadSaleReport(obj){
			var echartsA = echarts.init(document.getElementById('tpl-echarts-month-sales'));
			
			var categoryData = new Array();
			var seriesData = new Array();
			for(var i in obj){
				categoryData.push(i);
				seriesData.push(obj[i]);
			}
			
			option = {
				tooltip : {
					trigger : 'axis',
				},
				legend : {
					data : [ '订货额']
				},
				grid : {
					left : '1%',
					right : '1%',
					bottom : '1%',
					containLabel : true
				},
				xAxis : [ {
					type : 'category',
					boundaryGap : true,
					data : categoryData
				} ],

				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '订货额',
					type : 'line',
					data : seriesData
				}]
			};
			echartsA.setOption(option);
		}
		
		/**
		 * 分类榜单
		 */
		function loadTopCateReport(obj){
			var echartsA = echarts.init(document.getElementById('tpl-echarts-top-sales'));
			
			var categoryData = new Array();
			var seriesData = new Array();
			var ary = new Array();
			for(var i in obj){
				categoryData.push(i);
				
				ary.push(obj[i].saleamount);
			}
			seriesData.push({
				name : '订货额',
				type : 'bar',
	            barWidth: '60%',
				data : ary
			});
			
			option = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			    	left : '1%',
					right : '1%',
					bottom : '1%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : categoryData,
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : seriesData
			};
			echartsA.setOption(option);
		}
		
		/**
		 * 供应商榜单
		 */
		function loadTopSupplyReport(obj){
			var echartsA = echarts.init(document.getElementById('tpl-echarts-top-supply-sales'));
			
			var categoryData = new Array();
			var seriesData = new Array();
			var ary = new Array();
			for(var i in obj){
				categoryData.push(i);
				
				ary.push(obj[i].saleamount);
			}
			seriesData.push({
				name : '订货额',
				type : 'bar',
	            barWidth: '60%',
				data : ary
			});
			
			option = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			    	left : '1%',
					right : '1%',
					bottom : '1%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : categoryData,
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : seriesData
			};
			echartsA.setOption(option);
		}
		
		/**
		 * 分类榜单
		 */
		function loadTopCatePieReport(obj){
			var echartsA = echarts.init(document.getElementById('tpl-echarts-top-sales-pie'));
			
			var categoryData = new Array();
			var seriesData = new Array();
			var ary = new Array();
			for(var i in obj){
				categoryData.push(i);
				
				seriesData.push({
					name : i,
					value : obj[i].saleamount
				});
			}
			
			
			option = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    series : [
			        {
			            name: '订货额',
			            type: 'pie',
			            radius : '75%',
			            center: ['50%', '60%'],
			            data : seriesData,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            },
				        label: {
			                normal: {
			                    show: true,
			                    formatter: '{b} {d}%'
			                }
			            }
			        }
			    ]
			};
			echartsA.setOption(option);
		}
		
		/**
		 * 供应商榜单
		 */
		function loadTopSupplyPieReport(obj){
			var echartsA = echarts.init(document.getElementById('tpl-echarts-top-supply-sales-pie'));
			
			var categoryData = new Array();
			var seriesData = new Array();
			var ary = new Array();
			for(var i in obj){
				categoryData.push(i);
				
				seriesData.push({
					name : i,
					value : obj[i].saleamount
				});
			}
			
			option = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    series : [
			        {
			            name: '订货额',
			            type: 'pie',
			            radius : '75%',
			            center: ['50%', '60%'],
			            data : seriesData,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            },
				        label: {
			                normal: {
			                    show: true,
			                    formatter: '{b} {d}%'
			                }
			            }
			        }
			    ]
			};
			echartsA.setOption(option);
		}
	</script>
</body>
</html>