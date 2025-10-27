//package com.luoyx.hauyne.uaa.config;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.env.EnvironmentPostProcessor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.ConfigurableEnvironment;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//@Configuration
//public class RabbitMqDevConfig implements EnvironmentPostProcessor {
//
//    @Override
//    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
//        try {
//            String hostname = InetAddress.getLocalHost().getHostName();
//            System.setProperty("rabbitmq.vhost", "/" + hostname.toLowerCase());
//        } catch (UnknownHostException e) {
//            System.setProperty("rabbitmq.vhost", "/dev");
//        }
//        System.out.println("配置 rabbitmq.vhost=" + System.getProperty("rabbitmq.vhost"));
//    }
//
//}
