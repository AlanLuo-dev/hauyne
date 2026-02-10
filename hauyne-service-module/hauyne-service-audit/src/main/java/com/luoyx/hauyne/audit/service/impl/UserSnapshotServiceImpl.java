package com.luoyx.hauyne.audit.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoyx.hauyne.audit.entity.UserSnapshot;
import com.luoyx.hauyne.audit.mapper.UserSnapshotMapper;
import com.luoyx.hauyne.audit.service.UserSnapshotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息-快照表 服务实现类
 * </p>
 *
 * @author Alan.Luo
 * @since 2025-09-13
 */
@Slf4j
@Service
public class UserSnapshotServiceImpl extends ServiceImpl<UserSnapshotMapper, UserSnapshot> implements UserSnapshotService {

}
