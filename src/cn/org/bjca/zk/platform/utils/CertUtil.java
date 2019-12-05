package cn.org.bjca.zk.platform.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.commons.io.IOUtils;

import cn.org.bjca.zk.platform.bean.CertBean;

/***************************************************************************
 * <pre>证书工具类</pre>
 * @文件名称:  CertUtil.java
 * @包   路   径：  cn.org.bjca.zk.platform.utils
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-19 下午8:53:44
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
public class CertUtil {
	
	/**
	  * <p>解析证书文件</p>
	  * @Description:
	  * @param certBty 证书文件字节数组 
	  * @return X509Certificate 证书对象
	  * @throws Exception
	 */
	public static X509Certificate readBytesToX509Certificate(byte[] certBty) throws Exception {
		X509Certificate certObj = null;
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(certBty)));
			String beginKey = "-----BEGIN CERTIFICATE-----\n";
			String endKey = "-----END CERTIFICATE-----";
			String certStr = bufRdr.readLine();
			if (certStr != null && !certStr.startsWith("-----")
					&& !certStr.startsWith("0")) {// 对非标准的base64证书格式的处理
				beginKey += certStr + "\n";
				while (true) {
					certStr = bufRdr.readLine();
					if (certStr == null)
						break;
					beginKey += certStr + "\n";
				}
				beginKey += endKey;
				certBty = beginKey.getBytes();
			}
			CertificateFactory factory = CertificateFactory.getInstance("X509");
			certObj = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBty));
		}finally{
			IOUtils.closeQuietly(bufRdr);
		}
		return certObj;
	}
	
	/**
	  * <p>根据证书对象获取信息</p>
	  * @Description:
	  * @param cert
	  * @return
	  * @throws Exception
	 */
	public static CertBean getCertInfoFromObj(X509Certificate cert) throws Exception {
		CertBean bean = new CertBean();
		bean.setBeginTime(cert.getNotBefore());
		bean.setEndTime(cert.getNotAfter());
		bean.setCertDN(cert.getSubjectDN().getName());
		bean.setSerialNumber(cert.getSerialNumber().toString(16).toUpperCase());
		return bean;
	}
	
	/**
	  * <p>获取证书信息</p>
	  * @Description:
	  * @param certBty
	  * @return
	  * @throws Exception
	 */
	public static CertBean getCertInfo(byte[] certBty) throws Exception {
		X509Certificate  cert = readBytesToX509Certificate(certBty);
		CertBean bean = new CertBean();
		bean.setBeginTime(cert.getNotBefore());
		bean.setEndTime(cert.getNotAfter());
		bean.setCertDN(cert.getSubjectDN().getName());
		bean.setSerialNumber(cert.getSerialNumber().toString(16).toUpperCase());
		return bean;
	}
	
	/**
	  * <p>验证证书签名</p>
	  * @Description:
	  * @param rootCert
	  * @param targetCert
	  * @return
	  * @throws Exception
	 */
	public static boolean certVerify(byte[] rootCert, byte[] targetCert) {
		boolean status = false;
		try{
		 X509Certificate root = readBytesToX509Certificate(rootCert);
		 X509Certificate cert = readBytesToX509Certificate(targetCert);
		 cert.verify(root.getPublicKey());
		 status = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return status;
	}

}
