package cn.org.bjca.zk.db.entity;

/**
 * 助理类
 * @ClassName Assistant
 * @Author redleaf
 * @Date 2020/6/1 5:25
 **/
public class Assistant extends IdEntity {
    /**
     * 助理姓名
     */
    private String assistantName;


    /**
     * ic卡号
     */
    private String icCardNumber;

    /**
     * 领导id
     */
    private String leaderId;


    /**
     * 照片
     */
    private byte[] picFile;


    /**
     * 机柜信息
     */
    private Cabinet cabinet;

    /**
     * 柜门信息
     */
    private CabinetDoor cabinetDoor;

    public void setIcCardNumber(String icCardNumber) {
        this.icCardNumber = icCardNumber;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public CabinetDoor getCabinetDoor() {
        return cabinetDoor;
    }

    public void setCabinetDoor(CabinetDoor cabinetDoor) {
        this.cabinetDoor = cabinetDoor;
    }

    public Employee getLeaderEmployee() {
        return leaderEmployee;
    }

    public void setLeaderEmployee(Employee leaderEmployee) {
        this.leaderEmployee = leaderEmployee;
    }

    public String getAssistantName() {
        return assistantName;
    }

    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    public String getIcCardNumber() {
        return icCardNumber;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public byte[] getPicFile() {
        return picFile;
    }

    public void setPicFile(byte[] picFile) {
        this.picFile = picFile;
    }

    /**
     * 领导信息
     */
    private Employee leaderEmployee;


}
