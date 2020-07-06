package cn.org.bjca.zk.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/***************************************************************************
 * <pre>统一定义id的entity基类</pre>
 * @文件名称:  IdEntity.java
 * @包   路   径：  cn.org.bjca.zk.entity
 * @版权所有：北京数字认证股份有限公司 (C) 2012
 *
 * @类描述:  
 * @版本: V1.0
 * @创建人： wangwenc
 * @创建时间：2013-2-6 下午7:02:32
 ***************************************************************************/
@MappedSuperclass
public abstract class IdEntity implements Serializable {

	private static final long serialVersionUID = 8603678646489218828L;

	public static final String STATUS_START = "1";// 启用、启动

	public static final String STATUS_STOP = "2";// 禁用、停止

    public static final String SEAL_STATUS_START = "0";//启用

    public static final String SEAL_STATUS_STOP = "1";//停用

    public static final String SEAL_STATUS_REVOKE = "2";//撤销

	private String id;// 主键

	private Timestamp optTime = new Timestamp(new Date().getTime());;// 操作时间

	private String userId;// 创建用户ID

	private String ext1;// 扩展字段1

	private String ext2;// 扩展字段2

	private String ext3;// 扩展字段3

	@Id
	@Column(name = "ID", length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "OPTTIME")
	public Timestamp getOptTime() {
		return optTime;
	}

	public void setOptTime(Timestamp optTime) {
		this.optTime = optTime;
	}

	@Column(name = "USERID", length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "EXT1", length = 100)
	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	@Column(name = "EXT2", length = 100)
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	@Column(name = "EXT3", length = 100)
	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

}
