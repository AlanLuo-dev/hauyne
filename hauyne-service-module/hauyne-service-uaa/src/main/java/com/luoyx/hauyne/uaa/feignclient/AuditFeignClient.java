package com.luoyx.hauyne.uaa.feignclient;

import com.luoyx.hauyne.audit.api.AuditAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "service-audit")
public interface AuditFeignClient extends AuditAPI {


}
