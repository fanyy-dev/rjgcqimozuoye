<template>
  <div class="dashboard-container">
    <div class="dashboard-header">
      <h1>欢迎使用酒店管理系统</h1>
      <div class="user-info">
        <span>{{ user.name || user.username }}</span>
        <el-button type="text" @click="handleLogout">退出</el-button>
      </div>
    </div>
    
    <el-row :gutter="20" class="dashboard-content">
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-number">128</div>
          <div class="stat-label">总房间数</div>
          <div class="stat-icon">🏨</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-number">45</div>
          <div class="stat-label">已入住</div>
          <div class="stat-icon">👥</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-number">23</div>
          <div class="stat-label">待接待</div>
          <div class="stat-icon">📋</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-number">¥45,680</div>
          <div class="stat-label">今日收入</div>
          <div class="stat-icon">💰</div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-card class="banner-card">
      <img src="https://via.placeholder.com/800x300?text=Hotel+Lobby" alt="酒店大厅" class="banner-image">
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      user: {}
    }
  },
  created() {
    const userStr = localStorage.getItem('user')
    if (userStr) {
      this.user = JSON.parse(userStr)
    } else {
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
.dashboard-container {
  padding: 20px;
  background: #f0f2f5;
  min-height: 100vh;
}

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.dashboard-header h1 {
  margin: 0;
  color: #333;
  font-size: 24px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-info span {
  color: #666;
}

.dashboard-content {
  margin-bottom: 30px;
}

.stat-card {
  position: relative;
  overflow: hidden;
  min-height: 150px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 8px;
}

.stat-card:nth-child(2) {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-card:nth-child(3) {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-card:nth-child(4) {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 10px;
}

.stat-label {
  font-size: 14px;
  opacity: 0.9;
}

.stat-icon {
  position: absolute;
  top: 10px;
  right: 15px;
  font-size: 40px;
  opacity: 0.2;
}

.banner-card {
  border: none;
  border-radius: 8px;
  overflow: hidden;
}

.banner-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 8px;
}
</style>
