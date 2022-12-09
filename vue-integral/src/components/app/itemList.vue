<template>
    <div class="obj-list">
        <ul class="items" v-if="itemList.length > 0">
            <li class="item" v-for="(item,index) in itemList" :key="index">
                <router-link :to="{path:'itemDetail/'+item.id}" class="flex">
                    <div class="img-wrap">
                        <img :src="imageWebServer + item.goodsIconPhoto.path + item.goodsIconPhoto.name">
                    </div>
                    <div class="item-info side-auto flex flex1">
                        <h3>{{item.goodsName}}</h3>
                        <p>
                            <span class="item-credits t-red" v-if="1 == item.exchangeRules.exchangePriceType">
                                {{item.exchangeRules.exchangeIntegralPrice}}积分
                            </span>
                            <span class="item-credits t-red" v-else>
                                {{item.exchangeRules.exchangeIntegralPrice}}积分 + {{item.exchangeRules.exchangePrice}}元
                            </span>
                        </p>
                    </div>
                    <div class="btn-wrap">
                        <button type="" class="item-btn t-red">
                            兑换
                        </button>
                    </div>
                </router-link>
            </li>
        </ul>

        <p class="no-more" v-else>已经没有更多啦！</p>
    </div>
</template>

<script>
    import {getGoodsList} from '@/service/getAppData';
    import config from '@/config/config';

    export default {
        props: ['type', 'value'],
        data() {
            return {
                imageWebServer: config.ossImgPath,
                itemList: []
            }
        },
        methods: {
            getItem() {
                let params = {pageSize: -1, deleteStatus: false};
                if ('coupons' == this.type || 'entity' == this.type || 'virtual' == this.type) {
                    params.goodsType = this.value;
                } else if ('category' == this.type) {
                    params.categoryId = this.value;
                }
                getGoodsList(params).then(res => {
                    if (res.data.data.success) {
                        const re = res.data.data.data;
                        this.itemList = re.data;
                    }
                    this.$emit('is-loading', false);
                });
            }
        },
        created() {
            this.getItem();
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .obj-list {
        .flex {
            display: -webkit-box;
            display: -ms-flexbox;
            display: flex;
            word-break: break-all;
            align-items: center;
        }
        .item {
            overflow: hidden;
            position: relative;
            padding: .3rem;
            border-top: solid #eee 1px;
            background-color: #fff;
            background-position: 3.02rem 50%;
            background-size: .08rem;
            &:first-child {
                border-top: none;
            }
            .img-wrap {
                width: 2.58rem;
                height: 1.6rem;
                margin-right: 0.2rem;
                img {
                    width: 100%;
                    height: 100%;
                }
            }
            .side-auto {
                padding: 0.1rem 0;
                box-sizing: border-box;
                height: 1.6rem;
                align-content: space-between;
                flex-direction: column;
                align-items: flex-start;
                margin-right: 0.16rem;
                h3 {
                    font-size: 0.32rem;
                    font-weight: 500;
                    line-height: 1.4;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;
                    -webkit-box-orient: vertical;
                    -webkit-line-clamp: 2;
                }
                .item-credits {
                    font-size: .28rem;
                    vertical-align: middle;
                    display: inline-block;
                }
            }
            .btn-wrap {
                .item-btn {
                    font-size: 0.26rem;
                    padding: 0.06rem 0.2rem;
                    background: #fff;
                    border: 1px solid #ff484a;
                    border-radius: 0.1rem;
                }
            }
        }
    }
</style>
