package com.luoyx.hauyne.admin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author luoyingxiong
 */
@SpringBootConfiguration
@OpenAPIDefinition(
        // ## API的基本信息，包括标题、版本号、描述、联系人等
        info = @Info(
                title = "系统管理服务 接口文档",       // Api接口文档标题（必填）
                description = "包含RBAC权限管理、数据字典管理、登录历史等API",      // Api接口文档描述
                version = "0.1.1",                                   // Api接口版本
                contact = @Contact(
                        name = "罗英雄",                            // 作者名称
                        email = "luo_yingxiong@163.com",                  // 作者邮箱
                        url = "https://gitee.com/hero_luo"  // 介绍作者的URL地址
                )
        ),
        // ## 表示服务器地址或者URL模板列表，多个服务地址随时切换（只不过是有多台IP有当前的服务API）
        servers = {
                @Server(url = "http://localhost:8763/", description = "本地服务器一服务"),
                @Server(url = "http://192.168.3.15:8763/", description = "本地服务器二服务"),
        })
public class SwaggerOpenApiConfig {

}
