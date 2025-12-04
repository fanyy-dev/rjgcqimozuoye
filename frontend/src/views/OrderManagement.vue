<template>
  <div class="order-container">
    <div class="page-header">
      <h2>预订管理</h2>
      <el-button type="primary" @click="showAddOrder">添加预订</el-button>
    </div>
    
    <el-card>
      <el-table :data="orderList" style="width: 100%" stripe>
        <el-table-column prop="orderId" label="预订号" width="120"></el-table-column>
        <el-table-column prop="roomNumber" label="房间号" width="100"></el-table-column>
        <el-table-column prop="guestName" label="客户名" width="120"></el-table-column>
        <el-table-column prop="checkInDate" label="入住日期" width="130"></el-table-column>
        <el-table-column prop="checkOutDate" label="退房日期" width="130"></el-table-column>
        <el-table-column prop="nights" label="天数" width="80"></el-table-column>
        <el-table-column prop="totalPrice" label="总价" width="100"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag 
              :type="scope.row.status === '已入住' ? 'danger' : scope.row.status === '已预订' ? 'success' : 'info'"
            >
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="editOrder(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteOrder(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 编辑/添加预订对话框 -->
    <el-dialog 
      :title="isEdit ? '编辑预订' : '添加预订'" 
      :visible.sync="dialogVisible"
      width="550px"
    >
      <el-form :model="orderForm" label-width="100px" ref="orderForm">
        <el-form-item label="预订号" prop="orderId">
          <el-input v-model="orderForm.orderId" :disabled="isEdit"></el-input>
        </el-form-item>
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="orderForm.roomNumber"></el-input>
        </el-form-item>
        <el-form-item label="客户名" prop="guestName">
          <el-input v-model="orderForm.guestName"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="orderForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="入住日期" prop="checkInDate">
          <el-date-picker v-model="orderForm.checkInDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="退房日期" prop="checkOutDate">
          <el-date-picker v-model="orderForm.checkOutDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="天数" prop="nights">
          <el-input-number v-model="orderForm.nights" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="总价" prop="totalPrice">
          <el-input-number v-model="orderForm.totalPrice" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="orderForm.status">
            <el-option label="已预订" value="已预订"></el-option>
            <el-option label="已入住" value="已入住"></el-option>
            <el-option label="已退房" value="已退房"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveOrder">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'OrderManagement',
  data() {
    return {
      orderList: [
        { orderId: '19', roomNumber: '13', guestName: '王小明', checkInDate: '2025-11-20', checkOutDate: '2025-11-22', nights: 2, totalPrice: 1160, status: '已入住', phone: '13800138000' },
        { orderId: '22', roomNumber: '13', guestName: '李四', checkInDate: '2025-11-24', checkOutDate: '2025-11-28', nights: 4, totalPrice: 2320, status: '已预订', phone: '13900139000' },
        { orderId: '24', roomNumber: '13', guestName: '张三', checkInDate: '2025-11-29', checkOutDate: '2025-11-30', nights: 1, totalPrice: 580, status: '已预订', phone: '18612345678' }
      ],
      dialogVisible: false,
      isEdit: false,
      orderForm: {
        orderId: '',
        roomNumber: '',
        guestName: '',
        phone: '',
        checkInDate: '',
        checkOutDate: '',
        nights: 1,
        totalPrice: 0,
        status: '已预订'
      }
    }
  },
  methods: {
    showAddOrder() {
      this.isEdit = false
      this.orderForm = {
        orderId: '',
        roomNumber: '',
        guestName: '',
        phone: '',
        checkInDate: '',
        checkOutDate: '',
        nights: 1,
        totalPrice: 0,
        status: '已预订'
      }
      this.dialogVisible = true
    },
    editOrder(order) {
      this.isEdit = true
      this.orderForm = { ...order }
      this.dialogVisible = true
    },
    saveOrder() {
      if (!this.orderForm.orderId) {
        this.$message.error('预订号不能为空')
        return
      }
      if (this.isEdit) {
        const index = this.orderList.findIndex(o => o.orderId === this.orderForm.orderId)
        if (index > -1) {
          this.$set(this.orderList, index, this.orderForm)
        }
        this.$message.success('预订更新成功')
      } else {
        this.orderList.push(this.orderForm)
        this.$message.success('预订添加成功')
      }
      this.dialogVisible = false
    },
    deleteOrder(order) {
      this.$confirm('确定删除该预订吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.orderList.findIndex(o => o.orderId === order.orderId)
        if (index > -1) {
          this.orderList.splice(index, 1)
        }
        this.$message.success('预订删除成功')
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.order-container {
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
