package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.*;
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
import cn.org.bjca.zk.platform.web.page.AssistantPage;
import cn.org.bjca.zk.platform.web.page.EmployeePage;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

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
			employee.setOptTime(new Timestamp(new Date().getTime()));
			employeeDao.update(employee);
		}else{
			employee.setId(EssPdfUtil.genrRandomUUID());
			employee.setIcCardNumber(createEmployeeIcCardNumber());
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
	 * @param cardNumber
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

	@Transactional(readOnly = false)
	public void delAssistantId(String id){
		employeeDao.delAssistantId(id);
	}

	/**
	 * <p>分页查询</p>
	 * @Description:
	 * @param employeePage
	 * @return
	 */
	public EmployeePage<Employee> findPage(EmployeePage<Employee> employeePage) {
		List<Employee> list = employeeDao.findPage(employeePage);
		//System.out.println(list);
		employeePage.setData(list);
		return employeePage;
	}

	public EmployeePage<Employee> findPageShixisheng(EmployeePage<Employee> employeePage) {
		List<Employee> list = employeeDao.findPageShixisheng(employeePage);
		employeePage.setData(list);
		return employeePage;
	}

	public AssistantPage<Assistant> findAssistantPage(AssistantPage<Assistant> assistantPage) {
		List<Assistant> list = employeeDao.findAssistantPage(assistantPage);
		assistantPage.setData(list);
		return assistantPage;
	}


	public EmployeePage<Employee> getAllNotActive(EmployeePage<Employee> employeePage, String ip) {
//		根据ip地址查看城市
		Cabinet cityCabinet = cabinetDao.findByIP(ip);
		if (cityCabinet==null){
			return null;
		}
		CabinetPO cabinetPO = new CabinetPO();
		cabinetPO.setCabinetIp(ip);
		List<Cabinet> cabinetList = cabinetDao.findByCondition(cabinetPO);
		if(cabinetList==null||cabinetList.isEmpty()){
			return null;
		}
		List<CabinetDoor> cabinetDoorList = new ArrayList<>();
//		循环机柜获取所有柜门
		for(Cabinet cabinet:cabinetList){
//			CabinetDoorPO cabinetDoorPO = new CabinetDoorPO();
//			cabinetDoorPO.setCabinetNumber(cabinet.getCabinetNumber());
//			List<CabinetDoor> doorList = cabinetDoorDao.findByCondition(cabinetDoorPO);
			List<CabinetDoor> doorList = cabinetDoorDao.findByCabinetID(cabinet.getId());
			cabinetDoorList.addAll(doorList);
		}
		List<String> list = new ArrayList<>();
//		循环柜门获取员工id
		for(CabinetDoor cabinetDoor:cabinetDoorList){
			list.add(cabinetDoor.getEmployeeId());
		}
		List<Employee> employeeList = new ArrayList<>();
//		循环员工id获取所有未激活员工
		for(String s:list){
			Employee employee = employeeDao.findUniqueById(s);
			if(employee!=null){
				employeeList.add(employee);
			}
		}
//        List<Employee> list = employeeDao.getAllNotActive(employeePage);
		employeePage.setData(employeeList);
		return employeePage;
	}

	public AssistantPage<Assistant> getAllAssistantNotActive(AssistantPage<Assistant> assistantPage) {

		List<Assistant> list = employeeDao.getAllNoActiveAssistant();
		assistantPage.setData(list);
		return assistantPage;
	}

	/**
	 * <p>查询所有列表</p>
	 * @Description:
	 * @return
	 */
	public List<Employee> getAll() {
		return employeeDao.getAll();
	}

	@Transactional(readOnly = false)
	public int clearFaceData(String id){
		return employeeDao.clearFaceData(id);
	}

	@Transactional(readOnly = false)
	public int clearAssistantFaceData(String id){
		return employeeDao.clearAssistantFaceData(id);
	}

	/**
	 * 根据ic卡号查询员工
	 * @param icCardNumber
	 * @return
	 */
	public Employee findByicCardNumber(String icCardNumber){
		return employeeDao.findByIcCardNumber(icCardNumber);
	}

	public List<Employee> findAllNoDoor(){
		List<Employee> list = employeeDao.getAll();
		List<Employee> listResult = new ArrayList<>();
		for(Employee employee:list){
			List<CabinetDoor> cabinetDoorList = cabinetDoorDao.selectDoorByEmployeeId(employee.getId());
			if(cabinetDoorList==null||cabinetDoorList.size()==0){
				listResult.add(employee);
			}
		}
		return listResult;
	}

	@Transactional(readOnly = false)
	public Employee findByEmployeeNumber(String employeeNumber){
		return employeeDao.findByEmployeeNumber(employeeNumber);
	}

	//生成员工开门卡号
	public String createEmployeeIcCardNumber(){
		long no = new Date().getTime();
		Random random = new Random();
		int x = random.nextInt(899999);
		x = x+100000;
		return no+""+x;
	}

	public List<Assistant> getAllAssistantNoActive(String ip){
		Cabinet cityCabinet = cabinetDao.findByIP(ip);
		if (cityCabinet==null){
			return null;
		}
		List<CabinetDoor> cabinetDoorList = cabinetDoorDao.findByCabinetID(cityCabinet.getId());

		if(cabinetDoorList==null||cabinetDoorList.size()==0){
			return null;
		}
		List<String> listEMP = new ArrayList<>();
//		循环柜门获取员工id
		for(CabinetDoor cabinetDoor:cabinetDoorList){
			listEMP.add(cabinetDoor.getEmployeeId());
		}
		List<Assistant> assistantList = new ArrayList<>();
		//循环员工找有助理的人员
		for(String s:listEMP){
			Assistant assistant = employeeDao.findAssistantByLeaderId(s);
			if(assistant!=null&&assistant.getPicFile()==null){
				assistant.setCabinet(cityCabinet);
				Employee LeaderEMP = employeeDao.findUniqueById(s);
				LeaderEMP.setDepartment(departmentDao.findUniqueById(LeaderEMP.getDepartmentId()));
				assistant.setLeaderEmployee(LeaderEMP);
				assistant.setCabinetDoor(cabinetDoorDao.findByCabinetIDAndEmployeeID(cityCabinet.getId(),s));
				assistantList.add(assistant);
			}
		}
		return assistantList;
	}

	public Assistant findAssistantByLeaderId(String leaderId){
		return employeeDao.findAssistantByLeaderId(leaderId);
	}

	public Assistant findAssistantById(String id){
		return employeeDao.findAssistantById(id);
	}

	@Transactional(readOnly = false)
	public int SaveOrUpdateAssistan(Assistant assistant){
		int result = 0;
		if(StringUtils.isNotBlank(assistant.getId())){//id不为空时
			result = employeeDao.updateAssistant(assistant);
		}else{
			assistant.setId(EssPdfUtil.genrRandomUUID());
			assistant.setIcCardNumber(createEmployeeIcCardNumber());
			result = employeeDao.insertAssistant(assistant);
		}
		System.out.println(result);
		return result;
	}

	public Assistant findAssistantByIcCardNumber(String icCardNumber){
		return employeeDao.findAssistantByIcCardNumber(icCardNumber);
	}

	public List<Map<String,String>> syncEmpAndDep(){
		return employeeDao.syncEmpAndDep();
	}

	public List<Map<String,String>> syncEmpAndDepByDepartmentNumber(String departmentNumber) {
		return employeeDao.syncEmpAndDepByDepartmentNumber(departmentNumber);
	}

}
