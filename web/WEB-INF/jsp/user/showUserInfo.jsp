<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="common/header.jsp"%>
<div class="clearfix"></div>
<div class="row">
  <div class="col-md-12 col-sm-12 col-xs-12">
    <div class="x_panel">
      <div class="x_title">
        <h2>用户信息 <i class="fa fa-user"></i><small>${devUserSession.devName}</small></h2>
             <div class="clearfix"></div>
      </div>
      <div class="x_content">

        <div>
          <c:choose>
            <c:when test="${empty  userSession.email}">
              <a href="${pageContext.request.contextPath}/user/emailAdd.html">未绑定邮箱</a>
            </c:when>

            <c:otherwise>
              已绑定 ${userSession.email}邮箱
            </c:otherwise>
          </c:choose>
        </div>

        <div>
          <c:choose>
            <c:when test="${empty  userSession.wxId}">
              <a href="${pageContext.request.contextPath }/WeCat/login1">未绑定微信</a>
            </c:when>

            <c:otherwise>
              已绑定微信
            </c:otherwise>
          </c:choose>
        </div>

        <div>
          当前账户余额:<span id="moneys">${userSession.money}</span>
        </div>

        <div>
          <c:choose>
            <c:when test="${empty  userSession.identityId}">
              <a href="${pageContext.request.contextPath}/user/updateUserInfo.html">未实名认证</a>
            </c:when>

            <c:otherwise>
                已实名认证>${userSession.userName}<br/>
              <a id="but">点击充值</a>
            </c:otherwise>
          </c:choose>
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.js"></script>
        <script type="text/javascript">
         var  path="${pageContext.request.contextPath }";
          $("#but").click(function () {
            var money = prompt("请输入充值金额");
            $.getJSON(path+"/user/updateMoney",{"money":money},function (data) {
              if(data.num==1){
              $("#moneys").text(Number($("#moneys").text())+Number(money));
              }
            })
          });
        </script>

      </div>
    </div>
  </div>
</div>
<%@include file="common/footer.jsp"%>
<%--
<script src="${pageContext.request.contextPath }/statics/localjs/appinfoadd.js"></script>--%>
