<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/seal/signSealRule">
	<input type="hidden" name="pageNum" value="${signSealRulePage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${signSealRulePage.pageVO.pageSize}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/seal/signSealRule" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					签章规则名称：<input type="text" name="sealName" value="${signSealRulePage.ruleName}"/>
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
			<li><a class="add" href="${ctx}/seal/signSealRule/toEditFormPage" target="navTab"><span>添加签章规则</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/seal/signSealRule/delete/{sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/seal/signSealRule/toEditFormPage?id={sid_user}" target="navTab"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="javascript:$.printBox('w_list_print')"><span>打印</span></a></li>
		</ul>
	</div>
	
	<div id="w_list_print">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="8%">序号</th>
					<th width="20%">签章规则名称</th>
					<th width="20%">印章名称</th>
					<th width="10%">签名策略名称</th>
					<th width="15%">定位类型</th>
					<th width="15%">时间</th>
					<th width="12%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${signSealRulePage.data}" varStatus="serial">
					<tr target="sid_user" rel="${item.id}">
						<td>
							${(signSealRulePage.pageVO.pageSize * (signSealRulePage.pageVO.currentPage - 1))+ serial.index + 1 } 、</td>
						<td>${item.ruleName}</td>
						<td>${item.serverSeal.sealName}</td>
						<td>${item.signPolicy.policyName}</td>
						<td>
							<c:if test="${item.locationType==1}">关键字</c:if>
							<c:if test="${item.locationType==2}">坐标</c:if></td>
						<td>${item.optTime}</td>
						<td>&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/seal/signSealRule/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;   
							<a title="修改印章信息" href="${ctx}/seal/signSealRule/toEditFormPage?id=${item.id}" class="btnEdit" target="navTab">修改</a>
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
				<option value="10" <c:if test="${signSealRulePage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${signSealRulePage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${signSealRulePage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${signSealRulePage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${signSealRulePage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${signSealRulePage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${signSealRulePage.pageVO.totalRows}" numPerPage="${signSealRulePage.pageVO.pageSize}" pageNumShown="10" currentPage="${signSealRulePage.pageVO.currentPage}"></div>
	</div>
	<%--page end--%>
</div>
