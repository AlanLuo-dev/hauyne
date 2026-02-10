package com.luoyx.hauyne.gateway.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {


    /*@Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(r -> r
                .path("/api/uaa/**")
                .uri("lb://auth-server")
                .filters(new StripPrefixGatewayFilterFactory(), new CustomGatewayFilter())
                .id("auth-server"))
                .build();

    }*/
}
