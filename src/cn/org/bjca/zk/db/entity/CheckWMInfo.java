package cn.org.bjca.zk.db.entity;

public class CheckWMInfo {
    private String cabinetDoorNumber;
    private String departmentName;
    private String employeeName;
    private String icCardNumber;
    private String monthTotal;//本月汇总

    private String zccTotal;//正常存入次数
    private String csTotal;//超时存入次数
   // private String wcTotal;//未存次数
    private String zcqTotal;//正常取出次数
    private String yjTotal;//应急取出次数
    private String dcTotal;//多次存取次数
    private String csDate;//超时存入日期
   // private String wcDate;//未存日期
    private String yjDate;//应急取出日期
    private String dcDate;//多次存取日期

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

    public String getIcCardNumber() {
        return icCardNumber;
    }

    public void setIcCardNumber(String icCardNumber) {
        this.icCardNumber = icCardNumber;
    }

    public String getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(String monthTotal) {
        this.monthTotal = monthTotal;
    }

    public String getZccTotal() {
        return zccTotal;
    }

    public void setZccTotal(String zccTotal) {
        this.zccTotal = zccTotal;
    }

    public String getCsTotal() {
        return csTotal;
    }

    public void setCsTotal(String csTotal) {
        this.csTotal = csTotal;
    }

    /*public String getWcTotal() {
        return wcTotal;
    }

    public void setWcTotal(String wcTotal) {
        this.wcTotal = wcTotal;
    }*/

    public String getZcqTotal() {
        return zcqTotal;
    }

    public void setZcqTotal(String zcqTotal) {
        this.zcqTotal = zcqTotal;
    }

    public String getYjTotal() {
        return yjTotal;
    }

    public void setYjTotal(String yjTotal) {
        this.yjTotal = yjTotal;
    }

    public String getDcTotal() {
        return dcTotal;
    }

    public void setDcTotal(String dcTotal) {
        this.dcTotal = dcTotal;
    }

    public String getCsDate() {
        return csDate;
    }

    public void setCsDate(String csDate) {
        this.csDate = csDate;
    }

    /*public String getWcDate() {
        return wcDate;
    }

    public void setWcDate(String wcDate) {
        this.wcDate = wcDate;
    }*/

    public String getYjDate() {
        return yjDate;
    }

    public void setYjDate(String yjDate) {
        this.yjDate = yjDate;
    }

    public String getDcDate() {
        return dcDate;
    }

    public void setDcDate(String dcDate) {
        this.dcDate = dcDate;
    }
}
