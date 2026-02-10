package com.luoyx.hauyne.uaa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * auth-server是认证服务，但是需要加上@EnableResourceServer注解，开启资源服务
 * 因为程序需要对外暴露获取Token的API接口和验证Token的API接口，所以该程序也是一个资源服务
 *
 * @author 罗英雄 luo_yingxiong@163.com
 * @date 2020/5/5 21:40
 *
 * @EnableFeignClients 开启Feign Client功能，否则 FeignClient接口无法依赖注入
 * @EnableHystrix 开启Hystrix熔断器功能
 * @EnableHystrixDashboard 开启Hystrix 仪表板监控功能
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@EnableFeignClients
public class UAA {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(UAA.class, args);

        Environment env = application.getEnvironment();
        log.info("\n\t----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }
}
