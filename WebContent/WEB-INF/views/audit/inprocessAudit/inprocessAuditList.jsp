<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/audit/inprocessAudit" method="post">
	<input type="hidden" name="pageNum" value="${inprocessAuditPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${inprocessAuditPage.pageVO.pageSize}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					员工姓名：<input type="text" name="employeeName" value="${inprocessAuditPage.employeeName}"/>
				</td>
				<td>
					IC卡号：<input type="text" name="icCardNumber" value="${inprocessAuditPage.icCardNumber}"/>
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
			<li><a class="add" href="${ctx}/audit/inprocessAudit/toEditFormPage/-1" target="navTab" ><span>添加巡检记录</span></a></li>
			<li class="line">line</li>
			<%-- <li><a class="delete" href="${ctx}/audit/inprocessAudit/delete/{sid_inprocessAudit}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li> --%>
			<li><a class="edit" href="${ctx}/audit/inprocessAudit/toEditFormPage/{sid_inprocessAudit}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="99%" layoutH="138" rel="userBox">
		<thead>
			<tr>
			    <th width="10%">序号</th>
				<th width="20%">标题</th>
				<th width="10%">巡检人姓名</th>
				<th width="10%">员工姓名</th>
				<th width="15%">巡检时间</th>
				<th width="10%">操作时间</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${inprocessAuditPage.data}" varStatus="serial">
				<tr target="sid_inprocessAudit" rel="${item.id}">
						<td >
							${(inprocessAuditPage.pageVO.pageSize * (inprocessAuditPage.pageVO.currentPage - 1))+ serial.index + 1 }</td>
						
						<td >${item.title}</td>
						<td >${item.user.userName}</td>
						<td >${item.employee.employeeName}</td>
						<td ><fmt:formatDate value="${item.inprocessTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${item.optTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>			
						<td><div class="center" style="width: 116px;">&nbsp;
						<!-- <a title="详情" target="navTab" mask="true" href="${ctx}/audit/inprocessAudit/detail/${item.id}" class="btnEdit">详情</a>&nbsp;&nbsp;&nbsp;&nbsp; -->	   
							<a title="修改" target="navTab" mask="true" href="${ctx}/audit/inprocessAudit/toEditFormPage/${item.id}" class="btnEdit">修改</a>
							
						</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1}, 'userBox')">
				<option value="10" <c:if test="${inprocessAuditPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${inprocessAuditPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${inprocessAuditPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${inprocessAuditPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${inprocessAuditPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${inprocessAuditPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination"  targetType="navTab" totalCount="${inprocessAuditPage.pageVO.totalRows}" numPerPage="${inprocessAuditPage.pageVO.pageSize}" pageNumShown="10" currentPage="${inprocessAuditPage.pageVO.currentPage}"></div>
	</div>
</div>