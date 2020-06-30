<%--
  Created by IntelliJ IDEA.
  User: 红叶
  Date: 2020/6/2
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--机柜管理表单 --%>
<script type="text/javascript">
    $('#employeeId').select2();
    $('#cabinetId').select2();
</script>
<div class="pageContent">
    <form:form method="post" action="${ctx}/cabinet/cabinetDoor/saveOrUpdate"   modelAttribute="cabinet" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
        <input type="hidden" name="id" value="${cabinet.id}" />
        <div class="pageFormContent" layoutH="58">

            <div class="unit">
                <label>机柜编号：</label>
                <input type="text" name="cabinetNumber" size="40" minlength="1" maxlength="20" class="required" value="${cabinet.cabinetPositionDetail}" <c:if test="${not empty cabinet.id}">readonly</c:if>/>
            </div>
            <div class="divider"></div>


            <div class="unit">
                <label>柜门编号：</label>
                <select name="cabinetDoorNumber" id="cabinetDoorNumber" class="required">
                    <option value=""  desc="">-- 请选择柜门--</option>
                    <c:forEach items="${cabinetDoorList}" var="cabinetDoorObj">
                        <option value="${cabinetDoorObj.cabinetDoorNumber}"> ${cabinetDoorObj.cabinetDoorNumber} </option>
                    </c:forEach>
                </select>
            </div>
            <div class="divider"></div>

            <div class="unit">
                <label>使用人：</label>
                <select name="employeeId" id="employeeId" class="required">
                    <option value=""  desc="">-- 请选择员工--</option>
                    <c:forEach items="${employeeList}" var="employeeObj">
                        <option value="${employeeObj.id}" <c:if test="${employeeObj.id==cabinetDoor.employeeId}">selected="selected"</c:if> > ${employeeObj.employeeName} - ${employeeObj.employeeNumber}</option>
                    </c:forEach>
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






