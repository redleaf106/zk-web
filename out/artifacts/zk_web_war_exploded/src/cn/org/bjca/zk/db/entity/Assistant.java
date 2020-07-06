package cn.org.bjca.zk.db.entity;

/**
 * @ClassName Assistant
 * @Author redleaf
 * @Date 2020/6/1 5:25
 **/
public class Assistant extends IdEntity {
    /**
     * 员工姓名
     */
    private String assistantName;

    /**
     * 员工编号
     */
    private String assistantNumber;

    /**
     * ic卡号
     */
    private String assistantIcCardNumber;

    /**
     * 领导id
     */
    private String leaderId;

    /**
     * 手机号码1
     */
    private String mobilePhone;

    /**
     * 手机号码2
     */
    private String mobilePhone2;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 照片
     */
    private byte[] picFile;

    /**
     * 部门Id
     */
    private String departmentId;

    /**
     * 部门
     */
    private Department department;

    public String getAssistantName() {
        return assistantName;
    }

    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    public String getAssistantNumber() {
        return assistantNumber;
    }

    public void setAssistantNumber(String assistantNumber) {
        this.assistantNumber = assistantNumber;
    }

    public String getAssistantIcCardNumber() {
        return assistantIcCardNumber;
    }

    public void setAssistantIcCardNumber(String assistantIcCardNumber) {
        this.assistantIcCardNumber = assistantIcCardNumber;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getMobilePhone2() {
        return mobilePhone2;
    }

    public void setMobilePhone2(String mobilePhone2) {
        this.mobilePhone2 = mobilePhone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPicFile() {
        return picFile;
    }

    public void setPicFile(byte[] picFile) {
        this.picFile = picFile;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
