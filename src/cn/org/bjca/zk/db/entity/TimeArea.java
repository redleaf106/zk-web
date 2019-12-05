package cn.org.bjca.zk.db.entity;


import java.sql.Timestamp;

/**
 * 工作时间段实体
 * FileName: cn.org.bjca.zk.db.cabinet.entity.TimeArea
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 13:50
 * Version: 1.0
 */
public class TimeArea extends IdEntity {
	
	private static final long serialVersionUID = -6558349880569366560L;

	/**
	 * 部门id
	 */
	private String departmentId;
	
    /**
     * 开始时间
     */
    private Timestamp startTime;
    /**
     * 结束时间
     */
    private Timestamp endTime;

    
    
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
    
}
