<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/cabinet/checkList">
    <input type="hidden" name="pageNum" value="${checkListPage.pageVO.currentPage}" />
    <input type="hidden" name="numPerPage" value="${checkListPage.pageVO.pageSize}" />
</form>

<script type="text/javascript">
    function saveReport() {
        alert("ssss")
    }
</script>

<div class="pageHeader">
    <form action="${ctx}/cabinet/cabinetDoorEvent/checkWMInfo" method="post" id="checkListForm" onsubmit="return validateCallback(this, navTabAjaxDone)">
        <input type="hidden" value="2" name="reportType"/>
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        日期：<input type="date" name="date" />
                    </td>
                    <td><div class="buttonActive"><div class="buttonContent"><button id="checkList_submit" type="submit">手动生成</button></div></div></td>
                </tr>
            </table>

        </div>
    </form>
</div>

<div class="pageContent">

    <div id="w_list_print">
        <table class="table" width="100%" layoutH="138">
            <thead>
            <tr>
                <th width="5%">序号</th>
                <th width="15%">文件名</th>
                <th width="15%">文件路径</th>
                <th width="15%">生成时间</th>
                <th width="10%" align="center">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${checkListPage.data}" varStatus="serial">
                <tr target="sid_checkList" rel="${item.id}">
                    <td>
                            ${(checkListPage.pageVO.pageSize * (checkListPage.pageVO.currentPage - 1))+ serial.index + 1 }
                    </td>
                    <td>${item.fileName}</td>
                    <td>${item.filePath}</td>
                    <td><fmt:formatDate value="${item.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>&nbsp;
                        <a title="下载报表" href="${ctx}/checkList/${item.fileName}" class="btnInfo" >下载</a>&nbsp;&nbsp;
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
                <option value="10" <c:if test="${checkListPage.pageVO.pageSize == 10}">selected="selected"</c:if>>10</option>
                <option value="20" <c:if test="${checkListPage.pageVO.pageSize == 20}">selected="selected"</c:if>>20</option>
                <option value="50" <c:if test="${checkListPage.pageVO.pageSize == 50}">selected</c:if>>50</option>
                <option value="100" <c:if test="${checkListPage.pageVO.pageSize == 100}">selected</c:if>>100</option>
                <option value="200" <c:if test="${checkListPage.pageVO.pageSize == 200}" >selected</c:if>>200</option>
            </select>
            <span>条，共${checkListPage.pageVO.totalRows}条</span>
        </div>
        <div class="pagination" targetType="navTab" totalCount="${checkListPage.pageVO.totalRows}" numPerPage="${checkListPage.pageVO.pageSize}" pageNumShown="10" currentPage="${checkListPage.pageVO.currentPage}"></div>
    </div>
    <%--page end--%>
</div>

