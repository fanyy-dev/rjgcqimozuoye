<template>
  <div class="home-container">
    <div class="header">
      <h1>酒店服务系统</h1>
      <div class="user-info">
        <span>欢迎，{{ user.name || user.username }}</span>
        <el-button type="text" @click="handleLogout">退出</el-button>
      </div>
    </div>
    <div class="content">
      <el-card class="welcome-card">
        <h2>欢迎使用酒店服务系统</h2>
        <p>这是系统主页面</p>
        <div class="info-section">
          <p><strong>用户名：</strong>{{ user.username }}</p>
          <p><strong>姓名：</strong>{{ user.name || '未设置' }}</p>
          <p><strong>电话：</strong>{{ user.phone || '未设置' }}</p>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      user: {}
    }
  },
  created() {
    // 获取用户信息
    const userStr = localStorage.getItem('user')
    if (userStr) {
      this.user = JSON.parse(userStr)
    } else {
      // 如果没有用户信息，跳转到登录页
      this.$router.push('/login')
    }
  },
  methods: {
    handleLogout() {
      this.$confirm('确定要退出登录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        localStorage.removeItem('user')
        this.$message.success('退出成功')
        this.$router.push('/login')
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.home-container {
  width: 100%;
  height: 100vh;
  background: #f0f2f5;
}

.header {
  height: 60px;
  background: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.header h1 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-info span {
  color: #666;
}

.content {
  padding: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.welcome-card {
  width: 600px;
  text-align: center;
}

.welcome-card h2 {
  color: #409EFF;
  margin-bottom: 20px;
}

.welcome-card p {
  font-size: 16px;
  color: #666;
  margin: 10px 0;
}

.info-section {
  margin-top: 30px;
  text-align: left;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 5px;
}

.info-section p {
  margin: 10px 0;
  font-size: 14px;
}
</style>
