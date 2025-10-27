package com.luoyx.hauyne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 罗英雄
 * @date 2021/9/9 11:23
 */
@SpringBootApplication
@EnableFeignClients
public class Message {
    public static void main(String[] args) {

        // 处理邮件附件过长导致文件名丢失的问题
        System.setProperty("mail.mime.splitlongparameters", "false");

        SpringApplication.run(Message.class, args);
    }
}
