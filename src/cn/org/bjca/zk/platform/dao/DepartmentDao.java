/**
 * 
 */
package cn.org.bjca.zk.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.Department;
import cn.org.bjca.zk.platform.web.page.DepartmentPage;


/***************************************************************************

 * @文件名称: DepartmentDao.java
 * @包   路   径： cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/

@MyBatisRepository
public interface DepartmentDao {
	
	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */
	List<Department> findPage(DepartmentPage<Department> webPage);
	
	
	/**
	  * <p>查询所有组织机构列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_DEPARTMENT")
	List<Department> getAll();
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Delete("DELETE FROM BO_DEPARTMENT WHERE id = #{id}")
	void delDepartmentById(String id);
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param department
	 */
	void save(Department department);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param department
	 */
	void update(Department department);
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	Department findUniqueById(String id);
	
	
	/**
	  * <p>根据部门名称查询列表</p>
	  * @Description:
	  * @param sealName
	  * @return
	 */
	List<Department> findByDepartmentName(String departmentName);
	
	
}
