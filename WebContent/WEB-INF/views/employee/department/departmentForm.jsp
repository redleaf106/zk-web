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
			
			 <!--  
			<div class="unit">
				<label>工作时间：</label>
				 <input id="startTime" type="text" class="Wdate" name="startTime" size="30"
                               value= "<fmt:formatDate value='${startTime}' type='both' pattern='HH:mm:ss'/> "
                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,
                       minDate: '%y-%M-%d',maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
                       
                  <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
                  <input id="endTime" type="text" class="Wdate" name="endTime" size="30"
                               value= "<fmt:formatDate value='${endTime}' type='both' pattern='HH:mm:ss'/> "
                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
                  <div class="pageContent sortDrag" style="border: 0px dotted #B8D0D6" id="sortbox">
                               
                  </div>     
			</div>
			 -->
			
			 <div class="unit">
				 <fieldset>
	               <legend>工作时间</legend>    
	               <div  id="sortbox" class="pageContent " style="border: 0px dotted #B8D0D6">   
	 					 <c:choose>
			               	<c:when test="${empty department.id}">
			               	<div style='border: 0px solid #B8D0D6; margin: 0; padding: 5px 0;' class='unit'>
			               		<label>时间段：</label>   	
		              			 <input id="startTime" type="text" class="Wdate" name="startTime" 
	                               value= ""
	                               onClick="WdatePicker({dateFmt:'HH:mm:ss',readOnly:true})"/>
				                  <input id="endTime" type="text" class="Wdate" name="endTime" 
		                              value= "" 
		                               onClick="WdatePicker({dateFmt:'HH:mm:ss',readOnly:true})"/>
		                    	  <input  type="button" value="添加时间段" onclick="addTimeAreasDiv()"/>
						          <input class="input_param" type="button" value="删除时间段"/>
		                    </div>
			               	</c:when>
			                <c:otherwise>
			                	<c:forEach var="item" items="${department.timeAreas}" varStatus="serial">
				                		<div style='border: 0px solid #B8D0D6; margin: 0; padding: 5px 0;' class='unit'>
							                <label>时间段：</label>   	
						                	<input id="startTime" type="text" class="Wdate" name="startTime"
					                               value= "<fmt:formatDate value='${item.startTime}' type='both' pattern='HH:mm:ss'/> "
					                               onClick="WdatePicker({dateFmt:'HH:mm:ss',readOnly:true})"/>
					                  		<input id="endTime" type="text" class="Wdate" name="endTime"
					                              value= "<fmt:formatDate value='${item.endTime}' type='both' pattern='HH:mm:ss'/>"
					                               onClick="WdatePicker({dateFmt:'HH:mm:ss',readOnly:true})"/>
						                    	<input  type="button" value="添加时间段" onclick="addTimeAreasDiv()"/>
						                    	<input class="input_param" type="button" value="删除时间段"/>
					                  </div>
				                </c:forEach>
			                </c:otherwise>
	               		</c:choose>              
	               </div>         
	                <div class="pageContent " style="border: 0px dotted #B8D0D6">   
		            </div>
	             </fieldset>
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

<script>

function addTimeAreasDiv(){
    //父容器 sortbox
    var parentDiv = $("#sortbox", navTab.getCurrentPanel());
        
        var newItem= $(
        		"<div style='border: 0px solid #B8D0D6; margin: 0; padding: 5px 0;' class='unit'>"+
            	"<label>时间段：</label>   	"+
           		"<input id='startTime' type='text' class='Wdate' name='startTime'"+
                "       value='' "+
                 "      onClick='WdatePicker({dateFmt:\"HH:mm:ss\",readOnly:true})'/>"+
                 "<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>   	"+
          		"<input id='endTime' type='text' class='Wdate' name='endTime'"+
                "       value= '' "+
                "       onClick='WdatePicker({dateFmt:\"HH:mm:ss\",readOnly:true})'/> "+
            	"<input  type='button' value='添加时间段' onclick='addTimeAreasDiv()'/> "+
                "	<input class='input_param' type='button' value='删除时间段' onclick='delTimeAreasDiv($(this)); '/> "+
            	"</div>"
            	);
    // 在父元素中追加div
    newItem.appendTo(parentDiv);
}

function delTimeAreasDiv(data){
	data.parent().remove(); 
}

$('.input_param').bind('click',function(){
       $(this).parent().remove(); 
})
</script>

