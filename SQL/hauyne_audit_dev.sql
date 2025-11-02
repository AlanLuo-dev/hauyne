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

 Date: 02/11/2025 10:53:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hyn_audit_user_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `hyn_audit_user_snapshot`;
CREATE TABLE `hyn_audit_user_snapshot`  (
  `id` bigint NOT NULL COMMENT '主键（使用hyn_sys_user_profile 表的 id）',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像URL',
  `last_updated_time` datetime NOT NULL COMMENT '最后修改时间（使用hyn_sys_user_profile 表的 last_updated_time）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息-快照表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hyn_audit_user_snapshot
-- ----------------------------
INSERT INTO `hyn_audit_user_snapshot` VALUES (1, '管理员', '薛定谔的猫', 'dfd', '2025-10-15 21:39:24');
INSERT INTO `hyn_audit_user_snapshot` VALUES (5, 'dsf（已删除）', 'dsf', 'dsf', '2025-09-20 18:41:20');
INSERT INTO `hyn_audit_user_snapshot` VALUES (6, 'dsf', 'dsf33', 'dsf', '2025-05-15 22:12:15');
INSERT INTO `hyn_audit_user_snapshot` VALUES (7, '', NULL, '', '2025-08-22 13:06:29');
INSERT INTO `hyn_audit_user_snapshot` VALUES (8, '', NULL, '', '2025-08-22 13:11:05');
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

-- ----------------------------
-- Table structure for jv_commit
-- ----------------------------
DROP TABLE IF EXISTS `jv_commit`;
CREATE TABLE `jv_commit`  (
  `commit_pk` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_date` timestamp(3) NULL DEFAULT NULL,
  `commit_date_instant` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_id` decimal(22, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`commit_pk`) USING BTREE,
  INDEX `jv_commit_commit_id_idx`(`commit_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jv_commit
-- ----------------------------
INSERT INTO `jv_commit` VALUES (1, '1', '2025-10-15 21:50:52.480', '2025-10-15T13:50:52.480685430Z', 1.00);
INSERT INTO `jv_commit` VALUES (2, '1', '2025-10-15 21:51:54.551', '2025-10-15T13:51:54.551907333Z', 2.00);
INSERT INTO `jv_commit` VALUES (3, '1', '2025-10-15 22:03:33.377', '2025-10-15T14:03:33.377571073Z', 3.00);
INSERT INTO `jv_commit` VALUES (4, '1', '2025-10-27 22:41:07.370', '2025-10-27T14:41:07.370565612Z', 4.00);

-- ----------------------------
-- Table structure for jv_commit_property
-- ----------------------------
DROP TABLE IF EXISTS `jv_commit_property`;
CREATE TABLE `jv_commit_property`  (
  `property_name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `property_value` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_fk` bigint NOT NULL,
  PRIMARY KEY (`commit_fk`, `property_name`) USING BTREE,
  INDEX `jv_commit_property_commit_fk_idx`(`commit_fk` ASC) USING BTREE,
  INDEX `jv_commit_property_property_name_property_value_idx`(`property_name` ASC, `property_value`(191) ASC) USING BTREE,
  CONSTRAINT `jv_commit_property_commit_fk` FOREIGN KEY (`commit_fk`) REFERENCES `jv_commit` (`commit_pk`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jv_commit_property
-- ----------------------------

-- ----------------------------
-- Table structure for jv_global_id
-- ----------------------------
DROP TABLE IF EXISTS `jv_global_id`;
CREATE TABLE `jv_global_id`  (
  `global_id_pk` bigint NOT NULL AUTO_INCREMENT,
  `local_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fragment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `owner_id_fk` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`global_id_pk`) USING BTREE,
  INDEX `jv_global_id_local_id_idx`(`local_id` ASC) USING BTREE,
  INDEX `jv_global_id_owner_id_fk_idx`(`owner_id_fk` ASC) USING BTREE,
  CONSTRAINT `jv_global_id_owner_id_fk` FOREIGN KEY (`owner_id_fk`) REFERENCES `jv_global_id` (`global_id_pk`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jv_global_id
-- ----------------------------
INSERT INTO `jv_global_id` VALUES (1, '62', NULL, 'hyn_sys_role', NULL);
INSERT INTO `jv_global_id` VALUES (2, '62', NULL, 'hyn_sys_role_authority', NULL);
INSERT INTO `jv_global_id` VALUES (3, '65', NULL, 'hyn_sys_role_authority', NULL);

-- ----------------------------
-- Table structure for jv_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `jv_snapshot`;
CREATE TABLE `jv_snapshot`  (
  `snapshot_pk` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version` bigint NULL DEFAULT NULL,
  `state` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `changed_properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `managed_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `global_id_fk` bigint NULL DEFAULT NULL,
  `commit_fk` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`snapshot_pk`) USING BTREE,
  INDEX `jv_snapshot_global_id_fk_idx`(`global_id_fk` ASC) USING BTREE,
  INDEX `jv_snapshot_commit_fk_idx`(`commit_fk` ASC) USING BTREE,
  CONSTRAINT `jv_snapshot_commit_fk` FOREIGN KEY (`commit_fk`) REFERENCES `jv_commit` (`commit_pk`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `jv_snapshot_global_id_fk` FOREIGN KEY (`global_id_fk`) REFERENCES `jv_global_id` (`global_id_pk`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jv_snapshot
-- ----------------------------
INSERT INTO `jv_snapshot` VALUES (1, 'INITIAL', 1, '{\n  \"roleCode\": \"wangfei\",\n  \"roleName\": \"王菲\",\n  \"id\": 62\n}', '[\n  \"roleCode\",\n  \"roleName\",\n  \"id\"\n]', 'hyn_sys_role', 1, 1);
INSERT INTO `jv_snapshot` VALUES (2, 'INITIAL', 1, '{\n  \"authorityNames\": [\n    \"系统管理\",\n    \"权限管理\",\n    \"查询权限列表\",\n    \"新增权限资源\",\n    \"修改权限资源\",\n    \"删除权限资源\"\n  ],\n  \"roleId\": 62\n}', '[\n  \"authorityNames\",\n  \"roleId\"\n]', 'hyn_sys_role_authority', 2, 2);
INSERT INTO `jv_snapshot` VALUES (3, 'UPDATE', 2, '{\n  \"roleCode\": \"joji\",\n  \"roleName\": \"王菲\",\n  \"id\": 62\n}', '[\n  \"roleCode\"\n]', 'hyn_sys_role', 1, 3);
INSERT INTO `jv_snapshot` VALUES (4, 'INITIAL', 1, '{\n  \"authorityNames\": [\n    \"系统管理\",\n    \"字典管理\",\n    \"角色管理\",\n    \"新增角色\",\n    \"修改角色\",\n    \"分页查询角色\",\n    \"删除角色\",\n    \"分配权限\",\n    \"字典值管理\",\n    \"查询字典值\",\n    \"新增字典值\",\n    \"修改字典值\",\n    \"删除字典值\",\n    \"启用/禁用字典值\",\n    \"调整字典值排序\"\n  ],\n  \"roleId\": 65\n}', '[\n  \"authorityNames\",\n  \"roleId\"\n]', 'hyn_sys_role_authority', 3, 4);

SET FOREIGN_KEY_CHECKS = 1;
