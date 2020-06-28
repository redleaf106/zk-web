package cn.org.bjca.zk.db.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
   *  机柜门事件实体
 * FileName: cn.org.bjca.zk.db.entity.CabinetDoorEvnet
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 12:05
 * Version: 1.0
 */
public class CabinetDoorEvent extends IdEntity {

	private static final long serialVersionUID = 7366604581739614154L;
	
	/**
	 * 正常存
	 */
	public static final String NORMAL_PUSH  = "0" ;
	/**
	 * 正常取
	 */
	public static final String NORMAL_PULL  = "1" ;
			
	/**
	 * 晚存件
	 */
	public static final String LATE_STORE  = "2" ;
	
	/**
	 * 早取件
	 */
	public static final String ERRLY_STORE  = "3" ;
	
	/**
	 * 紧急开门
	 */
	public static final String EMERGENCY_FETCH  = "4" ;
	
	
	/**
	 * 柜门编号
	 */
	private String cabinetDoorNumber;
	/**
	 * 机柜
	 */
	private String cabinetNumber;
	
	/**
	 * 机柜ip
	 */
	private String cabinetIp;
	
	/**
	 * 动作状态：0正常存 1 正常取 2晚存 3早取  4紧急
	 *    
	 */
	private String status;

	/**
	 * 事件发生时间
	 */
	
	private Timestamp  doorOptTime = new Timestamp(new Date().getTime());
	
	/**
	 * 开门员工工号
	 */
	private String employeeCardNumber;

	/**
	 * 开门卡号
	 */
	
	private String actionCardNumber;
	
	/**
	 * 备注
	 */
	private String remark;

	//临时的
	private int temporaryStatus;

	public int getTemporaryStatus() {
		return temporaryStatus;
	}

	public void setTemporaryStatus(int temporaryStatus) {
		this.temporaryStatus = temporaryStatus;
	}

	public String getCabinetDoorNumber() {
		return cabinetDoorNumber;
	}

	public void setCabinetDoorNumber(String cabinetDoorNumber) {
		this.cabinetDoorNumber = cabinetDoorNumber;
	}

	public String getCabinetNumber() {
		return cabinetNumber;
	}

	public void setCabinetNumber(String cabinetNumber) {
		this.cabinetNumber = cabinetNumber;
	}

	
	public String getCabinetIp() {
		return cabinetIp;
	}

	public void setCabinetIp(String cabinetIp) {
		this.cabinetIp = cabinetIp;
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


	public String getEmployeeCardNumber() {
		return employeeCardNumber;
	}

	public void setEmployeeCardNumber(String employeeCardNumber) {
		this.employeeCardNumber = employeeCardNumber;
	}

	public String getActionCardNumber() {
		return actionCardNumber;
	}

	public void setActionCardNumber(String actionCardNumber) {
		this.actionCardNumber = actionCardNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
