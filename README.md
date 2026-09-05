# 校园论坛系统

基于 Spring Boot 3 + Vue 3 的前后端分离校园论坛，提供用户交流、内容管理、文件上传和校园知识问答能力。

## 主要功能

- 用户注册、登录、邮箱验证码、密码重置、个人资料与隐私设置
- 帖子发布、分类、全文搜索、评论、点赞、收藏、置顶、锁定、屏蔽和违禁词检测
- 管理端用户、帖子、帖子分类及邮件记录管理
- 基于 Elasticsearch 的帖子检索与高亮
- 基于 Spring AI 的校园知识库问答，支持 SSE 流式聊天
- RabbitMQ 异步邮件发送、失败消息持久化与管理员重发
- MinIO 图片对象存储、Redis 验证码/缓存/限流
- Spring Security + JWT 认证、管理员路由保护和统一 JSON 响应

## 技术栈

### 后端

- Java 17
- Spring Boot 3.5.8、Spring Web、Spring Validation
- Spring Security、Auth0 java-jwt 4.3.0
- MyBatis-Plus 3.5.15、MySQL Connector/J
- Spring Data Redis、Spring Data Elasticsearch
- Spring AMQP（RabbitMQ）、Spring Mail
- MinIO Java SDK、Spring AI DeepSeek（BOM 1.1.2）
- SpringDoc OpenAPI 2.1.0、Lombok、FastJSON2
- Logback、Maven

### 前端

- Vue 3.3、Vue Router 4、Pinia 4
- Vite 4、Axios 1.4
- Element Plus 2.11、Element Plus Icons
- VueUse、VueQuill、Markdown-It、Less
- unplugin-auto-import、unplugin-vue-components

### 基础设施

- MySQL 8.0
- Redis 7
- RabbitMQ 3（含 Management 控制台）
- MinIO（对象存储）
- Elasticsearch（帖子搜索，需单独准备服务）
- SMTP 邮件服务、和风天气 API、AI 模型服务（按需配置）

## 系统架构

请求链路如下：

~~~text
Vue 页面 -> Axios -> Spring Security/JWT 过滤器 -> Controller -> Service -> Mapper/Repository -> MySQL、Redis、Elasticsearch、RabbitMQ、MinIO
~~~

前端默认访问 http://localhost:8080，开发服务器默认由 Vite 使用 http://localhost:5173。

## 项目结构

~~~text
campus-forum-springboot-vue/
├── README.md
├── CODE_WIKI.md
├── database.sql                 # MySQL 初始化脚本
├── infra/
│   └── docker-compose.yml       # MySQL、Redis、RabbitMQ、MinIO
├── my-project-backend/
│   ├── pom.xml                 # Maven 依赖与 dev/prod profile
│   └── src/main/
│       ├── java/com/example/
│       │   ├── config/          # Security、Web、Swagger、RabbitMQ、MinIO 等配置
│       │   ├── controller/      # HTTP 接口
│       │   ├── controller/admin/ # 管理端接口
│       │   ├── entity/          # DTO、请求 VO、响应 VO、统一响应
│       │   ├── filter/          # JWT、日志、CORS、限流过滤器
│       │   ├── listener/        # RabbitMQ 消费者
│       │   ├── mapper/          # MyBatis-Plus Mapper
│       │   ├── repository/      # Elasticsearch Repository
│       │   ├── service/         # 业务接口与实现
│       │   └── utils/            # JWT、缓存、限流、雪花 ID 等工具
│       └── resources/
│           ├── application.yml
│           ├── application-dev.yml
│           ├── application-prod.yml
│           ├── es/http_ca.crt
│           └── logback-spring.xml
└── my-project-frontend/
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── components/          # 编辑器、天气、互动等复用组件
        ├── net/                 # Axios 与 API 封装
        ├── router/              # 路由与登录守卫
        ├── store/               # Pinia 状态
        └── views/               # 登录、论坛、管理、设置页面
~~~

## 环境要求

- JDK 17 或更高版本
- Maven 3.9+
- Node.js 18 LTS 或更高版本、npm 9+
- Docker Desktop（包含 Docker Compose v2）
- 可用端口：3306、6379、5672、15672、9000、9001、8080、5173
- Elasticsearch 服务（开发配置默认连接 localhost:9200）
- 可用 SMTP 邮箱、和风天气 API Key；AI 功能还需要可用的模型服务地址和密钥

## 环境配置

### 1. 配置 Compose 环境变量

在仓库根目录创建 .env（不要提交真实密钥）：

~~~dotenv
MYSQL_ROOT_PASSWORD=your_mysql_password
MYSQL_DATABASE=study1
RABBITMQ_DEFAULT_USER=admin
RABBITMQ_DEFAULT_PASS=your_rabbitmq_password
MINIO_ROOT_USER=minioadmin
MINIO_ROOT_PASSWORD=your_minio_password
~~~

### 2. 配置后端 profile

开发环境默认使用 dev profile。编辑 my-project-backend/src/main/resources/application-dev.yml，至少确认：

- spring.datasource：MySQL 地址、数据库名、用户名和密码
- spring.rabbitmq：RabbitMQ 地址、账号、密码和 virtual host
- spring.elasticsearch：Elasticsearch URI、用户名、密码
- spring.mail：SMTP 主机、邮箱账号和授权码
- spring.minio：MinIO endpoint、账号和密码
- spring.weather.key：和风天气 API Key
- spring.ai.deepseek：AI 模型服务的 api-key 与 base-url
- spring.security.jwt.key：仅保存在本地配置中的 JWT 签名密钥

生产环境使用 application-prod.yml，可通过 Maven profile 切换。生产配置默认监听 80 端口并关闭 OpenAPI 文档。

### 3. 初始化数据库与 MinIO

Compose 会自动挂载根目录的 database.sql。当前脚本主要包含账号、资料、隐私、图片和帖子分类等基础表；完整论坛功能上线前，请确认数据库已补齐当前版本源码所需的帖子、评论、互动、通知和邮件记录表。

启动后打开 MinIO 控制台 http://localhost:9001，创建名为 study 的 bucket。后端不会自动创建该 bucket。

## 快速启动

~~~bash
# 1. 启动基础设施
docker compose -f infra/docker-compose.yml up -d

# 2. 启动后端（开发环境，默认 dev profile）
cd my-project-backend
mvn spring-boot:run

# 3. 启动前端（新终端）
cd my-project-frontend
npm install
npm run dev
~~~

访问：

- 前端：http://localhost:5173
- 后端：http://localhost:8080
- Swagger UI（开发环境）：http://localhost:8080/swagger-ui/index.html
- RabbitMQ 管理台：http://localhost:15672
- MinIO 控制台：http://localhost:9001

## 生产构建

~~~bash
cd my-project-backend
mvn clean package -Pprod
java -jar target/my-project-backend-0.0.1-SNAPSHOT.jar

cd my-project-frontend
npm run build
~~~

前端生产产物位于 my-project-frontend/dist，需要由 Nginx 等静态服务器托管，并为 Vue History 路由配置回退到 index.html。

## 注意事项

- 不要将 .env、邮箱授权码、JWT 密钥、天气 API Key 或 AI API Key 提交到 Git。
- 前端后端地址当前写在 my-project-frontend/src/main.js；后端端口变化时需要同步修改。
- Elasticsearch 不在当前 Compose 文件中，需要单独启动并与 application-dev.yml 保持一致。

