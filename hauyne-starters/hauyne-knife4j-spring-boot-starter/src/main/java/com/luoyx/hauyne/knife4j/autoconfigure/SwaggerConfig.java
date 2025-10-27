
package com.luoyx.hauyne.knife4j.autoconfigure;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author 罗英雄
 */
@Slf4j
@Configuration
@EnableKnife4j
//@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

//    @Bean(value = "orderApi")
//    @Order(value = 1)
//    public Docket groupRestApi() {
//        log.info("Swagger配置：创建Bean -> {}", Docket.class);
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(groupApiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.luoyx.hauyne"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo groupApiInfo() {
//        return new ApiInfoBuilder()
//                .title("蓝方石")
//                .description("<div style='font-size:14px;color:red;'>swagger-bootstrap-ui-demo RESTful APIs</div>")
//                .termsOfServiceUrl("http://www.group.com/")
//                .contact(new Contact("", "", "luo_yingxiong@163.com"))
//                .version("1.0")
//                .build();
//    }

    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags() != null) {
                openApi.getTags().forEach(tag -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("x-order", 2);
                    tag.setExtensions(map);
                });
            }
            if (openApi.getPaths() != null) {
                openApi.addExtension("x-test123", "333");
                openApi.getPaths().addExtension("x-abb", 4);
            }

        };
    }

//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("XXX用户系统API")
//                        .version("1.0")
//
//                        .description("Knife4j集成springdoc-openapi示例")
//                        .termsOfService("http://doc.xiaominfo.com")
//                        .license(new License().name("Apache 2.0")
//                                .url("http://doc.xiaominfo.com")));
//    }

//    @Bean
//    public ModelPropertyBuilderPlugin modelPropertyBuilderPlugin() {
//        log.info("Swagger配置：创建自定义Swagger2插件Bean，使 @ApiModelProperty标记的字段按定义顺序显示 -> {}", CustomApiModelPropertyPositionBuilder.class);
//        return new CustomApiModelPropertyPositionBuilder();
//    }


}
