<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript">
	function validateForm(form) {
		var $form = $(form);
		if (!$form.valid()) {
			return false;
		}
	}
</script>
<%--产生证书请求表单 --%>
<div class="pageContent">
	<form:form method="post" action="${ctx}/security/signPolicy/genCertReq" modelAttribute="certReqBean" class="pageForm required-validate" onsubmit="return validateForm(this);">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>签名密钥：</label>
				<select name="signKeyId" class="required combox">
					<option value="">-- 请选择 --</option>
					<c:forEach var="bean" items="${signkeyList}">
						<option value="${bean.id}">${bean.name}</option>
					</c:forEach>
				</select>				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>通用名：</label>
				<input type="text" name="commName" size="40" minlength="2" maxlength="20" class="required"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>国家：</label>
				<input type="text" name="country" size="40" minlength="2" maxlength="10" value="CN" class="required"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>省：</label>
				<input type="text" name="province" size="40" minlength="2" maxlength="10"/>&nbsp;&nbsp;<font color="red">例如：BJ</font>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>地市：</label>
				<input type="text" name="city" size="40" minlength="2" maxlength="10"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>单位名称：</label>
				<input type="text" name="unit" size="40" minlength="2" maxlength="10"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>部门名称：</label>
				<input type="text" name="depName" size="40" minlength="2" maxlength="10"/>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>电子邮件：</label>
				<input type="text" name="email" size="40" minlength="2" maxlength="100" class="email"/>
			</div>
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">产生</button></div></div></li>
				<li><p style="width:30px;"></p></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form:form>
	
</div>

