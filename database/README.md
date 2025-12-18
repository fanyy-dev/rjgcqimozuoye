# 酒店管理系统 - 数据库说明

## 数据库文件
- `hotely5d.sql` - 完整的数据库结构和初始数据脚本

## 数据库配置

### 环境要求
- MySQL 8.0+

### 导入数据库

#### 方法一：使用MySQL命令行
1. 登录MySQL：
```bash
mysql -u root -p
```

2. 创建数据库：
```sql
CREATE DATABASE hotely5d DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

3. 选择数据库：
```sql
USE hotely5d;
```

4. 导入SQL脚本：
```sql
SOURCE /path/to/database/hotely5d.sql;
```

#### 方法二：使用图形化工具（如Navicat、MySQL Workbench）
1. 创建数据库：`hotely5d`
2. 右键点击数据库，选择"运行SQL文件"
3. 选择`hotely5d.sql`文件并执行

## 数据库结构

### 主要表结构

#### 1. admin（管理员表）
- `id` - 主键ID
- `username` - 账号
- `password` - 密码
- `name` - 姓名
- `gender` - 性别

#### 2. appointment（预约表）
- `id` - 主键ID
- `member_id` - 用户ID
- `room_id` - 房间ID
- `start_time` - 入住时间
- `days` - 居住天数
- `status` - 状态
- `remark` - 备注
- `money` - 已付金额

#### 3. category（房型表）
- `id` - 主键ID
- `category_name` - 房间类型
- `photo` - 房间图片
- `price` - 价格
- `live_num` - 可住人数
- `bed_num` - 床位数
- `area` - 面积
- `introduce` - 介绍

#### 4. member（会员表）
- `id` - 主键ID
- `username` - 账号
- `password` - 密码
- `name` - 姓名
- `gender` - 性别
- `phone` - 手机
- `idcard` - 身份证
- `head` - 头像
- `create_time` - 注册时间

#### 5. message（消息表）
- `id` - 主键ID
- `member_id` - 用户ID
- `content` - 内容
- `create_time` - 创建时间

#### 6. notice（公告表）
- `id` - 主键ID
- `title` - 标题
- `content` - 内容
- `create_time` - 创建时间

#### 7. orders（订单表）
- `id` - 主键ID
- `member_id` - 用户ID
- `room_id` - 房间ID
- `start_time` - 入住时间
- `end_time` - 退房时间
- `days` - 居住天数
- `status` - 状态
- `total_price` - 总价

#### 8. room（房间表）
- `id` - 主键ID
- `category_id` - 房型ID
- `number` - 房间号
- `status` - 状态

## 初始数据

### 管理员账号
- 账号：admin
- 密码：123456

### 测试数据
- 包含了一些测试用的房间、房型、订单和预约数据
- 包含了测试用的会员账号

## 注意事项

1. 确保MySQL版本为8.0或以上
2. 导入前请确保已创建数据库
3. 导入后请检查数据完整性
4. 在生产环境中，请修改默认的管理员密码
5. 根据实际需求调整数据库配置（如字符集、存储引擎等）