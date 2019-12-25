<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/cabinet/cabinet">
	<input type="hidden" name="pageNum" value="${cabinetPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${cabinetPage.pageVO.pageSize}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cabinet/cabinet" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					机柜编号：<input type="text" name="cabinetNumber" value="${cabinetPage.cabinetNumber}"/>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button id="cabinetList_submit" type="submit">检索</button></div></div></td>
			</tr>
		</table>
		
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/cabinet/cabinet/toEditFormPage/-1" target="navTab"><span>添加机柜</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/cabinet/cabinet/delete/{sid_cabinet}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/cabinet/cabinet/toEditFormPage/{sid_cabinet}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	
	<div id="w_list_print">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="5%">序号</th>
					<th width="15%">机柜编号</th>
					<th width="20%">机柜位置</th>
					<th width="10%">机柜IP</th>
					<th width="15%">操作时间</th>
					<th width="5%">已使用/空闲/总数</th>
					<th width="5%">状态</th>
					<th width="5%">摄像头状态</th>
					<th width="10%">软件版本号</th>
					<th width="10%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${cabinetPage.data}" varStatus="serial">
					<tr target="sid_cabinet" rel="${item.id}">
						<td>
							${(cabinetPage.pageVO.pageSize * (cabinetPage.pageVO.currentPage - 1))+ serial.index + 1 } 
						</td>
						<td>${item.cabinetNumber}</td>
						<td>${item.cabinetPosition}-${item.cabinetPositionDetail}</td>
						<td>${item.cabinetIP}</td>
						<td><fmt:formatDate value="${item.optTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${item.fullDoorCount}/${item.doorCount-item.fullDoorCount}/${item.doorCount}</td>
						<td>不可用
						</td>
						<td>0/0</td>
						<td>${item.software}</td>
						<td>&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/cabinet/cabinet/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;   
							<a title="修改机柜信息" href="${ctx}/cabinet/cabinet/toEditFormPage/${item.id}" class="btnEdit" target="navTab">修改</a>&nbsp;&nbsp;
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
				<option value="10" <c:if test="${cabinetPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${cabinetPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${cabinetPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${cabinetPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${cabinetPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${cabinetPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${cabinetPage.pageVO.totalRows}" numPerPage="${cabinetPage.pageVO.pageSize}" pageNumShown="10" currentPage="${cabinetPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
