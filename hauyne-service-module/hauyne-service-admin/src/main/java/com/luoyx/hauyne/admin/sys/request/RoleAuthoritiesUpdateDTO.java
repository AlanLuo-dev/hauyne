package com.luoyx.hauyne.admin.sys.request;

import com.luoyx.hauyne.admin.api.sys.enums.YesNoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RoleAuthoritiesUpdateDTO {

    @Schema(description = "权限资源id数组")
    @NotNull(message = "权限资源id不能为空")
    private List<Long> authorityIds;

    @Schema(description = "权限菜单父子节点选中状态是否联动")
    @NotNull(message = "权限菜单父子节点选中状态是否联动 不能为空")
    private YesNoEnum authorityCheckLinkage;
}
