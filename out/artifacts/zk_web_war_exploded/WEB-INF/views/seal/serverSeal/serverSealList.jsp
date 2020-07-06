<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/seal/serverSeal">
	<input type="hidden" name="pageNum" value="${serverSealPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${serverSealPage.pageVO.pageSize}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/seal/serverSeal" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					服务器印章名称：<input type="text" name="sealName" value="${serverSealPage.sealName}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/seal/serverSeal/toEditFormPage" target="navTab"><span>添加服务器印章</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/seal/serverSeal/delete/{sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/seal/serverSeal/toEditFormPage?id={sid_user}" target="navTab"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="javascript:$.printBox('w_list_print')"><span>打印</span></a></li>
		</ul>
	</div>
	
	<div id="w_list_print">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="8%">序号</th>
					<th width="20%">服务器印章名称</th>
					<th width="20%">印章用途</th>
					<th width="10%">图片类型</th>
					<th width="15%">印章状态</th>
					<th width="15%">时间</th>
					<th width="12%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${serverSealPage.data}" varStatus="serial">
					<tr target="sid_user" rel="${item.id}">
						<td>
							${(serverSealPage.pageVO.pageSize * (serverSealPage.pageVO.currentPage - 1))+ serial.index + 1 } 、</td>
						<td>${item.sealName}</td>
						<td>${item.sealUsed}</td>
						<td>${item.sealType}</td>
						<td>
							<c:if test="${item.status==1}">启用</c:if>
							<c:if test="${item.status==2}">禁用</c:if></td>
						<td>${item.optTime}</td>
						<td>&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/seal/serverSeal/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;   
							<a title="修改印章信息" href="${ctx}/seal/serverSeal/toEditFormPage?id=${item.id}" class="btnEdit" target="navTab">修改</a>
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
				<option value="10" <c:if test="${serverSealPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${serverSealPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${serverSealPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${serverSealPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${serverSealPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${serverSealPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${serverSealPage.pageVO.totalRows}" numPerPage="${serverSealPage.pageVO.pageSize}" pageNumShown="10" currentPage="${serverSealPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
