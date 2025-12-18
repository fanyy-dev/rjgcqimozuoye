<template>
    <div class="page-container">
        <div class="section">
            <div class="section-title">
                预约入住
            </div>
            <el-form :inline="true" :model="formInline" class="mt-20">
                <el-form-item label="入住日期">
                    <el-date-picker :clearable="false" :picker-options="pickerOptions" v-model="formInline.startDate"
                        type="date" placeholder="入住日期" value-format="yyyy-MM-dd" format="yyyy-MM-dd">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="房型">
                    <el-select v-model="formInline.categoryId" placeholder="房型" clearable>
                        <el-option v-for="item in categoryList" :label="item.categoryName" :value="item.id"
                            :key="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="formInline.canUse" placeholder="状态" clearable>
                        <el-option label="可预订" :value="true"></el-option>
                        <el-option label="已满房" :value="false"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">查询</el-button>
                </el-form-item>
            </el-form>
            <div class="section-list">

                <div v-for="(item, index) in roomList" :key="item.id" class="section-item card-hover fade-in"
                    :class="`delay-${(index % 5) * 100}`"
                    @click="$router.push(`/front/roomDetails/${item.id}`)">
                    <div class="img-container">
                        <img :src="$baseFileUrl + item.category.photo" class="hotel-img">
                    </div>
                    <div class="hotel-info">
                        <div class="hotel-title">{{ item.category.categoryName }}-{{ item.roomNum }}</div>
                        <div class="hotel-details">
                            {{ item.canUse ? '随时可入住' : '当前已满房' }}
                        </div>
                        <div class="hotel-features">
                            <span class="feature-tag">入住人数: {{ item.category.live_num }}</span>
                            <span class="feature-tag">床位数: {{ item.category.bed_num }}</span>
                            <span class="feature-tag">面积: {{ item.category.area }}㎡</span>
                        </div>
                        <div class="hotel-footer">
                            <el-tag type="success" v-if="item.canUse">可预订</el-tag>
                            <el-tag type="danger" v-else>已满房</el-tag>
                            <div class="hotel-price">￥{{ item.category.price }}</div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
import { findAllCategoryAPI } from '@/api/category'
import { findRoomListAPI } from '@/api/room'
import dayjs from 'dayjs'
export default {
    name: 'FrontCategory',

    data() {
        return {
            pickerOptions: {
                disabledDate: this.disabledDate
            },
            formInline: {
                startDate: dayjs().format('YYYY-MM-DD'),
                categoryId: null,
                canUse: null
            },
            categoryList: [],
            roomList: []
        };
    },

    async mounted() {
        // 从路由中获取分类ID，若存在则设置到表单中
        if (this.$route.query?.categoryId) {
            this.formInline.categoryId = parseInt(this.$route.query?.categoryId)
        }
        // 获取分类列表和客房列表
        this.getCategoryList()
        this.getList()
    },

    methods: {
        disabledDate(time) {
            //禁用今天之前的日期
            return dayjs(time).isBefore(dayjs(), 'day')
        },
        // 获取分类列表
        async getCategoryList() {
            const { data } = await findAllCategoryAPI()
            this.categoryList = data
        },
        // 获取客房列表
        async getList() {
            const { data } = await findRoomListAPI(this.formInline)
            this.roomList = data;
        },
        // 表单提交时重新获取客房列表
        onSubmit() {
            this.getList()
        }
    },
};
</script>

<style lang="less" scoped>
.page-container {
    width: 1200px;
    margin: 0 auto;
    padding: 20px 0;

    .section {
        .section-title {
            background: linear-gradient(135deg, #ec4899 0%, #8b5cf6 100%);
            color: white;
            padding: 24px;
            border-radius: 16px;
            text-align: center;
            font-size: 26px;
            font-weight: 800;
            margin-bottom: 30px;
            box-shadow: 0 6px 18px rgba(236, 72, 153, 0.3);
            position: relative;
            overflow: hidden;
        }
        .section-title::before {
            content: '';
            position: absolute;
            top: -50%;
            left: -50%;
            width: 200%;
            height: 200%;
            background: linear-gradient(45deg, transparent, rgba(255, 255, 255, 0.1), transparent);
            transform: rotate(45deg);
            animation: shine 3s infinite;
        }
        @keyframes shine {
            0% { transform: translateX(-100%) translateY(-100%) rotate(45deg); }
            100% { transform: translateX(100%) translateY(100%) rotate(45deg); }
        }

        .section-list {
            margin-bottom: 30px;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
            gap: 24px;
            padding: 0 10px;
        }

        .section-item {
                background: white;
                border-radius: 20px;
                overflow: hidden;
                box-shadow: 0 8px 24px rgba(0, 0, 0, 0.06);
                transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
                cursor: pointer;
                height: 480px;
                display: flex;
                flex-direction: column;
                border: 1px solid #fce7f3;
                position: relative;

                &:hover {
                    transform: translateY(-12px) rotateX(5deg);
                    box-shadow: 0 24px 48px rgba(0, 0, 0, 0.12);
                    border-color: #ec4899;
                }

                &::before {
                    content: '';
                    position: absolute;
                    top: 0;
                    left: 0;
                    right: 0;
                    height: 4px;
                    background: linear-gradient(90deg, #ec4899, #db2777, #be185d);
                    transform: scaleX(0);
                    transition: transform 0.4s ease;
                }

                &:hover::before {
                    transform: scaleX(1);
                }

                .img-container {
                    overflow: hidden;
                    border-radius: 20px 20px 0 0;
                    height: 240px;
                    position: relative;
                }

                .hotel-img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                    border-radius: 20px 20px 0 0;
                    transition: transform 0.6s ease;
                }

                .img-container:hover .hotel-img {
                    transform: scale(1.1) rotate(2deg);
                }

                .hotel-info {
                    padding: 24px;
                    flex: 1;
                    display: flex;
                    flex-direction: column;
                }

                .hotel-title {
                    font-weight: 700;
                    font-size: 22px;
                    color: #0f172a;
                    margin: 0 0 16px 0;
                    text-align: center;
                    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
                }

                .hotel-details {
                    font-size: 15px;
                    color: #64748b;
                    margin-bottom: 20px;
                    text-align: center;
                    flex: 0;
                    font-weight: 500;
                }

                .hotel-features {
                    display: flex;
                    flex-wrap: wrap;
                    gap: 10px;
                    margin-bottom: 20px;
                    justify-content: center;
                }

                .feature-tag {
                    background: linear-gradient(135deg, #fce7f3, #fdf2f8);
                    color: #be185d;
                    padding: 6px 14px;
                    border-radius: 20px;
                    font-size: 13px;
                    font-weight: 600;
                    box-shadow: 0 2px 8px rgba(236, 72, 153, 0.15);
                }

                .hotel-footer {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    padding-top: 20px;
                    border-top: 2px solid #f0f9ff;
                    margin-top: auto;
                }

                .hotel-price {
                    background: linear-gradient(135deg, #f43f5e, #ef4444);
                    -webkit-background-clip: text;
                    background-clip: text;
                    color: transparent;
                    font-size: 28px;
                    font-weight: 800;
                    margin: 0;
                }
        }
    }
}

.el-tag {
    border-radius: 20px;
    padding: 4px 12px;
    font-size: 13px;
}

.el-form {
    background: linear-gradient(135deg, #fdf2f8 0%, #fce7f3 100%);
    padding: 32px;
    border-radius: 20px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
    margin-bottom: 32px;
    border: 2px solid #fbcfe8;
    position: relative;
    overflow: hidden;

    &::before {
        content: '';
        position: absolute;
        top: -50%;
        right: -50%;
        width: 100%;
        height: 200%;
        background: radial-gradient(circle, rgba(236, 72, 153, 0.1) 0%, transparent 70%);
        transform: rotate(30deg);
    }

    .el-form-item {
        position: relative;
        z-index: 1;
    }

    .el-button {
            background: linear-gradient(135deg, #ec4899 0%, #db2777 100%) !important;
            color: white !important;
            border: none !important;
            padding: 12px 32px !important;
            border-radius: 12px !important;
            font-size: 16px !important;
            font-weight: 700 !important;
            cursor: pointer !important;
            transition: all 0.4s ease !important;
            margin-top: 12px;
            box-shadow: 0 4px 12px rgba(236, 72, 153, 0.3) !important;
            text-transform: uppercase !important;
            letter-spacing: 0.5px !important;

            &:hover {
                opacity: 1 !important;
                transform: translateY(-3px) !important;
                box-shadow: 0 8px 20px rgba(236, 72, 153, 0.4) !important;
            }
        }

    .el-input__wrapper, .el-select__wrapper {
        border-radius: 12px !important;
        background: white !important;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08) !important;
        border: 2px solid #fce7f3 !important;
        transition: all 0.4s ease !important;

        &:hover, &.is-focus {
            border-color: #ec4899 !important;
            box-shadow: 0 4px 16px rgba(236, 72, 153, 0.2) !important;
        }
    }

    .el-date-editor .el-input__wrapper {
        width: 100% !important;
    }
}
</style>