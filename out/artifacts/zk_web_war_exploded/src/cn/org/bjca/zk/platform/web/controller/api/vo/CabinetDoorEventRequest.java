/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.api.vo;

/***************************************************************************

 * @文件名称: CabinetDoorEventRequest.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.api.vo
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
public class CabinetDoorEventRequest extends BaseRequest{

	private static final long serialVersionUID = -5199502131826883389L;

	/**
	 * 开门的员工id
	 */
	private String accountPKId;
	
	
	/**
	 * 机柜编号
	 */
	private String machineNumber;
	
	/**
	 * 柜门号，对应柜门号1~60
	 */
	private String doorId;
	/**
	 * 动作状态: 0正常开门 1 正常关门 2存件晚 3紧急取件  4未存
	 */
	private String status;
	/**
	 * 事件发生时间
	 */
	private String actionTime;
	/**
	 * 实际开门人id
	 */
	private String actionUserId;
	
	/**
	 * 开门备注
	 */
	private String remark;

	public String getAccountPKId() {
		return accountPKId;
	}

	public void setAccountPKId(String accountPKId) {
		this.accountPKId = accountPKId;
	}


	public String getMachineNumber() {
		return machineNumber;
	}

	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}

	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActionTime() {
		return actionTime;
	}

	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}

	public String getActionUserId() {
		return actionUserId;
	}

	public void setActionUserId(String actionUserId) {
		this.actionUserId = actionUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	} 
	
	
	
}
