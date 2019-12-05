package cn.org.bjca.zk.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.Menu;

/***************************************************************************
 * <pre></pre>
 * @文件名称:  MenuDao.java
 * @包   路   径：  cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-27 上午10:49:14
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
public interface MenuDao {
	
	/**
	  * <p>根据父节点id查询列表</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	List<Menu> getAllByParentId(String id);
	
	/**
	  * <p>根据父节点id与状态查询列表</p>
	  * @Description:
	  * @param parentid 父菜单id
	  * @param status 菜单状态
	  * @return
	 */
	List<Menu> getAllByParentIdAndStatus(@Param(value = "parentid") String parentid, @Param(value = "status") String status);

	
	/**
	  * <p>获取系统中所有非系统的menu记录，用于初始化授权</p>
	  * @Description:
	  * @param parentid 父菜单id
	  * @return
	 */
	List<Menu> getNoSystemMenuByParentIdAndStatus(@Param(value = "parentid") String parentid);

	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_MENU WHERE id = #{id}")
	Menu findUniqueById(String id);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_MENU")
	List<Menu> getAll();

	/**
	 * <p>获取所有父节点</p>
	 * @return
	 */
	@Select("SELECT * FROM BO_MENU where PARENTID = '-1' ORDER BY SHOWORDER")
	List<Menu>  getAllParentList();
	
	/**
	 * <p>更新菜单</p>
	 * @param menu
	 */
	void update(Menu menu);
}
