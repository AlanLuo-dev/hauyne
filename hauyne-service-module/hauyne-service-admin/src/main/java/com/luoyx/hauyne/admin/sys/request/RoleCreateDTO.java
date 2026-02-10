package com.luoyx.hauyne.admin.sys.request;

import com.luoyx.hauyne.validation.group.sequence.First;
import com.luoyx.hauyne.validation.group.sequence.Second;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 新增角色 表单数据DTO类
 *
 * @author luoyingxiong
 */
@Data
public class RoleCreateDTO {

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @NotBlank(message = "角色编码必填", groups = First.class)
    @Length(max = 32, message = "角色编码不能超过32个字", groups = Second.class)
    private String roleCode;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称必填", groups = First.class)
    @Length(max = 50, message = "角色名称不能超过50个字", groups = Second.class)
    private String roleName;
}
