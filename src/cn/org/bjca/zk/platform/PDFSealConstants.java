package cn.org.bjca.zk.platform;


/***************************************************************************
 * <pre>常量类</pre>
 ***************************************************************************/
public final class PDFSealConstants {

	// session用户
	public static final String SESSION_USER = "sessionUser";

	// session User cert id
	public static final String SESSION_CERTUNIQUEID = "certUniqueId";
	
	// 登录类型参数名
   public static final String LOGIN_TYPE_PARAMNAME = "loginType";

	// 用户登录类型，口令登录
	public static final String LOGIN_TYPE_PWD = "1";
	
	// 用户登录类型，证书登录
	public static final String LOGIN_TYPE_CERT = "2";

	// 用户类型，超级用户
	public static final String USER_TYPE_SUPER = "1";

	// 用户类型，普通用户
	public static final String USER_TYPE_GENERNAL = "2";

	// RSA签名算法
	public static final String SIGNALG_RSA = "SHA1WithRSA";

	// SM2签名算法
	public static final String SIGNALG_SM2 = "SM3WithSM2";

	// 证书类型RSA
	public static final String CERTTYPE_RSA = "RSA";

	// 证书类型SM2
	public static final String CERTTYPE_SM2 = "SM2";

	// SHA1算法
	public static final String HASH_SHA1 = "SHA1";

	// BC 提供商
	public static final String BC = "BC";

	// 日志信息
	public static final String LOG_INFO = "logInfo";


	// ip地址
	public static final String NETWORK_IPADDR = "IPADDR";
	// 子网掩码
	public static final String NETWORK_NETMASK = "NETMASK";
	// 网关
	public static final String NETWORK_GATEWAY = "GATEWAY";
	// 网段
	public static final String NETWORK_NETWORK = "NETWORK";

	// 默认字符集编码
	public static final String CODING = "UTF-8";

	// GBK编码
	public static final String CODING_GBK = "GBK";

	// iso-8859-1编码
	public static final String CODING_ISO8859 = "iso-8859-1";


	// ************************磁盘与内存空间计算

	// bt字节参考量
	public static final long SIZE_BT = 1024L;

	// KB字节参考量
	public static final long SIZE_KB = SIZE_BT * 1024L;

	// MB字节参考量
	public static final long SIZE_MB = SIZE_KB * 1024L;

	// GB字节参考量
	public static final long SIZE_GB = SIZE_MB * 1024L;

	// TB字节参考量
	public static final long SIZE_TB = SIZE_GB * 1024L;

}
