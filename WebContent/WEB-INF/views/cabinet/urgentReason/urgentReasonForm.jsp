<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--机柜管理表单 --%>
<script type="text/javascript">
	$('#urgentReasontype').select2();
</script>


<div class="pageContent">
	<form:form method="post" action="${ctx}/cabinet/urgentReason/saveOrUpdate"   modelAttribute="urgentReason" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${urgentReason.id}" />
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>异常原因：</label>
				<input type="text" name="reason" size="40" minlength="1" maxlength="60" class="required" value="${urgentReason.reason}" <c:if test="${not empty cabinetDoor.id}">readonly</c:if>/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>异常类型：</label>
				<select id="urgentReasontype" name="type" class="required">
					<option value=""  desc="">-- 请选择类型--</option>
					<option value="0">晚存原因</option>
					<option value="1">早取原因</option>
					<option value="2">早取提示</option>
				</select>
					<%--<input type="text" name="cabinetDoorNumber" size="40" minlength="1" maxlength="20" class="required" value="${cabinetDoor.cabinetDoorNumber}" <c:if test="${not empty cabinetDoor.id}">readonly</c:if>/>--%>
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


