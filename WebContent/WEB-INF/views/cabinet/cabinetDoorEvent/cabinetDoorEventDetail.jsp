<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--机柜编号表单 --%>
<script type="text/javascript">
	$('#cabinetId').select2();
	$('#employeeId').select2();
	$('#statusId').select2();

	function changeCabinet(id) {
		$.ajax({
			type : "post",
			url : "${ctx}/cabinet/cabinet/getEmployeeList?cabinetId=" + id,
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

	function showAllEMP(checkbox){
		var vau = document.getElementById("checkchooseAllEmployee").checked;
		var param;
		if(vau){
			param=0;
			$("#employeeId").removeClass("required");
		}else {
			param=1;
			$("#employeeId").addClass("required");
		}
		$.ajax({
			type : "post",
			url : "${ctx}/employee/employee/chooseAllEmpOrNot?check="+param,
			async : true,
			success : function(data){
				$("#allEmpOrNotId").val(data);
			},
			error : function(e) {
				alert("error");
				//请求失败时调用此函数
			}
		});
	}
</script>

<div class="pageContent">
	<form:form method="post" action="${ctx}/cabinet/sendEvent/saveOrUpdate"   modelAttribute="cabinetDoorEvent" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>机柜编号：</label>
				<select name="cabinetId" id="cabinetId" class="required" onchange="changeCabinet(this.value)">
					<option value=""  desc="">-- 请选择机柜--</option>
					<c:forEach items="${cabinetList}" var="cabinetObj">
						<option value="${cabinetObj.id}" <c:if test="${cabinetObj.id==cabinetDoor.cabinetId}">selected="selected"</c:if> > ${cabinetObj.cabinetPositionDetail} </option>
					</c:forEach>
				</select>
				<input type="text" value="1" id = "cabinetDoorNameId" name="cabinetDoorName" style="display: none">
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>使用人：</label>
				<select name="employeeId" id="employeeId" class="required">
					<option value=""  desc="">-- 请选择员工--</option>
				</select>
				<input type="checkbox" value="0" onchange="showAllEMP(checked)" id="checkchooseAllEmployee"/>选择全部
			</div>
			<div class="divider"></div>
			<input type="text" value="1" name="allEmployee" id="allEmpOrNotId" style="display: none">
			<div class="unit">
				<label>开柜时间：</label>
				<input type="datetime-local" name="doorOptTime" class="required"/>
			</div> 
			<div class="divider"></div>
			<div class="unit">
				<label>状态：</label>
				<select name="status" id="statusId" class="required">
					<option value=""  desc="">-- 请选择状态--</option>
					<option value="0">正常存</option>
					<option value="1">正常取</option>
					<option value="4">紧急</option>
				</select>
			</div>
			<div class="divider"></div>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form:form>
</div>


