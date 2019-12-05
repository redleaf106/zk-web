<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
<title>退出系统</title>
	<%@ include file="/commons/meta.jsp" %>
	<script>
		window.top.location.href= "${ctx}/login/toLogin";
	</script>
</head>
</html>