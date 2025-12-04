<template>
  <div class="room-container">
    <div class="page-header">
      <h2>房间管理</h2>
      <el-button type="primary" @click="showAddRoom">添加房间</el-button>
    </div>
    
    <el-card>
      <el-table :data="roomList" style="width: 100%" stripe>
        <el-table-column prop="roomNumber" label="房间号" width="100"></el-table-column>
        <el-table-column prop="roomType" label="房间类型" width="120"></el-table-column>
        <el-table-column prop="bedType" label="床型" width="100"></el-table-column>
        <el-table-column prop="capacity" label="容纳人数" width="100"></el-table-column>
        <el-table-column prop="price" label="价格/晚" width="100"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag 
              :type="scope.row.status === '空闲' ? 'success' : scope.row.status === '已入住' ? 'danger' : 'warning'"
            >
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="floor" label="楼层" width="80"></el-table-column>
        <el-table-column prop="remarks" label="备注"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="editRoom(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteRoom(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 编辑/添加房间对话框 -->
    <el-dialog 
      :title="isEdit ? '编辑房间' : '添加房间'" 
      :visible.sync="dialogVisible"
      width="500px"
    >
      <el-form :model="roomForm" label-width="100px" ref="roomForm">
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="roomForm.roomNumber"></el-input>
        </el-form-item>
        <el-form-item label="房间类型" prop="roomType">
          <el-select v-model="roomForm.roomType">
            <el-option label="单人间" value="单人间"></el-option>
            <el-option label="双人间" value="双人间"></el-option>
            <el-option label="豪华间" value="豪华间"></el-option>
            <el-option label="套房" value="套房"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="床型" prop="bedType">
          <el-input v-model="roomForm.bedType"></el-input>
        </el-form-item>
        <el-form-item label="容纳人数" prop="capacity">
          <el-input-number v-model="roomForm.capacity" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="价格/晚" prop="price">
          <el-input-number v-model="roomForm.price" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="楼层" prop="floor">
          <el-input-number v-model="roomForm.floor" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="roomForm.remarks" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveRoom">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'RoomManagement',
  data() {
    return {
      roomList: [
        { roomNumber: '201', roomType: '双人间', bedType: '大床', capacity: 2, price: 580, status: '空闲', floor: 2, remarks: '靠窗房间' },
        { roomNumber: '202', roomType: '双人间', bedType: '大床', capacity: 2, price: 580, status: '已入住', floor: 2, remarks: '高楼层' },
        { roomNumber: '203', roomType: '豪华间', bedType: '大床', capacity: 2, price: 680, status: '维修中', floor: 2, remarks: '需维修' },
        { roomNumber: '204', roomType: '单人间', bedType: '单床', capacity: 1, price: 380, status: '空闲', floor: 2, remarks: '' },
        { roomNumber: '205', roomType: '套房', bedType: '大床+沙发床', capacity: 3, price: 980, status: '空闲', floor: 2, remarks: '豪华套房' }
      ],
      dialogVisible: false,
      isEdit: false,
      roomForm: {
        roomNumber: '',
        roomType: '',
        bedType: '',
        capacity: 1,
        price: 0,
        floor: 1,
        remarks: ''
      }
    }
  },
  methods: {
    showAddRoom() {
      this.isEdit = false
      this.roomForm = {
        roomNumber: '',
        roomType: '',
        bedType: '',
        capacity: 1,
        price: 0,
        floor: 1,
        remarks: ''
      }
      this.dialogVisible = true
    },
    editRoom(room) {
      this.isEdit = true
      this.roomForm = { ...room }
      this.dialogVisible = true
    },
    saveRoom() {
      if (!this.roomForm.roomNumber) {
        this.$message.error('房间号不能为空')
        return
      }
      if (this.isEdit) {
        const index = this.roomList.findIndex(r => r.roomNumber === this.roomForm.roomNumber)
        if (index > -1) {
          this.$set(this.roomList, index, this.roomForm)
        }
        this.$message.success('房间更新成功')
      } else {
        this.roomList.push(this.roomForm)
        this.$message.success('房间添加成功')
      }
      this.dialogVisible = false
    },
    deleteRoom(room) {
      this.$confirm('确定删除该房间吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.roomList.findIndex(r => r.roomNumber === room.roomNumber)
        if (index > -1) {
          this.roomList.splice(index, 1)
        }
        this.$message.success('房间删除成功')
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.room-container {
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
