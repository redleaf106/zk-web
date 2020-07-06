package cn.org.bjca.zk.db.entity;


/**
 * 视频及审计实体
 * FileName: cn.org.bjca.zk.db.entity.VideoAudit
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 13:19
 * Version: 1.0
 */
public class VideoAudit extends  IdEntity{

	private static final long serialVersionUID = -625072672846532744L;
	/**
     * 机柜号码
     */
    private Cabinet cabinet;
    /**
     * 柜门号码
     */
    private CabinetDoor cabinetDoor;
    /**
     * 员工姓名、员工编号
     */
    private Employee employee;
    /**
     * 部门
     */
    private Department department;
    /**
     * 状态
     */
    private String status;
    
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
