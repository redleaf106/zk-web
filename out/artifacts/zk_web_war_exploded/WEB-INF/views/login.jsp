<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<title>通讯设备智能管控系统</title>
	<%@ include file="/commons/meta.jsp" %>
	<link href="${ctx}/styles/css/login.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/scripts/jquery-1.7.2.js" type="text/javascript"></script>
	<script>
	</script>
</head>
<script>
	<%-- 用户认证--%>
	function userSubmit() {
	   var loginName = $("#loginNameId").val();
	   if(loginName == "") {
			alert("请输入登录名！");
			return false;
	   }
	   var pwd = $("#pwdId").val();
	   if(pwd == "") {
			alert("请输入密码！");
			return false;
	   }
	   var verifyCode = $("#verifyCodeId").val();
	   if(verifyCode == "") {
			alert("请输入验证码！");
			return false;
	   }
	   $.ajax({
		    type:"POST",
		    async:false,
		    url:"${ctx}/login/ajaxAuth",
		    data:{"loginType":"1","loginName":loginName,"pwd":pwd ,"verifyCode":verifyCode },
		    success:function(msg){
		    if(msg == "200"){   
		    	$("#userLoginFormId").submit();
		    }else{
		    	$('#kaptchaImage').click();
		    	alert(msg);
		    } 
	    }
	  });
	}   
	
	<%-- 重新获取验证码 --%>
	$(function(){
	    $('#kaptchaImage').click(function () {
		        $(this).attr('src','${ctx}/commons/validateCode.jsp?' + Math.floor(Math.random()*100) );
		   });
		   $(document).keydown(function(event){
	         var keynum;
	         //为兼容 IE , Firefox  。。。。。等浏览器。判断一下          
	         if (window.event){ // IE
	             keynum = event.keyCode;
	         } else if (event.which) {// Netscape/Firefox/Opera
	         	 keynum = event.which;
	         }
	         if(keynum == 13){
	         	$('#userAuth').click();
	         }
	     });
	});
	<%-- 防止后退到登陆页面 --%>
	history.go(1);
</script>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<img src="${ctx}/styles/default/images/login_logo.png" />
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li>&nbsp;</li>
					</ul>
				</div>
				<h2 class="login_title"><img src="${ctx}/styles/default/images/login_title.png" /></h2>
			</div>
		</div>
		<div id="login_content">
			<div id="userLoginId" class="loginForm">
				<form id="userLoginFormId" action="${ctx}/login" method="post">
					<p>
						<label>登录名：</label>
						<input id="loginNameId" type="text" name="loginName"  minlength="2" maxlength="20" style="width:145px" />
					</p>
					<p>
						<label>密&nbsp;&nbsp;&nbsp;码：</label>
						<input id="pwdId" type="password" name="pwd"  minlength="6" maxlength="20" style="width:145px" autocomplete="off"/>
					</p>
					<p>
						<label>验证码：</label>
						<input name="verifyCode" id="verifyCodeId" class="code" type="text" size="5" minlength="4" maxlength="4"/>
						<span>
							<img src="${ctx}/commons/validateCode.jsp" id="kaptchaImage" style="cursor: hand"  width="75" height="24"/></span>
					</p>
					<div class="login_bar">
					    <br>
						<input class="sub" id="userAuth" type="button" value="" size="20"  onclick="userSubmit();"/>
					</div>
					<input id="login_loginType_1" type="hidden" name="loginType" value="1" />
					<br><br>
				</form>
			</div>
			<div class="login_banner"><img src="${ctx}/styles/default/images/login_banner.jpg" /></div>
			<div class="login_main">
				<ul class="helpList">
				</ul>
				<div class="login_inner">
					<p>国内领先的金融行业管控AI+制造商。</p>
					<p></p>
					<p>终结传统管理模式，开创智能管控新时代。</p>
				</div>
			</div>
		</div>
		<div id="login_footer">
			<jsp:useBean id="now" class="java.util.Date" />
			<div id="footer">Copyright &copy;<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy"/> &nbsp;&nbsp;&nbsp;&nbsp;万道智控信息技术有限公司  </div>
		</div>
	</div>
</body>
	<script type="text/javascript" event="OnLoad" for="window">
		GetUserList("loginForm.certListId");
	</script>
	<script type="text/javascript" event="OnUsbKeyChange" for="XTXAPP">
		ChangeUserList("loginForm.certListId");
	</script>
</html>