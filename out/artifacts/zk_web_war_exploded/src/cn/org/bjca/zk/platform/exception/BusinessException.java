/**
 * 
 */
package cn.org.bjca.zk.platform.exception;

import cn.org.bjca.zk.platform.ResultEnum;

/***************************************************************************

 * @文件名称: BusinessException.java
 * @包   路   径： cn.org.bjca.zk.platform.exception
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
public class BusinessException extends Exception{
	
	private static final long serialVersionUID = -9006609818803840231L;
	
	private String code;
	
	private String message;
	
	
	public BusinessException(ResultEnum resultEnum) {
		super();
		this.code = resultEnum.getCode();
		this.message = resultEnum.getMessage();
		
	}

	public BusinessException(Throwable cause) {
		super(cause);
		this.code = ResultEnum.UNKOWN_ERROR.getCode();
		this.message = cause.getCause().getMessage();
	}
	
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
