package com.luoyx.hauyne.admin.sys.service;

import com.luoyx.hauyne.admin.api.sys.dto.UserFullNameDTO;
import com.luoyx.hauyne.admin.sys.entity.UserProfile;
import com.luoyx.hauyne.admin.sys.query.EmailUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.PhoneUniqueCheckQuery;
import com.luoyx.hauyne.mybatisplus.service.BaseService;
//import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;

import java.util.List;
import java.util.Set;

/**
 * @author admini
 */
public interface UserProfileService extends BaseService<UserProfile> {

    /**
     * 按用户id集合查询用户全名
     *
     * @param userIds 用户id 集合
     * @return
     */
    List<UserFullNameDTO> findFullNamesByUserIds(Set<Long> userIds);

    /**
     * 检查手机号是否唯一
     *
     * @param query 手机号唯一性校验查询条件
     * @return true=可用，false=已被占用
     */
    boolean checkPhoneUnique(PhoneUniqueCheckQuery query);

    /**
     * 检查邮箱是否唯一
     *
     * @param query 邮箱唯一性校验查询条件
     * @return true=可用，false=已被占用
     */
    boolean checkEmailUnique(EmailUniqueCheckQuery query);

}
