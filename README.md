# 启明艺术空间 · 后端服务（qiming-server）

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Deploy: Cloudflare Pages](https://img.shields.io/badge/前端-已部署-blue)](https://qiming-artspace.pages.dev)

「启明艺术空间」的全栈后端，基于 **Spring Boot + MyBatis-Plus + JWT**，为前端艺术空间与管理后台提供作品 / 图文 / 分类 / 评论 / 用户 / 站点配置等数据服务。

> 配套前端（已部署）：<https://qiming-artspace.pages.dev>
> 管理后台仓库：<https://github.com/lakewhisper1999/qiming-admin>

---

## ✨ 功能特性

- 作品 / 图文笔记 / 分类的增删改查与软删除
- 用户提问（评论）审核与回复
- 访客日志、操作日志审计
- 站点配置（含「关于我」`about_subtitle` / `about_content`）
- JWT 鉴权：公开接口免登录，后台接口需 `Bearer Token` + admin 角色
- 默认管理员由 `DataInitializer` 首次启动自动创建

## 🧱 技术栈

| 项 | 说明 |
|---|---|
| 语言 | Java 8 |
| 框架 | Spring Boot 2.7.18 |
| ORM | MyBatis-Plus 3.5.5 |
| 鉴权 | JWT（jjwt 0.9.1） |
| 数据库 | MySQL（9 张表，`init.sql` 建表） |
| 密码加密 | Spring Security Crypto（仅 BCrypt，避免与 JWT 鉴权冲突） |
| 端口 | 8088 |

## 📋 环境要求

- JDK 8+
- Maven 3.6+
- MySQL 5.7 / 8.x

## 🚀 快速开始（IDEA）

1. 用 IDEA 打开本目录：**File → Open → 选择 `qiming-server` 文件夹**（单独打开，不要打开上层 `启明` 目录）。
2. 等待 Maven 自动导入依赖；若没动，点右侧 **Maven → Reload All Maven Projects**。
3. 本地准备 MySQL，创建数据库：
   ```sql
   CREATE DATABASE qiming CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
   ```
4. 执行建表 SQL：在 MySQL 客户端或 IDEA 的 Database 工具里跑一遍 `src/main/resources/sql/init.sql`。
5. 检查 `src/main/resources/application.yml` 的数据库连接（默认 `root` / `123456`；也可通过环境变量 `DB_PASSWORD` 覆盖）。
6. 运行主类 **`top.kaiven.qiming.QimingApplication`**（右键 → Run）。
7. 控制台出现 `Started QimingApplication` 即启动成功，接口监听 `http://localhost:8088`。

> ⚠️ 首次启动会由 `DataInitializer` 自动创建默认管理员，**无需手动插入**。

## 👤 默认管理员

| 用户名 | 密码 |
|---|---|
| `admin` | `admin123` |

## 🔌 接口概览

| 类型 | 路径前缀 | 鉴权 |
|---|---|---|
| 前台公开接口 | `/api/public/**` | 无需登录 |
| 后台管理接口 | `/api/admin/**` | 需 JWT + admin 角色（`Authorization: Bearer <token>`） |

## 🔒 配置与安全说明

- `application.yml` 中的 `jwt.secret`、`spring.datasource.password` 均为**本地开发占位值**（默认 `123456` / 开发用 secret）。生产环境请改用环境变量 `DB_PASSWORD` / `JWT_SECRET`，**切勿把真实密钥提交到仓库**。
- 上传文件存放于 `uploads/`，已加入 `.gitignore`，**不会入库**（约 1.9G 媒体，学习者在本地运行后可自行上传）。
- Java 9+ 已移除 `javax.xml.bind`，本项目已显式引入 `jaxb-api:2.3.1` 以兼容 jjwt 0.9.1，修改依赖后请 Reload Maven。

## 📁 目录结构

```
src/main/java/top/kaiven/qiming/
├── QimingApplication.java      # 启动类
├── controller/                 # 接口层（前台 + 后台）
├── service/                    # 业务逻辑
├── mapper/                     # MyBatis-Plus Mapper
├── entity/                     # 实体（含 @TableField 自动填充 created_at/updated_at）
├── config/                     # JWT / 跨域 / 拦截器
└── ...
src/main/resources/
├── application.yml             # 主配置
├── sql/init.sql                # 建表 SQL
└── mapper/*.xml                # MyBatis XML
```

## 🤝 参与贡献

欢迎提 Issue 与 PR。提交前请确保：

- 本地能正常启动并跑通基础接口；
- 新增接口补充到本 README 的「接口概览」；
- 密钥、上传媒体等已正确加入 `.gitignore`（不要提交 `uploads/`、`target/`、`.idea/`）。

## 📄 许可证

本项目基于 [MIT 许可证](LICENSE) 开源。© 2026 Kevin (lakewhisper1999)。

## 🔗 相关仓库

- 前端（已部署）：<https://github.com/lakewhisper1999/qiming-artspace>
- 管理后台：<https://github.com/lakewhisper1999/qiming-admin>
