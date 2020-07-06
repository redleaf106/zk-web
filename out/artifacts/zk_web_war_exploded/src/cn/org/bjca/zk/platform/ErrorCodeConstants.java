package cn.org.bjca.zk.platform;

/***************************************************************************
 * <pre>错误码常量类</pre>
 ***************************************************************************/
public final class ErrorCodeConstants {

	// 成功
	public static final String SUCCESS = "200";

	// 参数错误
	public static final String PARAMETER_ERROR = "300";

	// 连接错误
	public static final String CONNECT_ERROR = "400";

	// 服务器内部错误
	public static final String INTERNAL_ERROR = "500";

	// PDF文件错误
	public static final String PDFFILE_ERROR = "600";
	
	//***** pdf签章错误码从6000开始
	// 机构权限错误，没有对应的机构
	public static final String ORG_PERMISSION_ERROR = "6000";

	// 签章规则错误，没有创建对应的签章规则
	public static final String SIGNSEALRULE_ERROR = "6001";

	// 关键字搜索错误
	public static final String KEYWORD_ERROR = "6002";

	// PDF签名服务器签名错误
	public static final String PDFSERVERSIGN_ERROR = "6003";

	// DSVS服务器签名错误
	public static final String DSVSSERVERSIGN_ERROR = "6004";

	// TSS服务器签名错误
	public static final String TSSSERVERSIGN_ERROR = "6005";

	// PDF签章错误
	public static final String PDFSERVERSIGNSEAL_ERROR = "6006";

	// PDF服务器验证签名错误
	public static final String PDFSERVERVERIFY_ERROR = "6007";

	// PDF文件生成错误
	public static final String GENPDF_ERROR = "6008";

	// PDF模版不存在
	public static final String PDF_TEMPLATE_NOTEXIST_ERROR = "6009";

	// PDF模版数据解析错误
	public static final String PDF_TEMPLATE_PARSE_ERROR = "6010";

	// PDF文件被篡改
	public static final String PDF_MODIFIED_ERROR = "6011";

	// 证书有效期错误
	public static final String CERT_TERM_ERROR = "6012";

	// 条形码规则错误，没有创建对应的条形码规则
	public static final String BARCODERULE_ERROR = "6013";

	// 产生条形码错误
	public static final String GENBARCODER_ERROR = "6014";

	// 印章宽度错误，不能小于1像素
	public static final String SEALWIDTH_ERROR = "6015";

	// IP禁止访问
	public static final String IPACCESS_ERROR = "6016";

	// 从PDF导出XML数据错误
	public static final String EXPORTXMLDATA_ERROR = "6017";

	// 文件上传ftp服务器错误
	public static final String FTPSERVER_ERROR = "6018";

	// 产生客户端签章摘要失败
	public static final String CLIENT_SIGNDIGEST_ERROR = "6019";

	// 合并客户端PDF签章文件失败
	public static final String CLIENT_SIGNSEAL_ERROR = "6020";

	// 证书链验证错误
	public static final String CERT_CHAIN_ERROR = "6021";

	// 证书已注销
	public static final String CERT_CRL_ERROR = "6022";

	// 证书文件错误
	public static final String CERT_FILE_ERROR = "6023";

	// 产生 图片错误
	public static final String PDF_TO_IMAGES_ERROR = "6024";
	
	// 数据库数据同步错误
    public static final String DBDATASYN_ERROR = "6026";
    
    // 服务器印章编号错误
 	public static final String SERVERSEALNUM_ERROR = "6027";
 	
 	// 签名策略编号错误
  	public static final String SIGNPOLICYNUM_ERROR = "6028";
  	
  	// PDF文档添加附件文件
   	public static final String APPEND_ATTACHEMENT_ERROR = "6029";
   	
   	// 事件证书策略编号错误
  	public static final String EVENTCERT_POLICYNUM_ERROR = "6030";
  	
  	// 事件证书签名错误
   	public static final String EVENTCERT_SIGN_ERROR = "6031";
   	
   	// 在线事件证书签发错误
	public static final String EVENTCERT_ONLINE_SIGN_ERROR = "6032";
	
 	// 产生企业证书和印章错误
	public static final String GEN_COMPANY_CERT_SEAL_ERROR = "6033";
	
	// 证书验证权限错误
	public static final String CERT_VERIFY_AUTH_ERROR = "6034";
	
	// 单证模板编号或模板文件错误
	public static final String REPORT_TEMPLATE_ERROR = "6035";
	
	// 产生单证文件错误
    public static final String GEN_REPORT_FILE_ERROR = "6036";
    
    // 邮件发送错误
    public static final String EMAIL_SEND_ERROR = "6037";
    
    // 机构编号错误
    public static final String ORGCODE_ERROR = "6038";
    
    // 产生印章错误
    public static final String GEN_SEAL_ERROR = "6039";
   	
	//*****dsvs 错误码从7000开始
	// p1签名错误
	public static final String PKCS1_SIGN_ERROR = "7000";
	
	// p1验证错误
	public static final String PKCS1_VERIFY_ERROR = "7001";
	
	// 签名策略编号（签名证书编号）错误
	public static final String POLICYNUM_ERROR = "7002";
	
	// 下载签名证书错误
	public static final String DOWN_CERT_ERROR = "7003";
	
	// p7签名错误
	public static final String PKCS7_SIGN_ERROR = "7004";
		
	// p7验证错误
	public static final String PKCS7_VERIFY_ERROR = "7005";
	
	// 数据加密错误
	public static final String DATA_ENCRYPT_ERROR = "7006";
	
	// 数据解密错误
	public static final String DATA_DECRYPT_ERROR = "7007";
	
	// 数字信封加密错误
	public static final String PKCS7_ENCRYPT_ENVELOP_ERROR = "7008";
	
	// 数字信封解密错误
	public static final String PKCS7_DECRYPT_ENVELOP_ERROR = "7009";
	
	// 公钥加密错误
	public static final String PUBKEY_ENCRYPT_ERROR = "7010";
	
	// 私钥解密错误
	public static final String PRIKEY_DECRYPT_ERROR = "7011";
	
	// 获取p7签名信息错误
	public static final String GET_PCKS7SIGNINFO_ERROR = "7012";
	
	// 产生摘要数据错误
	public static final String GEN_DIGEST_ERROR = "7013";
}
