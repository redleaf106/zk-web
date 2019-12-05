package cn.org.bjca.zk.platform.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.Cabinet;
import cn.org.bjca.zk.db.entity.CabinetDoor;
import cn.org.bjca.zk.db.entity.Department;
import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.platform.ResultEnum;
import cn.org.bjca.zk.platform.dao.CabinetDao;
import cn.org.bjca.zk.platform.dao.CabinetDoorDao;
import cn.org.bjca.zk.platform.dao.DepartmentDao;
import cn.org.bjca.zk.platform.dao.EmployeeDao;
import cn.org.bjca.zk.platform.exception.BusinessException;
import cn.org.bjca.zk.platform.po.CabinetDoorPO;
import cn.org.bjca.zk.platform.po.CabinetPO;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.controller.api.vo.EmployeeRegisterRequest;
import cn.org.bjca.zk.platform.web.page.EmployeePage;

/***************************************************************************
 * <pre></pre>
 ***************************************************************************/

//Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class EmployeeService {
	/**
	 * 员工dao
	 */
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CabinetDoorDao cabinetDoorDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private CabinetDao cabinetDao; 
	
	/**
	 * 员工注册
	 * @param employeeRegisterRequest
	 * @throws BusinessException
	 */
	@Transactional(readOnly = false)
	public void regist(EmployeeRegisterRequest employeeRegisterRequest) throws BusinessException {
		
		if(null==employeeRegisterRequest.getMachineNumber()||"".equals(employeeRegisterRequest.getMachineNumber())) {
			throw new BusinessException(ResultEnum.VERFIIY_DEPARTMENT_ERROR);
		}
		//根据部门名称 判断部门是否存在，不存在则抛出异常。
		List<Department> departmentList = departmentDao.findByDepartmentName(employeeRegisterRequest.getDepartment());
		Department department = null;
		if(null!=departmentList && departmentList.size()>0) {
			department = departmentList.get(0);
		}else {
			throw new BusinessException(ResultEnum.VERFIIY_DEPARTMENT_ERROR);
		}
		
		
		//根据工卡判断员工是否存在，存在则更新员工信息，不存在则新建员工信息。
		List<Employee> employeeList = employeeDao.findEmployeesByIcCardNumber(employeeRegisterRequest.getAccountPkId());
		Employee employee  = null;
		if(null!=employeeList && !employeeList.isEmpty()) {
			employee = employeeList.get(0);
			employee.setEmployeeName(employeeRegisterRequest.getName());
			employee.setDepartmentId(department.getId());
			employee.setEmail(employeeRegisterRequest.getEmail());
			employee.setIcCardNumber(employeeRegisterRequest.getAccountPkId());
			employee.setMobilePhone(employeeRegisterRequest.getTel());
			if(null!=employeeRegisterRequest.getImage()) {
				employee.setPicFile(Base64.decode(employeeRegisterRequest.getImage()));
			}
			employee.setOptTime(new Timestamp(new java.util.Date().getTime()));
			employeeDao.update(employee);
		}else{
			employee = new Employee();
			employee.setId(EssPdfUtil.genrRandomUUID());
			employee.setEmployeeName(employeeRegisterRequest.getName());
			employee.setDepartmentId(department.getId());
			employee.setEmail(employeeRegisterRequest.getEmail());
			employee.setEmployeeName("");
			employee.setEmployeeNumber(EssPdfUtil.genrRandomUUID());
			employee.setIcCardNumber(employeeRegisterRequest.getAccountPkId());
			employee.setMobilePhone(employeeRegisterRequest.getTel());
			if(null!=employeeRegisterRequest.getImage()) {
				employee.setPicFile(Base64.decode(employeeRegisterRequest.getImage()));
			}
			employee.setOptTime(new Timestamp(new java.util.Date().getTime()));
			employeeDao.save(employee);
			
		}
		
		if(null==employeeRegisterRequest.getMachineNumber()||"".equals(employeeRegisterRequest.getMachineNumber())) {
			throw new BusinessException(ResultEnum.VERFITY_CABINET_ERROR);
		}
		//根据机柜编号判断机柜是否存在，不存在则抛出异常。
		CabinetPO cabinetPO = new CabinetPO();
		cabinetPO.setCabinetNumber(employeeRegisterRequest.getMachineNumber());
		List<Cabinet> cabinetList = cabinetDao.findByCondition(cabinetPO);
		Cabinet cabinet = null;
		if(null!=cabinetList && !cabinetList.isEmpty()) {
			cabinet = cabinetList.get(0);
		}else {
			throw new BusinessException(ResultEnum.VERFITY_CABINET_ERROR);
		}
		 
		//根据机柜编号和柜门编号判断是否已存在授权的柜门机柜信息，存在则更新柜门授权信息，不存在则新建柜门授权信息。
		CabinetDoorPO cabinetDoorPO = new CabinetDoorPO();
		cabinetDoorPO.setCabinetNumber(employeeRegisterRequest.getMachineNumber());
		cabinetDoorPO.setCabinetDoorNumber(employeeRegisterRequest.getDoorId());
		List<CabinetDoor> cabinetDoorList = cabinetDoorDao.findByCondition(cabinetDoorPO);
		CabinetDoor cabinetDoor = null;
		if(null!=cabinetDoorList && cabinetDoorList.size()>0) {
			cabinetDoor = cabinetDoorList.get(0);
			cabinetDoor.setAccessCount(0);
			cabinetDoor.setEmployeeId(employee.getId());
			cabinetDoor.setOptTime(new Timestamp(new java.util.Date().getTime()));
			cabinetDoorDao.update(cabinetDoor);
		}else {
			cabinetDoor = new CabinetDoor();
			cabinetDoor.setId(EssPdfUtil.genrRandomUUID());
			cabinetDoor.setCabinetDoorNumber(employeeRegisterRequest.getDoorId());
			cabinetDoor.setCabinetId(cabinet.getId());
			cabinetDoor.setEmployeeId(employee.getId());
			cabinetDoor.setOptTime(new Timestamp(new java.util.Date().getTime()));
			cabinetDoorDao.save(cabinetDoor);
		}
		
	}
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param employee
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(Employee employee){
		if(StringUtils.isNotBlank(employee.getId())){//id不为空时
			employeeDao.update(employee);
		}else{
			employee.setId(EssPdfUtil.genrRandomUUID());
			employeeDao.save(employee);
		}
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public Employee findUniqueById(String id){
		return employeeDao.findUniqueById(id);
	}
	
	/**
	  * <p>根据员工编号部门ID查询对象集合</p>
	  * @Description:
	  * @param orgId
	  * @return
	 */
	public List<Employee> findEmployeesByCardNumber(String cardNumber){
		return employeeDao.findEmployeesByCardId(cardNumber);
	}
	
	/**
	  * <p>根据部门ID查询对象集合</p>
	  * @Description:
	  * @param departmentId
	  * @return
	 */
	public List<Employee> findEmployeesByDepartmentId(String departmentId){
		return employeeDao.findEmployeesByDepartmentId(departmentId);
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delEmployeeById(String id){
		employeeDao.delEmployeeById(id);
	}

	/**
	  * <p>分页查询</p>
	  * @Description:
	  * @param employeePage
	  * @return
	 */
	public EmployeePage<Employee> findPage(EmployeePage<Employee> employeePage) {
		List<Employee> list = employeeDao.findPage(employeePage);
		employeePage.setData(list);
		return employeePage;
	}
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	public List<Employee> getAll() {
		return employeeDao.getAll();
	}


}
