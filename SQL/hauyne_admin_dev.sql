/*
 Navicat Premium Data Transfer

 Source Server         : aiven
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : hauyne-dev-luoyingxiong123-9aa4.c.aivencloud.com:16704
 Source Schema         : hauyne_admin_dev

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 24/09/2025 21:42:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for _deleted_hyn_sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `_deleted_hyn_sys_dict_item`;
CREATE TABLE "_deleted_hyn_sys_dict_item" (
  "id" bigint NOT NULL COMMENT '主键，自增',
  "dict_type_id" bigint NOT NULL COMMENT '字典类型id',
  "dict_item_code" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典选项编码',
  "dict_item_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典选项名称',
  "sort" smallint NOT NULL COMMENT '排序',
  "is_enabled" tinyint NOT NULL COMMENT '启用状态（1=启用；0=禁用 无符号）',
  "remark" varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  "created_by" bigint NOT NULL COMMENT '创建人id（无符号）',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人id（无符号）',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of _deleted_hyn_sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO `_deleted_hyn_sys_dict_item` VALUES (14, 20, '3242', '3224', 1, 0, '1', 1, '2023-06-03 22:17:15', 1, '2023-11-11 15:01:10');
INSERT INTO `_deleted_hyn_sys_dict_item` VALUES (15, 18, 'ip', 'sdaf', 2, 1, '', 1, '2023-12-30 11:56:53', 1, '2023-12-30 11:56:53');
COMMIT;

-- ----------------------------
-- Table structure for _deleted_hyn_sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `_deleted_hyn_sys_dict_type`;
CREATE TABLE "_deleted_hyn_sys_dict_type" (
  "id" bigint NOT NULL COMMENT '主键',
  "dict_type_code" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型编码',
  "dict_type_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型名称',
  "is_enabled" tinyint NOT NULL DEFAULT '1' COMMENT '是否启用（1=启用；0=禁用; 无符号）',
  "description" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  "created_by" bigint NOT NULL COMMENT '创建人id（无符号）',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人id（无符号）',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  "deleted_by" bigint DEFAULT NULL COMMENT '删除者的用户id',
  "deleted_time" datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of _deleted_hyn_sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `_deleted_hyn_sys_dict_type` VALUES (31, 'test', '才水电费', 1, '王力宏', 1, '2024-09-23 15:31:17', 1, '2024-11-23 11:58:44', 1, '2025-06-17 11:54:45');
INSERT INTO `_deleted_hyn_sys_dict_type` VALUES (32, 'frefe', '萨芬', 1, '撒地方', 1, '2024-09-23 17:54:11', 1, '2024-09-23 17:54:11', 1, '2024-12-22 17:25:20');
INSERT INTO `_deleted_hyn_sys_dict_type` VALUES (33, '现在v这些', '发的', 1, '打法水电', 1, '2024-09-23 17:56:49', 1, '2024-09-23 17:56:49', 1, '2024-12-11 20:49:00');
INSERT INTO `_deleted_hyn_sys_dict_type` VALUES (34, 'xzc', '小组唱', 1, '都是', 1, '2024-09-23 17:57:37', 1, '2025-05-20 22:04:27', 1, '2025-06-17 11:52:58');
INSERT INTO `_deleted_hyn_sys_dict_type` VALUES (35, '1', '1', 1, '', 1, '2025-05-20 22:04:39', 1, '2025-05-20 22:04:39', 1, '2025-06-17 11:49:21');
INSERT INTO `_deleted_hyn_sys_dict_type` VALUES (36, '2', '2', 1, '', 1, '2025-05-20 22:09:53', 1, '2025-05-20 22:09:53', 1, '2025-05-20 22:10:18');
INSERT INTO `_deleted_hyn_sys_dict_type` VALUES (37, '3', '3', 1, '', 1, '2025-05-20 22:13:22', 1, '2025-05-20 22:13:22', 1, '2025-05-20 22:14:25');
INSERT INTO `_deleted_hyn_sys_dict_type` VALUES (38, '1', '1', 1, '1', 1, '2025-06-17 12:34:25', 1, '2025-06-17 12:34:25', 1, '2025-07-05 00:22:39');
INSERT INTO `_deleted_hyn_sys_dict_type` VALUES (39, '143', '143', 1, '3', 1, '2025-06-17 12:39:52', 1, '2025-06-17 12:53:16', 1, '2025-07-05 00:22:20');
COMMIT;

-- ----------------------------
-- Table structure for _deleted_hyn_sys_login_history
-- ----------------------------
DROP TABLE IF EXISTS `_deleted_hyn_sys_login_history`;
CREATE TABLE "_deleted_hyn_sys_login_history" (
  "id" bigint NOT NULL COMMENT '主键，无符号自增',
  "type" tinyint NOT NULL COMMENT '类型（1=登录；0=注销）',
  "result" tinyint NOT NULL COMMENT '登录/注销结果（1=登录成功，2=登录失败，3=注销成功，4=注销失败）',
  "fail_reason" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '失败原因',
  "user_id" bigint NOT NULL DEFAULT '0' COMMENT '用户id（0=用户输入的账号不存在）',
  "ip_address" varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ip',
  "location" varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录地点',
  "browser" varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端浏览器',
  "browser_version" varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端浏览器版本',
  "os_name" varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端操作系统名称',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "deleted_by" bigint DEFAULT NULL COMMENT '删除者的用户id',
  "deleted_time" datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of _deleted_hyn_sys_login_history
-- ----------------------------
BEGIN;
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (9, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '109.0.0.0', 'Windows 10', '2023-02-03 20:44:03', 1, '2024-04-06 17:18:54');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (12, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '109.0.0.0', 'Windows 10', '2023-02-13 20:04:38', 1, '2024-04-06 17:17:31');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (13, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '109.0.0.0', 'Windows 10', '2023-02-13 20:05:31', 1, '2024-04-06 18:15:04');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (14, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '109.0.0.0', 'Windows 10', '2023-02-13 20:05:33', 1, '2024-04-06 20:37:26');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (15, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '109.0.0.0', 'Windows 10', '2023-02-13 20:09:08', 1, '2024-04-07 11:16:18');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (16, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '109.0.0.0', 'Windows 10', '2023-02-13 20:09:10', 1, '2024-04-07 12:00:13');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (17, 1, 2, '验证码已过期', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-15 21:33:27', 1, '2024-04-07 12:13:45');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (18, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-15 21:33:30', 1, '2024-04-07 14:32:11');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (19, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-15 21:42:02', 1, '2024-04-07 14:32:11');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (20, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-15 21:42:13', 1, '2024-04-07 14:39:14');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (21, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-15 21:49:11', 1, '2024-04-07 14:39:14');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (22, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-15 21:49:14', 1, '2024-04-07 14:48:42');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (23, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-15 21:49:28', 1, '2024-04-07 14:48:42');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (24, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-15 21:49:31', 1, '2024-04-07 14:58:57');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (25, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-17 10:55:39', 1, '2024-04-07 14:58:57');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (26, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-19 11:34:08', 1, '2024-04-07 15:00:49');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (27, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-19 11:44:20', 1, '2024-04-21 14:33:03');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (28, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-19 11:44:23', 1, '2024-04-21 14:36:01');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (29, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-02-22 22:32:07', 0, '2024-04-21 14:53:49');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (30, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-04 13:20:31', 1, '2024-04-21 14:51:18');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (31, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-05 23:26:03', 1, '2024-04-21 15:47:17');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (32, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-06 21:24:06', 1, '2024-04-23 12:23:42');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (33, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-06 23:02:12', NULL, NULL);
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (34, 1, 2, '验证码已过期', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-06 23:11:13', 1, '2024-05-12 14:44:19');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (35, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-06 23:11:18', 1, '2024-05-12 14:51:40');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (36, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 00:12:48', 1, '2024-04-23 15:57:07');
INSERT INTO `_deleted_hyn_sys_login_history` VALUES (323, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '123.0.0.0', 'Windows 10', '2024-04-06 14:48:42', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for clientdetails
-- ----------------------------
DROP TABLE IF EXISTS `clientdetails`;
CREATE TABLE "clientdetails" (
  "appId" varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  "resourceIds" varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  "appSecret" varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  "scope" varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  "grantTypes" varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  "redirectUrl" varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  "authorities" varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  "access_token_validity" int DEFAULT NULL,
  "refresh_token_validity" int DEFAULT NULL,
  "additionalInformation" varchar(4096) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  "autoApproveScopes" varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY ("appId")
);

-- ----------------------------
-- Records of clientdetails
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for hyn_codegen_code
-- ----------------------------
DROP TABLE IF EXISTS `hyn_codegen_code`;
CREATE TABLE "hyn_codegen_code" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  "gen_datasource_id" bigint NOT NULL COMMENT '数据源ID',
  "table_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表名',
  "package_name" varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码包名',
  "module_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块名',
  "business_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务名',
  "function_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '方法名',
  "author" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者',
  "template_id" bigint NOT NULL COMMENT '代码生成模板ID',
  "status" int NOT NULL COMMENT '生成状态：0-未生成，1-生成成功，2-生成失败',
  "error_message" varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '错误信息',
  "created_by" bigint NOT NULL COMMENT '创建人',
  "created_time" datetime NOT NULL COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人',
  "last_updated_time" datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY ("id"),
  KEY "idx_datasource_id" ("gen_datasource_id")
);

-- ----------------------------
-- Records of hyn_codegen_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for hyn_codegen_data_source
-- ----------------------------
DROP TABLE IF EXISTS `hyn_codegen_data_source`;
CREATE TABLE "hyn_codegen_data_source" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  "data_source_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据源名称',
  "driver_class_name" varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'JDBC驱动类名',
  "url" varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'JDBC连接URL',
  "username" varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库用户名',
  "password" varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库密码',
  "created_by" bigint NOT NULL COMMENT '创建人',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of hyn_codegen_data_source
-- ----------------------------
BEGIN;
INSERT INTO `hyn_codegen_data_source` VALUES (1, '(*´▽｀)ノノ)', 'B', 'C', 'D', 'E', 1, '2023-05-07 11:36:27', 1, '2023-05-07 14:53:30');
INSERT INTO `hyn_codegen_data_source` VALUES (2, 'A', 'B', 'C', 'D', 'E', 1, '2023-05-07 11:39:32', 1, '2023-05-07 11:39:32');
INSERT INTO `hyn_codegen_data_source` VALUES (3, 'A', 'B', 'C', 'D', 'E', 1, '2023-05-07 14:28:14', 1, '2023-05-07 14:28:14');
INSERT INTO `hyn_codegen_data_source` VALUES (4, 'A', 'B', 'C', 'D', 'E', 1, '2023-05-07 14:28:16', 1, '2023-05-07 14:28:16');
COMMIT;

-- ----------------------------
-- Table structure for hyn_codegen_template
-- ----------------------------
DROP TABLE IF EXISTS `hyn_codegen_template`;
CREATE TABLE "hyn_codegen_template" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  "name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板名称',
  "description" varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '模板描述',
  "template" longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码生成模板',
  "created_by" bigint NOT NULL COMMENT '创建人',
  "created_time" datetime NOT NULL COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人',
  "last_updated_time" datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of hyn_codegen_template
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for hyn_codegen_template_param
-- ----------------------------
DROP TABLE IF EXISTS `hyn_codegen_template_param`;
CREATE TABLE "hyn_codegen_template_param" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  "template_id" bigint NOT NULL COMMENT '代码生成模板ID',
  "param_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数名称',
  "param_type" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数类型',
  "param_desc" varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '参数描述',
  "default_value" varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认值',
  "sort" int NOT NULL COMMENT '排序',
  "created_by" bigint NOT NULL COMMENT '创建人',
  "created_time" datetime NOT NULL COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人',
  "last_updated_time" datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of hyn_codegen_template_param
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_authority`;
CREATE TABLE "hyn_sys_authority" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  "parent_id" bigint NOT NULL DEFAULT '0' COMMENT '父权限id',
  "is_leaf" tinyint NOT NULL DEFAULT '1' COMMENT '是否为叶子节点（1=是，0=否）',
  "level" tinyint unsigned NOT NULL COMMENT '权限树层级（1=第1层，2=第2层，以此类推）',
  "authority_type" varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限类型（menu=菜单，operation=操作,可扩展）',
  "authority_code" varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限编码',
  "authority_name" varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
  "icon" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图标CSS样式',
  "path" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求路径',
  "sort" tinyint unsigned NOT NULL DEFAULT '0' COMMENT '排序(无符号)',
  "remark" varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  "created_by" bigint NOT NULL COMMENT '创建人id',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人id',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_authority_name" ("authority_name")
);

-- ----------------------------
-- Records of hyn_sys_authority
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_authority` VALUES (1, 0, 0, 1, 'menu', 'sys-manage', '系统管理', 'setting', '', 1, '', 1, '2020-10-27 22:43:13', 1, '2024-08-18 14:59:06');
INSERT INTO `hyn_sys_authority` VALUES (2, 1, 0, 2, 'menu', 'sys-authority:tree-list', '权限管理', 'safety', 'admin/authority', 3, '', 1, '2021-03-06 23:27:39', 1, '2024-11-22 22:45:00');
INSERT INTO `hyn_sys_authority` VALUES (4, 1, 1, 2, 'menu', 'sys-login-history:list', '登录历史', 'history', 'admin/login-history', 4, '', 1, '2021-09-14 21:23:21', 1, '2024-10-04 13:50:06');
INSERT INTO `hyn_sys_authority` VALUES (5, 1, 0, 2, 'menu', 'sys-dict-type:find-page', '字典管理', 'book', 'admin/dictionary/dict-type', 5, '', 1, '2022-07-23 16:32:14', 1, '2024-11-22 13:01:11');
INSERT INTO `hyn_sys_authority` VALUES (23, 1, 0, 2, 'menu', 'sys-role:find-page', '角色管理', 'team', 'admin/role', 0, '', 1, '2022-09-05 16:50:59', 1, '2024-11-22 10:46:58');
INSERT INTO `hyn_sys_authority` VALUES (36, 1, 1, 2, 'menu', 'sys:user:list', '用户管理', 'user', 'admin/user', 0, '', 1, '2023-07-02 15:30:37', 1, '2024-09-25 15:52:44');
INSERT INTO `hyn_sys_authority` VALUES (37, 23, 1, 3, 'button', 'sys-role:add', '新增角色', 'usergroup-add', '', 2, '', 1, '2024-11-19 17:27:19', 1, '2024-11-22 10:58:49');
INSERT INTO `hyn_sys_authority` VALUES (38, 23, 1, 3, 'button', 'sys-role:update', '修改角色', 'edit', '', 3, '', 1, '2024-11-21 12:17:49', 1, '2024-11-22 10:59:04');
INSERT INTO `hyn_sys_authority` VALUES (39, 0, 1, 1, 'menu', 'dashboard', '首页', 'dashboard', 'dashboard', 0, '', 1, '2024-11-21 21:16:19', 1, '2025-08-01 13:11:30');
INSERT INTO `hyn_sys_authority` VALUES (40, 23, 1, 3, 'button', 'sys-role:find-page', '分页查询角色', 'search', '', 1, '', 1, '2024-11-22 10:58:05', 1, '2024-12-02 13:55:13');
INSERT INTO `hyn_sys_authority` VALUES (41, 23, 1, 3, 'button', 'sys-role:delete', '删除角色', 'delete', '', 4, '', 1, '2024-11-22 11:00:34', 1, '2024-11-22 11:00:34');
INSERT INTO `hyn_sys_authority` VALUES (42, 23, 1, 3, 'button', 'sys-role:assign-authorities', '分配权限', 'safety', '', 5, '', 1, '2024-11-22 11:12:24', 1, '2024-11-22 11:12:24');
INSERT INTO `hyn_sys_authority` VALUES (43, 5, 1, 3, 'button', 'sys-dict-type:find-page', '分页查询字典类型', 'search', '', 1, '', 1, '2024-11-22 13:00:18', 1, '2024-11-22 13:00:18');
INSERT INTO `hyn_sys_authority` VALUES (44, 5, 1, 3, 'button', 'sys-dict-type:add', '新增字典类型', 'plus', '', 2, '', 1, '2024-11-22 13:02:46', 1, '2024-11-22 13:05:04');
INSERT INTO `hyn_sys_authority` VALUES (45, 5, 1, 3, 'button', 'sys-dict-type:update', '修改字典类型', 'edit', '', 3, '', 1, '2024-11-22 13:04:02', 1, '2024-11-22 13:04:02');
INSERT INTO `hyn_sys_authority` VALUES (46, 5, 1, 1, 'button', 'sys-dict-type:delete', '删除字典类型', 'delete', '', 4, '', 1, '2024-11-22 13:06:11', 1, '2024-11-22 13:59:45');
INSERT INTO `hyn_sys_authority` VALUES (47, 5, 1, 3, 'button', 'sys-dict-type:recycle-bin', '回收站', 'rest', '', 5, '', 1, '2024-11-22 15:59:09', 1, '2025-07-31 11:52:50');
INSERT INTO `hyn_sys_authority` VALUES (48, 5, 0, 3, 'button', 'sys-dict-item:list', '字典值管理', 'skin', '', 8, '', 1, '2024-11-22 16:00:57', 1, '2024-11-22 17:00:02');
INSERT INTO `hyn_sys_authority` VALUES (49, 5, 1, 3, 'button', 'sys-dict-type:toggle-status', '启用/禁用字典类型', 'check-circle', '', 6, '', 1, '2024-11-22 16:09:52', 1, '2024-12-07 13:20:43');
INSERT INTO `hyn_sys_authority` VALUES (55, 48, 1, 4, 'button', 'sys-dict-item:list', '查询字典值', 'search', '', 1, '', 1, '2024-11-22 17:02:23', 1, '2024-12-02 13:16:14');
INSERT INTO `hyn_sys_authority` VALUES (56, 48, 1, 4, 'button', 'sys-dict-item:add', '新增字典值', 'plus', '', 2, '', 1, '2024-11-22 17:03:54', 1, '2024-11-22 17:03:54');
INSERT INTO `hyn_sys_authority` VALUES (57, 48, 1, 4, 'button', 'sys-dict-item:update', '修改字典值', 'edit', '', 3, '', 1, '2024-11-22 17:05:07', 1, '2024-11-22 17:05:07');
INSERT INTO `hyn_sys_authority` VALUES (59, 48, 1, 4, 'button', 'sys-dict-item:delete', '删除字典值', 'skin', '', 4, '', 1, '2024-11-22 17:08:11', 1, '2024-11-22 17:08:11');
INSERT INTO `hyn_sys_authority` VALUES (60, 48, 1, 4, 'button', 'sys-dict-item:toggle-status', '启用/禁用字典值', 'skin', '', 5, '', 1, '2024-11-22 17:08:58', 1, '2024-11-23 13:51:56');
INSERT INTO `hyn_sys_authority` VALUES (62, 48, 1, 4, 'button', 'sys-dict-item:reorder', '调整字典值排序', 'drag', '', 7, '', 1, '2024-11-22 17:13:02', 1, '2024-11-22 17:13:02');
INSERT INTO `hyn_sys_authority` VALUES (63, 2, 1, 3, 'button', 'sys-authority:tree-list', '查询权限列表', 'search', '', 1, '', 1, '2024-11-22 22:46:02', 1, '2024-11-22 22:46:02');
INSERT INTO `hyn_sys_authority` VALUES (64, 2, 1, 3, 'button', 'sys-authority:add', '新增权限资源', 'plus', '', 2, '', 1, '2024-11-22 22:47:10', 1, '2024-11-22 22:47:10');
INSERT INTO `hyn_sys_authority` VALUES (65, 2, 1, 3, 'button', 'sys-authority:update', '修改权限资源', 'edit', '', 3, '', 1, '2024-11-22 22:47:55', 1, '2024-11-22 22:47:55');
INSERT INTO `hyn_sys_authority` VALUES (66, 2, 1, 3, 'button', 'sys-authority:delete', '删除权限资源', 'delete', '', 4, '', 1, '2024-11-22 22:48:48', 1, '2024-11-22 22:48:48');
INSERT INTO `hyn_sys_authority` VALUES (78, 1, 0, 2, 'menu', 'test', '测试', 'dribbble-square', '332', 6, '', 1, '2025-05-19 23:24:04', 1, '2025-07-04 23:52:41');
INSERT INTO `hyn_sys_authority` VALUES (82, 78, 1, 3, 'menu', '32', '23', 'skin', '', 1, '', 1, '2025-07-15 22:09:11', 1, '2025-07-15 22:09:11');
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_dept`;
CREATE TABLE "hyn_sys_dept" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  "parent_id" bigint NOT NULL COMMENT '父级部门id',
  "dept_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  "leader_id" bigint NOT NULL COMMENT '部门负责人id',
  "level" int NOT NULL COMMENT '部门层级',
  "remark" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  "created_by" bigint NOT NULL COMMENT '创建人id',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '最后修改人id',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_dept_name" ("dept_name")
);

-- ----------------------------
-- Records of hyn_sys_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_dict_item`;
CREATE TABLE "hyn_sys_dict_item" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  "dict_type_id" bigint NOT NULL COMMENT '字典类型id',
  "dict_item_code" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典选项编码',
  "dict_item_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典选项名称',
  "sort" smallint NOT NULL COMMENT '排序',
  "is_enabled" tinyint NOT NULL COMMENT '启用状态（1=启用；0=禁用）',
  "remark" varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  "created_by" bigint NOT NULL COMMENT '创建人id',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人id',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_dict_type_id_dict_item_code" ("dict_type_id","dict_item_code"),
  UNIQUE KEY "uk_dict_type_id_dict_item_name" ("dict_type_id","dict_item_name")
);

-- ----------------------------
-- Records of hyn_sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_dict_item` VALUES (5, 3, 'male', '男', 1, 1, '295959', 1, '2022-12-11 21:52:31', 1, '2025-07-16 09:46:22');
INSERT INTO `hyn_sys_dict_item` VALUES (6, 3, 'female', '女', 2, 1, '', 1, '2023-02-03 20:09:03', 1, '2025-07-16 09:46:22');
INSERT INTO `hyn_sys_dict_item` VALUES (7, 15, 'menu', '菜单', 1, 1, '', 1, '2023-03-07 22:26:30', 1, '2024-08-23 22:28:40');
INSERT INTO `hyn_sys_dict_item` VALUES (8, 15, 'button', '按钮', 2, 1, '', 1, '2023-03-07 22:26:42', 1, '2024-08-03 16:54:53');
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_dict_type`;
CREATE TABLE "hyn_sys_dict_type" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  "dict_type_code" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型编码',
  "dict_type_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型名称',
  "is_enabled" tinyint NOT NULL DEFAULT '1' COMMENT '是否启用（1=启用；0=禁用）',
  "description" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  "created_by" bigint NOT NULL COMMENT '创建人id',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人id',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_dict_type_code" ("dict_type_code"),
  UNIQUE KEY "uk_dict_type_name" ("dict_type_name")
);

-- ----------------------------
-- Records of hyn_sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_dict_type` VALUES (3, 'sex', '性别', 1, '你', 1, '2022-05-28 22:47:41', 1, '2025-05-07 12:07:07');
INSERT INTO `hyn_sys_dict_type` VALUES (15, 'authority_type', '权限类型', 1, '我操', 1, '2023-03-07 22:25:54', 1, '2024-09-13 17:12:44');
INSERT INTO `hyn_sys_dict_type` VALUES (25, 'volume_unit', '体积单位', 1, '哈哈', 1, '2024-08-31 15:48:12', 1, '2025-09-13 15:03:21');
INSERT INTO `hyn_sys_dict_type` VALUES (40, 'ers', 'drtd', 1, '', 1, '2025-07-05 14:06:00', 1, '2025-09-20 07:38:52');
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_login_history
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_login_history`;
CREATE TABLE "hyn_sys_login_history" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，无符号自增',
  "type" tinyint NOT NULL COMMENT '类型（1=登录；0=注销）',
  "result" tinyint NOT NULL COMMENT '登录/注销结果（1=登录成功，2=登录失败，3=注销成功，4=注销失败）',
  "fail_reason" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '失败原因',
  "user_id" bigint NOT NULL DEFAULT '0' COMMENT '用户id（0=用户输入的账号不存在）',
  "ip_address" varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ip',
  "location" varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录地点',
  "browser" varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端浏览器',
  "browser_version" varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端浏览器版本',
  "os_name" varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端操作系统名称',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of hyn_sys_login_history
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_login_history` VALUES (37, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 00:13:02');
INSERT INTO `hyn_sys_login_history` VALUES (38, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 20:24:41');
INSERT INTO `hyn_sys_login_history` VALUES (39, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 20:42:22');
INSERT INTO `hyn_sys_login_history` VALUES (40, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 20:44:50');
INSERT INTO `hyn_sys_login_history` VALUES (41, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 20:45:11');
INSERT INTO `hyn_sys_login_history` VALUES (42, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 20:54:20');
INSERT INTO `hyn_sys_login_history` VALUES (43, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 20:54:39');
INSERT INTO `hyn_sys_login_history` VALUES (44, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 20:54:43');
INSERT INTO `hyn_sys_login_history` VALUES (45, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 20:55:15');
INSERT INTO `hyn_sys_login_history` VALUES (46, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 20:55:19');
INSERT INTO `hyn_sys_login_history` VALUES (47, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-07 21:10:17');
INSERT INTO `hyn_sys_login_history` VALUES (48, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-08 23:03:06');
INSERT INTO `hyn_sys_login_history` VALUES (49, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-08 23:41:47');
INSERT INTO `hyn_sys_login_history` VALUES (50, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-09 22:59:27');
INSERT INTO `hyn_sys_login_history` VALUES (51, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 22:48:04');
INSERT INTO `hyn_sys_login_history` VALUES (52, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 22:49:41');
INSERT INTO `hyn_sys_login_history` VALUES (53, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 22:50:11');
INSERT INTO `hyn_sys_login_history` VALUES (54, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 22:54:24');
INSERT INTO `hyn_sys_login_history` VALUES (55, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:00:27');
INSERT INTO `hyn_sys_login_history` VALUES (56, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:01:44');
INSERT INTO `hyn_sys_login_history` VALUES (57, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:01:55');
INSERT INTO `hyn_sys_login_history` VALUES (58, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:02:05');
INSERT INTO `hyn_sys_login_history` VALUES (59, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:03:16');
INSERT INTO `hyn_sys_login_history` VALUES (60, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:03:41');
INSERT INTO `hyn_sys_login_history` VALUES (61, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:05:25');
INSERT INTO `hyn_sys_login_history` VALUES (62, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:05:59');
INSERT INTO `hyn_sys_login_history` VALUES (63, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:11:21');
INSERT INTO `hyn_sys_login_history` VALUES (64, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:11:42');
INSERT INTO `hyn_sys_login_history` VALUES (65, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:19:38');
INSERT INTO `hyn_sys_login_history` VALUES (66, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-10 23:20:17');
INSERT INTO `hyn_sys_login_history` VALUES (67, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-11 10:45:29');
INSERT INTO `hyn_sys_login_history` VALUES (68, 1, 2, '用户名或密码错误', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-12 10:38:14');
INSERT INTO `hyn_sys_login_history` VALUES (69, 1, 2, '验证码已过期', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-12 10:38:18');
INSERT INTO `hyn_sys_login_history` VALUES (70, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '110.0.0.0', 'Windows 10', '2023-03-12 10:38:21');
INSERT INTO `hyn_sys_login_history` VALUES (71, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-03-26 14:57:11');
INSERT INTO `hyn_sys_login_history` VALUES (72, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-03-26 16:17:33');
INSERT INTO `hyn_sys_login_history` VALUES (73, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-03-26 16:17:44');
INSERT INTO `hyn_sys_login_history` VALUES (74, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-03-26 16:37:55');
INSERT INTO `hyn_sys_login_history` VALUES (75, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-01 11:29:13');
INSERT INTO `hyn_sys_login_history` VALUES (76, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-01 11:34:55');
INSERT INTO `hyn_sys_login_history` VALUES (77, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-01 11:34:57');
INSERT INTO `hyn_sys_login_history` VALUES (78, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-01 11:35:05');
INSERT INTO `hyn_sys_login_history` VALUES (79, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-01 11:35:09');
INSERT INTO `hyn_sys_login_history` VALUES (80, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-01 11:35:23');
INSERT INTO `hyn_sys_login_history` VALUES (81, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-01 11:35:24');
INSERT INTO `hyn_sys_login_history` VALUES (82, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-01 11:36:33');
INSERT INTO `hyn_sys_login_history` VALUES (83, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-01 11:36:36');
INSERT INTO `hyn_sys_login_history` VALUES (84, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-02 21:49:08');
INSERT INTO `hyn_sys_login_history` VALUES (85, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-03 22:00:06');
INSERT INTO `hyn_sys_login_history` VALUES (86, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-04 16:55:43');
INSERT INTO `hyn_sys_login_history` VALUES (87, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-05 15:41:58');
INSERT INTO `hyn_sys_login_history` VALUES (88, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-05 20:52:39');
INSERT INTO `hyn_sys_login_history` VALUES (89, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-05 20:52:44');
INSERT INTO `hyn_sys_login_history` VALUES (90, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-09 13:14:31');
INSERT INTO `hyn_sys_login_history` VALUES (91, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-09 13:37:02');
INSERT INTO `hyn_sys_login_history` VALUES (92, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-09 13:37:05');
INSERT INTO `hyn_sys_login_history` VALUES (93, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-15 12:25:13');
INSERT INTO `hyn_sys_login_history` VALUES (94, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '111.0.0.0', 'Windows 10', '2023-04-15 22:02:36');
INSERT INTO `hyn_sys_login_history` VALUES (95, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-04-22 16:34:26');
INSERT INTO `hyn_sys_login_history` VALUES (96, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-04-25 23:27:52');
INSERT INTO `hyn_sys_login_history` VALUES (97, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-04-27 20:00:32');
INSERT INTO `hyn_sys_login_history` VALUES (98, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-04-28 23:28:11');
INSERT INTO `hyn_sys_login_history` VALUES (99, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-05 23:09:35');
INSERT INTO `hyn_sys_login_history` VALUES (100, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-06 21:22:14');
INSERT INTO `hyn_sys_login_history` VALUES (101, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-07 10:29:24');
INSERT INTO `hyn_sys_login_history` VALUES (102, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-07 22:06:40');
INSERT INTO `hyn_sys_login_history` VALUES (103, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-10 20:33:21');
INSERT INTO `hyn_sys_login_history` VALUES (104, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-11 20:50:41');
INSERT INTO `hyn_sys_login_history` VALUES (105, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-11 22:22:38');
INSERT INTO `hyn_sys_login_history` VALUES (106, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-11 22:22:40');
INSERT INTO `hyn_sys_login_history` VALUES (107, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-11 23:37:26');
INSERT INTO `hyn_sys_login_history` VALUES (108, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '112.0.0.0', 'Windows 10', '2023-05-11 23:49:25');
INSERT INTO `hyn_sys_login_history` VALUES (109, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-13 12:25:15');
INSERT INTO `hyn_sys_login_history` VALUES (110, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-13 12:25:29');
INSERT INTO `hyn_sys_login_history` VALUES (111, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-13 16:18:23');
INSERT INTO `hyn_sys_login_history` VALUES (112, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-14 20:19:26');
INSERT INTO `hyn_sys_login_history` VALUES (113, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-15 22:23:15');
INSERT INTO `hyn_sys_login_history` VALUES (114, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-15 22:23:33');
INSERT INTO `hyn_sys_login_history` VALUES (115, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-15 22:23:38');
INSERT INTO `hyn_sys_login_history` VALUES (116, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-15 23:00:00');
INSERT INTO `hyn_sys_login_history` VALUES (117, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-15 23:00:06');
INSERT INTO `hyn_sys_login_history` VALUES (118, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-16 20:58:51');
INSERT INTO `hyn_sys_login_history` VALUES (119, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-17 22:58:03');
INSERT INTO `hyn_sys_login_history` VALUES (120, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-18 22:22:06');
INSERT INTO `hyn_sys_login_history` VALUES (121, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-18 22:22:57');
INSERT INTO `hyn_sys_login_history` VALUES (122, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-18 22:23:22');
INSERT INTO `hyn_sys_login_history` VALUES (123, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-19 21:45:28');
INSERT INTO `hyn_sys_login_history` VALUES (124, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-20 20:28:58');
INSERT INTO `hyn_sys_login_history` VALUES (125, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-20 21:11:07');
INSERT INTO `hyn_sys_login_history` VALUES (126, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-20 22:42:50');
INSERT INTO `hyn_sys_login_history` VALUES (127, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-20 22:51:29');
INSERT INTO `hyn_sys_login_history` VALUES (128, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-21 10:03:01');
INSERT INTO `hyn_sys_login_history` VALUES (129, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-21 16:31:48');
INSERT INTO `hyn_sys_login_history` VALUES (130, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-22 23:15:00');
INSERT INTO `hyn_sys_login_history` VALUES (131, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-23 21:08:27');
INSERT INTO `hyn_sys_login_history` VALUES (132, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-26 21:54:38');
INSERT INTO `hyn_sys_login_history` VALUES (133, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 17:50:21');
INSERT INTO `hyn_sys_login_history` VALUES (134, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:18:59');
INSERT INTO `hyn_sys_login_history` VALUES (135, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:19:14');
INSERT INTO `hyn_sys_login_history` VALUES (136, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:21:46');
INSERT INTO `hyn_sys_login_history` VALUES (137, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:54:51');
INSERT INTO `hyn_sys_login_history` VALUES (138, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:55:16');
INSERT INTO `hyn_sys_login_history` VALUES (139, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:57:05');
INSERT INTO `hyn_sys_login_history` VALUES (140, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:57:09');
INSERT INTO `hyn_sys_login_history` VALUES (141, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:57:54');
INSERT INTO `hyn_sys_login_history` VALUES (142, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:57:59');
INSERT INTO `hyn_sys_login_history` VALUES (143, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:58:10');
INSERT INTO `hyn_sys_login_history` VALUES (144, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:58:50');
INSERT INTO `hyn_sys_login_history` VALUES (145, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-27 23:59:18');
INSERT INTO `hyn_sys_login_history` VALUES (146, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:01:16');
INSERT INTO `hyn_sys_login_history` VALUES (147, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:01:30');
INSERT INTO `hyn_sys_login_history` VALUES (148, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:03:40');
INSERT INTO `hyn_sys_login_history` VALUES (149, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:03:45');
INSERT INTO `hyn_sys_login_history` VALUES (150, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:03:51');
INSERT INTO `hyn_sys_login_history` VALUES (151, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:04:10');
INSERT INTO `hyn_sys_login_history` VALUES (152, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:08:30');
INSERT INTO `hyn_sys_login_history` VALUES (153, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:08:33');
INSERT INTO `hyn_sys_login_history` VALUES (154, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:09:26');
INSERT INTO `hyn_sys_login_history` VALUES (155, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:09:31');
INSERT INTO `hyn_sys_login_history` VALUES (156, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-28 00:09:46');
INSERT INTO `hyn_sys_login_history` VALUES (157, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-29 23:24:48');
INSERT INTO `hyn_sys_login_history` VALUES (158, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-29 23:32:53');
INSERT INTO `hyn_sys_login_history` VALUES (159, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-29 23:33:00');
INSERT INTO `hyn_sys_login_history` VALUES (160, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-29 23:33:15');
INSERT INTO `hyn_sys_login_history` VALUES (161, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-29 23:33:33');
INSERT INTO `hyn_sys_login_history` VALUES (162, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-05-30 20:54:44');
INSERT INTO `hyn_sys_login_history` VALUES (163, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-06-03 20:21:53');
INSERT INTO `hyn_sys_login_history` VALUES (164, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-06-05 20:52:17');
INSERT INTO `hyn_sys_login_history` VALUES (165, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-06-05 22:27:42');
INSERT INTO `hyn_sys_login_history` VALUES (166, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-06-05 23:41:52');
INSERT INTO `hyn_sys_login_history` VALUES (167, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-06-05 23:44:15');
INSERT INTO `hyn_sys_login_history` VALUES (168, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-06-05 23:44:28');
INSERT INTO `hyn_sys_login_history` VALUES (169, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-06-05 23:47:00');
INSERT INTO `hyn_sys_login_history` VALUES (170, 1, 2, '验证码已过期', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-06-05 23:50:14');
INSERT INTO `hyn_sys_login_history` VALUES (171, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '113.0.0.0', 'Windows 10', '2023-06-05 23:50:17');
INSERT INTO `hyn_sys_login_history` VALUES (172, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-13 23:33:27');
INSERT INTO `hyn_sys_login_history` VALUES (173, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-17 15:34:36');
INSERT INTO `hyn_sys_login_history` VALUES (174, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-18 11:44:06');
INSERT INTO `hyn_sys_login_history` VALUES (175, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-18 12:10:28');
INSERT INTO `hyn_sys_login_history` VALUES (176, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-18 12:11:39');
INSERT INTO `hyn_sys_login_history` VALUES (177, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-18 21:06:09');
INSERT INTO `hyn_sys_login_history` VALUES (178, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 15:57:05');
INSERT INTO `hyn_sys_login_history` VALUES (179, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:01:18');
INSERT INTO `hyn_sys_login_history` VALUES (180, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:01:40');
INSERT INTO `hyn_sys_login_history` VALUES (181, 1, 2, '验证码已过期', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:02:00');
INSERT INTO `hyn_sys_login_history` VALUES (182, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:02:03');
INSERT INTO `hyn_sys_login_history` VALUES (183, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:09:17');
INSERT INTO `hyn_sys_login_history` VALUES (184, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:10:37');
INSERT INTO `hyn_sys_login_history` VALUES (185, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:13:22');
INSERT INTO `hyn_sys_login_history` VALUES (186, 1, 2, '验证码已过期', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:14:54');
INSERT INTO `hyn_sys_login_history` VALUES (187, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:14:56');
INSERT INTO `hyn_sys_login_history` VALUES (188, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:39:21');
INSERT INTO `hyn_sys_login_history` VALUES (189, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:40:28');
INSERT INTO `hyn_sys_login_history` VALUES (190, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:40:42');
INSERT INTO `hyn_sys_login_history` VALUES (191, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 18:42:16');
INSERT INTO `hyn_sys_login_history` VALUES (192, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:02:59');
INSERT INTO `hyn_sys_login_history` VALUES (193, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:21:01');
INSERT INTO `hyn_sys_login_history` VALUES (194, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:22:02');
INSERT INTO `hyn_sys_login_history` VALUES (195, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:23:11');
INSERT INTO `hyn_sys_login_history` VALUES (196, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:30:50');
INSERT INTO `hyn_sys_login_history` VALUES (197, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:32:38');
INSERT INTO `hyn_sys_login_history` VALUES (198, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:33:04');
INSERT INTO `hyn_sys_login_history` VALUES (199, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:34:52');
INSERT INTO `hyn_sys_login_history` VALUES (200, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:38:00');
INSERT INTO `hyn_sys_login_history` VALUES (201, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:39:05');
INSERT INTO `hyn_sys_login_history` VALUES (202, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 19:46:01');
INSERT INTO `hyn_sys_login_history` VALUES (203, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:22:17');
INSERT INTO `hyn_sys_login_history` VALUES (204, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:23:01');
INSERT INTO `hyn_sys_login_history` VALUES (205, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:23:11');
INSERT INTO `hyn_sys_login_history` VALUES (206, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:24:44');
INSERT INTO `hyn_sys_login_history` VALUES (207, 1, 2, '验证码已过期', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:24:45');
INSERT INTO `hyn_sys_login_history` VALUES (208, 1, 2, '验证码已过期', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:24:45');
INSERT INTO `hyn_sys_login_history` VALUES (209, 1, 2, '验证码已过期', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:24:46');
INSERT INTO `hyn_sys_login_history` VALUES (210, 1, 2, '验证码已过期', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:24:46');
INSERT INTO `hyn_sys_login_history` VALUES (211, 1, 2, '验证码已过期', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:24:46');
INSERT INTO `hyn_sys_login_history` VALUES (212, 1, 2, '验证码已过期', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:24:47');
INSERT INTO `hyn_sys_login_history` VALUES (213, 1, 2, '验证码已过期', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:24:54');
INSERT INTO `hyn_sys_login_history` VALUES (214, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:26:48');
INSERT INTO `hyn_sys_login_history` VALUES (215, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:27:00');
INSERT INTO `hyn_sys_login_history` VALUES (216, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:28:37');
INSERT INTO `hyn_sys_login_history` VALUES (217, 0, 3, '注销成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:29:08');
INSERT INTO `hyn_sys_login_history` VALUES (218, 1, 1, '登录成功', 1, '::1,127.0.0.1', '', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-22 22:31:58');
INSERT INTO `hyn_sys_login_history` VALUES (219, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-23 11:46:44');
INSERT INTO `hyn_sys_login_history` VALUES (220, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-23 22:06:42');
INSERT INTO `hyn_sys_login_history` VALUES (221, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 10:09:14');
INSERT INTO `hyn_sys_login_history` VALUES (222, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 10:09:54');
INSERT INTO `hyn_sys_login_history` VALUES (223, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 10:10:15');
INSERT INTO `hyn_sys_login_history` VALUES (224, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 10:55:56');
INSERT INTO `hyn_sys_login_history` VALUES (225, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 11:00:37');
INSERT INTO `hyn_sys_login_history` VALUES (226, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 12:19:31');
INSERT INTO `hyn_sys_login_history` VALUES (227, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 12:24:03');
INSERT INTO `hyn_sys_login_history` VALUES (228, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:25:54');
INSERT INTO `hyn_sys_login_history` VALUES (229, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:27:02');
INSERT INTO `hyn_sys_login_history` VALUES (230, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:31:56');
INSERT INTO `hyn_sys_login_history` VALUES (231, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:33:58');
INSERT INTO `hyn_sys_login_history` VALUES (232, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:38:50');
INSERT INTO `hyn_sys_login_history` VALUES (233, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:39:29');
INSERT INTO `hyn_sys_login_history` VALUES (234, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:39:48');
INSERT INTO `hyn_sys_login_history` VALUES (235, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:40:40');
INSERT INTO `hyn_sys_login_history` VALUES (236, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:40:49');
INSERT INTO `hyn_sys_login_history` VALUES (237, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:42:28');
INSERT INTO `hyn_sys_login_history` VALUES (238, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:42:35');
INSERT INTO `hyn_sys_login_history` VALUES (239, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:45:13');
INSERT INTO `hyn_sys_login_history` VALUES (240, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:45:22');
INSERT INTO `hyn_sys_login_history` VALUES (241, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:49:56');
INSERT INTO `hyn_sys_login_history` VALUES (242, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:50:00');
INSERT INTO `hyn_sys_login_history` VALUES (243, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:50:21');
INSERT INTO `hyn_sys_login_history` VALUES (244, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 13:50:26');
INSERT INTO `hyn_sys_login_history` VALUES (245, 1, 2, '验证码已过期', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:03:44');
INSERT INTO `hyn_sys_login_history` VALUES (246, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:03:47');
INSERT INTO `hyn_sys_login_history` VALUES (247, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:04:16');
INSERT INTO `hyn_sys_login_history` VALUES (248, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:05:16');
INSERT INTO `hyn_sys_login_history` VALUES (249, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:07:38');
INSERT INTO `hyn_sys_login_history` VALUES (250, 1, 2, '验证码已过期', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:12:13');
INSERT INTO `hyn_sys_login_history` VALUES (251, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:12:31');
INSERT INTO `hyn_sys_login_history` VALUES (252, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:13:24');
INSERT INTO `hyn_sys_login_history` VALUES (253, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:19:08');
INSERT INTO `hyn_sys_login_history` VALUES (254, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:19:32');
INSERT INTO `hyn_sys_login_history` VALUES (255, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:19:39');
INSERT INTO `hyn_sys_login_history` VALUES (256, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:19:49');
INSERT INTO `hyn_sys_login_history` VALUES (257, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:22:44');
INSERT INTO `hyn_sys_login_history` VALUES (258, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:22:53');
INSERT INTO `hyn_sys_login_history` VALUES (259, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:29:27');
INSERT INTO `hyn_sys_login_history` VALUES (260, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:29:57');
INSERT INTO `hyn_sys_login_history` VALUES (261, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:30:28');
INSERT INTO `hyn_sys_login_history` VALUES (262, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:31:00');
INSERT INTO `hyn_sys_login_history` VALUES (263, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:56:11');
INSERT INTO `hyn_sys_login_history` VALUES (264, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-24 14:56:20');
INSERT INTO `hyn_sys_login_history` VALUES (265, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-06-29 22:12:21');
INSERT INTO `hyn_sys_login_history` VALUES (266, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-07-01 15:05:46');
INSERT INTO `hyn_sys_login_history` VALUES (267, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-07-02 10:18:15');
INSERT INTO `hyn_sys_login_history` VALUES (268, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-07-02 15:30:55');
INSERT INTO `hyn_sys_login_history` VALUES (269, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Windows 10', '2023-07-02 15:30:57');
INSERT INTO `hyn_sys_login_history` VALUES (270, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Linux', '2023-07-18 23:40:04');
INSERT INTO `hyn_sys_login_history` VALUES (271, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '114.0.0.0', 'Linux', '2023-07-23 16:33:37');
INSERT INTO `hyn_sys_login_history` VALUES (272, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '115.0.0.0', 'Linux', '2023-07-25 21:17:20');
INSERT INTO `hyn_sys_login_history` VALUES (273, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '115.0.0.0', 'Linux', '2023-07-26 22:11:53');
INSERT INTO `hyn_sys_login_history` VALUES (274, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '119.0.0.0', 'Linux', '2023-11-11 14:36:04');
INSERT INTO `hyn_sys_login_history` VALUES (275, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '119.0.0.0', 'Linux', '2023-11-11 15:11:22');
INSERT INTO `hyn_sys_login_history` VALUES (276, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2023-12-10 21:46:58');
INSERT INTO `hyn_sys_login_history` VALUES (277, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2023-12-16 11:39:09');
INSERT INTO `hyn_sys_login_history` VALUES (278, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2023-12-16 13:58:50');
INSERT INTO `hyn_sys_login_history` VALUES (279, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2023-12-16 22:01:10');
INSERT INTO `hyn_sys_login_history` VALUES (280, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2023-12-29 22:19:55');
INSERT INTO `hyn_sys_login_history` VALUES (281, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2023-12-30 11:56:21');
INSERT INTO `hyn_sys_login_history` VALUES (282, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2023-12-30 12:09:46');
INSERT INTO `hyn_sys_login_history` VALUES (283, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2024-01-14 18:11:57');
INSERT INTO `hyn_sys_login_history` VALUES (284, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2024-01-14 18:13:01');
INSERT INTO `hyn_sys_login_history` VALUES (285, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2024-01-15 22:48:09');
INSERT INTO `hyn_sys_login_history` VALUES (286, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2024-01-15 22:48:54');
INSERT INTO `hyn_sys_login_history` VALUES (287, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2024-01-19 21:04:13');
INSERT INTO `hyn_sys_login_history` VALUES (288, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2024-01-20 09:55:19');
INSERT INTO `hyn_sys_login_history` VALUES (289, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '120.0.0.0', 'Linux', '2024-01-20 09:59:55');
INSERT INTO `hyn_sys_login_history` VALUES (290, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 14:54:27');
INSERT INTO `hyn_sys_login_history` VALUES (291, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 14:55:46');
INSERT INTO `hyn_sys_login_history` VALUES (292, 1, 2, '验证码已过期', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 14:57:23');
INSERT INTO `hyn_sys_login_history` VALUES (293, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 15:01:38');
INSERT INTO `hyn_sys_login_history` VALUES (294, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 16:28:54');
INSERT INTO `hyn_sys_login_history` VALUES (295, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 16:40:52');
INSERT INTO `hyn_sys_login_history` VALUES (296, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 16:40:56');
INSERT INTO `hyn_sys_login_history` VALUES (297, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 16:41:36');
INSERT INTO `hyn_sys_login_history` VALUES (298, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 16:41:41');
INSERT INTO `hyn_sys_login_history` VALUES (299, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 16:43:41');
INSERT INTO `hyn_sys_login_history` VALUES (300, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-27 16:43:46');
INSERT INTO `hyn_sys_login_history` VALUES (301, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-29 23:01:52');
INSERT INTO `hyn_sys_login_history` VALUES (302, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-29 23:35:39');
INSERT INTO `hyn_sys_login_history` VALUES (303, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:03:14');
INSERT INTO `hyn_sys_login_history` VALUES (304, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:04:00');
INSERT INTO `hyn_sys_login_history` VALUES (305, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:41:30');
INSERT INTO `hyn_sys_login_history` VALUES (306, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:41:33');
INSERT INTO `hyn_sys_login_history` VALUES (307, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:41:54');
INSERT INTO `hyn_sys_login_history` VALUES (308, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:41:56');
INSERT INTO `hyn_sys_login_history` VALUES (309, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:47:57');
INSERT INTO `hyn_sys_login_history` VALUES (310, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:48:00');
INSERT INTO `hyn_sys_login_history` VALUES (311, 1, 2, '用户名或密码错误', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:56:16');
INSERT INTO `hyn_sys_login_history` VALUES (312, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-01-30 21:56:23');
INSERT INTO `hyn_sys_login_history` VALUES (313, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '121.0.0.0', 'Linux', '2024-02-02 22:03:56');
INSERT INTO `hyn_sys_login_history` VALUES (314, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '122.0.0.0', 'Linux', '2024-02-26 21:11:26');
INSERT INTO `hyn_sys_login_history` VALUES (315, 1, 2, '用户名或密码错误', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '123.0.0.0', 'Linux', '2024-03-23 16:41:12');
INSERT INTO `hyn_sys_login_history` VALUES (316, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '122.0.0.0', 'Windows 10', '2024-03-30 15:42:05');
INSERT INTO `hyn_sys_login_history` VALUES (317, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '123.0.0.0', 'Windows 10', '2024-03-31 13:23:06');
INSERT INTO `hyn_sys_login_history` VALUES (318, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '123.0.0.0', 'Windows 10', '2024-03-31 13:23:06');
INSERT INTO `hyn_sys_login_history` VALUES (319, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '123.0.0.0', 'Windows 10', '2024-03-31 13:23:06');
INSERT INTO `hyn_sys_login_history` VALUES (320, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '123.0.0.0', 'Windows 10', '2024-03-31 13:23:06');
INSERT INTO `hyn_sys_login_history` VALUES (321, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '123.0.0.0', 'Windows 10', '2024-03-31 13:23:06');
INSERT INTO `hyn_sys_login_history` VALUES (322, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '123.0.0.0', 'Windows 10', '2024-04-06 11:46:36');
INSERT INTO `hyn_sys_login_history` VALUES (324, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '123.0.0.0', 'Windows 10', '2024-04-07 11:14:13');
INSERT INTO `hyn_sys_login_history` VALUES (325, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '124.0.0.0', 'Windows 10', '2024-04-21 14:27:13');
INSERT INTO `hyn_sys_login_history` VALUES (326, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '124.0.0.0', 'Windows 10', '2024-04-23 12:20:02');
INSERT INTO `hyn_sys_login_history` VALUES (327, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '124.0.0.0', 'Windows 10', '2024-05-09 14:38:47');
INSERT INTO `hyn_sys_login_history` VALUES (328, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '124.0.0.0', 'Windows 10', '2024-05-09 20:53:33');
INSERT INTO `hyn_sys_login_history` VALUES (329, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '124.0.0.0', 'Windows 10', '2024-05-10 14:02:35');
INSERT INTO `hyn_sys_login_history` VALUES (330, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '124.0.0.0', 'Windows 10', '2024-05-12 14:39:06');
INSERT INTO `hyn_sys_login_history` VALUES (331, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-21 17:22:54');
INSERT INTO `hyn_sys_login_history` VALUES (332, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-21 20:33:59');
INSERT INTO `hyn_sys_login_history` VALUES (333, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-22 14:36:16');
INSERT INTO `hyn_sys_login_history` VALUES (334, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 11:46:07');
INSERT INTO `hyn_sys_login_history` VALUES (335, 1, 2, '验证码已过期', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 11:47:14');
INSERT INTO `hyn_sys_login_history` VALUES (336, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 12:20:30');
INSERT INTO `hyn_sys_login_history` VALUES (337, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 12:29:28');
INSERT INTO `hyn_sys_login_history` VALUES (338, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 12:56:14');
INSERT INTO `hyn_sys_login_history` VALUES (339, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 20:39:52');
INSERT INTO `hyn_sys_login_history` VALUES (340, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 20:41:33');
INSERT INTO `hyn_sys_login_history` VALUES (341, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 20:44:58');
INSERT INTO `hyn_sys_login_history` VALUES (342, 1, 1, '登录成功', 1, '127.0.0.1', '临时删除', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 20:49:18');
INSERT INTO `hyn_sys_login_history` VALUES (343, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 20:52:56');
INSERT INTO `hyn_sys_login_history` VALUES (344, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:00:23');
INSERT INTO `hyn_sys_login_history` VALUES (345, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:03:13');
INSERT INTO `hyn_sys_login_history` VALUES (346, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:09:16');
INSERT INTO `hyn_sys_login_history` VALUES (347, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:11:59');
INSERT INTO `hyn_sys_login_history` VALUES (348, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:13:18');
INSERT INTO `hyn_sys_login_history` VALUES (349, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:14:48');
INSERT INTO `hyn_sys_login_history` VALUES (350, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:16:58');
INSERT INTO `hyn_sys_login_history` VALUES (351, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:18:37');
INSERT INTO `hyn_sys_login_history` VALUES (352, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:20:17');
INSERT INTO `hyn_sys_login_history` VALUES (353, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:24:30');
INSERT INTO `hyn_sys_login_history` VALUES (354, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:25:49');
INSERT INTO `hyn_sys_login_history` VALUES (355, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:26:50');
INSERT INTO `hyn_sys_login_history` VALUES (356, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:27:41');
INSERT INTO `hyn_sys_login_history` VALUES (357, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:28:59');
INSERT INTO `hyn_sys_login_history` VALUES (358, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:34:46');
INSERT INTO `hyn_sys_login_history` VALUES (359, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:40:57');
INSERT INTO `hyn_sys_login_history` VALUES (360, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:47:00');
INSERT INTO `hyn_sys_login_history` VALUES (361, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:53:35');
INSERT INTO `hyn_sys_login_history` VALUES (362, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 21:56:32');
INSERT INTO `hyn_sys_login_history` VALUES (363, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 22:07:28');
INSERT INTO `hyn_sys_login_history` VALUES (364, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 22:10:17');
INSERT INTO `hyn_sys_login_history` VALUES (365, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-23 22:11:12');
INSERT INTO `hyn_sys_login_history` VALUES (366, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-25 12:07:30');
INSERT INTO `hyn_sys_login_history` VALUES (367, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-25 20:57:04');
INSERT INTO `hyn_sys_login_history` VALUES (368, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-25 21:25:29');
INSERT INTO `hyn_sys_login_history` VALUES (369, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-25 21:25:32');
INSERT INTO `hyn_sys_login_history` VALUES (370, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-26 17:51:04');
INSERT INTO `hyn_sys_login_history` VALUES (371, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-26 18:05:43');
INSERT INTO `hyn_sys_login_history` VALUES (372, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-26 18:29:38');
INSERT INTO `hyn_sys_login_history` VALUES (373, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-26 21:35:20');
INSERT INTO `hyn_sys_login_history` VALUES (374, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-27 12:37:49');
INSERT INTO `hyn_sys_login_history` VALUES (375, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-27 13:38:41');
INSERT INTO `hyn_sys_login_history` VALUES (376, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-27 17:13:54');
INSERT INTO `hyn_sys_login_history` VALUES (377, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-27 17:18:45');
INSERT INTO `hyn_sys_login_history` VALUES (378, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-27 18:19:56');
INSERT INTO `hyn_sys_login_history` VALUES (379, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-28 14:20:50');
INSERT INTO `hyn_sys_login_history` VALUES (380, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-28 15:59:53');
INSERT INTO `hyn_sys_login_history` VALUES (381, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-28 17:01:37');
INSERT INTO `hyn_sys_login_history` VALUES (382, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-28 18:27:05');
INSERT INTO `hyn_sys_login_history` VALUES (383, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-28 21:54:21');
INSERT INTO `hyn_sys_login_history` VALUES (384, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-05-29 17:15:08');
INSERT INTO `hyn_sys_login_history` VALUES (385, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-01 14:38:07');
INSERT INTO `hyn_sys_login_history` VALUES (386, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-01 15:39:47');
INSERT INTO `hyn_sys_login_history` VALUES (387, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-01 16:51:35');
INSERT INTO `hyn_sys_login_history` VALUES (388, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-03 22:38:06');
INSERT INTO `hyn_sys_login_history` VALUES (389, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-04 13:54:42');
INSERT INTO `hyn_sys_login_history` VALUES (390, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-04 15:01:52');
INSERT INTO `hyn_sys_login_history` VALUES (391, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-04 16:02:17');
INSERT INTO `hyn_sys_login_history` VALUES (392, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-04 17:55:23');
INSERT INTO `hyn_sys_login_history` VALUES (393, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-05 11:39:26');
INSERT INTO `hyn_sys_login_history` VALUES (394, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-05 12:42:42');
INSERT INTO `hyn_sys_login_history` VALUES (395, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-05 21:39:25');
INSERT INTO `hyn_sys_login_history` VALUES (396, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-05 22:39:54');
INSERT INTO `hyn_sys_login_history` VALUES (397, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-06 10:40:46');
INSERT INTO `hyn_sys_login_history` VALUES (398, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-06 11:47:35');
INSERT INTO `hyn_sys_login_history` VALUES (399, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-06 14:00:08');
INSERT INTO `hyn_sys_login_history` VALUES (400, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-06 15:03:36');
INSERT INTO `hyn_sys_login_history` VALUES (401, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-08 14:01:07');
INSERT INTO `hyn_sys_login_history` VALUES (402, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-08 15:09:06');
INSERT INTO `hyn_sys_login_history` VALUES (403, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-08 16:11:31');
INSERT INTO `hyn_sys_login_history` VALUES (404, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-09 12:02:49');
INSERT INTO `hyn_sys_login_history` VALUES (405, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-09 14:17:20');
INSERT INTO `hyn_sys_login_history` VALUES (406, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '125.0.0.0', 'Windows 10', '2024-06-09 16:09:46');
INSERT INTO `hyn_sys_login_history` VALUES (407, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '126.0.0.0', 'Windows 10', '2024-07-16 21:02:40');
INSERT INTO `hyn_sys_login_history` VALUES (408, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '126.0.0.0', 'Windows 10', '2024-07-20 15:54:31');
INSERT INTO `hyn_sys_login_history` VALUES (409, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '126.0.0.0', 'Windows 10', '2024-07-21 11:51:27');
INSERT INTO `hyn_sys_login_history` VALUES (410, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '126.0.0.0', 'Windows 10', '2024-07-21 12:05:50');
INSERT INTO `hyn_sys_login_history` VALUES (411, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '126.0.0.0', 'Windows 10', '2024-07-21 12:06:11');
INSERT INTO `hyn_sys_login_history` VALUES (412, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '127.0.0.0', 'Windows 10', '2024-08-03 16:53:00');
INSERT INTO `hyn_sys_login_history` VALUES (413, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '127.0.0.0', 'Windows 10', '2024-08-04 13:25:33');
INSERT INTO `hyn_sys_login_history` VALUES (414, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '127.0.0.0', 'Windows 10', '2024-08-15 21:54:39');
INSERT INTO `hyn_sys_login_history` VALUES (415, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '127.0.0.0', 'Windows 10', '2024-08-18 13:58:49');
INSERT INTO `hyn_sys_login_history` VALUES (416, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-08-25 15:26:00');
INSERT INTO `hyn_sys_login_history` VALUES (417, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-08-25 15:26:07');
INSERT INTO `hyn_sys_login_history` VALUES (418, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-08-25 15:38:42');
INSERT INTO `hyn_sys_login_history` VALUES (419, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-08-25 16:33:22');
INSERT INTO `hyn_sys_login_history` VALUES (420, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-08-25 21:36:37');
INSERT INTO `hyn_sys_login_history` VALUES (421, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-08-28 21:43:21');
INSERT INTO `hyn_sys_login_history` VALUES (422, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-08-31 15:47:34');
INSERT INTO `hyn_sys_login_history` VALUES (423, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-13 14:06:59');
INSERT INTO `hyn_sys_login_history` VALUES (424, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-13 14:14:58');
INSERT INTO `hyn_sys_login_history` VALUES (425, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-13 14:15:06');
INSERT INTO `hyn_sys_login_history` VALUES (426, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-13 14:21:14');
INSERT INTO `hyn_sys_login_history` VALUES (427, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-13 14:21:45');
INSERT INTO `hyn_sys_login_history` VALUES (428, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-13 14:22:04');
INSERT INTO `hyn_sys_login_history` VALUES (429, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-18 12:57:36');
INSERT INTO `hyn_sys_login_history` VALUES (430, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-18 20:56:04');
INSERT INTO `hyn_sys_login_history` VALUES (431, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-20 20:39:23');
INSERT INTO `hyn_sys_login_history` VALUES (432, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-20 20:39:31');
INSERT INTO `hyn_sys_login_history` VALUES (433, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-20 20:39:58');
INSERT INTO `hyn_sys_login_history` VALUES (434, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 15:57:00');
INSERT INTO `hyn_sys_login_history` VALUES (435, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 15:58:15');
INSERT INTO `hyn_sys_login_history` VALUES (436, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 16:00:39');
INSERT INTO `hyn_sys_login_history` VALUES (437, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 16:00:57');
INSERT INTO `hyn_sys_login_history` VALUES (438, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 16:01:02');
INSERT INTO `hyn_sys_login_history` VALUES (439, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 16:01:36');
INSERT INTO `hyn_sys_login_history` VALUES (440, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 16:02:33');
INSERT INTO `hyn_sys_login_history` VALUES (441, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 16:02:46');
INSERT INTO `hyn_sys_login_history` VALUES (442, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 16:03:07');
INSERT INTO `hyn_sys_login_history` VALUES (443, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-21 16:05:34');
INSERT INTO `hyn_sys_login_history` VALUES (444, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-23 15:13:04');
INSERT INTO `hyn_sys_login_history` VALUES (445, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '128.0.0.0', 'Windows 10', '2024-09-23 19:14:49');
INSERT INTO `hyn_sys_login_history` VALUES (446, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-24 16:06:31');
INSERT INTO `hyn_sys_login_history` VALUES (447, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-25 15:50:32');
INSERT INTO `hyn_sys_login_history` VALUES (448, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-25 15:52:55');
INSERT INTO `hyn_sys_login_history` VALUES (449, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-25 15:53:01');
INSERT INTO `hyn_sys_login_history` VALUES (450, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-25 19:00:54');
INSERT INTO `hyn_sys_login_history` VALUES (451, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-25 20:46:35');
INSERT INTO `hyn_sys_login_history` VALUES (452, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-09-25 20:46:46');
INSERT INTO `hyn_sys_login_history` VALUES (453, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 13:42:29');
INSERT INTO `hyn_sys_login_history` VALUES (454, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 13:43:38');
INSERT INTO `hyn_sys_login_history` VALUES (455, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 13:43:46');
INSERT INTO `hyn_sys_login_history` VALUES (456, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 13:47:12');
INSERT INTO `hyn_sys_login_history` VALUES (457, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 13:48:50');
INSERT INTO `hyn_sys_login_history` VALUES (458, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 13:48:58');
INSERT INTO `hyn_sys_login_history` VALUES (459, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 13:50:11');
INSERT INTO `hyn_sys_login_history` VALUES (460, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 13:50:19');
INSERT INTO `hyn_sys_login_history` VALUES (461, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 14:43:59');
INSERT INTO `hyn_sys_login_history` VALUES (462, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 14:55:52');
INSERT INTO `hyn_sys_login_history` VALUES (463, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 14:57:43');
INSERT INTO `hyn_sys_login_history` VALUES (464, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 14:57:51');
INSERT INTO `hyn_sys_login_history` VALUES (465, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 15:00:36');
INSERT INTO `hyn_sys_login_history` VALUES (466, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 15:10:32');
INSERT INTO `hyn_sys_login_history` VALUES (467, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-04 15:10:44');
INSERT INTO `hyn_sys_login_history` VALUES (468, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-10 20:10:51');
INSERT INTO `hyn_sys_login_history` VALUES (469, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-11 10:17:47');
INSERT INTO `hyn_sys_login_history` VALUES (470, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-11 13:25:55');
INSERT INTO `hyn_sys_login_history` VALUES (471, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-11 19:35:00');
INSERT INTO `hyn_sys_login_history` VALUES (472, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-11 20:03:41');
INSERT INTO `hyn_sys_login_history` VALUES (473, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-12 11:11:44');
INSERT INTO `hyn_sys_login_history` VALUES (474, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-12 18:32:53');
INSERT INTO `hyn_sys_login_history` VALUES (475, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-12 18:33:27');
INSERT INTO `hyn_sys_login_history` VALUES (476, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '129.0.0.0', 'Windows 10', '2024-10-12 18:33:48');
INSERT INTO `hyn_sys_login_history` VALUES (477, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '130.0.0.0', 'Windows 10', '2024-11-17 21:39:53');
INSERT INTO `hyn_sys_login_history` VALUES (478, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '130.0.0.0', 'Windows 10', '2024-11-23 15:53:27');
INSERT INTO `hyn_sys_login_history` VALUES (479, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '131.0.0.0', 'Windows 10', '2025-01-03 21:26:12');
INSERT INTO `hyn_sys_login_history` VALUES (480, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '131.0.0.0', 'Windows 10', '2025-01-04 11:19:21');
INSERT INTO `hyn_sys_login_history` VALUES (481, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '131.0.0.0', 'Windows 10', '2025-01-05 07:21:13');
INSERT INTO `hyn_sys_login_history` VALUES (482, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '131.0.0.0', 'Windows 10', '2025-01-05 07:43:33');
INSERT INTO `hyn_sys_login_history` VALUES (483, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '131.0.0.0', 'Windows 10', '2025-01-05 07:50:46');
INSERT INTO `hyn_sys_login_history` VALUES (484, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '132.0.0.0', 'Windows 10', '2025-02-02 08:41:38');
INSERT INTO `hyn_sys_login_history` VALUES (485, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '132.0.0.0', 'Windows 10', '2025-02-02 08:42:16');
INSERT INTO `hyn_sys_login_history` VALUES (486, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '134.0.0.0', 'Windows 10', '2025-03-29 14:47:12');
INSERT INTO `hyn_sys_login_history` VALUES (487, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '134.0.0.0', 'Windows 10', '2025-03-29 15:44:20');
INSERT INTO `hyn_sys_login_history` VALUES (488, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '134.0.0.0', 'Windows 10', '2025-03-29 15:46:17');
INSERT INTO `hyn_sys_login_history` VALUES (489, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '134.0.0.0', 'Windows 10', '2025-03-30 11:47:31');
INSERT INTO `hyn_sys_login_history` VALUES (490, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '134.0.0.0', 'Windows 10', '2025-03-30 12:05:41');
INSERT INTO `hyn_sys_login_history` VALUES (491, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '134.0.0.0', 'Windows 10', '2025-04-03 17:44:03');
INSERT INTO `hyn_sys_login_history` VALUES (492, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '134.0.0.0', 'Windows 10', '2025-04-03 17:44:26');
INSERT INTO `hyn_sys_login_history` VALUES (493, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-04-06 16:36:05');
INSERT INTO `hyn_sys_login_history` VALUES (494, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-04-06 16:38:20');
INSERT INTO `hyn_sys_login_history` VALUES (495, 1, 2, '用户不存在', 0, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-04-06 16:38:28');
INSERT INTO `hyn_sys_login_history` VALUES (496, 1, 2, '用户不存在', 0, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-04-06 16:39:07');
INSERT INTO `hyn_sys_login_history` VALUES (497, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-04-24 11:18:23');
INSERT INTO `hyn_sys_login_history` VALUES (498, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-04-24 11:20:42');
INSERT INTO `hyn_sys_login_history` VALUES (499, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-04-24 11:36:39');
INSERT INTO `hyn_sys_login_history` VALUES (500, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-04-24 14:21:00');
INSERT INTO `hyn_sys_login_history` VALUES (501, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-04-25 11:15:47');
INSERT INTO `hyn_sys_login_history` VALUES (502, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-05-01 16:37:43');
INSERT INTO `hyn_sys_login_history` VALUES (503, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-05 11:34:37');
INSERT INTO `hyn_sys_login_history` VALUES (504, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-05 14:44:14');
INSERT INTO `hyn_sys_login_history` VALUES (505, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-05 18:18:06');
INSERT INTO `hyn_sys_login_history` VALUES (506, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-05-05 20:26:37');
INSERT INTO `hyn_sys_login_history` VALUES (507, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-05-05 21:08:40');
INSERT INTO `hyn_sys_login_history` VALUES (508, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '135.0.0.0', 'Windows 10', '2025-05-05 21:35:59');
INSERT INTO `hyn_sys_login_history` VALUES (509, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-06 10:01:01');
INSERT INTO `hyn_sys_login_history` VALUES (510, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-06 10:02:52');
INSERT INTO `hyn_sys_login_history` VALUES (511, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-06 10:09:25');
INSERT INTO `hyn_sys_login_history` VALUES (512, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-06 16:51:03');
INSERT INTO `hyn_sys_login_history` VALUES (513, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-06 21:45:19');
INSERT INTO `hyn_sys_login_history` VALUES (514, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-07 11:12:38');
INSERT INTO `hyn_sys_login_history` VALUES (515, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-07 12:06:25');
INSERT INTO `hyn_sys_login_history` VALUES (516, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-07 22:25:33');
INSERT INTO `hyn_sys_login_history` VALUES (517, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 09:29:56');
INSERT INTO `hyn_sys_login_history` VALUES (518, 1, 2, '验证码已过期', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 09:34:10');
INSERT INTO `hyn_sys_login_history` VALUES (519, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 09:34:14');
INSERT INTO `hyn_sys_login_history` VALUES (520, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 09:49:32');
INSERT INTO `hyn_sys_login_history` VALUES (521, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 09:49:42');
INSERT INTO `hyn_sys_login_history` VALUES (522, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 09:54:29');
INSERT INTO `hyn_sys_login_history` VALUES (523, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 09:57:13');
INSERT INTO `hyn_sys_login_history` VALUES (524, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 11:35:18');
INSERT INTO `hyn_sys_login_history` VALUES (525, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 11:40:02');
INSERT INTO `hyn_sys_login_history` VALUES (526, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 11:45:09');
INSERT INTO `hyn_sys_login_history` VALUES (527, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 12:05:01');
INSERT INTO `hyn_sys_login_history` VALUES (528, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-08 12:06:36');
INSERT INTO `hyn_sys_login_history` VALUES (529, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-08 22:08:56');
INSERT INTO `hyn_sys_login_history` VALUES (530, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-08 22:14:21');
INSERT INTO `hyn_sys_login_history` VALUES (531, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-08 22:15:24');
INSERT INTO `hyn_sys_login_history` VALUES (532, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-09 11:53:47');
INSERT INTO `hyn_sys_login_history` VALUES (533, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-09 21:42:15');
INSERT INTO `hyn_sys_login_history` VALUES (534, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-09 21:57:31');
INSERT INTO `hyn_sys_login_history` VALUES (535, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-10 13:13:03');
INSERT INTO `hyn_sys_login_history` VALUES (536, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-10 18:10:26');
INSERT INTO `hyn_sys_login_history` VALUES (537, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-11 12:03:44');
INSERT INTO `hyn_sys_login_history` VALUES (538, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-11 12:04:14');
INSERT INTO `hyn_sys_login_history` VALUES (539, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-11 12:26:16');
INSERT INTO `hyn_sys_login_history` VALUES (540, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-11 12:27:11');
INSERT INTO `hyn_sys_login_history` VALUES (541, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-11 12:32:22');
INSERT INTO `hyn_sys_login_history` VALUES (542, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-11 12:47:15');
INSERT INTO `hyn_sys_login_history` VALUES (543, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-11 12:48:30');
INSERT INTO `hyn_sys_login_history` VALUES (544, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-11 15:42:17');
INSERT INTO `hyn_sys_login_history` VALUES (545, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Linux', '2025-05-11 15:57:34');
INSERT INTO `hyn_sys_login_history` VALUES (546, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-11 16:51:40');
INSERT INTO `hyn_sys_login_history` VALUES (547, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 11:40:53');
INSERT INTO `hyn_sys_login_history` VALUES (548, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 12:00:37');
INSERT INTO `hyn_sys_login_history` VALUES (549, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 12:01:55');
INSERT INTO `hyn_sys_login_history` VALUES (550, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 12:02:58');
INSERT INTO `hyn_sys_login_history` VALUES (551, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 12:04:44');
INSERT INTO `hyn_sys_login_history` VALUES (552, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 12:14:33');
INSERT INTO `hyn_sys_login_history` VALUES (553, 1, 2, '用户不存在', 0, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 14:04:39');
INSERT INTO `hyn_sys_login_history` VALUES (554, 1, 2, '用户不存在', 0, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 14:04:43');
INSERT INTO `hyn_sys_login_history` VALUES (555, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 14:04:58');
INSERT INTO `hyn_sys_login_history` VALUES (556, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-12 21:32:51');
INSERT INTO `hyn_sys_login_history` VALUES (557, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-13 09:40:22');
INSERT INTO `hyn_sys_login_history` VALUES (558, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-13 10:01:37');
INSERT INTO `hyn_sys_login_history` VALUES (559, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-13 16:05:09');
INSERT INTO `hyn_sys_login_history` VALUES (560, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-13 16:44:52');
INSERT INTO `hyn_sys_login_history` VALUES (561, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-13 16:45:01');
INSERT INTO `hyn_sys_login_history` VALUES (562, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-13 21:22:47');
INSERT INTO `hyn_sys_login_history` VALUES (563, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-14 22:09:46');
INSERT INTO `hyn_sys_login_history` VALUES (564, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-14 22:11:20');
INSERT INTO `hyn_sys_login_history` VALUES (565, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-14 22:12:45');
INSERT INTO `hyn_sys_login_history` VALUES (566, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-15 22:09:25');
INSERT INTO `hyn_sys_login_history` VALUES (567, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-15 22:50:24');
INSERT INTO `hyn_sys_login_history` VALUES (568, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-15 22:50:44');
INSERT INTO `hyn_sys_login_history` VALUES (569, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-15 23:06:03');
INSERT INTO `hyn_sys_login_history` VALUES (570, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-15 23:06:23');
INSERT INTO `hyn_sys_login_history` VALUES (571, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-15 23:12:13');
INSERT INTO `hyn_sys_login_history` VALUES (572, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-15 23:13:29');
INSERT INTO `hyn_sys_login_history` VALUES (573, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-16 12:11:07');
INSERT INTO `hyn_sys_login_history` VALUES (574, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-16 22:57:37');
INSERT INTO `hyn_sys_login_history` VALUES (575, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-16 22:58:11');
INSERT INTO `hyn_sys_login_history` VALUES (576, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-16 22:58:51');
INSERT INTO `hyn_sys_login_history` VALUES (577, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-16 23:00:34');
INSERT INTO `hyn_sys_login_history` VALUES (578, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-16 23:00:59');
INSERT INTO `hyn_sys_login_history` VALUES (579, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-16 23:02:01');
INSERT INTO `hyn_sys_login_history` VALUES (580, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-16 23:02:21');
INSERT INTO `hyn_sys_login_history` VALUES (581, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-17 09:15:22');
INSERT INTO `hyn_sys_login_history` VALUES (582, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-17 09:20:18');
INSERT INTO `hyn_sys_login_history` VALUES (583, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-18 10:54:27');
INSERT INTO `hyn_sys_login_history` VALUES (584, 1, 1, '登录成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-18 13:19:16');
INSERT INTO `hyn_sys_login_history` VALUES (585, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-18 15:54:21');
INSERT INTO `hyn_sys_login_history` VALUES (586, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-18 17:09:31');
INSERT INTO `hyn_sys_login_history` VALUES (587, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-18 17:32:01');
INSERT INTO `hyn_sys_login_history` VALUES (588, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-18 17:58:59');
INSERT INTO `hyn_sys_login_history` VALUES (589, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '136.0.0.0', 'Windows 10', '2025-05-20 12:56:57');
INSERT INTO `hyn_sys_login_history` VALUES (590, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-05-31 15:15:10');
INSERT INTO `hyn_sys_login_history` VALUES (591, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-05-31 19:49:36');
INSERT INTO `hyn_sys_login_history` VALUES (592, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-05-31 19:54:28');
INSERT INTO `hyn_sys_login_history` VALUES (593, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-05-31 19:56:01');
INSERT INTO `hyn_sys_login_history` VALUES (594, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-05-31 22:41:12');
INSERT INTO `hyn_sys_login_history` VALUES (595, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-05-31 23:50:17');
INSERT INTO `hyn_sys_login_history` VALUES (596, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-01 11:19:28');
INSERT INTO `hyn_sys_login_history` VALUES (597, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-01 11:28:07');
INSERT INTO `hyn_sys_login_history` VALUES (598, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-01 11:33:43');
INSERT INTO `hyn_sys_login_history` VALUES (599, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-01 11:34:30');
INSERT INTO `hyn_sys_login_history` VALUES (600, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-01 11:36:41');
INSERT INTO `hyn_sys_login_history` VALUES (601, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-01 11:42:26');
INSERT INTO `hyn_sys_login_history` VALUES (602, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-01 11:46:15');
INSERT INTO `hyn_sys_login_history` VALUES (603, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-01 11:47:12');
INSERT INTO `hyn_sys_login_history` VALUES (604, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-06-08 11:34:43');
INSERT INTO `hyn_sys_login_history` VALUES (605, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 10:45:00');
INSERT INTO `hyn_sys_login_history` VALUES (606, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 14:44:32');
INSERT INTO `hyn_sys_login_history` VALUES (607, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 15:01:31');
INSERT INTO `hyn_sys_login_history` VALUES (608, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 15:06:59');
INSERT INTO `hyn_sys_login_history` VALUES (609, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 15:09:48');
INSERT INTO `hyn_sys_login_history` VALUES (610, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 15:10:08');
INSERT INTO `hyn_sys_login_history` VALUES (611, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 15:40:53');
INSERT INTO `hyn_sys_login_history` VALUES (612, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 16:18:27');
INSERT INTO `hyn_sys_login_history` VALUES (613, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 16:51:39');
INSERT INTO `hyn_sys_login_history` VALUES (614, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-12 18:52:55');
INSERT INTO `hyn_sys_login_history` VALUES (615, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-06-13 10:22:36');
INSERT INTO `hyn_sys_login_history` VALUES (616, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-06-15 17:44:44');
INSERT INTO `hyn_sys_login_history` VALUES (617, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-06-15 19:03:33');
INSERT INTO `hyn_sys_login_history` VALUES (618, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-16 23:44:55');
INSERT INTO `hyn_sys_login_history` VALUES (619, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-16 23:56:17');
INSERT INTO `hyn_sys_login_history` VALUES (620, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-16 23:58:12');
INSERT INTO `hyn_sys_login_history` VALUES (621, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-16 23:59:23');
INSERT INTO `hyn_sys_login_history` VALUES (622, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-17 11:43:30');
INSERT INTO `hyn_sys_login_history` VALUES (623, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Linux', '2025-06-17 11:47:33');
INSERT INTO `hyn_sys_login_history` VALUES (624, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '137.0.0.0', 'Windows 10', '2025-06-27 15:30:13');
INSERT INTO `hyn_sys_login_history` VALUES (625, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '138.0.0.0', 'Windows 10', '2025-07-02 22:04:26');
INSERT INTO `hyn_sys_login_history` VALUES (626, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '138.0.0.0', 'Windows 10', '2025-07-03 21:58:39');
INSERT INTO `hyn_sys_login_history` VALUES (627, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '138.0.0.0', 'Linux', '2025-07-05 14:16:15');
INSERT INTO `hyn_sys_login_history` VALUES (628, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '138.0.0.0', 'Windows 10', '2025-07-15 15:45:44');
INSERT INTO `hyn_sys_login_history` VALUES (629, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '138.0.0.0', 'Windows 10', '2025-07-15 22:10:26');
INSERT INTO `hyn_sys_login_history` VALUES (630, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '138.0.0.0', 'Windows 10', '2025-07-16 17:35:02');
INSERT INTO `hyn_sys_login_history` VALUES (631, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '138.0.0.0', 'Windows 10', '2025-07-17 18:04:42');
INSERT INTO `hyn_sys_login_history` VALUES (632, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '138.0.0.0', 'Windows 10', '2025-07-31 09:55:27');
INSERT INTO `hyn_sys_login_history` VALUES (633, 0, 3, '注销成功', 1, '127.0.0.1', '0|0|0|内网IP|内网IP', 'Chrome', '139.0.0.0', 'Linux', '2025-08-30 09:54:18');
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_member
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_member`;
CREATE TABLE "hyn_sys_member" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  "is_enabled" tinyint NOT NULL COMMENT '是否已启用（1=是，0=否）',
  "gender" tinyint NOT NULL COMMENT '性别（0=女，1=男，-1=未知）',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of hyn_sys_member
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_member` VALUES (13, 1, 0);
INSERT INTO `hyn_sys_member` VALUES (20, 0, -1);
INSERT INTO `hyn_sys_member` VALUES (21, 1, 0);
INSERT INTO `hyn_sys_member` VALUES (22, 1, 1);
INSERT INTO `hyn_sys_member` VALUES (23, 1, 1);
INSERT INTO `hyn_sys_member` VALUES (24, 1, 1);
INSERT INTO `hyn_sys_member` VALUES (25, 1, 1);
INSERT INTO `hyn_sys_member` VALUES (26, 1, 1);
INSERT INTO `hyn_sys_member` VALUES (27, 1, 1);
INSERT INTO `hyn_sys_member` VALUES (28, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_role`;
CREATE TABLE "hyn_sys_role" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，无符号自增',
  "role_code" varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  "role_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  "created_by" bigint NOT NULL COMMENT '创建人id',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '最后修改人id',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_role_code" ("role_code"),
  UNIQUE KEY "uk_role_name" ("role_name")
);

-- ----------------------------
-- Records of hyn_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_role` VALUES (1, 'ADMIN', '管理员', 1, '2020-10-27 22:32:04', 1, '2024-11-22 11:05:54');
INSERT INTO `hyn_sys_role` VALUES (21, 'developer', '开发人员', 1, '2024-08-28 21:44:41', 1, '2025-06-17 13:05:28');
INSERT INTO `hyn_sys_role` VALUES (59, 'tom', '布里', 1, '2025-06-24 16:02:48', 1, '2025-07-03 18:29:49');
INSERT INTO `hyn_sys_role` VALUES (62, 'jack', 'hahahahah', 1, '2025-07-05 13:53:59', 1, '2025-07-15 22:03:20');
INSERT INTO `hyn_sys_role` VALUES (64, 'test_role', '测试角色', 1, '2025-08-26 21:34:32', 1, '2025-08-28 16:21:18');
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_role_authority`;
CREATE TABLE "hyn_sys_role_authority" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，无符号自增',
  "role_id" bigint NOT NULL COMMENT '角色id',
  "authority_id" bigint NOT NULL COMMENT '权限id',
  "created_by" bigint NOT NULL COMMENT '创建人id',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_role_id_authority_id" ("role_id","authority_id")
);

-- ----------------------------
-- Records of hyn_sys_role_authority
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_role_authority` VALUES (996, 1, 64, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (997, 1, 1, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (998, 1, 65, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (999, 1, 2, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1000, 1, 66, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1001, 1, 4, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1002, 1, 5, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1003, 1, 78, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1005, 1, 23, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1006, 1, 36, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1007, 1, 37, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1008, 1, 38, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1009, 1, 39, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1010, 1, 40, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1011, 1, 41, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1012, 1, 42, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1013, 1, 43, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1014, 1, 44, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1015, 1, 45, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1016, 1, 46, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1017, 1, 47, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1018, 1, 48, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1019, 1, 49, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1020, 1, 55, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1021, 1, 56, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1022, 1, 57, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1023, 1, 59, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1024, 1, 60, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1025, 1, 62, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1026, 1, 63, 1, '2025-05-20 12:56:49');
INSERT INTO `hyn_sys_role_authority` VALUES (1237, 21, 39, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1238, 21, 1, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1239, 21, 23, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1240, 21, 40, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1241, 21, 37, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1242, 21, 38, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1243, 21, 41, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1244, 21, 42, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1245, 21, 36, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1246, 21, 2, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1247, 21, 63, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1248, 21, 64, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1249, 21, 65, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1250, 21, 66, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1251, 21, 4, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1252, 21, 5, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1253, 21, 43, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1254, 21, 44, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1255, 21, 45, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1256, 21, 46, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1257, 21, 47, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1258, 21, 49, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1259, 21, 48, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1260, 21, 55, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1261, 21, 56, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1262, 21, 57, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1263, 21, 59, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1264, 21, 60, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1265, 21, 62, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1266, 21, 78, 1, '2025-07-05 13:37:00');
INSERT INTO `hyn_sys_role_authority` VALUES (1271, 62, 78, 1, '2025-07-15 22:00:33');
INSERT INTO `hyn_sys_role_authority` VALUES (1272, 62, 23, 1, '2025-07-15 22:00:33');
INSERT INTO `hyn_sys_role_authority` VALUES (1273, 62, 40, 1, '2025-07-15 22:00:33');
INSERT INTO `hyn_sys_role_authority` VALUES (1274, 62, 37, 1, '2025-07-15 22:00:33');
INSERT INTO `hyn_sys_role_authority` VALUES (1275, 62, 38, 1, '2025-07-15 22:00:33');
INSERT INTO `hyn_sys_role_authority` VALUES (1276, 62, 41, 1, '2025-07-15 22:00:33');
INSERT INTO `hyn_sys_role_authority` VALUES (1277, 62, 42, 1, '2025-07-15 22:00:33');
INSERT INTO `hyn_sys_role_authority` VALUES (1278, 62, 1, 1, '2025-07-15 22:00:33');
INSERT INTO `hyn_sys_role_authority` VALUES (1293, 59, 36, 1, '2025-08-02 12:19:07');
INSERT INTO `hyn_sys_role_authority` VALUES (1294, 59, 1, 1, '2025-08-02 12:19:07');
INSERT INTO `hyn_sys_role_authority` VALUES (1346, 64, 23, 1, '2025-08-30 17:24:21');
INSERT INTO `hyn_sys_role_authority` VALUES (1347, 64, 40, 1, '2025-08-30 17:24:21');
INSERT INTO `hyn_sys_role_authority` VALUES (1348, 64, 37, 1, '2025-08-30 17:24:21');
INSERT INTO `hyn_sys_role_authority` VALUES (1349, 64, 38, 1, '2025-08-30 17:24:21');
INSERT INTO `hyn_sys_role_authority` VALUES (1350, 64, 41, 1, '2025-08-30 17:24:21');
INSERT INTO `hyn_sys_role_authority` VALUES (1351, 64, 42, 1, '2025-08-30 17:24:21');
INSERT INTO `hyn_sys_role_authority` VALUES (1352, 64, 1, 1, '2025-08-30 17:24:21');
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_user`;
CREATE TABLE "hyn_sys_user" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  "username" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  "password" char(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  "is_account_non_expired" tinyint NOT NULL DEFAULT '1' COMMENT '帐户是否未过期（1=是 0=否）',
  "is_account_non_locked" tinyint NOT NULL DEFAULT '1' COMMENT '帐户是否未锁定（1=是 0=否）',
  "is_credentials_non_expired" tinyint NOT NULL DEFAULT '1' COMMENT '凭据（即密码）是否未过期（1=是 0=否）',
  "is_enabled" tinyint NOT NULL DEFAULT '1' COMMENT '是否可用（1=是 0=否）',
  "password_change_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改密码的时间',
  "last_login_time" datetime DEFAULT NULL COMMENT '最近登录时间',
  "last_login_ip" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最近登录IP',
  "login_count" bigint NOT NULL DEFAULT '0' COMMENT '累计登录次数',
  "created_by" bigint NOT NULL COMMENT '创建人id',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人id',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_username" ("username"),
  KEY "idx_login_count" ("login_count"),
  KEY "idx_last_login_time" ("last_login_time")
);

-- ----------------------------
-- Records of hyn_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_user` VALUES (1, 'admin', '$2a$10$GZLRpfj4quBa1fnnter8vuOSA1LDX3SM0dU6/y2prPjt98OjTzzyq', 1, 1, 1, 1, '2022-06-21 23:38:34', NULL, NULL, 0, 1, '2022-06-11 10:37:27', 1, '2025-05-07 10:28:12');
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_user_profile`;
CREATE TABLE "hyn_sys_user_profile" (
  "id" bigint NOT NULL COMMENT '主键，用户id（即hyn_sys_user表的id）',
  "real_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户真实姓名',
  "nickname" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  "gender" tinyint DEFAULT NULL COMMENT '性别(1=男 0=女)',
  "birthday" date DEFAULT NULL COMMENT '出生日期',
  "avatar" varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像地址',
  "phone" varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机',
  "email" varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮箱',
  "position" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '职位',
  "remark" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  "created_by" bigint NOT NULL COMMENT '创建人id',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "last_updated_by" bigint NOT NULL COMMENT '修改人id',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_nickname" ("nickname"),
  UNIQUE KEY "uk_phone" ("phone"),
  UNIQUE KEY "uk_email" ("email")
);

-- ----------------------------
-- Records of hyn_sys_user_profile
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_user_profile` VALUES (1, '管理员', '薛定谔的猫', 1, NULL, 'dfd', NULL, NULL, 'java程序员', '', 1, '2022-06-23 00:08:59', 1, '2025-07-15 07:47:21');
INSERT INTO `hyn_sys_user_profile` VALUES (5, 'dsf', 'dsf', 0, NULL, 'dsf', 'sdf', 'dsf', 'sdf', 'dsf', 1, '2025-05-13 21:28:57', 1, '2025-05-13 21:28:57');
INSERT INTO `hyn_sys_user_profile` VALUES (6, 'dsf', 'dsf33', 0, NULL, 'dsf', '13267', 'fwefs@163.com', 'sdf', 'dsf', 1, '2025-05-15 22:12:15', 1, '2025-05-15 22:12:15');
INSERT INTO `hyn_sys_user_profile` VALUES (7, '', NULL, NULL, NULL, '', NULL, NULL, '', '', 1, '2025-08-22 13:06:29', 1, '2025-08-22 13:06:29');
INSERT INTO `hyn_sys_user_profile` VALUES (8, '', NULL, NULL, NULL, '', NULL, NULL, '', '', 1, '2025-08-22 13:11:05', 1, '2025-08-22 13:11:05');
INSERT INTO `hyn_sys_user_profile` VALUES (10, '张三', '', NULL, NULL, '', '', '', '', '', 1, '2025-09-13 10:01:16', 1, '2025-09-13 10:01:16');
INSERT INTO `hyn_sys_user_profile` VALUES (13, '李四', '周杰伦', NULL, NULL, '1', '1', '1', '', '', 1, '2025-09-13 10:21:42', 1, '2025-09-13 11:27:13');
INSERT INTO `hyn_sys_user_profile` VALUES (14, '王思聪', 'cvcv', NULL, NULL, '', NULL, NULL, '', '', 1, '2025-09-13 11:29:25', 1, '2025-09-13 13:16:39');
INSERT INTO `hyn_sys_user_profile` VALUES (15, '林俊杰', 'JJ', NULL, '1994-05-03', '', NULL, NULL, '', '', 1, '2025-09-20 12:01:16', 1, '2025-09-20 12:01:16');
INSERT INTO `hyn_sys_user_profile` VALUES (16, '节课', NULL, NULL, NULL, '', NULL, NULL, '', '', 1, '2025-09-20 12:15:13', 1, '2025-09-20 12:15:13');
INSERT INTO `hyn_sys_user_profile` VALUES (17, '汤姆', NULL, NULL, NULL, '', NULL, NULL, '', '', 1, '2025-09-20 12:20:18', 1, '2025-09-20 12:20:18');
INSERT INTO `hyn_sys_user_profile` VALUES (18, '梁家辉', NULL, NULL, NULL, '', NULL, NULL, '', '', 1, '2025-09-20 12:26:23', 1, '2025-09-20 12:26:23');
INSERT INTO `hyn_sys_user_profile` VALUES (19, '黄礼志', 'LZ', NULL, NULL, '', NULL, NULL, '', '', 1, '2025-09-20 12:30:18', 1, '2025-09-20 18:18:23');
INSERT INTO `hyn_sys_user_profile` VALUES (20, '沈腾', NULL, NULL, NULL, '', NULL, NULL, '', '', 1, '2025-09-20 18:20:09', 1, '2025-09-20 18:20:09');
INSERT INTO `hyn_sys_user_profile` VALUES (21, '如果这都不算爱', '测试111', NULL, NULL, '', NULL, NULL, '', '', 1, '2025-09-21 17:29:47', 1, '2025-09-21 22:13:02');
INSERT INTO `hyn_sys_user_profile` VALUES (22, '章若楠', '萨菲的', NULL, NULL, '', NULL, NULL, '', '', 1, '2025-09-21 22:31:48', 1, '2025-09-21 22:35:25');
INSERT INTO `hyn_sys_user_profile` VALUES (23, '我屮艸芔茻', NULL, NULL, NULL, '', NULL, NULL, '', '', 1, '2025-09-23 22:51:35', 1, '2025-09-24 10:56:34');
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_user_role`;
CREATE TABLE "hyn_sys_user_role" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT '主键，无符号自增',
  "user_id" bigint NOT NULL COMMENT '用户id',
  "role_id" bigint NOT NULL COMMENT '角色id',
  "created_by" bigint unsigned NOT NULL COMMENT '创建人id',
  "created_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_user_id_role_id" ("user_id","role_id")
);

-- ----------------------------
-- Records of hyn_sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_user_role` VALUES (1, 1, 1, 0, '2020-10-27 22:34:29');
INSERT INTO `hyn_sys_user_role` VALUES (6, 8, 21, 1, '2025-08-22 13:11:05');
COMMIT;

-- ----------------------------
-- Table structure for hyn_sys_user_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `hyn_sys_user_snapshot`;
CREATE TABLE "hyn_sys_user_snapshot" (
  "id" bigint NOT NULL COMMENT '主键（使用hyn_sys_user表的 id）',
  "real_name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
  "nickname" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  "avatar" varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像URL',
  "last_updated_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of hyn_sys_user_snapshot
-- ----------------------------
BEGIN;
INSERT INTO `hyn_sys_user_snapshot` VALUES (1, '管理员', '薛定谔的猫', 'dfd', '2025-07-15 07:47:21');
INSERT INTO `hyn_sys_user_snapshot` VALUES (5, 'dsf（已删除）', 'dsf', 'dsf', '2025-09-20 18:41:20');
INSERT INTO `hyn_sys_user_snapshot` VALUES (6, 'dsf', 'dsf33', 'dsf', '2025-05-15 22:12:15');
INSERT INTO `hyn_sys_user_snapshot` VALUES (7, '', NULL, '', '2025-08-22 13:06:29');
INSERT INTO `hyn_sys_user_snapshot` VALUES (8, '', NULL, '', '2025-08-22 13:11:05');
INSERT INTO `hyn_sys_user_snapshot` VALUES (10, '张三（已删除）', '', '', '2025-09-20 18:41:20');
INSERT INTO `hyn_sys_user_snapshot` VALUES (13, '李四（已删除）', '周杰伦', '1', '2025-09-20 18:41:20');
INSERT INTO `hyn_sys_user_snapshot` VALUES (14, '王思聪（已删除）', 'cvcv', '', '2025-09-20 18:41:20');
INSERT INTO `hyn_sys_user_snapshot` VALUES (15, '林俊杰（已删除）', 'JJ', '', '2025-09-20 18:41:20');
INSERT INTO `hyn_sys_user_snapshot` VALUES (16, '节课（已删除）', NULL, '', '2025-09-20 18:41:20');
INSERT INTO `hyn_sys_user_snapshot` VALUES (17, '汤姆（已删除）', NULL, '', '2025-09-20 18:41:20');
INSERT INTO `hyn_sys_user_snapshot` VALUES (18, '梁家辉', NULL, NULL, '2025-09-20 12:26:23');
INSERT INTO `hyn_sys_user_snapshot` VALUES (19, '黄礼志', 'LZ', '', '2025-09-20 18:18:23');
INSERT INTO `hyn_sys_user_snapshot` VALUES (20, '沈腾', NULL, NULL, '2025-09-20 18:20:09');
INSERT INTO `hyn_sys_user_snapshot` VALUES (21, 'null（已删除）', 'null（已删除）', '', '2025-09-21 22:14:37');
INSERT INTO `hyn_sys_user_snapshot` VALUES (22, '章若楠（已删除）', '萨菲的（已删除）', '', '2025-09-21 22:36:34');
INSERT INTO `hyn_sys_user_snapshot` VALUES (23, '我屮艸芔茻（已删除）', 'null（已删除）', '', '2025-09-24 10:58:01');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE "sys_user" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  "username" varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名',
  "password" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  "name" varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  "description" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
  "status" tinyint DEFAULT NULL COMMENT '状态（1：正常 0：停用）',
  PRIMARY KEY ("id"),
  UNIQUE KEY "idx_username" ("username")
);

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'user', '$2a$10$8fyY0WbNAr980e6nLcPL5ugmpkLLH3serye5SJ3UcDForTW5b0Sx.', '测试用户', 'Spring Security 测试用户', 1);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE "user" (
  "id" bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  "login_name" varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录名',
  "password" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_login_name" ("login_name")
);

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
