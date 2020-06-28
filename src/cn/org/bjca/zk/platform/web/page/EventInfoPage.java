package cn.org.bjca.zk.platform.web.page;

import com.cn.bjca.seal.esspdf.core.pagination.annotation.Paging;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName EventInfoPage
 * @Author redleaf
 * @Date 2020/6/11 10:23
 **/
@Paging(field = "pageVO")
public class EventInfoPage <T> extends BasePage<T>{
    private static final long serialVersionUID = -6183879852926580372L;

    private String cabinetNumber;
    private String cabinetPositionDetail;
    private String cabinetDoorName;
    private String employeeName;
    private String status;
    private String remark;
    private Timestamp doorOptTime = new Timestamp(new Date().getTime());

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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
