<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/cabinet/cabinetDoor">
	<input type="hidden" name="pageNum" value="${cabinetDoorPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${cabinetDoorPage.pageVO.pageSize}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cabinet/cabinetDoor" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					机柜编号：<input type="text" name="cabinetNumber" value="${cabinetDoorPage.cabinetNumber}"/>
				</td>
				<td>
					柜门编号：<input type="text" name="cabinetDoorNumber" value="${cabinetDoorPage.cabinetDoorNumber}"/>
				</td>
				<td>
					员工姓名：<input type="text" name="employeeName" value="${cabinetDoorPage.employeeName}"/>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button id="cabinetDoorList_submit" type="submit">检索</button></div></div></td>
			</tr>
		</table>
		
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/cabinet/cabinetDoor/toEditFormPage/-1" target="navTab"><span>绑定柜门</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/cabinet/cabinetDoor/delete/{sid_cabinetDoor}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
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
					<th width="10%">使用人</th>
					<th width="10%">操作时间</th>
					<%--<th width="10%">存取次数</th>--%>
					<th width="10%">状态</th>
					<th width="20%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${cabinetDoorPage.data}" varStatus="serial">
					<tr target="sid_cabinetDoor" rel="${item.id}">
						<td>
							${(cabinetDoorPage.pageVO.pageSize * (cabinetDoorPage.pageVO.currentPage - 1))+ serial.index + 1 } 
						</td>
						<td>${item.cabinet.cabinetNumber}</td>
						<td>${item.cabinetDoorNumber}</td>
						<td>${item.employee.employeeName}</td>
						<td><fmt:formatDate value="${item.doorOptTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<%--<td>${item.accessCount}</td>--%>
						<td>
							<c:choose>
								<c:when test="${item.status=='1'}"><i class="iconfont">&#xe6a2;</i>存入</c:when>
								<c:when test="${item.status=='2'}"><i class="iconfont">&#xe6a1;</i>取出</c:when>
								<c:otherwise>--</c:otherwise>
							</c:choose>
						</td>
						<td>&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/cabinet/cabinetDoor/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;   
							<a title="修改授权信息" href="${ctx}/cabinet/cabinetDoor/toEditFormPage/${item.id}" class="btnEdit" target="navTab">修改</a>&nbsp;
							<%--<a title="确定开柜门吗？" href="${ctx}/cabinet/cabinetDoor/openCabinetDoor/${item.id}" class="btnView" target="ajaxTodo">开门</a>&nbsp;--%>
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
				<option value="10" <c:if test="${cabinetDoorPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${cabinetDoorPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${cabinetDoorPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${cabinetDoorPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${cabinetDoorPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${cabinetDoorPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${cabinetDoorPage.pageVO.totalRows}" numPerPage="${cabinetDoorPage.pageVO.pageSize}" pageNumShown="10" currentPage="${cabinetDoorPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
