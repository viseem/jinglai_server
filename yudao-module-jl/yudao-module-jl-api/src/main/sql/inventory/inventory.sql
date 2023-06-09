--库管相关表

--项目物资表
-- ----------------------------
-- Table structure for jl_inventory_project_supply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_project_supply`;
CREATE TABLE `jl_inventory_project_supply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '总数量',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',    
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目物资表';

--公司实验物资库存表
-- ----------------------------
-- Table structure for jl_inventory_company_supply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_company_supply`;
CREATE TABLE `jl_inventory_company_supply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '总数量',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',    
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公司实验物资库存表';

--项目实验物资库存表
-- ----------------------------
-- Table structure for jl_inventory_category_supply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_category_supply`;
CREATE TABLE `jl_inventory_category_supply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '总数量',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',    
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目实验物资库存表';

--出库申请表
-- ----------------------------
-- Table structure for jl_inventory_out_apply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_out_apply`;
CREATE TABLE `jl_inventory_out_apply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='出库申请表';

--出库申请明细表
-- ----------------------------
-- Table structure for jl_inventory_out_applyitem
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_out_applyitem`;
CREATE TABLE `jl_inventory_out_applyitem` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `out_apply_id` bigint(20) NOT NULL COMMENT '出库申请表 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '数量',
	  `quantity_total` int(10) NOT NULL DEFAULT 1 COMMENT '总量',
	  `mark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='出库申请明细表';

--入库申请表
-- ----------------------------
-- Table structure for jl_inventory_in_apply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_in_apply`;
CREATE TABLE `jl_inventory_in_apply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入库申请表';

--入库申请明细表
-- ----------------------------
-- Table structure for jl_inventory_in_applyitem
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_in_applyitem`;
CREATE TABLE `jl_inventory_in_applyitem` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `in_apply_id` bigint(20) NOT NULL COMMENT '入库申请表 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '数量',
	  `quantity_total` int(10) NOT NULL DEFAULT 1 COMMENT '总量',
	  `mark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
	  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
	  `catalog_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
	  `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入库申请明细表';

--实验产品入库表
-- ----------------------------
-- Table structure for jl_inventory_category_apply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_category_apply`;
CREATE TABLE `jl_inventory_category_apply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实验产品入库表';

--实验产品入库明细表
-- ----------------------------
-- Table structure for jl_inventory_category_applyitem
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_category_applyitem`;
CREATE TABLE `jl_inventory_category_applyitem` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `category_apply_id` bigint(20) NOT NULL COMMENT '实验产品入库表 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '数量',
	  `quantity_total` int(10) NOT NULL DEFAULT 1 COMMENT '总量',
	  `mark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
	  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
	  `catalog_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
	  `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实验产品入库表';

--实验产品寄送表
-- ----------------------------
-- Table structure for jl_inventory_category_send
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_category_send`;
CREATE TABLE `jl_inventory_category_send` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实验产品寄送表';

--实验产品寄送明细表
-- ----------------------------
-- Table structure for jl_inventory_category_senditem
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_category_senditem`;
CREATE TABLE `jl_inventory_category_senditem` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `category_send_id` bigint(20) NOT NULL COMMENT '实验产品寄送表 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '数量',
	  `quantity_total` int(10) NOT NULL DEFAULT 1 COMMENT '总量',
	  `mark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
	  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
	  `catalog_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
	  `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实验产品寄送明细表';

--签收表
-- ----------------------------
-- Table structure for jl_inventory_sgin
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_sgin`;
CREATE TABLE `jl_inventory_sgin` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    `annex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '附件',
    `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='签收表';

--签收明细表
-- ----------------------------
-- Table structure for jl_inventory_sginitem
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_sginitem`;
CREATE TABLE `jl_inventory_sginitem` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `sgin_id` bigint(20) NOT NULL COMMENT '签收表 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '数量',
	  `quantity_total` int(10) NOT NULL DEFAULT 1 COMMENT '总量',
	  `mark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
	  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
	  `catalog_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
	  `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='签收明细表';

--寄送采购入库表
-- ----------------------------
-- Table structure for jl_inventory_send_apply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_send_apply`;
CREATE TABLE `jl_inventory_send_apply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='寄送采购入库表';

--寄送采购入库明细表
-- ----------------------------
-- Table structure for jl_inventory_send_applyitem
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_send_applyitem`;
CREATE TABLE `jl_inventory_send_applyitem` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `send_apply_id` bigint(20) NOT NULL COMMENT '寄送采购入库表 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '数量',
	  `quantity_total` int(10) NOT NULL DEFAULT 1 COMMENT '总量',
	  `mark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
	  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
	  `catalog_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
	  `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='寄送采购入库明细表';

--采购签收表
-- ----------------------------
-- Table structure for jl_inventory_buy_sign
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_buy_sign`;
CREATE TABLE `jl_inventory_buy_sign` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    `annex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '附件',
    `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '备注',        
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购签收表';

--采购签收明细表
-- ----------------------------
-- Table structure for jl_inventory_buy_signitem
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_buy_signitem`;
CREATE TABLE `jl_inventory_buy_signitem` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `buy_sign_id` bigint(20) NOT NULL COMMENT '采购签收表 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '数量',
	  `quantity_total` int(10) NOT NULL DEFAULT 1 COMMENT '总量',
	  `mark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
	  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
	  `catalog_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
	  `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购签收明细表';

--上门取签收表
-- ----------------------------
-- Table structure for jl_inventory_door_sign
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_door_sign`;
CREATE TABLE `jl_inventory_door_sign` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    `annex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '附件',
    `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '备注',        
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='上门取签收表';

--上门取签收明细表
-- ----------------------------
-- Table structure for jl_inventory_door_signitem
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_door_signitem`;
CREATE TABLE `jl_inventory_door_signitem` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
    `door_sign_id` bigint(20) NOT NULL COMMENT '上门取签收表 id',
	  `supply_id` bigint(20) NOT NULL COMMENT '物资 id',
	  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
	  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
	  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
	  `quantity` int(10) NOT NULL DEFAULT 1 COMMENT '数量',
	  `quantity_total` int(10) NOT NULL DEFAULT 1 COMMENT '总量',
	  `mark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
	  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
	  `catalog_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
	  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
	  `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='上门取签收明细表';









