-- 为项目表添加状态变更说明字段
-- 该字段仅在不需要审批的状态变更时更新

-- MySQL
ALTER TABLE jl_project_base ADD COLUMN stage_mark VARCHAR(500) DEFAULT NULL COMMENT '最近状态变更说明（仅不需审批的直接变更才更新此字段）';

-- PostgreSQL
-- ALTER TABLE jl_project_base ADD COLUMN stage_mark VARCHAR(500) DEFAULT NULL;
-- COMMENT ON COLUMN jl_project_base.stage_mark IS '最近状态变更说明（仅不需审批的直接变更才更新此字段）';

-- Oracle
-- ALTER TABLE jl_project_base ADD stage_mark VARCHAR2(500) DEFAULT NULL;
-- COMMENT ON COLUMN jl_project_base.stage_mark IS '最近状态变更说明（仅不需审批的直接变更才更新此字段）';

-- SQL Server
-- ALTER TABLE jl_project_base ADD stage_mark VARCHAR(500) NULL;
-- EXEC sp_addextendedproperty 'MS_Description', '最近状态变更说明（仅不需审批的直接变更才更新此字段）', 'SCHEMA', 'dbo', 'TABLE', 'jl_project_base', 'COLUMN', 'stage_mark';

