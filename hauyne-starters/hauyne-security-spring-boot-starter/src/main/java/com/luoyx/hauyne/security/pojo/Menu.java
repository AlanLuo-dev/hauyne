package com.luoyx.hauyne.security.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * ng-zorro的导航菜单属性
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu implements Serializable{

    private static final long serialVersionUID = 7445577652913780993L;

    private Integer level;
    private String name;
    private String title;
    private String path;
    private Integer order;
    /**
     * 是否禁用菜单 默认为false
     */
    private boolean disabled;

    /**
     * 图标
     */
    private String icon;
    private boolean selected;

    /**
     * 子菜单
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> children;
}
