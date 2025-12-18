<template>
  <div class="home">
    <!-- 顶部导航栏 -->
    <div class="navbar">
      <div class="navbar-left">
        <div class="logo-text flex items-center">
          <img src="@/assets/logo.png" height="30px"/>{{ $sysTitle }}后台
        </div>
      </div>
      <div class="navbar-right">
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            {{ user.name }}<i class="el-icon-caret-bottom el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-s-custom" @click.native="$router.push('/admin/adminInfo')">个人信息</el-dropdown-item>
            <el-dropdown-item icon="el-icon-caret-left" @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    
    <!-- 顶部功能栏 -->
    <div class="top-menu">
      <el-menu default-active="home" background-color="#8B4513" text-color="#fff"
        active-text-color="#FFA500" router mode="horizontal">
        <el-menu-item index="/admin">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item index="/admin/admin">
          <i class="el-icon-user"></i>
          <span slot="title">管理员管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/member">
          <i class="el-icon-user-solid"></i>
          <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/category">
          <i class="el-icon-s-promotion"></i>
          <span slot="title">房型管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/room">
          <i class="el-icon-s-cooperation"></i>
          <span slot="title">房间管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/appointment">
          <i class="el-icon-s-flag"></i>
          <span slot="title">预约管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/orders">
          <i class="el-icon-s-order"></i>
          <span slot="title">入住管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/message">
          <i class="el-icon-warning"></i>
          <span slot="title">留言管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/notice">
          <i class="el-icon-bell"></i>
          <span slot="title">公告管理</span>
        </el-menu-item>
      </el-menu>
    </div>
    
    <!-- 内容部分 -->
    <div class="section">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { mapState,mapMutations } from 'vuex'
import { removeToken } from '@/utils/auth'
export default {
  name: "Home",
  computed: {
    ...mapState(['user'])  // 映射 Vuex 中的 user 状态到当前组件的计算属性
  },
  data() {
    return {
    };
  },
  methods: {
    ...mapMutations(['setUser', 'setToken']),
    logout() {
      this.$confirm('确认退出登录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removeToken()  // 移除用户 token
        this.setUser({})  // 清空用户信息
        this.setToken('')  // 清空 token
        this.$router.replace('/front')  // 跳转到前台页面
      }).catch(() => { });
    }
  },
};
</script>

<style lang="less" scoped>
.home {
  .navbar {
    height: 60px;
    background-color: #FFA500;
    display: flex;
    align-items: center;
    padding: 0 20px;
    justify-content: space-between;
    border-bottom: 2px solid #8B4513;
    box-shadow: 0 2px 8px rgba(139, 69, 19, 0.2);

    .navbar-left {
      display: flex;
      align-items: center;
    }

    .logo-text {
      color: #8B4513;
      font-size: 18px;
      font-weight: bold;
      gap: 10px;
    }

    .navbar-right {
      min-width: 100px;
      height: 40px;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      cursor: pointer;
      color: #8B4513;
      font-weight: bold;
    }
  }

  .top-menu {
    width: 100%;
    background-color: #8B4513;
    border-bottom: 2px solid #8B4513;
    box-shadow: 0 2px 8px rgba(139, 69, 19, 0.2);
    
    .el-menu {
      background-color: #8B4513;
      border-right: none;
      border-bottom: none;
      
      .el-menu-item {
        color: white;
        
        &:hover {
          background-color: rgba(255, 255, 255, 0.1);
        }
        
        &.is-active {
          background-color: #FFA500;
          color: #8B4513;
        }
      }
    }
  }

  .section {
    padding: 16px;
    margin-top: 0;
  }
}
</style>
