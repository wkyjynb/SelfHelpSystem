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
                <h2>邮箱绑定</h2>
                <div class="clearfix"></div>
            </div>

            <form id="form1" action="${pageContext.request.contextPath}/user/addIdentityId" method="post" enctype="multipart/form-data">
                <ul>
                    <li>  Email : <input type="email" id="front" name="email"></li><br/>
                    <li> 验证码 : <input type="text" onblur="back()" name="yanzhengma"> <input type="button" id="butt" value="获取验证码" onclick="but()"></li><br/>
                    <input type="submit" value="提交">
                </ul>
            </form>
            <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.js"></script>
            <script type="text/javascript">
                function but() {
                    var qq=$("#front").val();
                    alert(qq);
                    $.ajax({
                        "url" : "${pageContext.request.contextPath }/user/yan.html",
                        "type" : "POST",
                        "data" : "qq="+qq,
                        "dataType" : "JSON",
                        "success" : make
                    })
                    function  make(data) {
                        if(data.yan==1){
                            alert("发送成功，注意查看信息");
                            i=60;
                            setInterval("time()",1000);
                        }
                        if(data.yan==0){
                            alert("发生失败，出现错误");
                        }
                    }
                }
                function back() {

                }
                var i=60;
                function  time() {
                    if(i>0){
                        i--;
                       $("#butt").val("重新发送("+i+")");
                    }
                    if(i==0){
                        $("#butt").val("获取验证码");
                    }
                }
            </script>
        </div>
<%@include file="common/footer.jsp"%>