package cn.org.bjca.zk.platform.web.page;

import java.io.Serializable;
import java.util.List;

import com.cn.bjca.seal.esspdf.core.pagination.page.Page;

/***************************************************************************
 * <pre>分页条件基类</pre>
 * @文件名称:  BasePage.java
 * @包   路   径：  cn.org.bjca.zk.platform.web.page
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V1.0
 * @创建人： wangwenc
 * @创建时间：2013-2-12 下午4:16:09
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
public abstract class BasePage<T> implements Serializable {

	private static final long serialVersionUID = 7711635979781412204L;
	
	private Page pageVO;//分页属性类
	
	private List<T> data;//查询的列表数据

	public Page getPageVO() {
		return pageVO;
	}

	public void setPageVO(Page pageVO) {
		this.pageVO = pageVO;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
