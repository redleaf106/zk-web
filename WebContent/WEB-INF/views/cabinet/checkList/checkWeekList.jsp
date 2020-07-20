<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form id="pagerForm" method="post" action="${ctx}/cabinet/checkList">
    <input type="hidden" name="pageNum" value="${checkListPage.pageVO.currentPage}" />
    <input type="hidden" name="numPerPage" value="${checkListPage.pageVO.pageSize}" />
</form>

<script type="text/javascript">

    var downloadpath=[];
    var filename=[];
    var filepath=document.getElementById("dayCheckFilePath").value;

    function getcheckbox() {
        downloadpath=[];
        filename=[];
        $("input[name='downloadFiles']:checked").each(function(i){
//把所有被选中的复选框的值存入数组
            downloadpath[i]=filepath+$(this).val();
            filename[i]=$(this).val();
        })
    }

    var btn = document.getElementById('download-btn');

    function download(name, href) {
        var a = document.createElement("a"), //创建a标签
            e = document.createEvent("MouseEvents"); //创建鼠标事件对象
        e.initEvent("click", false, false); //初始化事件对象
        a.href = href; //设置下载地址
        a.download = name; //设置下载文件名
        a.dispatchEvent(e); //给指定的元素，执行事件click事件
    }

    //给多文件下载按钮添加点击事件
    btn.onclick = function name(params) {
        if(downloadpath.length<1){
            alertMsg.info("请先勾选要下载的报表");
        }else {
            for (let index = 0; index < downloadpath.length; index++) {
                download(filename[index], downloadpath[index]);
            }
        }
    }
</script>

<div class="pageHeader">
    <input type="text" id="dayCheckFilePath" value="${ctx}/checkList/" style="display: none">
    <form action="${ctx}/cabinet/cabinetDoorEvent/checkDayInfo" method="post" id="checkListForm" onsubmit="return validateCallback(this, navTabAjaxDone)">
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
    批量下载&nbsp;&nbsp;
    <a id="download-btn" href="javascript:void(0)"><img src="${ctx}/images/icons/download.png" height="20px" width="20px"></a>
</div>

<div class="pageContent">

    <div id="w_list_print">
        <form>
            <table class="table" width="100%" layoutH="138">
                <thead>
                <tr>
                    <th width="5%">序号</th>
                    <th width="15%">文件名</th>
                    <th width="15%">文件路径</th>
                    <th width="15%">生成时间</th>
                    <th width="10%" align="center">操作</th>
                    <th width="10%" align="center">选择</th>
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
                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="" href="${ctx}/cabinet/checkList/showDayChcek/${item.id}" target="dialog" rel="dlg_page1" max="true" mask="true">
                                <img src="${ctx}/images/icons/yulan.png" height="20px" width="20px">
                            </a>
                            <a title="下载" href="${ctx}/checkList/${item.fileName}" class="">
                                <img src="${ctx}/images/icons/download.png" height="20px" width="20px">
                            </a>&nbsp;&nbsp;
                        </td>
                        <td>
                            <input type="checkbox" name="downloadFiles" id="downloadFiles" value="${item.fileName}" onchange="getcheckbox()">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>
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

