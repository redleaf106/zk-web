/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.api.vo;

import java.io.Serializable;

/***************************************************************************

 * @文件名称: BaseResponse.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.api.vo
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 4688044232526084890L;
	
	protected String code;
	
	protected String message;

	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
