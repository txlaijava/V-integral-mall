<template>
    <div class="container">
        <section class="object-info goods-info panel" v-if="orderDetails != undefined && orderDetails.oid != undefined">
            <div class="flex flex--aligncenter media-box">
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
            <div class="media-box order-info">
                <div class="panel__bd">
                    <p class="flex t-666">支付金额：<span class="t-red">{{orderDetails.orderTotalIntegralPrice}}积分</span></p>
                    <p class="t-666">订单编号：{{orderDetails.oid}}</p>
                    <p class="t-666">自提地点：{{orderDetails.orderGoods.goods.takeSelfAddr}}</p>
                    <p class="t-666">核销码：{{orderDetails.verifyCode}}</p>
                    <p class="t-666">下单时间：{{orderDetails.addTime}}</p>
                </div>
            </div>
        </section>
        <section class="errorMessage" v-if="loading == false && (orderDetails == undefined || orderDetails.oid == undefined)">
            核销码无效
        </section>
        <section class="goods-footer" v-if="orderDetails != undefined && orderDetails.oid != undefined">
            <div class="goods-footer__bd">
                <button class="btn-exchange btn btn--primary" id="exchange" v-if="orderDetails.orderState==30 && !verifyStatus" @click="confirm">确认核销</button>
                <button class="btn-exchange btn btn-disabled" v-if="verifyStatus || orderDetails.orderState!=30">已核销</button>
            </div>
        </section>
        <transition name="app-loading">
            <app-loading v-show="loading" style="z-index: 1;"></app-loading>
        </transition>
    </div>
</template>

<script>
    import {getOrderDetailsByVerifyCode,postVerifyOrder} from '@/service/getAppData';
    import config from '@/config/config';
    import loading from '@/components/app/loading';

    export default {
        data() {
            return {
                imageWebServer: config.ossImgPath,
                loading: true,
                acApplyId: this.$route.query.acApplyId,
                verifyCode:this.$route.query.verifyCode,
                verifyStatus:false,
                orderDetails: {orderGoods: {orderGoodsIconPhoto: {path: '', name: ''}, goods: {}}}
            }
        },
        methods: {
            getOrderDetailsByVerifyCode: function () {
                getOrderDetailsByVerifyCode({acApplyId: this.acApplyId,verifyCode:this.verifyCode}).then(res => {
                    this.orderDetails = res.data.data.data;
                    this.loading = false;
                    console.log(this.orderDetails);
                });
            },
            confirm:function(){
                var that = this;
                postVerifyOrder(this.orderDetails.oid).then(res => {
                    if(res.data.data.code == 0){
                        this.$toast('核销成功!');
                        this.verifyStatus = true;
                    }
                });
            }
        },
        created() {
            this.getOrderDetailsByVerifyCode();
        },
        components: {
            'app-loading': loading
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .errorMessage{
        margin: 100px;
        text-align: center;
        font-size: 20px;
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
    }
    .goods-footer {
        position: fixed;
        bottom: 0;
        left: 0;
        right: 0;
        padding: 0.2rem 0.3rem;
        text-align: center;
        background: #FFF;
        -webkit-box-shadow: 0 -0.08533333rem 0.17066667rem rgba(0, 0, 0, .04);
        box-shadow: 0 -0.08533333rem 0.17066667rem rgba(0, 0, 0, .04);
        .timedown {
            font-size: 0.26rem;
            margin-bottom: 0.2rem;
            strong {
                background: #333;
                -webkit-border-radius: .04rem;
                border-radius: .04rem;
                margin: 0 0.01rem;
                padding: 0.03rem 0.05rem;
                font-weight: 400;
                color: #FFF;
                span {
                    display: inline-block;
                }
            }
        }
        .btn-exchange {
            width: 5.9rem;
            height: 0.9rem;
            border-radius: 0.9rem;
            font-size: 0.3rem;
        }
    }
</style>
