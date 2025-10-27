package com.luoyx.hauyne.admin.sys.response;

import com.luoyx.hauyne.commons.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典类型 VO类 维护功能
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DictTypeDetailVO extends BaseVO {

    /**
     * Id
     */
    @Schema(description = "id")
    private Long id;

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
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 是否已启用
     */
    @Schema(description = "是否已启用【true=已启用，false=已禁用】")
    private Boolean enabled;

}
