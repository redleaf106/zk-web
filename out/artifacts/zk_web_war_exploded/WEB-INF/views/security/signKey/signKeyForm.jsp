<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--签名密钥表单 --%>
<div class="pageContent">
	<form:form method="post" action="${ctx}/security/signKey/saveOrUpdate" modelAttribute="signKey" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="id" value="${signKey.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>密钥名称：</label>
				<c:choose>
				   <c:when test="${signKey.id == null}">
				       <input type="text" name="name" size="40" minlength="2" maxlength="20" class="required" value="${signKey.name}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="name" size="40" minlength="2" maxlength="20" class="required" value="${signKey.name}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>密钥来源：</label>
				<select class="combox" name="keyType">
					<option value="1">软密钥</option>
				</select>				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>密钥算法：</label>
				<select class="combox" name="keyAlg">
					<option value="RSA">RSA</option>
				</select>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>密钥长度：</label>
				
				<select class="combox" name="keySize">
					<option value="1024" <c:if test="${signKey.keySize == 1024}">selected</c:if>>1024</option>
					<option value="2048" <c:if test="${signKey.keySize == 2048}">selected</c:if>>2048</option>
				</select>
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

