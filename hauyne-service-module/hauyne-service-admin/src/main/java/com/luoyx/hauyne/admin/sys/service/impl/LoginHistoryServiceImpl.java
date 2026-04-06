package com.luoyx.hauyne.admin.sys.service.impl;

import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
import com.luoyx.hauyne.admin.sys.converter.LoginHistoryConverter;
import com.luoyx.hauyne.admin.sys.entity.LoginHistory;
import com.luoyx.hauyne.admin.sys.mapper.LoginHistoryMapper;
import com.luoyx.hauyne.admin.sys.service.LoginHistoryService;
import com.luoyx.hauyne.admin.sys.service.UserService;
import com.luoyx.hauyne.mybatisplus.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 登录历史表 服务实现类
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
@Service
@RequiredArgsConstructor
public class LoginHistoryServiceImpl extends BaseServiceImpl<LoginHistoryMapper, LoginHistory> implements LoginHistoryService {

    private final LoginHistoryConverter loginHistoryConverter;
    private final UserService userService;

    /**
     * 保存登录日志
     *
     * @param saveLoginHistoryDTO 登录日志请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SaveLoginHistoryDTO saveLoginHistoryDTO) {
        baseMapper.insert(loginHistoryConverter.toEntity(saveLoginHistoryDTO));

        // 更新用户最近登录时间
        userService.updateLastLoginTime(saveLoginHistoryDTO.getUserId(), saveLoginHistoryDTO.getLoginTime());
    }
}
