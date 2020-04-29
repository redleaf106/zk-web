package cn.org.bjca.zk.platform.dao;

import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.platform.web.page.EmployeePage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@MyBatisRepository
public interface EmployeeDao {
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param employee
	 */
	void save(Employee employee);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param employee
	 */
	void update(Employee employee);

	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	Employee findUniqueById(String id);
	
	/**
	  * <p>根据CardNumber查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_EMPLOYEE WHERE ICCARDNUMBER = #{cardNumber}")
	List<Employee> findEmployeesByCardId(String cardNumber);
	
	
	/**
	  * <p>根据DEPARTMENTID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_EMPLOYEE WHERE DEPARTMENTID = #{id}")
	List<Employee> findEmployeesByDepartmentId(String id);
	
	/**
	  * <p>根据DEPARTMENTID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_EMPLOYEE WHERE ICCARDNUMBER = #{icCardNumber} ORDER BY OPTTIME DESC")
	List<Employee> findEmployeesByIcCardNumber(String icCardNumber);
	
	/**
	  * <p>删除员工对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Delete("DELETE FROM BO_EMPLOYEE WHERE id = #{id}")
	void delEmployeeById(String id);

	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */

	List<Employee> findPage(EmployeePage<Employee> webPage);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_EMPLOYEE")
	List<Employee> getAll();

	/**
	 * <p>查询所有未激活员工</p>
	 * @return
	 */
	List<Employee> getAllNotActive(EmployeePage<Employee> webPage);

	/**
	 * 根据ic卡号查询员工
	 * @param icCardNumber
	 * @return
	 */
	@Select("SELECT * FROM BO_EMPLOYEE WHERE ICCARDNUMBER = #{icCardNumber}")
	Employee findByIcCardNumber(String icCardNumber);

	@Select("SELECT * FROM BO_EMPLOYEE WHERE EMPLOYEENUMBER = #{employeeNumber}")
	Employee findByEmployeeNumber(String employeeNumber);


}
