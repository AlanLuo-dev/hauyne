package com.luoyx.hauyne.audit.feignclient;

import com.luoyx.hauyne.uaa.api.UAAAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "service-uaa")
public interface UAAFeignClient extends UAAAPI {


}
