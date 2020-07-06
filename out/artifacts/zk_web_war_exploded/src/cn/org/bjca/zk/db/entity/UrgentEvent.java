package cn.org.bjca.zk.db.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName UrgentEvent
 * @Author redleaf
 * @Date 2020/4/9 18:45
 **/
public class UrgentEvent{

    private int id;

    private String employeeCardNumber;

    private String employeeName;

    private Timestamp doorOptTime = new Timestamp(new Date().getTime());

    private String remark;

    private int emailStatus;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getEmployeeCardNumber() {
        return employeeCardNumber;
    }

    public void setEmployeeCardNumber(String employeeCardNumber) {
        this.employeeCardNumber = employeeCardNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Timestamp getDoorOptTime() {
        return doorOptTime;
    }

    public void setDoorOptTime(Timestamp doorOptTime) {
        this.doorOptTime = doorOptTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
