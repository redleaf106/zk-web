<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/cabinet/urgentReason/">
	<input type="hidden" name="pageNum" value="${urgentReasonPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${urgentReasonPage.pageVO.pageSize}" />
</form>


<div class="pageHeader">

</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/cabinet/urgentReason/toEditFormPage/-1" target="navTab"><span>添加异常原因</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/cabinet/urgentReason/delete/{sid_urgentReason}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/cabinet/urgentReason/toEditFormPage/{sid_urgentReason}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	
	<div id="w_list_print">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="5%">序号</th>
					<th width="15%">理由</th>
					<th width="20%">类别</th>
					<th width="15%">操作时间</th>
					<th width="10%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${urgentReasonPage.data}" varStatus="serial">
					<tr target="sid_urgentReason" rel="${item.id}">
						<td>
							${(urgentReasonPage.pageVO.pageSize * (urgentReasonPage.pageVO.currentPage - 1))+ serial.index + 1 }
						</td>
						<td>${item.reason}</td>
						<td>
							<c:choose><c:when test="${item.type==0}">晚存</c:when>
							<c:when test="${item.type==1}">早取</c:when>
								<c:when test="${item.type==2}">早取提示</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose></td>
						<td><fmt:formatDate value="${item.opttime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/cabinet/urgentReason/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;
							<a title="修改机柜信息" href="${ctx}/cabinet/urgentReason/toEditFormPage/${item.id}" class="btnEdit" target="navTab">修改</a>&nbsp;&nbsp;
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
				<option value="10" <c:if test="${urgentReasonPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${urgentReasonPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${urgentReasonPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${urgentReasonPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${urgentReasonPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${urgentReasonPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${urgentReasonPage.pageVO.totalRows}" numPerPage="${urgentReasonPage.pageVO.pageSize}" pageNumShown="10" currentPage="${urgentReasonPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
