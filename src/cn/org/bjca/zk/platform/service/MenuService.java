package cn.org.bjca.zk.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.Menu;
import cn.org.bjca.zk.platform.dao.MenuDao;

/***************************************************************************
 * <pre>功能菜单服务</pre>
 * @文件名称:  MenuService.java
 * @包   路   径：  cn.org.bjca.zk.platform.service
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-27 上午11:41:00
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
public class MenuService {
	
	@Autowired
	private MenuDao menuDao;//功能菜单DAO
	
	/**
	  * <p>根据父节点id查询列表</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public List<Menu> getAllByParentId(String id) {
		return menuDao.getAllByParentId(id);
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public Menu findUniqueById(String id) {
		return menuDao.findUniqueById(id);
	}
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	public List<Menu> getAll() {
		return menuDao.getAll();
	}

	/**
	  * <p>获取所有父菜单列表</p>
	  * @Description:
	  * @return
	 */
	public List<Menu> getAllParentList() {
		return menuDao.getAllParentList();
	}
	
}
