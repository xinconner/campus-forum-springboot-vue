# 校园论坛系统

基于 Spring Boot 3 + Vue 3 的前后端分离校园论坛，提供用户交流、内容管理与校园知识问答能力。

## 主要功能

- 用户注册、登录、封禁、禁言及强制修改密码
- 帖子发布、分类、搜索、锁定、屏蔽、置顶和违禁词检测
- 管理端用户、帖子、帖子分类和邮件发送管理
- 校园知识库 AI 助手，支持基于 RAG 的问答与 SSE 流式聊天
- RabbitMQ 异步邮件处理及失败消息持久化、状态查询

## 技术亮点

- Spring Security + JWT 实现认证与管理端路由守卫
- Elasticsearch 提供高效帖子检索，Canal 增量同步 MySQL 数据
- Redis 用于验证码、缓存和接口限流
- MyBatis-Plus、RabbitMQ、MinIO、Spring AI 构成后端基础能力
- Vue 3、Vue Router、Pinia、Element Plus、Axios 构成前端应用

## 快速启动

```bash
# 启动 MySQL、Redis、RabbitMQ 和 MinIO
cd infra && docker compose up -d

# 启动后端（Java 17，需要 Maven）
cd ../my-project-backend && mvn spring-boot:run

# 启动前端
cd ../my-project-frontend && npm install && npm run dev
```

AI 功能需按后端配置填写模型服务参数；接口文档启动后可通过 `/swagger-ui.html` 访问。
