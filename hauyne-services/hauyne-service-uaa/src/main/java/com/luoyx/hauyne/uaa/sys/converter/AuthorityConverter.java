package com.luoyx.hauyne.uaa.sys.converter;

import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.common.converter.BooleanYesNoConverter;
import com.luoyx.hauyne.security.pojo.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 权限类 实体转换接口
 * <p>
 * componentModel="spring" 表示使用spring依赖注入
 *
 * @author 罗英雄
 */
@Mapper(componentModel = "spring", imports = BooleanYesNoConverter.class)
public interface AuthorityConverter {


    @Mappings(value = {
            @Mapping(target = "title", source = "label"),
            @Mapping(target = "path", source = "routerLink"),
            @Mapping(target = "children", source = "items")
    })
    Menu toMenu(UserDTO.SideMenuDTO  sideMenuDTO);

    List<Menu> toMenuList(List<UserDTO.SideMenuDTO > sideMenuList);
}
