<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>APP开发者平台</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath }/statics/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath }/statics/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath }/statics/css/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath }/statics/css/custom.min.css" rel="stylesheet">
</head>

<body class="login">
<div>
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>

    <div class="login_wrapper">
        <div class="animate form login_form">
            <section class="login_content">
                <form action="${pageContext.request.contextPath }/modifyPwd.html" method="post">
                    <h1>找回密码</h1>
                    <div>
                        <input type="text" class="form-control" name="phone" id="phone" placeholder="请输入手机号" required="" />
                    </div>
                    <div>
                        <input type="text" class="form-control" onblur="back()" name="yanzhengma" id="text" placeholder="输入验证码"> <input type="button" id="butt" value="获取验证码" onclick="but()">
                    </div>
                    <div style="display: none" class="div">
                        <input type="password" class="form-control" name="pwd" placeholder="请输入新密码" required="" />
                    </div>
                    <div style="display: none" class="div">
                        <button type="submit" class="btn btn-success">提   交</button>
                        <button type="reset" class="btn btn-default">重　填</button>
                    </div>
                </form>
            </section>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
    function but() {
        var phone=$("#phone").val();
        if(phone==""||phone==null){
            alert("请先填写手机号");
            return;
        }
        $.ajax({
            "url" : "${pageContext.request.contextPath }/modifyPhone.html",
            "type" : "POST",
            "data" : "phone="+phone,
            "dataType" : "JSON",
            "success" : make
        })
        function  make(data) {
            if(data.yan==1){
                alert("发送成功，注意查看邮箱信息");
                i=60;
                $("#butt").attr({disabled: true});
                setInterval("time()",1000);
            }
            if(data.yan==0){
                alert("发生失败，出现错误");
            }
        }
    }
    function back() {
        var phone=$("#phone").val();
        if(phone==""||phone==null){
            alert("请先填写手机号");
            return;
        }
        var text=$("#text").val();
        $.ajax({
            "url" : "${pageContext.request.contextPath }/extits.html",
            "type" : "POST",
            "data" : "phone="+phone,
            "dataType" : "JSON",
            "success" : make
        })
        function  make(data) {
            if(text!=null||text!=""){
                if(data.zhen!=text||data.zhen==""){
                    alert("验证码输入错误");
                }
                if(data.zhen==text&&data.zhen!=""){
                    $("#butt").hide();
                    $(".div").show();
                }
            }
        }
    }
    var i=60;
    function  time() {
        if(i>0){
            i--;
            $("#butt").val("重新发送("+i+")");
        }
        if(i==0){
            $("#butt").removeAttr('disabled');
            $("#butt").val("获取验证码");
        }
    }
</script>

</body>
</html>