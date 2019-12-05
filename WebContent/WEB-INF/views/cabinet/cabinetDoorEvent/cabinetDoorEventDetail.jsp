<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--机柜编号表单 --%>

<div class="pageContent">
	<form:form method="post" action=""   modelAttribute="cabinetDoorEvent" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${cabinetDoor.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>机柜编号：</label>
				<input type="text" name="cabinetNumber" size="40" minlength="1" maxlength="20" class="required" value="${cabinetDoorEvent.cabinetNumber}" readonly/>
			
			</div>
			<div class="divider"></div>
		
		
			<div class="unit">
				<label>柜门编号：</label>
				<input type="text" name="cabinetDoorNumber" size="40" minlength="1" maxlength="20" class="required" value="${cabinetDoorEvent.cabinetDoorNumber}" readonly/>
			</div>
			<div class="divider"></div>
				
			<div class="unit">
				<label>员工IC卡号：</label>
				<input type="text" name="employeeCardNumber" size="40" minlength="1" maxlength="20" class="required" value="${cabinetDoorEvent.employeeCardNumber}" readonly/>
			</div> 
			<div class="divider"></div>
			
				
			<div class="unit">
				<label>操作时间：</label>
				<input type="text" name="doorOptTime" size="40" minlength="1" maxlength="20" class="required" value="<fmt:formatDate value='${cabinetDoorEvent.doorOptTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly/>
			</div> 
			<div class="divider"></div>
			
			
				
			<div class="unit">
				<label>状态：</label>
				<label>
					<c:choose>
								<c:when test="${cabinetDoorEvent.status=='0'}">正常开门</c:when>
								<c:when test="${cabinetDoorEvent.status=='1'}">正常关门</c:when>
								<c:when test="${cabinetDoorEvent.status=='2'}">晚存件</c:when>
								<c:when test="${cabinetDoorEvent.status=='3'}">紧急取</c:when>
								<c:when test="${cabinetDoorEvent.status=='4'}">未存</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
			    </label>
			</div> 
			<div class="divider"></div>
			
			
				
			<div class="unit">
				<label>备注：</label>
				<textarea name="remark" cols="44" rows="3"  maxlength="60" readonly>${cabinetDoorEvent.remark}</textarea>
			</div> 
			<div class="divider"></div>
			
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form:form>
</div>


