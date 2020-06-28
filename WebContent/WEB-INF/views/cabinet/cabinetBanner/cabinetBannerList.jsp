<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>


<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="add" href="${ctx}/cabinet/cabinetBanner/toEditCabinetBannerFormPage/-1" target="navTab" ><span>添加界面图片</span></a></li>
		<li class="line">line</li>
		<li><a class="delete" href="${ctx}/cabinet/cabinetBanner/delete/{sid_cabinetBanner}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
		<li class="line">line</li>
		<li><a class="edit" href="${ctx}/cabinet/cabinetBanner/toEditFormPage/{sid_cabinetBanner}" target="navTab"><span>修改</span></a></li>
	</ul>
</div>
<table class="table" width="99%" layoutH="138" rel="employeeBox">
<thead>
<tr>
	<th width="10%">序号</th>
	<th width="10%">图片</th>
	<th width="10%">启用/停用</th>
	<th width="10%">置顶状态</th>
	<th width="20%">操作</th>
</tr>
</thead>
<tbody>
<c:forEach var="item" items="${cabinetBannerPage.data}" varStatus="serial">
	<tr target="sid_cabinetBanner" rel="${item.id}" style="text-align:center;vertical-align:middle">
		<td >
				${(cabinetBannerPage.pageVO.pageSize * (cabinetBannerPage.pageVO.currentPage - 1))+ serial.index + 1 }</td>

		<td >
			<a class="button" href="${ctx}/cabinet/cabinetBanner/showPic/${item.id}" target="dialog" rel="dlg_page1" max="true" mask="true">
				<span>预览</span>
			</a>
						<td style="text-align:center;vertical-align:middle">
							<c:choose>
								<c:when test="${item.picStatus==0}">
                                    <a title="启用/停用" target="ajaxTodo" href="${ctx}/cabinet/cabinetBanner/updateStatus/${item.id}">&nbsp;&nbsp;<img src="${ctx}/images/icons/kaiguan_on.png" height="10" width="20"></a>
                                </c:when>
								<c:when test="${item.picStatus==1}">
                                    <a title="启用/停用" target="ajaxTodo" href="${ctx}/cabinet/cabinetBanner/updateStatus/${item.id}">&nbsp;&nbsp;<img src="${ctx}/images/icons/kaiguan_1.png" height="20" width="20"></a>
                                </c:when>
								<c:otherwise>--</c:otherwise>
							</c:choose>
						</td>
		<td style="text-align:center">
			<c:choose>
				<c:when test="${item.top==0}">
                    <a title="置顶/取消置顶" target="ajaxTodo" href="${ctx}/cabinet/cabinetBanner/updateTop/${item.id}">&nbsp;&nbsp;&nbsp;<img src="${ctx}/images/icons/light_off.png" height="20" width="20">未置顶</a>
                    </c:when>
				<c:when test="${item.top==1}"><a title="置顶/取消置顶" target="ajaxTodo" href="${ctx}/cabinet/cabinetBanner/updateTop/${item.id}">&nbsp;&nbsp;&nbsp;<img src="${ctx}/images/icons/light.png" height="20" width="20">置顶中</a></c:when>
				<c:otherwise>--</c:otherwise>
			</c:choose>
		</td>
						<td>
			<a title="确定要删除吗?" target="ajaxTodo" href="${ctx}/cabinet/cabinetBanner/delete/${item.id}"><img src="${ctx}/images/icons/delete.png" height="20" width="20"></a>&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	</tr>
</c:forEach>
</tbody>
</table>
<div class="panelBar">
	<div class="pages">
		<span>显示</span>
		<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1}, 'employeeBox')">
			<option value="10" <c:if test="${cabinetBannerPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
			<option value="20" <c:if test="${cabinetBannerPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
			<option value="50" <c:if test="${cabinetBannerPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
			<option value="100" <c:if test="${cabinetBannerPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
			<option value="200" <c:if test="${cabinetBannerPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
		</select>
		<span>条，共${cabinetBannerPage.pageVO.totalRows}条</span>
	</div>
	<div class="pagination"  targetType="navTab" totalCount="${cabinetBannerPage.pageVO.totalRows}" numPerPage="${cabinetBannerPage.pageVO.pageSize}" pageNumShown="10" currentPage="${cabinetBannerPage.pageVO.currentPage}"></div>
</div>
</div>