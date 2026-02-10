package com.luoyx.hauyne.admin.sys.query;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 手机号唯一性校验查询条件
 *
 * @author luo_yingxiong@163.com
 */
@Data
@NoArgsConstructor
public class PhoneUniqueCheckQuery {

    /**
     * 要排除的用户Id（编辑用户的场景）
     */
    private Long excludeUserId;

    /**
     * 手机号
     */
    private String phone;

    public PhoneUniqueCheckQuery(String phone) {
        this.phone = phone;
    }

    public PhoneUniqueCheckQuery(Long excludeUserId, String phone) {
        this.excludeUserId = excludeUserId;
        this.phone = phone;
    }
}
