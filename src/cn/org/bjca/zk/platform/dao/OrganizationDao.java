package cn.org.bjca.zk.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.Organization;
import cn.org.bjca.zk.platform.web.page.OrganizationPage;

/***************************************************************************
 * <pre>组织机构DAO</pre>
 * @文件名称:  OrganizationDao.java
 * @包   路   径：  cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-9 下午1:08:30
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
public interface OrganizationDao { 
	
	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */
	List<Organization> findPage(OrganizationPage<Organization> webPage);

	/**
	  * <p>查询所有组织机构列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_ORGANIZATION")
	List<Organization> getAll();
	
	/**
	  * <p>根据id查询唯一机构 </p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_ORGANIZATION WHERE ID = #{id}")
	Organization findUniqueById(String id);
	
	/**
	  * <p>根据id查询唯一机构map结果 </p>
	  * @Description:
	  * @param id
	  * @return
	 */
	Organization findUniqueOrgMapById(String id);
	
	/**
	  * <p>据当前机构id查询下级机构列表</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	List<Organization> findSubOrgListById(String id);
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param org
	 */
	void save(Organization org);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param org
	 */
	void update(Organization org);
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Select("DELETE FROM BO_ORGANIZATION WHERE id = #{id}")
	void delOrgById(String id);

}
