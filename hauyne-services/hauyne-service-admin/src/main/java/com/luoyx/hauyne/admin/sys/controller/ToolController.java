package com.luoyx.hauyne.admin.sys.controller;

import com.luoyx.hauyne.admin.util.LogicalDeleteTableDDLGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ToolController", description = "工具Controller")
@RestController
@RequestMapping(value = "/tool")
public class ToolController {

    @Operation(summary = "生成逻辑删除归档表")
    @PostMapping(value = "/generate-deleted-table-ddl")
    private String generateDeletedTableDdl(@RequestBody String ddl) {
        return LogicalDeleteTableDDLGenerator.generateLogicalDeleteTableDDL(ddl);
    }
}
