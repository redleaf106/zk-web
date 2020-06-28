package cn.org.bjca.zk.db.entity;

import org.apache.axis2.databinding.types.soapencoding.String;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.security.Timestamp;

/**
 * @ClassName EventInfo
 * @Author redleaf
 * @Date 2020/6/1 6:55
 **/
@MappedSuperclass
public class EventInfo implements Serializable {

    @Id
    @Column
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private static final long serialVersionUID = -4101967479347358553L;

    @Column(name = "cabinetnumber")
    private String cabinetNumber;
    @Column(name = "cabinetpositiondetail")
    private String cabinetPositionDetail;
    @Column(name = "cabinetdoorname")
    private String cabinetDoorName;
    @Column(name = "employeename")
    private String employeeName;
    @Column(name = "status")
    private String status;
    @Column(name = "remark")
    private String remark;
    @Column(name = "dooropttime")
    private Timestamp doorOptTime;
    @Column(name = "picFilePath")
    private String picFilePath;
    @Column(name = "videoFilePath")
    private String videoFilePath;

    public String getCabinetNumber() {
        return cabinetNumber;
    }

    public void setCabinetNumber(String cabinetNumber) {
        this.cabinetNumber = cabinetNumber;
    }

    public String getCabinetPositionDetail() {
        return cabinetPositionDetail;
    }

    public void setCabinetPositionDetail(String cabinetPositionDetail) {
        this.cabinetPositionDetail = cabinetPositionDetail;
    }

    public String getCabinetDoorName() {
        return cabinetDoorName;
    }

    public void setCabinetDoorName(String cabinetDoorName) {
        this.cabinetDoorName = cabinetDoorName;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getDoorOptTime() {
        return doorOptTime;
    }

    public void setDoorOptTime(Timestamp doorOptTime) {
        this.doorOptTime = doorOptTime;
    }
}
