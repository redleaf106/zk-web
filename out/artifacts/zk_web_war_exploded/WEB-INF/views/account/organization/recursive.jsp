<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<li>
	<a href="${ctx}/account/organization/orgList/${org.id}" target="ajax" rel="organizationBox" title="${org.orgName}">
		<c:choose>  
			<c:when test="${fn:length(org.orgName) > (18-org.nodeDepth-2)}">  
				<c:out value="${fn:substring(org.orgName, 0, 18-org.nodeDepth-2)} ..." />  
			</c:when>  
			<c:otherwise>  
				${org.orgName}
			</c:otherwise>  
		</c:choose>
	</a>
	<c:if test="${not empty org.subOrgList}">
		<ul>
		 	<c:forEach var="org" items="${org.subOrgList}">
                <c:set var="org" value="${org}" scope="request"/>
            	<jsp:include page="recursive.jsp"/>
            </c:forEach>           
		</ul>
	</c:if>
</li>