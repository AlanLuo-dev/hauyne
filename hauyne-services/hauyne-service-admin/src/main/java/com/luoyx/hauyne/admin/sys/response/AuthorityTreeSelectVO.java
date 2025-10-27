package com.luoyx.hauyne.admin.sys.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 新增、编辑权限表单页，要选择的 上级菜单树VO类
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
public class AuthorityTreeSelectVO {

    @Schema(description = "唯一键")
    private Long key;

    /**
     * 父节点
     */
    @JsonIgnore
    private Long parentKey;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 是否为叶子节点 true=是 false=否
     */
    @JsonProperty(value = "isLeaf")
    private Boolean leaf;

    /**
     * 子节点集合
     */
    @Schema(description = "子节点集合")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<AuthorityTreeSelectVO> children;

}
