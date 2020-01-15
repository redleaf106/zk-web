<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<c:set var="orgId" value="${org.id}"/>
<div class="pageContent" style="padding:5px">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>组织机构树</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
				<div layoutH="50" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
			    	<ul class="tree treeFolder">
						<jsp:include page="recursive.jsp"/>
			    	</ul>
				</div>
				<div id="orgSealBox" class="unitBox" style="margin-left:246px;">
					<!--#include virtual="list1.html" -->
					<jsp:include page="/seal/orgSeal/${orgId}"/>
				</div>
			</div>
		</div>
		
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>