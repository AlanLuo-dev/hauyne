package com.luoyx.hauyne.admin.util;

public class LogicalDeleteTableDDLGenerator {

    public static String generateLogicalDeleteTableDDL(String originalTableDDL) {
        // 定义逻辑删除表的前缀
        String deletedTablePrefix = "_deleted_";

        // 去掉AUTO_INCREMENT标记
        String ddlWithoutAutoIncrement = originalTableDDL.replaceAll("AUTO_INCREMENT", "");

        // 修改表名，添加逻辑删除表的前缀
        String logicalDeleteTableDDL = ddlWithoutAutoIncrement.replaceFirst("CREATE TABLE `(\\w+)`", "CREATE TABLE `" + deletedTablePrefix + "$1`");

        // 去除唯一索引
        logicalDeleteTableDDL = logicalDeleteTableDDL.replaceAll("(?i)UNIQUE KEY `[^`]+` \\([^\\)]+\\) USING BTREE COMMENT '[^']+'", "");

        // 去除额外的换行和逗号
        logicalDeleteTableDDL = logicalDeleteTableDDL.replaceAll(",\\s*,", ",");

        // 添加删除者字段和删除时间字段
        int lastBracketIndex = logicalDeleteTableDDL.lastIndexOf(")");
        String additionalColumns = "`deleted_by` bigint NOT NULL COMMENT '删除者的用户id',\n" +
                "`deleted_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '删除时间'\n";
        logicalDeleteTableDDL = new StringBuilder(logicalDeleteTableDDL)
                .insert(lastBracketIndex, additionalColumns)
                .toString();

        // 修改表注释
        logicalDeleteTableDDL = logicalDeleteTableDDL.replaceFirst("COMMENT='([^']+)'", "COMMENT='逻辑删除归档 - $1'");

        return logicalDeleteTableDDL;
    }

    public static void main(String[] args) {
        String originalTableDDL = "CREATE TABLE `hyn_sys_dict_type` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',\n" +
                "  `dict_type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型编码',\n" +
                "  `dict_type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型名称',\n" +
                "  `is_enabled` tinyint NOT NULL DEFAULT '1' COMMENT '是否启用（1=启用；0=禁用; 无符号）',\n" +
                "  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',\n" +
                "  `created_by` bigint NOT NULL COMMENT '创建人id（无符号）',\n" +
                "  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `last_updated_by` bigint NOT NULL COMMENT '修改人id（无符号）',\n" +
                "  `last_updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
                "  PRIMARY KEY (`id`) USING BTREE,\n" +
                "  UNIQUE KEY `uk_dict_type_code` (`dict_type_code`) USING BTREE COMMENT '唯一索引（字典类型编码）',\n" +
                "  UNIQUE KEY `uk_dict_type_name` (`dict_type_name`) USING BTREE COMMENT '唯一索引（字典类型名称）'\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据字典类型表';";

        String logicalDeleteTableDDL = generateLogicalDeleteTableDDL(originalTableDDL);
        System.out.println(logicalDeleteTableDDL);
    }
}

