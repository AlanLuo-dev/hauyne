package com.luoyx.hauyne.admin.sys.request;

import com.luoyx.hauyne.admin.api.sys.enums.AuthorityTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * 创建权限 请求参数 VO 类
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
public class AuthorityUpdateDTO {

    @Schema(description = "主键Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Id不能为空")
    private Long id;

    @Schema(description = "父权限id【不传递时，视为根节点】", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long parentId;

    @Schema(description = "权限类型")
    @NotNull(message = "权限类型不能为空")
    private AuthorityTypeEnum authorityType;

    @Schema(description = "权限编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限编码不能为空")
    private String authorityCode;

    @Schema(description = "权限名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限名称不能为空")
    private String authorityName;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "请求路径")
    private String path;

    @Schema(description = "排序（无符号）")
    @Range(min = 0, max = 255, message = "排序范围只能在0到255")
    private Integer sort;

    @Schema(description = "备注")
    @Length(max = 100, message = "备注不能超过100个字")
    private String remark;
}
