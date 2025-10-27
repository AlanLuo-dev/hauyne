package com.luoyx.hauyne.cache.client;

import com.luoyx.hauyne.admin.api.sys.UserProfileAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "service-admin")
public interface UserProfileClient extends UserProfileAPI {
}
