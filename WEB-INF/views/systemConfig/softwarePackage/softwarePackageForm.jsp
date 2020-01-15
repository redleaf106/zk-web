<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<script type="text/javascript">


</script>

<%--软件版本表单 --%>
<div class="pageContent">
	<form:form method="post" action="${ctx}/systemConfig/softwarePackage/saveOrUpdate" enctype="multipart/form-data"  modelAttribute="softwarePackage" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone)">
		<!-- return iframeCallback(this, navTabAjaxDone) --> <!-- return validateCallback(this, navTabAjaxDone) -->
		<input type="hidden" name="id" value="${softwarePackage.id}" />
		<div class="pageFormContent" layoutH="58">
			
			<div class="unit">
				<label>版本名称：</label>
				 <input type="text" name="name" size="40" minlength="2" maxlength="20" class="required" value="${softwarePackage.name}"/>
			</div>
			<div class="divider"></div>
				
			<div class="unit">
				<label>版本号：</label>
				<input type="text" name="version" size="40" minlength="2" maxlength="32" class="required" value="${softwarePackage.version}"/>
			</div>
			<div class="divider"></div>		
  	
			<div class="unit">
				<label>更新状态：</label>
				  <c:if test="${empty softwarePackage.id}">
				  	<input type="radio" name="updateStatus" value="1" checked="checked">选择更新</input>
                  	<input type="radio" name="updateStatus" value="2">强制更新</input>
                  </c:if>
                   <c:if test="${not empty softwarePackage.id}">
                   	<input type="radio" name="updateStatus" value="1"  <c:if test="${softwarePackage.updateStatus=='1'}">checked="checked"</c:if>>选择更新</input>
                  	<input type="radio" name="updateStatus" value="2"   <c:if test="${softwarePackage.updateStatus=='2'}">checked="checked"</c:if>>强制更新</input>
                   </c:if>
			</div>
			<div class="divider"></div>		
		 
		
			 <div class="unit">
			      	<label>软件包：</label>
					<input id = "file" name="file"  type="file" size="40" class="required" />
				</div>
			<div class="divider"></div>
		 
			<div class="unit">
				<label>描述：</label>
				<textarea name="content" cols="44" rows="3"  maxlength="60">${softwarePackage.content}</textarea>
			</div>
			<div class="divider"></div>			
		
			
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button id = "softwarePackageButton" type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form:form>
</div>

