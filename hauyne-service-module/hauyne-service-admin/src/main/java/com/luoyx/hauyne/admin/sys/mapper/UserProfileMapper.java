package com.luoyx.hauyne.admin.sys.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luoyx.hauyne.admin.api.sys.dto.UserFullNameDTO;
import com.luoyx.hauyne.admin.sys.entity.UserProfile;
import com.luoyx.hauyne.admin.sys.query.EmailUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.PhoneUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.response.UserEditFormVO;
import com.luoyx.hauyne.mybatisplus.mapper.GenericMapper;
//import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author admini
 */
public interface UserProfileMapper extends GenericMapper<UserProfile> {

    /**
     * 查询所有用户的id、真实姓名
     *
     * @param userIds 用户id 集合
     * @return
     */
    List<UserFullNameDTO> findFullNamesByUserIds(@Param("userIds") Set<Long> userIds);

    /**
     * 手机号唯一性校验
     *
     * @param query 手机号唯一性校验查询条件
     * @return 返回大于0 则表示手机号已存在，返回0 则表示手机号可用
     */
    default long countByPhone(PhoneUniqueCheckQuery query) {
        return this.selectCount(
                Wrappers.<UserProfile>lambdaQuery()
                        .eq(UserProfile::getPhone, query.getPhone())
                        .ne(query.getExcludeUserId() != null, UserProfile::getId, query.getExcludeUserId())
        );
    }

    /**
     * 邮箱唯一性校验
     *
     * @param query 邮箱唯一性校验查询条件
     * @return 返回大于0 则表示邮箱已存在，返回0 则表示邮箱可用
     */
    default long countByEmail(EmailUniqueCheckQuery query) {
        return this.selectCount(
                Wrappers.<UserProfile>lambdaQuery()
                        .eq(UserProfile::getEmail, query.getEmail())
                        .ne(query.getExcludeUserId() != null, UserProfile::getId, query.getExcludeUserId())
        );
    }

    /**
     * 按用户id查询 用户资料 以编辑（表单回显）
     *
     * @param id 用户id
     * @return 用户资料
     */
    UserEditFormVO.UserProfileDTO selectForUserEditForm(Long id);

}
