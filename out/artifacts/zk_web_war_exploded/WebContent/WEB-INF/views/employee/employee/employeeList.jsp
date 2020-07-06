<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/employee/employee" method="post">
	<input type="hidden" name="pageNum" value="${employeePage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${employeePage.pageVO.pageSize}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					员工姓名：<input type="text" name="employeeName" value="${employeePage.employeeName}"/>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
			</tr>
		</table>
	</div>
	</form>
</div>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/employee/employee/toEditFormPage/-1" target="navTab" ><span>添加员工</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/employee/employee/delete/{sid_employee}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/employee/employee/toEditFormPage/{sid_employee}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="99%" layoutH="138" rel="employeeBox">
		<thead>
			<tr>
			    <th width="10%">序号</th>
				<th width="10%">员工工号</th>
				<th width="10%">姓名</th>
				<th width="10%">部门</th>
				<th width="15%">手机号码</th>
				<th width="10%">邮箱</th>
				<th width="15%">操作时间</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${employeePage.data}" varStatus="serial">
				<tr target="sid_employee" rel="${item.id}">
						<td >
								${(employeePage.pageVO.pageSize * (employeePage.pageVO.currentPage - 1))+ serial.index + 1 }</td>
						
						<td >${item.employeeNumber}</td>
						<td >${item.employeeName}</td>
						<td >${item.department.departmentName}</td>
						<td >${item.mobilePhone}</td>
						<td >${item.email}</td>
						<td><fmt:formatDate value="${item.optTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>			
						<td><div class="center" style="width: 116px;">&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/employee/employee/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;   
							<a title="修改" target="navTab" mask="true" href="${ctx}/employee/employee/toEditFormPage/${item.id}" class="btnEdit">修改</a>
						</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1}, 'employeeBox')">
				<option value="10" <c:if test="${employeePage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${employeePage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${employeePage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${employeePage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${employeePage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${employeePage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination"  targetType="navTab" totalCount="${employeePage.pageVO.totalRows}" numPerPage="${employeePage.pageVO.pageSize}" pageNumShown="10" currentPage="${employeePage.pageVO.currentPage}"></div>
	</div>
</div>