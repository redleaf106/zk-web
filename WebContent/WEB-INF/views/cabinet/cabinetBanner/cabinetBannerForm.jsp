<%--
  Created by IntelliJ IDEA.
  User: 红叶
  Date: 2020/6/9
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<script type="text/javascript">

    $("#showBannerPic").click(function(){
        if(checkAttachmentSize()){
            var id = $("#attachmentId", navTab.getCurrentPanel()).val();
            if(id==""){
                id='${cabinetBanner.id}';
            }
            if (id != '') {
                alert(id);
                $("#picDivId", navTab.getCurrentPanel()).css("display", "");
                $("#picId", navTab.getCurrentPanel()).attr('src', '${ctx}/cabinet/cabinetBanner/showBannerPic?id=' + id + "&" + Math.floor(Math.random() * 100));
            } else {

                alertMsg.error("请先上传图片!");
                //alertMsg.error("123456!");
            }
        }
        return false;//<%-- 解决firefox自动提交 --%>
    });


    function checkAttachmentSize() {
        var attachmentSize = $("#attachmentSize").val();
        var attachmentMaxSize0 = $("#attachmentMaxSize", navTab.getCurrentPanel()).val().split("_")[0];
        var attachmentMaxSize1 = $("#attachmentMaxSize", navTab.getCurrentPanel()).val().split("_")[1];
        if(attachmentSize - attachmentMaxSize0 > 0){
            alertMsg.error("图片应不大于" + Math.floor(attachmentMaxSize1) + "KB，请重新选择图片!");
            return false;
        }
        return true;
    }


    <%-- 图片显示控制 --%>
    function scaleImg(obj){
        if(obj.width>200){
            obj.width=200;
        }
        if(obj.height>200){
            obj.height=200;
        }
    }

</script>

<div class="pageContent">
    <form:form method="post" action="${ctx}/cabinet/cabinetBanner/saveBannerPic" enctype="multipart/form-data"  modelAttribute="assistant" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone)">
        <div class="pageFormContent" layoutH="58">
            <div class="unit">
                <label>界面图片：</label>
                <input type="hidden" name="attachment.id" value="">
                <input type="hidden" id="attachmentId" name="attachment.attachmentPath" value="" >
                <input type="hidden" id="attachmentSize" name="attachment.attachmentSize" value="" >
                <input type="hidden" id="attachmentMaxSize" name="attachment.attachmentMaxSize" value="" >
                <input class="readonly" name="attachment.fileName" value="" readonly="readonly" type="text" size="40"/>
                    <%--                        <span id="imgSPAN" class="info" style="color:red;">*</span>--%>
                <a class="btnAttach" href="${ctx}/cabinet/cabinetBanner/jumpUploadBannerPic" lookupGroup="attachment" width="560" height="300" title="界面图片">界面图片</a>
                <span class="info">(选择)&nbsp;&nbsp;</span>
                <span class="unit">
		            <div class="buttonActive">
                        <div class="buttonContent">
                            <button id="showBannerPic" >显示图片
                            </button>
                        </div>
                    </div>
                </span>
            </div>
            <div id="picDivId" class="unit" style="display: none;">
                <label>&nbsp;</label>
                <img id="picId" alt="界面图片" onload="scaleImg(this);" src=""/>
            </div>
            <div class="divider"></div>
            <div class="unit">
                <label>是否启用</label>
                <select name="picStatus" id="picStatus" class="required">
                        <option value="0">启用</option>
                        <option value="1">停用</option>
                </select>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button id = "employeeButton" type="submit">提交</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form:form>
</div>
