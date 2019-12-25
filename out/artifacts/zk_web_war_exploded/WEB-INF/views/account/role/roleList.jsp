<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/account/role">
	<input type="hidden" name="pageNum" value="${rolePage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${rolePage.pageVO.pageSize}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/account/role" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					角色名称：<input type="text" name="roleName" value="${rolePage.roleName}"/>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button id="roleList_submit" type="submit">检索</button></div></div></td>
			</tr>
		</table>
		
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/account/role/toEditFormPage/-1" target="navTab"><span>添加角色</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/account/role/delete/{sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/account/role/toEditFormPage/{sid_user}" target="navTab"><span>修改</span></a></li>
			<!-- 
			<li class="line">line</li>
			<li><a class="icon" href="javascript:$.printBox('w_list_print')"><span>打印</span></a></li>
			 -->
		</ul>
	</div>
	
	<div id="w_list_print">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="20%">序号</th>
					<th width="20%">角色名称</th>
					<th width="20%">状态</th>
					<th width="20%">时间</th>
					<th width="20%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${rolePage.data}" varStatus="serial">
					<tr target="sid_user" rel="${item.id}">
						<td>
							${(rolePage.pageVO.pageSize * (rolePage.pageVO.currentPage - 1))+ serial.index + 1 } 
						</td>
						<td>${item.roleName}</td>
						<td>
							<c:if test="${item.status==1}">启用</c:if>
							<c:if test="${item.status==2}">禁用</c:if>
						</td>
						<td><fmt:formatDate value="${item.optTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/account/role/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;   
							<a title="修改角色信息" href="${ctx}/account/role/toEditFormPage/${item.id}" class="btnEdit" target="navTab">修改</a>&nbsp;&nbsp;
							<a title="角色授权" href="${ctx}/account/role/toSelMenuPage/${item.id}" class="btnAssign" target="dialog" mask="true">角色授权</a>
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
				<option value="10" <c:if test="${rolePage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${rolePage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${rolePage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${rolePage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${rolePage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${rolePage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${rolePage.pageVO.totalRows}" numPerPage="${rolePage.pageVO.pageSize}" pageNumShown="10" currentPage="${rolePage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
