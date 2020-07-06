<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">
	<form:form method="post" action="${ctx}/account/role/roleAuth" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="roleId" value="${roleId}" />
		<input type="hidden" name="menuIds" value="-1" />
		<div class="pageFormContent" layoutH="58">
			<div class="tabs">
				<div>
					<div layoutH="60" style="float:left; display:block; overflow:auto; width:99%; border:solid 1px #CCC; line-height:21px; background:#fff">
				    	<ul class="tree treeFolder treeCheck">
							<jsp:include page="recursive.jsp"/>
				    	</ul>
					</div>
				</div>
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

