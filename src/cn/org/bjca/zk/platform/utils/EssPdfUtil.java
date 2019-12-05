package cn.org.bjca.zk.platform.utils;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/***************************************************************************
 * <pre>系统工具类</pre>
 * @文件名称:  EssPdfUtil.java
 * @包   路   径：  cn.org.bjca.zk.platform.utils
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V1.0
 * @创建人： wangwenc
 * @创建时间：2013-2-13 下午6:50:04
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
public class EssPdfUtil {
	private static Logger logger = LoggerFactory.getLogger(EssPdfUtil.class);
	/**
	  * <p>产生随机UUID</p>
	  * @Description:
	  * @return
	 */
	public static String genrRandomUUID(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
    
	/**
	 * <p>获取当前工程classes路径</p>
	 * @return
	 */
	public static String getCurrPath() {
		ClassPathResource resource = new ClassPathResource("/");
		String path = null;
		try {
			path = resource.getFile().getAbsolutePath();
			path = path.replaceAll("\\\\", "/") + "/";
			logger.debug("**************the current path is:" + path);
		} catch (IOException e) {
			logger.error("**************get classes path fails:", e);
		}
		return path;
	}

	
	public static void main(String[] args) {
		System.out.println(EssPdfUtil.genrRandomUUID());
	}

}
