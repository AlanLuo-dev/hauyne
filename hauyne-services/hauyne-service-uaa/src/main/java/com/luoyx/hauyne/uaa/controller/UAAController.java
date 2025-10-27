package com.luoyx.hauyne.uaa.controller;

import com.luoyx.hauyne.uaa.api.UAAAPI;
import com.luoyx.hauyne.uaa.feignclient.AuditFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ex-luoyingxiong
 * @date 2020/7/29 10:38
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UAAController implements UAAAPI {

    private final AuditFeignClient auditFeignClient;

    @Override
    @Operation(summary = "测试接口")
    @GetMapping(value = "/test-call-uaa")
    public Map<String, String> testCallUAA() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        System.out.println("请求头Authorization: " + request.getHeader("Authorization"));

        Map<String, String> map = new HashMap<>();
        map.put("name", "这是来自UAA 的数据");
        map.putAll(auditFeignClient.testCallAudit());

        return map;
    }


}
