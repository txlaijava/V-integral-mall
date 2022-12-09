<template>
    <div class="container">
        <div class="object-order-page">
            <section class="panel">
                <div class="media-box media-box_appmsg order-status">
                    <div class="order-status__text" v-if="10 == orderDetails.orderState">
                        <i class="iconfont icon_waitship-status">&#xe6fc;</i><span class="t-333">待付款</span>
                    </div>
                    <div class="order-status__text" v-if="20 == orderDetails.orderState">
                        <i class="iconfont icon_waitship-status">&#xe6fc;</i><span class="t-333">待审核</span>
                    </div>
                    <div class="order-status__text" v-if="30 == orderDetails.orderState">
                        <i class="iconfont icon_waitship-status">&#xe6fc;</i><span class="t-333">待发货</span>
                    </div>
                    <div class="order-status__text" v-if="40 == orderDetails.orderState">
                        <i class="iconfont icon_waitship-status">&#xe6fc;</i><span class="t-333">已发货</span>
                    </div>
                    <div class="order-status__text" v-if="50 == orderDetails.orderState">
                        <i class="iconfont icon_waitship-status">&#xe6fc;</i><span class="t-333">已完成</span>
                    </div>
                    <div class="order-status__text"
                         v-if="60 == orderDetails.orderState || 80 == orderDetails.orderState">
                        <i class="iconfont icon_waitship-status">&#xe6fc;</i><span class="t-333">兑换失败</span>
                    </div>
                    <div class="order-status__text" v-if="70 == orderDetails.orderState">
                        <i class="iconfont icon_waitship-status">&#xe6fc;</i><span class="t-333">已取消</span>
                    </div>
                </div>
            </section>
        </div>
        <section class="panel delivery-info" v-if="orderDetails.orderGoods.goods.goodsType == 1">
            <div class="media-box media-box_appmsg flex flex--aligncenter delivery-info__consignee"
                 v-if="orderDetails.orderStateType == 1">
                <div class="iconfont icon_location">&#xe9090;</div>
                <div class="flex__item">
                    <p class="delivery-info_consignee-name">
                        <span class="t-333">收货人：{{orderDetails.addrUserName}} &emsp;</span>
                        <span class="t-333">{{orderDetails.addrTelephone}}</span>
                    </p>
                    <p class="delivery-info_consignee-address t-666">
                        {{orderDetails.addrAreaInfo}}
                    </p>
                </div>
            </div>
            <div class="media-box media-box_appmsg flex flex--aligncenter delivery-info__consignee" v-else>
                <div class="iconfont icon_location">&#xe9090;</div>
                <div class="flex__item">
                    <p class="t-666">
                        自提地点:{{orderDetails.orderGoods.goods.takeSelfAddr}}
                    </p>
                </div>
            </div>
        </section>
        <section class="panel delivery-info" v-if="orderDetails.orderStateType == 2 && 30 == orderDetails.orderState">
            <van-collapse v-model="activeNames">
                <van-collapse-item :title="'核销码：'+orderDetails.verifyCode" name="1">
                    <div id="qrcode"></div>
                </van-collapse-item>
            </van-collapse>
        </section>
        <section class="object-info goods-info panel">
            <div class="flex flex--aligncenter media-box media-box_appmsg">
                <div class="goods-info__img  media-box__hd">
                    <img
                        :src="imageWebServer + orderDetails.orderGoods.orderGoodsIconPhoto.path + orderDetails.orderGoods.orderGoodsIconPhoto.name"
                        v-if="orderDetails.orderGoods.orderGoodsIconPhoto.path">
                </div>
                <div class="flex__item media-box__bd">
                    <div class="goods-info__title">
                        {{orderDetails.orderGoods.orderGoodsName}}
                    </div>
                </div>
            </div>
            <div class="media-box media-box_appmsg order-info">
                <div class="panel__bd">
                    <p class="flex t-666">支付金额：<span class="t-red">{{orderDetails.orderTotalIntegralPrice}}积分</span></p>
                    <p class="t-666">订单编号：{{orderDetails.oid}}</p>
                    <p class="t-666">下单时间：{{orderDetails.addTime}}</p>
                </div>
            </div>
        </section>
        <section class="footer footer--noboxshadow" v-if="orderDetails.bizId && 10 == orderDetails.orderState">
            <div class="footer__bd">
                <button class="app_btn btn--primary btn--lg" @click="cancel">
                    取消订单
                </button>
            </div>
        </section>

        <div class="float">
            <a class="backhome-float float__item t-center" href="#/app/index/home">
                <i class="iconfont icon__home t-333">&#xe6cb;</i>
            </a>
        </div>
        <transition name="app-loading">
            <app-loading v-show="loading" style="z-index: 1;"></app-loading>
        </transition>
    </div>
</template>

<script>
    import QRCode from 'qrcodejs2'
    import {getOrderDetails, postCancelOrder} from '@/service/getAppData';
    import config from '@/config/config';
    import loading from '@/components/app/loading';

    export default {
        data() {
            return {
                imageWebServer: config.ossImgPath,
                orderId: this.$route.params.order_id,
                loading: true,
                orderDetails: {orderGoods: {orderGoodsIconPhoto: {path: '', name: ''}, goods: {}}},
                activeNames: ['1']
            }
        },
        methods: {
            getOrderDetail: function () {
                getOrderDetails({orderId: this.orderId}).then(res => {
                    this.orderDetails = res.data.data.data;
                    this.loading = false;
                    if (this.orderDetails.orderStateType == 2 && 30 == this.orderDetails.orderState) {
                        this.$nextTick(function () {
                            this.qrcode();
                        })
                    }
                });
            },
            qrcode() {
                new QRCode("qrcode", {
                    width: 200,
                    height: 200, // 高度
                    text: config.appPath + "/index/verifyDetail?acApplyId=" + this.orderDetails.acApply.id + "&verifyCode=" + this.orderDetails.verifyCode
                });
            },
            cancel() {
                const _this = this;
                this.$dialog.confirm({
                    title: '提示',
                    message: '确认要取消订单吗？'
                }).then(() => {
                    postCancelOrder(_this.orderId).then(res => {
                        if (res.data.data.code == 0) {
                            _this.$router.go(-1);
                        }
                    });
                }).catch(() => {
                    // on cancel
                });
            },
        },
        created() {
            this.getOrderDetail();
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
        .object-order-page {
            .order-status {
                justify-content: center;
                .order-status__text {
                    margin: .12rem 0;
                    text-align: center;
                    .icon_waitship-status {
                        color: #FF8335;
                        margin-right: 0.1rem;
                        font-weight: 700;
                    }
                    span {
                        font-size: 0.34rem;
                        font-weight: 700;
                    }
                }
            }

        }
        .delivery-info {
            .icon_location {
                margin-right: 0.2rem;
                font-size: 0.4rem;
            }
            .flex__item {
                .delivery-info_consignee-name {
                    margin-bottom: 0.2rem;
                    span:first-child {
                        margin-right: 0.1rem;
                    }
                }
                .delivery-info_consignee-address {
                    font-size: 0.26rem;
                }
            }
        }
        .goods-info {
            .flex {
                justify-content: flex-start;
                .goods-info__img {
                    width: 2rem;
                    height: 1.24rem;
                    margin-right: 0.2rem;
                    img {
                        width: 100%;
                        height: 100%;
                    }
                }
                .goods-info__title {
                    display: -webkit-box;
                    -webkit-line-clamp: 2;
                    -webkit-box-orient: vertical;
                    overflow: hidden;
                    text-overflow: ellipsis;
                }
            }
            .order-info {
                p {
                    margin-bottom: 0.34rem;
                    &:last-child {
                        margin-bottom: 0;
                    }
                }
            }
        }
        .float {
            position: fixed;
            bottom: 2.5rem;
            right: 0.1rem;
            .backhome-float {
                display: block;
                width: 0.84rem;
                height: 0.84rem;
                line-height: 0.84rem;
                border-radius: 50%;
                background-color: #fff;
                box-shadow: 0 2px 6px 0 rgba(0, 0, 0, .18);
                .icon__home {
                    font-size: 0.4rem;
                    font-weight: 600;
                }
            }
        }

        .footer {
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            padding: 0.3rem 0.2rem;
            text-align: center;
            background: #FFF;
            .app_btn {
                font-size: 0.4rem;
                width: 100%;
                height: 100%;
            }
            .btn--primary {
                line-height: 1rem;
            }
            .cancel:hover{
                color: #333 !important;
            }
        }
    }
</style>
