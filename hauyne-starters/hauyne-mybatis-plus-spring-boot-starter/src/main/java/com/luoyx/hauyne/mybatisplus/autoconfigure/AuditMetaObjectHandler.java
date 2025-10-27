package com.luoyx.hauyne.mybatisplus.autoconfigure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.luoyx.hauyne.security.context.RequestDateTimeHolder;
import com.luoyx.hauyne.security.context.UserContextHolder;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 该类是一个用于重写 MyBatis Plus 的 MetaObjectHandler 的组件，
 * 它用于处理实体类中的公共字段（例如创建人、创建时间、修改人、修改时间等）的值填充。
 * 在插入和更新实体类时，该类通过调用 MyBatis Plus 的 setFieldValByName 方法设置公共字段的值。
 * 同时，该类还使用了 Spring Security 的 getCurrentSysUserId 方法获取当前用户的 id，
 * 并将其设置为实体类中的 created_by 和 last_updated_by 字段的值。
 * 同时，该类还使用 LocalDateTime 的 now 方法获取当前时间，并将其设置为实体类中的 created_time 和 last_updated_time 字段的值。
 *
 * @author 1564469545@qq.com
 * @date 2023/3/31 22:09
 */
public class AuditMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATED_BY = "createdBy";
    private static final String CREATED_TIME = "createdTime";
    private static final String LAST_UPDATED_BY = "lastUpdatedBy";
    private static final String LAST_UPDATED_TIME = "lastUpdatedTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        final Long userId = UserContextHolder.getUserId();
        final LocalDateTime nowTime = RequestDateTimeHolder.get();

        // 如果创建人为空，则进行填充
        Object createdBy = getFieldValByName(CREATED_BY, metaObject);
        if (null == createdBy) {
            this.setFieldValByName(CREATED_BY, userId, metaObject);
        }

        // 如果创建时间为空，则进行填充
        Object createdTime = getFieldValByName(CREATED_TIME, metaObject);
        if (null == createdTime) {
            this.setFieldValByName(CREATED_TIME, nowTime, metaObject);
        }

        // 如果修改人为空，则进行填充
        Object lastUpdatedBy = getFieldValByName(LAST_UPDATED_BY, metaObject);
        if (null == lastUpdatedBy) {
            this.setFieldValByName(LAST_UPDATED_BY, userId, metaObject);
        }

        // 如果修改时间为空，则进行填充
        Object lastUpdatedTime = getFieldValByName(LAST_UPDATED_TIME, metaObject);
        if (null == lastUpdatedTime) {
            this.setFieldValByName(LAST_UPDATED_TIME, nowTime, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        // 如果修改人为空，则进行填充
        Object lastUpdatedBy = getFieldValByName(LAST_UPDATED_BY, metaObject);
        if (null == lastUpdatedBy) {
            this.setFieldValByName(LAST_UPDATED_BY, UserContextHolder.getUserId(), metaObject);
        }

        // 如果修改时间为空，则进行填充
        Object lastUpdatedTime = getFieldValByName(LAST_UPDATED_TIME, metaObject);
        if (null == lastUpdatedTime) {
            this.setFieldValByName(LAST_UPDATED_TIME, RequestDateTimeHolder.get(), metaObject);
        }
    }
}

