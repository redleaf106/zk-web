package cn.org.bjca.zk.platform.vo;

import org.springframework.beans.factory.annotation.Value;

/**
 * 邮箱
 */

public class EmailVO {
//    @Value("#{EmailConfig['myEmailAccount']}")
//    private String myEmailAccount;//邮箱地址
//    @Value("#{EmailConfig['myEmailPassword']}")
//    private String myEmailPassword;//邮箱密码
//    @Value("#{EmailConfig['myEmailSMTPHost']}")
//    private String myEmailSMTPHost;//邮箱smtp服务器地址
//    @Value("#{EmailConfig['receiveMailAccount']}")
//    private String receiveMailAccount;//收件人邮箱

    private String myEmailAccount;
    private String myEmailPassword;
    private String myEmailSMTPHost;
    private String receiveMailAccount;

    public EmailVO(String myEmailAccount, String myEmailPassword, String myEmailSMTPHost, String receiveMailAccount) {
        this.myEmailAccount = myEmailAccount;
        this.myEmailPassword = myEmailPassword;
        this.myEmailSMTPHost = myEmailSMTPHost;
        this.receiveMailAccount = receiveMailAccount;
    }

    public EmailVO() {
    }

    public String getMyEmailAccount() {
        System.out.println(myEmailAccount);
        return myEmailAccount;
    }
    public void setMyEmailAccount(String myEmailAccount) {
        this.myEmailAccount = myEmailAccount;
    }
    public String getMyEmailPassword() {
        return myEmailPassword;
    }
    public void setMyEmailPassword(String myEmailPassword) {
        this.myEmailPassword = myEmailPassword;
    }
    public String getMyEmailSMTPHost() {
        return myEmailSMTPHost;
    }
    public void setMyEmailSMTPHost(String myEmailSMTPHost) {
        this.myEmailSMTPHost = myEmailSMTPHost;
    }
    public String getReceiveMailAccount() {
        return receiveMailAccount;
    }
    public void setReceiveMailAccount(String receiveMailAccount) {
        this.receiveMailAccount = receiveMailAccount;
    }

}
