<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--机柜管理表单 --%>
	
<div class="pageContent">
	<form:form method="post" action="${ctx}/cabinet/cabinet/saveOrUpdate"   modelAttribute="cabinet" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${cabinet.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>机柜编号：</label>
				<c:choose>
				   <c:when test="${cabinet.id == null}">
				       <input type="text" name="cabinetNumber" size="40" minlength="2" maxlength="20" class="required" value="${cabinet.cabinetNumber}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="cabinetNumber" size="40" minlength="2" maxlength="20" class="required" value="${cabinet.cabinetNumber}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
				
			
			<div class="unit">
				<label>机柜位置：</label>
				<select name="cabinetPosition" id="cabinetPosition" class="required">
					<option value="北京市" <c:if test="${cabinet.cabinetPosition}=='北京市'  ">selected="selected"</c:if> >北京市</option>
					<option value="上海市" <c:if test="${cabinet.cabinetPosition}=='上海市'  ">selected="selected"</c:if> >上海市</option>
					<option value="天津市" <c:if test="${cabinet.cabinetPosition}=='天津市'  ">selected="selected"</c:if> >天津市</option>
					<option value="河北省" <c:if test="${cabinet.cabinetPosition}=='河北省'  ">selected="selected"</c:if> >河北省</option>
				</select>
			</div> 
			<div class="divider"></div>
			
			
			<div class="unit">
				<label>机柜具体位置：</label>
				<input type="text" name="cabinetPositionDetail" size="60" class="required"  value="${cabinet.cabinetPositionDetail}"/>
			</div>
			<div class="divider"></div>
			
			<div class="unit">
				<label>机柜ip</label>
				<input type="text" name="cabinetIP" size="40" class="required" value="${cabinet.cabinetIP}"/>
			</div>
			<div class="divider"></div>
			
			<div class="unit">
				<label>软件版本：</label>				
				<input  type="text" name="software" size="40" class="required" value="${cabinet.software}">			
			</div>
			<div class="divider"></div>
			
			<div class="unit">
				<label>机柜门数：</label>
				<input type="text" name="doorCount" size="40" class="number required" value="${cabinet.doorCount}"/>
			</div>
			<div class="divider"></div>
			
			<div class="unit">
				<label>摄像头（左）：</label>
				<input type="text" name="leftCameraIP" size="40" value="${cabinet.leftCameraIP}"/>
			</div>
			<div class="divider"></div>
			
			<div class="unit">
				<label>摄像头（右）：</label>
				<input type="text" name="rightCameraIP" size="40" value="${cabinet.rightCameraIP}"/>
			</div>
			<div class="divider"></div>
			
			<div class="unit">
				<label>摄像头（前）：</label>
				<input type="text" name="frontCameraIP" size="40" value="${cabinet.frontCameraIP}"/>
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


