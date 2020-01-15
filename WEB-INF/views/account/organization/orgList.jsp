<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" onsubmit="return divSearch(this, 'organizationBox');" action="${ctx}/account/organization/orgList/${orgId}" method="post">
	<input type="hidden" name="pageNum" value="${organizationPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${organizationPage.pageVO.pageSize}" />
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						组织机构名称：<input type="text" name="orgName" size="40" value="${organizationPage.orgName}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/account/organization/toEditFormPage/${parentId}/-1" target="navTab" ><span>添加组织机构</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/account/organization/toEditFormPage/{sid_user}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="99%" layoutH="190" rel="organizationBox">
		<thead>
			<tr>
				<th width="8%">序号</th>
				<th width="18%">组织机构名称</th>
				<th width="18%">机构代码</th>
				<th width="19%">上级机构名称</th>
				<th width="8%">状态</th>
				<th width="17%">时间</th>
				<th width="12%" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${organizationPage.data}" varStatus="serial">
				<tr target="sid_user" rel="${item.parentId}/${item.id}">
					<td>
						${(organizationPage.pageVO.pageSize * (organizationPage.pageVO.currentPage - 1))+ serial.index + 1 } 、</td>
					<td>${item.orgName}</td>
					<td>${item.orgCode}</td>
					<td>${item.parentOrganization.orgName}</td>
					<td>
						<c:if test="${item.status==1}">启用</c:if>
						<c:if test="${item.status==2}">禁用</c:if></td>
					<td>${item.optTime}</td>
					<td>&nbsp;
						<c:if test="${fn:length(item.subOrgList) <= 0}">
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/account/organization/delete/${item.parentId}/${item.id}" class="btnDel">删除</a>
						</c:if>
						<c:if test="${fn:length(item.subOrgList) > 0}">
							<a class="btnDel" href="javascript:;" onclick="alertMsg.warn('删除有误，请检查无下级机构再进行删除！')"><span>警告提示</span></a>
						</c:if>
						&nbsp;&nbsp;&nbsp;&nbsp;   
						<a title="修改印章信息" href="${ctx}/account/organization/toEditFormPage/${item.parentId}/${item.id}" class="btnEdit" target="navTab">修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%--page begin--%>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1}, 'organizationBox')">
				<option value="10" <c:if test="${organizationPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${organizationPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${organizationPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${organizationPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${organizationPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${organizationPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" rel="organizationBox" targetType="navTab" totalCount="${organizationPage.pageVO.totalRows}" numPerPage="${organizationPage.pageVO.pageSize}" pageNumShown="10" currentPage="${organizationPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>