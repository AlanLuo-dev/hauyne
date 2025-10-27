///*
// * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
// * All rights reserved.
// * Official Web Site: http://www.xiaominfo.com.
// * Developer Web Site: http://open.xiaominfo.com.
// */
//
//package com.luoyx.hauyne.gateway.knife4j;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.gateway.config.GatewayProperties;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.support.NameUtils;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 因为Swagger暂不支持webflux项目，所以不能在Gateway配置SwaggerConfig，需要编写
// * GatewaySwaggerProvider实现SwaggerResourcesProvider接口，用于获取SwaggerResources
// *
// * @author luoyingxiong
// * @see org.springframework.context.annotation.Primary
// * 该注解用于表明GatewaySwaggerProvider是SwaggerResourcesProvider接口的首选实现，
// * 在存在多个相同类型的Bean时，优先选择被标注了@Primary注解的Bean进行注入。
// */
//@RequiredArgsConstructor
//@Component
//@Primary
//public class GatewaySwaggerProvider implements SwaggerResourcesProvider {
//
//    public static final String API_URI = "/v2/api-docs";
//    private final RouteLocator routeLocator;
//    private final GatewayProperties gatewayProperties;
//
//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
//        List<String> routes = new ArrayList<>();
//
//        // 取出Spring Cloud Gateway中的route
//        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
//
//        // 结合 application.yml中的路由配置，只获取有效的route节点
//        gatewayProperties.getRoutes().stream()
//                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
//                .forEach(
//                        routeDefinition -> routeDefinition.getPredicates().stream()
//                                .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
//                                .forEach(
//                                        predicateDefinition -> resources.add(
//                                                swaggerResource(
//                                                        routeDefinition.getId(),
//                                                        predicateDefinition.getArgs()
//                                                                .get(NameUtils.GENERATED_NAME_PREFIX + "0")
//                                                                .replace("/**", API_URI)
//                                                )
//                                        )
//                                )
//                );
//
//
//        return resources;
//    }
//
//
//    private SwaggerResource swaggerResource(String name, String location) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion("2.0");
//
//        return swaggerResource;
//    }
//
//
//}