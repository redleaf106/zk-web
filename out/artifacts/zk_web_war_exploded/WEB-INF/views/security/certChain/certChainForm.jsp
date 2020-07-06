<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--证书链表单 --%>
<div class="pageContent">
	<form:form method="post" action="${ctx}/security/certChain/saveOrUpdate" enctype="multipart/form-data" modelAttribute="certChain" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone)">
		<input type="hidden" name="id" value="${certChain.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>根证书名称：</label>
				<c:choose>
				   <c:when test="${certChain.id == null}">
				       <input type="text" name="certName" size="40" minlength="4" maxlength="20" class="required" value="${certChain.certName}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="certName" size="40" minlength="4" maxlength="20" class="required" value="${certChain.certName}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>证书类型：</label>
				<select class="combox" name="certType">
					<option value="RSA">RSA</option>
				</select>				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>校验类型：</label>
				<select class="combox" name="checkType">
					<option value="1">不校验</option>
				</select>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>上级根证书：</label>
				<select class="combox" name="parentId">
					<option value="-1">无</option>
					<c:forEach var="bean" items="${certChainList}">
						<option value="${bean.id}" <c:if test="${bean.id == certChain.parentId}">selected="selected"</c:if>>${bean.certName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>根证书文件：</label>
				<input name="file" type="file" size="40" class="required"/>&nbsp;&nbsp;文件类型：*.cer
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

