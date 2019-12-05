package cn.org.bjca.zk.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.RoleMenu;

/***************************************************************************
 * <pre>角色菜单Dao</pre>
 * @文件名称:  RoleMenuDao.java
 * @包   路   径：  cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-27 下午1:26:30
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
public interface RoleMenuDao {
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Delete("DELETE FROM RL_ROLE_MENU WHERE id = #{id}")
	void delRoleMenuById(String id);
	
	/**
	  * <p>根据角色ID删除记录</p>
	  * @Description:
	  * @param roleId
	 */
	@Delete("DELETE FROM RL_ROLE_MENU WHERE ROLEID = #{roleId}")
	void delRoleMenuByRoleId(String roleId);
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param roleMenu
	 */
	@Insert("insert into RL_ROLE_MENU(ID,ROLEID,MENUID,OPTTIME) " +
			                  "values(#{id},#{roleId},#{menuId},#{optTime})")
	void save(RoleMenu roleMenu);
	
	/**
	  * <p>根据角色id查找角色菜单</p>
	  * @Description:
	  * @param roleId
	  * @return
	 */
	@Select("SELECT * FROM RL_ROLE_MENU WHERE ROLEID = #{roleId}")
	List<RoleMenu> findByRoleId(String roleId);

	/**
	  * <p>根据角色id查找角色菜单Map列表</p>
	  * @Description:
	  * @param roleId 角色ID
	  * @return
	 */
	List<RoleMenu> findMapsByRoleId(String roleId);
	
}
