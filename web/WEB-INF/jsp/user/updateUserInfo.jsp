<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="common/header.jsp"%>
<div class="clearfix"></div>
<div class="row">
  <div class="col-md-12 col-sm-12 col-xs-12">
    <div class="x_panel">
      <div class="x_title">
        <h2>查看个人信息 <i class="fa fa-user"></i></h2>
             <div class="clearfix"></div>
      </div>
      <div class="x_title">
            <h2>实名认证</h2>
          <div class="clearfix"></div>
      </div>

        <form id="form1" action="${pageContext.request.contextPath}/user/addIdentityId" method="post" enctype="multipart/form-data">
            <ul>
              <li>  身份证正面:<input type="file" id="front" name="attachs"></li>
                <li> 身份证反面:<input type="file" id="back" name="attachs"></li>
                <input type="submit" value="提交">
            </ul>
        </form>
        <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.js"></script>
        <script type="text/javascript">
           $("#form1").submit(function () {
                if($("#front").val()==""||$("#back").val()==""){
                alert("请上传完整信息");
                return false;
                }
                return true;
            })

        </script>

</div>
<%@include file="common/footer.jsp"%>