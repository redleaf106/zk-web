package cn.org.bjca.zk.platform.bean;

import java.io.Serializable;

/***************************************************************************
 * <pre>附件message</pre>
 * @文件名称:  AttachmentMessage.java
 * @包   路   径：  cn.org.bjca.seal.esspdf.platform.bean
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-4-16 上午10:46:13
 ***************************************************************************/
public class AttachmentMessage implements Serializable {

	private static final long serialVersionUID = 4012676770229329193L;

	private String id;

	private String fileName;// 文件名称

	private String attachmentPath;// 附件路径

	private String attachmentSize;// 附件大小

	private String attachmentMaxSize;// 附件最大值

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(String attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

	public String getAttachmentMaxSize() {
		return attachmentMaxSize;
	}

	public void setAttachmentMaxSize(String attachmentMaxSize) {
		this.attachmentMaxSize = attachmentMaxSize;
	}

}
