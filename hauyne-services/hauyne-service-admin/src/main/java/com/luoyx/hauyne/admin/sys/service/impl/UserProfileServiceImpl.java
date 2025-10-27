package com.luoyx.hauyne.admin.sys.service.impl;

import com.luoyx.hauyne.admin.api.sys.dto.UserFullNameDTO;
import com.luoyx.hauyne.admin.sys.entity.UserProfile;
import com.luoyx.hauyne.admin.sys.mapper.UserProfileMapper;
import com.luoyx.hauyne.admin.sys.query.EmailUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.PhoneUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.service.UserProfileService;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
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
     * @param query 手机号唯一性校验查询条件
     * @return true=可用，false=已被占用
     */
    @Override
    public boolean checkPhoneUnique(PhoneUniqueCheckQuery query) {
        return baseMapper.countByPhone(query) == 0;
    }

    /**
     * 检查邮箱是否唯一
     *
     * @param query 邮箱唯一性校验查询条件
     * @return true=可用，false=已被占用
     */
    @Override
    public boolean checkEmailUnique(EmailUniqueCheckQuery query) {
        return baseMapper.countByEmail(query) == 0;
    }

    /**
     * 查询所有用户快照消息
     *
     * @return 用户快照消息列表
     */
    @Override
    public List<UserSnapshotMessage> findUserSnapshots() {
        return baseMapper.findUserSnapshots();
    }
}
