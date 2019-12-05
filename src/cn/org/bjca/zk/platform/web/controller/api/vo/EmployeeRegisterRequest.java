/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.api.vo;

/***************************************************************************

 * @文件名称: EmployeeRegisterRequest.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.api.vo
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
public class EmployeeRegisterRequest extends BaseRequest{

	private static final long serialVersionUID = 6451834914092381251L;

	/**
	 *开门对应的ID   ，员工ID，对应工号
	 */
	private String accountPkId;
	
	/**
	 * 员工姓名
	 */
	private String name;
	
	private String machineNumber;
	
	/**
	 *  柜门号    对应柜门号1~60
	 */
	private String doorId;
	/**
	 *部门名称
	 */
	private String department;
	
	/**
	 * 事件发生时间
	 */
	private String time;
	
	/**
	 * 电话
	 */
	private String tel;
	
	/**
	 * 员工邮箱
	 */
	private String email;
	/**
	 * 员工人脸图片
	 */
	private String image;
	
	public String getAccountPkId() {
		return accountPkId;
	}
	public void setAccountPkId(String accountPkId) {
		this.accountPkId = accountPkId;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
