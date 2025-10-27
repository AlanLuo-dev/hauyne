package com.luoyx.hauyne.admin.api.sys;

import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.admin.api.sys.query.LoginLookupQuery;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserAPI {

    String SYS_USERS = "/sys/users";

    /**
     * 登录查询 URL
     */
    String LOGIN_LOOKUP = SYS_USERS + "/login-lookup";

    /**
     * 登录查询
     *
     * @param loginLookupQuery 登录查询条件
     * @return 用户信息
     */
    @GetMapping(value = LOGIN_LOOKUP)
    UserDTO loginLookup(@SpringQueryMap LoginLookupQuery loginLookupQuery);
}
