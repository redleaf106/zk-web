package cn.org.bjca.zk.db.entity;

import java.sql.Timestamp;

/**
    * 巡检审计日志实体
 * FileName: cn.org.bjca.zk.db.entity.UnprocessAudit
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 12:05
 * Version: 1.0
 */
public class InprocessAudit extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8598327647351953853L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 巡检时间
	 */
	private Timestamp inprocessTime;
	
	/**
	 * 巡检人（录入人）
	 */
	private String userId;
	
	/**
	 * 被巡检人
	 */
	private String employeeId;
	/**
	 * 巡查人
	 */
	private User user;
	/**
	 * 被巡检人
	 */
	private Employee employee;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getInprocessTime() {
		return inprocessTime;
	}

	public void setInprocessTime(Timestamp inprocessTime) {
		this.inprocessTime = inprocessTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
