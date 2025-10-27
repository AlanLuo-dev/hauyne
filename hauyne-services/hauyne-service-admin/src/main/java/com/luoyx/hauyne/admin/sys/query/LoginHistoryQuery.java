package com.luoyx.hauyne.admin.sys.query;


import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录历史查询参数
 *
 * @author 罗英雄
 * @date 2021/8/29 21:27
 */
@Getter
@Setter
public class LoginHistoryQuery extends PageQuery {

    /**
     * 类型（1=登录；0=注销）
     */
    @Schema(description = "类型（1=登录；0=注销）")
    private Integer type;

    /**
     * 登录/注销结果（1=登录成功，2=登录失败，3=注销成功，4=注销失败）
     */
    @Schema(description = "登录/注销结果（1=登录成功，2=登录失败，3=注销成功，4=注销失败）")
    private Integer result;

    /**
     * 用户登录名
     */
    @Schema(description = "用户登录名")
    private String username;

    /**
     * 客户端浏览器
     */
    @Schema(description = "客户端浏览器")
    private String browser;

    /**
     * 客户端操作系统名称
     */
    @Schema(description = "客户端操作系统名称")
    private String osName;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private String endTime;
}
