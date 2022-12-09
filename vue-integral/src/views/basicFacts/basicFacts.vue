<template>
    <div class="main">
        <div class="overview wrap">
            <div class="page-title">
                <div>
                    <h2>实时概况</h2>
                    <span class="time"> 更新时间： {{currentdate}} 0:00:00 </span>
                </div>
                <router-link to="datacenter/flowdata"><span class="more">更多数据<i class="iconfont">&#xe68d;</i></span>
                </router-link>
            </div>
            <div class="_content">
                <div class="part part1">
                    <div class="icon">
                        <img src="../../../static/icon/uv.png">
                    </div>
                    <div class="item uv">
                        <p>页面浏览量</p>
                        <span class="data">{{pv_day}}</span>
                        <span class="yesterday">前日：{{pv}}</span>
                    </div>
                    <div class="item pv">
                        <p>访客量</p>
                        <span class="data">{{uv_day}}</span>
                        <span class="yesterday">前日：{{uv}}</span>
                    </div>
                </div>
                <div class="part part2">
                    <div class="icon">
                        <img src="../../../static/icon/out.png">
                    </div>
                    <div class="item uv">
                        <p>支付金额(元)</p>
                        <span class="data">{{price_total_day}}</span>
                        <span class="yesterday">前日：{{price_total}}</span>
                    </div>
                    <div class="item pv">
                        <p>支付订单数</p>
                        <span class="data">{{order_total_day}}</span>
                        <span class="yesterday">前日：{{order_total}}</span>
                    </div>
                    <div class="item pv">
                        <p>订单转化率</p>
                        <span class="data">{{conversion_ratio_day}}%</span>
                        <span class="yesterday">前日：{{conversion_ratio}}%</span>
                    </div>
                </div>
                <div class="part part3">
                    <div class="icon">
                        <img src="../../../static/icon/use.png">
                    </div>
                    <div class="item uv">
                        <p>消耗金额(元)
                            <span class="tool-tip-wrap">
                                <span @mouseenter="enter('money_explain')" @mouseleave="leave('money_explain')">
                                    <a href="javascript:void(0)" class="iconfont tips-icon">&#xe629;</a>
                                </span>
                                <div class="tooltip top" role="tooltip" v-if="money_explain">
                                    <div class="tooltip-arrow"></div>
                                    <div class="tooltip-inner" v-text="'统计时间内用户购买兑吧商品扣除账户余额的资金'"></div>
                                </div>
                            </span>
                        </p>
                        <span class="data">{{price_total_day}}</span>
                        <span class="yesterday">前日：{{price_total}}</span>
                    </div>
                    <div class="item pv">
                        <p>消耗积分</p>
                        <span class="data">{{integral_total_day}}</span>
                        <span class="yesterday">前日：{{integral_total}}</span>
                    </div>
                </div>
                <div class="part part4">
                    <div class="icon">
                        <img src="../../../static/icon/add.png">
                    </div>
                    <div class="item uv">
                        <p>新增客户数</p>
                        <span class="data">{{add_user_day}}</span>
                        <span class="yesterday">前日：{{add_user}}</span>
                    </div>
                    <div class="item pv">
                        <p>活动曝光数
                            <span class="tool-tip-wrap">
                                <span @mouseenter="enter('activity_explain')" @mouseleave="leave('activity_explain')">
                                    <a href="javascript:void(0)" class="iconfont tips-icon">&#xe629;</a>
                                </span>
                                <div class="tooltip top" role="tooltip" v-if="activity_explain">
                                    <div class="tooltip-arrow"></div>
                                    <div class="tooltip-inner" v-text="'统计时间内，浏览过活动的PV'"></div>
                                </div>
                            </span>
                        </p>
                        <span class="data">0</span>
                        <span class="yesterday">前日：0</span>
                    </div>
                    <div class="item pv">
                        <p>活动参与人数</p>
                        <span class="data">0</span>
                        <span class="yesterday">前日：0</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="tips wrap">
            <div class="page-title">
                <h2>重要提醒</h2>
            </div>
            <div class="_content">
                <div class="part">
                    <div class="icon">
                        <img src="../../../static/icon/msg.png">
                        <p class="icon-des" v-text="'商品提醒'"></p>
                    </div>
                    <div class="item hover0">
                            <router-link to="mygoods">
                                <div class="des">缺货商品:
                                    <i class="font-st warn" v-text="laca_goods"></i>
                                </div>
                            </router-link>
                        <router-link to="mygoods">
                            <div class="des">售罄商品:
                                <i class="font-st " v-text="sell_out_goods"></i>
                            </div>
                        </router-link>
                        <router-link to="mygoods">
                            <div class="des">集市新增:
                                <i class="font-st " v-text="add_goods"></i>
                            </div>
                        </router-link>
                    </div>
                </div>
                <div class="part">
                    <div class="icon">
                        <img src="../../../static/icon/order.png">
                        <p class="icon-des" v-text="'订单提醒'"></p>
                    </div>
                    <div class="item hover0">
                        <router-link :to="{name:'ordersearch',params:{orderState:30}}">
                            <div class="des">待发货订单:
                                <i class="font-st" v-text="delivery_order"></i>
                            </div>
                        </router-link>
                        <router-link :to="{name:'ordersearch',params:{orderState:20}}">
                            <div class="des">待审核订单:
                                <i class="font-st " v-text="audited_order"></i>
                            </div>
                        </router-link>
                        <!--<router-link to="item.path">-->
                            <!--<div class="des">待处理退款:-->
                                <!--<i class="font-st " v-text="0"></i>-->
                            <!--</div>-->
                        <!--</router-link>-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {getBasicFacts} from '@/service/getData';

    export default{
        data() {
            return {
                currentdate: '',                            // 当前日期
                isShow: false,
                //今日数据
                uv_day:0,
                pv_day:0,
                add_user_day:0,
                order_total_day:0,
                integral_total_day:0,
                price_total_day:0,
                conversion_ratio_day:0,
                //今日数据end

                //前日数据
                uv:0,
                pv:0,
                add_user:0,
                order_total:0,
                integral_total:0,
                price_total:0,
                conversion_ratio:0,
                //前日数据end

                //商品提醒
                sell_out_goods:0,    //售完商品
                laca_goods:0,        //缺货商品
                add_goods:0,    //新增商品
                //商品提醒end

                //订单提醒
                delivery_order:0,    //待发货
                audited_order:0,        //待审核
                //商品提醒end

                money_explain :false,
                activity_explain:false,
            }
        },
        methods: {
            getBasicFacts: function () {
                getBasicFacts().then((res) => {
                    var data = res.data.data;
                    console.log(data)
                    this.uv_day = data.uv_day;
                    this.pv_day = data.pv_day;
                    this.add_user_day = data.add_user_day;
                    this.order_total_day = data.order_total_day;
                    this.integral_total_day = data.integral_total_day;
                    this.price_total_day = data.price_total_day;
                    this.conversion_ratio_day = data.conversion_ratio_day;
                    this.uv = data.uv;
                    this.pv = data.pv;
                    this.add_user = data.add_user;
                    this.order_total = data.order_total;
                    this.integral_total = data.integral_total;
                    this.price_total = data.price_total;
                    this.conversion_ratio = data.conversion_ratio;
                    this.sell_out_goods = data.sell_out_goods;
                    this.laca_goods = data.laca_goods;
                    this.add_goods = data.add_goods;
                    this.delivery_order = data.delivery_order;
                    this.audited_order = data.audited_order;
                });

            },
            // 获取当前日期
            getNowFormatDate() {
                var date = new Date();
                var seperator = "-";
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                if (month >= 1 && month <= 9) {
                    month = "0" + month;
                }
                if (strDate >= 0 && strDate <= 9) {
                    strDate = "0" + strDate;
                }
                this.currentdate = year + seperator + month + seperator + strDate;
            },
            // 鼠标移入显示提示
            enter(value) {
                console.log(value)
                this[value] = true;
            },
            // 鼠标移出隐藏提示
            leave(value) {
                this[value] = false;
            }
        },
        created() {
            this.getBasicFacts();
            this.getNowFormatDate();
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .main {
        .wrap {
            /*padding: 20px 0;*/
            /*background-color: #fff;*/
            /*margin: 20px 0;*/
            min-width: 900px;
            &.overview {
                .page-title {
                    display: -webkit-box;
                    display: -ms-flexbox;
                    display: flex;
                    position: relative;
                    border-bottom: 0;
                    align-items: baseline;
                    justify-content: space-between;
                    h3 {
                        font-size: 16px;
                    }
                    .time {
                        font-family: HiraginoSansGB-W3;
                        font-size: 14px;
                        line-height: 1.2;
                        color: #9b9b9b;
                        vertical-align: bottom;
                        margin-left: 30px;
                    }
                    .more {
                        font-family: HiraginoSansGB-W3;
                        font-size: 14px;
                        color: #29b6b0;
                        vertical-align: middle;
                        i {
                            width: 14px;
                            height: 14px;
                            font-size: 14px;
                            margin-left: 3px;
                            vertical-align: top;
                        }
                    }
                }
                ._content {
                    display: -webkit-box;
                    display: -ms-flexbox;
                    display: flex;
                    -ms-flex-wrap: wrap;
                    flex-wrap: wrap;
                    -webkit-box-pack: justify;
                    -ms-flex-pack: justify;
                    justify-content: space-between;
                    margin-top: 20px;
                    padding: 40px 10px;
                    background: #fff;
                    border-radius: 10px;
                    .part {
                        width: 50%;
                        height: 50%;
                        position: relative;
                        display: -webkit-box;
                        display: -ms-flexbox;
                        display: flex;
                        &.part1, &.part2 {
                            margin-bottom: 50px;
                        }
                        &.part1, &.part3 {
                            width: 45%;
                            .item {
                                width: 30%;
                            }
                        }
                        &.part2, &.part4 {
                            width: 55%;
                            .item {
                                width: 25%;
                            }
                        }
                        .icon {
                            margin-top: 25px;
                            top: 40px;
                            margin-left: 40px;
                            margin-right: 40px;
                        }
                        .item {
                            display: -webkit-box;
                            display: -ms-flexbox;
                            display: flex;
                            -webkit-box-orient: vertical;
                            -webkit-box-direction: normal;
                            -ms-flex-direction: column;
                            flex-direction: column;
                            margin-left: 10px;
                            margin-right: 10px;
                            p {
                                font-family: HiraginoSansGB-W3;
                                font-size: 14px;
                                color: #333;
                                margin-bottom: 20px;
                                .tool-tip-wrap {
                                    position: relative;
                                    .tips-icon {
                                        color: #999;
                                    }
                                    .tooltip {
                                        width: 200px;
                                        left: -92px;
                                        bottom: 20px;
                                    }
                                }

                            }
                            .data {
                                font-size: 24px;
                                color: #333;
                                margin-bottom: 20px;
                            }
                            .yesterday {
                                font-family: HiraginoSansGB-W3;
                                font-size: 14px;
                                color: #9b9b9b;
                                &:hover {
                                    color: #34495d;
                                }
                            }
                        }
                    }
                }
            }
            &.tips {
                .page-title {
                    border-bottom: 0;
                }
                ._content {
                    display: -webkit-box;
                    display: -ms-flexbox;
                    display: flex;
                    width: 100%;
                    /*height: 110px;*/
                    margin-top: 20px;
                    padding: 40px 10px;
                    background: #fff;
                    border-radius: 10px;
                    .part {
                        width: 33.3333%;
                        display: -webkit-box;
                        display: -ms-flexbox;
                        display: flex;
                        -webkit-box-orient: horizontal;
                        -webkit-box-direction: normal;
                        -ms-flex-direction: row;
                        flex-direction: row;
                        &:first-child {
                            width: 45%;
                        }
                        .icon {
                            margin-top: 20px;
                            margin-left: 40px;
                            .icon-des {
                                font-size: 14px;
                                margin-left: -8px;
                                margin-top: 12px;
                            }
                        }
                        .item {
                            margin-left: 60px;
                            margin-top: 10px;
                            display: -webkit-box;
                            display: -ms-flexbox;
                            display: flex;
                            -webkit-box-orient: vertical;
                            -webkit-box-direction: normal;
                            -ms-flex-direction: column;
                            flex-direction: column;
                            &:hover {
                                color: #29b6b0 !important;
                            }
                            a {
                                color: #333;
                                .des {
                                    font-size: 14px;
                                    line-height: 2.3;
                                    .font-st {
                                        font-style: normal;
                                    }
                                    .warn {
                                        color: #ff484a;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
</style>
