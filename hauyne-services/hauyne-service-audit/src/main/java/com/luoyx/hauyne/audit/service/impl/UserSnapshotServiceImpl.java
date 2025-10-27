package com.luoyx.hauyne.audit.service.impl;


import com.luoyx.hauyne.audit.entity.UserSnapshot;
import com.luoyx.hauyne.audit.mapper.UserSnapshotMapper;
import com.luoyx.hauyne.usersnapshot.converter.UserSnapshotConverter;
import com.luoyx.hauyne.usersnapshot.service.AbstractUserSnapshotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

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
public class UserSnapshotServiceImpl extends AbstractUserSnapshotService<UserSnapshotMapper, UserSnapshot> {

    public UserSnapshotServiceImpl(UserSnapshotConverter converter) {
        super(converter);
    }

    @Override
    protected Supplier<UserSnapshot> entitySupplier() {
        return UserSnapshot::new;
    }
}
