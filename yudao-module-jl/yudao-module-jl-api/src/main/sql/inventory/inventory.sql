-- 库管相关表

-- 公司实验物资库存表
-- ----------------------------
-- Table structure for jl_inventory_company_supply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_company_supply`;
CREATE TABLE `jl_inventory_company_supply`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`      varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`    bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `supply_id`    bigint(20)                                                    NOT NULL COMMENT '物资 id',
    `name`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `fee_standard` varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '规则/单位',
    `unit_fee`     varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '单价',
    `unit_amount`  int(10)                                                       NOT NULL DEFAULT 1 COMMENT '单量',
    `quantity`     int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
    `valid_date`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='公司实验物资库存表';

-- 出库申请表
-- ----------------------------
-- Table structure for jl_inventory_supply_out
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_supply_out`;
CREATE TABLE `jl_inventory_supply_out`
(
    `id`                  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`         datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`         datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)     NOT NULL                    DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20) NOT NULL                    DEFAULT '0' COMMENT '租户编号',
    `project_id`          bigint(20) NOT NULL COMMENT '项目 id',
    `project_category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '说明',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='出库申请表';

-- 出库申请明细表
-- ----------------------------
-- Table structure for jl_inventory_supply_out_item
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_supply_out_item`;
CREATE TABLE `jl_inventory_supply_out_item`
(
    `id`                bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`           varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`       datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`       datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`         bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `out_apply_id`      bigint(20)                                                    NOT NULL COMMENT '出库申请表 id',
    `project_supply_id` bigint(20)                                                    NOT NULL COMMENT '物资 id',
    `name`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `fee_standard`      varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '规则/单位',
    `unit_fee`          varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '单价',
    `unit_amount`       int(10)                                                       NOT NULL DEFAULT 1 COMMENT '单量',
    `quantity`          int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    `mark`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='出库申请明细表';

-- 入库申请表
-- ----------------------------
-- Table structure for jl_inventory_in_apply
-- ----------------------------
# DROP TABLE IF EXISTS `jl_inventory_in_apply`;
# CREATE TABLE `jl_inventory_in_apply`
# (
#     `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
#     `creator`     varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
#     `create_time` datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#     `updater`     varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
#     `update_time` datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
#     `deleted`     bit(1)     NOT NULL                    DEFAULT b'0' COMMENT '是否删除',
#     `tenant_id`   bigint(20) NOT NULL                    DEFAULT '0' COMMENT '租户编号',
#     `project_id`  bigint(20) NOT NULL COMMENT '项目 id',
#     `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
#     PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB
#   AUTO_INCREMENT = 1
#   DEFAULT CHARSET = utf8mb4
#   COLLATE = utf8mb4_unicode_ci COMMENT ='入库申请表';
#
# --入库申请明细表
# -- ----------------------------
# -- Table structure for jl_inventory_in_applyitem
# -- ----------------------------
# DROP TABLE IF EXISTS `jl_inventory_in_applyitem`;
# CREATE TABLE `jl_inventory_in_applyitem`
# (
#     `id`                  bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
#     `creator`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
#     `create_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#     `updater`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
#     `update_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
#     `deleted`             bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
#     `tenant_id`           bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
#     `in_apply_id`         bigint(20)                                                    NOT NULL COMMENT '入库申请表 id',
#     `supply_id`           bigint(20)                                                    NOT NULL COMMENT '物资 id',
#     `name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
#     `specs`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
#     `number`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
#     `quantity`            int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
#     `quantity_total`      int(10)                                                       NOT NULL DEFAULT 1 COMMENT '总量',
#     `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
#     `brand`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
#     `catalog_no`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
#     `valid_date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
#     `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
#     PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB
#   AUTO_INCREMENT = 1
#   DEFAULT CHARSET = utf8mb4
#   COLLATE = utf8mb4_unicode_ci COMMENT ='入库申请明细表';

-- 实验产品入库表
-- ----------------------------
-- Table structure for jl_inventory_category_apply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_product_in`;
CREATE TABLE `jl_inventory_product_in`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`     varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)     NOT NULL                    DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint(20) NOT NULL                    DEFAULT '0' COMMENT '租户编号',
    `project_id`  bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    `project_category_id` bigint(20) NOT NULL COMMENT '实验名目 id',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    `mark`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='实验产品入库表';

-- 实验产品入库明细表
-- ----------------------------
-- Table structure for jl_inventory_product_in_item
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_product_in_item`;
CREATE TABLE `jl_inventory_product_in_item`
(
    `id`                  bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `product_in_id`   bigint(20)                                                    NOT NULL COMMENT '实验产品入库表 id',
    `project_supply_source` bigint(20) COMMENT '产自实验物资的 id',
    `name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `fee_standard`      varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '规则/单位',
    `unit_fee`          varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '单价',
    `unit_amount`       int(10)                                                       NOT NULL DEFAULT 1 COMMENT '单量',
    `quantity`          int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          COMMENT '备注',
    `room_id`          bigint(20) COMMENT '房间 id',
    `storage_id`          bigint(20) COMMENT '储存器材 id',
    `zoom_id`          bigint(20) COMMENT '区域位置 id',
    `valid_date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  COMMENT '有效期',
    `temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='实验产品入库表';

-- 实验产品寄送表
-- ----------------------------
-- Table structure for jl_inventory_product_send
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_product_send`;
CREATE TABLE `jl_inventory_product_send`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`     varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)     NOT NULL                    DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint(20) NOT NULL                    DEFAULT '0' COMMENT '租户编号',
    `project_id`  bigint(20) NOT NULL COMMENT '项目 id',
    `project_category_id` bigint(20) COMMENT '实验名目库的名目 id',
    `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  COMMENT '寄送单号',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          COMMENT '备注',
    `send_date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  COMMENT '寄送时间',
    `address`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          COMMENT '收货地址',
    `receiver_name`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          COMMENT '收货人',
    `receiver_phone`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          COMMENT '收货人电话',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='实验产品寄送表';

-- 实验产品寄送明细表
-- ----------------------------
-- Table structure for jl_inventory_product_send_item
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_product_send_item`;
CREATE TABLE `jl_inventory_product_send_item`
(
    `id`                  bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `product_send_id`    bigint(20)                                                    NOT NULL COMMENT '实验产品寄送表 id',
    `product_id`           bigint(20)                                                    NOT NULL COMMENT '物资 id',
    `name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `fee_standard`      varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '规则/单位',
    `unit_fee`          varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '单价',
    `unit_amount`       int(10)                                                       NOT NULL DEFAULT 1 COMMENT '单量',
    `quantity`          int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    `valid_date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
    `temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='实验产品寄送明细表';

-- 采购签收表
-- ----------------------------
-- Table structure for jl_inventory_procurement_checkin
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_procurement_checkin`;
CREATE TABLE `jl_inventory_procurement_checkin`
(
    `id`          bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`     varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id`  bigint(20)                                                    NOT NULL COMMENT '项目 id',
    `mark`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '备注',
    `procurement_id`        bigint(20) NOT NULL COMMENT '采购单 id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='采购签收表';

-- 采购签收上传的附件
-- ----------------------------
-- Table structure for jl_inventory_checkin_attachment
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_checkin_attachment`;
CREATE TABLE `jl_inventory_checkin_attachment`
(
    `id`          bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`     varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `checkin_id`  bigint(20)                                                    NOT NULL COMMENT '签收 id',
    `file_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名',
    `file_url`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '地址',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='采购签收上传的附件';

-- 采购签收明细表
-- ----------------------------
-- Table structure for jl_inventory_procurement_checkin_item
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_procurement_checkin_item`;
CREATE TABLE `jl_inventory_procurement_checkin_item`
(
    `id`                  bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `checkin_id`             bigint(20)                                                    NOT NULL COMMENT '签收表 id',
    `procurement_item_id`           bigint(20)                                                    NOT NULL COMMENT '采购单项物资 id',
    `name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `fee_standard`      varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '规则/单位',
    `unit_fee`          varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '单价',
    `unit_amount`       int(10)                                                       NOT NULL DEFAULT 1 COMMENT '单量',
    `quantity`          int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态，未到、部分，全部',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    `valid_date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
    `temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='采购签收明细表';


-- 上门取件签收表
-- ----------------------------
-- Table structure for jl_inventory_pickup_checkin
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_pickup_checkin`;
CREATE TABLE `jl_inventory_pickup_checkin`
(
    `id`          bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`     varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id`  bigint(20)                                                    NOT NULL COMMENT '项目 id',
    `pickup_id` bigint(20)                                                    NOT NULL COMMENT '取货单 id',
    `user_id` bigint(20)                                                    NOT NULL COMMENT '取货的人 id',
    `date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  COMMENT '寄送时间',
    `address`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          COMMENT '取件地址',
    `contact_name`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          COMMENT '联系人',
    `contact_phone`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          COMMENT '联系方式',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='上门取件签收表';

-- 上门取件签收明细表
-- ----------------------------
-- Table structure for jl_inventory_pickup_item
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_pickup_item`;
CREATE TABLE `jl_inventory_pickup_item`
(
    `id`                  bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `pickup_id`        bigint(20)                                                    NOT NULL COMMENT '上门取件 id',
    `supply_id`           bigint(20)                                                    NOT NULL COMMENT '物资 id',
    `name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `specs`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
    `number`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
    `quantity`            int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
    `quantity_total`      int(10)                                                       NOT NULL DEFAULT 1 COMMENT '总量',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    `brand`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
    `catalog_no`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
    `valid_date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
    `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='上门取件签收明细表';


--寄送采购入库表
-- ----------------------------
-- Table structure for jl_inventory_send_apply
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_send_apply`;
CREATE TABLE `jl_inventory_send_apply`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`     varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)     NOT NULL                    DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint(20) NOT NULL                    DEFAULT '0' COMMENT '租户编号',
    `project_id`  bigint(20) NOT NULL COMMENT '项目 id',
    `category_id` bigint(20) NOT NULL COMMENT '实验名目库的名目 id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='寄送采购入库表';

--寄送采购入库明细表
-- ----------------------------
-- Table structure for jl_inventory_send_applyitem
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_send_applyitem`;
CREATE TABLE `jl_inventory_send_applyitem`
(
    `id`                  bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `send_apply_id`       bigint(20)                                                    NOT NULL COMMENT '寄送采购入库表 id',
    `supply_id`           bigint(20)                                                    NOT NULL COMMENT '物资 id',
    `name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `specs`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格',
    `number`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数量',
    `quantity`            int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
    `quantity_total`      int(10)                                                       NOT NULL DEFAULT 1 COMMENT '总量',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    `brand`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
    `catalog_no`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
    `valid_date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
    `storage_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='寄送采购入库明细表';





