package cn.org.bjca.zk.db.entity;


/**
 * 短信日志实体
 * FileName: cn.org.bjca.zk.db.entity.MessageLog
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 13:22
 * Version: 1.0
 */
public class MessageLog extends IdEntity {
	private static final long serialVersionUID = 5262239187135632124L;

	/**
     * 员工姓名、编号、手机号、部门
     */
    private Employee employee;

    /**
     * 部门
     */
    private Department department;

    /**
     * 机柜号码
     */
    private Cabinet cabinet;

    /**
     * 机柜门号码
     */
    private CabinetDoor cabinetDoor;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Cabinet getCabinet() {
		return cabinet;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}

	public CabinetDoor getCabinetDoor() {
		return cabinetDoor;
	}

	public void setCabinetDoor(CabinetDoor cabinetDoor) {
		this.cabinetDoor = cabinetDoor;
	}
    
    
}
