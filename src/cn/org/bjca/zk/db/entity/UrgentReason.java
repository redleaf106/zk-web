package cn.org.bjca.zk.db.entity;

import java.sql.Timestamp;

/**
 * @ClassName UrgentReason
 * @Author redleaf
 * @Date 2020/6/9 11:27
 **/
public class UrgentReason {
    private int id;
    private String reason;
    private int type;
    private Timestamp opttime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getOpttime() {
        return opttime;
    }

    public void setOpttime(Timestamp opttime) {
        this.opttime = opttime;
    }
}
