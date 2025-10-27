//package com.luoyx.hauyne.gateway.knife4j;
//
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//import springfox.documentation.swagger.web.SecurityConfiguration;
//import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//import springfox.documentation.swagger.web.UiConfiguration;
//import springfox.documentation.swagger.web.UiConfigurationBuilder;
//
//import java.util.Optional;
//
//
///**
// * 因为没有在Spring Cloud Gateway中配置SwaggerConfig，但是运行Swagger-UI的时候需要依赖一些接口，
// * 所以需要建立相应的Swagger-Resource端点
// *
// * @author luoyingxiong
// */
//@RestController
//@RequestMapping("/swagger-resources")
//public class SwaggerHandler {
//
//    @Autowired(required = false)
//    private SecurityConfiguration securityConfiguration;
//
//    @Autowired(required = false)
//    private UiConfiguration uiConfiguration;
//
//    private final SwaggerResourcesProvider swaggerResources;
//
//    @Autowired
//    public SwaggerHandler(SwaggerResourcesProvider swaggerResources) {
//        this.swaggerResources = swaggerResources;
//    }
//
//    @GetMapping("/configuration/security")
//    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
//        return Mono.just(
//                new ResponseEntity<>(
//                        Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()),
//                        HttpStatus.OK
//                )
//        );
//    }
//
//    @GetMapping("/configuration/ui")
//    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
//        return Mono.just(
//                new ResponseEntity<>(
//                        Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()),
//                        HttpStatus.OK
//                )
//        );
//    }
//
//    @Operation(summary = "加载所有的微服务信息显示到Swagger2 文档聚合页左上角的下拉列表")
//    @GetMapping
//    public Mono<ResponseEntity> swaggerResources() {
//        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
//    }
//
//
//}
//
