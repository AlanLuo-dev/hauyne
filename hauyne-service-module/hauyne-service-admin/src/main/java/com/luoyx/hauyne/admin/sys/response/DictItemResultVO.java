package com.luoyx.hauyne.admin.sys.response;

import com.baomidou.mybatisplus.annotation.TableField;
import com.luoyx.hauyne.admin.api.sys.enums.YesNoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 数据字典选项 维护功能的VO类
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-15
 */
@Getter
@Setter
@ToString
public class DictItemResultVO {

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "字典值编码")
    private String dictItemCode;

    @Schema(description = "字典值名称")
    private String dictItemName;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "启用状态")
    private Boolean enabled;

    @Schema(description = "是否系统内置")
    private YesNoEnum builtin;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建人姓名")
    private String createdBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "最后修改人的姓名")
    private String lastUpdatedBy;

    @Schema(description = "最后修改时间")
    private LocalDateTime lastUpdatedTime;
}
