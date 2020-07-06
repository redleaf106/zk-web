package cn.org.bjca.zk.db.entity;


/**
 * 屏幕保护实体
 * FileName: cn.org.bjca.zk.db.entity.ScreenPic
 * Copyright (C), 2019, 北京数字认证股份有限公司
 * Author:   gaozhijiang
 * Date:     2019/11/25 13:34
 * Version: 1.0
 */
public class ScreenPic extends IdEntity{

	private static final long serialVersionUID = 29071991653413605L;
	/**
     * 图片名称
     */
    private String picName;
    /**
     * 图片内容
     */
    private byte[] picContents;

    /**
     * 图片状态 0:启用 1 停用
     */
    private String status;

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public byte[] getPicContents() {
		return picContents;
	}

	public void setPicContents(byte[] picContents) {
		this.picContents = picContents;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
