package cn.org.bjca.zk.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/***************************************************************************
 * <pre>角色菜单</pre>
 * @文件名称:  RoleMenu.java
 * @包   路   径：  cn.org.bjca.zk.db.entity
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-13 下午5:07:04
 ***************************************************************************/
@Entity
@Table(name = "RL_ROLE_MENU")
public class RoleMenu extends IdEntity {

	private static final long serialVersionUID = 2193405875942873358L;

	private String roleId;

	private String menuId;

	private Menu menu;// 功能菜单

	@Column(name = "ROLEID", length = 32)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "MENUID", length = 32)
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Transient
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
