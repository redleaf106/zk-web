<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/security/certChain">
	<input type="hidden" name="pageNum" value="${certChainPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${certChainPage.pageVO.pageSize}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/security/certChain" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					证书名称：<input type="text" name="certName" value="${certChainPage.certName}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<%-- 
					<li><a class="button" href="${ctx}/security/certChain/toAdvanceSearch" target="dialog" mask="true" title="高级检索"><span>高级检索</span></a></li>
				--%>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/security/certChain/toEditFormPage/-1" target="dialog" mask="true"><span>添加根证书</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/security/certChain/delete/{sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/security/certChain/toEditFormPage/{sid_user}" target="dialog" mask="true"><span>修改</span></a></li>
			<li class="line">line</li>
			<%-- 
				<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="确定导出所有记录吗?"><span>导出EXCEL</span></a></li>
			--%>
			<li><a class="icon" href="javascript:$.printBox('w_list_print')"><span>打印</span></a></li>
		</ul>
	</div>
	
	<div id="w_list_print">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="8%">序号</th>
					<th width="15%">证书名称</th>
					<th width="20%">证书主题</th>
					<th width="15%">证书类型</th>
					<th width="15%">校验类型</th>
					<th width="15%">到期时间</th>
					<th width="12%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${certChainPage.data}" varStatus="serial">
					<tr target="sid_user" rel="${item.id}">
						<td>
							${(certChainPage.pageVO.pageSize * (certChainPage.pageVO.currentPage - 1))+ serial.index + 1 } 、</td>
						<td>${item.certName}</td>
						<td title="${item.certDn}">${item.certDn}</td>
						<td>${item.certType}</td>
						<td>
							<c:if test="${item.checkType == 1}">不校验</c:if></td>
						<td>
							<fmt:formatDate pattern="yyyy-mm-dd hh:MM:ss" value="${item.endTime}" type="both"/></td>
						<td>&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/security/certChain/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;   
							<a title="修改" target="dialog" mask="true" href="${ctx}/security/certChain/toEditFormPage/${item.id}" class="btnEdit">修改</a>
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
				<option value="10" <c:if test="${certChainPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${certChainPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${certChainPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${certChainPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${certChainPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${certChainPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${certChainPage.pageVO.totalRows}" numPerPage="${certChainPage.pageVO.pageSize}" pageNumShown="10" currentPage="${certChainPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
