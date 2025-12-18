<template>
    <div class="page-container">
        <el-card>
            <div slot="header" class="clearfix">
                <span>系统公告</span>
            </div>
            <el-form :inline="true" class="demo-form-inline" :model="searchForm">
                <el-form-item label="公告标题">
                    <el-input placeholder="请输入公告标题" v-model="searchForm.title"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleSearch">查询</el-button>
                </el-form-item>
            </el-form>
            <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row
                size="small">
                <el-table-column align="center" label="ID" prop="id"></el-table-column>
                <el-table-column align="center" label="标题" prop="title" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" label="发布时间" prop="createTime"></el-table-column>
                <el-table-column fixed="right" label="操作" width="100">
                    <template slot-scope="scope">
                        <el-button @click="$router.push(`/front/noticeDetails/${scope.row.id}`)" type="text" size="small">查看</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页器 -->
            <el-pagination class="page-box" background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="pageNum" :page-sizes="[10, 20]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
import {
    findNoticePageAPI,
} from "@/api/notice";

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
            let response = await findNoticePageAPI(
                this.pageNum,
                this.pageSize,
                this.searchForm
            );
            this.list = response.data.records;
            this.total = response.data.total;
            this.listLoading = false;
        },
    },
};
</script>

<style lang="less" scoped>
.page-container {
    width: 1200px;
    margin: 20px auto;
}

.el-card {
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    border: 2px solid #fbcfe8;
}

.el-card .el-card__header {
    background: linear-gradient(135deg, #ec4899 0%, #8b5cf6 100%);
    color: white;
    border-bottom: none;
    font-size: 20px;
    font-weight: 700;
}

.el-button {
    background: linear-gradient(135deg, #ec4899 0%, #db2777 100%) !important;
    color: white !important;
    border: none !important;
    box-shadow: 0 4px 12px rgba(236, 72, 153, 0.3) !important;
    
    &:hover {
        box-shadow: 0 8px 20px rgba(236, 72, 153, 0.4) !important;
    }
}

.el-input__wrapper {
    border: 2px solid #fce7f3 !important;
    border-radius: 12px !important;
    
    &:hover, &.is-focus {
        border-color: #ec4899 !important;
        box-shadow: 0 4px 12px rgba(236, 72, 153, 0.2) !important;
    }
}

.el-table {
    border-radius: 12px;
    overflow: hidden;
    
    .el-table__header-wrapper {
        background: linear-gradient(135deg, #ec4899 0%, #8b5cf6 100%);
    }
    
    .el-table__header-wrapper th {
        color: white !important;
        font-weight: 600;
    }
}

.el-pagination {
    .el-pagination__button {
        color: #ec4899;
    }
    
    .el-pagination__button--current {
        background: linear-gradient(135deg, #ec4899 0%, #db2777 100%);
        color: white;
        border: none;
    }
}
</style>