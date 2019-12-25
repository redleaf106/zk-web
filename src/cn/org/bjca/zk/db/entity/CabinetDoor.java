package cn.org.bjca.zk.db.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
   *  机柜门实体
 * FileName: cn.org.bjca.zk.db.entity.CabinetDoor
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 12:05
 * Version: 1.0
 */
public class CabinetDoor extends IdEntity {

	private static final long serialVersionUID = 7366604581739614154L;
	
	/**
	 * 存入
	 */
	public static final String STORE  = "1" ;
	/**
	 * 取出
	 */
	public static final String FETCH  = "2" ;
			
	
	/**
	 * 柜门编号
	 */
	private String cabinetDoorNumber;
	/**
	 * 机柜
	 */
	private String cabinetId;
	
	/**
	 * 使用人(员工）id
	 */
	private String employeeId;

	/**
	 * 存取次数
	 */
	private int accessCount;
	/**
	 * 使用状态
	 */
	private String status;
	
	private Timestamp  doorOptTime = new Timestamp(new Date().getTime());// 操作时间
	
	
	/**
	 * 使用人(员工）
	 */
	private Employee employee;
	
	/**
	 * 机柜实体
	 */
	private Cabinet cabinet;

	public String getCabinetDoorNumber() {
		return cabinetDoorNumber;
	}

	public void setCabinetDoorNumber(String cabinetDoorNumber) {
		this.cabinetDoorNumber = cabinetDoorNumber;
	}

	
	public String getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Timestamp getDoorOptTime() {
		return doorOptTime;
	}

	public void setDoorOptTime(Timestamp doorOptTime) {
		this.doorOptTime = doorOptTime;
	}

	public Cabinet getCabinet() {
		return cabinet;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}

	@Override
	public String toString() {
		return "CabinetDoor{" +
				"cabinetDoorNumber='" + cabinetDoorNumber + '\'' +
				", cabinetId='" + cabinetId + '\'' +
				", employeeId='" + employeeId + '\'' +
				", accessCount=" + accessCount +
				", status='" + status + '\'' +
				", doorOptTime=" + doorOptTime +
				", employee=" + employee +
				", cabinet=" + cabinet +
				'}';
	}
}
