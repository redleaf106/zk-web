package cn.org.bjca.zk.db.entity;

/**
 * 短信网关实体
* FileName: cn.org.bjca.zk.db.entity.MessageGateway
* Copyright (C), 2019, 北京数字认证股份有限公司
* Author:   gaozhijiang
* Date:     2019/11/25 13:21
* Version: 1.0
*/
public class MessageGateway extends IdEntity {

	private static final long serialVersionUID = -4540291510091419874L;
	/**
	 * 短信服务地址
	 */
	private String messageAddress;
	/**
	 * 短信内容模板
	 */
	private String messageTemplate;

	/**
	 * 检测时间
	 */
	private String checkTime;

	public String getMessageAddress() {
		return messageAddress;
	}

	public void setMessageAddress(String messageAddress) {
		this.messageAddress = messageAddress;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}

	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

}
