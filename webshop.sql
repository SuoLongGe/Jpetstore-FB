/*
 Navicat Premium Data Transfer

 Source Server         : sql5.7test
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44)
 Source Host           : localhost:3306
 Source Schema         : webshop

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44)
 File Encoding         : 65001

 Date: 24/04/2025 20:25:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '大类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '美妆');
INSERT INTO `category` VALUES (2, '电脑');
INSERT INTO `category` VALUES (3, 'Test');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `categoryid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '自然堂水润保湿柔肤乳补水保湿乳液舒缓滋养女男学生护肤品正品', 10.00, '001.png', 1);
INSERT INTO `goods` VALUES (2, '4090笔记本', 9999.00, '002.png', 2);
INSERT INTO `goods` VALUES (3, '致态TiPlus5000 7100 Ti600', 225.00, '003.png', 2);
INSERT INTO `goods` VALUES (4, '华硕TX GAMING B760M WIFI D4天选电脑主板cpu套装华硕官方旗舰店', 1089.00, '004.png', 2);
INSERT INTO `goods` VALUES (5, '卡诺基 RX580 RX590 8G 2048SP 单HDMI接口 高端显卡 吃鸡显卡', 299.00, '005.png', 2);
INSERT INTO `goods` VALUES (6, '全新额定300W 400W 500W 600W 700W PC电脑主机箱台式机显卡电源', 43.75, '006.png', 2);
INSERT INTO `goods` VALUES (7, 'Test1', 1.00, '001.png', 3);
INSERT INTO `goods` VALUES (8, 'Test2', 1.00, '001.png', 3);
INSERT INTO `goods` VALUES (9, 'Test3', 1.00, '001.png', 3);
INSERT INTO `goods` VALUES (10, 'Test4', 1.00, '001.png', 3);
INSERT INTO `goods` VALUES (11, 'Test5', 1.00, '001.png', 3);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `good_id` int(11) NULL DEFAULT NULL,
  `quantity` int(11) NULL DEFAULT NULL,
  `price` decimal(10, 2) NOT NULL,
  `is_paid` tinyint(1) NULL DEFAULT 0,
  `order_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '订单ID,可重复,1对多个商品ID,userid+time',
  `product_id` int(11) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `recipient` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `good_id`(`good_id`) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`good_id`) REFERENCES `goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 1, 2, 2, 2.00, 0, '1#2024-10-30 02:05:10', 4, '长沙', NULL);
INSERT INTO `orders` VALUES (4, 1, 2, 2, 2.00, 1, '1#2024-10-30 02:17:33', 4, '长沙', NULL);
INSERT INTO `orders` VALUES (6, 1, 1, 7, 43.00, 1, '1#2024-10-30 10:01:30', 1, '长沙', NULL);
INSERT INTO `orders` VALUES (15, 1, 2, 3, 19999.00, 1, '1#2024-11-02 01:38:10', 4, '长沙', NULL);
INSERT INTO `orders` VALUES (16, 3, 1, 1, 43.00, 1, '3#2024-11-02 01:40:57', 1, '长沙', NULL);
INSERT INTO `orders` VALUES (17, 3, 2, 1, 19999.00, 1, '3#2024-11-02 10:06:05', 4, '长沙', NULL);
INSERT INTO `orders` VALUES (18, 3, 2, 3, 19999.00, 1, '3#2024-11-02 10:40:58', 4, '长沙', NULL);
INSERT INTO `orders` VALUES (19, 3, 2, 1, 9999.00, 1, '3#2024-11-02 14:05:35', 5, '长沙', NULL);
INSERT INTO `orders` VALUES (20, 3, 2, 1, 19999.00, 0, '3#2024-11-02 14:38:01', 6, '长沙', NULL);
INSERT INTO `orders` VALUES (21, 3, 2, 1, 13800.00, 1, '3#2024-11-02 14:39:24', 5, '长沙', NULL);
INSERT INTO `orders` VALUES (22, 3, 1, 3, 10.00, 1, '3#2024-11-02 14:51:59', 1, '长沙', NULL);
INSERT INTO `orders` VALUES (23, 3, 2, 1, 19999.00, 1, '3#2024-11-02 14:51:59', 6, '长沙', NULL);
INSERT INTO `orders` VALUES (25, 3, 2, 1, 13800.00, 1, '3#2024-11-02 17:02:51', 5, '长沙', NULL);
INSERT INTO `orders` VALUES (26, 3, 2, 3, 13800.00, 1, '3#2024-11-02 17:44:33', 5, '长沙', NULL);
INSERT INTO `orders` VALUES (27, 3, 2, 2, 9999.00, 1, '3#2024-11-02 19:21:11', 4, '长沙', NULL);
INSERT INTO `orders` VALUES (28, 3, 2, 1, 13800.00, 1, '3#2024-11-02 19:36:29', 5, '长沙', NULL);
INSERT INTO `orders` VALUES (29, 3, 2, 1, 19999.00, 1, '3#2024-11-02 19:38:59', 6, '长沙', NULL);
INSERT INTO `orders` VALUES (30, 3, 2, 3, 13800.00, 0, '3#2024-11-02 20:34:30', 5, '长沙', NULL);
INSERT INTO `orders` VALUES (31, 3, 1, 3, 10.00, 0, '3#2024-11-02 20:34:30', 1, '长沙', NULL);
INSERT INTO `orders` VALUES (33, 3, 2, 2, 13800.00, 1, '3#2024-11-02 20:43:30', 5, '长沙', NULL);
INSERT INTO `orders` VALUES (34, 3, 1, 3, 30.00, 1, '3#2024-11-02 20:43:30', 3, '长沙', NULL);
INSERT INTO `orders` VALUES (36, 3, 2, 2, 19999.00, 1, '3#2024-11-02 20:49:10', 6, '长沙', NULL);
INSERT INTO `orders` VALUES (37, 3, 1, 3, 20.00, 1, '3#2024-11-02 20:49:10', 2, '长沙', NULL);
INSERT INTO `orders` VALUES (39, 3, 2, 1, 19999.00, 0, '3#2024-11-02 21:31:56', 6, '深圳', NULL);
INSERT INTO `orders` VALUES (40, 3, 2, 1, 19999.00, 1, '3#2024-11-02 21:46:36', 6, '深圳', NULL);
INSERT INTO `orders` VALUES (41, 3, 1, 3, 30.00, 1, '3#2024-11-02 21:46:36', 3, '深圳', NULL);
INSERT INTO `orders` VALUES (42, 3, 2, 1, 9999.00, 1, '3#2024-11-04 23:49:00', 4, '长沙', NULL);
INSERT INTO `orders` VALUES (43, 5, 1, 3, 20.00, 1, '5#2024-11-06 10:26:02', 2, 'CSU', NULL);
INSERT INTO `orders` VALUES (44, 5, 1, 5, 30.00, 1, '5#2024-11-06 10:26:02', 3, 'CSU', NULL);
INSERT INTO `orders` VALUES (46, 6, 2, 3, 13800.00, 1, '6#2024-11-06 10:31:12', 5, '长沙', NULL);
INSERT INTO `orders` VALUES (47, 7, 1, 10, 30.00, 1, '7#2024-11-11 22:05:32', 3, 'CSU', NULL);
INSERT INTO `orders` VALUES (48, 7, 4, 1, 1089.00, 1, '7#2024-11-11 22:05:32', 11, 'CSU', NULL);
INSERT INTO `orders` VALUES (50, 7, 6, 1, 43.75, 0, '7#2024-11-11 22:05:58', 13, '', NULL);
INSERT INTO `orders` VALUES (51, 15, 1, 2, 20.00, 1, '15#2024-12-20 12:52:53', 2, 'cs', NULL);
INSERT INTO `orders` VALUES (52, 15, 1, 15, 20.00, 1, '15#2024-12-20 13:33:54', 2, 'cs', NULL);
INSERT INTO `orders` VALUES (53, 15, 2, 4, 13800.00, 1, '15#2024-12-20 13:33:54', 5, 'cs', NULL);
INSERT INTO `orders` VALUES (55, 15, 6, 2, 43.75, 1, '15#2024-12-20 15:57:54', 13, 'cs4', NULL);
INSERT INTO `orders` VALUES (56, 15, 2, 1, 13800.00, 0, '15#2024-12-20 16:06:19', 5, 'cs', NULL);
INSERT INTO `orders` VALUES (57, 15, 3, 1, 735.00, 0, '15#2024-12-20 16:14:08', 9, 'cs', NULL);
INSERT INTO `orders` VALUES (58, 15, 2, 2, 13800.00, 0, '15#2024-12-20 16:19:32', 5, 'cs', NULL);
INSERT INTO `orders` VALUES (59, 15, 2, 1, 13800.00, 0, '15#2024-12-20 16:22:18', 5, 'cs', NULL);
INSERT INTO `orders` VALUES (60, 15, 1, 3, 20.00, 0, '15#2024-12-20 16:30:42', 2, 'cs', NULL);
INSERT INTO `orders` VALUES (61, 15, 1, 2, 20.00, 0, '15#2024-12-20 16:33:43', 2, 'cs', NULL);
INSERT INTO `orders` VALUES (62, 15, 3, 3, 345.00, 0, '15#2024-12-20 16:33:43', 8, 'cs', NULL);
INSERT INTO `orders` VALUES (64, 15, 5, 4, 299.00, 0, '15#2024-12-20 16:38:16', 12, 'cs', NULL);
INSERT INTO `orders` VALUES (65, 15, 2, 2, 13800.00, 1, '15#2024-12-20 16:40:41', 5, 'cs', NULL);
INSERT INTO `orders` VALUES (66, 15, 6, 2, 43.75, 0, '15#2024-12-20 16:40:56', 13, 'cs', NULL);
INSERT INTO `orders` VALUES (67, 15, 4, 1, 1089.00, 0, '15#2024-12-20 16:40:56', 11, 'cs', NULL);
INSERT INTO `orders` VALUES (69, 15, 4, 1, 1089.00, 1, '15#2024-12-20 16:46:08', 11, 'cs', NULL);
INSERT INTO `orders` VALUES (70, 15, 6, 1, 43.75, 1, '15#2024-12-20 16:46:08', 13, 'cs', NULL);
INSERT INTO `orders` VALUES (72, 15, 2, 1, 9999.00, 1, '15#2024-12-20 16:52:02', 4, 'cs', NULL);
INSERT INTO `orders` VALUES (73, 15, 2, 1, 13800.00, 1, '15#2024-12-20 17:02:32', 5, 'cs', NULL);
INSERT INTO `orders` VALUES (74, 15, 4, 1, 1089.00, 1, '15#2024-12-20 17:03:37', 11, 'cs', NULL);
INSERT INTO `orders` VALUES (75, 15, 1, 2, 20.00, 0, '15#2024-12-20 17:36:32', 2, 'cccc', 'name');
INSERT INTO `orders` VALUES (76, 15, 1, 1, 30.00, 1, '15#2024-12-20 17:38:32', 3, 'csuuu', 'name');
INSERT INTO `orders` VALUES (77, 15, 4, 1, 1089.00, 1, '15#2024-12-20 17:38:32', 11, 'csuuu', 'name');
INSERT INTO `orders` VALUES (78, 15, 2, 1, 19999.00, 1, '15#2024-12-20 17:38:32', 6, 'csuuu', 'name');
INSERT INTO `orders` VALUES (79, 15, 2, 1, 19999.00, 1, '15#2024-12-20 17:41:49', 6, 'cssss', 'namezxczc');
INSERT INTO `orders` VALUES (80, 15, 2, 1, 9999.00, 0, '15#2024-12-20 18:01:40', 4, 'cs', 'name');
INSERT INTO `orders` VALUES (81, 15, 4, 4, 1089.00, 1, '15#2024-12-20 22:19:00', 11, 'csu', 'st');
INSERT INTO `orders` VALUES (82, 15, 4, 3, 1089.00, 1, '15#2024-12-20 22:42:32', 11, 'cs长沙', 'hello你好');
INSERT INTO `orders` VALUES (83, 15, 1, 7, 20.00, 1, '15#2024-12-20 22:42:32', 2, 'cs长沙', 'hello你好');
INSERT INTO `orders` VALUES (84, 71, 3, 4, 225.00, 1, '71#2024-12-25 10:10:14', 7, 'csu', 'songtie');
INSERT INTO `orders` VALUES (85, 72, 3, 5, 345.00, 1, '72#2024-12-25 10:25:56', 8, 'csu', 'songtie');
INSERT INTO `orders` VALUES (86, 15, 2, 2, 19999.00, 1, '15#2025-01-02 19:20:58', 6, 'csu', 'songtie');
INSERT INTO `orders` VALUES (87, 15, 1, 3, 30.00, 1, '15#2025-01-02 19:20:58', 3, 'csu', 'songtie');
INSERT INTO `orders` VALUES (88, 15, 4, 1, 1089.00, 1, '15#2025-01-02 19:20:58', 11, 'csu', 'songtie');
INSERT INTO `orders` VALUES (89, 15, 4, 4, 1089.00, 0, '15#2025-01-03 13:14:53', 11, 'cs', 'jjjjj');
INSERT INTO `orders` VALUES (90, 15, 3, 4, 869.00, 0, '15#2025-01-03 13:14:53', 10, 'cs', 'jjjjj');
INSERT INTO `orders` VALUES (91, 3, 1, 1, 20.00, 1, '3#2025-04-06 17:57:01', 2, '长沙', 'LWJ');
INSERT INTO `orders` VALUES (92, 3, 2, 1, 13800.00, 1, '3#2025-04-06 17:57:01', 5, '长沙', 'LWJ');
INSERT INTO `orders` VALUES (93, 1, 1, 10, 100.00, 0, '1#2025-04-13 19:47:13', 1, 'csu', 'jie');
INSERT INTO `orders` VALUES (95, 1, 1, 10, 13800.00, 0, '1#2025-04-13 20:45:01', 5, '长沙', 'lwj');
INSERT INTO `orders` VALUES (96, 1, 2, 10, 13800.00, 1, '1#2025-04-13 20:48:29', 5, '长沙', 'lwj');
INSERT INTO `orders` VALUES (97, 1, 3, 10, 225.00, 1, '1#2025-04-13 20:48:29', 7, '长沙', 'lwj');
INSERT INTO `orders` VALUES (98, 1, 3, 10, 225.00, 0, '1#2025-04-13 20:49:58', 7, '长沙', 'lwj');
INSERT INTO `orders` VALUES (99, 1, 1, 7, 10.00, 1, '1#2025-04-18 23:31:02', 1, 'poiuqeqwe', 'admin');
INSERT INTO `orders` VALUES (100, 1, 2, 5, 19999.00, 1, '1#2025-04-19 02:44:12', 6, 'poiuqeqwe', 'admin66666');
INSERT INTO `orders` VALUES (101, 1, 2, 7, 9999.00, 1, '1#2025-04-21 20:08:06', 4, 'poiuqeqwe', 'admin66666');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NULL DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `product_price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 1, '低级保湿乳液', 10.00);
INSERT INTO `product` VALUES (2, 1, '中级保湿乳液', 20.00);
INSERT INTO `product` VALUES (3, 1, '高级保湿乳液', 30.00);
INSERT INTO `product` VALUES (4, 2, '2060笔记本', 9999.00);
INSERT INTO `product` VALUES (5, 2, '4060笔记本', 13800.00);
INSERT INTO `product` VALUES (6, 2, '4090笔记本', 19999.00);
INSERT INTO `product` VALUES (7, 3, '[QLC颗粒]Ti600 500G 4.0 赠散热片', 225.00);
INSERT INTO `product` VALUES (8, 3, '[QLC颗粒]Ti600 1TB 4.0 赠散热片', 345.00);
INSERT INTO `product` VALUES (9, 3, '[QLC颗粒]Ti600 2TB 4.0 赠散热片', 735.00);
INSERT INTO `product` VALUES (10, 3, '【顺丰包邮】Tiplus7100 2T 4.0 赠散热片', 869.00);
INSERT INTO `product` VALUES (11, 4, '【TX GAMING B760M WIFI D4】', 1089.00);
INSERT INTO `product` VALUES (12, 5, '卡诺基 RX580 RX590 8G 2048SP 单HDMI接口 高端显卡 吃鸡显卡', 299.00);
INSERT INTO `product` VALUES (13, 6, '雷神之源ATX-500W额定300W', 43.75);
INSERT INTO `product` VALUES (14, 7, 'Test1', 1.00);
INSERT INTO `product` VALUES (15, 8, 'Test2', 1.00);
INSERT INTO `product` VALUES (16, 9, 'Test3', 1.00);
INSERT INTO `product` VALUES (17, 10, 'Test4', 1.00);
INSERT INTO `product` VALUES (18, 11, 'Test5', 1.00);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `good_id` int(11) NULL DEFAULT NULL,
  `quantity` int(11) NULL DEFAULT NULL,
  `is_in_order` int(2) NOT NULL COMMENT '是否进入了购物车,0为没有进入购物车,1为删除订单',
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `good_id`(`good_id`) USING BTREE,
  CONSTRAINT `shopping_cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `shopping_cart_ibfk_2` FOREIGN KEY (`good_id`) REFERENCES `goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES (1, 1, 2, 7, 1, 4);
INSERT INTO `shopping_cart` VALUES (14, 1, 1, 7, 1, 1);
INSERT INTO `shopping_cart` VALUES (16, 1, 2, 3, 1, 4);
INSERT INTO `shopping_cart` VALUES (17, 3, 2, 3, 1, 4);
INSERT INTO `shopping_cart` VALUES (18, 3, 1, 1, 1, 1);
INSERT INTO `shopping_cart` VALUES (19, 3, 2, 1, 1, 4);
INSERT INTO `shopping_cart` VALUES (20, 3, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (25, 3, 2, 1, 1, 6);
INSERT INTO `shopping_cart` VALUES (26, 3, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (27, 3, 1, 3, 1, 1);
INSERT INTO `shopping_cart` VALUES (28, 3, 2, 1, 1, 6);
INSERT INTO `shopping_cart` VALUES (29, 3, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (30, 3, 2, 2, 2, 4);
INSERT INTO `shopping_cart` VALUES (31, 3, 2, 1, 2, 6);
INSERT INTO `shopping_cart` VALUES (32, 3, 2, 3, 1, 5);
INSERT INTO `shopping_cart` VALUES (33, 3, 2, 2, 1, 4);
INSERT INTO `shopping_cart` VALUES (34, 3, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (35, 3, 2, 1, 1, 6);
INSERT INTO `shopping_cart` VALUES (36, 3, 2, 3, 1, 5);
INSERT INTO `shopping_cart` VALUES (37, 3, 2, 1, 2, 4);
INSERT INTO `shopping_cart` VALUES (38, 3, 2, 1, 1, 6);
INSERT INTO `shopping_cart` VALUES (39, 3, 1, 3, 1, 1);
INSERT INTO `shopping_cart` VALUES (40, 3, 2, 2, 1, 5);
INSERT INTO `shopping_cart` VALUES (41, 3, 1, 3, 1, 3);
INSERT INTO `shopping_cart` VALUES (42, 3, 2, 2, 1, 6);
INSERT INTO `shopping_cart` VALUES (43, 3, 1, 3, 1, 2);
INSERT INTO `shopping_cart` VALUES (44, 3, 2, 3, 1, 6);
INSERT INTO `shopping_cart` VALUES (45, 3, 1, 3, 1, 2);
INSERT INTO `shopping_cart` VALUES (46, 3, 1, 1, 1, 1);
INSERT INTO `shopping_cart` VALUES (47, 3, 1, 1, 1, 2);
INSERT INTO `shopping_cart` VALUES (48, 3, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (49, 3, 2, 1, 1, 4);
INSERT INTO `shopping_cart` VALUES (50, 3, 2, 1, 1, 6);
INSERT INTO `shopping_cart` VALUES (51, 3, 1, 3, 1, 3);
INSERT INTO `shopping_cart` VALUES (52, 3, 1, 1, 1, 2);
INSERT INTO `shopping_cart` VALUES (53, 3, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (54, 3, 2, 1, 1, 4);
INSERT INTO `shopping_cart` VALUES (55, 3, 2, 1, 2, 6);
INSERT INTO `shopping_cart` VALUES (56, 3, 2, 2, 2, 5);
INSERT INTO `shopping_cart` VALUES (57, 5, 1, 3, 1, 2);
INSERT INTO `shopping_cart` VALUES (58, 5, 1, 5, 1, 3);
INSERT INTO `shopping_cart` VALUES (59, 5, 5, 1, 2, 12);
INSERT INTO `shopping_cart` VALUES (60, 6, 2, 3, 1, 5);
INSERT INTO `shopping_cart` VALUES (61, 6, 4, 1, 2, 11);
INSERT INTO `shopping_cart` VALUES (62, 7, 1, 10, 1, 3);
INSERT INTO `shopping_cart` VALUES (63, 7, 6, 1, 1, 13);
INSERT INTO `shopping_cart` VALUES (64, 7, 5, 1, 2, 12);
INSERT INTO `shopping_cart` VALUES (65, 7, 4, 1, 1, 11);
INSERT INTO `shopping_cart` VALUES (66, 7, 3, 1, 0, 7);
INSERT INTO `shopping_cart` VALUES (67, 15, 1, 2, 1, 2);
INSERT INTO `shopping_cart` VALUES (68, 15, 1, 15, 1, 2);
INSERT INTO `shopping_cart` VALUES (69, 15, 2, 4, 1, 5);
INSERT INTO `shopping_cart` VALUES (70, 15, 6, 2, 1, 13);
INSERT INTO `shopping_cart` VALUES (71, 15, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (72, 15, 3, 1, 1, 9);
INSERT INTO `shopping_cart` VALUES (73, 15, 2, 2, 1, 5);
INSERT INTO `shopping_cart` VALUES (74, 15, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (75, 15, 1, 3, 1, 2);
INSERT INTO `shopping_cart` VALUES (76, 15, 1, 2, 1, 2);
INSERT INTO `shopping_cart` VALUES (77, 15, 3, 3, 1, 8);
INSERT INTO `shopping_cart` VALUES (78, 15, 2, 3, 2, 6);
INSERT INTO `shopping_cart` VALUES (79, 15, 5, 4, 1, 12);
INSERT INTO `shopping_cart` VALUES (80, 15, 6, 2, 1, 13);
INSERT INTO `shopping_cart` VALUES (81, 15, 2, 2, 1, 5);
INSERT INTO `shopping_cart` VALUES (82, 15, 4, 1, 1, 11);
INSERT INTO `shopping_cart` VALUES (83, 15, 4, 1, 1, 11);
INSERT INTO `shopping_cart` VALUES (84, 15, 6, 1, 1, 13);
INSERT INTO `shopping_cart` VALUES (85, 15, 2, 1, 1, 4);
INSERT INTO `shopping_cart` VALUES (86, 15, 4, 1, 1, 11);
INSERT INTO `shopping_cart` VALUES (87, 15, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (88, 15, 3, 1, 1, 7);
INSERT INTO `shopping_cart` VALUES (89, 15, 2, 2, 1, 5);
INSERT INTO `shopping_cart` VALUES (90, 15, 1, 3, 1, 3);
INSERT INTO `shopping_cart` VALUES (91, 15, 2, 1, 1, 5);
INSERT INTO `shopping_cart` VALUES (92, 15, 2, 2, 1, 5);
INSERT INTO `shopping_cart` VALUES (93, 15, 1, 1, 1, 3);
INSERT INTO `shopping_cart` VALUES (94, 15, 4, 1, 1, 11);
INSERT INTO `shopping_cart` VALUES (95, 15, 2, 1, 1, 6);
INSERT INTO `shopping_cart` VALUES (96, 15, 2, 1, 1, 6);
INSERT INTO `shopping_cart` VALUES (97, 15, 2, 1, 1, 4);
INSERT INTO `shopping_cart` VALUES (102, 15, 4, 4, 1, 11);
INSERT INTO `shopping_cart` VALUES (103, 15, 2, 2, 1, 6);
INSERT INTO `shopping_cart` VALUES (104, 15, 4, 3, 1, 11);
INSERT INTO `shopping_cart` VALUES (105, 15, 1, 7, 1, 2);
INSERT INTO `shopping_cart` VALUES (106, 15, 1, 3, 1, 3);
INSERT INTO `shopping_cart` VALUES (110, 71, 3, 4, 1, 7);
INSERT INTO `shopping_cart` VALUES (113, 72, 3, 5, 1, 8);
INSERT INTO `shopping_cart` VALUES (114, 15, 4, 1, 1, 11);
INSERT INTO `shopping_cart` VALUES (115, 15, 4, 4, 1, 11);
INSERT INTO `shopping_cart` VALUES (116, 15, 3, 4, 1, 10);
INSERT INTO `shopping_cart` VALUES (117, 15, 5, 6, 0, 12);
INSERT INTO `shopping_cart` VALUES (118, 15, 6, 8, 0, 13);
INSERT INTO `shopping_cart` VALUES (119, 3, 1, 3, 0, 1);
INSERT INTO `shopping_cart` VALUES (120, 1, 2, 9, 0, 5);
INSERT INTO `shopping_cart` VALUES (121, 1, 3, 9, 0, 7);
INSERT INTO `shopping_cart` VALUES (122, 1, 2, 1, 0, 5);
INSERT INTO `shopping_cart` VALUES (123, 1, 3, 3, 0, 8);
INSERT INTO `shopping_cart` VALUES (124, 1, 4, 3, 0, 11);
INSERT INTO `shopping_cart` VALUES (125, 1, 2, 5, 1, 6);
INSERT INTO `shopping_cart` VALUES (126, 1, 3, 3, 0, 8);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `giteeid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `username`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '$2a$10$rsQSj.X.alWXqu6MBHVlWuKZXApetwsgsa.JsCN8ao5hdPm2y1yja', 'admin7777', 'admin@gmail.com', 'poiuqeqwe', 14205635);
INSERT INTO `users` VALUES (3, 'jiejie', '114514', 'LWJ', '1737607255@qq.com', '长沙', NULL);
INSERT INTO `users` VALUES (4, 'jie', '123456', 'lll', '1737607255@qq.com', NULL, NULL);
INSERT INTO `users` VALUES (5, 'czq', '123456', 'czq', '123456@qq.com', 'CSU', NULL);
INSERT INTO `users` VALUES (6, 'st', '123456', 'st', '123456@qq.com', '长沙', NULL);
INSERT INTO `users` VALUES (7, 'songtie', '123456', 'songtie', '123456@qq.com', '123', NULL);
INSERT INTO `users` VALUES (8, 'lwj', '123456', 'lwj', '13@qq.com', NULL, NULL);
INSERT INTO `users` VALUES (9, '66', '66', '66', '', NULL, NULL);
INSERT INTO `users` VALUES (11, 'llwwjj', '123456', 'name', '134@qq.com', 'cs', NULL);
INSERT INTO `users` VALUES (12, '520', '1314', '5467', '1736@qq.com', 'cs', NULL);
INSERT INTO `users` VALUES (15, 'lwjj', '123456', 'jjjjj', '134@qq.com', 'cs', NULL);
INSERT INTO `users` VALUES (16, 'asd', '123456', 'name', '1736@qq.com', 'cs', NULL);
INSERT INTO `users` VALUES (17, 'saxc', 'asdxzcz', 'asdqf', 'caqqf@qq.com', 'a46v1', NULL);
INSERT INTO `users` VALUES (18, 'asdxzc', 'asdas', 'xzc', 'asd@qq.com', 'xzczc', NULL);
INSERT INTO `users` VALUES (21, 'asdasdzc', 'zxczxcas', 'caca', '134@qq.com', 'asdxzc', NULL);
INSERT INTO `users` VALUES (35, 'lwjzxc', 'zxcca', 'zxczvz', 'qwee@qq.com', 'zxcxc', NULL);
INSERT INTO `users` VALUES (53, 'lwjx', '', '', '', '', NULL);
INSERT INTO `users` VALUES (65, 'lwjjs', '123456', 'name', '134@qq.com', 'cs', NULL);
INSERT INTO `users` VALUES (66, 'lwjjxzcz', '123456', 'name', '134@qq.com', 'cs', NULL);
INSERT INTO `users` VALUES (67, 'asdas', 'asdxzxc', 'asdasd', 'adasd@qq.com', 'asdasd', NULL);
INSERT INTO `users` VALUES (68, 'lwjjx', '123456', 'name', '134@qq.com', 'cs', NULL);
INSERT INTO `users` VALUES (69, 'lwjjsz', '12345622', 'namew', '134@qq.com', 'csus', NULL);
INSERT INTO `users` VALUES (70, 'lwjjsx', '123456', 's', '134@qq.com', 'cs', NULL);
INSERT INTO `users` VALUES (71, 'lwjjszx', '123456', 'namex', 'asd@qq.com', 'cs', NULL);
INSERT INTO `users` VALUES (72, 'congtie', '$2a$10$xMYN1PHGjwsPqMi28Lws8.tKKSqP5fT.7cXU30GQIAdZEsaXsekWC', 'congtie', '134@qq.com', 'csu', NULL);
INSERT INTO `users` VALUES (74, 'llwwjjccc', '$2a$10$1Q1KtWXh4vgq4G3OgYNXCOdSWrD9.K4Y4Zt53wsAhjoxajgIl5i4m', 'llwwjj', 'asdsa@qq.com', 'zxc', NULL);
INSERT INTO `users` VALUES (76, 'okqs', '$2a$10$iR1iHFxmRjVOONeLlmhtmOeR2oTnNV9WsA0WfZvEdFGSWfupqOm5y', 'llwwjj', 'asdsa@qq.com', 'zxc', NULL);
INSERT INTO `users` VALUES (77, 'jiejieaa', '123456', 'llwwjj', 'asdsa@qq.com', 'zxc', NULL);
INSERT INTO `users` VALUES (78, 'lwjaaaa', '$2a$10$7KScrxibrBseo8I6gYUZcuv0TSH5hmZcZAXiJz8h7y.fsm0HD03jm', 'llwwjj', 'asdsa@qq.com', 'zxc', NULL);
INSERT INTO `users` VALUES (79, 'llwwjjzxc', '$2a$10$6/vBoSFMyGiNNzi0xeP.qOhnkspsAa2QW1Nrxawq67q0Tx/n4aTZO', 'llwwjj', 'Admin@qq.com', 'csu', NULL);
INSERT INTO `users` VALUES (80, 'llwwjjzxczxc', '$2a$10$jqTkZ.BW.e3TPhlqs6jHpehZnujgVt2T4y7mmi.ZJ3CRu/3IwC2Sa', 'llwwjj', 'Admin@qq.com', 'csu', NULL);
INSERT INTO `users` VALUES (81, 'admin123', '$2a$10$XjIJ/O37AXZJs1JxYquMduYqBXllDKgEOzTnVxpx6cjdMRkgFg3oS', 'admin', '123@qq.com', 'csu', NULL);
INSERT INTO `users` VALUES (82, 'admin1234', '$2a$10$.9JFgQ8x/RAO2WCCkxHDy.FwfBtghZm.FliggtuCawj/11t7NwfmW', 'admin', '123@qq.com', 'csu', NULL);
INSERT INTO `users` VALUES (83, 'admin12345', '$2a$10$xJtOXLwdWPNp2ZmnKwIs4uIenAfYkoV5EBGhM8VTI.WaD5AHgHTNi', 'admin', 'admin@qq.com', 'csu', NULL);

SET FOREIGN_KEY_CHECKS = 1;
