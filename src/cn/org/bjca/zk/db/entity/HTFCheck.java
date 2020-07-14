package cn.org.bjca.zk.db.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName HTFCheck
 * @Author redleaf
 * @Date 2020/1/9 19:01
 **/
public class HTFCheck implements Serializable {
    private static final long serialVersionUID = 8603678646489218828L;
    @Id
    @Column(name = "id")
    private int id;

    private String fileName;
    private String filePath;
    private Date createTime;
    private String reportType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
