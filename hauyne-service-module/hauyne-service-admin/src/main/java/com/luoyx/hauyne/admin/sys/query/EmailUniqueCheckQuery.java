package com.luoyx.hauyne.admin.sys.query;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 电子邮邮箱唯一性校验查询条件
 *
 * @author luo_yingxiong@163.com
 */
@Data
@NoArgsConstructor
public class EmailUniqueCheckQuery {

    /**
     * 要排除的用户Id（编辑用户的场景）
     */
    private Long excludeUserId;

    /**
     * 电子邮箱
     */
    private String email;

    public EmailUniqueCheckQuery(String email) {
        this.email = email;
    }

    public EmailUniqueCheckQuery(Long excludeUserId, String email) {
        this.excludeUserId = excludeUserId;
        this.email = email;
    }
}
