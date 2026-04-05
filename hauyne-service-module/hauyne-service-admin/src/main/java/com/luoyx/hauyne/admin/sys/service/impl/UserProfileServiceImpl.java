package com.luoyx.hauyne.admin.sys.service.impl;

import com.luoyx.hauyne.admin.api.sys.dto.UserFullNameDTO;
import com.luoyx.hauyne.admin.sys.entity.UserProfile;
import com.luoyx.hauyne.admin.sys.mapper.UserProfileMapper;
import com.luoyx.hauyne.admin.sys.service.UserProfileService;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author admini
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfileMapper, UserProfile> implements UserProfileService {

    /**
     * 按用户id集合查询用户全名
     *
     * @param userIds 用户id 集合
     * @return
     */
    @Override
    public List<UserFullNameDTO> findFullNamesByUserIds(Set<Long> userIds) {
        return baseMapper.findFullNamesByUserIds(userIds);
    }

    /**
     * 检查手机号是否唯一
     *
     * @param excludeUserId 要排除的用户Id（编辑用户的场景）
     * @param phone         手机号
     * @return true=可用，false=已被占用
     */
    @Override
    public boolean isPhoneUnique(Long excludeUserId, String phone) {
        return baseMapper.selectOneByPhone(excludeUserId, phone) == null;
    }

    /**
     * 检查邮箱是否唯一
     *
     * @param excludeUserId 要排除的用户Id（编辑用户的场景）
     * @param email         邮箱
     * @return true=可用，false=已被占用
     */
    @Override
    public boolean isEmailUnique(Long excludeUserId, String email) {
        return baseMapper.selectOneByEmail(excludeUserId, email) == null;
    }

}
