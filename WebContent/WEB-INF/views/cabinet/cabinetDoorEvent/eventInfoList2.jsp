<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader" style="border:1px #B8D0D6 solid">
    <form id="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/cabinet/cabinetDoorEvent" method="post">
        <input type="hidden" name="pageNum" value="${employeePage.pageVO.currentPage}" />
        <input type="hidden" name="numPerPage" value="${employeePage.pageVO.pageSize}" />
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        机柜编号：<input type="text" name="employeeName" value="${employeePage.cabinetNumber}"/>
                    </td>
                    <td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
                </tr>
            </table>
        </div>
    </form>
</div>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
    <table class="table" width="99%" layoutH="138" rel="employeeBox">
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
        <c:forEach var="item" items="${employeePage.data}" varStatus="serial">
            <tr target="sid_employee" rel="${item.id}">
                <td >
                        ${(employeePage.pageVO.pageSize * (employeePage.pageVO.currentPage - 1))+ serial.index + 1 }</td>

                <td>${item.cabinetNumber}</td>
                <td>${item.employeeCardNumber}</td>
                <td>${item.employeeCardNumber}</td>
                <td>${item.employeeCardNumber}</td>
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
                <td><div class="center" style="width: 116px;">
                    <a class="button" href="${ctx}/cabinet/cabinetDoorEvent/showMonitorPic?" target="dialog" rel="dlg_page1" max="true" mask="true">
                        <span>预览监控图</span>
                    </a>
                    <a class="button" href="${ctx}/cabinet/cabinetDoorEvent/showMonitorVideo?" target="dialog" rel="dlg_page1"  max="true"mask="true">
                        <span>预览监控视频</span>
                    </a>
                    <a title="查看详情" href="${ctx}/cabinet/cabinetDoorEvent/toDetailPage/${item.id}" class="btnEdit" target="navTab">查看详情</a>&nbsp;&nbsp;
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
                <option value="100" <c:if test="${employeePage.pageVO.pageSize == 100}">selected="selected"</c:if>>100</option>
                <option value="150" <c:if test="${employeePage.pageVO.pageSize == 150}">selected="selected"</c:if>>150</option>
                <option value="200" <c:if test="${employeePage.pageVO.pageSize == 200}">selected</c:if>>200</option>
                <option value="300" <c:if test="${employeePage.pageVO.pageSize == 300}">selected</c:if>>300</option>
                <option value="500" <c:if test="${employeePage.pageVO.pageSize == 500}" >selected</c:if>>500</option>
            </select>
            <span>条，共${employeePage.pageVO.totalRows}条</span>
        </div>
        <div class="pagination"  targetType="navTab" totalCount="${employeePage.pageVO.totalRows}" numPerPage="${employeePage.pageVO.pageSize}" pageNumShown="10" currentPage="${employeePage.pageVO.currentPage}"></div>
    </div>
</div>