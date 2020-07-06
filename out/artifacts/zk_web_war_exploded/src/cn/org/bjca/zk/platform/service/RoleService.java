package cn.org.bjca.zk.platform.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.Role;
import cn.org.bjca.zk.platform.dao.RoleDao;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.RolePage;

/***************************************************************************
 * <pre>角色管理服务</pre>
 * @文件名称:  RoleService.java
 * @包   路   径：  cn.org.bjca.zk.platform.service
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-26 下午8:33:01
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
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;//角色管理DAO

	/**
	  * <p>分页查询</p>
	  * @Description:
	  * @param rolePage
	  * @return
	 */
	public RolePage<Role> findPage(RolePage<Role> rolePage){
		List<Role> list =roleDao.findPage(rolePage);
		rolePage.setData(list);
		return rolePage;
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delRoleById(String id){
		roleDao.delRoleById(id);
	}
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param role
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(Role role){
		if(StringUtils.isNotBlank(role.getId())){//id不为空时
			roleDao.update(role);
		}else{
			role.setId(EssPdfUtil.genrRandomUUID());
			roleDao.save(role);
		}
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public Role findUniqueById(String id){
		return roleDao.findUniqueById(id);
	}
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	public List<Role> getAll() {
		return roleDao.getAll();
	}
	
	/**
	  * <p>根据印章名称查询列表</p>
	  * @Description:
	  * @param roleName 角色名称
	  * @return
	 */
	public List<Role> findByRoleName(String roleName) {
		return roleDao.findByRoleName(roleName);
	}
}
