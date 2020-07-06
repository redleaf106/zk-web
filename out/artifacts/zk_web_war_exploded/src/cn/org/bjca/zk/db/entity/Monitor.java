package cn.org.bjca.zk.db.entity;

/**
 * @ClassName Monitor
 * @Author redleaf
 * @Date 2020/6/1 6:58
 **/
public class Monitor extends IdEntity{

    private String cabinetDoorEventId;
    private CabinetDoorEvent cabinetDoorEvent;
    private String picFilePath;
    private String videoFilePath;

    public String getCabinetDoorEventId() {
        return cabinetDoorEventId;
    }

    public void setCabinetDoorEventId(String cabinetDoorEventId) {
        this.cabinetDoorEventId = cabinetDoorEventId;
    }

    public CabinetDoorEvent getCabinetDoorEvent() {
        return cabinetDoorEvent;
    }

    public void setCabinetDoorEvent(CabinetDoorEvent cabinetDoorEvent) {
        this.cabinetDoorEvent = cabinetDoorEvent;
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
