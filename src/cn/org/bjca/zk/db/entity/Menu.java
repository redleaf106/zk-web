package cn.org.bjca.zk.db.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/***************************************************************************
 * <pre>功能菜单</pre>
 * @文件名称:  FunctionMenu.java
 * @包   路   径：  cn.org.bjca.zk.db.entity
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  Menu分为系统菜单和自定义菜单，系统菜单固定授权给系统角色。
 * 自定义菜单父级菜单状态为2，子级菜单有两种状态，status=2或0，状态为2的菜单可供自定义角色进行授权，状态为0是隐藏状态，通常增强版功能默认是0，例如条形码和PDF模板。
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-13 下午3:54:50
 ***************************************************************************/
@Entity
@Table(name = "BO_MENU")
public class Menu extends IdEntity {

	private static final long serialVersionUID = 7922557861464609145L;

	private String menuName;// 菜单名称

	private String parentId;// 父结点id

	private String link;// url地址

	private String target;// 目标

	private String rel;// 关联

	private String showOrder;// 显示顺序

	private String status = "1";// 1:系统菜单，2：自定义菜单，0：是隐藏状态，已隐藏的菜单在角色管理中无法查看和授权，增强版功能菜单状态默认为0。

	private List<Menu> subMenuList = new ArrayList<Menu>();// 子菜单列表

	@Column(name = "MENUNAME", length = 50)
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "PARENTID", length = 32)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "LINK", length = 100)
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "TARGET", length = 20)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "REL", length = 20)
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@Column(name = "SHOWORDER", length = 20)
	public String getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}

	@Column(name = "STATUS", length = 4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public List<Menu> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<Menu> subMenuList) {
		this.subMenuList = subMenuList;
	}

}
