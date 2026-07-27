-- =====================================================
-- 启明艺术空间 · 数据库初始化脚本
-- 数据库: qiming
-- 共 9 张表
-- =====================================================

CREATE DATABASE IF NOT EXISTS qiming DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE qiming;

-- ----------------------------
-- 1. 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(50)  NOT NULL COMMENT '用户名',
    `password`    VARCHAR(255) NOT NULL COMMENT '密码（BCrypt加密）',
    `nickname`    VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
    `avatar`      VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    `role`        VARCHAR(20)  NOT NULL DEFAULT 'admin' COMMENT '角色: admin',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 默认管理员由 DataInitializer 首次启动自动创建
-- 用户名: admin / 密码: admin123 (BCrypt 加密)

-- ----------------------------
-- 2. 分类表
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `name`        VARCHAR(50)  NOT NULL COMMENT '分类名称',
    `icon`        VARCHAR(255) DEFAULT NULL COMMENT '图标',
    `sort_order`  INT          NOT NULL DEFAULT 0 COMMENT '排序',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- 默认分类
INSERT INTO `category` (`name`, `sort_order`) VALUES
('平面及动效作品', 1),
('素材库', 2),
('学习工程', 3),
('图文笔记', 4),
('提问箱', 5);

-- ----------------------------
-- 3. 作品表
-- ----------------------------
DROP TABLE IF EXISTS `artwork`;
CREATE TABLE `artwork` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '作品ID',
    `title`         VARCHAR(100) NOT NULL COMMENT '标题',
    `description`   TEXT         DEFAULT NULL COMMENT '描述',
    `cover_url`     VARCHAR(255) NOT NULL COMMENT '封面URL(第一张图)',
    `image_urls`    TEXT         DEFAULT NULL COMMENT '多图URL(JSON数组，不含封面)',
    `category_id`   BIGINT       DEFAULT NULL COMMENT '分类ID',
    `download_url`  VARCHAR(255) DEFAULT NULL COMMENT '下载URL',
    `video_url`     VARCHAR(512) DEFAULT NULL COMMENT '视频嵌入地址(B站/YouTube iframe)',
    `file_size`     BIGINT       DEFAULT 0 COMMENT '文件大小(字节)',
    `view_count`    INT          NOT NULL DEFAULT 0 COMMENT '浏览次数',
    `download_count` INT         NOT NULL DEFAULT 0 COMMENT '下载次数',
    `user_id`       BIGINT       NOT NULL COMMENT '创建者ID',
    `created_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='作品表';

-- ----------------------------
-- 4. 评论/提问表
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `nickname`    VARCHAR(50)  DEFAULT '匿名用户' COMMENT '匿名昵称',
    `content`     TEXT         NOT NULL COMMENT '提问内容',
    `reply`       TEXT         DEFAULT NULL COMMENT '管理员回复',
    `user_id`     BIGINT       DEFAULT NULL COMMENT '回复者ID(管理员)',
    `replied_at`  DATETIME     DEFAULT NULL COMMENT '回复时间',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提问时间',
    `updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提问/评论表';

-- ----------------------------
-- 5. 图文笔记表
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '文章ID',
    `title`       VARCHAR(200) NOT NULL COMMENT '标题',
    `content`     LONGTEXT     DEFAULT NULL COMMENT '正文(Markdown)',
    `cover_url`   VARCHAR(255) DEFAULT NULL COMMENT '封面URL',
    `category_id` BIGINT       DEFAULT NULL COMMENT '分类ID',
    `view_count`  INT          NOT NULL DEFAULT 0 COMMENT '浏览次数',
    `user_id`     BIGINT       NOT NULL COMMENT '作者ID',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图文笔记表';

-- ----------------------------
-- 6. 收藏表
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
    `id`          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id`     BIGINT   NOT NULL COMMENT '用户ID',
    `artwork_id`  BIGINT   NOT NULL COMMENT '作品ID',
    `created_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_artwork_id` (`artwork_id`),
    UNIQUE KEY `uk_user_artwork` (`user_id`, `artwork_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

-- ----------------------------
-- 7. 访客日志表
-- ----------------------------
DROP TABLE IF EXISTS `visit_log`;
CREATE TABLE `visit_log` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `ip`          VARCHAR(50)  DEFAULT NULL COMMENT '访问IP',
    `page`        VARCHAR(255) DEFAULT NULL COMMENT '访问页面',
    `user_agent`  VARCHAR(500) DEFAULT NULL COMMENT '浏览器UA',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
    PRIMARY KEY (`id`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访客日志表';

-- ----------------------------
-- 8. 操作日志表
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `user_id`     BIGINT       DEFAULT NULL COMMENT '操作用户ID',
    `username`    VARCHAR(50)  DEFAULT NULL COMMENT '操作用户名',
    `action`      VARCHAR(100) NOT NULL COMMENT '操作类型',
    `target`      VARCHAR(255) DEFAULT NULL COMMENT '操作目标',
    `ip`          VARCHAR(50)  DEFAULT NULL COMMENT '操作IP',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- ----------------------------
-- 9. 站点配置表
-- ----------------------------
DROP TABLE IF EXISTS `site_config`;
CREATE TABLE `site_config` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `config_key`   VARCHAR(100) NOT NULL COMMENT '配置键',
    `config_value` TEXT         DEFAULT NULL COMMENT '配置值',
    `description`  VARCHAR(255) DEFAULT NULL COMMENT '配置说明',
    `updated_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='站点配置表';

-- 默认站点配置
INSERT INTO `site_config` (`config_key`, `config_value`, `description`) VALUES
('site_name', '启明艺术空间', '站点名称'),
('site_description', '个人艺术作品展示与交流平台', '站点描述'),
('site_logo', '/uploads/logo.png', '站点Logo'),
('about_subtitle', '视觉设计爱好者+码农', '关于页副标题'),
('about_content', '这里是关于页面的内容...', '关于页面内容');
