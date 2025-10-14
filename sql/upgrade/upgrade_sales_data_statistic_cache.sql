-- 销售数据统计缓存表结构升级脚本

-- 1. 备份原有数据
CREATE TABLE IF NOT EXISTS `jl_sales_data_statistic_cache_backup` AS 
SELECT * FROM `jl_sales_data_statistic_cache`;

-- 2. 添加新字段
ALTER TABLE `jl_sales_data_statistic_cache` 
ADD COLUMN `start_time` DATETIME DEFAULT NULL COMMENT '统计开始时间' AFTER `payment_amount`,
ADD COLUMN `end_time` DATETIME DEFAULT NULL COMMENT '统计结束时间' AFTER `start_time`,
ADD COLUMN `cache_update_time` DATETIME DEFAULT NULL COMMENT '缓存更新时间' AFTER `end_time`;

-- 3. 删除旧字段
ALTER TABLE `jl_sales_data_statistic_cache` DROP COLUMN `statistic_date`;

-- 4. 添加新索引
ALTER TABLE `jl_sales_data_statistic_cache` 
ADD KEY `idx_time_range` (`start_time`, `end_time`),
ADD UNIQUE KEY `uk_user_time_range` (`user_id`, `start_time`, `end_time`, `deleted`);

-- 5. 删除旧索引
ALTER TABLE `jl_sales_data_statistic_cache` DROP KEY IF EXISTS `idx_statistic_date`;

-- 6. 清空旧数据（因为数据结构已变更，旧数据不兼容）
TRUNCATE TABLE `jl_sales_data_statistic_cache`;

-- 7. 重置自增ID
ALTER TABLE `jl_sales_data_statistic_cache` AUTO_INCREMENT = 1;

-- 说明：
-- 由于表结构发生了根本性变化（从单个时间点变为时间范围），
-- 旧数据无法直接迁移，建议清空后通过定时任务重新生成缓存数据。
-- 如需保留旧数据进行分析，可从备份表 jl_sales_data_statistic_cache_backup 中查看。
