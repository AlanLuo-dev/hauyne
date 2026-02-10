package com.luoyx.hauyne.uaa.feignclient;

import com.luoyx.hauyne.admin.api.sys.UserAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "service-admin")
public interface UserFeignClient extends UserAPI {
}
