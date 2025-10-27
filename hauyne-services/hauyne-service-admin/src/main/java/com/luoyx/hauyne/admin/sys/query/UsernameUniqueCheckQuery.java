package com.luoyx.hauyne.admin.sys.query;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户名唯一性校验 查询条件
 *
 * @author luo_yingxiong@163.com
 */
@Data
@NoArgsConstructor
public class UsernameUniqueCheckQuery {

    /**
     * 要排除的用户Id（编辑用户的场景）
     */
    private Long excludeUserId;

    /**
     * 用户名
     */
    private String username;

    public UsernameUniqueCheckQuery(String username) {
        this.username = username;
    }

    public UsernameUniqueCheckQuery(Long excludeUserId, String username) {
        this.excludeUserId = excludeUserId;
        this.username = username;
    }
}
