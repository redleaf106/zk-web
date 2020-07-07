<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader">
    <form id="pagerForm" onsubmit="return navTabSearch(this);" method="post" action="${ctx}/cabinet/cabinetDoorEvent">
	<input type="hidden" name="pageNum" value="${cabinetDoorEventPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${cabinetDoorEventPage.pageVO.pageSize}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						机柜编号：<input type="text" name="cabinetNumber" value="${cabinetDoorEventPage.cabinetNumber}"/>
					</td>
					<td>
						柜门名称：<input type="text" name="cabinetdoorname" value="${cabinetDoorEventPage.cabinetdoorname}"/>
					</td>
					<td><div class="buttonActive"><div class="buttonContent"><button id="cabinetDoorEventList_submit" type="submit">检索</button></div></div></td>
				</tr>
			</table>
		</div>
    </form>
</div>
<div class="pageContent">
	<div class="panelBar">
	</div>
	
	<div id="w_list_print">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="5%">序号</th>
					<th width="5%">机柜编号</th>
					<th width="10%">机柜位置</th>
					<th width="5%">柜门名称</th>
					<th width="10%">员工姓名</th>
					<th width="5%">状态</th>
					<th width="10%">异常原因</th>
					<th width="10%">操作时间</th>
					<th width="20%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${cabinetDoorEventPage.data}" varStatus="serial">
					<tr target="sid_cabinetDoorEvent" rel="${item.id}">
						<td>
							${(cabinetDoorEventPage.pageVO.pageSize * (cabinetDoorEventPage.pageVO.currentPage - 1))+ serial.index + 1 } 
						</td>
						<td>${item.cabinetNumber}</td>
						<td>${item.cabinet.cabinetPositionDetail}</td>
						<td>${item.cabinetdoorname}</td>
						<td>${item.employee.employeeName}</td>
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
						<td>${item.remark}</td>
						<td><fmt:formatDate value="${item.doorOptTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>&nbsp;
							<a title="预览监控图" href="${ctx}/cabinet/cabinetDoorEvent/showMonitorPic/${item.id}" target="dialog" rel="dlg_page1" max="true" mask="true" style="margin-right: 15%">
								<img src="${ctx}/styles/default/images/button/1190943.png" width="5%" height="100%">
							</a>
							<a title="预览监控视频" href="${ctx}/cabinet/cabinetDoorEvent/showMonitorVideo/${item.id}" target="dialog" rel="dlg_page1"  max="true"mask="true" style="margin-right: 15%">
								<img src="${ctx}/styles/default/images/button/1230619.png" width="5%" height="100%">
							</a>
							<a title="查看详情" href="${ctx}/cabinet/cabinetDoorEvent/toDetailPage/${item.id}" target="navTab" style="margin-right: 15%">
								<img src="${ctx}/styles/default/images/button/1152103.png" width="5%" height="100%">
							</a>
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
				<option value="300" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 300}">selected="selected"</c:if>>300</option>
				<option value="500" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 500}">selected="selected"</c:if>>500</option>
				<option value="800" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 800}">selected</c:if>>800</option>
				<option value="1000" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 1000}">selected</c:if>>1000</option>
				<option value="1200" <c:if test="${cabinetDoorEventPage.pageVO.pageSize == 1200}" >selected</c:if>>1200</option>
			</select>
			<span>条，共${cabinetDoorEventPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${cabinetDoorEventPage.pageVO.totalRows}" numPerPage="${cabinetDoorEventPage.pageVO.pageSize}" pageNumShown="10" currentPage="${cabinetDoorEventPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
