# JinkAccess 权限后台系统

## 项目简介
JinkAccess 是一个基于 Spring Boot + Vue3 + JWT + Spring Security 的权限后台系统，支持：
- 用户注册、登录（JWT）
- 角色权限控制（用户 / 管理员）
- 管理员管理用户
- 前后端分离，前端基于 Vue3 + element-plus

## 项目结构
- src → Spring Boot 后端源码  
- frontend → Vue3 + Vite + element-plus 前端  

---

## 后端说明

### 技术栈
- Spring Boot 3.5.5
- Spring Security 6
- JWT
- MySQL + JPA (Hibernate)

### 功能
- 用户注册（加密存储密码）
- 登录获取 JWT
- JWT 拦截与校验
- 管理员接口（获取用户列表、测试接口）
- CORS 配置（支持前端 5173 访问）

### 启动
```bash
mvn spring-boot:run
默认地址：http://localhost:8080

接口示例
POST /api/user/register

POST /api/user/login

GET /api/user/info

GET /api/admin/users

GET /api/admin/hello

前端说明
技术栈
Vue 3

Vite

element-plus

axios

vue-router

功能
登录页：账号密码登录，保存 JWT

用户首页：显示当前用户信息

管理员页面：查看所有用户

启动
bash

cd frontend
npm install
npm run dev
默认地址：http://localhost:5173