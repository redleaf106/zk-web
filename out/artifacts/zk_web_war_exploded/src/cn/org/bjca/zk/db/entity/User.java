package cn.org.bjca.zk.db.entity;

import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***************************************************************************
 * <pre>用户</pre>
 * @文件名称:  User.java
 * @包   路   径： cn.org.bjca.zk.db.entity
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-13 上午11:52:22
 ***************************************************************************/
@Entity
@Table(name = "BO_USER")
public class User extends IdEntity {

	private static final long serialVersionUID = 6699983329530975844L;

	private String userName;// 用户名称

	private String loginName;// 登录名称

	private String pwd;// 密码

	private String serialNumber;// 证书序列号

	private String certDn;// 证书DN项

	private Timestamp beginTime; // 证书起始时间

	private Timestamp endTime; // 证书到期时间

	private String creditType;// 证件类型

	private String creditCode;// 证件号码

	private String email;// 邮箱

	private String status = STATUS_START;// 状态，1：为启用,2：为禁用，默认为启用状态

	private String mobilePhone;// 移动电话

	private String telephone;// 固定电话

	private String orgId;// 所属机构ID

	private String orgFlag;// 机构标识

	private Integer loginTimes;// 登录失败次数

	private byte[] certFile;// 证书文件

	private String loginType = "1";// 登录类型，1：普通用户、2：证书用户、3：两者

	private String certChainId;// 根证书ID

	private String roleId;// 角色id

	private Organization organization;// 组织机构

	private Role role;// 角色

	private User user;// 用户

	private String userType = "2";// 用类型，1：为超级用户，2：普通用户，默认为普通用户

	private String certSrc = "1";// 用户证书来源,1:为USBKey,2:为文件，默认为：USBKey

	@Column(name = "USERNAME", length = 100)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "CREDITTYPE", length = 4)
	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	@Column(name = "CREDITCODE", length = 50)
	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "STATUS", length = 4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "MOBILEPHONE", length = 50)
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "TELEPHONE", length = 50)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "ORGID", length = 32)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name = "ORGFLAG", length = 500)
	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	@JsonIgnore
	@Column(name = "PWD", length = 50)
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "SERIALNUMBER", length = 50)
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "CERTDN", length = 255)
	public String getCertDn() {
		return certDn;
	}

	public void setCertDn(String certDn) {
		this.certDn = certDn;
	}

	@Column(name = "BEGINTIME")
	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	@Column(name = "ENDTIME")
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "LOGINTIMES")
	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	@Column(name = "LOGINNAME", length = 50)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@JsonIgnore
	@Column(name = "CERTFILE")
	@Lob
	public byte[] getCertFile() {
		return certFile;
	}

	public void setCertFile(byte[] certfile) {
		if (certfile != null) {// 解决 The user-supplied array is stored directly
			this.certFile = Arrays.copyOf(certfile, certfile.length);
		}
	}

	@Column(name = "LOGINTYPE", length = 4)
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Column(name = "CERTCHAINID", length = 32)
	public String getCertChainId() {
		return certChainId;
	}

	public void setCertChainId(String certChainId) {
		this.certChainId = certChainId;
	}

	@Column(name = "ROLEID", length = 32)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@JsonIgnore
	@Transient
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@JsonIgnore
	@Transient
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "USERTYPE", length = 4)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "CERTSRC", length = 4)
	public String getCertSrc() {
		return certSrc;
	}

	public void setCertSrc(String certSrc) {
		this.certSrc = certSrc;
	}

	@JsonIgnore
	@Transient
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
