<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--机柜管理表单 --%>
<script type="text/javascript">
	$('#employeeId').select2();
	$('#cabinetId').select2();
	$('#cabinetDoorId').select2();

	function changeCabinet(id) {
		$.ajax({
			type : "post",
			url : "${ctx}/cabinet/cabinet/getCabinetList?cabinetId=" + id,
			async : true,
			success : function(data){
				$("#cabinetDoorId").html(data);
			},
			error : function(e) {
				alert("error");
				//请求失败时调用此函数
			}
		});
	}

	function showAllEMP(checkbox){
		var vau = document.getElementById("checkShowAllEmployee").checked;
		var param;
		if(vau){
			param=0;
		}else {
			param=1;
		}
		$.ajax({
			type : "post",
			url : "${ctx}/employee/employee/selectAllEmployee?check="+param,
			async : true,
			success : function(data){
				$("#employeeId").html(data);
			},
			error : function(e) {
				alert("error");
				//请求失败时调用此函数
			}
		});
	}

</script>


<div class="pageContent">
	<form:form method="post" action="${ctx}/cabinet/cabinetDoor/saveOrUpdate"   modelAttribute="cabinetDoor" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)" >
		<input type="hidden" name="id" value="${cabinetDoor.id}" />
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>机柜编号：</label>
				<select name="cabinetId" id="cabinetId" class="required" onchange="changeCabinet(this.value)">
					<option value=""  desc="">-- 请选择机柜--</option>
					<c:forEach items="${cabinetList}" var="cabinetObj">
						<option value="${cabinetObj.id}" <c:if test="${cabinetObj.id==cabinetDoor.cabinetId}">selected="selected"</c:if> > ${cabinetObj.cabinetPositionDetail} </option>
					</c:forEach>
				</select>
				<input type="text" value="" id = "cabinetDoorNameId" name="cabinetDoorName" style="display: none">
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>柜门编号：</label>
				<select id="cabinetDoorId" name="cabinetDoorNumber" class="required">
					<option value=""  desc="">-- 请选择柜门--</option>
				</select>
					<%--<input type="text" name="cabinetDoorNumber" size="40" minlength="1" maxlength="20" class="required" value="${cabinetDoor.cabinetDoorNumber}" <c:if test="${not empty cabinetDoor.id}">readonly</c:if>/>--%>
			</div>
			<div class="divider"></div>
			<%--<div class="unit">--%>
				<%--<label>柜门名称：</label>--%>
					<%--<input type="text" name="cabinetDoorName" size="40" minlength="1" maxlength="20" class="required" value="${cabinetDoor.cabinetDoorName}" <c:if test="${not empty cabinetDoor.id}">readonly</c:if>/>--%>
			<%--</div>--%>
			<%--<div class="divider"></div>--%>
			<div class="unit">
				<label>使用人：</label>
				<select name="employeeId" id="employeeId" class="required">
					<option value=""  desc="">-- 请选择员工--</option>
					<c:forEach items="${employeeList}" var="employeeObj">
						<option value="${employeeObj.id}" <c:if test="${employeeObj.id==cabinetDoor.employeeId}">selected="selected"</c:if> > ${employeeObj.employeeName} - ${employeeObj.employeeNumber}</option>
					</c:forEach>
				</select>
				<input type="checkbox" value="0" onchange="showAllEMP(checked)" id="checkShowAllEmployee"/>显示全部
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


