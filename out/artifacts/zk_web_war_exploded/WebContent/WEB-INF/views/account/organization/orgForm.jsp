<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--组织机构表单 --%>
<div class="pageContent">
	<form:form method="post" action="${ctx}/account/organization/saveOrUpdate" modelAttribute="org" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone)">
		<input type="hidden" name="parentId" value="${org.parentId}" />
		<input type="hidden" name="id" value="${org.id}" />
		<input type="hidden" name="nodeDepth" value="${org.nodeDepth}" />
		<input type="hidden" name="orgFlag" value="${org.orgFlag}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>机构名称：</label>
				<input type="text" name=orgName size="60" minlength="2" maxlength="20" class="required" value="${org.orgName}"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>上级机构名称：</label>
				<input type="text" size="60" value="${org.parentOrganization.orgName}" minlength="2" maxlength="32" readonly="readonly" />
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>机构代码：</label>
				<input type="text" name="orgCode" size="60" value="${org.orgCode}" minlength="2" maxlength="32" class="required"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>公司电话：</label>
				<input type="text" name="tel" size="60" value="${org.tel}"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>传真：</label>
				<input type="text" name="tax" size="60" value="${org.tax}"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>Email：</label>
				<input type="text" name="email" size="60" value="${org.email}"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>通讯地址：</label>
				<input type="text" name="address" size="60" value="${org.address}"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>邮政编码：</label>
				<input type="text" name="postalCode" size="60" value="${org.postalCode}"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>状态：</label>
				<input type="radio" name="status" value="1" checked="checked"/>启用
				<input type="radio" name="status" value="2" <c:if test="${2 == org.status}">checked="checked"</c:if>/>禁用
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

