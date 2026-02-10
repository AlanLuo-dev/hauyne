package com.luoyx.hauyne.admin.util;

import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单树工具类
 *
 * @author 罗英雄 luo_yingxiong@163.com
 */
@Slf4j
public class MenuTreeUtil {

    private List<UserDTO.SideMenuDTO> menuList = new ArrayList<>();

    public MenuTreeUtil(List<UserDTO.SideMenuDTO> menuList) {
        this.menuList = menuList;
    }

    /**
     * 建立树形结构
     *
     * @return
     */
    public List<UserDTO.SideMenuDTO> builTree() {
        List<UserDTO.SideMenuDTO> sideMenuDTOList = new ArrayList<>();
        for (UserDTO.SideMenuDTO parentNode : getRootNode()) {
            parentNode = buildChilTree(parentNode);
            sideMenuDTOList.add(parentNode);
        }

        return sideMenuDTOList;
    }

    /**
     * 递归，建立子树形结构
     *
     * @param parentNode 父节点
     * @return
     */
    private UserDTO.SideMenuDTO buildChilTree(UserDTO.SideMenuDTO parentNode) {
        List<UserDTO.SideMenuDTO> chilMenus = new ArrayList<>();
        for (UserDTO.SideMenuDTO menuNode : menuList) {
            if (menuNode.getParentId().equals(parentNode.getId())) {
                chilMenus.add(buildChilTree(menuNode));
            }
        }
        parentNode.setItems(chilMenus);

        return parentNode;
    }

    /**
     * 获取根节点
     *
     * @return
     */
    private List<UserDTO.SideMenuDTO> getRootNode() {
        return menuList.stream()
                .filter(item -> item.getParentId() == 0)
                .collect(Collectors.toList());
    }
}
