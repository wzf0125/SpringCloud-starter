/*
 Navicat Premium Data Transfer

 Source Server         : 测试服务器(内网穿透)
 Source Server Type    : MySQL
 Source Server Version : 80100
 Source Host           : xxx.xxx.xxx:3306
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 80100
 File Encoding         : 65001

 Date: 22/01/2024 00:29:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `resource_ids` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `scope` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(0) NULL DEFAULT NULL,
  `refresh_token_validity` int(0) NULL DEFAULT NULL,
  `additional_information` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `trusted` tinyint(1) NULL DEFAULT 0,
  `autoapprove` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT 'false',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('quanta', NULL, '$2a$10$c.qV3yJXo5luxn8KqSXQf.a/sTYahmX28/QrWJJzPTivJauRIJaWW', 'all', 'authorization_code,password,refresh_token,client_credentials', 'https://www.baidu.com', NULL, 3600, 86400, NULL, '2024-01-13 16:43:16', 0, 'false');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父级权限id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限路径',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `is_deleted` tinyint(0) NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 0, '用户模块', ':user', '用户模块', 0, '2024-01-03 03:28:52', '2024-01-16 06:29:57');
INSERT INTO `permission` VALUES (2, 1, '权限管理', ':user:permission', '权限管理接口权限', 0, '2024-01-03 03:29:31', '2024-01-03 03:29:31');
INSERT INTO `permission` VALUES (3, 2, '添加权限', ':user:permission:add', '权限管理接口权限', 0, '2024-01-03 03:29:55', '2024-01-03 03:29:55');
INSERT INTO `permission` VALUES (4, 2, '编辑权限', ':user:permission:edit', '权限管理接口权限', 0, '2024-01-03 03:30:03', '2024-01-03 03:30:03');
INSERT INTO `permission` VALUES (5, 2, '获取列表', ':user:permission:get', '权限管理接口权限', 0, '2024-01-03 03:30:16', '2024-01-03 03:30:16');
INSERT INTO `permission` VALUES (6, 2, '删除列表', ':user:permission:delete', '权限管理接口权限', 0, '2024-01-03 03:30:23', '2024-01-03 03:30:23');
INSERT INTO `permission` VALUES (7, 1, '角色管理', ':user:role', '角色管理模块', 0, '2024-01-03 03:31:01', '2024-01-03 03:31:01');
INSERT INTO `permission` VALUES (8, 7, '添加角色', ':user:role:add', '角色管理模块', 0, '2024-01-03 03:31:23', '2024-01-03 03:31:23');
INSERT INTO `permission` VALUES (9, 7, '编辑角色', ':user:role:edit', '角色管理模块', 0, '2024-01-03 03:31:32', '2024-01-03 03:31:32');
INSERT INTO `permission` VALUES (10, 7, '角色列表', ':user:role:list', '角色管理模块', 0, '2024-01-03 03:31:41', '2024-01-03 03:31:41');
INSERT INTO `permission` VALUES (11, 7, '删除角色', ':user:role:delete', '角色管理模块', 0, '2024-01-03 03:31:51', '2024-01-03 03:31:51');
INSERT INTO `permission` VALUES (12, 1, '用户管理', ':user:user', '用户管理接口权限', 0, '2024-01-17 12:44:28', '2024-01-17 12:44:34');
INSERT INTO `permission` VALUES (13, 12, '添加用户', ':user:user:add', '用户管理接口权限', 0, '2024-01-17 12:44:49', '2024-01-17 12:44:53');
INSERT INTO `permission` VALUES (14, 12, '查看用户', ':user:user:get', '用户管理接口权限', 0, '2024-01-17 12:45:05', '2024-01-17 12:45:05');
INSERT INTO `permission` VALUES (15, 12, '编辑用户', ':user:user:edit', '用户管理接口权限', 0, '2024-01-17 12:45:18', '2024-01-17 12:45:18');
INSERT INTO `permission` VALUES (16, 12, '删除用户', ':user:user:delete', '用户管理接口权限', 0, '2024-01-17 12:45:58', '2024-01-17 12:45:58');
INSERT INTO `permission` VALUES (17, 12, '通过账号获取用户', ':user:user:get:byAccount', '用户管理接口权限', 0, '2024-01-17 12:46:42', '2024-01-17 12:46:44');
INSERT INTO `permission` VALUES (18, 12, '通过邮箱获取用户', ':user:user:get:byEmail', '用户管理接口权限', 0, '2024-01-17 12:46:49', '2024-01-17 12:47:20');
INSERT INTO `permission` VALUES (19, 12, '获取用户角色', ':user:user:role:get', '用户管理接口权限', 0, '2024-01-17 12:48:01', '2024-01-17 12:48:20');
INSERT INTO `permission` VALUES (20, 12, '编辑用户角色', ':user:user:role:edit', '用户管理接口权限', 0, '2024-01-17 12:48:19', '2024-01-17 12:48:24');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父节点id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `is_deleted` tinyint(0) NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 0, '管理员', NULL, 0, '2024-01-03 09:49:23', '2024-01-03 09:49:23');
INSERT INTO `role` VALUES (2, 1, '用户管理员', NULL, 0, '2024-01-03 09:49:56', '2024-01-03 09:49:56');
INSERT INTO `role` VALUES (3, 1, '用户管理员(只读)', NULL, 0, '2024-01-03 09:50:16', '2024-01-03 09:50:16');
INSERT INTO `role` VALUES (4, 0, '用户', NULL, 0, '2024-01-03 09:50:32', '2024-01-03 09:50:32');
INSERT INTO `role` VALUES (5, 4, '词典用户', NULL, 0, '2024-01-03 09:52:03', '2024-01-03 09:52:03');
INSERT INTO `role` VALUES (6, 4, '新闻用户', NULL, 0, '2024-01-03 09:52:09', '2024-01-03 09:52:09');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int(0) NULL DEFAULT NULL COMMENT '权限id',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_deleted` tinyint(0) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1, '2024-01-03 14:44:23', '2024-01-03 14:44:23', 0);
INSERT INTO `role_permission` VALUES (2, 1, 2, '2024-01-03 14:44:23', '2024-01-03 14:44:23', 0);
INSERT INTO `role_permission` VALUES (3, 1, 3, '2024-01-03 14:44:23', '2024-01-03 14:44:23', 0);
INSERT INTO `role_permission` VALUES (4, 1, 4, '2024-01-03 14:44:23', '2024-01-03 14:44:23', 0);
INSERT INTO `role_permission` VALUES (5, 1, 5, '2024-01-03 14:44:23', '2024-01-03 14:44:23', 0);
INSERT INTO `role_permission` VALUES (6, 1, 6, '2024-01-03 14:44:23', '2024-01-03 14:44:23', 0);
INSERT INTO `role_permission` VALUES (7, 1, 7, '2024-01-03 14:44:23', '2024-01-03 14:44:23', 0);
INSERT INTO `role_permission` VALUES (8, 1, 8, '2024-01-03 14:44:23', '2024-01-03 14:44:23', 0);
INSERT INTO `role_permission` VALUES (9, 1, 9, '2024-01-03 14:44:24', '2024-01-03 14:44:24', 0);
INSERT INTO `role_permission` VALUES (10, 1, 10, '2024-01-03 14:44:24', '2024-01-03 14:44:24', 0);
INSERT INTO `role_permission` VALUES (11, 1, 11, '2024-01-03 14:44:24', '2024-01-03 14:44:24', 0);
INSERT INTO `role_permission` VALUES (12, 1, 12, '2024-01-17 12:49:02', '2024-01-17 12:49:02', 0);
INSERT INTO `role_permission` VALUES (13, 1, 13, '2024-01-17 12:49:05', '2024-01-17 12:49:05', 0);
INSERT INTO `role_permission` VALUES (14, 1, 14, '2024-01-17 12:49:09', '2024-01-17 12:49:09', 0);
INSERT INTO `role_permission` VALUES (15, 1, 15, '2024-01-17 12:49:11', '2024-01-17 12:49:11', 0);
INSERT INTO `role_permission` VALUES (16, 1, 16, '2024-01-17 12:49:15', '2024-01-17 12:49:15', 0);
INSERT INTO `role_permission` VALUES (17, 1, 17, '2024-01-17 12:49:22', '2024-01-17 12:49:22', 0);
INSERT INTO `role_permission` VALUES (18, 1, 18, '2024-01-17 12:49:28', '2024-01-17 12:49:28', 0);
INSERT INTO `role_permission` VALUES (19, 1, 19, '2024-01-17 12:49:31', '2024-01-17 12:49:31', 0);
INSERT INTO `role_permission` VALUES (20, 1, 20, '2024-01-17 12:49:36', '2024-01-17 12:49:36', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `is_deleted` tinyint(0) NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'wzf', 'wzf', '974500760@qq.com', '$2a$10$NYXLv.khwxWjJ9zFtN2D.eScP41o96aKcOMamo/Ysi1SnXspFh4h2', 0, '2024-01-09 10:37:07', '2024-01-09 10:37:07');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(0) NOT NULL COMMENT '用户id',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `is_deleted` tinyint(0) NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 1, '', '', 0, '2024-01-09 10:37:07', '2024-01-09 10:37:07');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色id',
  `is_deleted` tinyint(0) NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, 0, '2024-01-05 10:48:59', '2024-01-05 10:48:59');
INSERT INTO `user_role` VALUES (2, 1, 2, 0, '2024-01-05 10:48:59', '2024-01-05 10:48:59');
INSERT INTO `user_role` VALUES (3, 1, 3, 0, '2024-01-05 10:48:59', '2024-01-05 10:48:59');

SET FOREIGN_KEY_CHECKS = 1;
