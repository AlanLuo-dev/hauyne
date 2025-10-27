/*
 Navicat Premium Data Transfer

 Source Server         : aiven
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : hauyne-dev-luoyingxiong123-9aa4.c.aivencloud.com:16704
 Source Schema         : hauyne_audit_dev

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 15/10/2025 21:52:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hyn_audit_user_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `hyn_audit_user_snapshot`;
CREATE TABLE "hyn_audit_user_snapshot" (
  "id" bigint NOT NULL COMMENT '主键（使用hyn_sys_user_profile 表的 id）',
  "real_name" varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
  "nickname" varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  "avatar" varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像URL',
  "last_updated_time" datetime NOT NULL COMMENT '最后修改时间（使用hyn_sys_user_profile 表的 last_updated_time）',
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of hyn_audit_user_snapshot
-- ----------------------------
BEGIN;
INSERT INTO `hyn_audit_user_snapshot` VALUES (1, '管理员', '薛定谔的猫', 'dfd', '2025-10-15 21:39:24');
INSERT INTO `hyn_audit_user_snapshot` VALUES (5, 'dsf（已删除）', 'dsf', 'dsf', '2025-09-20 18:41:20');
INSERT INTO `hyn_audit_user_snapshot` VALUES (10, '张三（已删除）', '', '', '2025-09-20 18:41:20');
INSERT INTO `hyn_audit_user_snapshot` VALUES (13, '李四（已删除）', '周杰伦', '1', '2025-09-20 18:41:20');
INSERT INTO `hyn_audit_user_snapshot` VALUES (14, '王思聪（已删除）', 'cvcv', '', '2025-09-20 18:41:20');
INSERT INTO `hyn_audit_user_snapshot` VALUES (15, '林俊杰（已删除）', 'JJ', '', '2025-09-20 18:41:20');
INSERT INTO `hyn_audit_user_snapshot` VALUES (16, '节课（已删除）', NULL, '', '2025-09-20 18:41:20');
INSERT INTO `hyn_audit_user_snapshot` VALUES (17, '汤姆（已删除）', NULL, '', '2025-09-20 18:41:20');
INSERT INTO `hyn_audit_user_snapshot` VALUES (18, '梁家辉（已删除）', NULL, '', '2025-09-20 12:26:23');
INSERT INTO `hyn_audit_user_snapshot` VALUES (19, '黄礼志（已删除）', 'LZ', '', '2025-09-20 18:18:23');
INSERT INTO `hyn_audit_user_snapshot` VALUES (20, '沈腾', NULL, NULL, '2025-09-20 18:20:09');
INSERT INTO `hyn_audit_user_snapshot` VALUES (21, 'null（已删除）', 'null（已删除）', '', '2025-09-21 22:14:37');
INSERT INTO `hyn_audit_user_snapshot` VALUES (22, '章若楠（已删除）', '萨菲的（已删除）', '', '2025-09-21 22:36:34');
INSERT INTO `hyn_audit_user_snapshot` VALUES (23, '我屮艸芔茻（已删除）', 'null（已删除）', '', '2025-09-24 10:58:01');
INSERT INTO `hyn_audit_user_snapshot` VALUES (24, '才狼', NULL, NULL, '2025-09-24 22:13:11');
COMMIT;

-- ----------------------------
-- Table structure for jv_commit
-- ----------------------------
DROP TABLE IF EXISTS `jv_commit`;
CREATE TABLE "jv_commit" (
  "commit_pk" bigint NOT NULL AUTO_INCREMENT,
  "author" varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  "commit_date" timestamp(3) NULL DEFAULT NULL,
  "commit_date_instant" varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  "commit_id" decimal(22,2) DEFAULT NULL,
  PRIMARY KEY ("commit_pk"),
  KEY "jv_commit_commit_id_idx" ("commit_id")
);

-- ----------------------------
-- Records of jv_commit
-- ----------------------------
BEGIN;
INSERT INTO `jv_commit` VALUES (1, '1', '2025-10-15 21:50:52.480', '2025-10-15T13:50:52.480685430Z', 1.00);
INSERT INTO `jv_commit` VALUES (2, '1', '2025-10-15 21:51:54.551', '2025-10-15T13:51:54.551907333Z', 2.00);
COMMIT;

-- ----------------------------
-- Table structure for jv_commit_property
-- ----------------------------
DROP TABLE IF EXISTS `jv_commit_property`;
CREATE TABLE "jv_commit_property" (
  "property_name" varchar(191) COLLATE utf8mb4_general_ci NOT NULL,
  "property_value" varchar(600) COLLATE utf8mb4_general_ci DEFAULT NULL,
  "commit_fk" bigint NOT NULL,
  PRIMARY KEY ("commit_fk","property_name"),
  KEY "jv_commit_property_commit_fk_idx" ("commit_fk"),
  KEY "jv_commit_property_property_name_property_value_idx" ("property_name","property_value"(191)),
  CONSTRAINT "jv_commit_property_commit_fk" FOREIGN KEY ("commit_fk") REFERENCES "jv_commit" ("commit_pk")
);

-- ----------------------------
-- Records of jv_commit_property
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for jv_global_id
-- ----------------------------
DROP TABLE IF EXISTS `jv_global_id`;
CREATE TABLE "jv_global_id" (
  "global_id_pk" bigint NOT NULL AUTO_INCREMENT,
  "local_id" varchar(191) COLLATE utf8mb4_general_ci DEFAULT NULL,
  "fragment" varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  "type_name" varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  "owner_id_fk" bigint DEFAULT NULL,
  PRIMARY KEY ("global_id_pk"),
  KEY "jv_global_id_local_id_idx" ("local_id"),
  KEY "jv_global_id_owner_id_fk_idx" ("owner_id_fk"),
  CONSTRAINT "jv_global_id_owner_id_fk" FOREIGN KEY ("owner_id_fk") REFERENCES "jv_global_id" ("global_id_pk")
);

-- ----------------------------
-- Records of jv_global_id
-- ----------------------------
BEGIN;
INSERT INTO `jv_global_id` VALUES (1, '62', NULL, 'hyn_sys_role', NULL);
INSERT INTO `jv_global_id` VALUES (2, '62', NULL, 'hyn_sys_role_authority', NULL);
COMMIT;

-- ----------------------------
-- Table structure for jv_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `jv_snapshot`;
CREATE TABLE "jv_snapshot" (
  "snapshot_pk" bigint NOT NULL AUTO_INCREMENT,
  "type" varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  "version" bigint DEFAULT NULL,
  "state" text COLLATE utf8mb4_general_ci,
  "changed_properties" text COLLATE utf8mb4_general_ci,
  "managed_type" varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  "global_id_fk" bigint DEFAULT NULL,
  "commit_fk" bigint DEFAULT NULL,
  PRIMARY KEY ("snapshot_pk"),
  KEY "jv_snapshot_global_id_fk_idx" ("global_id_fk"),
  KEY "jv_snapshot_commit_fk_idx" ("commit_fk"),
  CONSTRAINT "jv_snapshot_commit_fk" FOREIGN KEY ("commit_fk") REFERENCES "jv_commit" ("commit_pk"),
  CONSTRAINT "jv_snapshot_global_id_fk" FOREIGN KEY ("global_id_fk") REFERENCES "jv_global_id" ("global_id_pk")
);

-- ----------------------------
-- Records of jv_snapshot
-- ----------------------------
BEGIN;
INSERT INTO `jv_snapshot` VALUES (1, 'INITIAL', 1, '{\n  \"roleCode\": \"wangfei\",\n  \"roleName\": \"王菲\",\n  \"id\": 62\n}', '[\n  \"roleCode\",\n  \"roleName\",\n  \"id\"\n]', 'hyn_sys_role', 1, 1);
INSERT INTO `jv_snapshot` VALUES (2, 'INITIAL', 1, '{\n  \"authorityNames\": [\n    \"系统管理\",\n    \"权限管理\",\n    \"查询权限列表\",\n    \"新增权限资源\",\n    \"修改权限资源\",\n    \"删除权限资源\"\n  ],\n  \"roleId\": 62\n}', '[\n  \"authorityNames\",\n  \"roleId\"\n]', 'hyn_sys_role_authority', 2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
