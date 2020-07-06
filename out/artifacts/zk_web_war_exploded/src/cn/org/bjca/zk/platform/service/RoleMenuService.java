package cn.org.bjca.zk.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.RoleMenu;
import cn.org.bjca.zk.platform.dao.RoleMenuDao;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;

/***************************************************************************
 * <pre>角色菜单服务</pre>
 * @文件名称:  RoleMenuService.java
 * @包   路   径：  cn.org.bjca.zk.platform.service
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-27 下午1:41:45
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
@Component
@Transactional(readOnly = true)
public class RoleMenuService {
	
	@Autowired
	private RoleMenuDao roleMenuDao;//角色菜单DAO
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delRoleMenuById(String id) {
		roleMenuDao.delRoleMenuById(id);
	}
	
	/**
	  * <p>根据角色ID删除记录</p>
	  * @Description:
	  * @param roleId
	 */
	@Transactional(readOnly = false)
	public void delRoleMenuByRoleId(String roleId) {
		roleMenuDao.delRoleMenuByRoleId(roleId);
	}
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param roleMenu
	 */
	@Transactional(readOnly = false)
	public void save(RoleMenu roleMenu) {
		roleMenu.setId(EssPdfUtil.genrRandomUUID());
		roleMenuDao.save(roleMenu);
	}
	
	/**
	  * <p>根据角色id查找角色菜单</p>
	  * @Description:
	  * @param roleId
	  * @return
	 */
	public List<RoleMenu> findByRoleId(String roleId) {
		return roleMenuDao.findByRoleId(roleId);
	}

	/**
	  * <p>根据角色id查找角色菜单Map列表</p>
	  * @Description:
	  * @param roleId
	  * @return
	 */
	public List<RoleMenu> findMapsByRoleId(String roleId) {
		return roleMenuDao.findMapsByRoleId(roleId);
	}
	
}
