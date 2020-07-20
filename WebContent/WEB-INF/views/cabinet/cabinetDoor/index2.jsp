<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<link rel="stylesheet" type="text/css" href="${ctx}/styles/css/cabinetdoor.css" />
<script type="text/javascript" src="${ctx}/scripts/slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/html2canvas/html2canvas.js"></script>

<div class="pageContent">
    <div class="page-left">
        <ul class="main-menu">
            <c:forEach items="${cabinetList}" var="cobj" varStatus="cstatus">
                <li>
                    <span class="pp-name">${cobj.cabinetPositionDetail}</span>
                    <div class="page-img">
                        <a href="#" onclick="showCabinetDoor('${cobj.id}','${cobj.cabinetNumber}',this);">
                            <c:if test="${cobj.ext2!='' && cobj.ext2!=null}">
                                <img id="img_${cobj.id}" src="${cobj.ext2}" width='100%' height='20%'/>
                            </c:if>
                            <c:if test="${cobj.ext2=='' || cobj.ext2==null}">
                                <img id="img_${cobj.id}" src="${ctx}/images/cabinetdoor/16层.jpg" width='100%' height='20%'/>
                            </c:if>
                        </a>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="page-right">
        <div id="content" class="cdoor"></div>

        <div class="cemply">
            <form:form id="ceform" method="post" action="${ctx}/cabinet/cabinetDoor/saveOrUpdate"   modelAttribute="cabinetDoor" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
                <input type="hidden" name="cabinetId" id="cabinetId" value="62ddd43da5f6400a9d6b87f74ba96ef9"/>
                <input type="hidden" name="ext2" id="ext2" value=""/>
                <div class="pageFormContent" layoutH="50">
                    <div class="unit">
                        <label>柜门编号：</label>
                        <input type="text" id="cabinetDoorName" name="cabinetDoorName" size="40" value="" readonly="readonly"/>
                        <input type="hidden" id="cabinetDoorNumber" name="cabinetDoorNumber" value="">
                    </div>
                    <div class="unit">
                        <label>使用人：</label>
                        <select name="employeeId" id="employeeId" class="required">
                            <option value=""  desc="">-- 请选择员工--</option>
                            <c:forEach items="${employeeList}" var="employeeObj">
                                <option value="${employeeObj.id}" <c:if test="${employeeObj.id==cabinetDoor.employeeId}">selected="selected"</c:if> > ${employeeObj.employeeName} - ${employeeObj.employeeNumber}</option>
                            </c:forEach>
                        </select>
                        <input type="checkbox" value="0" onchange="showAllEMP(checked)" id="checkShowAllEmployee"/>显示全部
                    </div>
                </div>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveChoose();">提交</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
                    </ul>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script type="text/javascript">
    //$(".active").find("a").click();
    $('#employeeId').select2();
    /*$(".pageContent").css("max-height",$(window).height()-100);
    $(".pageContent").css("overflow", "hidden");
    $(".pageContent").slimScroll({
        height: $(window).height()-150,
        distance: '0px',
        borderRadius: '7px',
        size: '7px'
    });*/

    $('.page-left').slimScroll({
        width:$(window).width()*0.11,
        height: $(window).height()-150, //容器高度
        size:"3px", //滚动条宽度
        color:"rgb(57, 110, 224)", //滚动条颜色,默认#000000
        alwaysVisible: true, //是否禁用隐藏滚动条,默认false
        opacity:0.5 // 滚动条透明度

    });
    var prwidth =($('.page').width()-$('.page-left').width()-100)+'px';
    $(".page-right").width(prwidth);

    initPage("62ddd43da5f6400a9d6b87f74ba96ef9");
    function chooseDoor(obj,dnum){
        $(obj).parent().siblings().find("button").removeClass("selectedoor");
        $(obj).parent().parent().siblings().find("button").removeClass("selectedoor");
        $(obj).addClass("selectedoor");

        $("#cabinetDoorName").val($(obj).text());
        $("#cabinetDoorNumber").val(dnum);
    }
    function showCabinetDoor(cabinetId,dnumber,obj){
        $(obj).parent().parent().addClass("active");
        $(obj).parent().parent().siblings().removeClass("active");
        $("#ceform").find('input[type=text],select,input[type=hidden]').each(function() {
           $(this).val('');
        });
        $("#cabinetId").val(cabinetId);
        if(dnumber=='120'){
            var dhtml="<div class='left-1'>";
            var dum = 1;
            for(var i=1;i<14;i++){
                dhtml+="<div id='A"+i+"'><button onclick='chooseDoor(this,"+dum+");'>A"+i+"</button></div>";
                dum++;
            }
            dhtml+="</div><div class='left-2'>";
            for(var i=1;i<5;i++){
                dhtml+="<div id='B"+i+"'><button onclick='chooseDoor(this,"+dum+");'>B"+i+"</button></div>";
                dum++;
            }
            dhtml+="</div><div class='left-2'>";
            for(var i=1;i<5;i++){
                dhtml+="<div id='C"+i+"'><button onclick='chooseDoor(this,"+dum+");'>C"+i+"</button></div>";
                dum++;
            }
            dhtml+="</div><div class='left-3'>";
            for(var i=1;i<14;i++){
                dhtml+="<div id='D"+i+"'><button onclick='chooseDoor(this,"+dum+");'>D"+i+"</button></div>";
                dum++;
            }
            dhtml+="</div>";
            $("#content").empty();
            $("#content").html(dhtml);
            var h = $(".left-1").height()/13;
            $(".left-2").css("margin-top",h*9);
        }
        if(dnumber=='161'){
            var dhtml="";
            var zm = ["A","B","C","D","E","F","G"];
            var dum=1;
            for(var h=0;h<zm.length;h++){
                dhtml+="<div class='left-1'>";
                for(var i=1;i<18;i++){
                    dhtml+="<div id='"+zm[h]+i+"'><button onclick='chooseDoor(this,"+dum+");'>"+zm[h]+i+"</button></div>";
                    dum++;
                }
                dhtml+="</div>";
            }
            $("#content").empty();
            $("#content").html(dhtml);
        }
        if(dnumber=='162'){
            var dhtml="";
            var zm = ["H","I","J","K","L","M","N"];
            var dum=1;
            for(var h=0;h<zm.length;h++){
                dhtml+="<div class='left-1'>";
                for(var i=1;i<18;i++){
                    dhtml+="<div id='"+zm[h]+i+"'><button onclick='chooseDoor(this,"+dum+");'>"+zm[h]+i+"</button></div>";
                    dum++;
                }
                dhtml+="</div>";
            }
            $("#content").empty();
            $("#content").html(dhtml);
        }
        if(dnumber=='171'){
            var dhtml="";
            var zm = ["A","B","C","D","E","F","G"];
            var dum=1;
            for(var h=0;h<zm.length;h++){
                dhtml+="<div class='left-1'>";
                for(var i=1;i<18;i++){
                    dhtml+="<div id='"+zm[h]+i+"'><button onclick='chooseDoor(this,"+dum+");'>"+zm[h]+i+"</button></div>";
                    dum++;
                }
                dhtml+="</div>";
            }
            $("#content").empty();
            $("#content").html(dhtml);
        }
        if(dnumber=='172'){
            var dhtml="";
            var zm = ["H","I","J","K","L","M","N"];
            var dum=1;
            for(var h=0;h<zm.length;h++){
                dhtml+="<div class='left-1'>";
                for(var i=1;i<18;i++){
                    dhtml+="<div id='"+zm[h]+i+"'><button onclick='chooseDoor(this,"+dum+");'>"+zm[h]+i+"</button></div>";
                    dum++;
                }
                dhtml+="</div>";
            }
            $("#content").empty();
            $("#content").html(dhtml);
        }
        if(dnumber=='181'){
            var dhtml="";
            var zm = ["A","B","C","D","E","F","G"];
            var dum=1;
            for(var h=0;h<zm.length;h++){
                dhtml+="<div class='left-1'>";
                for(var i=1;i<18;i++){
                    dhtml+="<div id='"+zm[h]+i+"'><button onclick='chooseDoor(this,"+dum+");'>"+zm[h]+i+"</button></div>";
                    dum++;
                }
                dhtml+="</div>";
            }
            $("#content").empty();
            $("#content").html(dhtml);
        }
        if(dnumber=='182'){
            var dhtml="";
            var zm = ["H","I","J","K","L","M","N"];
            var dum=1;
            for(var h=0;h<zm.length;h++){
                dhtml+="<div class='left-1'>";
                for(var i=1;i<18;i++){
                    dhtml+="<div id='"+zm[h]+i+"'><button onclick='chooseDoor(this,"+dum+");'>"+zm[h]+i+"</button></div>";
                    dum++;
                }
                dhtml+="</div>";
            }
            $("#content").empty();
            $("#content").html(dhtml);
        }
        if(dnumber=='200'){
            var dhtml="";
            var dum=1;
            var zm = ["A","B","C"];
            for(var h=0;h<zm.length;h++){
                dhtml+="<div class='left-1'>";
                for(var i=1;i<11;i++){
                    dhtml+="<div id='"+zm[h]+i+"'><button onclick='chooseDoor(this,"+dum+");'>"+zm[h]+i+"</button></div>";
                    dum++;
                }
                dhtml+="</div>";
            }
            $("#content").empty();
            $("#content").html(dhtml);
        }
        if (dnumber=='270'){
            var dhtml="<div class='left-1'>";
            var dum=1;
            for(var i=1;i<16;i++){
                dhtml+="<div id='A"+i+"'><button onclick='chooseDoor(this,"+dum+");'>A"+i+"</button></div>";
                dum++;
            }
            dhtml+="</div><div class='left-2'>";
            for(var i=1;i<6;i++){
                dhtml+="<div id='B"+i+"'><button onclick='chooseDoor(this,"+dum+");'>B"+i+"</button></div>";
                dum++;
            }
            dhtml+="</div><div class='left-2'>";
            for(var i=1;i<6;i++){
                dhtml+="<div id='C"+i+"'><button onclick='chooseDoor(this,"+dum+");'>C"+i+"</button></div>";
                dum++;
            }
            dhtml+="</div><div class='left-3'>";
            for(var i=1;i<16;i++){
                dhtml+="<div id='D"+i+"'><button onclick='chooseDoor(this,"+dum+");'>D"+i+"</button></div>";
                dum++;
            }
            dhtml+="</div>";
            $("#content").empty();
            $("#content").html(dhtml);
            var h = $(".left-1").height()/15;
            $(".left-2").css("margin-top",h*10);
        }
        getSelectedDoorEmployee(cabinetId);
        //因为有解绑的情况，每次点击重新画图，保存图片
        getScreenShort(cabinetId);
        setTimeout(function(){
            var pageImg = $("#img_"+cabinetId).attr("src");
            $.ajax({
                type : "post",
                url : "${ctx}/cabinet/cabinetDoor/updateCabinetImg",
                dataType:'json',
                data:{cabinetId:cabinetId,pageImg:pageImg},
                async : false,
                success : function(){
                }
            });
        }, 1000);
    }
    function getSelectedDoorEmployee(cabinetId){
        $.ajax({
            type : "post",
            url : "${ctx}/cabinet/cabinetDoor/getCabinedoorList",
            dataType:'json',
            data:{"cabinetId":cabinetId},
            async : false,
            success : function(response){
                var list = response;
                for(var i=0;i<list.length;i++){
                    $("#"+list[i].cabinetDoorName+" button").text(list[i].cabinetDoorName+"-"+list[i].employee.employeeName)
                    $("#"+list[i].cabinetDoorName+" button").addClass("checkdoor");
                    $("#"+list[i].cabinetDoorName+" button").attr("disabled","disabled");
                }
            },
            error : function(e) {
                alert("error");
                //请求失败时调用此函数
            }
        });
    }
    //页面进来显示12层的柜子
    function initPage(cabinetId){
        $(".main-menu li:first").addClass("active");
        setTimeout(function(){
            var dhtml="<div class='left-1'>";
            for(var i=1;i<14;i++){
                dhtml+="<div id='A"+i+"'><button onclick='chooseDoor(this);'>A"+i+"</button></div>";
            }
            dhtml+="</div><div class='left-2'>";
            for(var i=1;i<5;i++){
                dhtml+="<div id='B"+i+"'><button onclick='chooseDoor(this);'>B"+i+"</button></div>";
            }
            dhtml+="</div><div class='left-2'>";
            for(var i=1;i<5;i++){
                dhtml+="<div id='C"+i+"'><button onclick='chooseDoor(this);'>C"+i+"</button></div>";
            }
            dhtml+="</div><div class='left-3'>";
            for(var i=1;i<14;i++){
                dhtml+="<div id='D"+i+"'><button onclick='chooseDoor(this);'>D"+i+"</button></div>";
            }
            dhtml+="</div>";
            $("#content").empty();
            $("#content").html(dhtml);
            var h = $(".left-1").height()/13;
            $(".left-2").css("margin-top",h*9);
            getSelectedDoorEmployee(cabinetId);
        },100);

    }
    function showAllEMP(checkbox){
        var vau = document.getElementById("checkShowAllEmployee").checked;
        var param;
        if(vau){
            param=0;
        }else {
            param=1;
        }
        $.ajax({
            type : "post",
            url : "${ctx}/employee/employee/selectAllEmployee?check="+param,
            async : true,
            success : function(data){
                $("#employeeId").html(data);
            },
            error : function(e) {
                alert("error");
                //请求失败时调用此函数
            }
        });
    }
    function saveChoose(){
        var cabinetId = $("#cabinetId").val();
        getScreenShort(cabinetId);
        setTimeout(function(){
            var pageImg = $("#img_"+cabinetId).attr("src");
            $("#ext2").val(pageImg);
            $("#ceform").submit();
        }, 1000);
    }
    function getScreenShort(pageId){
        $("#img_"+pageId).attr("width","100%");
        $("#img_"+pageId).attr("height","20%");

        var html = $("#content").html();
        //创建一个新的canvas
        var canvas2 = document.createElement("canvas");
        let _canvas = document.querySelector('#content');
        var w = parseInt(window.getComputedStyle(_canvas).width);
        var h = parseInt(window.getComputedStyle(_canvas).height);
        //将canvas画布放大若干倍，然后盛放在较小的容器内，就显得不模糊了
        canvas2.width = w*2+"px";
        canvas2.height = h*2+"px";
        canvas2.style.width = w+"px";
        canvas2.style.height = h+"px";
        //可以按照自己的需求，对context的参数修改,translate指的是偏移量
        //  var context = canvas.getContext("2d");
        //  context.translate(0,0);
        var context = canvas2.getContext("2d");
        context.scale(2,2);
        html2canvas(document.querySelector('#content'),{canvas:canvas2,logging:false,useCORS:true,backgroundColor:null}).then(function(canvas) {
            //document.body.appendChild(canvas);
            //$("#img_"+pageId).attr('src',canvas.toDataURL());
            console.log(document.querySelector("#img_"+pageId));
            document.querySelector("#img_"+pageId).setAttribute('src',canvas.toDataURL());
            //canvas转换成url，然后利用a标签的download属性，直接下载，绕过上传服务器再下载
            // document.querySelector(".down").setAttribute('href',canvas.toDataURL());
            /*$.mySessionAjax({
                url:contentPath+'/displayManage/createImg',
                data:{pageId:pageId,pageImg:canvas.toDataURL()}
            });*/
        });
    }
</script>



