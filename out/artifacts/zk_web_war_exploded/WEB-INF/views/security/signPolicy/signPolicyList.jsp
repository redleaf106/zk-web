<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/security/signPolicy">
	<input type="hidden" name="pageNum" value="${signPolicyPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${signPolicyPage.pageVO.pageSize}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/security/signPolicy" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					签名策略名称：<input type="text" name="policyName" value="${signPolicyPage.policyName}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<%-- 
					<li><a class="button" href="${ctx}/security/signPolicy/toAdvanceSearch" target="dialog" mask="true" title="高级检索"><span>高级检索</span></a></li>
				--%>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/security/signPolicy/toCertReqFormPage" target="dialog" mask="true"><span>产生证书请求</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="${ctx}/security/signPolicy/toSignPolicyFormPage/-1" target="dialog" mask="true"><span>添加签名策略</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/security/signPolicy/delete/{sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/security/signPolicy/toSignPolicyFormPage/{sid_user}" target="dialog" mask="true"><span>修改</span></a></li>
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
					<th width="15%">签名策略名称</th>
					<th width="20%">证书主题</th>
					<th width="15%">根证书名称</th>
					<th width="15%">签名模式</th>
					<th width="15%">到期时间</th>
					<th width="12%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${signPolicyPage.data}" varStatus="serial">
					<tr target="sid_user" rel="${item.id}">
						<td>
							${(signPolicyPage.pageVO.pageSize * (signPolicyPage.pageVO.currentPage - 1))+ serial.index + 1 } 、</td>
						<td>${item.policyName}</td>
						<td title="${item.certDn}">${item.certDn}</td>
						<td>${item.certChain.certName}</td>
						<td>
							<c:if test="${item.signPattern==1}">本地签名</c:if></td>
						<td>
							<fmt:formatDate pattern="yyyy-mm-dd hh:MM:ss" value="${item.endTime}" type="both"/></td>
						<td>&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/security/signPolicy/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;   
							<a title="修改" target="dialog" mask="true" href="${ctx}/security/signPolicy/toSignPolicyFormPage/${item.id}" class="btnEdit">修改</a>
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
				<option value="10" <c:if test="${signPolicyPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${signPolicyPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${signPolicyPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${signPolicyPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${signPolicyPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${signPolicyPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${signPolicyPage.pageVO.totalRows}" numPerPage="${signPolicyPage.pageVO.pageSize}" pageNumShown="10" currentPage="${signPolicyPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
