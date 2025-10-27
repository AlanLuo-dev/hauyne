package com.luoyx.hauyne.admin.sys.query;

import com.luoyx.hauyne.common.enums.EnableStatusEnum;
import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据字典类型 查询参数类
 *
 * @author LuoYingxiong
 */
@Getter
@Setter
public class DictTypeQuery extends PageQuery {

    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码")
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    private String dictTypeName;

    /**
     * 启用状态
     */
    @Schema(description = "启用状态【true=启用; false=禁用】")
    @EnumCheck(message = "启用状态的枚举值不合法", enumClazz = EnableStatusEnum.class, getterMethod = "getBoolValue")
    private Boolean enabled;
}
