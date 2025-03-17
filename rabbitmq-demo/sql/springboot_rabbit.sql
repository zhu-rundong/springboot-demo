/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : springboot_rabbit

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 16/03/2025 21:04:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_deliveryman
-- ----------------------------
DROP TABLE IF EXISTS `t_deliveryman`;
CREATE TABLE `t_deliveryman`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '骑手id',
  `name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态，1：有效，0：无效',
  `date` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '骑手' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `account_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `deliveryman_id` bigint(20) NULL DEFAULT NULL COMMENT '骑手id',
  `settlement_id` bigint(20) NULL DEFAULT NULL COMMENT '结算id',
  `reward_id` bigint(20) NULL DEFAULT NULL COMMENT '积分奖励id',
  `order_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态，1：订单创建中，2：餐厅确认，3：骑手确认，4：订单创建成功，5：订单创建失败',
  `order_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单地址',
  `order_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单价格',
  `date` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `restaurant_id` bigint(20) NULL DEFAULT NULL COMMENT '地址',
  `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `product_price` decimal(9, 2) NULL DEFAULT NULL COMMENT '产品单价',
  `product_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品状态，1：有效，0：无效',
  `date` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_restaurant
-- ----------------------------
DROP TABLE IF EXISTS `t_restaurant`;
CREATE TABLE `t_restaurant`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '餐厅id',
  `settlement_id` bigint(20) NULL DEFAULT NULL COMMENT '结算id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '餐厅名称',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '餐厅地址',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '餐厅状态，1：有效，0：无效',
  `date` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '餐厅' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reward
-- ----------------------------
DROP TABLE IF EXISTS `t_reward`;
CREATE TABLE `t_reward`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '奖励id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `amount` decimal(9, 2) NULL DEFAULT NULL COMMENT '积分量',
  `status` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `date` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_settlement
-- ----------------------------
DROP TABLE IF EXISTS `t_settlement`;
CREATE TABLE `t_settlement`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '结算id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `transaction_id` bigint(20) NULL DEFAULT NULL COMMENT '交易id',
  `amount` decimal(9, 2) NULL DEFAULT NULL COMMENT '金额',
  `status` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `date` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1168 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
