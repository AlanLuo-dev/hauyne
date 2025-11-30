package com.luoyx.hauyne.admin.sys.query;

import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 用户查询条件
 *
 * @author 罗英雄
 */
@Getter
@Setter
@ToString
public class UserPageQuery extends PageQuery {

    @Schema(hidden = true)
    private Long currentUserId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 性别(1=男 0=女)
     */
    @Schema(description = "性别(1=男 0=女)")
    private Integer gender;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 是否可用（1=是 0=否）
     */
    @Schema(description = "是否可用（true=是；false=否）")
    private Boolean enabled;

    @Schema(description = "创建时间（开始时间）")
    private LocalDateTime startCreatedTime;

    @Schema(description = "创建时间（结束时间）")
    private LocalDateTime endCreatedTime;
}
