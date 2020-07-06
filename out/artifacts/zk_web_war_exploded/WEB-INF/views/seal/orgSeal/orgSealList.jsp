<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" onsubmit="return divSearch(this, 'orgSealBox');" action="${ctx}/seal/orgSeal/${orgId}" method="post">
	<input type="hidden" name="pageNum" value="${orgSealPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${orgSealPage.pageVO.pageSize}" />
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						机构印章名称：<input type="text" name="sealName" value="${orgSealPage.sealName}"/>
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
			<li><a class="add" href="${ctx}/seal/orgSeal/toEditFormPage/${orgId}/-1" target="navTab" ><span>添加机构印章</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/seal/orgSeal/delete/{sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/seal/orgSeal/toEditFormPage/{sid_user}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="99%" layoutH="190" rel="orgSealBox">
		<thead>
			<tr>
				<th width="8%">序号</th>
				<th width="18%">机构印章名称</th>
				<th width="18%">印章用途</th>
				<th width="19%">所属机构</th>
				<th width="8%">印章状态</th>
				<th width="17%">时间</th>
				<th width="12%" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${orgSealPage.data}" varStatus="serial">
				<tr target="sid_user" rel="${item.organization.id}/${item.id}">
					<td>
						${(orgSealPage.pageVO.pageSize * (orgSealPage.pageVO.currentPage - 1))+ serial.index + 1 } 、</td>
					<td>${item.sealName}</td>
					<td>${item.sealUsed}</td>
					<td title="${item.organization.orgName}">${item.organization.orgName}</td>
					<td>
						<c:if test="${item.status==1}">启用</c:if>
						<c:if test="${item.status==2}">禁用</c:if></td>
					<td>${item.optTime}</td>
					<td>&nbsp;
						<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/seal/orgSeal/delete/${item.organization.id}/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;   
						<a title="修改印章信息" href="${ctx}/seal/orgSeal/toEditFormPage/${item.organization.id}/${item.id}" class="btnEdit" target="navTab">修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%--page begin--%>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1}, 'orgSealBox')">
				<option value="10" <c:if test="${orgSealPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${orgSealPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${orgSealPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${orgSealPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${orgSealPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${orgSealPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" rel="orgSealBox" targetType="navTab" totalCount="${orgSealPage.pageVO.totalRows}" numPerPage="${orgSealPage.pageVO.pageSize}" pageNumShown="10" currentPage="${orgSealPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>