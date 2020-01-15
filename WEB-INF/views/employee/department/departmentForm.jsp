<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--机柜管理表单 --%>
	
<div class="pageContent">
	<form:form method="post" action="${ctx}/employee/department/saveOrUpdate"   modelAttribute="department" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${department.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>部门编号：</label>
				<c:choose>
				   <c:when test="${department.id == null}">
				       <input type="text" name=departmentNumber size="40" minlength="2" maxlength="32" class="required" value="${department.departmentNumber}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="departmentNumber" size="40" minlength="2" maxlength="32" class="required" value="${department.departmentNumber}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
				
			
			<div class="unit">
				<label>部门名称：</label>
				<input type="text" name=departmentName size="40" minlength="2" maxlength="20" class="required" value="${department.departmentName}"/>
			</div> 
			<div class="divider"></div>
			
			 
			<div class="unit">
				<label>工作时间：</label>
				 <input id="startTime" type="text" class="Wdate" name="startTime" size="30"
                               value= "<fmt:formatDate value='${startTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/> "
                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,
                       minDate: '%y-%M-%d',maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
                       
                  <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
                  <input id="endTime" type="text" class="Wdate" name="endTime" size="30"
                               value= "<fmt:formatDate value='${endTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/> "
                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
                  <div class="pageContent sortDrag" style="border: 0px dotted #B8D0D6" id="sortbox">
                               
                  </div>     
			</div>
			 
			 <!-- 
			 <div class="unit">
				 <fieldset>
	               <legend>工作时间</legend>    
	               <div>   
	               		<label>开始时间</label>   	
		               <input id="startTime" type="text" class="Wdate" name="startTime" size="30"
	                               value= "<fmt:formatDate value='${startTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/> "
	                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,
	                       minDate: '%y-%M-%d',maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
	                       
	                  <label>&nbsp;&nbsp;&nbsp;&nbsp;结束时间</label>
	                  <input id="endTime" type="text" class="Wdate" name="endTime" size="30"
	                               value= "<fmt:formatDate value='${endTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/> "
	                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
		                        <font class="info">&nbsp;&nbsp;&nbsp;<a href="#" onclick="addSignerCertDiv()"><b>增加工作时间</b></a></font>
	               </div>         
	                      
	             </fieldset>
             </div>
			<div class="divider"></div>
			 -->
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form:form>
</div>

<script>

function addSignerCertDiv(){
    var signerCertDivCount = $("#sortbox", navTab.getCurrentPanel()).children('DIV').length;
    if (signerCertDivCount == 5) {
        alertMsg.warn("可添加签名证书最大数量：5");
        return ;
    }
    //父容器 sortbox
    var parentDiv = $("#sortbox", navTab.getCurrentPanel());
    // 新div元素
    var newItem = $("<div style='border: 0px solid #B8D0D6; margin: 0; padding: 5px 0;' class='innerDIV'><table>" +
        "<tr><td><label>证书文件：</label></td><td><input type='file' name='signerCert' size='40'></td></tr></table></div>");
    // 在父元素中追加div
    newItem.appendTo(parentDiv);
    // 新div适配sortDrag
    //$("div.sortDrag", document).loadItem(newItem);
}

// 删除signerCertDiv
function delSignerCertDiv(data){
    $(data).closest('.innerDIV').remove(); // 删除div
}
</script>

