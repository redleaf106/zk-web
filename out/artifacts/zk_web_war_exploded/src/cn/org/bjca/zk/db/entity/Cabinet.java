
package cn.org.bjca.zk.db.entity;

import java.util.List;

/**
   * 机柜实体
 * FileName: cn.org.bjca.zk.db.entity.Cabinet
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 12:05
 * Version: 1.0
 */
public class Cabinet extends IdEntity {

	private static final long serialVersionUID = -7965600159474929390L;
	
	/**
	 * 机柜编号
	 */
	private String cabinetNumber;
	/**
	 * 机柜位置
	 */
	private String cabinetPosition;
	/**
	 * 机柜位置详情
	 */
	private String cabinetPositionDetail;
	/**
	 * 机柜ip
	 */
	private String cabinetIP;
	/**
	 * 软件版本
	 */
	private String software;
	/**
	 * 柜门数量
	 */
	private int doorCount;
	/**
	 * 正在使用的柜门数量
	 */
	private int fullDoorCount;
	/**
	 * 左摄像头ip
	 */
	private String leftCameraIP;
	/**
	 * 前摄像头ip
	 */
	private String frontCameraIP;
	/**
	 * 右摄像头ip
	 */
	private String rightCameraIP;
	
	/**
	 * 柜门列表
	 */
	private List<CabinetDoor> cabinetDoorList;
	
	public String getCabinetNumber() {
		return cabinetNumber;
	}
	public void setCabinetNumber(String cabinetNumber) {
		this.cabinetNumber = cabinetNumber;
	}
	public String getCabinetPosition() {
		return cabinetPosition;
	}
	public void setCabinetPosition(String cabinetPosition) {
		this.cabinetPosition = cabinetPosition;
	}
	public String getCabinetPositionDetail() {
		return cabinetPositionDetail;
	}
	public void setCabinetPositionDetail(String cabinetPositionDetail) {
		this.cabinetPositionDetail = cabinetPositionDetail;
	}
	public String getCabinetIP() {
		return cabinetIP;
	}
	public void setCabinetIP(String cabinetIP) {
		this.cabinetIP = cabinetIP;
	}
	public String getSoftware() {
		return software;
	}
	public void setSoftware(String software) {
		this.software = software;
	}
	public int getDoorCount() {
		return doorCount;
	}
	public void setDoorCount(int doorCount) {
		this.doorCount = doorCount;
	}
	
	public String getLeftCameraIP() {
		return leftCameraIP;
	}
	public void setLeftCameraIP(String leftCameraIP) {
		this.leftCameraIP = leftCameraIP;
	}
	public String getFrontCameraIP() {
		return frontCameraIP;
	}
	public void setFrontCameraIP(String frontCameraIP) {
		this.frontCameraIP = frontCameraIP;
	}
	public String getRightCameraIP() {
		return rightCameraIP;
	}
	public void setRightCameraIP(String rightCameraIP) {
		this.rightCameraIP = rightCameraIP;
	}
	public List<CabinetDoor> getCabinetDoorList() {
		return cabinetDoorList;
	}
	public void setCabinetDoorList(List<CabinetDoor> cabinetDoorList) {
		this.cabinetDoorList = cabinetDoorList;
	}

	public int getFullDoorCount() {
		return fullDoorCount;
	}

	public void setFullDoorCount(int fullDoorCount) {
		this.fullDoorCount = fullDoorCount;
	}
}
