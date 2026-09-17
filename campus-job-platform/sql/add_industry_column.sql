-- 为 t_job 表添加 industry 列
ALTER TABLE t_job ADD COLUMN industry VARCHAR(50) DEFAULT NULL COMMENT '所属行业' AFTER city;

-- 为 industry 列添加索引
ALTER TABLE t_job ADD INDEX idx_industry (industry);
