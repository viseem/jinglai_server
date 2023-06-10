-- 物资寄来单申请表
-- ----------------------------
-- Table structure for jl_project_supply_send_in
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_supply_send_in`;
CREATE TABLE `jl_project_supply_send_in`
(
    `id`                  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`         datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`         datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)     NOT NULL                    DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20) NOT NULL                    DEFAULT '0' COMMENT '租户编号',
    `project_id`          bigint(20) NOT NULL COMMENT '项目 id',
    `project_category_id` bigint(20) COMMENT '实验名目库的名目 id',
    `code`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '寄来单号',
    `shipment_number`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '寄来物流单号',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
    `send_date`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '寄来时间',
    `address`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '收货地址',
    `receiver_name`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '收货人',
    `receiver_phone`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '收货人电话',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='物资寄来单申请表';

-- 物资寄来单申请明细表
-- ----------------------------
-- Table structure for jl_project_supply_send_in_item
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_supply_send_in_item`;
CREATE TABLE `jl_project_supply_send_in_item`
(
    `id`                  bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_id`          bigint(20)                                                    NOT NULL COMMENT '项目 id',
    `project_category_id` bigint(20) COMMENT '实验名目库的名目 id',
    `supply_send_in_id`   bigint(20)                                                    NOT NULL COMMENT '物资寄来申请表id',
    `project_supply_id`   bigint(20)                                                    NOT NULL COMMENT '项目物资 id',
    `name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `fee_standard`        varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '规则/单位',
    `unit_fee`            varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '单价',
    `unit_amount`         int(10)                                                       NOT NULL DEFAULT 1 COMMENT '单量',
    `quantity`            int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    `valid_date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
    `temperature`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='物资寄来单申请明细表';

-- 取货单申请表
-- ----------------------------
-- Table structure for jl_project_supply_pickup
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_supply_pickup`;
CREATE TABLE `jl_project_supply_pickup`
(
    `id`                  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`         datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`         datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)     NOT NULL                    DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20) NOT NULL                    DEFAULT '0' COMMENT '租户编号',
    `project_id`          bigint(20) NOT NULL COMMENT '项目 id',
    `project_category_id` bigint(20) COMMENT '实验名目库的名目 id',
    `code`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '取货单号',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
    `send_date`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '取货时间',
    `user_id`             bigint(20) NOT NULL COMMENT '取货人',
    `address`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '取货地址',
    `contact_name`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '联系人姓名',
    `contact_phone`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '联系人电话',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='取货单申请表';

-- 取货单申请明细表
-- ----------------------------
-- Table structure for jl_project_supply_pickup_item
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_supply_pickup_item`;
CREATE TABLE `jl_project_supply_pickup_item`
(
    `id`                bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`           varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`       datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`       datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`         bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `supply_pickup_id`  bigint(20)                                                    NOT NULL COMMENT '取货单申请id',
    `project_supply_id` bigint(20)                                                    NOT NULL COMMENT '项目物资 id',
    `name`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `fee_standard`      varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '规则/单位',
    `unit_fee`          varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '单价',
    `unit_amount`       int(10)                                                       NOT NULL DEFAULT 1 COMMENT '单量',
    `quantity`          int(10)                                                       NOT NULL DEFAULT 1 COMMENT '数量',
    `mark`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    `valid_date`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
    `temperature`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
    `status`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',

    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='取货单申请明细表';


-- 项目采购单申请表
-- ----------------------------
-- Table structure for jl_project_procurement
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_procurement`;
CREATE TABLE `jl_project_procurement`
(
    `id`                  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`         datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`         datetime   NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)     NOT NULL                    DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20) NOT NULL                    DEFAULT '0' COMMENT '租户编号',
    `project_id`          bigint(20) NOT NULL COMMENT '项目 id',
    `project_category_id` bigint(20) COMMENT '实验名目库的名目 id',
    `code`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '采购单号',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
    `start_date`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '采购发起时间',
    `check_user_id`       bigint(20) NOT NULL COMMENT '签收陪审人',
    `address`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '收货地址',
    `receiver_user_id`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '收货人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='项目采购单申请表';

-- 项目采购单申请明细表
-- ----------------------------
-- Table structure for jl_project_procurement_item
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_procurement_item`;
CREATE TABLE `jl_project_procurement_item`
(
    `id`                bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`           varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`       datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`       datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`         bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `procurement_id`    bigint(20)                                                    NOT NULL COMMENT '采购单id',
    `project_supply_id` bigint(20)                                                    NOT NULL COMMENT '项目物资 id',
    `name`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `fee_standard`      varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '采购规则/单位',
    `unit_fee`          varchar(255) COLLATE utf8mb4_unicode_ci                       NOT NULL COMMENT '单价',
    `unit_amount`       int(10)                                                       NOT NULL DEFAULT 1 COMMENT '单量',
    `quantity`          int(10)                                                       NOT NULL DEFAULT 1 COMMENT '采购数量',
    `supplier_id`       bigint(20)                                                    NOT NULL COMMENT '供货商id',
    `buy_price`         int(10)                                                       NOT NULL DEFAULT 1 COMMENT '原价',
    `sale_price`        int(10)                                                       NOT NULL DEFAULT 1 COMMENT '销售价',
    `mark`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    `valid_date`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
    `brand`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '品牌',
    `catalog_number`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '目录号',
    `delivery_date`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '货期',
    `status`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态:等待采购信息、等待打款、等待采购、等待签收、等待入库',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='项目采购单申请明细表';

-- 项目采购单打款表
-- ----------------------------
-- Table structure for jl_project_procurement_payment
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_procurement_payment`;
CREATE TABLE `jl_project_procurement_payment`
(
    `id`             bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`        varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`      bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `procurement_id` bigint(20)                                                    NOT NULL COMMENT '采购单id',
    `payment_date`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '打款时间',
    `amount`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '打款金额',
    `supplier_id`    bigint(20)                                                    NOT NULL COMMENT '供货商 id',
    `mark`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    `proof_name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '凭证',
    `proof_url`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '凭证地址',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='项目采购单打款表';

-- 采购供货商表
-- ----------------------------
-- Table structure for jl_project_supplier
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_supplier`;
CREATE TABLE `jl_project_supplier`
(
    `id`            bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`       varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`     bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `contact_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '联系人',
    `contact_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '联系电话',
    `payment_cycle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '结算周期',
    `bank_account`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '银行卡号',
    `tax_number`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '税号',
    `mark`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='项目采购单物流信息表';

-- 项目采购单物流信息表
-- ----------------------------
-- Table structure for jl_project_procurement_shipment
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_procurement_shipment`;
CREATE TABLE `jl_project_procurement_shipment`
(
    `id`                  bigint(20)                                            NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)                                                NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20)                                            NOT NULL DEFAULT '0' COMMENT '租户编号',
    `procurement_id`      bigint(20)                                            NOT NULL COMMENT '采购单id',
    `shipment_number`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '物流单号',
    `file_name`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '附件名称',
    `file_url`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '附件地址',
    `mark`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
    `expect_arrival_time` datetime COMMENT '预计送达日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='项目采购单物流信息表';

-- 库管房间号
-- ----------------------------
-- Table structure for jl_inventory_room
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_room`;
CREATE TABLE `jl_inventory_room`
(
    `id`               bigint(20)                                            NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`          varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '创建者',
    `create_time`      datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '更新者',
    `update_time`      datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)                                                NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`        bigint(20)                                            NOT NULL DEFAULT '0' COMMENT '租户编号',
    `name`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '名称',
    `guardian_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '负责人',
    `mark`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注描述',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='库管房间号';

-- 库管存储容器表
-- ----------------------------
-- Table structure for jl_inventory_container
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_container`;
CREATE TABLE `jl_inventory_container`
(
    `id`               bigint(20)                                            NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`          varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '创建者',
    `create_time`      datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '更新者',
    `update_time`      datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)                                                NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`        bigint(20)                                            NOT NULL DEFAULT '0' COMMENT '租户编号',
    `room_id`          int(10)                                               NOT NULL DEFAULT '0' COMMENT '库房id：可以用字典',
    `name`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '名称',
    `place`            varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '所在位置',
    `guardian_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '负责人',
    `mark`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注描述',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='库管存储容器表';

-- 库管存储容器位置表
-- ----------------------------
-- Table structure for jl_inventory_container_place
-- ----------------------------
DROP TABLE IF EXISTS `jl_inventory_container_place`;
CREATE TABLE `jl_inventory_container_place`
(
    `id`           bigint(20)                                            NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`      varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)                                                NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`    bigint(20)                                            NOT NULL DEFAULT '0' COMMENT '租户编号',
    `container_id` int(10)                                               NOT NULL DEFAULT '0' COMMENT '库房id：可以用字典',
    `name`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '名称',
    `place`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '所在位置',
    `mark`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注描述',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='库管存储容器位置表';

-- 项目物资变更日志表
-- ----------------------------
-- Table structure for jl_project_supply_log
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_supply_log`;
CREATE TABLE `jl_project_supply_log`
(
    `id`          bigint(20)                                            NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`     varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) COLLATE utf8mb4_unicode_ci                         DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint(20)                                            NOT NULL DEFAULT '0' COMMENT '租户编号',
    `ref_id`      bigint(20)                                            NOT NULL DEFAULT '0' COMMENT '相关的id，采购、寄来、取货',
    `type`        bigint(20)                                            NOT NULL COMMENT '类型：采购、寄来、取货',
    `status`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    `mark`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注描述',
    `log`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '变更描述',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='项目物资变更日志表';

-- 库管项目物资库存变更日志附件表
-- ----------------------------
-- Table structure for jl_project_supply_log_attachment
-- ----------------------------
DROP TABLE IF EXISTS `jl_project_supply_log_attachment`;
CREATE TABLE `jl_project_supply_log_attachment`
(
    `id`                    bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`               varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
    `create_time`           datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`               varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
    `update_time`           datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`               bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`             bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
    `project_supply_log_id` bigint(20)                                                    NOT NULL COMMENT '项目物资日志表id',
    `file_name`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '附件名称',
    `file_url`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '附件地址',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='库管项目物资库存变更日志附件表';

-- 库管项目物资库存变更日志明细表
-- ----------------------------
-- Table structure for jl_project_supply_log_item
-- ----------------------------
# DROP TABLE IF EXISTS `jl_project_supply_log_item`;
# CREATE TABLE `jl_project_supply_log_item`
# (
#     `id`                    bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'ID',
#     `creator`               varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '创建者',
#     `create_time`           datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#     `updater`               varchar(64) COLLATE utf8mb4_unicode_ci                                 DEFAULT '' COMMENT '更新者',
#     `update_time`           datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
#     `deleted`               bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
#     `tenant_id`             bigint(20)                                                    NOT NULL DEFAULT '0' COMMENT '租户编号',
#     `project_supply_log_id` bigint(20)                                                    NOT NULL COMMENT '项目物资日志表id',
#     `project_supply_id`     bigint(20)                                                    NOT NULL COMMENT '项目物资表id',
#     `name`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '名称',
#     `change_num`            int(10)                                                       NOT NULL COMMENT '变更数量',
#     `place`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '所在位置',
#     `status`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
#     `mark`                  text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '备注',
#     `valid_date`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '有效期',
#     `temperature`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储温度',
#     `brand`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '品牌',
#     `catalog_no`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目录号',
#     `room_id`               bigint(20) COMMENT '房间 id',
#     `container_id`          bigint(20) COMMENT '储存器材 id',
#     `zoom_id`               bigint(20) COMMENT '区域位置 id',
#     PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB
#   AUTO_INCREMENT = 1
#   DEFAULT CHARSET = utf8mb4
#   COLLATE = utf8mb4_unicode_ci COMMENT ='库管项目物资库存变更日志明细表';