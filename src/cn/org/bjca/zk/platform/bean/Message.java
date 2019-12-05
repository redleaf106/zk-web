package cn.org.bjca.zk.platform.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/***************************************************************************
 * <pre>系统消息类</pre>
 * @文件名称:  Message.java
 * @包   路   径：  cn.org.bjca.zk.platform.bean
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V1.0
 * @创建人： wangwenc
 * @创建时间：2013-2-12 下午7:47:45
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 ***************************************************************************/
public class Message implements Serializable {

	private static final long serialVersionUID = -9162572643993516914L;
	
	private String statusCode;//状态码
	
	private String content;//内容
	
	private String forwardUrl = "";//定位url地址
	
	private String navTabId = "";//table id
	
	private String rel = "";//关系
	
	private String callbackType = "";//回调类型

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@JsonProperty("message")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

}
