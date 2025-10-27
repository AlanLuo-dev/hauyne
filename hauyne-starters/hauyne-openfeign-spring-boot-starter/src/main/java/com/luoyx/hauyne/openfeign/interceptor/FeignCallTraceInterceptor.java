package com.luoyx.hauyne.openfeign.interceptor;

import com.luoyx.hauyne.api.FeignCallRecord;
import com.luoyx.hauyne.feign.MsStackHolder;
import feign.MethodMetadata;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class FeignCallTraceInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String serviceId;

    @Override
    public void apply(RequestTemplate template) {
        // 获取当前服务名（本地服务）
        String currentService = serviceId;

        // 获取目标服务名
        String targetService = extractServiceName(template);

        // 获取调用方法（接口名 + 方法）
        String methodSignature = extractMethodSignature(template);

        // 构造调用记录
        MsStackHolder.set(new FeignCallRecord(
                targetService,
                template.feignTarget().url(), // or other target host info
                methodSignature
        ));

//        MsStackHolder.pushCallRecord(new FeignCallRecord(
//                targetService,
//                template.feignTarget().url(), // or other target host info
//                methodSignature
//        ));
    }

    private String extractServiceName(RequestTemplate template) {
        return template.feignTarget().name(); // service-uaa, service-audit
    }

    private String extractMethodSignature(RequestTemplate template) {
        MethodMetadata metadata = template.methodMetadata();
        return metadata.configKey(); // 类名#方法名(参数类型)，示例中你已经有了
    }
}

