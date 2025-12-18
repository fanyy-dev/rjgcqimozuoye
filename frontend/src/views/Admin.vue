<template>
  <div class="app-container">
    <el-card class="box-table">
      <el-form :inline="true" class="demo-form-inline" :model="searchForm">
        <el-form-item label="账号">
          <el-input placeholder="请输入账号" v-model="searchForm.username"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input placeholder="请输入姓名" v-model="searchForm.name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row
        size="small">
        <el-table-column align="center" label="ID" prop="id"></el-table-column>
        <el-table-column align="center" label="账号" prop="username"></el-table-column>
        <el-table-column align="center" label="姓名" prop="name"></el-table-column>
        <el-table-column align="center" label="性别" prop="gender">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.gender == 1">男</el-tag>
            <el-tag type="danger" v-else>女</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="deleteCofirm(scope.row)">
              <el-button type="text" slot="reference" size="small">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination class="page-box" background @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="pageNum" :page-sizes="[10, 20]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>
    <!-- 添加/修改的弹框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form label-position="left" :model="dialogForm" label-width="80px">
        <el-form-item label="账号">
          <el-input placeholder="请输入账号" v-model="dialogForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" placeholder="请输入密码" v-model="dialogForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input placeholder="请输入姓名" v-model="dialogForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="dialogForm.gender" placeholder="请选择">
            <el-option v-for="item in genderOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  findAdminPageAPI, // 导入查询管理员信息的 API
  addAdminAPI, // 导入新增管理员的 API
  modifyAdminAPI, // 导入修改管理员信息的 API
  removeAdminAPI, // 导入删除管理员的 API
} from "@/api/admin";

export default {
  data() {
    return {
      list: null, // 管理员列表数据
      listLoading: true, // 列表加载状态
      pageSize: 10, // 每页显示条数
      pageNum: 1, // 当前页码
      total: 0, // 总条数
      searchForm: {}, // 搜索表单数据
      dialogTitle: "添加", // 弹框标题，默认为添加
      dialogFormVisible: false, // 弹框显示状态
      dialogForm: {}, // 弹框表单数据
      genderOptions: [ // 性别选项
        {
          label: '男',
          value: 1
        },
        {
          label: '女',
          value: 2
        }
      ],
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    handleSizeChange(val) {//每页显示条数改变
      this.pageSize = val;
      this.fetchData();
    },
    handleCurrentChange(val) {//当前页码改变
      this.pageNum = val;
      this.fetchData();
    },
    handleSearch() {//点击搜索按钮
      this.fetchData();
    },
    async fetchData() {//查询数据
      this.listLoading = true;
      let response = await findAdminPageAPI(
        this.pageNum,
        this.pageSize,
        this.searchForm
      );
      this.list = response.data.records;
      this.total = response.data.total;
      this.listLoading = false;
    },
    handleAdd() {//点击新增按钮-显示弹框
      this.dialogTitle = "新增";
      this.dialogFormVisible = true;
      this.dialogForm = {gender: 1};
    },
    handleEdit(row) {//点击修改按钮-显示弹框
      this.dialogTitle = "修改";
      this.dialogFormVisible = true;
      this.dialogForm = { ...row };
    },
    async dialogConfirm() {//点击弹框确定按钮
      let res = null;
      if (this.dialogTitle == "新增") {
        res = await addAdminAPI(this.dialogForm);
      } else {
        res = await modifyAdminAPI(this.dialogForm);
      }
      if (res.flag) {
        this.dialogFormVisible = false;
      }
      this.$message({
        message: res.message,
        type: res.flag ? "success" : "error",
      });
      this.fetchData();
    },
    async deleteCofirm(row) {//点击确定删除按钮
      this.pageNum = 1;
      const res = await removeAdminAPI(row.id);
      this.$message({
        message: res.message,
        type: res.flag ? "success" : "error",
      });
      this.fetchData();
    },
  },
};
</script>

<style lang="less" scoped>
.page-box {
  margin-top: 20px;
}

.app-container {
  background-color: #FFF8DC;
  min-height: calc(100vh - 92px);
}

.box-table {
  border-radius: 10px;
  border: 2px solid #D2B48C;
  box-shadow: 0 4px 12px rgba(139, 69, 19, 0.15);
  margin-bottom: 20px;
}

.el-form {
  background-color: #FFF8DC;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  border: 1px solid #D2B48C;
}

.el-input__wrapper,
.el-select .el-input__wrapper {
  border: 2px solid #D2B48C !important;
  border-radius: 8px !important;
  
  &:hover {
    border-color: #FFA500 !important;
  }
}

.el-button {
  &[type="primary"] {
    background-color: #FFA500 !important;
    border-color: #FFA500 !important;
    color: #8B4513 !important;
    font-weight: bold;
    
    &:hover {
      background-color: #FF8C00 !important;
      border-color: #FF8C00 !important;
    }
  }
  
  &:not([type="primary"]) {
    background-color: #D2B48C !important;
    border-color: #D2B48C !important;
    color: #8B4513 !important;
    
    &:hover {
      background-color: #CD853F !important;
      border-color: #CD853F !important;
    }
  }
  
  &[type="text"] {
    background-color: transparent !important;
    border: none !important;
    color: #8B4513 !important;
    
    &:hover {
      background-color: rgba(139, 69, 19, 0.1) !important;
    }
  }
}

.el-table {
  border-radius: 8px;
  overflow: hidden;
  
  .el-table__header-wrapper {
    background-color: #8B4513;
    
    th {
      color: white !important;
      font-weight: bold;
      border-bottom: none;
    }
  }
  
  .el-table__body-wrapper {
    border: 1px solid #D2B48C;
    border-top: none;
    
    td {
      border-bottom: 1px solid #D2B48C;
      color: #8B4513;
    }
  }
  
  .el-table__row:hover {
    background-color: rgba(255, 165, 0, 0.1);
  }
}

.el-pagination {
  .el-pagination__sizes .el-input .el-input__wrapper {
    border-color: #D2B48C !important;
  }
  
  .el-pagination__button {
    color: #8B4513;
    
    &:hover {
      color: #FFA500;
    }
  }
  
  .el-pager li {
    color: #8B4513;
    
    &.active {
      background-color: #FFA500;
      color: #8B4513;
      font-weight: bold;
    }
    
    &:hover {
      color: #FFA500;
    }
  }
}

.el-dialog {
  border-radius: 10px;
  
  .el-dialog__header {
    background-color: #8B4513;
    color: white;
    border-radius: 10px 10px 0 0;
  }
  
  .el-dialog__title {
    color: white !important;
    font-weight: bold;
  }
  
  .el-dialog__body {
    background-color: #FFF8DC;
  }
}

.el-tag {
  &[type="success"] {
    background-color: #FFA500;
    border-color: #FFA500;
    color: #8B4513;
  }
  
  &[type="danger"] {
    background-color: #8B4513;
    border-color: #8B4513;
    color: white;
  }
}
</style>
