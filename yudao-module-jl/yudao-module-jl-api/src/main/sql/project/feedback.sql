DROP TABLE IF EXISTS `jl_project_feedback`;
CREATE TABLE `jl_project_feedback`
(
    `id`                  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `creator`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time`         datetime NOT NULL                      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time`         datetime NOT NULL                      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)   NOT NULL                      DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`           bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',

    `project_id`          bigint(20) NOT NULL COMMENT '项目 id',
    `project_category_id` bigint(20) NOT NULL COMMENT '实验名目 id',
    `userId`              bigint(20) COMMENT '内部人员 id',
    `customerId`          bigint(20) COMMENT '客户 id',
    `type`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '字典：内部反馈/客户反馈',
    `status`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '状态',
    `content`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '反馈的内容',
    `result`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '处理结果',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='项目反馈';