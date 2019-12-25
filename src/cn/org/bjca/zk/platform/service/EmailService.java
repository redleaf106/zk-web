package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.CabinetDoorEvent;
import cn.org.bjca.zk.platform.vo.EmailVO;
import cn.org.bjca.zk.platform.vo.SendMail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class EmailService {
    @Value("#{EmailConfig['myEmailAccount']}")
    private String myEmailAccount;//邮箱地址
    @Value("#{EmailConfig['myEmailPassword']}")
    private String myEmailPassword;//邮箱密码
    @Value("#{EmailConfig['myEmailSMTPHost']}")
    private String myEmailSMTPHost;//邮箱smtp服务器地址
    @Value("#{EmailConfig['receiveMailAccount']}")
    private String receiveMailAccount;//收件人邮箱

    private SendMail sendMail = new SendMail();

    public void sendOneMail(CabinetDoorEvent cabinetDoorEvent) throws Exception {
        EmailVO emailVO = new EmailVO();
        emailVO.setMyEmailAccount(myEmailAccount);
        emailVO.setMyEmailPassword(myEmailPassword);
        emailVO.setMyEmailSMTPHost(myEmailSMTPHost);
        emailVO.setReceiveMailAccount(receiveMailAccount);
        sendMail.sendOneMail(emailVO, cabinetDoorEvent);
    }
}
