<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">酒店服务系统</h2>
      
      <!-- 登录表单 -->
      <el-form v-if="!isRegister" :model="loginForm" :rules="loginRules" ref="loginForm" class="login-form">
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username" 
            placeholder="请输入用户名"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password" 
            type="password"
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
            @keyup.enter.native="handleLogin"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleLogin" 
            :loading="loading"
            class="login-button"
          >登录</el-button>
        </el-form-item>
        <div class="register-link">
          <span>还没有账号？</span>
          <el-button type="text" @click="toggleForm">立即注册</el-button>
        </div>
      </el-form>
      
      <!-- 注册表单 -->
      <el-form v-else :model="registerForm" :rules="registerRules" ref="registerForm" class="login-form">
        <el-form-item prop="username">
          <el-input 
            v-model="registerForm.username" 
            placeholder="请输入用户名"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            v-model="registerForm.password" 
            type="password"
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input 
            v-model="registerForm.confirmPassword" 
            type="password"
            placeholder="请确认密码"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input 
            v-model="registerForm.name" 
            placeholder="请输入姓名（可选）"
            prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input 
            v-model="registerForm.phone" 
            placeholder="请输入电话（可选）"
            prefix-icon="el-icon-phone"
            @keyup.enter.native="handleRegister"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleRegister" 
            :loading="loading"
            class="login-button"
          >注册</el-button>
        </el-form-item>
        <div class="register-link">
          <span>已有账号？</span>
          <el-button type="text" @click="toggleForm">返回登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.registerForm.confirmPassword !== '') {
          this.$refs.registerForm.validateField('confirmPassword')
        }
        callback()
      }
    }
    const validateConfirmPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }
    
    return {
      isRegister: false,
      loginForm: {
        username: '',
        password: ''
      },
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        name: '',
        phone: ''
      },
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
      registerRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '用户名长度3-20个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' },
          { min: 6, max: 20, message: '密码长度6-20个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: validateConfirmPass, trigger: 'blur' }
        ]
      },
      loading: false
    }
  },
  methods: {
    toggleForm() {
      this.isRegister = !this.isRegister
      // 清空表单
      if (this.isRegister) {
        this.$refs.loginForm && this.$refs.loginForm.resetFields()
      } else {
        this.$refs.registerForm && this.$refs.registerForm.resetFields()
      }
    },
    
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true
          this.$axios.post('/api/login', this.loginForm)
            .then(response => {
              this.loading = false
              if (response.data.code === 200) {
                this.$message.success(response.data.message)
                // 保存用户信息
                localStorage.setItem('user', JSON.stringify(response.data.data))
                // 跳转到仪表板
                this.$router.push('/admin/dashboard')
              } else {
                this.$message.error(response.data.message)
              }
            })
            .catch(error => {
              this.loading = false
              this.$message.error('登录失败，请检查网络连接')
              console.error('登录错误:', error)
            })
        }
      })
    },
    
    handleRegister() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          this.loading = true
          const registerData = {
            username: this.registerForm.username,
            password: this.registerForm.password,
            name: this.registerForm.name,
            phone: this.registerForm.phone
          }
          
          this.$axios.post('/api/register', registerData)
            .then(response => {
              this.loading = false
              if (response.data.code === 200) {
                this.$message.success(response.data.message)
                // 保存用户信息
                localStorage.setItem('user', JSON.stringify(response.data.data))
                // 注册成功后跳转到仪表板
                this.$router.push('/admin/dashboard')
              } else {
                this.$message.error(response.data.message)
              }
            })
            .catch(error => {
              this.loading = false
              this.$message.error('注册失败，请检查网络连接')
              console.error('注册错误:', error)
            })
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-size: 28px;
  font-weight: bold;
}

.login-form {
  margin-top: 20px;
}

.login-button {
  width: 100%;
  height: 45px;
  font-size: 16px;
  letter-spacing: 2px;
}

.register-link {
  text-align: center;
  margin-top: 15px;
  color: #666;
  font-size: 14px;
}

.register-link span {
  margin-right: 5px;
}
</style>
