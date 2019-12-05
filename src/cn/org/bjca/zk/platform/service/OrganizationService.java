package cn.org.bjca.zk.platform.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.Organization;
import cn.org.bjca.zk.platform.dao.OrganizationDao;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.OrganizationPage;

/***************************************************************************
 * <pre>组织机构服务</pre>
 * @文件名称:  OrganizationService.java
 * @包   路   径：  cn.org.bjca.zk.platform.service
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-9 下午1:57:43
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
public class OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;//组织机构DAO
	
	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */
	public OrganizationPage<Organization> findPage(OrganizationPage<Organization> webPage) {
		List<Organization> list =organizationDao.findPage(webPage);
		webPage.setData(list);
		return webPage;
	}
	
	/**
	  * <p>查询所有组织机构列表</p>
	  * @Description:
	  * @return
	 */
	public List<Organization> getAll(){
		return organizationDao.getAll();
	}
	
	/**
	  * <p>根据id查询唯一机构 </p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public Organization findUniqueById(String id){
		return organizationDao.findUniqueById(id);
	}
	
	/**
	  * <p>根据id查询唯一机构map结果 </p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public Organization findUniqueOrgMapById(String id){
		return organizationDao.findUniqueOrgMapById(id);
	}
	
	/**
	  * <p>据当前机构id查询下级机构列表</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public List<Organization> findSubOrgListById(String id){
		return organizationDao.findSubOrgListById(id);
	}
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param org
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(Organization org){
		if(StringUtils.isNotBlank(org.getId())){//id不为空时
			organizationDao.update(org);
		} else {
			Organization orgObj = this.findUniqueById(org.getParentId());
			org.setNodeDepth(orgObj.getNodeDepth()+1);
			String id = EssPdfUtil.genrRandomUUID();
			org.setId(id);
			if(orgObj.getOrgFlag() != null)
				org.setOrgFlag(orgObj.getOrgFlag()+"##"+id);
			else
				org.setOrgFlag(id);
			organizationDao.save(org);
		}
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delOrgById(String id){
		organizationDao.delOrgById(id);
	}

}
