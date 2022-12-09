<template>
<div id="content" :class="{paddingBottom:Unused}">
    <div class="coupon-order-page">
        <section class="goods-banner" id="J_Swiper">
            <app-banner :bannerType="'goods'" :imgList="goodsImgList" height="3.2rem"></app-banner>
        </section>
        <section class="coupon-info panel">
            <div class="panel__bd">
                <div class="media-box">
                    <h3 class="coupon-info__title">智能扫地机器人</h3>
                    <p class="coupon-info__validty-period">仅剩150天（至：2018-12-31）</p>
                </div>

            </div>
        </section>
        <section class="panel coupon-code">
            <div class="panel__bd">
                <div class="media-box">
                    <div class="flex coupon-code__item">
                        <label>券码：</label>
                        <div class="flex1"><strong>35615093212</strong></div>
                        <div class="coupon-code__btn">
                            <a class="btn btn--default btn--xs theme-bordercolor J_Copy">复制</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section class="panel goods-description">
            <div class="panel__bd article goods-description__article">
                <div class="media-box">
                    <div class="article__title">商品详情</div>
                    <div class="article__content">
                        <div>商品简介</div>
                        <div>智能扫地机器人，家居清洁小能手，续航持久，实惠耐用！原价99-109元/件，金币换购仅需39-49元/件，数量有限，赶紧抢购！</div>
                        </br>
                        <div>兑换流程</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        </br>
                        <div>兑换流程</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        <div>1、兑换后在积分商城首页兑换记录里获得【兑换码】并复制；</div>
                        </br>
                    </div>
                </div>
            </div>
        </section>
        <section class="coupon-order-info panel">
            <div class="panel__bd order-info">
                <div class="media-box">
                    <p class="flex t-666">支付金额：
                        <span class="t-red">9金币</span>
                    </p>
                    <p class="t-666">订单编号：73274652960391C0783</p>
                    <p class="t-666">下单时间：2018-08-04 14:59:51</p>
                </div>
            </div>
        </section>
    </div>
    <section class="footer" v-if="Unused">
        <div class="footer__bd" @click="showDialog">
            <a class="btn btn--primary btn--lg" id="J_UseBtn">立即使用</a>
        </div>
    </section>
    <div class="float">
        <a class="backhome-float float__item t-center" href="#/app/index/home">
            <i class="iconfont icon__home t-333">&#xe6cb;</i>
        </a>
    </div>
    <app-dialog :isShow="showQrcode" footerType="2" @on-close="hideDialog">
        <strong slot="hd" class="t-333 title">核销码</strong>
        <template slot="bd">
            <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533388209822&di=09e751e96bc3e72934c3e898f3c50ef6&imgtype=0&src=http%3A%2F%2Fwww.boroboromi.com%2Fimages%2Fqr-code.png">
        </template>
    </app-dialog>
</div>
</template>

<script>
    import {getOrderDetails} from '@/service/getAppData';
    import appDialog from '@/components/app/dialog';

    export default{
        data() {
            return {
//                imageWebServer: config.ossImgPath,
//                orderId: this.$route.params.order_id,
//                orderDetails: {orderGoods: {orderGoodsIconPhoto: {path: '', name: ''}, goods: {}}},
//                activeNames: ['1'],
                Unused: true,                                   // 未使用
                showQrcode: false,                              // 显示核销二维码
            }
        },
        methods: {
            getOrderDetail: function () {
//                getOrderDetails({orderId: this.orderId}).then(res => {
//                    this.orderDetails = res.data.data.data;
//                    console.log(this.orderDetails);
//                });
            },
            // 立即使用 显示核销码
            showDialog() {
                this.showQrcode = true;
            },
            // 关闭核销码
            hideDialog() {
                this.showQrcode = false;
            }
        },
        mounted() {
//            this.getOrderDetail();
        },
        components: {
//            'app-banner': appBanner,
            'app-dialog': appDialog,
        },
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
#content{
    &.paddingBottom{
        padding-bottom: 65px;
    }
    .coupon-order-page {
        padding: .3rem;
        .coupon-info{
            position: relative;
            margin-bottom: 0;
            border-bottom: 1px dashed #EFEFEF;
            &:before,&:after{
                content: '';
                position: absolute;
                background: #F4F4F4;
                width: .36rem;
                height: .36rem;
                -webkit-border-radius: 100%;
                border-radius: 100%;
                bottom: -.18rem;
            }
            &:before {
                left: -.18rem;
            }
            &:after {
                right: -.18rem;
            }
            .coupon-info__title{
                font-size: 0.32rem;
            }
            .coupon-info__validty-period{
                color: #999;
                margin: 0.1rem 0 0.04rem;
            }
        }
        .coupon-code{
            margin-bottom: 0;
            border-bottom: 1px dashed #EFEFEF;
            .coupon-code__item {
                padding: .24rem .2rem;
                font-size: .3rem;
                background: #F5F7F8;
                -webkit-border-radius: .06rem;
                border-radius: .128rem;
                .flex__item{
                    text-align: left;
                }
            }
        }
        .goods-description{
            .article__title {
                margin-bottom: 0.3rem;
                color: #333;
                font-size: 0.3rem;
                font-weight: 700;
            }
            .article__content {
                font-size: 0.28rem;
                line-height: 0.4rem;
                overflow: hidden;
                color: #333;
            }
        }
        .coupon-order-info{
            margin-bottom: 0;
            p {
                margin-bottom: .3rem;
                &:last-child{
                    margin-bottom: 0;
                }
                &.flex{
                    justify-content: flex-start;
                }
            }
        }
    }
    .footer {
        position: fixed;
        bottom: 0;
        left: 0;
        right: 0;
        padding: 0.2rem 0.8rem;
        text-align: center;
        background: #FFF;
        -webkit-box-shadow: 0 -0.08533333rem 0.17066667rem rgba(0, 0, 0, .04);
        box-shadow: 0 -0.08533333rem 0.17066667rem rgba(0, 0, 0, .04);
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

    .dialog__bd{
        img{
            width: 4.5rem;
            height: 4.5rem;
        }
    }
}
</style>
