# 酒店管理系统 - 前端项目

## 项目简介
这是一个基于Vue.js开发的酒店管理系统前端项目，提供了酒店预订、房间管理、订单管理、用户管理等功能。

## 技术栈
- Vue.js 2.6.14
- Element UI 2.15.13
- Vue Router 3.0.7
- Vuex 3.6.2
- Axios 1.3.5
- Dayjs 1.11.10
- Less 4.0.0

## 项目结构
```
├── public/              # 静态资源
├── src/                 # 源代码
│   ├── api/             # API接口定义
│   ├── assets/          # 资源文件
│   ├── router/          # 路由配置
│   ├── store/           # Vuex状态管理
│   ├── utils/           # 工具函数
│   ├── views/           # 页面组件
│   ├── App.vue          # 根组件
│   └── main.js          # 入口文件
├── .env.development     # 开发环境配置
├── babel.config.js      # Babel配置
├── jsconfig.json        # JavaScript配置
├── package.json         # 项目依赖
└── vue.config.js        # Vue CLI配置
```

## 安装与运行

### 安装依赖
```bash
npm install
```

### 开发环境运行
```bash
npm run dev
```

### 生产环境构建
```bash
npm run build
```

## 功能模块

### 前台功能
- 酒店首页展示
- 房间列表与详情
- 在线预订
- 个人订单管理
- 个人预约管理
- 用户信息管理
- 公告与消息查看

### 后台功能
- 用户管理
- 房间管理
- 订单管理
- 预约管理
- 分类管理
- 公告管理
- 消息管理

## 开发说明

### 接口配置
开发环境接口配置位于 `.env.development` 文件中，可根据实际情况修改API地址。

### 路由配置
路由配置位于 `src/router/index.js` 文件中，可根据需要添加或修改路由。

### API调用
所有API接口定义位于 `src/api/` 目录下，使用Axios进行HTTP请求。

## 构建与部署

### 构建
```bash
npm run build
```

构建完成后，生成的静态文件将位于 `dist/` 目录下。

### 部署
将 `dist/` 目录下的静态文件复制到后端项目的 `static/` 目录下，即可通过后端服务访问前端页面。

## 注意事项

1. 确保已安装Node.js环境（推荐版本：14.x或16.x）
2. 开发前请先运行 `npm install` 安装依赖
3. 构建前请确保已配置正确的API地址
4. 生产环境部署时请确保后端服务已正常运行
