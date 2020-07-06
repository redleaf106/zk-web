<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--服务器印章表单 --%>
<script type="text/javascript">
	function selSealImg(sealId){
		$("#imgDivId").css("display","");
		$("#sealImgId").attr("src",sealId.value);
	}
</script>
<div class="pageContent">
	<form:form method="post" action="${ctx}/seal/serverSeal/saveOrUpdate" enctype="multipart/form-data" modelAttribute="serverSeal" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${serverSeal.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>服务器印章名称：</label>
				<c:choose>
				   <c:when test="${serverSeal.id == null}">
				       <input type="text" name="sealName" size="40" minlength="2" maxlength="20" class="required" value="${serverSeal.sealName}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="sealName" size="40" minlength="2" maxlength="20" class="required" value="${serverSeal.sealName}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>印章图片：</label>
				<input name="file" type="file" size="40" class="required" onchange="selSealImg(this);" />&nbsp;&nbsp;
			</div>
			<%-- 印章图片预览 --%>
			<c:choose>
			   <c:when test="${serverSeal.id != null}">
			       <div id="imgDivId" class="unit" style="display: block;">
						<label></label>
						<img id="sealImgId" alt="印章图片" src="${ctx}/seal/serverSeal/showSeal?id=${serverSeal.id}&random=${random}">
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
			<p>
				<label>印章宽度：</label>
				<input type="text" name="sealWidth" size="10" class="required number" value="${serverSeal.sealWidth}"/>
				<span class="unit"><font color="red">注：0.0为原始图片宽度。</font></span>
			</p>
			<p>
				<label>印章高度：</label>
				<input type="text" name="sealHeight" size="10" class="required number" value="${serverSeal.sealHeight}"/>
				<span class="unit"><font color="red">注：0.0为原始图片高度。</font></span>
			</p>
			<div class="divider"></div>
			<div class="unit">
				<label>印章用途：</label>
				<input type="text" name="sealUsed" size="40" minlength="2" maxlength="20" class="required" value="${serverSeal.sealUsed}"/>				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>使用范围：</label>
				<select name="usedRule" class="required combox">
					<option value="">-- 请选择 --</option>
					<option value="1" <c:if test="${1 == serverSeal.usedRule}">selected="selected"</c:if>>所有机构</option>
					<option value="2" <c:if test="${2 == serverSeal.usedRule}">selected="selected"</c:if>>下级机构</option>
					<option value="3" <c:if test="${3 == serverSeal.usedRule}">selected="selected"</c:if>>当前机构</option>
				</select>	
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>印章状态：</label>
				<input type="radio" name="status" value="1" checked="checked"/>启用
				<input type="radio" name="status" value="2" <c:if test="${2 == serverSeal.status}">checked="checked"</c:if>/>禁用
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

