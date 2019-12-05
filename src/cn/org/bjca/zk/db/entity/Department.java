package cn.org.bjca.zk.db.entity;

import java.util.ArrayList;
import java.util.List;


/**
    *   部门实体对象
 * FileName: cn.org.bjca.zk.db.entity.Department
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 12:05
 * Version: 1.0
 */

public class Department extends IdEntity {
	
	private static final long serialVersionUID = -7053792493582557891L;

	/**
	 * 部门名称
	 */
	private String departmentName;

	/**
	 * 部门编号
	 */
	private String departmentNumber;
	
	
	private List<String> startTime = new ArrayList<String>();
	
	private List<String> endTime  = new ArrayList<String>();;
	

	private List<TimeArea> timeAreas = new ArrayList<TimeArea>();
	
	

	public List<String> getStartTime() {
		return startTime;
	}

	public void setStartTime(List<String> startTime) {
		this.startTime = startTime;
	}

	public List<String> getEndTime() {
		return endTime;
	}

	public void setEndTime(List<String> endTime) {
		this.endTime = endTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public List<TimeArea> getTimeAreas() {
		return timeAreas;
	}

	public void setTimeAreas(List<TimeArea> timeAreas) {
		this.timeAreas = timeAreas;
	}

}
