package com.luoyx.hauyne.admin.sys.request;

import com.luoyx.hauyne.validation.group.sequence.First;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 修改角色 表单数据VO 类
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
public class RoleUpdateDTO extends RoleCreateDTO {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @NotNull(message = "Id不能为NULL", groups = First.class)
    private Long id;

}
