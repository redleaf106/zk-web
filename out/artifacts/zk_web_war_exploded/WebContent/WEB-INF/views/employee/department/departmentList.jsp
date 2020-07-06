<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/employee/department">
	<input type="hidden" name="pageNum" value="${departmentPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${departmentPage.pageVO.pageSize}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/employee/department" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					部门名称：<input type="text" name="departmentName" value="${departmentPage.departmentName}"/>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button id="departmentList_submit" type="submit">检索</button></div></div></td>
			</tr>
		</table>

	</div>
	</form>

	<form onsubmit="return validateCallback(this, navTabAjaxDone);" action="${ctx}/employee/department/updateDepartmentTime" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						修改时间：<input type="number" step="1" min="-30" max="30" name="timeIndex"/> 分钟
					</td>
					<td><div class="buttonActive"><div class="buttonContent"><button id="updateDepartmentTime" type="submit">修改</button></div></div></td>
				</tr>
			</table>

		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/employee/department/toEditFormPage/-1" target="navTab"><span>添加部门</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/employee/department/delete/{sid_department}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/employee/department/toEditFormPage/{sid_department}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	
	<div id="w_list_print">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="5%">序号</th>
					<th width="15%">部门编号</th>
					<th width="10%">部门名称</th>
					<th width="45%">工作时间段</th>
					<th width="15%">操作时间</th>
					<th width="10%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${departmentPage.data}" varStatus="serial">
					<tr target="sid_department" rel="${item.id}">
						<td>
							${(departmentPage.pageVO.pageSize * (departmentPage.pageVO.currentPage - 1))+ serial.index + 1 } 
						</td>
						<td>${item.departmentNumber}</td>
						<td>${item.departmentName}</td>
						<td>
							<c:forEach var="timeAreaItem" items="${item.timeAreas}" varStatus="serial">
								<fmt:formatDate value="${timeAreaItem.startTime}" type="both" pattern="HH:mm:ss"/>至<fmt:formatDate value="${timeAreaItem.endTime}" type="both" pattern="HH:mm:ss"/> &nbsp;&nbsp;
							</c:forEach>

						</td>
						<td><fmt:formatDate value="${item.optTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						
						<td>&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/employee/department/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;   
							<a title="修改部门信息" href="${ctx}/employee/department/toEditFormPage/${item.id}" class="btnEdit" target="navTab">修改</a>&nbsp;&nbsp;
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<%--page begin--%>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
				<option value="10" <c:if test="${departmentPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${departmentPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${departmentPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${departmentPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${departmentPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${departmentPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${departmentPage.pageVO.totalRows}" numPerPage="${departmentPage.pageVO.pageSize}" pageNumShown="10" currentPage="${departmentPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
