package com.luoyx.hauyne.audit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.usersnapshot.entity.BaseUserSnapshot;

/**
 * <p>
 * 审计日志微服务的 用户信息-快照表
 * </p>
 *
 * @author Alan.Luo
 * @since 2025-09-13
 */
@TableName("hyn_audit_user_snapshot")
public class UserSnapshot extends BaseUserSnapshot<UserSnapshot> {
}
