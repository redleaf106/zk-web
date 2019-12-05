/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.api.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/***************************************************************************

 * @文件名称: SoftwarePackageResponse.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.api.vo
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
public class SoftwarePackageResponse implements Serializable {

	private static final long serialVersionUID = -5644537623451903524L;
	
	@JsonProperty(value = "Code")
	private  int code;
	
	@JsonProperty(value = "Msg")
	private String message;
	
	/**
	 * 更新状态 ：0代表不更新，1代表有版本更新，不需要强制升级，2代表有版本更新，需要强制升级
	 */
	
	@JsonProperty(value = "UpdateStatus")
	private int updateStatus;
	/**
	 * 版本号
	 */
	@JsonProperty(value = "VersionCode")
	private int versionCode;
	/**
	 * 版本名称
	 */
	@JsonProperty(value = "VersionName")
	private String versionName;
	/**
	 * 更新内容
	 */
	@JsonProperty(value = "ModifyContent")
	private String modifyContent;
	/**
	 * 下载地址
	 */
	@JsonProperty(value = "DownloadUrl")
	private String downloadUrl;
	/**
	 * 大小
	 */
	
	@JsonProperty(value = "ApkSize")
	private long apkSize;
	/**
	 * md5值
	 */
	@JsonProperty(value = "ApkMd5")
	private String apkMd5;
	

	@JsonIgnore
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	@JsonIgnore
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	@JsonIgnore
	public String getModifyContent() {
		return modifyContent;
	}
	public void setModifyContent(String modifyContent) {
		this.modifyContent = modifyContent;
	}
	@JsonIgnore
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	@JsonIgnore
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	@JsonIgnore
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@JsonIgnore
	public int getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(int updateStatus) {
		this.updateStatus = updateStatus;
	}
	@JsonIgnore
	public long getApkSize() {
		return apkSize;
	}
	public void setApkSize(long apkSize) {
		this.apkSize = apkSize;
	}
	@JsonIgnore
	public String getApkMd5() {
		return apkMd5;
	}
	public void setApkMd5(String apkMd5) {
		this.apkMd5 = apkMd5;
	}
	
}
