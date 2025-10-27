package com.luoyx.hauyne.audit.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 表示一次提交（一次 commit）中的所有字段变更
 */
@Data
@Builder
public class AuditChangeVO {

    /**
     * 版本号，对应 Javers 的 commitId（一般就是 1, 2, 3...）
     */
    @Schema(description = "版本号")
    private BigDecimal version;

    @Schema(description = "变更类型（CREATE=创建，MODIFY=修改，DELETE=删除）")
    private String changeType; // "MODIFY", "CREATE", "DELETE"

    /**
     * 提交人（Javers commitMetadata.author）
     */
    @Schema(description = "提交人")
    private String author;

    /**
     * 提交时间（Javers commitMetadata.commitDate）
     */
    @Schema(description = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commitDate;

    /**
     * 字段变更明细列表
     */
    @Schema(description = "字段变更明细列表")
    private List<FieldChangeVO> changes;

    /**
     * 可选：变更后的整个对象快照（如需前端查看变更后全量值）
     */
//    private Object stateAfterChange;


}
