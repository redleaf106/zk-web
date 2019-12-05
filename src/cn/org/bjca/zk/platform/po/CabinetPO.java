/**
 * 
 */
package cn.org.bjca.zk.platform.po;

import java.io.Serializable;


/***************************************************************************

 * @文件名称: CabinetPo.java
 * @包   路   径： cn.org.bjca.zk.platform.po
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年12月2日
 ***************************************************************************/
public class CabinetPO implements  Serializable{

	private static final long serialVersionUID = -2028317529328808197L;
	
	private String cabinetIp;
	
	private String cabinetNumber;
	
	public String getCabinetIp() {
		return cabinetIp;
	}
	public void setCabinetIp(String cabinetIp) {
		this.cabinetIp = cabinetIp;
	}
	public String getCabinetNumber() {
		return cabinetNumber;
	}
	public void setCabinetNumber(String cabinetNumber) {
		this.cabinetNumber = cabinetNumber;
	}

	
}
