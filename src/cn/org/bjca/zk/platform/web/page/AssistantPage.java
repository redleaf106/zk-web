package cn.org.bjca.zk.platform.web.page;

import com.cn.bjca.seal.esspdf.core.pagination.annotation.Paging;

/***************************************************************************
 * <pre></pre>
 * @param <T>
 * @文件名称:  EmployeePage.java
 * @包   路   径：  cn.org.bjca.zk.platform.web.page
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人：gaozhijiang
 * @创建时间：2019-11-27 
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 ***************************************************************************/
@Paging(field = "pageVO")
public class AssistantPage<T> extends BasePage<T> {
	
	
	private static final long serialVersionUID = -444037156007931311L;
	
	/**
	 * 助理姓名
	 */
	private String assistantName;

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}
	

	

}
