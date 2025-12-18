<template>
  <div class="app-container">
    <el-card class="box-table">
      <el-form :inline="true" class="demo-form-inline" :model="searchForm">
        <el-form-item label="房间号">
          <el-input placeholder="请输入房间号" v-model="searchForm.roomNum" autocomplete="off" clearable ></el-input>
        </el-form-item>
        <el-form-item label="房型">
          <el-select v-model="searchForm.categoryId" placeholder="请选择" clearable>
            <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择" clearable>
            <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row
        size="small">
        <el-table-column align="center" label="ID" prop="id"></el-table-column>
        <el-table-column align="center" label="房间号" prop="roomNum"></el-table-column>
        <el-table-column align="center" label="房型" prop="category.categoryName"></el-table-column>
        <el-table-column align="center" label="状态" prop="status">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status == 1">正常</el-tag>
            <el-tag type="danger" v-if="scope.row.status == 2">停用</el-tag>
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
        <el-form-item label="房间号">
          <el-input placeholder="请输入房间号" v-model="dialogForm.roomNum" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="房型">
          <el-select v-model="dialogForm.categoryId" placeholder="请选择">
            <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="dialogForm.status" placeholder="请选择">
            <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value">
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
  findRoomPageAPI,
  addRoomAPI,
  modifyRoomAPI,
  removeRoomAPI,
} from "@/api/room";
import { findAllCategoryAPI } from '@/api/category'
export default {
  data() {
    return {
      list: null,
      listLoading: true,
      pageSize: 10,
      pageNum: 1,
      total: 0,
      searchForm: {},
      dialogTitle: "添加",
      dialogFormVisible: false,
      dialogForm: {},
      categoryList: [],
      statusList: [
        { label: '正常', value: 1 },
        { label: '停用', value: 2 },
      ]
    };
  },
  created() {
    this.fetchData();
    this.getCategoryList()
  },
  methods: {
    async getCategoryList() {
      const { data } = await findAllCategoryAPI()
      this.categoryList = data
    },
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
      let response = await findRoomPageAPI(
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
      this.dialogForm = {
        categoryId: this.categoryList[0].id,
        status: this.statusList[0].value
      };
    },
    handleEdit(row) {//点击修改按钮-显示弹框
      this.dialogTitle = "修改";
      this.dialogFormVisible = true;
      this.dialogForm = { ...row };
    },
    async dialogConfirm() {//点击弹框确定按钮
      let res = null;
      if (this.dialogTitle == "新增") {
        res = await addRoomAPI(this.dialogForm);
      } else {
        res = await modifyRoomAPI(this.dialogForm);
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
      const res = await removeRoomAPI(row.id);
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
