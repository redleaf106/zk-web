<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<%--签名策略请求表单 --%>
<div class="pageContent">
	<form:form method="post" action="${ctx}/security/signPolicy/saveOrUpdate" enctype="multipart/form-data" modelAttribute="signPolicy" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="id" value="${signPolicy.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>签名策略名称：</label>
				<c:choose>
				   <c:when test="${signPolicy.id == null}">
				       <input type="text" name="policyName" size="40" minlength="2" maxlength="20" class="required"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="policyName" size="40" minlength="2" maxlength="20" value="${signPolicy.policyName}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>签名根证书：</label>
				<select name="certChainId" class="required combox">
					<option value="">-- 请选择 --</option>
					<c:forEach var="bean" items="${certChainList}">
						<option value="${bean.id}" <c:if test="${bean.id == signPolicy.certChainId}">selected="selected"</c:if>>${bean.certName}</option>
					</c:forEach>
				</select>				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>签名模式：</label>
				<select name="signPattern" class="required combox">
					<option value="">-- 请选择 --</option>
					<option value="1" <c:if test="${1 == signPolicy.signPattern}">selected="selected"</c:if>>本地签名</option>
				</select>				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>签名密钥：</label>
				<select name="signKeyId" class="required combox">
					<option value="">-- 请选择 --</option>
					<c:forEach var="bean" items="${signkeyList}">
						<option value="${bean.id}" <c:if test="${bean.id == signPolicy.signKeyId}">selected="selected"</c:if>>${bean.name}</option>
					</c:forEach>
				</select>				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>证书类型：</label>
				<select class="combox" name="certType">
					<option value="">-- 请选择 --</option>
					<option value="RSA" <c:if test="${'RSA' == signPolicy.certType}">selected="selected"</c:if>>RSA</option>
				</select>	
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>签名证书文件：</label>
				<input name="file" type="file" size="40" class="required"/>&nbsp;&nbsp;文件类型：*.cer
			</div>
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><p style="width:30px;"></p></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form:form>
	
</div>

