<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--角色管理表单 --%>
<div class="pageContent">
	<form:form method="post" action="${ctx}/account/role/saveOrUpdate"  modelAttribute="role" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${role.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>角色名称：</label>
				<c:choose>
				   <c:when test="${role.id == null}">
				       <input type="text" name="roleName" size="40" minlength="2" maxlength="20" class="required" value="${role.roleName}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="roleName" size="40" value="${role.roleName}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<!-- 
			<div class="divider"></div>
			<div class="unit">
				<label>使用范围：</label>
				<select name="usedRule" class="required combox">
					<option value="">-- 请选择 --</option>
					<option value="1" <c:if test="${1 == role.usedRule}">selected="selected"</c:if>>所有机构</option>
					<option value="2" <c:if test="${2 == role.usedRule}">selected="selected"</c:if>>下级机构</option>
					<option value="3" <c:if test="${3 == role.usedRule}">selected="selected"</c:if>>当前机构</option>
				</select>	
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>状态：</label>
				<input type="radio" name="status" value="1" checked="checked"/>启用
				<input type="radio" name="status" value="2" <c:if test="${2 == role.status}">checked="checked"</c:if>/>禁用
			</div>
			 -->
			<div class="divider"></div>
			<div class="unit">
				<label>描述：</label>
				<textarea name="description" cols="44" rows="3"  maxlength="60">${role.description}</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form:form>
	
</div>

