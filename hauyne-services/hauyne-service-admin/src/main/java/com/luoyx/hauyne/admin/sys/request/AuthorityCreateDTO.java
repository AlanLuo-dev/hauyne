package com.luoyx.hauyne.admin.sys.request;

import com.luoyx.hauyne.admin.sys.enums.AuthorityTypeEnum;
import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;


/**
 * 创建权限 请求参数 VO 类
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
public class AuthorityCreateDTO {

    /**
     * 父权限id
     */
    @Schema(description = "父级权限id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long parentId;

    /**
     * 权限类型（menu=菜单，operation=操作,可扩展）
     */
    @Schema(description = "权限类型（menu=菜单，operation=操作,可扩展）")
    @NotBlank(message = "权限类型不能为空")
    @EnumCheck(message = "权限类型不合法", enumClazz = AuthorityTypeEnum.class, getterMethod = "getValue")
    private String authorityType;

    /**
     * 权限编码
     */
    @Schema(description = "权限编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限编码不能为空")
    private String authorityCode;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限名称不能为空")
    private String authorityName;

    /**
     * 图标CSS样式
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 请求路径
     */
    @Schema(description = "请求路径")
    private String path;

    /**
     * 排序(无符号)
     */
    @Schema(description = "排序（无符号）")
    @Range(min = 0, max = 255, message = "排序范围只能在0到255")
    private Integer sort;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
