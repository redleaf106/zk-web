/**
 * 
 */
package cn.org.bjca.zk.platform.web.page;

import com.cn.bjca.seal.esspdf.core.pagination.annotation.Paging;

/***************************************************************************

 * @文件名称: DepartmentPage.java
 * @包   路   径： cn.org.bjca.zk.platform.web.page
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/

@Paging(field = "pageVO")
public class DepartmentPage  <T> extends BasePage<T> {

	private static final long serialVersionUID = -6183879852924320372L;
	
	/**
	 *  部门名称
	 */
	private String departmentName;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	
	
	

}
