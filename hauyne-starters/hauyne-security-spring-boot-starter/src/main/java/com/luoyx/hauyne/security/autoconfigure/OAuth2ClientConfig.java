//package com.luoyx.hauyne.security.autoconfigure;
//
//import feign.RequestInterceptor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
//
///**
// * OAuth2.0 客户端配置
// *
// * @author 罗英雄
// */
//@Slf4j
//@RequiredArgsConstructor
//@EnableConfigurationProperties
//@Configuration
//@ConditionalOnProperty(name = {
//        "security.oauth2.client.client-id",
//        "security.oauth2.client.client-secret",
//        "security.oauth2.client.access-token-uri",
//        "security.oauth2.client.grant-type",
//})
//public class OAuth2ClientConfig {
//
//    @Qualifier("oauth2ClientContext")
//    private final OAuth2ClientContext oauth2ClientContext;
//
//    /**
//     * 配置了一个 ClientCredentialsResourceDetails 类型的 Bean,
//     * 该 Bean 是通过读取配置文件中前缀为 security.oauth2.client 的配置来获取 Bean 的配置属性的
//     *
//     * @return
//     */
//    @Bean
//    @ConfigurationProperties(prefix = "security.oauth2.client")
//    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
//        ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
//        log.info("已创建 OAuth2 客户端");
//        return clientCredentialsResourceDetails;
//    }
//
//    /**
//     * 注入一个 OAuth2FeignRequestInterceptor 类型过滤器的 Bean;
//     *
//     * @return
//     */
//    @Bean
//    public RequestInterceptor oauth2FeignRequestInterceptor() {
//
//        /**
//         * 此处OAuth2ClientContext务必使用注入的Bean，
//         * 如果传new DefaultOAuth2ClientContext()会导致第一次Feign请求时，客户端会去认证服务/oauth/token接口申请新的access_token；
//         * 进而用新的access_token去执行Feign请求。
//         */
//        return new OAuth2FeignRequestInterceptor(oauth2ClientContext, clientCredentialsResourceDetails());
//    }
//
//    /**
//     * 最后注入了一个用于向 auth-server 服务请求的 OAuth2RestTemplate 类型的 Bean
//     *
//     * @return
//     */
//    @Bean
//    public OAuth2RestTemplate clientCredentialsRestTemplate(ClientCredentialsResourceDetails clientDetails) {
//        OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(clientDetails);
//        log.info("已创建Bean => {}", oauth2RestTemplate);
//        return oauth2RestTemplate;
//    }
//}
