<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<script type="text/javascript">

	$("#employeeId").select2();
</script>

<%--员工表单 --%>
<div class="pageContent">
	<form:form method="post" action="${ctx}/audit/inprocessAudit/saveOrUpdate" enctype="multipart/form-data"  modelAttribute="inprocessAudit" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${inprocessAudit.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>标题：</label>
				 <input type="text" name="title" size="40" minlength="2" maxlength="20" class="required" value="${inprocessAudit.title}"/>
			</div>
			<div class="divider"></div>
			
	  	    <div class="unit">
				<label>员工：</label>
				<select name="employeeId" id="employeeId" class="required">
				    	<option value=""  desc="">-- 请选择员工--</option>
					<c:forEach var="employeeObj" items="${employeeList}" >
						 <option value="${employeeObj.id}" <c:if test="${employeeObj.id==inprocessAudit.employeeId}"> selected="selected"</c:if >> ${employeeObj.employeeName}-${employeeObj.icCardNumber}</option>    
					</c:forEach>
				</select>
			</div> 
			<div class="divider"></div>
			
			<div class="unit">
                        <label>巡检时间：</label>
                        <input id="inprocessTime" type="text" class="required Wdate" name="inprocessTime" size="30"
                               value= "<fmt:formatDate value='${inprocessAudit.inprocessTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/> "
                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
            </div>
			
			
			<div class="unit">
				<label>内容：</label>
				<textarea name="content" cols="44" rows="3"  maxlength="60">${inprocessAudit.content}</textarea>
			</div>
			<div class="divider"></div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button id = "inprocessAuditButton" type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form:form>
</div>

