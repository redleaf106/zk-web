if (window.ActiveXObject) {
		document.writeln("<OBJECT classid=\"CLSID:3F367B74-92D9-4C5E-AB93-234F8A91D5E6\" height=1 id=XTXAPP  style=\"HEIGHT: 1px; LEFT: 10px; TOP: 28px; WIDTH: 1px\" width=1 VIEWASTEXT>");
		document.writeln("</OBJECT>");
		XTXAPP.SOF_GetVersion(); 
	} else {
		document.writeln("<embed id=XTXAPP0 type=application/x-xtx-axhost clsid={3F367B74-92D9-4C5E-AB93-234F8A91D5E6} event_OnUsbkeyChange=OnUsbKeyChange width=1 height=1 />");
		XTXAPP = document.getElementById("XTXAPP0");
		XTXAPP.SOF_GetVersion(); 
	}
	
		//获取用户列表 并填充到证书列表
	function GetUserList(strListID){
		var objListID = eval(strListID);
		var strUserList = XTXAPP.SOF_GetUserList();
		//alert(strUserList);
		while (1) {
			var i = strUserList.indexOf("&&&");
			if (i <= 0 ) {
				break;
			}
			var strOneUser = strUserList.substring(0, i);
			var strName = strOneUser.substring(0, strOneUser.indexOf("||"));
			var strUniqueID = strOneUser.substring(strOneUser.indexOf("||") + 2, strOneUser.length);
			var objItem = new Option(strName, strUniqueID);
			objListID.options.add(objItem);
			var len = strUserList.length;
			strUserList = strUserList.substring(i + 3,len);
		}
		var objListID = null;
		return;
	}
	
	//清空证书列表
	function RemoveUserList(strListID) {
		var objListID = eval(strListID);
		var i;
		var n = objListID.length;
		for(i = 0; i < n; i++) {
			objListID.remove(0);
		}
	}
	
	//重新填充用户列表
	function ChangeUserList(strListID) {
		RemoveUserList(strListID);
		GetUserList(strListID);
	}

	//非IE浏览器下用到的函数
	function OnUsbKeyChange(){
		//alert("OnUsbKeyChange called!");
		ChangeUserList("form1.login_test_certlst");
	}