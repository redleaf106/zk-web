package cn.org.bjca.zk.platform.exception;
/***************************************************************************
 * <pre>窗口异常</pre>
 * @文件名称:  DialogException.java
 * @包   路   径：  cn.org.bjca.zk.platform.exception
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-23 下午9:09:15
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
public class DialogException extends Exception {

	private static final long serialVersionUID = -4351125230931282132L;

	public DialogException() {
		super();
	}

	public DialogException(String message, Throwable cause) {
		super(message, cause);
	}

	public DialogException(String message) {
		super(message);
	}

	public DialogException(Throwable cause) {
		super(cause);
	}

}
