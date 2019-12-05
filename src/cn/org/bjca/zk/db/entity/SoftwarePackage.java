package cn.org.bjca.zk.db.entity;


/**
 * 软件升级包
 * FileName: cn.org.bjca.cabinet.entity.SoftwarePackage
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 12:02
 * Version: 1.0
 */
public class SoftwarePackage extends IdEntity{
	
	private static final long serialVersionUID = -5475405177289221150L;
	
	/**
	 * 选择更新
	 */
	public static final String CHOOSE_UPDATE="1";
	/**
	 * 强制更新
	 */
	public static final String FORCE_UPDATE="2";
	
	private String name;
	
	private String version;
	
	/**
	 * 更新状态
	 */
	private String updateStatus;
    
	private long size;
	
	private String digest;
	
	private String content;
	
	private String storePath;
	
	private byte[] softwarePackage;
	
	private String userId;
	
	private User user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public byte[] getSoftwarePackage() {
		return softwarePackage;
	}

	public void setSoftwarePackage(byte[] softwarePackage) {
		this.softwarePackage = softwarePackage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
