# 酒店服务系统

一个基于 Vue2 + SpringBoot 的酒店服务系统登录示例

## 项目结构

```
jiudianvue/
├── backend/          # SpringBoot后端
├── frontend/         # Vue2前端
└── database/         # 数据库脚本
```

## 技术栈

### 前端
- Vue 2.6.14
- Vue Router 3.5.3
- Element UI 2.15.13
- Axios 0.27.2

### 后端
- Spring Boot 2.7.14
- Spring Data JPA
- MySQL 8.0

## 快速开始

### 1. 数据库配置

首先需要在MySQL中执行数据库初始化脚本：

```bash
mysql -u root -p < database/init.sql
```

或者在MySQL命令行中执行：
```sql
source E:/jiudianvue/database/init.sql
```

这将创建 `hotel_db` 数据库和 `users` 表，并插入测试数据。

**测试账号：**
- 用户名：admin  密码：123456
- 用户名：test   密码：123456

### 2. 启动后端服务

```bash
cd backend
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

### 3. 启动前端服务

```bash
cd frontend
npm install
npm run dev
```

前端服务将在 http://localhost:8081 启动

## 使用说明

1. 访问 http://localhost:8081
2. 使用测试账号登录（admin/123456 或 test/123456）
3. 登录成功后将跳转到主页面
4. 主页面显示欢迎信息和用户信息

## 功能说明

- 用户登录验证
- 页面路由跳转
- 用户信息展示
- 退出登录

## 注意事项

- 确保MySQL服务已启动
- 数据库用户名：root，密码：123456
- 后端端口：8080
- 前端端口：8081
