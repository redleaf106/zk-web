package cn.org.bjca.zk.platform.web.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.utils.JsonMapper;

/***************************************************************************
 * <pre>基类Controller</pre>
 ***************************************************************************/
public abstract class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());//日志
	
	protected  final String SUCCESS = "200"; //操作成功
	
	protected  final String FAIL = "300"; //操作失败
	
	protected  final String TIMEOUT = "301"; //会话超时
	
	protected  final String SAVE = "信息添加成功！"; 
	
	protected  final String SAVE_ERROR = "信息添加失败！"; 
	
	protected  final String UPDATE = "信息修改成功！"; 
	
	protected  final String UPDATE_ERROR = "信息修改失败！"; 
	
	protected  final String DELETE = "信息删除成功！";

	protected final String OPENDOOR = "柜门开启成功！";

	protected final String OCCUPIED = "柜门被占用！";

	protected final String DOORFULL = "柜门已达上限!";
	
	protected  final String DELETE_ERROR = "信息删除失败！"; 
	
	protected static final String BLANK_PARAM_VALUE = "-1"; // 空参数测试

	protected static final String ROOT_MENU_TYPE = "-1"; // 功能菜单根标识

	protected static final String MENU_STATUS_NOTAUTH = "0"; // 未授权子菜单

	protected static final String MENU_STATUS_AUTH = "1"; // 授权子菜单

	protected static final String MENU_STATUS_CUSTOM = "2"; // 表示自定义菜单标识
	
	protected static final String CONTENT_DEFAULT_CHARSET = "charset=UTF-8"; // license环境变量产品序列号
	
	public static final String CONTENT_TYPE_MSDOWN = "application/x-msdownload; " + CONTENT_DEFAULT_CHARSET; // HTTP响应mime类型

	public static final String CONTENT_TYPE_PLAIN = "text/plain; " + CONTENT_DEFAULT_CHARSET; // HTTP响应mime类型

	public static final String CONTENT_TYPE_TEXT = "application/txt; " + CONTENT_DEFAULT_CHARSET; // HTTP响应mime类型

	public static final String CONTENT_TYPE_HTML = "text/html; " + CONTENT_DEFAULT_CHARSET; // HTTP响应mime类型

	public static final String CONTENT_TYPE_X509 = "application/x-x509-ca-cert; " + CONTENT_DEFAULT_CHARSET; // HTTP响应mime类型

	public static final String CONTENT_TYPE_STREAM = "application/octet-stream; " + CONTENT_DEFAULT_CHARSET; // HTTP响应mime类型

	public static final String CONTENT_TYPE_PDF = "application/pdf; " + CONTENT_DEFAULT_CHARSET; // HTTP响应mime类型
	
	/**
	  * <p>对象转json</p>
	  * @Description:
	  * @param obj
	  * @return
	 */
	protected String toJsonString(Object obj ) {
		JsonMapper binder = JsonMapper.nonDefaultMapper();
		String json = binder.toJson(obj);//object to json
		return json;
	}
	
	/**
	  * <p>ajax完成操作 </p>
	  * @Description:
	  * @param message
	  * @return
	 */
	protected ModelAndView ajaxDone(Message message) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		mav.addObject("statusCode", message.getStatusCode());
		mav.addObject("message", message.getContent());
		mav.addObject("navTabId", message.getNavTabId());
		mav.addObject("rel", message.getRel());
		mav.addObject("callbackType", message.getCallbackType());
		mav.addObject("forwardUrl", message.getForwardUrl());
		return mav;
	}
	
	@ExceptionHandler(Exception.class)
	protected ModelAndView handleException(Exception ex) {
		logger.error("Catch Exception: ", ex);// 把漏网的异常信息记入日志
		/*StringPrintWriter strintPrintWriter = new StringPrintWriter();
		ex.printStackTrace(strintPrintWriter);
		String mes = strintPrintWriter.getString();*/
		
		Message message = new Message();
		message.setContent("系统内部错误，请查看日志错误信息！");//内容提示
		message.setStatusCode(this.FAIL);
		return this.ajaxDone(message);
	}
	
	@ExceptionHandler(DialogException.class)
	protected ModelAndView handleException(DialogException ex) {
		logger.error("Catch Exception: ", ex);// 把漏网的异常信息记入日志
		/*StringPrintWriter strintPrintWriter = new StringPrintWriter();
		ex.printStackTrace(strintPrintWriter);
		String mes = strintPrintWriter.getString();*/
		
		Message message = new Message();
		message.setContent("系统内部错误，请查看日志错误信息！");//内容提示
		message.setStatusCode(this.FAIL);
		return this.ajaxDone(message);
	}
	
	/**
	  * <p>获取请求参数并过滤xss</p>
	  * @Description:
	  * @param request
	  * @return
	 */
	protected String getXssParameter(HttpServletRequest request, String pname) {
		return filterSafeStringXSS(request.getParameter(pname));
	}
	
	/**
	  * <p>跨站脚本攻击过滤</p>
	  * @Description:
	  * @param str
	  * @return
	 */
	public String filterSafeStringXSS(String str) {
		StringBuilder sb = new StringBuilder(str.length() + 16);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '\'':
				sb.append("&prime;");
				break;
			case '′':
				sb.append("&prime;");
				break;
			case '\"':
				sb.append("&quot;");
				break;
			case '＂':
				sb.append("&quot;");
				break;
			case '&':
				sb.append("＆");
				break;
			case '#':
				sb.append("＃");
				break;
			case '\\':
				sb.append('￥');
				break;
			case '=':
				sb.append("&#61;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
	

    public String filterCRLF(String str) {
        return filterSafeStringXSS(str.trim());
    }
    
    public String getXssFileName(CommonsMultipartFile mFile) {
        return filterCRLF(mFile.getOriginalFilename());
    }

    public String getXssContentType(CommonsMultipartFile mFile) {
        return filterCRLF(mFile.getContentType().toLowerCase().trim());
    }
	
	/**
	  * <p>过滤图片类型</p>
	  * @Description:
	  * @param contentType
	  * @return
	 */
	protected String filterImgType(String contentType) {
		Map<String, String> imgMap = new HashMap<String, String>();
		imgMap.put("image/gif", "gif");
		imgMap.put("image/pjpeg", "jpg");
		imgMap.put("image/jpeg", "jpg");
		imgMap.put("image/bmp", "bmp");
		imgMap.put("image/x-png", "png");
		imgMap.put("image/png", "png");
		imgMap.put("image/jpg", "jpg");
		return imgMap.get(contentType);
	}
	
	/**
	  * 重载字符打印
	 */
	class StringPrintWriter extends PrintWriter {
		
		public StringPrintWriter(){
	        super(new StringWriter());
	    }
	   
	    public StringPrintWriter(int initialSize) {
	          super(new StringWriter(initialSize));
	    }
	   
	    public String getString() {
	          flush();
	          return ((StringWriter) this.out).toString();
	    }
	   
	    @Override
	    public String toString() {
	        return getString();
	    }
	}
	
	
	
}
