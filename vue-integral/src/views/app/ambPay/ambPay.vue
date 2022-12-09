<template>
    <div class="ambpay-page">
        <div class="ambpay-status">
            <div class="wrap">
                <p class="ambpay-status__title">您已支付{{orderDetails.orderTotalIntegralPrice}}积分，请继续支付
                    <span class="theme-color">{{orderDetails.orderTotalPrice}}元</span>
                </p>
                <count-down  v-on:end_callback="countDownEnd()"
                    :currentTime="down.currentTime" :startTime="down.startTime"
                    :endTime="down.endTime"></count-down>
                <p class="ambpay-status__text">支付剩余时间</p>
                <a class="order-detail__btn" @click="showOrderDetail">订单详情</a>
            </div>
        </div>
        <div class="paymode">
            <div class="paymode__title">选择支付方式</div>
            <ul class="paymode__list">
                <li class="paymode__item flex" @click="paymode('wechat')">
                    <div class="pay wechat flex1">
                        微信支付
                    </div>
                    <div class="paymode__check">
                        <i class="icon" :class="{ checked:wechat }"></i>
                    </div>
                </li>
                <li class="paymode__item flex" @click="paymode('alipay')">
                    <div class="pay alipay flex1">
                        支付宝支付
                    </div>
                    <div class="paymode__check">
                        <i class="icon" :class="{ checked:alipay }"></i>
                    </div>
                </li>
            </ul>
        </div>
        <section class="footer footer--noboxshadow">
            <div class="footer__bd">
                <button class="app_btn btn--primary btn--lg" style="width: 65%;" @click="pay">
                    确认支付¥{{orderDetails.orderTotalPrice}}
                </button>
                <button class="app_btn btn--default btn--lg cancel" style="width: 30%; float: left;" @click="cancel">
                    取消
                </button>
            </div>
        </section>
        <app-dialog :isShow="isShowDialog" :username="orderDetails.addrUserName" :telephone="orderDetails.addrTelephone"
                    :address="addrObj.areaInfo" :credits="addrObj.credits" @on-close="showOrderDetail">
            <div class="order-detail__img">
                <img
                    :src="imageWebServer + orderDetails.orderGoods.goods.goodsMainPhoto.path + orderDetails.orderGoods.goods.goodsMainPhoto.name">
            </div>
            <div class="order-detail__title">{{orderDetails.orderGoods.orderGoodsName}}</div>
        </app-dialog>
        <transition name="app-loading">
            <app-loading v-show="loading" style="z-index: 1;"></app-loading>
        </transition>
    </div>
</template>

<script>
    import appDialog from '@/components/app/dialog';
    import loading from '@/components/app/loading'
    import {getOrderDetails, postCancelOrder} from '@/service/getAppData';
    import {appPay} from '@/api/appApi';
    import CountDown from '@/components/app/vue2-countdown';
    import config from '@/config/config';

    export default {
        data() {
            return {
                imageWebServer: config.ossImgPath,
                down: {currentTime: new Date().getTime(), startTime: new Date().getTime(), endTime:1},
                isShowDialog: false,
                orderId: this.$route.params.order_id,
                orderDetails: {orderGoods: {goods: {goodsMainPhoto :{path: '', name: ''}}}},
                addrObj: {
                    credits: "",
                    areaInfo: ""
                },
                channel: 'alipay',
                loading: true,
                alipay: false,
                wechat: true,
            }
        },
        methods: {
            // 倒计时方法
            countDown(payTime) {
                this.down.endTime = new Date(payTime.replace(/-/g, '/')).getTime() + 1000 * 60 * 30;
            },
            // 展示商品详情
            showOrderDetail() {
                this.isShowDialog = !this.isShowDialog;
            },
            getOrderDetail() {
                getOrderDetails({orderId: this.orderId}).then(res => {
                    this.loading = false;
                    if (res.data.data.code == 0) {
                        this.orderDetails = res.data.data.data;
                        this.countDown(this.orderDetails.payTime);
                        if (10 != this.orderDetails.orderState) {
                            this.countDownEnd();
                        }
                        this.addrObj.credits = this.orderDetails.orderTotalIntegralPrice + '积分';
                        if (this.orderDetails.orderTotalPrice > 0) {
                            this.addrObj.credits += ' + ' + this.orderDetails.orderTotalPrice + '元';
                            if (this.orderDetails.logisticsPrice > 0) {
                                this.addrObj.credits += "（含运费￥" + this.orderDetails.logisticsPrice + '元）';
                            }
                        }
                        if (this.orderDetails.orderStateType == 2) {
                            this.addrObj.areaInfo = "（自提地点）: " + this.orderDetails.orderGoods.goods.takeSelfAddr;
                        } else {
                            this.addrObj.areaInfo = "（配送地点）: " + this.orderDetails.addrAreaInfo;
                        }
                    }
                });
            },
            pay() {
                if(this.alipay){
                    this.channel = 'alipay';
                }else if (this.wechat){
                    this.channel = 'wx';
                }
                appPay(this.orderId, this.orderDetails.orderGoods.orderGoodsName, this.channel)
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
            countDownEnd() {
                if(!this.loading){
                    const _this = this;
                    this.$dialog.alert({
                        message: '支付超时，请重新下单'
                    }).then(() => {
                        _this.$router.go(-1);
                    });
                }
            },
            paymode(param){
                this.alipay = false
                this.wechat = false
                this[param] = true
            }
        },
        created() {
            this.getOrderDetail();
        },
        components: {
            appDialog,
            CountDown,
            'app-loading': loading
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

    .app-loading-enter-active, .app-loading-leave-active {
        transition: opacity .3s
    }

    .app-loading-enter, .app-loading-leave-active {
        opacity: 0
    }

    .ambpay-page {
        height: 100vh;
        overflow-x: hidden;
        background: #fff;
        .ambpay-status {
            position: relative;
            text-align: center;
            background: #F5F5F5;
            height: 2.2rem;
            padding: 0.5rem 0.5rem 0;
            margin-bottom: 2.4rem;
            &::after {
                content: '';
                background: #F5F5F5;
                position: absolute;
                bottom: -1.7rem;
                left: -1.4rem;
                right: -1.4rem;
                height: 6.4rem;
                -webkit-border-radius: 0 0 50% 50%;
                border-radius: 0 0 50% 50%;
            }
            .wrap {
                position: relative;
                z-index: 2;
                .ambpay-status__title {
                    font-size: 0.3rem;
                    color: #333;
                    margin-bottom: 0.3rem;
                    padding-top: 0.5rem;
                }
                .ambpay-status__time {
                    font-size: 0.66rem;
                    color: #000;
                    margin-bottom: 0.1rem;
                }
                .ambpay-status__text {
                    font-size: 0.3rem;
                    color: #333;
                }
                .order-detail__btn {
                    position: absolute;
                    top: -0.2rem;
                    right: -0.1rem;
                    font-size: 0.3rem;
                    color: #999;
                }
            }
        }
        .paymode {
            .paymode__title {
                color: #999;
                font-size: 0.3rem;
                text-align: center;
                position: relative;
                margin-bottom: 0.3rem;
                &:after, &:before {
                    position: absolute;
                    content: '';
                    top: 0.2rem;
                    width: 1rem;
                    height: 1px;
                    background: #CDCDCD;
                }
                &:before {
                    left: 1.6rem;
                }
                &:after {
                    right: 1.6rem;
                }
            }
            .paymode__list {
                .paymode__item {
                    font-size: 0.3rem;
                    padding: 0.26rem 0.3rem;
                    .pay {
                        background-repeat: no-repeat;
                        padding-left: 0.76rem;
                        width: 0.64rem;
                        height: 0.64rem;
                        line-height: 0.64rem;
                        background-size: 0.5rem 0.5rem;
                        background-position: 0.06rem 0.06rem;
                        &.alipay {
                            background-image: url("../../../../static/alipay.png");
                        }
                        &.wechat {
                            background-image: url("../../../../static/wechat.png");
                        }
                    }
                    .paymode__check {
                        .icon {
                            width: 0.48rem;
                            height: 0.48rem;
                            /*font-size: 0.48rem;*/
                            border: 1px solid #e5e5e5;
                            border-radius: 100%;
                            color: #3CC038;
                            display: inline-block;
                            &.checked{
                                background-image: url("../../../../static/icon/successful.png");
                                background-repeat: no-repeat;
                                background-size: 100%;
                                border: 0;
                            }
                        }
                    }
                }
            }
        }
        .footer {
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            padding: 0.5rem 0.2rem;
            text-align: center;
            background: #FFF;
            .app_btn {
                font-size: 0.4rem;
                width: 100%;
                height: 100%;
            }
            .cancel:hover{
                color: #333 !important;
            }
        }
        .order-detail__img {
            width: 3.7rem;
            margin: 0 auto;
            img {
                width: 100%;
            }
        }
        .order-detail__title {
            font-size: 0.32rem;
            color: #333;
            text-align: center;
            padding: 0.3rem;
            border-bottom: 1px solid #EFEFEF;
        }
    }

    .btn--primary {
        line-height: 1rem;
    }
</style>
