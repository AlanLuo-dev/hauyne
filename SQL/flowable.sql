/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 127.0.0.1:3306
 Source Schema         : flowable

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 19/03/2022 23:59:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ACT_APP_APPDEF
-- ----------------------------
DROP TABLE IF EXISTS `ACT_APP_APPDEF`;
CREATE TABLE `ACT_APP_APPDEF` (
  `ID_` varchar(255) NOT NULL,
  `REV_` int NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) NOT NULL,
  `VERSION_` int NOT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_IDX_APP_DEF_UNIQ` (`KEY_`,`VERSION_`,`TENANT_ID_`),
  KEY `ACT_IDX_APP_DEF_DPLY` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_APP_DEF_DPLY` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_APP_DEPLOYMENT` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_APP_APPDEF
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_APP_DATABASECHANGELOG
-- ----------------------------
DROP TABLE IF EXISTS `ACT_APP_DATABASECHANGELOG`;
CREATE TABLE `ACT_APP_DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_APP_DATABASECHANGELOG
-- ----------------------------
BEGIN;
INSERT INTO `ACT_APP_DATABASECHANGELOG` VALUES ('1', 'flowable', 'org/flowable/app/db/liquibase/flowable-app-db-changelog.xml', '2022-03-19 23:52:23', 1, 'EXECUTED', '8:496fc778bdf2ab13f2e1926d0e63e0a2', 'createTable tableName=ACT_APP_DEPLOYMENT; createTable tableName=ACT_APP_DEPLOYMENT_RESOURCE; addForeignKeyConstraint baseTableName=ACT_APP_DEPLOYMENT_RESOURCE, constraintName=ACT_FK_APP_RSRC_DPL, referencedTableName=ACT_APP_DEPLOYMENT; createIndex...', '', NULL, '4.3.5', NULL, NULL, '0683828159');
INSERT INTO `ACT_APP_DATABASECHANGELOG` VALUES ('2', 'flowable', 'org/flowable/app/db/liquibase/flowable-app-db-changelog.xml', '2022-03-19 23:52:23', 2, 'EXECUTED', '8:ccea9ebfb6c1f8367ca4dd473fcbb7db', 'modifyDataType columnName=DEPLOY_TIME_, tableName=ACT_APP_DEPLOYMENT', '', NULL, '4.3.5', NULL, NULL, '0683828159');
INSERT INTO `ACT_APP_DATABASECHANGELOG` VALUES ('3', 'flowable', 'org/flowable/app/db/liquibase/flowable-app-db-changelog.xml', '2022-03-19 23:52:23', 3, 'EXECUTED', '8:f1f8aff320aade831944ebad24355f3d', 'createIndex indexName=ACT_IDX_APP_DEF_UNIQ, tableName=ACT_APP_APPDEF', '', NULL, '4.3.5', NULL, NULL, '0683828159');
COMMIT;

-- ----------------------------
-- Table structure for ACT_APP_DATABASECHANGELOGLOCK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_APP_DATABASECHANGELOGLOCK`;
CREATE TABLE `ACT_APP_DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_APP_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN;
INSERT INTO `ACT_APP_DATABASECHANGELOGLOCK` VALUES (1, b'0', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ACT_APP_DEPLOYMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_APP_DEPLOYMENT`;
CREATE TABLE `ACT_APP_DEPLOYMENT` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `DEPLOY_TIME_` datetime(3) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_APP_DEPLOYMENT
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_APP_DEPLOYMENT_RESOURCE
-- ----------------------------
DROP TABLE IF EXISTS `ACT_APP_DEPLOYMENT_RESOURCE`;
CREATE TABLE `ACT_APP_DEPLOYMENT_RESOURCE` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_BYTES_` longblob,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_APP_RSRC_DPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_APP_RSRC_DPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_APP_DEPLOYMENT` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_APP_DEPLOYMENT_RESOURCE
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_CASEDEF
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_CASEDEF`;
CREATE TABLE `ACT_CMMN_CASEDEF` (
  `ID_` varchar(255) NOT NULL,
  `REV_` int NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) NOT NULL,
  `VERSION_` int NOT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` bit(1) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  `DGRM_RESOURCE_NAME_` varchar(4000) DEFAULT NULL,
  `HAS_START_FORM_KEY_` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_IDX_CASE_DEF_UNIQ` (`KEY_`,`VERSION_`,`TENANT_ID_`),
  KEY `ACT_IDX_CASE_DEF_DPLY` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_CASE_DEF_DPLY` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_CMMN_DEPLOYMENT` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_CASEDEF
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_DATABASECHANGELOG
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_DATABASECHANGELOG`;
CREATE TABLE `ACT_CMMN_DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_DATABASECHANGELOG
-- ----------------------------
BEGIN;
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('1', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:23', 1, 'EXECUTED', '8:8b4b922d90b05ff27483abefc9597aa6', 'createTable tableName=ACT_CMMN_DEPLOYMENT; createTable tableName=ACT_CMMN_DEPLOYMENT_RESOURCE; addForeignKeyConstraint baseTableName=ACT_CMMN_DEPLOYMENT_RESOURCE, constraintName=ACT_FK_CMMN_RSRC_DPL, referencedTableName=ACT_CMMN_DEPLOYMENT; create...', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('2', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:23', 2, 'EXECUTED', '8:65e39b3d385706bb261cbeffe7533cbe', 'addColumn tableName=ACT_CMMN_CASEDEF; addColumn tableName=ACT_CMMN_DEPLOYMENT_RESOURCE; addColumn tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('3', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:23', 3, 'EXECUTED', '8:c01f6e802b49436b4489040da3012359', 'addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_RU_CASE_INST; createIndex indexName=ACT_IDX_PLAN_ITEM_STAGE_INST, tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableNam...', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('4', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:23', 4, 'EXECUTED', '8:e40d29cb79345b7fb5afd38a7f0ba8fc', 'createTable tableName=ACT_CMMN_HI_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_RU_MIL_INST; addColumn tableName=ACT_CMMN_HI_MIL_INST', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('5', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 5, 'EXECUTED', '8:70349de472f87368dcdec971a10311a0', 'modifyDataType columnName=DEPLOY_TIME_, tableName=ACT_CMMN_DEPLOYMENT; modifyDataType columnName=START_TIME_, tableName=ACT_CMMN_RU_CASE_INST; modifyDataType columnName=START_TIME_, tableName=ACT_CMMN_RU_PLAN_ITEM_INST; modifyDataType columnName=T...', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('6', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 6, 'EXECUTED', '8:10e82e26a7fee94c32a92099c059c18c', 'createIndex indexName=ACT_IDX_CASE_DEF_UNIQ, tableName=ACT_CMMN_CASEDEF', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('7', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 7, 'EXECUTED', '8:530bc81a1e30618ccf4a2da1f7c6c043', 'renameColumn newColumnName=CREATE_TIME_, oldColumnName=START_TIME_, tableName=ACT_CMMN_RU_PLAN_ITEM_INST; renameColumn newColumnName=CREATE_TIME_, oldColumnName=CREATED_TIME_, tableName=ACT_CMMN_HI_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_RU_P...', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('8', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 8, 'EXECUTED', '8:e8c2eb1ce28bc301efe07e0e29757781', 'addColumn tableName=ACT_CMMN_HI_PLAN_ITEM_INST', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('9', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 9, 'EXECUTED', '8:4cb4782b9bdec5ced2a64c525aa7b3a0', 'addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_HI_PLAN_ITEM_INST', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('10', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 10, 'EXECUTED', '8:341c16be247f5d17badc9809da8691f9', 'addColumn tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_RU_CASE_INST; createIndex indexName=ACT_IDX_CASE_INST_REF_ID_, tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_HI_CASE_INST; addColumn tableName=ACT_CMMN_HI_CASE...', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('11', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 11, 'EXECUTED', '8:d7c4da9276bcfffbfb0ebfb25e3f7b05', 'addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_HI_PLAN_ITEM_INST', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('12', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 12, 'EXECUTED', '8:adf4ecc45f2aa9a44a5626b02e1d6f98', 'addColumn tableName=ACT_CMMN_RU_CASE_INST', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('13', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 13, 'EXECUTED', '8:7550626f964ab5518464709408333ec1', 'addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_HI_PLAN_ITEM_INST', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('14', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 14, 'EXECUTED', '8:086b40b3a05596dcc8a8d7479922d494', 'addColumn tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_HI_CASE_INST', '', NULL, '4.3.5', NULL, NULL, '0683828602');
INSERT INTO `ACT_CMMN_DATABASECHANGELOG` VALUES ('16', 'flowable', 'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', '2022-03-19 23:52:24', 15, 'EXECUTED', '8:a697a222ddd99dd15b36516a252f1c63', 'addColumn tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_HI_CASE_INST', '', NULL, '4.3.5', NULL, NULL, '0683828602');
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_DATABASECHANGELOGLOCK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_DATABASECHANGELOGLOCK`;
CREATE TABLE `ACT_CMMN_DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN;
INSERT INTO `ACT_CMMN_DATABASECHANGELOGLOCK` VALUES (1, b'0', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_DEPLOYMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_DEPLOYMENT`;
CREATE TABLE `ACT_CMMN_DEPLOYMENT` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `DEPLOY_TIME_` datetime(3) DEFAULT NULL,
  `PARENT_DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_DEPLOYMENT
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_DEPLOYMENT_RESOURCE
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_DEPLOYMENT_RESOURCE`;
CREATE TABLE `ACT_CMMN_DEPLOYMENT_RESOURCE` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_BYTES_` longblob,
  `GENERATED_` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_CMMN_RSRC_DPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_CMMN_RSRC_DPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_CMMN_DEPLOYMENT` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_DEPLOYMENT_RESOURCE
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_HI_CASE_INST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_HI_CASE_INST`;
CREATE TABLE `ACT_CMMN_HI_CASE_INST` (
  `ID_` varchar(255) NOT NULL,
  `REV_` int NOT NULL,
  `BUSINESS_KEY_` varchar(255) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `PARENT_ID_` varchar(255) DEFAULT NULL,
  `CASE_DEF_ID_` varchar(255) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `START_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `START_USER_ID_` varchar(255) DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  `REFERENCE_ID_` varchar(255) DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) DEFAULT NULL,
  `LAST_REACTIVATION_TIME_` datetime(3) DEFAULT NULL,
  `LAST_REACTIVATION_USER_ID_` varchar(255) DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_HI_CASE_INST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_HI_MIL_INST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_HI_MIL_INST`;
CREATE TABLE `ACT_CMMN_HI_MIL_INST` (
  `ID_` varchar(255) NOT NULL,
  `REV_` int NOT NULL,
  `NAME_` varchar(255) NOT NULL,
  `TIME_STAMP_` datetime(3) DEFAULT NULL,
  `CASE_INST_ID_` varchar(255) NOT NULL,
  `CASE_DEF_ID_` varchar(255) NOT NULL,
  `ELEMENT_ID_` varchar(255) NOT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_HI_MIL_INST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_HI_PLAN_ITEM_INST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_HI_PLAN_ITEM_INST`;
CREATE TABLE `ACT_CMMN_HI_PLAN_ITEM_INST` (
  `ID_` varchar(255) NOT NULL,
  `REV_` int NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `CASE_DEF_ID_` varchar(255) DEFAULT NULL,
  `CASE_INST_ID_` varchar(255) DEFAULT NULL,
  `STAGE_INST_ID_` varchar(255) DEFAULT NULL,
  `IS_STAGE_` bit(1) DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) DEFAULT NULL,
  `ITEM_DEFINITION_ID_` varchar(255) DEFAULT NULL,
  `ITEM_DEFINITION_TYPE_` varchar(255) DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_AVAILABLE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_ENABLED_TIME_` datetime(3) DEFAULT NULL,
  `LAST_DISABLED_TIME_` datetime(3) DEFAULT NULL,
  `LAST_STARTED_TIME_` datetime(3) DEFAULT NULL,
  `LAST_SUSPENDED_TIME_` datetime(3) DEFAULT NULL,
  `COMPLETED_TIME_` datetime(3) DEFAULT NULL,
  `OCCURRED_TIME_` datetime(3) DEFAULT NULL,
  `TERMINATED_TIME_` datetime(3) DEFAULT NULL,
  `EXIT_TIME_` datetime(3) DEFAULT NULL,
  `ENDED_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  `START_USER_ID_` varchar(255) DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  `ENTRY_CRITERION_ID_` varchar(255) DEFAULT NULL,
  `EXIT_CRITERION_ID_` varchar(255) DEFAULT NULL,
  `SHOW_IN_OVERVIEW_` bit(1) DEFAULT NULL,
  `EXTRA_VALUE_` varchar(255) DEFAULT NULL,
  `DERIVED_CASE_DEF_ID_` varchar(255) DEFAULT NULL,
  `LAST_UNAVAILABLE_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_HI_PLAN_ITEM_INST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_RU_CASE_INST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_RU_CASE_INST`;
CREATE TABLE `ACT_CMMN_RU_CASE_INST` (
  `ID_` varchar(255) NOT NULL,
  `REV_` int NOT NULL,
  `BUSINESS_KEY_` varchar(255) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `PARENT_ID_` varchar(255) DEFAULT NULL,
  `CASE_DEF_ID_` varchar(255) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `START_TIME_` datetime(3) DEFAULT NULL,
  `START_USER_ID_` varchar(255) DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  `LOCK_TIME_` datetime(3) DEFAULT NULL,
  `IS_COMPLETEABLE_` bit(1) DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) DEFAULT NULL,
  `LAST_REACTIVATION_TIME_` datetime(3) DEFAULT NULL,
  `LAST_REACTIVATION_USER_ID_` varchar(255) DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_CASE_INST_CASE_DEF` (`CASE_DEF_ID_`),
  KEY `ACT_IDX_CASE_INST_PARENT` (`PARENT_ID_`),
  KEY `ACT_IDX_CASE_INST_REF_ID_` (`REFERENCE_ID_`),
  CONSTRAINT `ACT_FK_CASE_INST_CASE_DEF` FOREIGN KEY (`CASE_DEF_ID_`) REFERENCES `ACT_CMMN_CASEDEF` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_RU_CASE_INST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_RU_MIL_INST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_RU_MIL_INST`;
CREATE TABLE `ACT_CMMN_RU_MIL_INST` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) NOT NULL,
  `TIME_STAMP_` datetime(3) DEFAULT NULL,
  `CASE_INST_ID_` varchar(255) NOT NULL,
  `CASE_DEF_ID_` varchar(255) NOT NULL,
  `ELEMENT_ID_` varchar(255) NOT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_MIL_CASE_DEF` (`CASE_DEF_ID_`),
  KEY `ACT_IDX_MIL_CASE_INST` (`CASE_INST_ID_`),
  CONSTRAINT `ACT_FK_MIL_CASE_DEF` FOREIGN KEY (`CASE_DEF_ID_`) REFERENCES `ACT_CMMN_CASEDEF` (`ID_`),
  CONSTRAINT `ACT_FK_MIL_CASE_INST` FOREIGN KEY (`CASE_INST_ID_`) REFERENCES `ACT_CMMN_RU_CASE_INST` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_RU_MIL_INST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_RU_PLAN_ITEM_INST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_RU_PLAN_ITEM_INST`;
CREATE TABLE `ACT_CMMN_RU_PLAN_ITEM_INST` (
  `ID_` varchar(255) NOT NULL,
  `REV_` int NOT NULL,
  `CASE_DEF_ID_` varchar(255) DEFAULT NULL,
  `CASE_INST_ID_` varchar(255) DEFAULT NULL,
  `STAGE_INST_ID_` varchar(255) DEFAULT NULL,
  `IS_STAGE_` bit(1) DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `START_USER_ID_` varchar(255) DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT '',
  `ITEM_DEFINITION_ID_` varchar(255) DEFAULT NULL,
  `ITEM_DEFINITION_TYPE_` varchar(255) DEFAULT NULL,
  `IS_COMPLETEABLE_` bit(1) DEFAULT NULL,
  `IS_COUNT_ENABLED_` bit(1) DEFAULT NULL,
  `VAR_COUNT_` int DEFAULT NULL,
  `SENTRY_PART_INST_COUNT_` int DEFAULT NULL,
  `LAST_AVAILABLE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_ENABLED_TIME_` datetime(3) DEFAULT NULL,
  `LAST_DISABLED_TIME_` datetime(3) DEFAULT NULL,
  `LAST_STARTED_TIME_` datetime(3) DEFAULT NULL,
  `LAST_SUSPENDED_TIME_` datetime(3) DEFAULT NULL,
  `COMPLETED_TIME_` datetime(3) DEFAULT NULL,
  `OCCURRED_TIME_` datetime(3) DEFAULT NULL,
  `TERMINATED_TIME_` datetime(3) DEFAULT NULL,
  `EXIT_TIME_` datetime(3) DEFAULT NULL,
  `ENDED_TIME_` datetime(3) DEFAULT NULL,
  `ENTRY_CRITERION_ID_` varchar(255) DEFAULT NULL,
  `EXIT_CRITERION_ID_` varchar(255) DEFAULT NULL,
  `EXTRA_VALUE_` varchar(255) DEFAULT NULL,
  `DERIVED_CASE_DEF_ID_` varchar(255) DEFAULT NULL,
  `LAST_UNAVAILABLE_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_PLAN_ITEM_CASE_DEF` (`CASE_DEF_ID_`),
  KEY `ACT_IDX_PLAN_ITEM_CASE_INST` (`CASE_INST_ID_`),
  KEY `ACT_IDX_PLAN_ITEM_STAGE_INST` (`STAGE_INST_ID_`),
  CONSTRAINT `ACT_FK_PLAN_ITEM_CASE_DEF` FOREIGN KEY (`CASE_DEF_ID_`) REFERENCES `ACT_CMMN_CASEDEF` (`ID_`),
  CONSTRAINT `ACT_FK_PLAN_ITEM_CASE_INST` FOREIGN KEY (`CASE_INST_ID_`) REFERENCES `ACT_CMMN_RU_CASE_INST` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_RU_PLAN_ITEM_INST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CMMN_RU_SENTRY_PART_INST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CMMN_RU_SENTRY_PART_INST`;
CREATE TABLE `ACT_CMMN_RU_SENTRY_PART_INST` (
  `ID_` varchar(255) NOT NULL,
  `REV_` int NOT NULL,
  `CASE_DEF_ID_` varchar(255) DEFAULT NULL,
  `CASE_INST_ID_` varchar(255) DEFAULT NULL,
  `PLAN_ITEM_INST_ID_` varchar(255) DEFAULT NULL,
  `ON_PART_ID_` varchar(255) DEFAULT NULL,
  `IF_PART_ID_` varchar(255) DEFAULT NULL,
  `TIME_STAMP_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_SENTRY_CASE_DEF` (`CASE_DEF_ID_`),
  KEY `ACT_IDX_SENTRY_CASE_INST` (`CASE_INST_ID_`),
  KEY `ACT_IDX_SENTRY_PLAN_ITEM` (`PLAN_ITEM_INST_ID_`),
  CONSTRAINT `ACT_FK_SENTRY_CASE_DEF` FOREIGN KEY (`CASE_DEF_ID_`) REFERENCES `ACT_CMMN_CASEDEF` (`ID_`),
  CONSTRAINT `ACT_FK_SENTRY_CASE_INST` FOREIGN KEY (`CASE_INST_ID_`) REFERENCES `ACT_CMMN_RU_CASE_INST` (`ID_`),
  CONSTRAINT `ACT_FK_SENTRY_PLAN_ITEM` FOREIGN KEY (`PLAN_ITEM_INST_ID_`) REFERENCES `ACT_CMMN_RU_PLAN_ITEM_INST` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CMMN_RU_SENTRY_PART_INST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CO_CONTENT_ITEM
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CO_CONTENT_ITEM`;
CREATE TABLE `ACT_CO_CONTENT_ITEM` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) NOT NULL,
  `MIME_TYPE_` varchar(255) DEFAULT NULL,
  `TASK_ID_` varchar(255) DEFAULT NULL,
  `PROC_INST_ID_` varchar(255) DEFAULT NULL,
  `CONTENT_STORE_ID_` varchar(255) DEFAULT NULL,
  `CONTENT_STORE_NAME_` varchar(255) DEFAULT NULL,
  `FIELD_` varchar(400) DEFAULT NULL,
  `CONTENT_AVAILABLE_` bit(1) DEFAULT b'0',
  `CREATED_` timestamp(6) NULL DEFAULT NULL,
  `CREATED_BY_` varchar(255) DEFAULT NULL,
  `LAST_MODIFIED_` timestamp(6) NULL DEFAULT NULL,
  `LAST_MODIFIED_BY_` varchar(255) DEFAULT NULL,
  `CONTENT_SIZE_` bigint DEFAULT '0',
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `SCOPE_ID_` varchar(255) DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `idx_contitem_taskid` (`TASK_ID_`),
  KEY `idx_contitem_procid` (`PROC_INST_ID_`),
  KEY `idx_contitem_scope` (`SCOPE_ID_`,`SCOPE_TYPE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CO_CONTENT_ITEM
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_CO_DATABASECHANGELOG
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CO_DATABASECHANGELOG`;
CREATE TABLE `ACT_CO_DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CO_DATABASECHANGELOG
-- ----------------------------
BEGIN;
INSERT INTO `ACT_CO_DATABASECHANGELOG` VALUES ('1', 'activiti', 'org/flowable/content/db/liquibase/flowable-content-db-changelog.xml', '2022-03-19 23:52:25', 1, 'EXECUTED', '8:7644d7165cfe799200a2abdd3419e8b6', 'createTable tableName=ACT_CO_CONTENT_ITEM; createIndex indexName=idx_contitem_taskid, tableName=ACT_CO_CONTENT_ITEM; createIndex indexName=idx_contitem_procid, tableName=ACT_CO_CONTENT_ITEM', '', NULL, '4.3.5', NULL, NULL, '0683830961');
INSERT INTO `ACT_CO_DATABASECHANGELOG` VALUES ('2', 'flowable', 'org/flowable/content/db/liquibase/flowable-content-db-changelog.xml', '2022-03-19 23:52:25', 2, 'EXECUTED', '8:fe7b11ac7dbbf9c43006b23bbab60bab', 'addColumn tableName=ACT_CO_CONTENT_ITEM; createIndex indexName=idx_contitem_scope, tableName=ACT_CO_CONTENT_ITEM', '', NULL, '4.3.5', NULL, NULL, '0683830961');
COMMIT;

-- ----------------------------
-- Table structure for ACT_CO_DATABASECHANGELOGLOCK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_CO_DATABASECHANGELOGLOCK`;
CREATE TABLE `ACT_CO_DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_CO_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN;
INSERT INTO `ACT_CO_DATABASECHANGELOGLOCK` VALUES (1, b'0', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ACT_DMN_DATABASECHANGELOG
-- ----------------------------
DROP TABLE IF EXISTS `ACT_DMN_DATABASECHANGELOG`;
CREATE TABLE `ACT_DMN_DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_DMN_DATABASECHANGELOG
-- ----------------------------
BEGIN;
INSERT INTO `ACT_DMN_DATABASECHANGELOG` VALUES ('1', 'activiti', 'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', '2022-03-19 23:52:24', 1, 'EXECUTED', '8:c8701f1c71018b55029f450b2e9a10a1', 'createTable tableName=ACT_DMN_DEPLOYMENT; createTable tableName=ACT_DMN_DEPLOYMENT_RESOURCE; createTable tableName=ACT_DMN_DECISION_TABLE', '', NULL, '4.3.5', NULL, NULL, '0683830359');
INSERT INTO `ACT_DMN_DATABASECHANGELOG` VALUES ('2', 'flowable', 'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', '2022-03-19 23:52:24', 2, 'EXECUTED', '8:47f94b27feb7df8a30d4e338c7bd5fb8', 'createTable tableName=ACT_DMN_HI_DECISION_EXECUTION', '', NULL, '4.3.5', NULL, NULL, '0683830359');
INSERT INTO `ACT_DMN_DATABASECHANGELOG` VALUES ('3', 'flowable', 'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', '2022-03-19 23:52:24', 3, 'EXECUTED', '8:ac17eae89fbdccb6e08daf3c7797b579', 'addColumn tableName=ACT_DMN_HI_DECISION_EXECUTION', '', NULL, '4.3.5', NULL, NULL, '0683830359');
INSERT INTO `ACT_DMN_DATABASECHANGELOG` VALUES ('4', 'flowable', 'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', '2022-03-19 23:52:24', 4, 'EXECUTED', '8:f73aabc4529e7292c2942073d1cff6f9', 'dropColumn columnName=PARENT_DEPLOYMENT_ID_, tableName=ACT_DMN_DECISION_TABLE', '', NULL, '4.3.5', NULL, NULL, '0683830359');
INSERT INTO `ACT_DMN_DATABASECHANGELOG` VALUES ('5', 'flowable', 'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', '2022-03-19 23:52:25', 5, 'EXECUTED', '8:3e03528582dd4eeb4eb41f9b9539140d', 'modifyDataType columnName=DEPLOY_TIME_, tableName=ACT_DMN_DEPLOYMENT; modifyDataType columnName=START_TIME_, tableName=ACT_DMN_HI_DECISION_EXECUTION; modifyDataType columnName=END_TIME_, tableName=ACT_DMN_HI_DECISION_EXECUTION', '', NULL, '4.3.5', NULL, NULL, '0683830359');
INSERT INTO `ACT_DMN_DATABASECHANGELOG` VALUES ('6', 'flowable', 'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', '2022-03-19 23:52:25', 6, 'EXECUTED', '8:646c6a061e0b6e8a62e69844ff96abb0', 'createIndex indexName=ACT_IDX_DEC_TBL_UNIQ, tableName=ACT_DMN_DECISION_TABLE', '', NULL, '4.3.5', NULL, NULL, '0683830359');
INSERT INTO `ACT_DMN_DATABASECHANGELOG` VALUES ('7', 'flowable', 'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', '2022-03-19 23:52:25', 7, 'EXECUTED', '8:215a499ff7ae77685b55355245b8b708', 'dropIndex indexName=ACT_IDX_DEC_TBL_UNIQ, tableName=ACT_DMN_DECISION_TABLE; renameTable newTableName=ACT_DMN_DECISION, oldTableName=ACT_DMN_DECISION_TABLE; createIndex indexName=ACT_IDX_DMN_DEC_UNIQ, tableName=ACT_DMN_DECISION', '', NULL, '4.3.5', NULL, NULL, '0683830359');
INSERT INTO `ACT_DMN_DATABASECHANGELOG` VALUES ('8', 'flowable', 'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', '2022-03-19 23:52:25', 8, 'EXECUTED', '8:5355bee389318afed91a11702f2df032', 'addColumn tableName=ACT_DMN_DECISION', '', NULL, '4.3.5', NULL, NULL, '0683830359');
COMMIT;

-- ----------------------------
-- Table structure for ACT_DMN_DATABASECHANGELOGLOCK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_DMN_DATABASECHANGELOGLOCK`;
CREATE TABLE `ACT_DMN_DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_DMN_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN;
INSERT INTO `ACT_DMN_DATABASECHANGELOGLOCK` VALUES (1, b'0', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ACT_DMN_DECISION
-- ----------------------------
DROP TABLE IF EXISTS `ACT_DMN_DECISION`;
CREATE TABLE `ACT_DMN_DECISION` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `VERSION_` int DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_NAME_` varchar(255) DEFAULT NULL,
  `DESCRIPTION_` varchar(255) DEFAULT NULL,
  `DECISION_TYPE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_IDX_DMN_DEC_UNIQ` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_DMN_DECISION
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_DMN_DEPLOYMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_DMN_DEPLOYMENT`;
CREATE TABLE `ACT_DMN_DEPLOYMENT` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOY_TIME_` datetime(3) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `PARENT_DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_DMN_DEPLOYMENT
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_DMN_DEPLOYMENT_RESOURCE
-- ----------------------------
DROP TABLE IF EXISTS `ACT_DMN_DEPLOYMENT_RESOURCE`;
CREATE TABLE `ACT_DMN_DEPLOYMENT_RESOURCE` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_BYTES_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_DMN_DEPLOYMENT_RESOURCE
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_DMN_HI_DECISION_EXECUTION
-- ----------------------------
DROP TABLE IF EXISTS `ACT_DMN_HI_DECISION_EXECUTION`;
CREATE TABLE `ACT_DMN_HI_DECISION_EXECUTION` (
  `ID_` varchar(255) NOT NULL,
  `DECISION_DEFINITION_ID_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `START_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `INSTANCE_ID_` varchar(255) DEFAULT NULL,
  `EXECUTION_ID_` varchar(255) DEFAULT NULL,
  `ACTIVITY_ID_` varchar(255) DEFAULT NULL,
  `FAILED_` bit(1) DEFAULT b'0',
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `EXECUTION_JSON_` longtext,
  `SCOPE_TYPE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_DMN_HI_DECISION_EXECUTION
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_EVT_LOG
-- ----------------------------
DROP TABLE IF EXISTS `ACT_EVT_LOG`;
CREATE TABLE `ACT_EVT_LOG` (
  `LOG_NR_` bigint NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_EVT_LOG
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_FO_DATABASECHANGELOG
-- ----------------------------
DROP TABLE IF EXISTS `ACT_FO_DATABASECHANGELOG`;
CREATE TABLE `ACT_FO_DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_FO_DATABASECHANGELOG
-- ----------------------------
BEGIN;
INSERT INTO `ACT_FO_DATABASECHANGELOG` VALUES ('1', 'activiti', 'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', '2022-03-19 23:52:25', 1, 'EXECUTED', '8:033ebf9380889aed7c453927ecc3250d', 'createTable tableName=ACT_FO_FORM_DEPLOYMENT; createTable tableName=ACT_FO_FORM_RESOURCE; createTable tableName=ACT_FO_FORM_DEFINITION; createTable tableName=ACT_FO_FORM_INSTANCE', '', NULL, '4.3.5', NULL, NULL, '0683830710');
INSERT INTO `ACT_FO_DATABASECHANGELOG` VALUES ('2', 'flowable', 'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', '2022-03-19 23:52:25', 2, 'EXECUTED', '8:986365ceb40445ce3b27a8e6b40f159b', 'addColumn tableName=ACT_FO_FORM_INSTANCE', '', NULL, '4.3.5', NULL, NULL, '0683830710');
INSERT INTO `ACT_FO_DATABASECHANGELOG` VALUES ('3', 'flowable', 'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', '2022-03-19 23:52:25', 3, 'EXECUTED', '8:abf482518ceb09830ef674e52c06bf15', 'dropColumn columnName=PARENT_DEPLOYMENT_ID_, tableName=ACT_FO_FORM_DEFINITION', '', NULL, '4.3.5', NULL, NULL, '0683830710');
INSERT INTO `ACT_FO_DATABASECHANGELOG` VALUES ('4', 'flowable', 'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', '2022-03-19 23:52:25', 4, 'EXECUTED', '8:2087829f22a4b2298dbf530681c74854', 'modifyDataType columnName=DEPLOY_TIME_, tableName=ACT_FO_FORM_DEPLOYMENT; modifyDataType columnName=SUBMITTED_DATE_, tableName=ACT_FO_FORM_INSTANCE', '', NULL, '4.3.5', NULL, NULL, '0683830710');
INSERT INTO `ACT_FO_DATABASECHANGELOG` VALUES ('5', 'flowable', 'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', '2022-03-19 23:52:25', 5, 'EXECUTED', '8:b4be732b89e5ca028bdd520c6ad4d446', 'createIndex indexName=ACT_IDX_FORM_DEF_UNIQ, tableName=ACT_FO_FORM_DEFINITION', '', NULL, '4.3.5', NULL, NULL, '0683830710');
INSERT INTO `ACT_FO_DATABASECHANGELOG` VALUES ('6', 'flowable', 'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', '2022-03-19 23:52:25', 6, 'EXECUTED', '8:384bbd364a649b67c3ca1bcb72fe537f', 'createIndex indexName=ACT_IDX_FORM_TASK, tableName=ACT_FO_FORM_INSTANCE; createIndex indexName=ACT_IDX_FORM_PROC, tableName=ACT_FO_FORM_INSTANCE; createIndex indexName=ACT_IDX_FORM_SCOPE, tableName=ACT_FO_FORM_INSTANCE', '', NULL, '4.3.5', NULL, NULL, '0683830710');
COMMIT;

-- ----------------------------
-- Table structure for ACT_FO_DATABASECHANGELOGLOCK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_FO_DATABASECHANGELOGLOCK`;
CREATE TABLE `ACT_FO_DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_FO_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN;
INSERT INTO `ACT_FO_DATABASECHANGELOGLOCK` VALUES (1, b'0', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ACT_FO_FORM_DEFINITION
-- ----------------------------
DROP TABLE IF EXISTS `ACT_FO_FORM_DEFINITION`;
CREATE TABLE `ACT_FO_FORM_DEFINITION` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `VERSION_` int DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_NAME_` varchar(255) DEFAULT NULL,
  `DESCRIPTION_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_IDX_FORM_DEF_UNIQ` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_FO_FORM_DEFINITION
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_FO_FORM_DEPLOYMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_FO_FORM_DEPLOYMENT`;
CREATE TABLE `ACT_FO_FORM_DEPLOYMENT` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOY_TIME_` datetime(3) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `PARENT_DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_FO_FORM_DEPLOYMENT
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_FO_FORM_INSTANCE
-- ----------------------------
DROP TABLE IF EXISTS `ACT_FO_FORM_INSTANCE`;
CREATE TABLE `ACT_FO_FORM_INSTANCE` (
  `ID_` varchar(255) NOT NULL,
  `FORM_DEFINITION_ID_` varchar(255) NOT NULL,
  `TASK_ID_` varchar(255) DEFAULT NULL,
  `PROC_INST_ID_` varchar(255) DEFAULT NULL,
  `PROC_DEF_ID_` varchar(255) DEFAULT NULL,
  `SUBMITTED_DATE_` datetime(3) DEFAULT NULL,
  `SUBMITTED_BY_` varchar(255) DEFAULT NULL,
  `FORM_VALUES_ID_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `SCOPE_ID_` varchar(255) DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_FORM_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_FORM_PROC` (`PROC_INST_ID_`),
  KEY `ACT_IDX_FORM_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_FO_FORM_INSTANCE
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_FO_FORM_RESOURCE
-- ----------------------------
DROP TABLE IF EXISTS `ACT_FO_FORM_RESOURCE`;
CREATE TABLE `ACT_FO_FORM_RESOURCE` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_BYTES_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of ACT_FO_FORM_RESOURCE
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_GE_BYTEARRAY
-- ----------------------------
DROP TABLE IF EXISTS `ACT_GE_BYTEARRAY`;
CREATE TABLE `ACT_GE_BYTEARRAY` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_RE_DEPLOYMENT` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_GE_BYTEARRAY
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_GE_PROPERTY
-- ----------------------------
DROP TABLE IF EXISTS `ACT_GE_PROPERTY`;
CREATE TABLE `ACT_GE_PROPERTY` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_GE_PROPERTY
-- ----------------------------
BEGIN;
INSERT INTO `ACT_GE_PROPERTY` VALUES ('batch.schema.version', '6.7.2.0', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('cfg.execution-related-entities-count', 'true', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('cfg.task-related-entities-count', 'true', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('common.schema.version', '6.7.2.0', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('entitylink.schema.version', '6.7.2.0', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('eventsubscription.schema.version', '6.7.2.0', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('identitylink.schema.version', '6.7.2.0', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('job.schema.version', '6.7.2.0', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('next.dbid', '1', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('schema.history', 'create(6.7.2.0)', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('schema.version', '6.7.2.0', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('task.schema.version', '6.7.2.0', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('variable.schema.version', '6.7.2.0', 1);
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_ACTINST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_ACTINST`;
CREATE TABLE `ACT_HI_ACTINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT '1',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `TRANSACTION_ORDER_` int DEFAULT NULL,
  `DURATION_` bigint DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_ACTINST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_ATTACHMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_ATTACHMENT`;
CREATE TABLE `ACT_HI_ATTACHMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_ATTACHMENT
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_COMMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_COMMENT`;
CREATE TABLE `ACT_HI_COMMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_COMMENT
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_DETAIL
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_DETAIL`;
CREATE TABLE `ACT_HI_DETAIL` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_DETAIL
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_ENTITYLINK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_ENTITYLINK`;
CREATE TABLE `ACT_HI_ENTITYLINK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `LINK_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REF_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REF_SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REF_SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HIERARCHY_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ENT_LNK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`,`LINK_TYPE_`),
  KEY `ACT_IDX_HI_ENT_LNK_ROOT_SCOPE` (`ROOT_SCOPE_ID_`,`ROOT_SCOPE_TYPE_`,`LINK_TYPE_`),
  KEY `ACT_IDX_HI_ENT_LNK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`,`LINK_TYPE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_ENTITYLINK
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_IDENTITYLINK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_IDENTITYLINK`;
CREATE TABLE `ACT_HI_IDENTITYLINK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_IDENT_LNK_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_IDENT_LNK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_IDENTITYLINK
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_PROCINST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_PROCINST`;
CREATE TABLE `ACT_HI_PROCINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT '1',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_PROCINST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_TASKINST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_TASKINST`;
CREATE TABLE `ACT_HI_TASKINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT '1',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_TASK_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_TASK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_TASKINST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_TSK_LOG
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_TSK_LOG`;
CREATE TABLE `ACT_HI_TSK_LOG` (
  `ID_` bigint NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_TSK_LOG
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_VARINST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_VARINST`;
CREATE TABLE `ACT_HI_VARINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT '1',
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_VAR_SCOPE_ID_TYPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_VAR_SUB_ID_TYPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_EXE` (`EXECUTION_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_HI_VARINST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_ID_BYTEARRAY
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_BYTEARRAY`;
CREATE TABLE `ACT_ID_BYTEARRAY` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_ID_BYTEARRAY
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_ID_GROUP
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_GROUP`;
CREATE TABLE `ACT_ID_GROUP` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_ID_GROUP
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_ID_INFO
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_INFO`;
CREATE TABLE `ACT_ID_INFO` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_ID_INFO
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_ID_MEMBERSHIP
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_MEMBERSHIP`;
CREATE TABLE `ACT_ID_MEMBERSHIP` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `ACT_ID_GROUP` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `ACT_ID_USER` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_ID_MEMBERSHIP
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_ID_PRIV
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_PRIV`;
CREATE TABLE `ACT_ID_PRIV` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PRIV_NAME` (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_ID_PRIV
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_ID_PRIV_MAPPING
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_PRIV_MAPPING`;
CREATE TABLE `ACT_ID_PRIV_MAPPING` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PRIV_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_PRIV_MAPPING` (`PRIV_ID_`),
  KEY `ACT_IDX_PRIV_USER` (`USER_ID_`),
  KEY `ACT_IDX_PRIV_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_PRIV_MAPPING` FOREIGN KEY (`PRIV_ID_`) REFERENCES `ACT_ID_PRIV` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_ID_PRIV_MAPPING
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_ID_PROPERTY
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_PROPERTY`;
CREATE TABLE `ACT_ID_PROPERTY` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_ID_PROPERTY
-- ----------------------------
BEGIN;
INSERT INTO `ACT_ID_PROPERTY` VALUES ('schema.version', '6.7.2.0', 1);
COMMIT;

-- ----------------------------
-- Table structure for ACT_ID_TOKEN
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_TOKEN`;
CREATE TABLE `ACT_ID_TOKEN` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `TOKEN_VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TOKEN_DATE_` timestamp(3) NULL DEFAULT NULL,
  `IP_ADDRESS_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_AGENT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TOKEN_DATA_` varchar(2000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_ID_TOKEN
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_ID_USER
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_USER`;
CREATE TABLE `ACT_ID_USER` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DISPLAY_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_ID_USER
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_PROCDEF_INFO
-- ----------------------------
DROP TABLE IF EXISTS `ACT_PROCDEF_INFO`;
CREATE TABLE `ACT_PROCDEF_INFO` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_PROCDEF_INFO
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RE_DEPLOYMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_DEPLOYMENT`;
CREATE TABLE `ACT_RE_DEPLOYMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DERIVED_FROM_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DERIVED_FROM_ROOT_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_DEPLOYMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ENGINE_VERSION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RE_DEPLOYMENT
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RE_MODEL
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_MODEL`;
CREATE TABLE `ACT_RE_MODEL` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_RE_DEPLOYMENT` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RE_MODEL
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RE_PROCDEF
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_PROCDEF`;
CREATE TABLE `ACT_RE_PROCDEF` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint DEFAULT NULL,
  `SUSPENSION_STATE_` int DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `ENGINE_VERSION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DERIVED_FROM_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DERIVED_FROM_ROOT_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DERIVED_VERSION_` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`DERIVED_VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RE_PROCDEF
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_ACTINST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_ACTINST`;
CREATE TABLE `ACT_RU_ACTINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT '1',
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint DEFAULT NULL,
  `TRANSACTION_ORDER_` int DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_RU_ACTI_START` (`START_TIME_`),
  KEY `ACT_IDX_RU_ACTI_END` (`END_TIME_`),
  KEY `ACT_IDX_RU_ACTI_PROC` (`PROC_INST_ID_`),
  KEY `ACT_IDX_RU_ACTI_PROC_ACT` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_RU_ACTI_EXEC` (`EXECUTION_ID_`),
  KEY `ACT_IDX_RU_ACTI_EXEC_ACT` (`EXECUTION_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_RU_ACTI_TASK` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_ACTINST
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_DEADLETTER_JOB
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_DEADLETTER_JOB`;
CREATE TABLE `ACT_RU_DEADLETTER_JOB` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_DEADLETTER_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
  KEY `ACT_IDX_DEADLETTER_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
  KEY `ACT_IDX_DEADLETTER_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
  KEY `ACT_IDX_DJOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_DJOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_DJOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_FK_DEADLETTER_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_DEADLETTER_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_DEADLETTER_JOB
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_ENTITYLINK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_ENTITYLINK`;
CREATE TABLE `ACT_RU_ENTITYLINK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LINK_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REF_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REF_SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REF_SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HIERARCHY_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_ENT_LNK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`,`LINK_TYPE_`),
  KEY `ACT_IDX_ENT_LNK_ROOT_SCOPE` (`ROOT_SCOPE_ID_`,`ROOT_SCOPE_TYPE_`,`LINK_TYPE_`),
  KEY `ACT_IDX_ENT_LNK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`,`LINK_TYPE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_ENTITYLINK
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_EVENT_SUBSCR
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_EVENT_SUBSCR`;
CREATE TABLE `ACT_RU_EVENT_SUBSCR` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_EVENT_SUBSCR
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_EXECUTION
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_EXECUTION`;
CREATE TABLE `ACT_RU_EXECUTION` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint DEFAULT NULL,
  `IS_CONCURRENT_` tinyint DEFAULT NULL,
  `IS_SCOPE_` tinyint DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint DEFAULT NULL,
  `IS_MI_ROOT_` tinyint DEFAULT NULL,
  `SUSPENSION_STATE_` int DEFAULT NULL,
  `CACHED_ENT_STATE_` int DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint DEFAULT NULL,
  `EVT_SUBSCR_COUNT_` int DEFAULT NULL,
  `TASK_COUNT_` int DEFAULT NULL,
  `JOB_COUNT_` int DEFAULT NULL,
  `TIMER_JOB_COUNT_` int DEFAULT NULL,
  `SUSP_JOB_COUNT_` int DEFAULT NULL,
  `DEADLETTER_JOB_COUNT_` int DEFAULT NULL,
  `EXTERNAL_WORKER_JOB_COUNT_` int DEFAULT NULL,
  `VAR_COUNT_` int DEFAULT NULL,
  `ID_LINK_COUNT_` int DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_IDC_EXEC_ROOT` (`ROOT_PROC_INST_ID_`),
  KEY `ACT_IDX_EXEC_REF_ID_` (`REFERENCE_ID_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`) ON DELETE CASCADE,
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_EXECUTION
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_EXTERNAL_JOB
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_EXTERNAL_JOB`;
CREATE TABLE `ACT_RU_EXTERNAL_JOB` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXTERNAL_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
  KEY `ACT_IDX_EXTERNAL_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
  KEY `ACT_IDX_EXTERNAL_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
  KEY `ACT_IDX_EJOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_EJOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_EJOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  CONSTRAINT `ACT_FK_EXTERNAL_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_EXTERNAL_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_EXTERNAL_JOB
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_HISTORY_JOB
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_HISTORY_JOB`;
CREATE TABLE `ACT_RU_HISTORY_JOB` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ADV_HANDLER_CFG_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_HISTORY_JOB
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_IDENTITYLINK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_IDENTITYLINK`;
CREATE TABLE `ACT_RU_IDENTITYLINK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_IDENT_LNK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_IDENT_LNK_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_IDENT_LNK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `ACT_RU_TASK` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_IDENTITYLINK
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_JOB
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_JOB`;
CREATE TABLE `ACT_RU_JOB` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
  KEY `ACT_IDX_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
  KEY `ACT_IDX_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
  KEY `ACT_IDX_JOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_JOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_JOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_FK_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_JOB
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_SUSPENDED_JOB
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_SUSPENDED_JOB`;
CREATE TABLE `ACT_RU_SUSPENDED_JOB` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_SUSPENDED_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
  KEY `ACT_IDX_SUSPENDED_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
  KEY `ACT_IDX_SUSPENDED_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
  KEY `ACT_IDX_SJOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_SJOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_SJOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_FK_SUSPENDED_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_SUSPENDED_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_SUSPENDED_JOB
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_TASK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_TASK`;
CREATE TABLE `ACT_RU_TASK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint DEFAULT NULL,
  `VAR_COUNT_` int DEFAULT NULL,
  `ID_LINK_COUNT_` int DEFAULT NULL,
  `SUB_TASK_COUNT_` int DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_IDX_TASK_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_TASK_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_TASK_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_TASK
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_TIMER_JOB
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_TIMER_JOB`;
CREATE TABLE `ACT_RU_TIMER_JOB` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TIMER_JOB_EXCEPTION_STACK_ID` (`EXCEPTION_STACK_ID_`),
  KEY `ACT_IDX_TIMER_JOB_CUSTOM_VALUES_ID` (`CUSTOM_VALUES_ID_`),
  KEY `ACT_IDX_TIMER_JOB_CORRELATION_ID` (`CORRELATION_ID_`),
  KEY `ACT_IDX_TIMER_JOB_DUEDATE` (`DUEDATE_`),
  KEY `ACT_IDX_TJOB_SCOPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_TJOB_SUB_SCOPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_TJOB_SCOPE_DEF` (`SCOPE_DEFINITION_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_FK_TIMER_JOB_EXECUTION` (`EXECUTION_ID_`),
  KEY `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` (`PROCESS_INSTANCE_ID_`),
  KEY `ACT_FK_TIMER_JOB_PROC_DEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_TIMER_JOB
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ACT_RU_VARIABLE
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_VARIABLE`;
CREATE TABLE `ACT_RU_VARIABLE` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_RU_VAR_SCOPE_ID_TYPE` (`SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_IDX_RU_VAR_SUB_ID_TYPE` (`SUB_SCOPE_ID_`,`SCOPE_TYPE_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RU_VARIABLE
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for FLW_CHANNEL_DEFINITION
-- ----------------------------
DROP TABLE IF EXISTS `FLW_CHANNEL_DEFINITION`;
CREATE TABLE `FLW_CHANNEL_DEFINITION` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `VERSION_` int DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_NAME_` varchar(255) DEFAULT NULL,
  `DESCRIPTION_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `IMPLEMENTATION_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_IDX_CHANNEL_DEF_UNIQ` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of FLW_CHANNEL_DEFINITION
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for FLW_EVENT_DEFINITION
-- ----------------------------
DROP TABLE IF EXISTS `FLW_EVENT_DEFINITION`;
CREATE TABLE `FLW_EVENT_DEFINITION` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `VERSION_` int DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_NAME_` varchar(255) DEFAULT NULL,
  `DESCRIPTION_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_IDX_EVENT_DEF_UNIQ` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of FLW_EVENT_DEFINITION
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for FLW_EVENT_DEPLOYMENT
-- ----------------------------
DROP TABLE IF EXISTS `FLW_EVENT_DEPLOYMENT`;
CREATE TABLE `FLW_EVENT_DEPLOYMENT` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `DEPLOY_TIME_` datetime(3) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `PARENT_DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of FLW_EVENT_DEPLOYMENT
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for FLW_EVENT_RESOURCE
-- ----------------------------
DROP TABLE IF EXISTS `FLW_EVENT_RESOURCE`;
CREATE TABLE `FLW_EVENT_RESOURCE` (
  `ID_` varchar(255) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) DEFAULT NULL,
  `RESOURCE_BYTES_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of FLW_EVENT_RESOURCE
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for FLW_EV_DATABASECHANGELOG
-- ----------------------------
DROP TABLE IF EXISTS `FLW_EV_DATABASECHANGELOG`;
CREATE TABLE `FLW_EV_DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of FLW_EV_DATABASECHANGELOG
-- ----------------------------
BEGIN;
INSERT INTO `FLW_EV_DATABASECHANGELOG` VALUES ('1', 'flowable', 'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', '2022-03-19 23:52:24', 1, 'EXECUTED', '8:1b0c48c9cf7945be799d868a2626d687', 'createTable tableName=FLW_EVENT_DEPLOYMENT; createTable tableName=FLW_EVENT_RESOURCE; createTable tableName=FLW_EVENT_DEFINITION; createIndex indexName=ACT_IDX_EVENT_DEF_UNIQ, tableName=FLW_EVENT_DEFINITION; createTable tableName=FLW_CHANNEL_DEFIN...', '', NULL, '4.3.5', NULL, NULL, '0683830120');
INSERT INTO `FLW_EV_DATABASECHANGELOG` VALUES ('2', 'flowable', 'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', '2022-03-19 23:52:24', 2, 'EXECUTED', '8:0ea825feb8e470558f0b5754352b9cda', 'addColumn tableName=FLW_CHANNEL_DEFINITION; addColumn tableName=FLW_CHANNEL_DEFINITION', '', NULL, '4.3.5', NULL, NULL, '0683830120');
INSERT INTO `FLW_EV_DATABASECHANGELOG` VALUES ('3', 'flowable', 'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', '2022-03-19 23:52:24', 3, 'EXECUTED', '8:3c2bb293350b5cbe6504331980c9dcee', 'customChange', '', NULL, '4.3.5', NULL, NULL, '0683830120');
COMMIT;

-- ----------------------------
-- Table structure for FLW_EV_DATABASECHANGELOGLOCK
-- ----------------------------
DROP TABLE IF EXISTS `FLW_EV_DATABASECHANGELOGLOCK`;
CREATE TABLE `FLW_EV_DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of FLW_EV_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN;
INSERT INTO `FLW_EV_DATABASECHANGELOGLOCK` VALUES (1, b'0', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for FLW_RU_BATCH
-- ----------------------------
DROP TABLE IF EXISTS `FLW_RU_BATCH`;
CREATE TABLE `FLW_RU_BATCH` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin NOT NULL,
  `SEARCH_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SEARCH_KEY2_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NOT NULL,
  `COMPLETE_TIME_` datetime(3) DEFAULT NULL,
  `STATUS_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `BATCH_DOC_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of FLW_RU_BATCH
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for FLW_RU_BATCH_PART
-- ----------------------------
DROP TABLE IF EXISTS `FLW_RU_BATCH_PART`;
CREATE TABLE `FLW_RU_BATCH_PART` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int DEFAULT NULL,
  `BATCH_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin NOT NULL,
  `SCOPE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCOPE_TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SEARCH_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SEARCH_KEY2_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NOT NULL,
  `COMPLETE_TIME_` datetime(3) DEFAULT NULL,
  `STATUS_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RESULT_DOC_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `FLW_IDX_BATCH_PART` (`BATCH_ID_`),
  CONSTRAINT `FLW_FK_BATCH_PART_PARENT` FOREIGN KEY (`BATCH_ID_`) REFERENCES `FLW_RU_BATCH` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Records of FLW_RU_BATCH_PART
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
