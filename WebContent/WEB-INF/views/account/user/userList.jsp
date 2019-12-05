<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/account/user" method="post">
	<input type="hidden" name="pageNum" value="${userPage.pageVO.currentPage}" />
	<input type="hidden" name="numPerPage" value="${userPage.pageVO.pageSize}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					用户查询：<input type="text" name="userName" value="${userPage.userName}"/>
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
			<li><a class="add" href="${ctx}/account/user/toEditFormPage/-1" target="navTab" ><span>添加用户</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="${ctx}/account/user/delete/{sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="${ctx}/account/user/toEditFormPage/{sid_user}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="99%" layoutH="138" >
		<thead>
			<tr>
			    <th width="20%">序号</th>
				<th width="20%">用户名</th>
				<th width="12%">登陆类型</th>
				<!-- <th width="20%">所属机构名称</th> -->
				<th width="10%">状态</th>
				<th width="18%">时间</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${userPage.data}" varStatus="serial">
				<tr target="sid_user" rel="${item.id}">
						<td >
							${(certChainPage.pageVO.pageSize * (certChainPage.pageVO.currentPage - 1))+ serial.index + 1 }</td>
						<td >${item.userName}</td>
						<td >
							<c:if test="${item.loginType == 1}">普通用户</c:if>
							<c:if test="${item.loginType == 2}">证书用户</c:if>
							<c:if test="${item.loginType == 3}">证书与普通用户</c:if>
						</td>
					<%-- 	<td  title="${item.organization.orgName}">${item.organization.orgName}</td> --%>
						<td >
								<c:if test="${item.status==1}">启用</c:if>
								<c:if test="${item.status==2}">禁用</c:if>
						</td>		
						<td><fmt:formatDate value="${item.optTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>			
						<td><div class="center" style="width: 116px;">&nbsp;
							<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/account/user/delete/${item.id}" class="btnDel">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;   
							<a title="修改" target="navTab" mask="true" href="${ctx}/account/user/toEditFormPage/${item.id}" class="btnEdit">修改</a>
						</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
				<option value="10" <c:if test="${userPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${userPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
				<option value="50" <c:if test="${userPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${userPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${userPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
			</select>
			<span>条，共${userPage.pageVO.totalRows}条</span>
		</div>
		<div class="pagination"  targetType="navTab" totalCount="${userPage.pageVO.totalRows}" numPerPage="${userPage.pageVO.pageSize}" pageNumShown="10" currentPage="${userPage.pageVO.currentPage}"></div>
	</div>
</div>