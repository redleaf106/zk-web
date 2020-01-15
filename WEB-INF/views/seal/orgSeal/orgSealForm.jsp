<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--机构印章表单 --%>
<script type="text/javascript">
	function selSealImg(sealId){
		$("#imgDivId").css("display","");
		$("#sealImgId").attr("src",sealId.value);
	}
</script>
<div class="pageContent">
	<form:form method="post" action="${ctx}/seal/orgSeal/saveOrUpdate" enctype="multipart/form-data" modelAttribute="orgSeal" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${orgSeal.id}" />
		<input type="hidden" name="orgId" value="${org.id}" />
		<input type="hidden" name="orgFlag" value="${org.orgFlag}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>机构印章名称：</label>
				<c:choose>
				   <c:when test="${orgSeal.id == null}">
				       <input type="text" name="sealName" size="40" minlength="2" maxlength="20" class="required" value="${orgSeal.sealName}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="sealName" size="40" minlength="2" maxlength="20" class="required" value="${orgSeal.sealName}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>所属机构：</label>
				<input type="text" name="" size="40" value="${org.orgName}" readonly="readonly"/>				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>印章图片：</label>
				<input name="file" type="file" size="40" class="required" onchange="selSealImg(this);" />&nbsp;&nbsp;
			</div>
			<%-- 印章图片预览 --%>
			<c:choose>
			   <c:when test="${orgSeal.id != null}">
			       <div id="imgDivId" class="unit" style="display: block;">
						<label></label>
						<img id="sealImgId" alt="印章图片" src="${ctx}/seal/orgSeal/showSeal?id=${orgSeal.id}&random=${random}">
				   </div>
			   </c:when>
			   <c:otherwise>
			   	   <div id="imgDivId" class="unit" style="display: none">
						<label></label>
						<img id="sealImgId" alt="印章图片" src="">
				   </div>
			   </c:otherwise>
			</c:choose>
			<div class="divider"></div>
			<div class="unit">
				<label>印章用途：</label>
				<input type="text" name="sealUsed" size="40" minlength="2" maxlength="20" class="required" value="${orgSeal.sealUsed}"/>				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>印章状态：</label>
				<input type="radio" name="status" value="1" checked="checked"/>启用
				<input type="radio" name="status" value="2" <c:if test="${2 == orgSeal.status}">checked="checked"</c:if>/>禁用
			</div>
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form:form>
	
</div>

