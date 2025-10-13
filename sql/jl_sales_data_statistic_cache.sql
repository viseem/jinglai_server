-- 销售数据统计缓存表
CREATE TABLE IF NOT EXISTS `jl_sales_data_statistic_cache` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `user_name` VARCHAR(100) DEFAULT NULL COMMENT '用户名称',
    `order_amount` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '成交金额',
    `accounts_receivable` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '应收金额',
    `invoice_amount` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '已开票金额',
    `payment_amount` DECIMAL(10, 2) DEFAULT 0.00 COMMENT '回款金额',
    `start_time` DATETIME DEFAULT NULL COMMENT '统计开始时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '统计结束时间',
    `time_range_type` VARCHAR(20) DEFAULT NULL COMMENT '时间范围类型',
    `cache_update_time` DATETIME DEFAULT NULL COMMENT '缓存更新时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator` BIGINT(20) DEFAULT NULL COMMENT '创建人',
    `updater` BIGINT(20) DEFAULT NULL COMMENT '更新人',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    `tenant_id` BIGINT(20) DEFAULT 0 COMMENT '租户ID',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_time_range` (`start_time`, `end_time`),
    KEY `idx_time_range_type` (`time_range_type`),
    KEY `idx_deleted` (`deleted`),
    UNIQUE KEY `uk_user_time_range` (`user_id`, `time_range_type`, `start_time`, `end_time`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='销售数据统计缓存表';

