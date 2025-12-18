<template>
    <!-- 页面容器 -->
    <div class="page-container">
        <!-- 卡片 -->
        <el-card>
            <!-- 卡片头部 -->
            <div slot="header" class="clearfix">
                <span>公告详情</span>
            </div>
            <h1 class="text-center">{{ notice.title }}</h1>
            <div class="text-gray text-center">{{ notice.createTime }}</div>
            <div class="content">
                {{ notice.content }}
            </div>
        </el-card>
    </div>
</template>

<script>
import { getNoticeByIdAPI } from '@/api/notice'
export default {
    name: 'FrontNoticeDetails',

    data() {
        return {
            notice: {}
        };
    },

    async mounted() {
        // 获取公告详情数据
        const {data:notice} = await getNoticeByIdAPI(this.$route.params.id)
        this.notice = notice
    },

    methods: {

    },
};
</script>

<style lang="less" scoped>
.page-container {
    width: 1200px;
    margin: 20px auto;
    .content{
        padding: 30px;
        line-height: 2;
        font-size: 16px;
        color: #333;
        background: linear-gradient(135deg, #fdf2f8 0%, #fce7f3 100%);
        border-radius: 12px;
        margin-top: 20px;
        border: 1px solid #fbcfe8;
    }
}

.el-card {
    border-radius: 20px;
    border: 2px solid #fbcfe8;
    box-shadow: 0 6px 20px rgba(236, 72, 153, 0.1);
    
    .el-card__header {
        background: linear-gradient(135deg, #ec4899 0%, #8b5cf6 100%);
        color: white;
        font-weight: bold;
        border-radius: 18px 18px 0 0;
    }
}

h1 {
    color: #be185d;
    font-size: 28px;
    margin: 20px 0;
    text-shadow: 0 2px 4px rgba(236, 72, 153, 0.1);
}

.text-gray {
    color: #999;
    font-size: 14px;
    margin-bottom: 20px;
}
</style>