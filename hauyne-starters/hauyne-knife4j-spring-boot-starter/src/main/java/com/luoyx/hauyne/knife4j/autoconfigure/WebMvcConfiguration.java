package com.luoyx.hauyne.knife4j.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 *
 * @author 罗英雄
 */
@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 配置资源处理器，将 Knife4j 增强 UI 页面路径映射到类路径下的资源
     * <p>
     * 由于NoHandlerFoundException的统一异常捕获需要配置
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.resources.add-mappings=false
     * 这会导致Knife4j的doc.html页面无法访问，所以此处特别配置doc.html的资源处理器
     * <p>
     *
     * @param registry 资源处理器注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 如果你想展示原生的swagger-ui界面，就用这个配置，但要引入原生的springfox-swagger-ui依赖，否则会跳到404页面，无法达到404统一异常捕获效果
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");

        // 映射 Knife4j 增强 UI 页面路径（doc.html），用于访问增强版接口文档页面
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        // 映射 webjars 相关静态资源路径，Knife4j 和 Swagger UI 前端依赖（JS/CSS）加载所需
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        log.info("已配置knife4j的doc.html静态资源映射");
    }
}
