package com.luoyx.hauyne.message.service;

import com.luoyx.hauyne.message.support.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author 罗英雄
 * @date 2021/9/9 22:16
 */
public interface SendEmailService {

    /**
     * 邮件发送 （ 1=支持简单文本； 2=支持html文本； 3=支持附件； 4=支持抄送，密抄送）
     * @param mailMessage
     * @throws IOException
     * @throws MessagingException
     */
    void sendMail(MailMessage mailMessage) throws IOException, MessagingException;

    /**
     * 邮件发送（支持读取邮件收件服务器邮件进行复制和发送）
     * @param mailMessage
     * @return
     */
    boolean readAndSend(MailMessage mailMessage);
}
