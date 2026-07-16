# AI智能食材管理系统 - 部署文档

## 一、环境要求

### 1.1 服务器配置
| 配置项 | 最低要求 | 推荐配置 |
|--------|---------|---------|
| CPU | 2核 | 4核 |
| 内存 | 4GB | 8GB |
| 磁盘 | 40GB | 100GB |
| 操作系统 | CentOS 7+ / Ubuntu 20+ | Ubuntu 22.04 |

### 1.2 软件依赖
| 软件 | 版本 | 用途 |
|------|------|------|
| JDK | 17+ | 运行后端服务 |
| Maven | 3.8+ | 构建后端项目 |
| MySQL | 8.0+ | 数据库 |
| Node.js | 18+ | 构建前端项目 |
| Nginx | 1.20+ | 反向代理 |

## 二、后端部署

### 2.1 数据库初始化
登录MySQL后执行：source /path/to/sql/init.sql

### 2.2 后端配置
编辑 backend/src/main/resources/application.yml，配置数据库连接、Coze API Key、Bot ID。

### 2.3 构建和运行
`ash
cd backend
mvn clean package -DskipTests
java -jar target/ai-ingredient-manager-1.0.0.jar
`

### 2.4 API文档
启动后访问：http://localhost:8080/swagger-ui/index.html

## 三、Web前端部署

### 3.1 构建
`ash
cd web-frontend
npm install
npm run build
# 产物在 dist/ 目录
`

### 3.2 Nginx配置
将 dist/ 目录部署到Nginx，并配置/api/反向代理到后端8080端口。

## 四、微信小程序部署

### 4.1 配置
修改 mini-program/utils/api.js 中的 baseUrl 为公网API地址。

### 4.2 发布流程
1. 微信开发者工具导入 mini-program 目录
2. 配置AppID
3. 上传提交体验版
4. 管理后台提交审核

## 五、Coze平台配置

### 5.1 创建AI机器人
1. 登录 https://www.coze.cn
2. 创建Bot，设置人设为食材管理专家
3. 发布后获取Bot ID

### 5.2 获取API Key
在Coze开发者中心创建API Key，配置到application.yml。

## 六、公网访问
- 配置域名解析
- 配置SSL证书（HTTPS）
- 开放80/443端口
- 可选：使用Docker容器化部署
