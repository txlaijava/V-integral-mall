<template>
    <div class="gridLists">
        <van-tabs @change="tabsChange">
            <van-tab v-for="(item, index) in tabs" :title="item.name" :key="index">
                <ul class="lists flex">
                    <li class="list" v-for="(item,index) in itemList" :key="index">
                        <router-link :to="'itemDetail/'+item.id">
                            <div class="img-wrap">
                                <img
                                    v-lazy="{src:imageWebServer + item.goodsMainPhoto.path + item.goodsMainPhoto.name,
                            error: 'static/lazy/error.png',
                            loading: 'static/lazy/loading.png'}">
                            </div>
                            <div class="info-wrap">
                                <h3 class="item-title">{{item.goodsName}}</h3>
                                <p class="item-credits t-red">{{item.exchangeRules.exchangeIntegralPrice}}积分</p>
                                <del v-if="item.exchangeRules.goodsPriceUnit === 0">{{item.exchangeRules.goodsPrice}}元
                                </del>
                                <del v-else-if="item.exchangeRules.goodsPriceUnit === 1">
                                    {{item.exchangeRules.goodsPrice}}积分
                                </del>
                            </div>
                        </router-link>
                    </li>
                </ul>
            </van-tab>
        </van-tabs>
    </div>
</template>

<script>
    import {getApplyThemeItemData, getGoodsList} from '@/service/getAppData';
    import * as SYSTEM from '@/api/system';
    import config from '@/config/config';

    export default {
        data() {
            return {
                pageSize: 10,
                imageWebServer: config.ossImgPath,
                itemList: [],
                tabs: [
                    {name: '全部', value: {startExchangeIntegralPrice: '', endExchangeIntegralPrice: ''}},
                    {name: '我能兑换的', value:  {startExchangeIntegralPrice: '', endExchangeIntegralPrice: ''}},
                    {name: '1~999', value:  {startExchangeIntegralPrice: 1, endExchangeIntegralPrice: 999}},
                    {name: '1000~2999', value:  {startExchangeIntegralPrice: 1000, endExchangeIntegralPrice: 2999}},
                    {name: '3000~4999', value:  {startExchangeIntegralPrice: 3000, endExchangeIntegralPrice: 4999}},
                    {name: '5000~6999', value:  {startExchangeIntegralPrice: 5000, endExchangeIntegralPrice: 6999}},
                    {name: '7000~99999', value:  {startExchangeIntegralPrice: 7000, endExchangeIntegralPrice: 99999}},
                ]
            }
        },
        props: ['themeItemId', 'showLoading'],
        methods: {
            getThemeItemData: function () {
                const params = {themeItemId: this.themeItemId};
                getApplyThemeItemData(params).then(res => {
                    const re = res.data.data;
                    if (re.data.length > 0) {
                        this.pageSize = re.data[0].itemDataValue;
                        this.onLoadGoodsList();
                    }
                    this.$emit('is-loading', 1);
                })
            },
            onLoadGoodsList(params){
                getGoodsList(Object.assign({
                    pageSize: this.pageSize,
                    deleteStatus: false,
                    goodsState: 1
                }, params)).then(res => {
                    if (SYSTEM.CODE_IS_OK == res.data.data.code) {
                        const re = res.data.data.data.data;
                        /*if (re.length <= 0) {
                            this.$emit('up-dom', this.themeItemId);
                            return
                        }*/
                        this.itemList = re;
                        this.$nextTick(function () {
                            this.$emit('showLo');
                        })
                    } else {
                        this.$emit('up-dom', this.themeItemId);
                    }
                });
            },
            tabsChange(index){
                const tab = this.tabs[index];
                if(tab){
                    if(index === 1){
                        tab.value.endExchangeIntegralPrice = this.$store.state.appUser.appUserData.credits || 0;
                    }
                   this.onLoadGoodsList(tab.value);
                }
            }
        },
        created() {
            this.getThemeItemData();
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .gridLists {
        .lists {
            flex-wrap: wrap;

            .list {
                width: 3.67rem;
                margin: 0 0.16rem 0.16rem 0;
                background: #fff;

                .img-wrap {
                    width: 100%;

                    img {
                        width: 100%;
                    }
                }

                .info-wrap {
                    padding: 0.1rem 0.3rem 0.2rem;

                    .item-title {
                        font-weight: normal;
                        font-size: 0.28rem;
                        white-space: nowrap;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        margin-bottom: 0.1rem;
                        color: #333;
                    }

                    .item-credits {
                        font-size: 0.26rem;
                    }

                    del {
                        margin-left: 0.2rem;
                    }
                }

                &:nth-child(even) {
                    margin-right: 0;
                }
            }

        }
    }
</style>
