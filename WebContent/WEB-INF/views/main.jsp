<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
<title>通讯设备智能管控系统</title>
<%@ include file="/commons/meta.jsp" %>

<link href="${ctx}/styles/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${ctx}/styles/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${ctx}/styles/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<!-- <link href="uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/> -->
<!--[if IE]>
<link href="${ctx}/styles/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<script src="${ctx}/scripts/speedup.js" type="text/javascript"></script>
<script src="${ctx}/scripts/jquery-1.7.2.js" type="text/javascript"></script>
<script src="${ctx}/scripts/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/scripts/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/scripts/jquery.bgiframe.js" type="text/javascript"></script>
<!-- <script src="xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>-->
<script src="${ctx}/uploadify/scripts/swfobject.js" type="text/javascript"></script>
<script src="${ctx}/uploadify/scripts/jquery.uploadify.v2.1.0.js" type="text/javascript"></script> 

<script src="${ctx}/scripts/dwz.core.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.util.date.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.validate.method.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.barDrag.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.drag.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.tree.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.accordion.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.ui.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.theme.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.navTab.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.tab.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.resize.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.dialog.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.cssTable.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.stable.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.taskBar.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.ajax.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.pagination.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.database.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.datepicker.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.effects.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.panel.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.checkbox.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.history.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.combox.js" type="text/javascript"></script>
<script src="${ctx}/scripts/dwz.print.js" type="text/javascript"></script>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="${ctx}/scripts/dwz.regional.zh.js" type="text/javascript"></script>


<!-- 新添加日历控件 -->
<script src="${ctx}/scripts/date/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/scripts/date/num-alignment.js" type="text/javascript"></script>
<link href="${ctx}/scripts/date/skin/default/datepicker.css" rel="stylesheet" type="text/css">
<link href="${ctx}/scripts/date/skin/whyGreen/datepicker.css" rel="stylesheet" type="text/css">
<link href="${ctx}/scripts/date/skin/WdatePicker.css" rel="stylesheet" type="text/css">

<!-- select 动态检索 -->
<script src="${ctx}/scripts/select2/js/select2.js" type="text/javascript"></script>
<link href="${ctx}/scripts/select2/css/select2.css" rel="stylesheet" type="text/css" media="screen">

<script type="text/javascript">
$(function(){
	DWZ.init("${ctx}/commons/dwz.frag.xml", {
		debug:false,	//<%--调试模式 【true|false】 --%>
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${ctx}/styles"}); // <%--themeBase 相对于index页面的主题base路径--%>
		}
	});
	initSysCurrTime();
});

function initSysCurrTime() {
    $.ajax({
        type: "POST",
        async: false,
        url: "${ctx}/login/getCurrTime",
        success: function (msg) {
            $("#currentTime").text(msg);
            sysCurrTime = moment(msg, DATE_FORMAT);
            setInterval("getSysCurrTime()", 1000);
        }
    });

}

function getSysCurrTime() {
    $("#currentTime").text(sysCurrTime.add(1, 'seconds').format(DATE_FORMAT));
}

</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<p class="logo">LOGO</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p><font color="white">当前用户： ${sessionScope.sessionUser.userName}
				      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	今天是：
                 <label id="currentTime"></label></font>
                 </p>
				<ul class="nav">
					<li><a href="changepwd.html" target="dialog" width="600">我的信息</a></li>
					<li><a href="${ctx}/login/exit">退出</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
				</ul>
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder expand">
							<c:forEach var="menu" items="${menu}">
	                            <li icon="${ctx}/images/icons/${menu.id}.png"><a>${menu.menuName}</a>
	                                <ul>
	                                    <c:forEach var="sub" items="${menu.subMenuList}">
	                                        <li><a href="${ctx}${sub.link}" target="navTab" rel="${sub.rel}">${sub.menuName}</a></li>
	                                    </c:forEach>
	                                </ul>
	                            </li>
                        	</c:forEach>
						</ul>
					</div>
					
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">系统首页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><%-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" --%>
					<div class="tabsRight">right</div><%-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" --%>
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">系统首页</a></li>
				</ul>
				
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
					<%-- begin --%>
					<img alt="" src="${ctx}/images/main.png" width="95%">
					<%-- end --%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:useBean id="now" class="java.util.Date" />
	<div id="footer">Copyright &copy;<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy"/> &nbsp;&nbsp;&nbsp;&nbsp;万道智控信息技术有限公司  </div>
</body>
</html>