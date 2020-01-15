package cn.org.bjca.zk.db.entity;

import java.util.Date;

/**
 * @ClassName HTFCheck
 * @Author redleaf
 * @Date 2020/1/9 19:01
 **/
public class HTFCheck extends IdEntity{

    private String fileName;
    private String filePath;
    private Date createTime;

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
}
