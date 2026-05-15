package com.luoyx.hauyne.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
/**
 * 网关主程序
 *
 * @author LuoYingxiong
 * @date 2020/5/4 19:17
 */
@Slf4j
@SpringBootApplication
public class Gateway {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(Gateway.class, args);

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

    @Bean
    @Lazy(false)
    public Set<SwaggerUrl> apis(RouteDefinitionLocator locator,
                                SwaggerUiConfigProperties swaggerUiConfigProperties) {
        Set<SwaggerUrl> urls = new HashSet<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        definitions.stream()
            .filter(RouteDefinition routeDefinition -> routeDefinition.getId().matches("service-.*"))
            .forEach(RouteDefinition routeDefinition -> {
                String name = routeDefinition.getId().replaceAll("service-", "");
                SwaggerUrl swaggerUrl = new SwaggerUrl(name, name + DEFAULT_API_DOCS_URL, null);
                urls.add(swaggerUrl);
            });
        swaggerUiConfigProperties.setUrls(urls);
        return urls;
    }
}
