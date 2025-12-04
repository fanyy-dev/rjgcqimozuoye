<template>
  <div class="user-container">
    <div class="page-header">
      <h2>用户管理</h2>
      <el-button type="primary" @click="showAddUser">添加用户</el-button>
    </div>
    
    <el-card>
      <el-table :data="userList" style="width: 100%" stripe>
        <el-table-column prop="id" label="ID" width="60"></el-table-column>
        <el-table-column prop="username" label="用户名" width="120"></el-table-column>
        <el-table-column prop="name" label="姓名" width="120"></el-table-column>
        <el-table-column prop="roomType" label="房间类型" width="120"></el-table-column>
        <el-table-column prop="bedType" label="床型" width="100"></el-table-column>
        <el-table-column prop="roomCount" label="房间数量" width="100"></el-table-column>
        <el-table-column prop="checkInDate" label="入住日期" width="130"></el-table-column>
        <el-table-column prop="checkOutDate" label="退房日期" width="130"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="editUser(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteUser(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 编辑/添加用户对话框 -->
    <el-dialog 
      :title="isEdit ? '编辑用户' : '添加用户'" 
      :visible.sync="dialogVisible"
      width="550px"
    >
      <el-form :model="userForm" label-width="100px" ref="userForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" :disabled="isEdit"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="userForm.name"></el-input>
        </el-form-item>
        <el-form-item label="房间类型" prop="roomType">
          <el-select v-model="userForm.roomType">
            <el-option label="单人间" value="单人间"></el-option>
            <el-option label="双人间" value="双人间"></el-option>
            <el-option label="豪华间" value="豪华间"></el-option>
            <el-option label="套房" value="套房"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="床型" prop="bedType">
          <el-input v-model="userForm.bedType"></el-input>
        </el-form-item>
        <el-form-item label="房间数量" prop="roomCount">
          <el-input-number v-model="userForm.roomCount" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="入住日期" prop="checkInDate">
          <el-date-picker v-model="userForm.checkInDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="退房日期" prop="checkOutDate">
          <el-date-picker v-model="userForm.checkOutDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="userForm.remark" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'UserManagement',
  data() {
    return {
      userList: [
        { id: 1, username: '201', name: '房主人', roomType: '单人间', bedType: '单床', roomCount: 1, checkInDate: '2025-11-20', checkOutDate: '2025-11-22', remark: '居住在我们酒店，已预订预订WiFi预订预订' },
        { id: 2, username: '202', name: '房主人', roomType: '双人间', bedType: '大床', roomCount: 1, checkInDate: '2025-11-24', checkOutDate: '2025-11-28', remark: '居住在我们酒店，已预订预订WiFi预订预订' },
        { id: 3, username: '203', name: '房主人', roomType: '豪华间', bedType: '大床', roomCount: 1, checkInDate: '2025-11-29', checkOutDate: '2025-11-30', remark: '居住在我们酒店，已预订预订WiFi预订预订' },
        { id: 4, username: '204', name: '房主人', roomType: '双人间', bedType: '双床', roomCount: 1, checkInDate: '2025-12-01', checkOutDate: '2025-12-05', remark: '居住在我们酒店，已预订预订WiFi预订预订' },
        { id: 5, username: '205', name: '房主人', roomType: '套房', bedType: '大床+沙发床', roomCount: 2, checkInDate: '2025-12-06', checkOutDate: '2025-12-10', remark: '居住在我们酒店，已预订预订WiFi预订预订' },
        { id: 6, username: '301', name: '房主人', roomType: '双人间', bedType: '双床', roomCount: 1, checkInDate: '2025-12-11', checkOutDate: '2025-12-15', remark: '居住在我们酒店，已预订预订WiFi预订预订' }
      ],
      dialogVisible: false,
      isEdit: false,
      userForm: {
        id: '',
        username: '',
        name: '',
        roomType: '',
        bedType: '',
        roomCount: 1,
        checkInDate: '',
        checkOutDate: '',
        remark: ''
      }
    }
  },
  methods: {
    showAddUser() {
      this.isEdit = false
      const newId = Math.max(...this.userList.map(u => u.id), 0) + 1
      this.userForm = {
        id: newId,
        username: '',
        name: '',
        roomType: '',
        bedType: '',
        roomCount: 1,
        checkInDate: '',
        checkOutDate: '',
        remark: ''
      }
      this.dialogVisible = true
    },
    editUser(user) {
      this.isEdit = true
      this.userForm = { ...user }
      this.dialogVisible = true
    },
    saveUser() {
      if (!this.userForm.username) {
        this.$message.error('用户名不能为空')
        return
      }
      if (this.isEdit) {
        const index = this.userList.findIndex(u => u.id === this.userForm.id)
        if (index > -1) {
          this.$set(this.userList, index, this.userForm)
        }
        this.$message.success('用户更新成功')
      } else {
        this.userList.push(this.userForm)
        this.$message.success('用户添加成功')
      }
      this.dialogVisible = false
    },
    deleteUser(user) {
      this.$confirm('确定删除该用户吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.userList.findIndex(u => u.id === user.id)
        if (index > -1) {
          this.userList.splice(index, 1)
        }
        this.$message.success('用户删除成功')
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.user-container {
  padding: 20px;
  background: #f0f2f5;
  min-height: 100vh;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  background: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.page-header h2 {
  margin: 0;
  color: #333;
}
</style>
