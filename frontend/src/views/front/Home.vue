<template>
    <div class="page-container">
        <div class="carousel">
            <el-carousel height="300px">
                <el-carousel-item>
                    <img src="@/assets/introduction.jpg" class="carousel-img" />
                </el-carousel-item>
                <el-carousel-item>
                    <img src="@/assets/jiudian2.jpg" class="carousel-img" />
                </el-carousel-item>
                <el-carousel-item>
                    <img src="@/assets/houtai.jpg" class="carousel-img" />
                </el-carousel-item>
            </el-carousel>
        </div>
        
        <div class="section">
            <div class="section-title">
                客房展示
            </div>
            <div class="section-list">

                <div v-for="(item, index) in categoryList" :key="item.id" class="section-item card-hover fade-in"
                    :class="`delay-${(index % 5) * 100}`"
                    @click="$router.push(`/front/room?categoryId=${item.id}`)">
                    <!-- 房间类别图片 -->
                    <div class="img-container">
                        <img :src="$baseFileUrl+item.photo" class="hotel-img">
                    </div>
                    <!-- 房间类别信息 -->
                    <div class="hotel-info">
                        <!-- 房间类别名称 -->
                        <div class="hotel-title">{{ item.categoryName }}</div>
                        <!-- 房间类别价格 -->
                        <div class="hotel-price">￥{{ item.price }}</div>
                        <!-- 房间特点 -->
                        <div class="hotel-features">
                            <span class="feature-tag">入住人数: {{ item.live_num }}</span>
                            <span class="feature-tag">床位数: {{ item.bed_num }}</span>
                            <span class="feature-tag">面积: {{ item.area }}㎡</span>
                        </div>
                        <!-- 查看详情按钮 -->
                        <button class="hotel-button btn-primary">查看详情</button>
                    </div>
                </div>

            </div>
        </div>
        <div class="section">
            <div class="section-title">
                酒店简介
            </div>
            <div class="hotel-introduction">
                <div class="introduction">
                    上海云悦酒店集团旗下新创的一个酒店业品牌，第一家门店位于市中心黄金地段。
                    云悦酒店位于真正的上海市中心。从酒店到Xintiandi，外滩、陆家嘴、人民广场和豫园只有几分钟的步行路程。
                    有些人也可以步行去那里。这是复兴东路隧道和延安东路隧道。你可以在浦西和浦东之间穿梭。
                    分别只需要20分钟。距离虹桥机场和浦东机场40分钟车程。
                    在这里您尽可体验淮海路繁华商业与城隍庙传统文化的交织融汇，
                    百分百感受东方巴黎—上海的时代脉搏。酒店按照当下风靡世界的“BOUTIQUEHOTEL”风格设计，
                    以现代欧版为基调，所配置的设施与服务也力求时尚与快捷。
                    酒店仅以中等的价格让更多的客人享受到优越的位置、独特的空间、诱人的美食以及体贴的服务。

                </div>
                <div class="hotel-img">
                    <img src="@/assets/introduce.jpeg" />
                </div>
            </div>

        </div>
        <div class="section">
            <div class="section-title">
                最新公告
            </div>
            <el-table class="my-20" :data="noticeList" border fit highlight-current-row size="small">
                <el-table-column align="center" label="ID" prop="id"></el-table-column>
                <el-table-column align="center" label="标题" prop="title" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" label="发布时间" prop="createTime"></el-table-column>
                <el-table-column fixed="right" label="操作" width="100">
                    <template slot-scope="scope">
                        <el-button @click="$router.push(`/front/noticeDetails/${scope.row.id}`)" type="text" size="small">查看</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import { getTop5NoticeAPI } from '@/api/notice'
import {findAllCategoryAPI} from '@/api/category'
export default {
    name: 'Hotely5dWebHome',

    data() {
        return {
            // 公告列表数据
            noticeList: [],
            // 房间类别列表数据   
            categoryList: []
        };
    },

    async mounted() {
        // 组件挂载后执行的异步操作
        // 获取前5条公告数据
        const { data: noticeList } = await getTop5NoticeAPI()
        this.noticeList = noticeList
        // 获取所有房间类别数据
        const {data: categoryList} = await findAllCategoryAPI()
        this.categoryList = categoryList
    },

    methods: {

    },
};
</script>

<style lang="less" scoped>
.carousel {
    margin-bottom: 40px;
    border-radius: 24px;
    overflow: hidden;
    box-shadow: 0 16px 48px rgba(0, 0, 0, 0.15);
    border: 3px solid white;
    position: relative;

    .carousel-img {
        width: 100%;
        height: 500px;
        object-fit: cover;
        transition: transform 0.8s ease;
    }

    &:hover .carousel-img {
        transform: scale(1.05);
    }

    &::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: linear-gradient(to bottom, transparent 0%, rgba(0, 0, 0, 0.1) 100%);
        z-index: 1;
    }
}

.page-container {
    width: 1200px;
    margin: 0 auto;
    padding: 20px 0;

    .section {
        margin-bottom: 40px;

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
            height: 420px;
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
                height: 220px;
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
                justify-content: space-between;
            }

            .hotel-title {
                font-weight: 700;
                font-size: 22px;
                color: #0f172a;
                margin: 0 0 16px 0;
                text-align: center;
                text-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
            }

            .hotel-price {
                background: linear-gradient(135deg, #f43f5e, #ef4444);
                -webkit-background-clip: text;
                background-clip: text;
                color: transparent;
                font-size: 28px;
                font-weight: 800;
                margin: 0;
                text-align: center;
            }

            .hotel-features {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                margin: 16px 0;
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

            .hotel-button {
                background: linear-gradient(135deg, #ec4899, #db2777);
                color: white;
                border: none;
                padding: 14px;
                border-radius: 12px;
                font-size: 16px;
                font-weight: 700;
                cursor: pointer;
                transition: all 0.4s ease;
                margin-top: 16px;
                box-shadow: 0 4px 12px rgba(236, 72, 153, 0.3);
                text-transform: uppercase;
                letter-spacing: 0.5px;

                &:hover {
                    opacity: 1;
                    transform: translateY(-3px);
                    box-shadow: 0 8px 20px rgba(236, 72, 153, 0.4);
                }
            }
        }
    }

    .hotel-introduction {
        background: linear-gradient(135deg, #fdf2f8 0%, #fce7f3 100%);
        border-radius: 28px;
        padding: 48px;
        box-shadow: 0 12px 36px rgba(0, 0, 0, 0.08);
        display: flex;
        gap: 48px;
        align-items: center;
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

        .hotel-img {
            flex: 1;
            position: relative;
            z-index: 1;

            img {
                width: 100%;
                height: 450px;
                object-fit: cover;
                border-radius: 20px;
                box-shadow: 0 16px 40px rgba(0, 0, 0, 0.15);
                border: 4px solid white;
                transition: all 0.4s ease;
            }

            &:hover img {
                transform: scale(1.05) rotate(2deg);
                box-shadow: 0 24px 56px rgba(0, 0, 0, 0.2);
            }
        }

        .introduction {
            flex: 1;
            line-height: 2.0;
            color: #0f172a;
            font-size: 19px;
            font-weight: 500;
            position: relative;
            z-index: 1;
            background: white;
            padding: 32px;
            border-radius: 20px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
            border-left: 6px solid #ec4899;
        }
    }
}

.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
}

.el-carousel__item {
    background-color: #f8fafc;
}

.el-table {
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.el-table__header-wrapper {
    background: linear-gradient(135deg, #ec4899 0%, #8b5cf6 100%);
}

.el-table__header-wrapper th {
    color: white !important;
    font-weight: 600;
}
</style>