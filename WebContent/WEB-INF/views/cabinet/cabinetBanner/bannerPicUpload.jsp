<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<h2 class="contentTitle">请选择需要上传的界面图片</h2>
<form action="${ctx}/cabinet/cabinetBanner/uploadBannerPic" method="post" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this, $.bringBack)">
<div class="pageContent">
	<div class="pageFormContent" layoutH="97">
		<dl>
			<dt>界面图片：</dt><dd><input id="attachmentLookup_file" type="file" name="file" class="required " size="40" /></dd>
		</dl>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button id="attachmentLookup_submit" type="submit">上传</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button id="attachmentLookup_close" class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>
</form>

