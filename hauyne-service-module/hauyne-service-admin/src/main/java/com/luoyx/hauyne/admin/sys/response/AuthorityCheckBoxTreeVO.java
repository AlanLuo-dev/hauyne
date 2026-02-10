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
 * 权限资源 复选框模式的树形结构数据 VO类
 *
 * @author luoyingxiong
 */
@Getter
@Setter
@ToString
public class AuthorityCheckBoxTreeVO {

    /**
     * 树节点的唯一Key,即权限资源id
     * <p>
     * 整个树范围内的所有节点的 key 值不能重复且不为空！
     *
     */
    @Schema(description = "树节点的唯一Key")
    private String key;

    /**
     * 父节点
     */
    @JsonIgnore
    private String parentKey;

    /**
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否为叶子节点 true=是 false=否
     */
    @JsonProperty(value = "isLeaf")
    private Boolean leaf;

    /**
     * 子节点集合
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<AuthorityCheckBoxTreeVO> children;
}
