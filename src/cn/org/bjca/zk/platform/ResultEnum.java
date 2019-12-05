/**
 * 
 */
package cn.org.bjca.zk.platform;

/***************************************************************************

 * @文件名称: ResultEnum.java
 * @包   路   径： cn.org.bjca.zk.platform
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
public enum ResultEnum {
	SUCCESS("0", "成功"),
	UNKOWN_ERROR("-1", "未知错误"),
	GET_CLIENTIP_ERROR("10001","获取客户端错误"),
	VERFIIY_DEPARTMENT_ERROR("10002","部门错误"),
	VERFITY_CABINET_ERROR("10003","机柜错误")
    ;
	
	private String code;
	private String message;
	
	private ResultEnum(String code,String message) {
        
        this.code = code;
        this.message = message;
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
