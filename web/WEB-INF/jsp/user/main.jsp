<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="common/header.jsp"%>
<div class="page-title">
	<div class="title_left">
		<h3>
			<strong>欢迎你：
			<c:choose>
				<c:when test="${empty  userSession.userName}">
				未实名
				</c:when>

				<c:otherwise>
				${userSession.userName}
		</c:otherwise>
			</c:choose>



			| 角色：租房用户</strong>
		</h3>
	</div>
</div>
<div class="clearfix"></div>
<%@include file="common/footer.jsp"%>
