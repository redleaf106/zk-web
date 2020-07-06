package cn.org.bjca.zk.platform.bean;

import java.io.Serializable;
import java.util.Date;

/***************************************************************************
 * <pre>证书bean</pre>
 * @文件名称:  CertBean.java
 * @包   路   径：  cn.org.bjca.zk.platform.bean
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： wangwenc
 * @创建时间：2013-3-19 下午8:57:36
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
public class CertBean implements Serializable {
	
	private static final long serialVersionUID = -689596850377186204L;

	private String certDN;//证书DN

	private Date beginTime; //证书生效时间
	
	private Date endTime;//证书到期时间
	
	private String serialNumber;//证书序列号

	public String getCertDN() {
		return certDN;
	}

	public void setCertDN(String certDN) {
		this.certDN = certDN;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
