/**
 * 
 */
package cn.org.bjca.zk.platform.web.page;

import com.cn.bjca.seal.esspdf.core.pagination.annotation.Paging;

/***************************************************************************

 * @文件名称: CabinetDoorEventPage.java
 * @包   路   径： cn.org.bjca.zk.platform.web.page
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/

@Paging(field = "pageVO")
public class CabinetDoorEventPage  <T> extends BasePage<T> {

	private static final long serialVersionUID = -6183879852924320372L;
	
	/**
	 * 机柜编号
	 */
	private String cabinetNumber;
	
	/**
	    *  机柜 ip 
	 */
	private String cabinetIp;
	
	/**
	    *  机柜门id 
	 */
	private String cabinetDoorNumber;
	
	/**
	 * 员工卡号
	 */
	private String employeeCardNumber;
	
	/**
	 * 实际开门卡号
	 */
	private String actionCardNumber;

	private String employeeName;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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

	public String getCabinetDoorNumber() {
		return cabinetDoorNumber;
	}

	public void setCabinetDoorNumber(String cabinetDoorNumber) {
		this.cabinetDoorNumber = cabinetDoorNumber;
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

	

}
