<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/cabinet/cabinetDoorEvent">
	<input type="hidden" name="pageNum" value="${eventInfoPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${eventInfoPage.pageVO.pageSize}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cabinet/cabinetDoorEvent" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					机柜编号：<input type="text" name="cabinetNumber" value="${eventInfoPage.cabinetNumber}"/>
				</td>
				<td>
					柜门编号：<input type="text" name="cabinetDoorName" value="${eventInfoPage.cabinetDoorName}"/>
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
					<th width="10%">图片预览</th>
					<th width="10%">视频下载</th>
					<th width="20%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${eventInfoPage.data}" varStatus="serial">
					<tr target="sid_cabinetDoorEvent" rel="${item.id}">
						<td>
							${(eventInfoPage.pageVO.pageSize * (eventInfoPage.pageVO.currentPage - 1))+ serial.index + 1 }
						</td>
						<td>${item.cabinetnumber}</td>
						<td>${item.cabinetpositiondetail}</td>
						<td>${item.cabinetdoorname}</td>
						<td>${item.employeename}</td>
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
						<td><fmt:formatDate value="${item.dooropttime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<a class="button" href="${ctx}/cabinet/cabinetDoorEvent/showMonitorPic?picFilePath=${item.picFilePath}" target="dialog" rel="dlg_page1" max="true" mask="true">
								<span>预览监控图</span>
							</a>
								</td>
						<td>
							<a class="button" href="${ctx}/cabinet/cabinetDoorEvent/showMonitorVideo?videoFilePath=${item.videoFilePath}" target="dialog" rel="dlg_page1"  max="true"mask="true">
								<span>预览监控视频</span>
							</a>
								</td>
						<td>&nbsp;
							<a title="查看详情" href="${ctx}/cabinet/cabinetDoorEvent/toDetailPage/${item.id}" class="btnEdit" target="navTab">查看详情</a>&nbsp;&nbsp;
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
				<option value="300" <c:if test="${eventInfoPage.pageVO.pageSize == 300}">selected="selected"</c:if>>300</option>
				<option value="400" <c:if test="${eventInfoPage.pageVO.pageSize == 400}">selected="selected"</c:if>>400</option>
				<option value="500" <c:if test="${eventInfoPage.pageVO.pageSize == 500}">selected</c:if>>500</option>
				<option value="700" <c:if test="${eventInfoPage.pageVO.pageSize == 700}">selected</c:if>>700</option>
				<option value="1000" <c:if test="${eventInfoPage.pageVO.pageSize == 1000}" >selected</c:if>>1000</option>
			</select>
			<span>条，共${eventInfoPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${eventInfoPage.pageVO.totalRows}" numPerPage="${eventInfoPage.pageVO.pageSize}" pageNumShown="10" currentPage="${eventInfoPage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
