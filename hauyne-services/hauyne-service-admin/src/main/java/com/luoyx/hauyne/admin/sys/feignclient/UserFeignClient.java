package com.luoyx.hauyne.admin.sys.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "service-audit")
public interface UserFeignClient {

    @GetMapping("/testRPC")
    String testRPC();
}
