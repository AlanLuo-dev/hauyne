//package com.luoyx.hauyne.openfeign.autoconfigure;
//
//import cn.hutool.core.util.StrUtil;
//import lombok.SneakyThrows;
//import org.apache.http.client.utils.URIBuilder;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
//import org.springframework.cloud.consul.discovery.ConsulServer;
//import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpRequest;
//
//import java.net.URI;
//import java.util.Optional;
//
///**
// * 类<code>RibbonConfig</code>
// * 用于：重新实现接口LoadBalancerRequestTransformer， 到达改造ServiceInstance request的目的。
// * <p>
// * .net / java 服务的接入点统一写入元数据：spring.cloud.consul.discovery.metadata.EntryPoint
// * 参照com.ztn.lbg.WebAutoConfiguration
// * <p>
// * 通过ribbon访问下游服务时改造ServiceInstance request
// *
// * @author zt19191
// * @version 1.0
// * @date 2021/11/4 15:06
// */
//@Configuration
//public class RibbonConfig {
//    @Bean
//    LoadBalancerRequestTransformer consulContextPathTransformer() {
//        return new LoadBalancerRequestTransformer() {
//            @Override
//            public HttpRequest transformRequest(HttpRequest request, ServiceInstance instance) {
////                Optional.ofNullable(instance.getMetadata().get(""))
//                if (instance instanceof RibbonLoadBalancerClient.RibbonServer) {
//                    // 默认 consulServer.getMetadata() 返回的是tags Map, 而非 meta Map.
//                    // spring.cloud.consul.discovery.tags-as-metadata=false
//
//                    String entryPoint = Optional.of(instance)
//                            .map(obj -> (RibbonLoadBalancerClient.RibbonServer) instance)
//                            .map(obj -> (ConsulServer) obj.getServer())
//                            .map(obj -> obj.getHealthService().getService().getMeta())
//                            .map(obj -> obj.get("EntryPoint"))
//                            .orElse("");
//                    if (StrUtil.isNotEmpty(entryPoint)) {
//                        String port = String.valueOf(instance.getPort());
//                        if (!entryPoint.endsWith(port) && entryPoint.indexOf(port) > 0) {
//                            return new CFLoadBalancerHttpRequestWrapper(request, entryPoint.substring(entryPoint.indexOf(port) + port.length()));
//                        }
//                    }
//                }
//                return request;
//            }
//        };
//    }
//
//    class CFLoadBalancerHttpRequestWrapper implements HttpRequest {
//        private HttpRequest request;
//        private String contextPath;
//
//        CFLoadBalancerHttpRequestWrapper(HttpRequest request, String contextPath) {
//            this.request = request;
//            this.contextPath = contextPath;
//        }
//
//        @Override
//        public String getMethodValue() {
//            return request.getMethodValue();
//        }
//
//        @SneakyThrows
//        @Override
//        public URI getURI() {
//            URI origURI = request.getURI();
//            return new URIBuilder(origURI).setPath(contextPath + origURI.getPath()).build();
//        }
//
//        @Override
//        public HttpHeaders getHeaders() {
//            return request.getHeaders();
//        }
//    }
//}
