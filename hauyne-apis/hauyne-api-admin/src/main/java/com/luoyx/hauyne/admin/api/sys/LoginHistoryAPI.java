package com.luoyx.hauyne.admin.api.sys;

import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginHistoryAPI {

    /**
     * 保存登录日志
     *
     * @param saveLoginHistoryDTO 登录日志请求参数
     */
    @PostMapping(value = "/sys/login-histories")
    void save(@RequestBody SaveLoginHistoryDTO saveLoginHistoryDTO);
}
