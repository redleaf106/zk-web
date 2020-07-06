package cn.org.bjca.zk.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.Role;
import cn.org.bjca.zk.platform.web.page.RolePage;

/***************************************************************************
 * <pre>角色管理DAO</pre>
 * @文件名称:  RoleDao.java
 * @包   路   径：  cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-26 下午8:29:38
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
@MyBatisRepository
public interface RoleDao {
	
	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */
	List<Role> findPage(RolePage<Role> webPage);
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Delete("DELETE FROM BO_ROLE WHERE id = #{id}")
	void delRoleById(String id);
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param role
	 */
	void save(Role role);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param serverSeal
	 */
	void update(Role role);
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_ROLE WHERE id = #{id}")
	Role findUniqueById(String id);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_ROLE")
	List<Role> getAll();
	
	/**
	  * <p>根据印章名称查询列表</p>
	  * @Description:
	  * @param sealName
	  * @return
	 */
	@Select("SELECT * FROM BO_ROLE WHERE ROLENAME = #{roleName}")
	List<Role> findByRoleName(String roleName);
}
