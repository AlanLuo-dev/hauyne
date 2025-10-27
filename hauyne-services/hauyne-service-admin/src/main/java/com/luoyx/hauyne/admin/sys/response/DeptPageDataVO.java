package com.luoyx.hauyne.admin.sys.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luoyx.hauyne.commons.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeptPageDataVO extends BaseVO {

    /**
     * 父级部门id
     */
    @JsonIgnore
    private Long parentId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 部门负责人
     */
    @Schema(description = "部门负责人")
    private Long leaderFullName;

    /**
     * 部门层级
     */
    @Schema(description = "部门层级")
    private Integer level;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
