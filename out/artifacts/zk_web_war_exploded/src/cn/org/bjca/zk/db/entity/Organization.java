package cn.org.bjca.zk.db.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***************************************************************************
 * <pre>组织机构</pre>
 * @文件名称:  Organization.java
 * @包   路   径：  cn.org.bjca.zk.db.entity
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-9 上午11:11:06
 ***************************************************************************/

@Entity
@Table(name = "BO_ORGANIZATION")
public class Organization extends IdEntity {

	private static final long serialVersionUID = -3628446468922081461L;

	private String orgName;// 机构名称

	private String orgCode;// 机构代码

	private String orgType;// 机构类型

	private String address;// 通讯地址

	private String postalCode;// 邮政编码

	private String tel;// 公司电话

	private String tax;// 传真

	private String email;// 公司邮箱

	private String parentId;// 上级机构ID

	private String showOrder;// 显示顺序

	private String orgFlag;// 机构标识

	private String status = STATUS_START;// 状态，1：为启用,2：为禁用，默认为启用状态

	private String description;// 描述

	private List<Organization> subOrgList = new ArrayList<Organization>();// 下级机构列表

	private Integer nodeDepth;// 机构深度

	private Organization parentOrganization;// 上级机构

	private User user;// 机构创建者

	
	private List<User> userList = new ArrayList<User>();// 用户列表



	@Column(name = "ORGNAME", length = 100)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "ORGCODE", length = 50)
	@Index(name = "IDX_ORGCODE")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "ORGTYPE", length = 4)
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "POSTALCODE", length = 6)
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "TEL", length = 50)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "TAX", length = 50)
	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PARENTID", length = 32)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "SHOWORDER", length = 4)
	public String getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}

	@Column(name = "ORGFLAG", length = 500)
	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	@Column(name = "STATUS", length = 4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	@Transient
	public List<Organization> getSubOrgList() {
		return subOrgList;
	}

	public void setSubOrgList(List<Organization> subOrgList) {
		this.subOrgList = subOrgList;
	}

	@Column(name = "NODEDEPTH")
	public Integer getNodeDepth() {
		return nodeDepth;
	}

	public void setNodeDepth(Integer nodeDepth) {
		this.nodeDepth = nodeDepth;
	}

	@JsonIgnore
	@Transient
	public Organization getParentOrganization() {
		return parentOrganization;
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}

	@JsonIgnore
	@Transient
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	@Transient
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
