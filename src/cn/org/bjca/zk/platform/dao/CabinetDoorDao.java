/**
 * 
 */
package cn.org.bjca.zk.platform.dao;

import cn.org.bjca.zk.db.entity.CabinetDoor;
import cn.org.bjca.zk.platform.po.CabinetDoorPO;
import cn.org.bjca.zk.platform.web.page.CabinetDoorPage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/***************************************************************************

 * @文件名称: CabinetDoorDao.java
 * @包   路   径： cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/

@MyBatisRepository
public interface CabinetDoorDao {
	
	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */
	List<CabinetDoor> findPage(CabinetDoorPage<CabinetDoor> webPage);


	List<CabinetDoor> findPageyrdg(CabinetDoorPage<CabinetDoor> webPage);

	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Delete("DELETE FROM BO_CABINETDOOR WHERE id = #{id}")
	int delCabinetDoorById(String id);
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param cabinetDoor
	 */
	void save(CabinetDoor cabinetDoor);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param cabinetDoor
	 */
	void update(CabinetDoor cabinetDoor);
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	CabinetDoor findUniqueById(String id);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_CABINETDOOR")
	List<CabinetDoor> getAll();
	
	/**
	  * <p>根据条件查询柜门列表</p>
	  * @Description:
	  * @param cabinetPO
	  * @return
	 */
	List<CabinetDoor> findByCondition(CabinetDoorPO cabinetPO);

	/**
	 * 根据员工id查看柜门
	 */
	@Select("SELECT * FROM BO_CABINETDOOR WHERE employeeid = #{employeeId}")
	List<CabinetDoor> selectDoorByEmployeeId(String employeeId);

	/**
	 * 根据机柜id和柜门号查找柜门
	 * @param cabinetId
	 * @param cabinetDoorNumber
	 * @return
	 */
	CabinetDoor selectDoorByCabinetIdAndCabinetDoorNumber(String cabinetId,String cabinetDoorNumber);

	//根据机柜id查看所有柜门
	@Select("SELECT * FROM BO_CABINETDOOR WHERE CABINETID = #{0}")
	List<CabinetDoor> findByCabinetID(String cabinetID);

	//根据机柜id和员工id查看单一柜门
	@Select("SELECT * FROM BO_CABINETDOOR WHERE CABINETID = #{0} AND EMPLOYEEID = #{1}")
	CabinetDoor findByCabinetIDAndEmployeeID( String cabinetID, String employeeID);


}
