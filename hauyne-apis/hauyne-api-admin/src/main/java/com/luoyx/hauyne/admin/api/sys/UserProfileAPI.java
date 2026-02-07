package com.luoyx.hauyne.admin.api.sys;

import com.luoyx.hauyne.admin.api.sys.dto.UserFullNameDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

public interface UserProfileAPI {

    /**
     * 按用户id集合查询用户全名
     *
     * @param userIds 用户id 集合
     * @return
     */
    @GetMapping(value = "/sys/user-profiles/full-name")
    List<UserFullNameDTO> findFullNamesByUserIds(@RequestParam("userIds") Set<Long> userIds);
}
