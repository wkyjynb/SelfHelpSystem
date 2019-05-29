<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="common/header.jsp"%>
<div class="clearfix"></div>
<div class="row">
  <div class="col-md-12 col-sm-12 col-xs-12">
    <div class="x_panel">
      <div class="x_title">
        <h2>购房页面 <i class="fa fa-user"></i><small>${devUserSession.devName}</small></h2>
             <div class="clearfix"></div>
      </div>
        ${room.addressName}>>>${room.buildingName}>>>${room.layerId}层>>>${room.name}
      <br/>
      <br/>
      当前账户余额:${userSession.money}
      <br/>
      <form method="post" action="${pageContext.request.contextPath}" id="form1s">
        请输入月数<input type="text" id="numbers">${room.price}/每月<br/>
        <input type="button" value="购买" id="buts">
        <input type="hidden" value="" id="stopTime" name="stopTime">
        <input type="hidden" value="" id="money" name="money">
      </form>
      <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.js"></script>
  <script type="text/javascript">
    $("#buts").click(function () {
      if(Number("${userSession.money}")<(Number("${room.price}")*Number($("#numbers").val()))){
        alert("当前余额不足");
      }else {
        var now = new Date();

        newDate = DateAdd("m ",Number($("#numbers").val()),now);
        var oDate = new Date(newDate);//转回时间类型
        alert(oDate.getFullYear()+"-"+oDate.getMonth()+"-"+oDate.getDate());

      }
    })

    function DateAdd(interval, number, date) {
      switch (interval) {
        case "y ": {
          date.setFullYear(date.getFullYear() + number);
          return date;
          break;
        }
        case "q ": {
          date.setMonth(date.getMonth() + number * 3);
          return date;
          break;
        }
        case "m ": {
          date.setMonth(date.getMonth() + number);
          return date;
          break;
        }
        case "w ": {
          date.setDate(date.getDate() + number * 7);
          return date;
          break;
        }
        case "d ": {
          date.setDate(date.getDate() + number);
          return date;
          break;
        }
        case "h ": {
          date.setHours(date.getHours() + number);
          return date;
          break;
        }
        case "m ": {
          date.setMinutes(date.getMinutes() + number);
          return date;
          break;
        }
        case "s ": {
          date.setSeconds(date.getSeconds() + number);
          return date;
          break;
        }
        default: {
          date.setDate(d.getDate() + number);
          return date;
          break;
        }
      }
    }
  </script>
  </div>
</div>
<%@include file="common/footer.jsp"%>
