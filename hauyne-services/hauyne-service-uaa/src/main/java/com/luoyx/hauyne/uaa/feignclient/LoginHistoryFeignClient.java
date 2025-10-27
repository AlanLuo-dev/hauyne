package com.luoyx.hauyne.uaa.feignclient;

import com.luoyx.hauyne.admin.api.sys.LoginHistoryAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "service-admin")
public interface LoginHistoryFeignClient extends LoginHistoryAPI {
}
