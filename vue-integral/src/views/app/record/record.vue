<template>
    <div class="container">
        <ul class="record-list valid-record-list panel">
            <li v-for="(item,index) in recordsItems">
                <a href="javascript:void(0)" @click="toLink(item.orderState,item.oid)" class="record-item flex media-box">
                    <div class="img-wrap media-box__hd">
                        <img
                            :src="imageWebServer + item.orderGoods.orderGoodsIconPhoto.path + item.orderGoods.orderGoodsIconPhoto.name">
                    </div>
                    <div class="info-wrap side-auto media-box__bd">
                        <p class="record-title media-box__title" v-text="item.orderGoods.orderGoodsName"></p>
                        <p class="record-status media-box__desc">
                            <span v-if="10 == item.orderState">待付款</span>
                            <span v-if="20 == item.orderState">待审核</span>
                            <span v-if="30 == item.orderState">待发货</span>
                            <span v-if="40 == item.orderState">已发货</span>
                            <span v-if="50 == item.orderState">已完成</span>
                            <span v-if="60 == item.orderState || 80 == item.orderState">兑换失败</span>
                            <span v-if="70 == item.orderState">已取消</span>
                        </p>
                    </div>
                    <div class="media-box__ft">
                        <i class="iconfont">&#xe612;</i>
                    </div>
                </a>
            </li>
        </ul>
        <div class="nothing-wrap">
        </div>
        <div class="more-wrap">
        </div>
        <div class="no-more">已经没有更多了！</div>
        <transition name="app-loading">
            <app-loading v-show="loading" style="z-index: 1;"></app-loading>
        </transition>
    </div>
</template>

<script>
    import {getRecords} from '@/service/getAppData';
    import config from '@/config/config';

    import loading from '@/components/app/loading';

    export default {
        data() {
            return {
                imageWebServer: config.ossImgPath,
                loading: true,
                recordsItems: [],
                items: []

            }
        },
        methods: {
            getRecords: function () {
                getRecords().then(res => {
                    this.recordsItems = res.data.data.data.data;
                    this.loading = false;
                })
            },
            toLink (state,order_id){
                const link = 10 == state ? 'ambPay' : 'recordDetail';
                this.$router.push({name:link, params: {order_id: order_id}});
            }
        },
        created() {
            this.getRecords();
        },
        components: {
            'app-loading': loading
        },
    }

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .app-loading-enter-active, .app-loading-leave-active {
        transition: opacity .3s
    }

    .app-loading-enter, .app-loading-leave-active {
        opacity: 0
    }
    .container {
        .record-list {
            .img-wrap {
                width: 2.08rem;
                height: 1.28rem;
                margin-right: 0.2rem;
                img {
                    width: 100%;
                    height: 100%;
                }
            }
            .info-wrap {
                .record-title {
                    display: -webkit-box;
                    -webkit-line-clamp: 2;
                    -webkit-box-orient: vertical;
                    margin-bottom: 0.18rem;
                    line-height: 1.4;
                    white-space: normal;
                }
            }
        }
        .no-more {
            text-align: center;
        }
    }
</style>
