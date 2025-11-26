package com.luoyx.hauyne.admin.sys.service.impl;


import com.luoyx.hauyne.admin.amqp.producer.UserSnapshotProducer;
import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.admin.api.sys.query.LoginLookupQuery;
import com.luoyx.hauyne.admin.sys.converter.UserConverter;
import com.luoyx.hauyne.admin.sys.converter.UserProfileConverter;
import com.luoyx.hauyne.admin.sys.converter.UserSnapshotConverter;
import com.luoyx.hauyne.admin.sys.entity.User;
import com.luoyx.hauyne.admin.sys.entity.UserProfile;
import com.luoyx.hauyne.admin.sys.entity.UserSnapshot;
import com.luoyx.hauyne.admin.sys.enums.AccountNonExpiredEnum;
import com.luoyx.hauyne.admin.sys.enums.AccountNonLockedEnum;
import com.luoyx.hauyne.admin.sys.enums.CredentialsNonExpiredEnum;
import com.luoyx.hauyne.admin.sys.enums.EnabledEnum;
import com.luoyx.hauyne.admin.sys.enums.GenderEnum;
import com.luoyx.hauyne.admin.sys.enums.PrivateKeyRedisKeyEnum;
import com.luoyx.hauyne.admin.sys.event.UserSnapshotEvent;
import com.luoyx.hauyne.admin.sys.mapper.UserMapper;
import com.luoyx.hauyne.admin.sys.query.EmailUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.PhoneUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.query.UserPageQuery;
import com.luoyx.hauyne.admin.sys.query.UsernameUniqueCheckQuery;
import com.luoyx.hauyne.admin.sys.request.ModifyPasswordDTO;
import com.luoyx.hauyne.admin.sys.request.ResetPasswordDTO;
import com.luoyx.hauyne.admin.sys.request.UserCreateDTO;
import com.luoyx.hauyne.admin.sys.request.UserUpdateDTO;
import com.luoyx.hauyne.admin.sys.response.CreatedUserVO;
import com.luoyx.hauyne.admin.sys.response.UserAutoCompleteVO;
import com.luoyx.hauyne.admin.sys.response.UserEditFormVO;
import com.luoyx.hauyne.admin.sys.response.UserPageResultVO;
import com.luoyx.hauyne.admin.sys.service.AuthorityService;
import com.luoyx.hauyne.admin.sys.service.RoleService;
import com.luoyx.hauyne.admin.sys.service.UserProfileService;
import com.luoyx.hauyne.admin.sys.service.UserRoleService;
import com.luoyx.hauyne.admin.sys.service.UserService;
import com.luoyx.hauyne.admin.sys.service.UserSnapshotService;
import com.luoyx.hauyne.framework.utils.SignUtils;
import com.luoyx.hauyne.framework.utils.rsa.RSAUtil;
import com.luoyx.hauyne.mybatisplus.dto.PageResult;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import com.luoyx.hauyne.security.util.SecurityUtils;
import com.luoyx.hauyne.usersnapshot.enums.EventType;
import com.luoyx.hauyne.web.exception.ResourceNotFoundException;
import com.luoyx.hauyne.web.exception.ValidateException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


/**
 * @author 罗英雄
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder bcryptPasswordEncoder;
    private final UserConverter userConverter;
    private final UserProfileConverter userProfileConverter;
    private final UserSnapshotConverter userSnapshotConverter;
    private final UserProfileService userProfileService;
    private final UserSnapshotService userSnapshotService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final AuthorityService authorityService;
    private final AsyncTaskExecutor taskExecutor;
    private final UserSnapshotProducer userSnapshotProducer;
    //    private final UserSnapshotConverter userSnapshotConverter;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value(value = "${login.lookup.secret}")
    private String loginLookupSecret;

    @Override
    public PageResult<UserPageResultVO> findPage(UserPageQuery query) {
        PageResult<UserPageResultVO> pageResult = super.findPage(query);
        List<UserPageResultVO> rows = pageResult.getRows();
        for (UserPageResultVO item : rows) {
            item.setAccountNonExpired(AccountNonExpiredEnum.MAP.get(item.getAccountNonExpiredValue()));
            item.setAccountNonLocked(AccountNonLockedEnum.MAP.get(item.getAccountNonLockedValue()));
            item.setCredentialsNonExpired(CredentialsNonExpiredEnum.MAP.get(item.getCredentialsNonExpiredValue()));
            item.setEnabled(EnabledEnum.MAP.get(item.getEnabledValue()));
            item.setGender(GenderEnum.MAP.get(item.getGenderValue()));
        }

        return pageResult;
    }

    /**
     * 登录查询
     *
     * @param query 登录查询条件
     * @return 用户信息
     */
    @Override
    public UserDTO loginLookup(LoginLookupQuery query) {

        final String username = query.getUsername();
        final Long timestamp = query.getTimestamp();
        final String sign = query.getSign();

        // 防重放攻击（5分钟有效）
        long now = System.currentTimeMillis();
        if (Math.abs(now - timestamp) > 5 * 60 * 1000) {
            throw new AccessDeniedException("请求过期");
        }

        // 验证签名
        String baseString = username + timestamp;
        String expectedSign = SignUtils.hmacSHA256(loginLookupSecret, baseString);
        if (!expectedSign.equalsIgnoreCase(sign)) {
            throw new AccessDeniedException("签名不合法");
        }

        UserDTO userDTO = baseMapper.findByUserName(username);
        UserDTO.UserBaseInfo userBaseInfo;
        if (null != userDTO && null != (userBaseInfo = userDTO.getUserBaseInfo())) {
            final Long userId = userBaseInfo.getId();

            CompletableFuture<Void> authoritiesFuture = CompletableFuture.runAsync(() -> {
                userDTO.setAuthorities(authorityService.findAuthoritiesByUserId(userId));
            }, taskExecutor);

            CompletableFuture<Void> menuFuture = CompletableFuture.runAsync(() -> {
                userDTO.setSideMenuList(authorityService.buildMenuTree(userId));
            }, taskExecutor);

            // 等待两个任务都完成
            CompletableFuture.allOf(authoritiesFuture, menuFuture).join();
        }

        return userDTO;
    }


    @Override
    public void modifyPassword(ModifyPasswordDTO modifyPasswordDTO) throws Exception {
        final String redisKey = PrivateKeyRedisKeyEnum.MODIFY_PASSWORD.getKeyPrefix() + modifyPasswordDTO.getKey();

        // 从redis获取RSA私钥
        final String rsaPrivateKey = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isBlank(rsaPrivateKey)) {
            log.error("缺失RSA私钥");
            throw new ValidateException("缺失key");
        }

        try {
            // RSA 私钥解密
            final String newPassword = RSAUtil.decryptByPrivateKey(modifyPasswordDTO.getNewPassword(), rsaPrivateKey);
            final String confirmPassword = RSAUtil.decryptByPrivateKey(modifyPasswordDTO.getConfirmPassword(), rsaPrivateKey);
            if (!newPassword.equals(confirmPassword)) {
                throw new ValidateException("两次输入的密码不一致");
            }

            User user = baseMapper.selectById(SecurityUtils.getCurrentSysUserId());
            if (null == user) {
                throw new ResourceNotFoundException("用户不存在");
            }

            String oldPassword = RSAUtil.decryptByPrivateKey(modifyPasswordDTO.getOldPassword(), rsaPrivateKey);
            if (!bcryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
                throw new ValidateException("旧密码不正确");
            }
            user.setPassword(bcryptPasswordEncoder.encode(newPassword));
            baseMapper.updateById(user);
        } catch (Exception e) {
            throw e;
        } finally {

            // 删除redis中的RSA私钥
            redisTemplate.delete(redisKey);
        }
    }

    /**
     * 按关键词 查询自动完成数据
     *
     * @param keyword 关键词
     * @return
     */
    @Override
    public List<UserAutoCompleteVO> findSuggestions(String keyword) {
        return baseMapper.findSuggestions(keyword);
    }

    /**
     * 新增用户
     * <p>
     * 控制在一个事务中，避免数据不一致
     *
     * @param userCreateDTO 表单参数
     * @return 新增的用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreatedUserVO create(UserCreateDTO userCreateDTO) {
        checkFormData(userCreateDTO);

        User user = userConverter.toUser(userCreateDTO);
        user.setPassword(bcryptPasswordEncoder.encode(userCreateDTO.getPassword()));
        user.setAccountNonExpired(AccountNonExpiredEnum.NORMAL.getValue());
        user.setAccountNonLocked(AccountNonLockedEnum.NORMAL.getValue());
        user.setCredentialsNonExpired(CredentialsNonExpiredEnum.NORMAL.getValue());
        user.setEnabled(EnabledEnum.ENABLED.getValue());
        baseMapper.insert(user);
        final Long userId = user.getId();

        Set<Long> roleIdSet = new HashSet<>(userCreateDTO.getRoleIds());
        if (userCreateDTO.getRoleIds().size() > roleIdSet.size()) {
            throw new ValidateException("选择的角色不能重复");
        }

        // 新增用户角色 关联关系
        userRoleService.saveUserRole(userId, roleIdSet);

        UserProfile userProfile = userProfileConverter.toUserProfile(userCreateDTO.getProfile());
        userProfile.setId(userId);
        userProfileService.save(userProfile);

        // 新增用户快照
        UserSnapshot userSnapshot = userSnapshotConverter.toUserSnapshot(userProfile);
        userSnapshotService.save(userSnapshot);

        applicationEventPublisher.publishEvent(new UserSnapshotEvent(Collections.singletonList(userSnapshot), EventType.CREATE));

        return userConverter.toCreatedUserVO(user, userProfile);
    }

    /**
     * 校验表单数据
     *
     * @param userCreateDTO 表单参数
     */
    private void checkFormData(UserCreateDTO userCreateDTO) {

        // 校验用户名是否存在
        final String username = userCreateDTO.getUsername();
        boolean result = checkUserNameUnique(new UsernameUniqueCheckQuery(username));
        if (!result) {
            throw new ValidateException("用户名（" + username + "）已存在");
        }

        // 校验手机号是否存在
        final String phone = userCreateDTO.getProfile().getPhone();
        result = userProfileService.checkPhoneUnique(new PhoneUniqueCheckQuery(phone));
        if (!result) {
            throw new ValidateException("手机号（" + phone + "）已存在");
        }

        // 校验邮箱是否存在
        final String email = userCreateDTO.getProfile().getEmail();
        result = userProfileService.checkEmailUnique(new EmailUniqueCheckQuery(email));
        if (!result) {
            throw new ValidateException("邮箱（" + email + "）已存在");
        }

        // 校验角色是否存在
        roleService.checkRolesExist(new HashSet<>(userCreateDTO.getRoleIds()));
    }

    /**
     * 用户名唯一性校验
     *
     * @param query 用户名唯一性校验查询条件
     * @return true 表示用户名可用，false 表示用户名已存在
     */
    @Override
    public boolean checkUserNameUnique(UsernameUniqueCheckQuery query) {
        return baseMapper.countByUserName(query) == 0;
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户id集合
     */
    @Override
    @Transactional
    public void deleteByIds(List<Long> userIds) {
        final Long currentSysUserId = SecurityUtils.getCurrentSysUserId();
        if (userIds.contains(currentSysUserId)) {
            throw new ValidateException("你不能删除自己");
        }

        List<User> users = baseMapper.selectByIds(userIds);
        for (User user : users) {
            if ("admin".equals(user.getUsername())) {
                throw new ValidateException("admin用户不能删除");
            }
        }

        // 删除之前，更新用户快照
        List<UserSnapshot> userSnapshotList = userProfileService.listByIds(userIds).stream()
                .map(item -> {
                    UserSnapshot userSnapshot = new UserSnapshot();
                    userSnapshot.setId(item.getId());
                    userSnapshot.setRealName(item.getRealName() + "（已删除）");
                    userSnapshot.setNickname(item.getNickname() + "（已删除）");
                    userSnapshot.setAvatar(item.getAvatar());
                    userSnapshot.setLastUpdatedTime(item.getLastUpdatedTime());

                    return userSnapshot;
                })
                .toList();
        userSnapshotService.updateBatchById(userSnapshotList);

        // 删除用户
        baseMapper.deleteByIds(userIds);
        userProfileService.removeByIds(userIds);
        userRoleService.deleteUserRoleByUserIds(userIds);

        // 发送已更新用户的快照消息
        applicationEventPublisher.publishEvent(new UserSnapshotEvent(userSnapshotList, EventType.DELETE));
    }

    /**
     * 用户 表单回显
     *
     * @param id 用户id
     * @return 用户详情
     */
    @Override
    public UserEditFormVO findUserForEdit(Long id) {
//        User user = baseMapper.selectById(id);
//        UserProfile userProfile = userProfileService.getById(id);
//
//        return userConverter.toUserDetailsVO(user, userProfile);
        return baseMapper.selectForUserEditForm(id);
    }

    /**
     * 修改用户信息
     *
     * @param userUpdateDTO 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateDTO userUpdateDTO) {
        final Long userId = userUpdateDTO.getId();
        User user = userConverter.toUser(userUpdateDTO);
        UserProfile userProfile = userProfileConverter.toUserProfile(userUpdateDTO.getProfile());
        userProfile.setId(userId);

        baseMapper.updateById(user);
        userProfileService.updateById(userProfile);

        // 更新用户角色 关联关系
        Set<Long> roleIdSet = new HashSet<>(userUpdateDTO.getRoleIds());
        if (userUpdateDTO.getRoleIds().size() > roleIdSet.size()) {
            throw new ValidateException("选择的角色不能重复");
        }
        userRoleService.updateUserRoleByUserId(userId, roleIdSet);

        // 更新用户快照
        UserSnapshot userSnapshot = userSnapshotConverter.toUserSnapshot(userProfile);
        userSnapshotService.updateById(userSnapshot);

        // 发送已更新用户的快照消息
        applicationEventPublisher.publishEvent(new UserSnapshotEvent(Collections.singletonList(userSnapshot), EventType.UPDATE));
    }

    /**
     * 重置密码
     *
     * @param resetPasswordDTO 重置密码DTO
     */
    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        // 从redis中获取RSA私钥
        final String redisKey = PrivateKeyRedisKeyEnum.RESET_PASSWORD.getKeyPrefix() + resetPasswordDTO.getKey();
        String rsaPrivateKey = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isBlank(rsaPrivateKey)) {
            throw new ValidateException("私钥不存在");
        }
        String decryptPassword;
        try {
            decryptPassword = RSAUtil.decryptByPrivateKey(resetPasswordDTO.getEncryptPassword(), rsaPrivateKey);
        } catch (Exception e) {
            throw new ValidateException("解密失败");
        }
        final Long userId = resetPasswordDTO.getUserId();
        if (null == baseMapper.selectById(userId)) {
            throw new ResourceNotFoundException("用户不存在");
        }
        String bcryptPassword = bcryptPasswordEncoder.encode(decryptPassword);
        baseMapper.resetPassword(userId, bcryptPassword);
    }
}
