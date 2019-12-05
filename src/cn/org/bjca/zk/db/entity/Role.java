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
 * <pre>角色</pre>
 * @文件名称:  Role.java
 * @包   路   径：  cn.org.bjca.zk.db.entity
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  角色分为系统角色和自定义角色，系统角色的对应的菜单不可自定义，Menu的status为1，例如系统管理员和审计管理员；
 * 自定义角色功能可自定义授权。
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-13 下午4:37:27
 ***************************************************************************/

@Entity
@Table(name = "BO_ROLE")
public class Role extends IdEntity {

	private static final long serialVersionUID = -476472798488488071L;

	private String roleName;// 角色名称

	private String orgId;// 所属机构ID

	private String orgFlag;// 机构标识

	private String usedRule = "2";// 使用范围，1:系统角色、2：自定义角色

	private String description;// 描述

	private String status = STATUS_START;// 角色状态，1：为启用,2：为禁用，默认为启用状态

	private Organization organization;// 组织机构

	private User user;// 创建人

	private List<RoleMenu> roleMenuList = new ArrayList<RoleMenu>();// 角色菜单授权列表

	private List<User> userList = new ArrayList<User>();// 用户列表

	@Column(name = "ROLENAME", length = 100)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "ORGID", length = 32)
	@Index(name = "orgIdIndex")
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

	@Column(name = "USEDRULE", length = 4)
	public String getUsedRule() {
		return usedRule;
	}

	public void setUsedRule(String usedRule) {
		this.usedRule = usedRule;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "STATUS", length = 4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	public List<RoleMenu> getRoleMenuList() {
		return roleMenuList;
	}

	public void setRoleMenuList(List<RoleMenu> roleMenuList) {
		this.roleMenuList = roleMenuList;
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
