package com.luoyx.hauyne.message.service.impl;

import com.luoyx.hauyne.message.service.SendEmailService;
import com.luoyx.hauyne.message.support.MailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.IOException;
import java.util.Properties;

/**
 * @author 罗英雄
 * @date 2021/9/12 14:50
 */
@Service
@Slf4j
public class SendEmailServiceImpl implements SendEmailService{

    @Value("${spring.profiles.active:dev}")
    private String profile;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.from}")
    private String from;

    private static Session session;

//    private final FileSystemClient fileSystemClient;
//    private final MemberClient memberClient;
//    private final ConfigClient configClient;

    private Session getSession() {
        if (session == null) {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.stmp.auth", "true");
            props.setProperty("mail.mime.address.strict", "false");
            props.setProperty("mail.store.protocol", "pop3");
            props.setProperty("mail.pop3.port", "110");
            props.setProperty("mail.pop3.host", host);

            session = Session.getInstance(props);

            return session;
        } else {
            return session;
        }
    }

    @Override
    public void sendMail(MailMessage mailMessage) throws IOException, MessagingException {

    }

    @Override
    public boolean readAndSend(MailMessage mailMessage) {
        return false;
    }
}
