<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/cabinet/cabinetDoorEvent">
	<input type="hidden" name="pageNum" value="${cabinetDoorEventPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${cabinetDoorEventPage.pageVO.pageSize}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cabinet/cabinetDoorEvent" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					机柜编号：<input type="text" name="cabinetNumber" value="${cabinetDoorEventPage.cabinetNumber}"/>
				</td>
				<td>
					柜门编号：<input type="text" name="cabinetDoorNumber" value="${cabinetDoorEventPage.cabinetDoorNumber}"/>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button id="cabinetDoorEventList_submit" type="submit">检索</button></div></div></td>
			</tr>
		</table>
		
	</div>
	</form>
	<%--<li><a class="add" href="${ctx}/cabinet/cabinetDoorEvent/jumpMedia" target="navTab"><span>查看监控</span></a></li>--%>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/cabinet/cabinetDoorEvent/toDetailPage/-1" target="navTab"><span>添加事件</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="${ctx}/cabinet/cabinetDoorEvent/sendAllEvent" target="ajaxTodo" title="确定要推送吗?"><span>全部推送</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/cabinet/cabinetDoor/toEditFormPage/{sid_cabinetDoor}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	
	<div id="w_list_print">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="10%">序号</th>
					<th width="20%">机柜编号</th>
					<th width="10%">柜门编号</th>
					<th width="10%">员工IC卡号</th>
					<th width="10%">操作时间</th>
					<th width="10%">状态</th>
					<th width="20%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${cabinetDoorEventPage.data}" varStatus="serial">
					<tr target="sid_cabinetDoorEvent" rel="${item.id}">
						<td>
							${(cabinetDoorEventPage.pageVO.pageSize * (cabinetDoorEventPage.pageVO.currentPage - 1))+ serial.index + 1 } 
						${item.id}
						</td>
						<td>${item.cabinetNumber}</td>
						<td>${item.cabinetDoorNumber}</td>
						<td>${item.employeeCardNumber}</td>
						<td><fmt:formatDate value="${item.doorOptTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<c:choose>
								<c:when test="${item.status=='0'}">正常存件</c:when>
								<c:when test="${item.status=='1'}">正常取件</c:when>
								<c:when test="${item.status=='2'}">晚存件</c:when>
								<c:when test="${item.status=='3'}">早取件</c:when>
								<c:when test="${item.status=='4'}">紧急开门</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
						</td>
						<td>&nbsp;
							<%--<a title="确定要推送吗?" href="${ctx}/cabinet/cabinetDoorEvent/sendOneEvent?id=${item.id}" class="btnEdit" target="ajaxTodo">事件推送</a>&nbsp;&nbsp;--%>
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
				<option value="50" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 100}">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 200}">selected</c:if>>200</option>
				<option value="300" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 300}">selected</c:if>>300</option>
				<option value="500" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 500}" >selected</c:if>>500</option>
			</select>
			<span>条，共${cabinetDoorEventPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${cabinetDoorEventPage.pageVO.totalRows}" numPerPage="${cabinetDoorEventPage.pageVO.pageSize}" pageNumShown="10" currentPage="${cabinetDoorEventPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
