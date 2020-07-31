package cn.org.bjca.zk.db.entity;

/**
 * @ClassName CheckInfo
 * @Author redleaf
 * @Date 2020/1/9 13:00
 **/
public class CheckInfo {

    private String cabinetDoorNumber;
    private String departmentName;
    private String employeeName;
    private String icCardNumber;
    private String pushTime;
    private String pushStatus;
    private String pullTime;
    private String pullStatus;
    private String superManager;
    private String remark;
    private String OAInfo;
    private String doorOptTime;
    private String doorOptStatus;

    public String getDoorOptTime() {
        return doorOptTime;
    }

    public void setDoorOptTime(String doorOptTime) {
        this.doorOptTime = doorOptTime;
    }

    public String getDoorOptStatus() {
        return doorOptStatus;
    }

    public void setDoorOptStatus(String doorOptStatus) {
        this.doorOptStatus = doorOptStatus;
    }

    public String getCabinetDoorNumber() {
        return cabinetDoorNumber;
    }

    public void setCabinetDoorNumber(String cabinetDoorNumber) {
        this.cabinetDoorNumber = cabinetDoorNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getPullTime() {
        return pullTime;
    }

    public void setPullTime(String pullTime) {
        this.pullTime = pullTime;
    }

    public String getPullStatus() {
        return pullStatus;
    }

    public void setPullStatus(String pullStatus) {
        this.pullStatus = pullStatus;
    }

    public String getSuperManager() {
        return superManager;
    }

    public void setSuperManager(String superManager) {
        this.superManager = superManager;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOAInfo() {
        return OAInfo;
    }

    public void setOAInfo(String OAInfo) {
        this.OAInfo = OAInfo;
    }

    public String getIcCardNumber() {
        return icCardNumber;
    }

    public void setIcCardNumber(String icCardNumber) {
        this.icCardNumber = icCardNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) {
            return true;
        }
        CheckInfo checkInfo = (CheckInfo)obj;
        if(this.icCardNumber.equals(checkInfo.icCardNumber)){
            return true;
        }
        return false;
    }
}
