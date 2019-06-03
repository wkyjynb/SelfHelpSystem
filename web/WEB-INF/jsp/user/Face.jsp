<%--
  Created by IntelliJ IDEA.
  User: LXW
  Date: 2019/5/31
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>人脸管理</title>
</head>
<body>
<% request.setAttribute("path",request.getContextPath());%>
<div style="text-align:center">

    <span style="color: red"> ${room.addressName}>>>${room.buildingName}>>>${room.layerId}层>>>${room.name}</span>

    <video height="400px" width="400px" autoplay="autoplay"></video>
    <input type="button" title="点击录入人脸" value="点击录入人脸" onclick="getPhoto1();" />
    <br />
    <canvas id="canvas1" height="400px" style="display: none"></canvas>
</div>



<script type="text/javascript" src="${requestScope.path}/statics/js/jquery-1.8.3.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        getMedia();
    })
    var video = document.querySelector('video');
    var audio, audioType, close;

    var canvas1 = document.getElementById('canvas1');
    var context1 = canvas1.getContext('2d');

    navigator.getUserMedia = navigator.getUserMedia
        || navigator.webkitGetUserMedia || navigator.mozGetUserMedia
        || navigator.msGetUserMedia;
    window.URL = window.URL || window.webkitURL || window.mozURL
        || window.msURL;

    var exArray = []; //存储设备源ID
    MediaStreamTrack.getSources(function(sourceInfos) {
        for (var i = 0; i != sourceInfos.length; ++i) {
            var sourceInfo = sourceInfos[i];
            //这里会遍历audio,video，所以要加以区分
            if (sourceInfo.kind === 'video') {
                exArray.push(sourceInfo.id);
            }
        }
    });

    function getMedia() {
        if (navigator.getUserMedia) {
            navigator.getUserMedia({
                'video' : {
                    'optional' : [ {
                        'sourceId' : exArray[1]
                        //0为前置摄像头，1为后置
                    } ]
                },
                'audio' : true
            }, successFunc, errorFunc);//success是获取成功的回调函数
        } else {
            alert('Native device media streaming (getUserMedia) not supported in this browser.');
        }
    }

    function successFunc(stream) {
        //alert('Succeed to get media!');
        if (video.mozSrcObject !== undefined) {
            //Firefox中，video.mozSrcObject最初为null，而不是未定义的，我们可以靠这个来检测Firefox的支持
            video.mozSrcObject = stream;
        } else {
            video.src = window.URL && window.URL.createObjectURL(stream)
                || stream;
        }
        //video.play();

        // 音频
        audio = new Audio();
        audioType = getAudioType(audio);
        if (audioType) {
            audio.src = 'polaroid.' + audioType;
            audio.play();
        }
        close = stream;
    }
    function errorFunc(e) {
        alert('Error！' + e);
    }

    //获取音频格式
    function getAudioType(element) {
        if (element.canPlayType) {
            if (element.canPlayType('audio/mp4; codecs="mp4a.40.5"') !== '') {
                return ('aac');
            } else if (element.canPlayType('audio/ogg; codecs="vorbis"') !== '') {
                return ("ogg");
            }
        }
        return false;
    }

    // vedio播放时触发，绘制vedio帧图像到canvas
    //        video.addEventListener('play', function () {
    //            drawVideoAtCanvas(video, context2);
    //        }, false);

    //拍照
    function getPhoto() {
        context1.drawImage(video, 0, 0, 400, 400); //将video对象内指定的区域捕捉绘制到画布上指定的区域，实现拍照。
    }

    function getMedia1() {
        close.getVideoTracks()[0].stop();
    }
    function getPhoto1() {
        getPhoto();
        var canvans1 = document.getElementById("canvas1");
        //获取浏览器页面的画布对象
        //以下开始编 数据
        var imgData = canvans1.toDataURL();
        //将图像转换为base64数据
        var base64Data = imgData.substr(22);






        $.ajax({
            url: "${requestScope.path}/user/addFace",
            type: "post",
            data: {
                img: base64Data,
                roomId:"${room.id}"
            },dataType:"json",
            success: function (data) {
            if(data.error_code==""){
                alert("录入人脸成功");
            }else{
                alert("录入失败请稍后在试");
            }
            }
        } );
    }
</script>
</body>
</html>
