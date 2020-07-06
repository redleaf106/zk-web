/**
 * 
 */
package cn.org.bjca.zk.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.TimeArea;



/***************************************************************************

 * @文件名称: TimeAreaDao.java
 * @包   路   径： cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/

@MyBatisRepository
public interface TimeAreaDao {
	
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Delete("DELETE FROM BO_CABINET WHERE id = #{id}")
	void delTimeAreaById(String id);
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param cabinet
	 */
	void save(TimeArea timeArea);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param cabinet
	 */
	void update(TimeArea timeArea);
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_TIMEAREA WHERE id = #{id}")
	TimeArea findUniqueById(String id);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_TIMEAREA")
	List<TimeArea> getAll();
	
	/**
	  * <p>根据部门Id查询列表</p>
	  * @Description:
	  * @param sealName
	  * @return
	 */
	@Select("SELECT * FROM BO_TIMEAREA WHERE DEPARTMENTID = #{departmentId}")
	List<TimeArea> findByDepartmentId(String departmentId);

	/**
	  * <p>根据部门id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Delete("DELETE FROM BO_TIMEAREA WHERE DEPARTMENTID = #{departmentId}")
	void delTimeAreaByDepartmentId(String departmentId);
	
	
	void batchInsert(List<TimeArea> list);
	
}
