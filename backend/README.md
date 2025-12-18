# 酒店管理系统 - 后端项目

## 项目简介
这是一个基于Spring Boot开发的酒店管理系统后端项目，提供了酒店预订、房间管理、订单管理、用户管理等功能的API接口。

## 技术栈
- Java 21
- Spring Boot 3.1.4
- MyBatis Plus 3.5.5
- MySQL 8.0
- Druid 1.2.20
- JWT 4.4.0
- Jakarta Servlet 6.0.0

## 项目结构
```
├── src/                     # 源代码
│   ├── main/                # 主代码目录
│   │   ├── java/            # Java源代码
│   │   │   └── com/         # 主包名
│   │   │       └── hotely5d/ # 项目包名
│   │   │           ├── config/     # 配置类
│   │   │           ├── controller/  # 控制器
│   │   │           ├── dao/         # 数据访问层
│   │   │           ├── entity/      # 实体类
│   │   │           ├── interceptor/ # 拦截器
│   │   │           ├── service/     # 服务层
│   │   │           ├── utils/       # 工具类
│   │   │           └── Application.java # 入口类
│   │   └── resources/       # 资源文件
│   │       ├── mapper/      # MyBatis映射文件
│   │       ├── static/      # 静态资源
│   │       └── application.yml # 配置文件
│   └── test/                # 测试代码
├── pom.xml                  # Maven配置文件
└── README.md                # 项目说明文档
```

## 安装与运行

### 环境要求
- JDK 21+
- Maven 3.6+
- MySQL 8.0+

### 数据库配置
1. 创建数据库：`hotely5d`
2. 执行数据库脚本：`database/hotely5d.sql`
3. 在`application.yml`中配置数据库连接信息

### 运行项目

#### 使用Maven运行
```bash
mvn spring-boot:run
```

#### 打包运行
```bash
# 打包
mvn clean package

# 运行jar文件
java -jar target/hotely5d-1.0-SNAPSHOT.jar
```

## 主要功能模块

### 1. 用户管理
- 用户登录/注册
- 用户信息管理
- 权限控制

### 2. 房间管理
- 房间类型管理
- 房间信息管理
- 房间状态管理

### 3. 订单管理
- 订单创建
- 订单查询
- 订单状态更新
- 订单统计

### 4. 预约管理
- 预约创建
- 预约查询
- 预约状态更新

### 5. 系统管理
- 公告管理
- 消息管理
- 文件上传

## API接口

### 认证相关
- `POST /api/system/login` - 用户登录
- `POST /api/system/register` - 用户注册

### 房间相关
- `GET /api/room/list` - 获取房间列表
- `GET /api/room/detail/{id}` - 获取房间详情
- `POST /api/room/add` - 添加房间
- `PUT /api/room/update` - 更新房间信息
- `DELETE /api/room/delete/{id}` - 删除房间

### 订单相关
- `GET /api/orders/list` - 获取订单列表
- `GET /api/orders/detail/{id}` - 获取订单详情
- `POST /api/orders/add` - 创建订单
- `PUT /api/orders/update` - 更新订单
- `DELETE /api/orders/delete/{id}` - 删除订单

### 预约相关
- `GET /api/appointment/list` - 获取预约列表
- `POST /api/appointment/add` - 创建预约
- `PUT /api/appointment/update` - 更新预约

### 用户相关
- `GET /api/admin/list` - 获取管理员列表
- `GET /api/member/list` - 获取会员列表

## 配置说明

### 主要配置文件
- `application.yml` - 主配置文件

### 配置项说明

#### 服务器配置
```yaml
server:
  port: 8080
  servlet:
    context-path: /api
```

#### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hotely5d?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
```

#### MyBatis Plus配置
```yaml
mybatis-plus:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.hotely5d.entity
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

## 开发说明

### 代码规范
- 遵循Spring Boot编码规范
- 类名使用驼峰命名法，首字母大写
- 方法名和变量名使用驼峰命名法，首字母小写
- 包名使用小写字母

### 开发流程
1. 创建实体类
2. 创建Mapper接口
3. 创建Mapper XML文件
4. 创建Service接口和实现类
5. 创建Controller
6. 测试接口

## 部署说明

### 开发环境
1. 启动MySQL服务
2. 导入数据库脚本
3. 运行后端服务
4. 运行前端项目

### 生产环境
1. 打包前端项目
2. 将前端打包后的静态资源复制到后端`static`目录
3. 打包后端项目
4. 部署jar文件到服务器
5. 配置Nginx反向代理（可选）

## 注意事项

1. 确保使用Java 21或更高版本
2. 确保MySQL服务正常运行
3. 确保数据库连接信息配置正确
4. 开发环境和生产环境配置需要分开管理
5. 定期备份数据库
