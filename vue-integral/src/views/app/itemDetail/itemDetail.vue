<template>
    <div id="goods-content" style="padding-bottom: 65px;">
        <section class="goods-banner" id="J_Swiper">
            <app-banner :bannerType="'goods'" :imgList="goodsImgList" height="3.5rem"></app-banner>
        </section>
        <section class="goods-info">
            <div class="goods-info__container">
                <div class="goods-info__hd">
                    <h3 class="goods-info__title t-333" v-text="goodsInfo.goodsName"></h3>
                </div>
                <div class="goods-info__ft">
                      <span v-if="goodsInfo.exchangeRules.exchangePriceType == 2">
                        <span class="t-red">¥</span>
                        <span class="goods-info__credits t-red">{{goodsInfo.exchangeRules.exchangePrice}}</span>
                        <span class="sale-price__plus">＋</span>
                        <span class="sale-price__plus theme-color t-red">{{goodsInfo.exchangeRules.exchangeIntegralPrice}}</span>
                      </span>
                    <span class="goods-info__credits theme-color t-red" v-else>{{goodsInfo.exchangeRules.exchangeIntegralPrice}}</span>
                    <span class="goods-info__unit t-red">积分</span>
                    <del class="goods-info__face-price t-BD" v-if="goodsInfo.exchangeRules.goodsPrice">
                        ¥{{goodsInfo.exchangeRules.goodsPrice}}
                    </del>
                </div>
            </div>
            <div class="way" v-show="goodsInfo.isSend">
                <div class="delivery-info flex" @click="show=true">
                    <label class="t-999">配送方式</label>
                    <span class="way-type" v-if="orderStateInfo.orderStateType==''">请选择</span>
                    <span class="way-type" v-else>{{orderStateInfo.orderStateValue}}</span>
                    <i class="iconfont t-999">&#xe612;</i>
                </div>
            </div>
            <div class="address"
                 v-if="login && goodsInfo.goodsType == 1 && (orderStateInfo.orderStateType == 1 || orderStateInfo.orderStateType == 2 || !goodsInfo.isSend)">
                <div class="delivery-info flex">
                    <label class="t-999">送至</label>
                    <router-link
                        :to="{ name: 'addAddress', params: { goodsId:goodsInfo.id,orderStateType:orderStateInfo.orderStateType }}"
                        class="delivery-info__address flex1" v-if="!isAddress">
                        <template>
                            <p class="t-BD">你还未填写收货信息，马上去填写</p>
                        </template>
                    </router-link>

                    <router-link
                        :to="{ name: 'addAddress', params: { addressId: addrObj.id,goodsId:goodsInfo.id,orderStateType:orderStateInfo.orderStateType }}"
                        class="delivery-info__address flex1" v-else>
                        <template>
                            <p id="J_ConsumerName" class="delivery-info__consignee-info"
                               v-text="addrObj.trueName + ' ' + addrObj.mobile"></p>
                            <p class="delivery-info__consignee-address text--line1 flex">
                                <i class="iconfont">&#xe9090;</i>
                                <span id="J_ConsumerAddr" v-text="addrObj.area + ' ' + addrObj.areaInfo"></span>
                            </p>
                        </template>
                    </router-link>
                    <i class="iconfont t-999">&#xe612;</i>
                </div>
                <div class="express-info" v-if="orderStateInfo.orderStateType==1">
                    <label class="t-999">运费</label>
                    <span class="express-info__price" v-if="goodsInfo.exchangeRules.freightWay == 0">包邮</span>
                    <span class="express-info__price" v-else-if="goodsInfo.exchangeRules.freightWay == 1">¥{{goodsInfo.exchangeRules.freightPrice}}</span>
                </div>

            </div>
        </section>
        <section class="goods-description" v-if="goodsInfo.goodsDetails">
            <div class="goods-description__article">
                <div class="article__title">商品详情</div>
                <div class="article__content" v-html="goodsInfo.goodsDetails">
                </div>
            </div>
        </section>
        <section class="goods-footer">
            <div class="goods-footer__bd">
                <div class="timedown" v-if="timedown">
                    <i class="iconfont t-999">&#xe625;</i>
                    <span class="dateRange t-999">倒计时</span>
                    <strong>
                        <span class="hour">02</span>
                    </strong>
                    <span>:</span>
                    <strong>
                        <span class="minute">31</span>
                    </strong>
                    <span>:</span>
                    <strong>
                        <span class="second">11</span>
                    </strong>
                </div>
                <!--<button class="btn-exchange btn" id="exchange"
                        :class=" myCredits >= goodsInfo.credits ? 'btn-primary'  : 'btn-disabled' "
                        @click=" myCredits >= goodsInfo.credits ? showDialog() : '' ">
                  {{myCredits >= goodsInfo.credits ? '立即兑换' : '积分不足'}}
                </button>-->

                <button class="btn-exchange btn--lg btn btn--disabled" v-if="!login">兑换前请先登录</button>

                <button class="btn-exchange btn--lg btn" id="exchange" v-else
                        :class=" myCredits >= goodsInfo.exchangeRules.exchangeIntegralPrice ? 'btn--primary'  : 'btn--disabled' "
                        @click=" myCredits >= goodsInfo.exchangeRules.exchangeIntegralPrice ? showDialog() : '' ">
                    {{myCredits >= goodsInfo.exchangeRules.exchangeIntegralPrice ? goodsInfo.exchangeButTxt ? goodsInfo.exchangeButTxt :'马上兑换' : '积分不足'}}
                </button>
            </div>
        </section>
        <div class="actionsheet show-actionsheet" :class="gearInfo.gearShow ? 'show':'hide'" @click="gearClose">
            <div class="actionsheet__container gear-actionsheet" @click.stop="">
                <div class="actionsheet__hd">
                    <div class="actionsheet__title"  v-text="goodsInfo.goodsName"></div>
                </div>
                <div class="actionsheet__bd">
                    <div class="gear-actionsheet__text">选择档位：</div>
                    <ul class="gear-actionsheet__group">
                        <li v-for="(item,index) in goodsInfo.gearValue" :class="gearInfo.gearVal == item.gearVal ? 'theme-bgcolor':''" @click="selectGear(item)">
                          {{item.gearTitle}}
                        </li>
                    </ul>
                </div>
                <div class="actionsheet__ft">
                    <div class="flex flex--aligncenter">
                        <div class="flex__item t-red">
                          <template v-if="gearInfo.gearVal != ''">
                            <span class="gear-actionsheet__credits" >{{gearInfo.gearPrice}}</span>
                            <span class="gear-actionsheet__unit">积分</span>
                          </template>
                        </div>
                        <div class="actionsheet__btn-wrap">
                            <button class="app_btn btn--md gear-actionsheet__confirm" :class="gearInfo.gearVal == ''? 'btn--disabled':'btn--primary'" @click=" gearInfo.gearVal != ''? gearConfirm():''">
                              {{gearInfo.gearVal != ''?'确定兑换':'请选择档位'}}
                            </button>
                        </div>
                    </div>
                </div>
                <div class="actionsheet__close" @click.stop="gearClose"><i class="iconfont icon-close"></i></div>
            </div>
        </div>
      <app-dialog :isShow="isShowDialog"  footerType="0" @on-close="hideDialog" @on-confirm="handleConfirm">
        <strong slot="hd" class="t-333">确认兑换</strong>
        <div slot="bd" class="object-exchange-dialog__bd">
          <p class="flex" v-if="addrObj.trueName">
            <label class="t-666 t-right">收货人：</label>
            <span class="flex__item flex1" v-text="addrObj.trueName"></span>
          </p>
          <p class="flex" v-if="addrObj.mobile">
            <label class="t-666 t-right">电&emsp;话：</label>
            <span class="flex__item flex1" v-text="addrObj.mobile"></span>
          </p>
          <p class="flex" v-if="orderStateInfo.orderStateType==2">
            <label class="t-666 t-right">配送方式：</label>
            <span class="flex__item flex1" v-text="orderStateInfo.orderStateValue"></span>
          </p>
          <p class="flex" v-else>
            <label class="t-666 t-right">地&emsp;址：</label>
            <span class="flex__item flex1" v-text="addrObj.area + addrObj.areaInfo"></span>
          </p>
          <p class="flex" v-if="orderStateInfo.orderStateType==1">
            <label class="t-666 t-right">兑换金额：</label>
            <span class="flex__item t-red flex1" v-text="credits">&nbsp;</span>
          </p>
        </div>
      </app-dialog>
        <app-tipDialog v-model="isShowTip">{{message}}</app-tipDialog>
        <app-tipDialog v-model="isShowLoading" className="loadingDialog">
            <img src="../../../../static/loading.png">
            兑换中...
        </app-tipDialog>
        <transition name="app-loading">
            <app-loading v-show="loading" style="z-index: 1;"></app-loading>
        </transition>
        <van-actionsheet v-model="show" :actions="actions" cancel-text="取消" @cancel="cancel"/>
    </div>
</template>

<script>
    import appBanner from '@/components/app/banner';
    import appDialog from '@/components/app/dialog';
    import appTipdialog from '@/components/app/tipDialog';
    import loading from '@/components/app/loading'
    import {getGoodsDetails, saveOrder, getAddress} from '@/service/getAppData';
    import {isApp,isAndroid,isIphoneOs,sUserAgent} from '@/api/appApi';
    import * as SYSTEM from '@/api/system';

    export default {
        data() {
            return {
                timedown: false,
                isAddress: false,
                addrObj: {
                    mobile: "",
                    area: "请选择收货地址",
                    areaInfo: "",
                    trueName: ""
                },
                goodsInfo: {exchangeRules: {}},
                orderStateInfo: {
                    orderStateType: this.$route.query.orderStateType || "",
                    orderStateValue: this.$route.query.orderStateType == 1 ? "送货上门" : ""
                },
                goodsImgList: [],
                isShowDialog: false,
                isShowLoading: false,
                isShowTip: false,
                message: "",
                credits: "",
                goodsId: this.$route.params.goodsId,
                loading: true,
                show: false,
                actions: [
                    {name: '送货上门', callback: this.typeChange},
                    {name: '上门自提', subname: "", callback: this.typeChange}
                ],
                gearInfo:{
                  gearShow:false,
                  gearVal:"",
                  gearPrice:"",
                }

            }
        },
        computed: {
            login() {
                return this.$store.state.appUser.appLoginStatus;
            },
            myCredits() {
                const appUserData = this.$store.state.appUser.appUserData;
                let credits = 0;
                //判断是否为JSON对象
                if (typeof(appUserData) == "object" &&
                    Object.prototype.toString.call(appUserData).toLowerCase() == "[object object]" && !appUserData.length) {
                    credits = this.$store.state.appUser.appUserData.credits;
                } else {
                    credits = JSON.parse(this.$store.state.appUser.appUserData).credits;
                }
                return credits;
            }
        },
        methods: {
            getItemDetail: function () {
                getGoodsDetails({goodsId: this.goodsId}).then((res) => {
                    const re = res.data.data.data;
                    console.log(re);
                    if (2007 == parseInt(res.data.data.code)) {
                        this.$toast('该商品已下架');
                        this.$router.go(-1);
                        return
                    }
                    this.goodsInfo = re;
                    if(re.gearType == 1){
                      this.goodsInfo.gearValue = re.gearValue;
                    }else{
                      this.goodsInfo.gearValue = JSON.parse(re.gearValue);
                    }

                    this.goodsImgList = this.goodsInfo.goodsPhotos;
                    this.loading = false;
                })
            },
            getAddrObj() {
                if (this.login) {
                    getAddress().then(res => {
                        const re = res.data.data;
                        if (SYSTEM.SUCCSSS == re.success) {
                            this.addrObj = re.data;
                            this.isAddress = true;
                        }
                    })
                }
            },
            showDialog() {

                if(this.goodsInfo.goodsType == 2){
                    if(this.goodsInfo.gearType == 2){
                      this.gearInfo.gearShow = true;
                    }else{
                      console.log("单档位");
                      this.gearInfo.gearVal = this.goodsInfo.gearValue;
                      this.handleConfirm();
                    }
                }else{
                    if (this.goodsInfo.isSend && (this.orderStateInfo.orderStateType == undefined || this.orderStateInfo.orderStateType == "")) {
                        this.isShowTip = true;
                        this.message = "请选择配送方式";
                        setTimeout(function () {
                            this.isShowTip = false;
                        }, 3000);
                        return
                    }
                  if (this.isAddress) {
                    this.isShowDialog = true;
                    this.credits = this.goodsInfo.exchangeRules.exchangeIntegralPrice + '积分';

                    if (this.goodsInfo.exchangeRules.exchangePriceType == 2) {
                      if (this.goodsInfo.exchangeRules.freightWay == 1) {
                        this.credits += ' + ' + (this.goodsInfo.exchangeRules.exchangePrice + this.goodsInfo.exchangeRules.freightPrice) + '元';
                        this.credits += "（含运费￥" + this.goodsInfo.exchangeRules.freightPrice + '元）';
                      } else {
                        this.credits += ' + ' + this.goodsInfo.exchangeRules.exchangePrice + '元';
                      }
                    }
                  } else{
                    this.isShowTip = true;
                    this.message = "请选择地址";
                    setTimeout(function () {
                      this.isShowTip = false;

                    }, 3000);
                  }
                }
            },
            hideDialog() {
                this.isShowDialog = false
            },
            handleConfirm() {
                this.isShowDialog = false;
                this.isShowLoading = true;
                var channel ="";
                if(isApp){
                  channel = "APP";
                }else{
                  channel = "WX";
                }
                const params = {
                    goodsId: this.goodsId,
                    addressId: this.addrObj.id,
                    orderStateType: (this.orderStateInfo.orderStateType=="" ? 1:this.orderStateInfo.orderStateType),
                    gearCode:this.gearInfo.gearVal,
                    channel:channel
                };
                saveOrder(params).then(res => {
                    this.isShowLoading = false;
                    const re = res.data.data;
                    if (SYSTEM.SUCCSSS == re.success) {
                        //如果是送货上门
                        if(this.orderStateInfo.orderStateType == 1 && (this.goodsInfo.exchangeRules.exchangePriceType == 2 || this.goodsInfo.exchangeRules.freightPrice > 0)){
                            this.$router.push({name: 'ambPay', params: {order_id: re.data}});
                        }else if(this.orderStateInfo.orderStateType == 2 && this.goodsInfo.exchangeRules.exchangePriceType == 2){
                            this.$router.push({name: 'ambPay', params: {order_id: re.data}});
                        }else {
                            this.$router.push({name: 'recordDetail', params: {order_id: re.data}});
                        }
                    } else if (parseInt(re.code) >= 6000 && parseInt(re.code) <= 6004) {
                        this.$toast('兑换失败，' + re.message);
                    } else if (7000 == parseInt(re.code)) {
                        this.$toast('商品库存不足');
                    } else if (7001 == parseInt(re.code)) {
                        this.$toast('该商品已下架');
                    } else {
                        this.$toast('兑换失败！' + re.message);
                    }
                })
            },
            typeChange(item) {
                this.show = false;
                if (item.name == "送货上门") {
                    this.orderStateInfo.orderStateType = "1";
                    this.orderStateInfo.orderStateValue = item.name;
                } else if (item.name == "上门自提") {
                    this.orderStateInfo.orderStateType = "2";
                    this.isAddress = true;
                    this.orderStateInfo.orderStateValue = item.name + item.subname;
                }

            },
            cancel() {
                this.orderStateInfo.orderStateType = "";
                this.orderStateInfo.orderStateValue = "请选择";
            },
            gearClose(){
                this.gearInfo.gearShow = false;
                this.gearInfo.gearVal = "";
                this.gearInfo.gearPrice = "";
            },
            selectGear(param){
                this.gearInfo.gearVal = param.gearVal;
                this.gearInfo.gearPrice = param.gearPrice;
            },
            gearConfirm(){
              this.gearInfo.gearShow = false;
              this.handleConfirm();
            }
        },
        created() {
            this.getAddrObj();
            this.getItemDetail();
        },
        components: {
            'app-banner': appBanner,
            'app-dialog': appDialog,
            'app-tipDialog': appTipdialog,
            'app-loading': loading
        },
        watch: {
            '$route'(to, from) {
                this.getAddrObj();
            },
            'goodsInfo'(to, from) {
                this.actions[1].subname = "(" + to.takeSelfAddr + ")";
            }
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
    .way-type{
        flex: 1;
        text-align: right;
        margin-right: 10px;
    };

    .goods-info {
        background: #FFF;
        padding: 0 0.3rem;
        margin-bottom: 0.3rem;
        .goods-info__container {
            padding: 0.3rem 0;
            border-bottom: 1px solid #e5e5e5;
            .goods-info__title {
                font-size: 0.32rem;
                font-weight: 600;
                margin-bottom: 0.16rem;
            }
            .goods-info__ft {
                .goods-info__credits {
                    font-size: 0.58rem;
                    font-weight: 400;
                }
                .goods-info__unit {
                    font-size: 0.3rem;
                }
                .goods-info__face-price {
                    font-size: 0.3rem;
                    margin-left: 0.08rem;
                }
            }
        }
        .way {
            font-size: 0.27rem;
            padding: 0.3rem 0;
            justify-content: flex-start;
            label {
                padding-right: 0.34rem;
            }
            .delivery-info {
                align-items: flex-start;
                .iconfont {
                    font-size: 0.26rem;
                }
            }
        }
        .address {
            font-size: 0.27rem;
            padding: 0.3rem 0;
            justify-content: flex-start;
            label {
                padding-right: 0.34rem;
            }
            .delivery-info {
                align-items: flex-start;
                p {
                    font-size: 0.26rem;
                    &.delivery-info__consignee-info {
                        margin-bottom: 0.12rem;
                    }
                    &.delivery-info__consignee-address {
                        color: #666;
                        justify-content: flex-start;
                        span {
                            margin-left: 0.1rem;
                            overflow: hidden;
                            display: inline-block;
                            white-space: nowrap;
                            width: 5.3rem;
                            text-overflow: ellipsis;
                        }
                    }
                }
                .iconfont {
                    font-size: 0.26rem;
                }
            }
            .express-info {
                margin-top: 0.32rem;
                .express-info__price {
                    font-size: 0.28rem;
                }
            }
        }
    }

    .goods-description {
        padding: 0 0.3rem;
        background: #fff;
        margin-bottom: 1rem;
        .goods-description__article {
            padding: 0.3rem 0;
            .article__title {
                font-size: 0.32rem;
                margin-bottom: 0.26rem;
                font-weight: 600;
            }
            .article__content {
                font-size: 0.28rem;
                line-height: 2;
            }
        }
    }

    .goods-footer {
        position: fixed;
        bottom: 0;
        left: 0;
        right: 0;
        padding: 0.2rem 0.8rem;
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
            /*width: 5.9rem;*/
            height: 0.9rem;
            border-radius: 0.9rem;
            font-size: 0.3rem;
        }
    }

    .object-exchange-dialog__bd {
        text-align: left;
        p {
            font-size: 0.3rem;
            margin-bottom: 0.24rem;
            align-items: flex-start;
            label{
                width: 1.42rem;
                white-space: nowrap;
            }
        }
    }

    .hide{display: none!important;}
    .show{display: block!important;}

    .actionsheet {
        position: fixed;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        background: rgba(0,0,0,.3);
        opacity: 0;
        transition: .2s opacity;
        display: none;
        z-index: 99;
        &.show-actionsheet {
            opacity: 1;
            .actionsheet__container {
                -webkit-transform: translate(0,0);
                transform: translate(0,0);
            }
        }
        .actionsheet__container {
            background: #FFF;
            width: 100%;
            position: absolute;
            top: 45%;
            left: 0;
            bottom: 0;
            border-radius: 0.24rem 0.24rem 0 0;
            overflow: hidden;
            transition: .3s ease;
            -webkit-transform: translate(0,100%);
            transform: translate(0,100%);
            font-size: 0.28rem;
            .actionsheet__hd {
                position: absolute;
                top: 0;
                right: 0;
                left: 0;
                height: 1.3rem;
                line-height: 1.3rem;
                font-size: 0.28rem;
                .actionsheet__title {
                    overflow: hidden;
                    font-size: 0.34rem;
                    color: #333;
                    font-weight: 700;
                    text-align: center;
                    margin: 0 auto;
                    width: 80%;
                    white-space: nowrap;
                    text-overflow: ellipsis;
                }
            }
            .actionsheet__bd {
                position: absolute;
                top: 1.3rem;
                bottom: 1.26rem;
                left: 0;
                width: 100%;
                overflow-x: hidden;
                padding: 0.3rem;
                .gear-actionsheet__text {
                    color: #666;
                    margin-bottom: 0.26rem;
                }
                .gear-actionsheet__group {
                    margin-bottom: 0.6rem;
                    li {
                        display: inline-block;
                        border-radius: 0.3rem;
                        padding: 0.1rem 0.3rem;
                        font-size: 0.28rem;
                        background: #F5F5F5;
                        margin: 0 0.3rem 0.3rem 0;
                    }
                }
            }
            .actionsheet__ft {
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                padding: 0.2rem 0.3rem;
                height: 1.26rem;
                border-top: solid #EFEFEF;
                border-width: 0.5px;
                .flex--aligncenter {
                    -webkit-box-align: center;
                    -ms-flex-align: center;
                    align-items: center;
                    .flex__item {
                        -webkit-box-flex: 1;
                        -ms-flex: 1;
                        flex: 1;
                        display: block;
                        min-height: 0;
                        min-width: 0;
                        .gear-actionsheet__credits {
                            font-size: 0.48rem;
                        }
                        .gear-actionsheet__unit {
                            font-size: 0.28rem;
                        }
                    }
                    .actionsheet__btn-wrap{
                        .gear-actionsheet__confirm {
                            font-size: 0.34rem;
                            line-height: 0.8rem;
                            border-radius: 0.9rem;
                        }
                    }
                }
            }
            .actionsheet__close {
                position: absolute;
                top: 0.16rem;
                right: 0.16rem;
                z-index: 2;
                padding: 0.1rem;
                .icon-close {
                    font-size: 0.24rem;
                    color: #c2c2c2;
                }
            }
        }
    }
</style>
