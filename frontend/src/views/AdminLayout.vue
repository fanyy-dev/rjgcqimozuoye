<template>
  <div class="admin-layout">
    <!-- 侧边栏 -->
    <el-menu 
      :collapse="isCollapse"
      default-active="dashboard"
      class="sidebar-menu"
      @select="handleMenuSelect"
      background-color="#2c3e50"
      text-color="#fff"
      active-text-color="#409EFF"
    >
      <div class="menu-header">
        <span v-if="!isCollapse" class="logo-text">🏨 酒店系统</span>
        <span v-else class="logo-icon">🏨</span>
      </div>
      
      <el-menu-item index="dashboard">
        <i class="el-icon-s-home"></i>
        <span slot="title">仪表板</span>
      </el-menu-item>
      
      <el-menu-item index="room">
        <i class="el-icon-s-unfold"></i>
        <span slot="title">房间管理</span>
      </el-menu-item>
      
      <el-menu-item index="order">
        <i class="el-icon-document"></i>
        <span slot="title">预订管理</span>
      </el-menu-item>
      
      <el-menu-item index="user">
        <i class="el-icon-user"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
      
      <el-divider style="margin: 10px 0;"></el-divider>
      
      <el-menu-item index="logout" @click="handleLogout">
        <i class="el-icon-switch-button"></i>
        <span slot="title">退出登录</span>
      </el-menu-item>
    </el-menu>
    
    <!-- 主内容区域 -->
    <div class="main-content">
      <!-- 顶部导航 -->
      <div class="top-navbar">
        <el-button 
          icon="el-icon-s-fold" 
          @click="isCollapse = !isCollapse"
          class="collapse-btn"
        ></el-button>
        <div class="navbar-right">
          <span class="user-name">{{ user.name || user.username }}</span>
          <i class="el-icon-user-solid"></i>
        </div>
      </div>
      
      <!-- 页面内容 -->
      <div class="content-area">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminLayout',
  data() {
    return {
      isCollapse: false,
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
    handleMenuSelect(index) {
      if (index === 'dashboard') {
        this.$router.push('/dashboard')
      } else if (index === 'room') {
        this.$router.push('/room')
      } else if (index === 'order') {
        this.$router.push('/order')
      } else if (index === 'user') {
        this.$router.push('/user')
      }
    },
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
.admin-layout {
  display: flex;
  height: 100vh;
  background: #f0f2f5;
}

.sidebar-menu {
  width: 200px;
  min-height: 100vh;
  border: none;
  transition: width 0.3s;
}

.sidebar-menu.el-menu--collapse {
  width: 64px;
}

.menu-header {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 60px;
  color: white;
  font-size: 18px;
  font-weight: bold;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  padding: 0 20px;
}

.logo-text {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.logo-icon {
  font-size: 28px;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.top-navbar {
  height: 60px;
  background: white;
  display: flex;
  align-items: center;
  padding: 0 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  justify-content: space-between;
}

.collapse-btn {
  font-size: 18px;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #666;
}

.user-name {
  font-size: 14px;
}

.content-area {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .sidebar-menu {
    position: absolute;
    left: -200px;
    top: 0;
    height: 100vh;
    z-index: 100;
    transition: left 0.3s;
  }
  
  .sidebar-menu.show {
    left: 0;
  }
}
</style>
