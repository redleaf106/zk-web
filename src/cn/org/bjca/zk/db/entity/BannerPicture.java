package cn.org.bjca.zk.db.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName BannerPicture
 * @Author redleaf
 * @Date 2020/6/9 15:38
 **/
public class BannerPicture {

    private int id;
    private String picFilePath;//图片路径
    private int picStatus;//是否启用
    private int top;
    private Timestamp optTime = new Timestamp(new Date().getTime());// 操作时间

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Timestamp getOptTime() {
        return optTime;
    }

    public void setOptTime(Timestamp optTime) {
        this.optTime = optTime;
    }

    public int getPicStatus() {
        return picStatus;
    }

    public void setPicStatus(int picStatus) {
        this.picStatus = picStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicFilePath() {
        return picFilePath;
    }

    public void setPicFilePath(String picFilePath) {
        this.picFilePath = picFilePath;
    }
}
