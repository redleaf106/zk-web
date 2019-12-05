/**
 * 
 */
package cn.org.bjca.zk.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.Cabinet;
import cn.org.bjca.zk.platform.po.CabinetPO;
import cn.org.bjca.zk.platform.web.page.CabinetPage;


/***************************************************************************

 * @文件名称: CabinetDao.java
 * @包   路   径： cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/

@MyBatisRepository
public interface CabinetDao {
	
	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */
	List<Cabinet> findPage(CabinetPage<Cabinet> webPage);
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Delete("DELETE FROM BO_CABINET WHERE id = #{id}")
	void delCabinetById(String id);
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param cabinet
	 */
	void save(Cabinet cabinet);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param cabinet
	 */
	void update(Cabinet cabinet);
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_CABINET WHERE id = #{id}")
	Cabinet findUniqueById(String id);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_CABINET")
	List<Cabinet> getAll();
	
	/**
	  * <p>根据条件查询机柜列表</p>
	  * @Description:
	  * @param cabinetPO
	  * @return
	 */
	List<Cabinet> findByCondition(CabinetPO cabinetPO);

}
