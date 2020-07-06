<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--签章规则表单 --%>
<script type="text/javascript">
	<%-- 选择印章定位类型--%>
	function selLocationType(type){
		if(type == 1){
			$("#keywordId").css("display","");
			$("#keyWord").addClass("required");
			$("#searchOrder").addClass("required");
			$("#moveSize").addClass("required");
			
			$("#rectangId").css("display","none");
			$("#pageNo").toggleClass("required",false);
			$("#posL").toggleClass("required",false);
			$("#posT").toggleClass("required",false);
			$("#posR").toggleClass("required",false);
			$("#posB").toggleClass("required",false);
		}else{
			$("#keywordId").css("display","none");
			$("#keyWord").toggleClass("required",false);
			$("#searchOrder").toggleClass("required",false);
			$("#moveSize").toggleClass("required",false);
			
			$("#rectangId").css("display","");
			$("#pageNo").addClass("required");
			$("#posL").addClass("required");
			$("#posT").addClass("required");
			$("#posR").addClass("required");
			$("#posB").addClass("required");
		}
	}
	
	<%-- 显示印章图片--%>
	function showSeal(id){
		if(id == ""){
			$("#imgDivId").css("display","none");
		}else{
			$("#imgDivId").css("display","");
			$("#sealImgId").attr("src","${ctx}/seal/serverSeal/showSeal?id="+id+"&random=${random}");
		}
	}
</script>
<div class="pageContent">
	<form:form method="post" action="${ctx}/seal/signSealRule/saveOrUpdate" modelAttribute="signSealRule" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<input type="hidden" name="id" value="${signSealRule.id}" />
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>签章规则名称：</label>
				<c:choose>
				   <c:when test="${signSealRule.id == null}">
				       <input type="text" name="ruleName" size="40" minlength="2" maxlength="20" class="required" value="${signSealRule.ruleName}"/>
				   </c:when>
				   <c:otherwise>
				   	   <input type="text" name="ruleName" size="40" minlength="2" maxlength="20" class="required" value="${signSealRule.ruleName}" readonly/>
				   </c:otherwise>
				</c:choose>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<p>
					<label>印章名称：</label>
					<select name="sealId" class="required combox" onchange="showSeal(this.value);">
						<option value="">-- 请选择 --</option>
						<c:forEach var="bean" items="${serverSealList}">
							<option value="${bean.id}" <c:if test="${bean.id == signSealRule.sealId}">selected="selected"</c:if>>${bean.sealName}</option>
						</c:forEach>
					</select>	
				</p>
				<p>
					<label>签名策略：</label>
					<select name="dsSignId" class="required combox">
						<option value="">-- 请选择 --</option>
						<c:forEach var="bean" items="${signPolicyList}">
							<option value="${bean.id}" <c:if test="${bean.id == signSealRule.dsSignId}">selected="selected"</c:if>>${bean.policyName}</option>
						</c:forEach>
					</select>	
				</p>
			</div>
			<c:choose>
			   <c:when test="${signSealRule.id != null}">
			       <div id="imgDivId" class="unit" style="display: block;">
						<label></label>
						<img id="sealImgId" alt="印章图片" src="${ctx}/seal/serverSeal/showSeal?id=${signSealRule.sealId}&random=${random}">
				   </div>
			   </c:when>
			   <c:otherwise>
			   	   <div id="imgDivId" class="unit" style="display: none">
						<label></label>
						<img id="sealImgId" alt="印章图片" src="">
				   </div>
			   </c:otherwise>
			</c:choose>
			<div class="divider"></div>
			<div class="unit">
				<p>
					<label>打印设置：</label>
					<input type="radio" name="isPrintSeal" value="1" checked="checked"/>打印印章
					<input type="radio" name="isPrintSeal" value="2" <c:if test="${2 == signSealRule.isPrintSeal}">checked="checked"</c:if>/>不打印印章
				</p>
				<p>
					<label>是否包含根证书链：</label>
					<input type="radio" name="isContainCertChain" value="1" checked="checked"/>否
					<input type="radio" name="isContainCertChain" value="2" <c:if test="${2 == signSealRule.isContainCertChain}">checked="checked"</c:if>/>是
				</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<p>
					<label>印章定位类型：</label>
					<input type="radio" name="locationType" value="1" checked="checked" onclick="selLocationType(1);"/>基于关键字
					<input type="radio" name="locationType" value="2" <c:if test="${2 == signSealRule.locationType}">checked="checked"</c:if> onclick="selLocationType(2);"/>基于坐标
				</p>
				<p>
					<label>使用范围：</label>
					<select name="usedRule" class="required combox">
						<option value="">-- 请选择 --</option>
						<option value="1" <c:if test="${1 == signSealRule.usedRule}">selected="selected"</c:if>>所有机构</option>
						<option value="2" <c:if test="${2 == signSealRule.usedRule}">selected="selected"</c:if>>下级机构</option>
						<option value="3" <c:if test="${3 == signSealRule.usedRule}">selected="selected"</c:if>>当前机构</option>
					</select>
				</p>
			</div>
			<div class="divider"></div>
			<%-- 基于关键字表单 --%>
			<div id="keywordId" >
				<div class="unit">
					<p>
						<label>关键字名称：</label>
						<input id="keyWord" type="text" name="keyWord" size="40" minlength="1" maxlength="16" class="required" value="${signSealRule.keyWord}"/>
					</p>
					<p>
						<label>检索顺序：</label>
						<select id="searchOrder" name="searchOrder" class="required combox">
							<option value="">-- 请选择 --</option>
							<option value="1" <c:if test="${1 == signSealRule.searchOrder}">selected="selected"</c:if>>正序</option>
							<option value="2" <c:if test="${2 == signSealRule.searchOrder}">selected="selected"</c:if>>倒序</option>
						</select>
					</p>
				</div>
				<div class="unit">
					<p>
						<label>印章覆盖位置：</label>
						<input type="radio" name="moveType" value="1" checked="checked"/>重叠
						<input type="radio" name="moveType" value="2" <c:if test="${2 == signSealRule.moveType}">checked="checked"</c:if>/>居下
						<input type="radio" name="moveType" value="3" <c:if test="${3 == signSealRule.moveType}">checked="checked"</c:if>/>居右
					</p>
					<p>
						<label>左右偏移量：</label>
						<input id="moveSize" type="text" name="moveSize" size="20" minlength="1" maxlength="4" class="required digits" value="${signSealRule.moveSize}"/>
					</p>
					
				</div>
			</div>
			<%-- 基于坐标表单 --%>
			<div id="rectangId" style="display:none;">
				<div class="unit">
					<label>印章所在页码：</label>
					<input id="pageNo" type="text" name="pageNo" size="20" minlength="1" maxlength="3" class="required digits" value="${signSealRule.pageNo}"/>
					<span class="unit">页</span>
				</div>
				<div class="divider"></div>
				<div class="unit">
					<p>
						<label>印章坐标：</label>
					</p>
				</div>
				<div class="unit">
					<p>
						<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							左边界：</label>
						<input id="posL" type="text" name="posL" size="20" minlength="1" maxlength="3" class="required number" value="${signSealRule.posL}"/>
					</p>
					<p>
						<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							上边界：</label>
						<input id="posT" type="text" name="posT" size="20" minlength="1" maxlength="3" class="required number" value="${signSealRule.posT}"/>
					</p>
					
				</div>
				<div class="unit">
					<p>
						<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							右边界：</label>
						<input id="posR" type="text" name="posR" size="20" minlength="1" maxlength="3" class="required number" value="${signSealRule.posR}"/>
					</p>
					<p>
						<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							下边界：</label>
						<input id="posB" type="text" name="posB" size="20" minlength="1" maxlength="3" class="required number" value="${signSealRule.posB}"/>
					</p>
					
				</div>
			</div>
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form:form>
	
	<script type="text/javascript">
		<c:choose>
		   <c:when test="${signSealRule.id == null}">
		   		selLocationType(1);
		   </c:when>
		   <c:otherwise>
		   		selLocationType("${signSealRule.locationType}");
		   </c:otherwise>
		</c:choose>
		
		<c:if test=""></c:if>
	</script>
</div>

