<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--用户表单 --%>
<script type="text/javascript">
	
	<%-- 选择用户登录类型  1:密码登录 2:证书登录 3:密码和证书登录--%>
	function selLocationType(type){
		if(type == 1){
			$("#keywordId").css("display","");
			$("#userLoginName").addClass("required");
			$("#w_validation_pwd").addClass("required");
			$("#w_validation_pwd_affirm").addClass("required");
			$("#certRootId").val("");
			$("#certFile").attr("value","");
			$("#rectangId").css("display","none");
			$("#certRootId").toggleClass("required",false);
			$("#certFile").toggleClass("required",false);
		}
		if(type == 2){
			$("#userLoginName").attr("value","");
			$("#w_validation_pwd").attr("value","");
			$("#w_validation_pwd_affirm").attr("value","");
			$("#keywordId").css("display","none");
			$("#userLoginName").toggleClass("required",false);
			$("#w_validation_pwd").toggleClass("required",false);
			$("#w_validation_pwd_affirm").toggleClass("required",false);
			
			$("#rectangId").css("display","");
			$("#certRootId").addClass("required");
			$("#certFile").addClass("required");
		}
		if(type == 3){
			$("#keywordId").css("display","");
			$("#rectangId").css("display","");
			$("#userLoginName").addClass("required");
			$("#w_validation_pwd").addClass("required");
			$("#w_validation_pwd_affirm").addClass("required");
			$("#certRootId").addClass("required");
			$("#certFile").addClass("required");
		}
	}
</script>
<div class="pageContent">
	<form:form method="post" action="${ctx}/account/user/saveOrUpdate" enctype="multipart/form-data"  modelAttribute="user" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${user.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>用户名称：</label>
				<c:choose>
				   <c:when test="${user.id == null}">
				       <input type="text" name="userName" size="40" minlength="2" maxlength="20" class="required" value="${user.userName}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="userName" size="40" minlength="2" maxlength="20" class="required" value="${user.userName}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
				
			
			<div class="unit">
				<label>角色：</label>
				<select name="roleId" id="roleId" class="required">
					<c:forEach var="roleObj" items="${roleList}" >
						<c:choose>
	   						<c:when test="${roleObj.id==user.roleId}"> 
						    	<option value="${roleObj.id}" selected="selected" >${roleObj.roleName}</option>    
						   </c:when>
						   <c:otherwise>
						 		<option value="${roleObj.id}" >${roleObj.roleName}</option>
						   </c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div> 
			
			<div class="divider"></div>
			<%-- 基于用户登录 --%>
			<div id="keywordId" >
				<div class="unit">
				<label>登录名称：</label>
				<c:choose>
				   <c:when test="${user.userName == null}">
				       <input id="userLoginName" type="text" name="loginName" size="40" minlength="2" maxlength="20" class="required" value="${user.loginName}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input id="userLoginName" type="text" name="loginName" size="40" value="${user.loginName}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
			
			<c:choose>
			   <c:when test="${user.pwd == null}">
			   <div class="unit">
			       <label>密码：</label>
				<input id="w_validation_pwd" class="required alphanumeric textInput" size="40" type="password" alt="字母、数字、下划线 6-20位" maxlength="20" minlength="6" name="pwd">
				</div>
				<div class="divider"></div>
			   </c:when>
			   <c:otherwise>
			   </c:otherwise>
			</c:choose>			
			
			<c:choose>
			   <c:when test="${user.pwd == null}">
			   <div class="unit">
			      	<label>确认密码：</label>				
			   		<input id="w_validation_pwd_affirm" class="required textInput" type="password" size="40" equalto="#w_validation_pwd" name="pwd_affirm">
				</div>
				<div class="divider"></div>
		   		</c:when>
		   		<c:otherwise>
	   			</c:otherwise>
			</c:choose>				
			</div>
			
			<%-- 基于证书登陆 --%>
			<div id="rectangId">
				<div class="unit">
				<label>签名根证书：</label>
				<select name="certChainId" id="certRootId" class="required">
					<option value="">-- 请选择 --</option>
					<c:forEach var="bean" items="${certChainList}">
						<option value="${bean.id}" <c:if test="${bean.id == user.certChainId}">selected="selected"</c:if>>${bean.certName}</option>
					</c:forEach>
				</select>	
			</div>
			<div class="divider"></div>
			   <div class="unit">
			      	<label>证书文件：</label>
					<input id = "certFile" name="file"  type="file" size="40" />&nbsp;&nbsp;文件类型：*.cer
				</div>
				<div class="divider"></div>
			</div>
			
			<div class="unit">
				<label>证件类型：</label>
				<select name="creditType"  class="">
					      <option value="">-- 请选择 --</option>
					      <option value="身份证" <c:if test="${user.creditType == '身份证'}">selected="selected"</c:if>>&nbsp;&nbsp;身份证</option>
				</select>
			</div>
			<div class="divider"></div>
			
			<div class="unit">
				<label>证件号码：</label>
				<input type="text" name="creditCode" size="60" value="${user.creditCode}"/>
			</div>
			<div class="divider"></div>
			
			<div class="unit">
				<label>电子邮件：</label>				
				<input class="email" type="text" size="60"  name="email" value="${user.email}">			
			</div>
			<div class="divider"></div>
			
			<div class="unit">
				<label>移动电话：</label>
				<input type="text" name="mobilePhone" size="60" value="${user.mobilePhone}"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>固定电话：</label>
				<input type="text" name="telephone" size="60" value="${user.telephone}"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>状态：</label>
				<input type="radio" name="status" value="1" checked="checked"/>启用
				<input type="radio" name="status" value="2" <c:if test="${user.status == 2}">checked="checked"</c:if> />停用
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

<script type="text/javascript">
	   		selLocationType(1);
</script>

