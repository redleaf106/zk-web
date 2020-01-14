<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<script type="text/javascript">

$("#showPicId").click(function(){
	 if(checkAttachmentSize()){
	       var id = $("#attachmentId", navTab.getCurrentPanel()).val();
	       if(id==""){
		       id='${employee.id}';
	       }
	       if (id != '') {
		       $("#picDivId", navTab.getCurrentPanel()).css("display", "");
		       $("#picId", navTab.getCurrentPanel()).attr('src', '${ctx}/employee/employee/showPic?id=' + id + "&" + Math.floor(Math.random() * 100));
	       } else {

		       alertMsg.error("请先上传图片!");
			   //alertMsg.error("123456!");
	       }
	  }
	   return false;//<%-- 解决firefox自动提交 --%>
	});


function checkAttachmentSize() {
	   var attachmentSize = $("#attachmentSize").val();
	   var attachmentMaxSize0 = $("#attachmentMaxSize", navTab.getCurrentPanel()).val().split("_")[0];
	   var attachmentMaxSize1 = $("#attachmentMaxSize", navTab.getCurrentPanel()).val().split("_")[1];
	   if(attachmentSize - attachmentMaxSize0 > 0){
		   alertMsg.error("图片应不大于" + Math.floor(attachmentMaxSize1) + "KB，请重新选择图片!");
		   return false;
	   }
		return true;
	}
	
	
<%-- 图片显示控制 --%>
function scaleImg(obj){
	if(obj.width>200){
		obj.width=200;
	}
	if(obj.height>200){
		obj.height=200;
	}
}
	
</script>

<%--员工表单 --%>
<div class="pageContent">
	<form:form method="post" action="${ctx}/employee/employee/saveOrUpdate" enctype="multipart/form-data"  modelAttribute="employee" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${employee.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>员工姓名：</label>
				 <input type="text" name="employeeName" size="40" minlength="2" maxlength="20" class="required" value="${employee.employeeName}"/>
			</div>
			<div class="divider"></div>
				
			<div class="unit">
				<label>员工工号：</label>
				<input type="text" name="icCardNumber" size="40" minlength="2" maxlength="32" class="required" value="${employee.icCardNumber}"/>
			</div>
			<div class="divider"></div>		
				
			<%--<div class="unit">--%>
				<%--<label>员工邮箱：</label>--%>
				<%--<input type="text" name="employeeNumber" style="display: none" size="40" minlength="2" maxlength="32" class="required" value="${employee.employeeNumber}"/>--%>
				<%--<input type="text" name="email" size="40" minlength="2" maxlength="32" class="required" value="${employee.email}"/>--%>
			<%--</div>--%>
			<div class="divider"></div>

		
			<div class="unit">
				<label>手机号码1：</label>
				<input type="text" name="mobilePhone" size="40"  class="required" value="${employee.mobilePhone}"/>
			</div>
			<div class="divider"></div>			
		
			
			<div class="unit">
				<label>手机号码2：</label>
				<input type="text" name="mobilePhone2" size="40" value="${employee.mobilePhone2}"/>
			</div>
			<div class="divider"></div>		
		
			<div class="unit">
				<label>邮箱：</label>				
				<input class="email" type="text" size="40"  name="email" value="${employee.email}">			
			</div>
			<div class="divider"></div>
		
			<div class="unit">
				<label>部门：</label>
				<select name="departmentId" id="departmentId" class="required">
					<c:forEach var="departmentObj" items="${departmentList}" >
						 <option value="${departmentObj.id}" <c:if test="${departmentObj.id==employee.departmentId}"> selected="selected"</c:if >> ${departmentObj.departmentName}</option>    
					</c:forEach>
				</select>
			</div> 
			<div class="divider"></div>
			 <!-- 
			 <div class="unit">
			      	<label>照片：</label>
					<input id = "file" name="file"  type="file" size="40" class="required" />
				</div>
			<div class="divider"></div>
			 -->
			
			 <div class="unit">
                        <label>员工图片：</label>
                        <input type="hidden" name="attachment.id" value="">
                        <input type="hidden" id="attachmentId" name="attachment.attachmentPath" value="" >
                        <input type="hidden" id="attachmentSize" name="attachment.attachmentSize" value="" >
                        <input type="hidden" id="attachmentMaxSize" name="attachment.attachmentMaxSize" value="" > 
                        <input class="readonly" name="attachment.fileName" value="" readonly="readonly" type="text" size="40"/>
<%--                        <span id="imgSPAN" class="info" style="color:red;">*</span>--%>
                        <a class="btnAttach" href="${ctx}/employee/employee/toAttachmentLookUpPage" lookupGroup="attachment" width="560" height="300" title="员工图片">员工图片</a>
                        <span class="info">(选择)&nbsp;&nbsp;</span>
                        <span class="unit">
					<div class="buttonActive"><div class="buttonContent"><button id="showPicId" >显示图片</button></div></div></span>
              </div>
			
			 <div id="picDivId" class="unit" style="display: none;">
						<label>&nbsp;</label>
						<img id="picId" alt="员工图片" onload="scaleImg(this);" src=""/>
			  </div>
			  
			
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button id = "employeeButton" type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form:form>
</div>

