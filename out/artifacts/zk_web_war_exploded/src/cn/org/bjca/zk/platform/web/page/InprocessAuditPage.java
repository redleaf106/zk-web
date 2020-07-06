package cn.org.bjca.zk.platform.web.page;

import com.cn.bjca.seal.esspdf.core.pagination.annotation.Paging;

/***************************************************************************
 * <pre></pre>
 * @param <T>
 * @文件名称:  InprocessAudit.java
 * @包   路   径：  cn.org.bjca.zk.platform.web.page
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V1.0
 * @创建人： gaozhijiang
 * @创建时间：2019-11-28 13:19:20
 *
 
 ***************************************************************************/
@Paging(field = "pageVO")
public class InprocessAuditPage<T> extends BasePage<T> {
	
	
	private static final long serialVersionUID = -444037156007961611L;
	
	/**
	 * 员工姓名
	 */
	private String employeeName;
	
	/**
	 * 员工编号
	 */
	private String icCardNumber;

	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getIcCardNumber() {
		return icCardNumber;
	}

	public void setIcCardNumber(String icCardNumber) {
		this.icCardNumber = icCardNumber;
	}

	
	

}
