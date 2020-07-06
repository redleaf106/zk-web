<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<li>
	<c:choose>  
		<c:when test="${menu.parentId == '-1'}">  
			<a tname="menuIds" tvalue="-1" title="${menu.menuName}">
		</c:when>  
		<c:otherwise>  
			<a tname="menuIds" tvalue="${menu.id}" title="${menu.menuName}" <c:if test="${fn:contains(menuIdList,menu.id)}"> checked="true"</c:if>>
		</c:otherwise>  
	</c:choose>
	<!--  -->
		<c:choose>  
			<c:when test="${fn:length(menu.menuName) > (18-org.nodeDepth-2)}">  
				<c:out value="${fn:substring(menu.menuName, 0, 18-org.nodeDepth-2)} ..." />  
			</c:when>  
			<c:otherwise>  
				${menu.menuName}
			</c:otherwise>  
		</c:choose>
	</a>
	<c:if test="${not empty menu.subMenuList}">
		<ul>
		 	<c:forEach var="menu" items="${menu.subMenuList}">
                <c:set var="menu" value="${menu}" scope="request"/>
            	<jsp:include page="recursive.jsp"/>
            </c:forEach>           
		</ul>
	</c:if>
</li>