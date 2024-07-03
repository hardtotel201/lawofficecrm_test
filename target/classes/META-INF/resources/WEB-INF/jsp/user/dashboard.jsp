<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
/*插件*/
.plugin
{
    background-color: whitesmoke;
    flex-grow: 1;
}
</style>

<div class="row">
	<div class="col-sm-4">
		<div class="widget-box transparent" >
			<div class="widget-header widget-header-flat widget-header-small">
				<h5 class="widget-title">
					<i class="ace-icon fa fa-signal"></i>本月新增客户
				</h5>
				<div class="widget-toolbar no-border">
					<div class="inline">
						<button id="btnMonthNew" class="btn btn-minier btn-primary">
						    查看所有( <c:out value='${pageMonthNewCount}' />)
							<i class="ace-icon fa icon-on-right bigger-110"></i>
						</button>
					</div>
				</div>
		    </div>
		    <div class="widget-body">
				<div class="widget-main no-padding">
					<table class="table table-bordered table-striped">
						<thead class="thin-border-bottom">
							<tr>
								<th>
									<i class="ace-icon fa fa-caret-right blue"></i>姓名
								</th>
								<th>
									<i class="ace-icon fa fa-caret-right blue"></i>客户等级
								</th>
								<th class="hidden-480">
									<i class="ace-icon fa fa-caret-right blue"></i>客户来源
								</th>
							</tr>
						</thead>
						<tbody>
						    <c:forEach var="item" items="${pageMonthNew}">
							<tr>
								<c:if test="${item.clientName==null || item.clientName=='' }">
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
							    </c:if>
							    <c:if test="${item.clientName!=null && item.clientName!='' }">
							        <td><i class="menu-icon fa fa-user">&nbsp;&nbsp;${item.clientName}</i></td>
									<td><i class="menu-icon fa fa-tag">&nbsp;&nbsp;${item.clientLevel}</i></td>
									<td><i class="menu-icon fa fa-star">&nbsp;&nbsp;${item.sourceMemo}</i></td>
							    </c:if>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		 </div>
	</div>
	<div class="vspace-12-sm"></div>
	<div class="col-sm-4">
		<div class="widget-box transparent" >
			<div class="widget-header widget-header-flat widget-header-small">
				<h5 class="widget-title">
					<i class="ace-icon fa fa-signal"></i>本月已成交客户
				</h5>
			    <div class="widget-toolbar no-border">
					<div class="inline">
						<button id="btnMonthTransacted" class="btn btn-minier btn-primary">
							 查看所有( <c:out value='${pageMonthTransactedCount}' />)
							<i class="ace-icon fa icon-on-right bigger-110"></i>
						</button>
					</div>
				</div>
		    </div>

		   <div class="widget-body">
				<div class="widget-main no-padding">
					<table class="table table-bordered table-striped">
						<thead class="thin-border-bottom">
							<tr>
								<th>
									<i class="ace-icon fa fa-caret-right blue"></i>姓名
								</th>
								<th>
									<i class="ace-icon fa fa-caret-right blue"></i>客户等级
								</th>
								<th class="hidden-480">
									<i class="ace-icon fa fa-caret-right blue"></i>客户来源
								</th>
							</tr>
						</thead>
						<tbody>
						    <c:forEach var="item" items="${pageMonthTransacted}">
							<tr>
							  <c:if test="${item.clientName==null || item.clientName=='' }">
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
							    </c:if>
							    <c:if test="${item.clientName!=null && item.clientName!='' }">
							        <td><i class="menu-icon fa fa-user">&nbsp;&nbsp;${item.clientName}</i></td>
									<td><i class="menu-icon fa fa-tag">&nbsp;&nbsp;${item.clientLevel}</i></td>
									<td><i class="menu-icon fa fa-star">&nbsp;&nbsp;${item.sourceMemo}</i></td>
							    </c:if>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		 </div>
	</div>
	<div class="vspace-12-sm"></div>
	<div class="col-sm-4">
		<div class="widget-box transparent" >
			<div class="widget-header widget-header-flat widget-header-small">
				<h5 class="widget-title">
					<i class="ace-icon fa fa-signal"></i>本月未成交客户
				</h5>
				<div class="widget-toolbar no-border">
					<div class="inline">
						<button id="btnUnrevisit" class="btn btn-minier btn-primary">
							 查看所有( <c:out value='${pageUnrevisitCount}' />)
							<i class="ace-icon fa icon-on-right bigger-110"></i>
						</button>
					</div>
				</div>
		    </div>
		    <div class="widget-body">
				<div class="widget-main no-padding">
					<table class="table table-bordered table-striped">
						<thead class="thin-border-bottom">
							<tr>
								<th>
									<i class="ace-icon fa fa-caret-right blue"></i>姓名
								</th>
								<th>
									<i class="ace-icon fa fa-caret-right blue"></i>客户等级
								</th>
								<th class="hidden-480">
									<i class="ace-icon fa fa-caret-right blue"></i>客户来源
								</th>
							</tr>
						</thead>
						<tbody>
						    <c:forEach var="item" items="${pageUnrevisit}">
							<tr>
								<c:if test="${item.clientName==null || item.clientName=='' }">
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
									<td>&nbsp;&nbsp;</td>
							    </c:if>
							    <c:if test="${item.clientName!=null && item.clientName!='' }">
							        <td><i class="menu-icon fa fa-user">&nbsp;&nbsp;${item.clientName}</i></td>
									<td><i class="menu-icon fa fa-tag">&nbsp;&nbsp;${item.clientLevel}</i></td>
									<td><i class="menu-icon fa fa-star">&nbsp;&nbsp;${item.sourceMemo}</i></td>
							    </c:if>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		 </div>
	</div>
</div>
<div class="row">
	<div class="col-sm-12">
		<div class="widget-box transparent ">
			<div class="widget-header widget-header-flat widget-header-small">
				<h5 class="widget-title">
					<i class="ace-icon fa fa-signal"></i>客户注册数量统计(按月)
				</h5>
			</div>
			<div class="widget-body">
				<div class="widget-main">
					<!-- #section:plugins/charts.flotchart -->
					<div id="divClientSum" style="height:300px"></div>
				</div>
				<!-- /.widget-main -->
			</div>
			<!-- /.widget-body -->
		</div>
		<!-- /.widget-box -->
	</div>
</div>
<!-- <div class="row"> -->
<!-- 	<div class="col-sm-4"> -->
<!-- 		<div class="widget-box"> -->
<!-- 			<div class="widget-header"> -->
<!-- 				<h4 class="widget-title lighter smaller"> -->
<!-- 					<i class="ace-icon fa fa-comment blue"></i> -->
<!-- 					Conversation -->
<!-- 				</h4> -->
<!-- 			</div> -->

<!-- 			<div class="widget-body"> -->
<!-- 				<div class="widget-main no-padding"> -->
<!-- 					<div class="dialogs"> -->
<!-- 						<div class="infobox infobox-blue infobox-small infobox-dark"> -->
<!-- 							<div class="infobox-chart"> -->
<!-- 								<span class="sparkline" data-values="3,4,2,3,4,4,2,2"></span> -->
<!-- 							</div> -->

<!-- 							<div class="infobox-data"> -->
<!-- 								<div class="infobox-content">Earnings</div> -->
<!-- 								<div class="infobox-content">$32,000</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="dialogs"> -->
<!-- 						<div class="col-sm-6"> -->
<!-- 						2 -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-6"> -->
<!-- 						3 -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="dialogs"> -->
<!-- 						<div class="col-sm-12"> -->
<!-- 						4 -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="vspace-12-sm"></div> -->
<!-- 	<div class="col-sm-4"> -->
<!-- 	</div> -->
<!-- 	<div class="vspace-12-sm"></div> -->
<!-- 	<div class="col-sm-4"> -->
<!-- 	</div> -->
<!-- </div> -->
<script>


	$(function () {
       $("#btnMonthNew").click(function(){
    	   showPageContent("../client/clientBasicPage/monthNew","客户信息");
       });
        $("#btnMonthTransacted").click(function(){
        	showPageContent("../client/clientBasicPage/monthTransacted","客户信息");
       });
       $("#btnUnrevisit").click(function(){
        	showPageContent("../client/clientBasicPage/unrevisit","客户信息");
       });
	   
	   //6-显示统计图
       sumClients();
	});
	
	
	function sumClients(){
		 $.get("../user/sumClientsByMonth",function(sumByStrings,status){
	    	   if(sumByStrings!=null){
		       		var rows =dataToFlotData(sumByStrings);
		       		var ticks=dataToFlotTicks(sumByStrings);
		       		console.log(JSON.stringify(ticks));
		       	 	$.plot($("#divClientSum"),[{
		       	 		label:"",
		       	 		data:rows
		       	 	}],{
			       	 	xaxis:{
			       	 		ticks:ticks
			       	 	}
		       	 	});
	    	   }
	       });
	}
	
	function dataToFlotData(sumByStrings){
		var rows=new Array();
		for(var index in sumByStrings){
			rows[rows.length]=new Array();
			rows[index][0]=index;
			rows[index][1]=sumByStrings[index].sumCount;
		}
		return rows;
	}
	
	function dataToFlotTicks(sumByStrings){
		var ticks=new Array();
		for(var index in sumByStrings){
			ticks[ticks.length]=new Array();
			ticks[index][0]=index;
			ticks[index][1]=sumByStrings[index].sumTitle;
		}
		return ticks;
	}
    
	
	//显示柱状图
	function showSumByCategoryChart(divId,strSubUrl,groupTimeFormat){
	    var nowTime=new Date();
	    var reqObj={
		    groupTimeFormat:groupTimeFormat,
		    timeBegin:timeToString("YYYY-01-01 00:00:00",nowTime),
	    	timeEnd  :timeToString("YYYY-MM-DD 23:59:59",nowTime)
		};
	    $.ajax({
			type : "post",
			url : "../sum/"+strSubUrl,
			data : JSON.stringify(reqObj),
			contentType : "application/json; charset=utf-8",
			datatype:'json',
			error : function() {
				alert("查询异常");
			},
			success : function(sums) {
			    var legendData=[];
			    for(var i in sums)
					if(legendData.indexOf(sums[i].sumCategory)<0)
						legendData[legendData.length]=sums[i].sumCategory;
				   
			    var xAxisData=[];
			    for(var i in sums)
					if(xAxisData.indexOf(sums[i].sumTitle)<0)
						xAxisData[xAxisData.length]=sums[i].sumTitle;
			    xAxisData.sort();
			    
			    var seriesAry=[];
			    for(var i in legendData)
				{
				    seriesAry[i]=new Object();
					seriesAry[i].name =legendData[i];
					seriesAry[i].label={show: true,position: 'top',color:'#ffffff'};
					seriesAry[i].data =[];
					seriesAry[i].type = 'bar';
				}
			    for(var i in sums) {
				   var categoryIndex=-1,titleIndex=-1;
				  	for(var j in legendData)
					   if(sums[i].sumCategory==legendData[j])
						{
					      	categoryIndex=j;
					      	break;
						}
				  	for(var j in xAxisData)
					   if(sums[i].sumTitle==xAxisData[j])
						{
					      	titleIndex=j;
					      	break;
						}
				  	seriesAry[categoryIndex].data[titleIndex]=new Object();
				  	seriesAry[categoryIndex].data[titleIndex].name =sums[i].sumTitle;
				  	seriesAry[categoryIndex].data[titleIndex].value=sums[i].sumCount;
			    }
			    showCategoryChart("",divId,legendData,xAxisData,seriesAry);
			}
	    });  
	}


	//显示柱状图
	function showCategoryChart(charTitle,divId,legendData,categoryDatas,seriesAry)
	{
	    var option = {
		    title: {
		            text: charTitle,
		            x:30,
		            y:20,
		            textStyle:{
		        		color:"black"
		            }
		    },
		    legend: {
		        orient: 'horizontal',
		        textStyle: {
			         color: "black",
			    },
		        data: legendData,
		        x:"center",
		        y:"bottom",
		        bottom:10,
		    },
		    xAxis: {
		        type: 'category',
		        axisLine: {
	            lineStyle: {
	        	  color: "#CECECE"
	            }
	            },
		        data:categoryDatas,
		        axisLabel: {
	                    show: true,
	                    textStyle: {
	                        color: "#CECECE"
	                    }
	            }
		    },
		    yAxis: {
		        type: 'value',
		        axisLine: {
	            lineStyle: {
	        	  color: "#CECECE"
	            }
	            },
	            axisLabel: {
	                    show: true,
	                    textStyle: {
	                        color: "#CECECE"
	                    }
	            }
		    },
		    series:seriesAry
		};
	    echarts.init(document.getElementById(divId)).setOption(option);
	}

	
</script>