package cn.org.bjca.zk.db.entity;

/**
    *     员工实体
 * FileName: cn.org.bjca.zk.db.entity.Employee
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 12:05
 * Version: 1.0
 */
public class Employee extends IdEntity {

	private static final long serialVersionUID = -4101967479347010353L;

	/**
	 * 员工姓名
	 */
	private String employeeName;

	/**
	 * 员工编号
	 */
	private String employeeNumber;

	/**
	 * ic卡号
	 */
	private String icCardNumber;

	/**
	 * 手机号码1
	 */
	private String mobilePhone;

	/**
	 * 手机号码2
	 */
	private String mobilePhone2;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 照片
	 */
	private byte[] picFile;
	
	/**
	  * 部门Id
	 */
	private String departmentId;

	/**
	 * 部门
	 */
	private Department department;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	
	public String getIcCardNumber() {
		return icCardNumber;
	}

	public void setIcCardNumber(String icCardNumber) {
		this.icCardNumber = icCardNumber;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhone2() {
		return mobilePhone2;
	}

	public void setMobilePhone2(String mobilePhone2) {
		this.mobilePhone2 = mobilePhone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPicFile() {
		return picFile;
	}

	public void setPicFile(byte[] picFile) {
		this.picFile = picFile;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	
}
