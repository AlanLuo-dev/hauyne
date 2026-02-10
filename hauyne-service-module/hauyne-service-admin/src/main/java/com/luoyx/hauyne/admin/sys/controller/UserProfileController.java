package com.luoyx.hauyne.admin.sys.controller;

import com.luoyx.hauyne.admin.api.sys.UserProfileAPI;
import com.luoyx.hauyne.admin.api.sys.dto.UserFullNameDTO;
import com.luoyx.hauyne.admin.sys.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/sys/user-profiles")
@RequiredArgsConstructor
public class UserProfileController implements UserProfileAPI {

    private final UserProfileService userProfileService;

    /**
     * 按用户id集合查询用户全名
     *
     * @param userIds 用户id 集合
     * @return
     */
    @Override
    @Operation(summary = "按用户id集合查询用户全名")
    @GetMapping(value = "/full-name")
    public List<UserFullNameDTO> findFullNamesByUserIds(Set<Long> userIds) {
        return userProfileService.findFullNamesByUserIds(userIds);
    }
}
