<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/systemConfig/softwarePackage" method="post">
	<input type="hidden" name="pageNum" value="${softwarePackagePage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${softwarePackagePage.pageVO.pageSize}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					版本名称：<input type="text" name="name" value="${softwarePackagePage.name}"/>
				</td>
				<td>
					版本号：<input type="text" name="version" value="${softwarePackagePage.version}"/>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
			</tr>
		</table>
	</div>
	</form>
</div>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/systemConfig/softwarePackage/toEditFormPage/-1" target="navTab" ><span>添加版本</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/systemConfig/softwarePackage/delete/{sid_softwarePackage}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/systemConfig/softwarePackage/toEditFormPage/{sid_softwarePackage}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="99%" layoutH="138" rel="softwarePackageBox" >  

		<thead>
			<tr>
			    <th width="10%">序号</th>
				<th width="15%">版本名称</th>
				<th width="15%">版本号</th>
				<th width="10%">是否强制升级</th>
				<th width="15%">升级包大小</th>
				<th width="15%">操作时间</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${softwarePackagePage.data}" varStatus="serial">
				<tr target="sid_softwarePackage" rel="${item.id}">
						<td >
							${(softwarePackagePage.pageVO.pageSize * (softwarePackagePage.pageVO.currentPage - 1))+ serial.index + 1 }</td>
						
						<td >${item.name}</td>
						<td >${item.version}</td>
						<td >
							<c:if test="${item.updateStatus=='1'}">否</c:if>
							<c:if test="${item.updateStatus=='2'}">是</c:if>
						</td>
						<td >${item.size}</td>
						<td><fmt:formatDate value="${item.optTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>			
						<td><div class="center" style="width: 116px;">&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/systemConfig/softwarePackage/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;   
							<a title="修改" target="navTab" mask="true" href="${ctx}/systemConfig/softwarePackage/toEditFormPage/${item.id}" class="btnEdit">修改</a>
						</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
		 <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1}, 'softwarePackageBox')"> 
			<!--	<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">-->
				<option value="10" <c:if test="${softwarePackagePage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${softwarePackagePage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${softwarePackagePage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${softwarePackagePage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${softwarePackagePage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${softwarePackagePage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" rel="softwarePackageBox"  targetType="navTab" totalCount="${softwarePackagePage.pageVO.totalRows}" numPerPage="${softwarePackagePage.pageVO.pageSize}" pageNumShown="10" currentPage="${softwarePackagePage.pageVO.currentPage}"></div>
	</div>
</div>