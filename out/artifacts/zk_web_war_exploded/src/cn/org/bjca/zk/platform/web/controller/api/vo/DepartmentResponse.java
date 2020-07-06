/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.api.vo;

import java.util.List;

import cn.org.bjca.zk.db.entity.Department;

/***************************************************************************

 * @文件名称: DepartmentResponse.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.api.vo
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年12月2日
 ***************************************************************************/
public class DepartmentResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7584414058432422801L;

	List<Department> deparatmentList;

	public List<Department> getDeparatmentList() {
		return deparatmentList;
	}

	public void setDeparatmentList(List<Department> deparatmentList) {
		this.deparatmentList = deparatmentList;
	}
	
}
