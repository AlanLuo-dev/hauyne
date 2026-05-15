package com.luoyx.hauyne.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.UnknownHostException;

/**
 * @author LuoYingxiong
 * @date 2020/5/2 20:33
 */
@Slf4j
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class Admin {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(Admin.class, args);

        Environment env = application.getEnvironment();
        log.info("\n\t----------------------------------------------------------\n\t" +
                        "系统管理微服务【{}】已启动！\n\t" +
                        "接口文档地址 ==> http://localhost:{}/swagger-ui/index.html\n\t" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"));
    }
}
