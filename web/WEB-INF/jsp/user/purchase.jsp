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
        ${requestScope.addressName}>>>${requestScope.buildingName}>>>${requestScope.layerId}层>>>${requestScope.name}

        请选择

  </div>
</div>
<%@include file="common/footer.jsp"%>
