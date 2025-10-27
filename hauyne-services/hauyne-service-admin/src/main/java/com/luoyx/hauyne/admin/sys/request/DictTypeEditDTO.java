package com.luoyx.hauyne.admin.sys.request;

import com.luoyx.hauyne.framework.utils.JsonUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 编辑数据字典类型 表单数据封装 VO类
 *
 * @author LuoYingxiong
 */
@Getter
@Setter
public class DictTypeEditDTO extends DictTypeCreateDTO {

    /**
     * Id
     */
    @Schema(description = "Id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用【true=启用；false=禁用】")
    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;

    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }


}
