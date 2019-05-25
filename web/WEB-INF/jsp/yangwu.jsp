<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="div" style="width: 600px; height: 400px;"></div><br/><br/>
	<div style="margin-left: 400px ">查询：<input type="date" id="date"> - <input type="date" id="date1"> <input type="button" value="查询" onclick="typetext()"></div><br/>
	<div>
		<div id="div1" style="width: 600px; height: 400px; float: left;">
		</div>
		<div id="div2" style="width: 600px; height: 400px; float: left;"></div>
	</div>
	<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
	<script src="js/echarts.min.js"></script>
	<script type="text/javascript">
		var myChart = echarts.init(document.getElementById('div'));
		var myChart1 = echarts.init(document.getElementById('div1'));
		var myChart2 = echarts.init(document.getElementById('div2'));
		var at = 1;
		var arr = [];
		var arr1 = [];
		var arr2 = [];
		var date = [];
		var humidity=[];
		var yanwu=[ {value : 0,name : '检测到烟雾'}, {value : 0,name : '未检测到烟雾'} ];
		option = {
			title : {
				text : '烟雾检测器',
				left : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : '{a} <br/>{b} : {c}'
			},
			legend : {
				left : 'left',
				data : [ '温度', '相对湿度' ]
			},
			xAxis : {
				type : 'category',
				name : 'x',
				splitLine : {
					show : false
				},
				data : date
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			yAxis : {
				type : 'log',
				name : 'y'
			},
			series : [ {
				name : '相对湿度',
				type : 'line',
				data : arr
			}, {
				name : '温度',
				type : 'line',
				data : arr1
			}, {
				name : '烟雾',
				type : 'line',
				data : arr2
			} ]
		};
		function fact() {
			$.ajax({
				"url" : "${pageContext.request.contextPath}/sel.html",
				"type" : "POST",
				"data" : "",
				"dataType" : "JSON",
				"success" : fortext
			})
			function fortext(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].smoke == 1) {
						arr2.push(99 + data[i].smoke);
					} else {
						arr2.push("1" + data[i].smoke);
					}
					arr.push(data[i].temperature);
					arr1.push(data[i].humidity);
					date.push(data[i].date);
				}
				myChart.setOption(option);
				myChart.hideLoading();
			}
		}
		setInterval("fact1()", 30000);
		function fact1() {
			if (at == 1) {
				fact();
				at--;
			}
			$.ajax({
				"url" : "${pageContext.request.contextPath}/add.html",
				"type" : "POST",
				"data" : "",
				"dataType" : "JSON",
				"success" : fortext1
			})
			function fortext1(data) {
				if (data.smoke == 1) {
					arr2.push(99 + data.smoke);
				} else {
					arr2.push("1" + data.smoke);
				}
				arr.push(data.temperature);
				arr1.push(data.humidity);
				date.push(data.date);
				myChart.setOption(option);
				myChart.hideLoading();
			}
		}
		option3 = {
			title : {
				text : '烟雾数据展示',
				subtext : '',
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				left : 'left',
				data : [ '检测到烟雾', '未检测到烟雾' ]
			},
			series : [ {
				name : '访问来源',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : yanwu,
				itemStyle : {
					emphasis : {
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : 'rgba(0, 0, 0, 0.5)'
					}
				}
			} ]
		};
		option2 = {
			xAxis : {
				type : 'category',
				data : [ '温度', '相对湿度' ]
			},
			yAxis : {
				type : 'value'
			},
			series : [ {
				data : humidity,
				type : 'bar'
			} ]
		};
		function typetext(){
			var date=$("#date").val();
			var date1=$("#date1").val();
			alert(date);
			if(date!=null&&date1!=null){
				$.ajax({
					"url" : "${pageContext.request.contextPath}/date.html",
					"type" : "POST",
					"data" : "date="+date+"&date1="+date1,
					"dataType" : "JSON",
					"success" : fack
				})
				function fack(data){
					yanwu[0].value=data[0].smoke1;
					yanwu[1].value=data[0].smoke;
					humidity[0]=data[0].humidity;
					humidity[1]=data[0].temperature;
					myChart1.setOption(option3);
					myChart1.hideLoading();
					myChart2.setOption(option2);
					myChart2.hideLoading();
				}
			}
		}
	</script>
</body>
</html>