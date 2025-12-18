# 酒店管理系统 - 部署说明

## 项目结构
```
├── backend/      # 后端项目
├── frontend/     # 前端项目
├── database/     # 数据库文件
└── docs/         # 文档
```

## 部署步骤

### 1. 环境准备
- JDK 21+
- Maven 3.6+
- Node.js 14.x或16.x
- MySQL 8.0+

### 2. 数据库部署

#### 2.1 创建数据库
```sql
CREATE DATABASE hotely5d DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

#### 2.2 导入数据库脚本
```bash
mysql -u root -p hotely5d < database/hotely5d.sql
```

#### 2.3 配置数据库连接
编辑`backend/src/main/resources/application.yml`文件，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hotely5d?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
    username: root
    password: 123456
```

### 3. 后端部署

#### 3.1 打包后端项目
```bash
cd backend
mvn clean package
```

#### 3.2 运行后端服务
```bash
java -jar target/hotely5d-1.0-SNAPSHOT.jar
```

或者使用Maven直接运行：
```bash
mvn spring-boot:run
```

后端服务默认运行在`http://localhost:8080`。

### 4. 前端部署

#### 4.1 安装前端依赖
```bash
cd frontend
npm install
```

#### 4.2 构建前端项目
```bash
npm run build
```

构建完成后，生成的静态文件将位于`frontend/dist/`目录下。

#### 4.3 部署前端静态资源
将前端构建后的静态资源复制到后端的`static`目录：

```bash
xcopy frontend\dist backend\src\main\resources\static /E /I /Y
```

这样，前端页面将通过后端服务访问：`http://localhost:8080`。

### 5. 独立部署前端（可选）

如果需要将前端独立部署，可以使用Nginx等Web服务器：

#### 5.1 安装Nginx
下载并安装Nginx：https://nginx.org/

#### 5.2 配置Nginx
编辑Nginx配置文件`nginx.conf`：

```nginx
server {
    listen       80;
    server_name  localhost;

    location / {
        root   /path/to/frontend/dist;
        index  index.html index.htm;
        try_files $uri $uri/ /index.html;
    }

    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

#### 5.3 启动Nginx
```bash
nginx -s start
```

前端页面将通过`http://localhost`访问，API请求将代理到后端服务。

## 常见问题

### 1. 端口占用
如果端口8080被占用，可以修改后端配置文件中的端口：

```yaml
server:
  port: 8081
```

或者使用命令行终止占用端口的进程：

```bash
# 查找占用8080端口的进程
netstat -ano | findstr :8080

# 终止进程
 taskkill /F /PID <进程ID>
```

### 2. 数据库连接失败
- 检查数据库服务是否正常运行
- 检查数据库用户名和密码是否正确
- 检查数据库名称是否正确
- 检查数据库连接URL是否正确

### 3. 前端页面无法访问
- 检查后端服务是否正常运行
- 检查前端静态资源是否已正确复制到后端的`static`目录
- 检查浏览器控制台是否有错误信息

### 4. API请求失败
- 检查前端配置的API地址是否正确
- 检查后端服务是否正常运行
- 检查跨域配置是否正确

## 升级说明

### 1. 后端升级
1. 备份数据库
2. 备份当前后端代码
3. 替换新的后端代码
4. 检查数据库是否需要迁移
5. 重新构建并运行后端服务

### 2. 前端升级
1. 备份当前前端代码
2. 替换新的前端代码
3. 重新安装依赖并构建
4. 将新的静态资源复制到后端

## 维护说明

### 1. 日志管理
后端日志默认输出到控制台，可以在配置文件中配置日志文件：

```yaml
logging:
  file:
    name: logs/hotely5d.log
```

### 2. 性能监控
可以使用Spring Boot Actuator进行性能监控，在`pom.xml`中添加依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### 3. 安全配置
- 生产环境中请修改默认的管理员密码
- 生产环境中请启用HTTPS
- 定期备份数据库
- 限制数据库用户的权限

## 联系方式

如有问题，请联系系统管理员。