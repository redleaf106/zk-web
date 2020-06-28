package cn.org.bjca.zk.db.entity;

/**
 * @ClassName Monitor
 * @Author redleaf
 * @Date 2020/6/1 6:58
 **/
public class Monitor{

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String cabinetDoorEventId;
    private String picFilePath;
    private String videoFilePath;

    public String getCabinetDoorEventId() {
        return cabinetDoorEventId;
    }

    public void setCabinetDoorEventId(String cabinetDoorEventId) {
        this.cabinetDoorEventId = cabinetDoorEventId;
    }

    public String getPicFilePath() {
        return picFilePath;
    }

    public void setPicFilePath(String picFilePath) {
        this.picFilePath = picFilePath;
    }

    public String getVideoFilePath() {
        return videoFilePath;
    }

    public void setVideoFilePath(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }
}
