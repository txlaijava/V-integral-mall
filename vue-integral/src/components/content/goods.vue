<template>
    <div>
        <div class="editor-wrapper item">
            <header>编辑商品区
                <a href="javascript:;" class="btn btn-green btn-lg btn-add" @click="picker()">添加商品</a>
            </header>
            <div class="editor-content">
                <div class="top-options">
                    <span class="itemType">
                        <label>选择商品</label>
                        <div class="select-group">
                            <Select v-model="classify"  @on-change="updateThemeItemData()">
                                <Option v-for="item in classifyList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                        </div>
                    </span>
                    <span class="fr pageSize">
                        <label>首页展示的商品数</label>
                        <div class="select-group">
                            <Select v-model="pageSize" @on-change="updateThemeItemData()">
                                <Option value="20" label="20">
                                    <span>20</span>
                                    <span style="float:right;color:#ccc">20</span>
                                </Option>
                                <Option value="30" label="30">
                                    <span>30</span>
                                    <span style="float:right;color:#ccc">30</span>
                                </Option>
                                <Option value="40" label="40">
                                    <span>40</span>
                                    <span style="float:right;color:#ccc">40</span>
                                </Option>
                                <Option value="50" label="50">
                                    <span>50</span>
                                    <span style="float:right;color:#ccc">50</span>
                                </Option>
                                <Option value="100" label="100">
                                    <span>100</span>
                                    <span style="float:right;color:#ccc">100</span>
                                </Option>
                                <Option value="300" label="300">
                                    <span>300</span>
                                    <span style="float:right;color:#ccc">300</span>
                                </Option>
                            </Select>
                        </div>
                    </span>
                </div>
                <div class="editor-full">
                    <div class="editor-list-wrapper">
                        <div class="s-item" v-for="(item,index) in goodsItem" :key="item.index" :class="{'s-top' : item.top}">
                            <div class="s-con">
                                <div class="s-img">
                                    <img :src="imageWebServer + item.goodsMainPhoto.path + item.goodsMainPhoto.name"
                                         lazy="loaded">
                                    <span class="s-index">{{(index + 1)}}</span>
                                </div>
                                <div class="s-options">
                                    <p>{{item.goodsName}}</p>
                                    <Tooltip placement="top" content="下架">
                                        <a href="javascript:void(0)" class="iconfont delete sold_out"
                                           @click="updateGoodsAjax(index,'down',item.id)"></a>
                                    </Tooltip>
                                    <Tooltip placement="top" content="置顶" v-if="!item.top">
                                        <a href="javascript:void(0)" class="iconfont icon-top" @click="setTop(index,item.id,true)"></a>
                                    </Tooltip>
                                    <Tooltip placement="top" content="取消置顶" v-else>
                                        <a href="javascript:void(0)" class="iconfont cancel_top" @click="setTop(index,item.id,false)"></a>
                                    </Tooltip>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="s-item s-empty" v-show="goodsItem.length < 1"></div>
                    <div class="supplementTip" v-if="goodsItem.length < 50">上架商品不足50，
                        <a href="javascript:;" class="link-normal">立即开启</a>自动补充。
                    </div>
                </div>
            </div>
            <div class="editor-bar" @click="picker()">
                <i class="iconfont">&#xe647;</i>
            </div>
        </div>

        <!-- 选择商品-->
        <div class="picker-wrapper item">
            <header>挑选商品
                <ul class="picker-menu">
                    <li :class="{'active':pickerMenusType == 2}" @click="pickerMenusTab(2)">优惠券</li>
                    <li :class="{'active':pickerMenusType == 1}" @click="pickerMenusTab(1)">实物类</li>
                </ul>
            </header>
            <div class="picker-content" style="width: 812px;">
                <div class="item-search" channel="object">
                    <div class="filter-form" v-if="pickerMenusType != 'recharge'">
                        <div class="filter-input">
                            <input type="text" name="" placeholder="请输入关键词"> <i class="iconfont">&#xe62c;</i>
                        </div>
                    </div>
                    <div class="result-list">
                        <div class="result-item" v-for="(item,index) in lower_goods">
                            <div class="i-content">
                                <div>
                                    <img :src="imageWebServer + item.goodsMainPhoto.path + item.goodsMainPhoto.name"
                                         lazy="loaded">
                                </div>
                                <div style="padding: 10px 15px">
                                    <p>{{item.goodsName}}</p>
                                    <p>{{item.exchangeRules.exchangeIntegralPrice}}积分</p>
                                </div>
                            </div>
                            <div class="cover" @click="updateGoodsAjax(index,'up',item.id)">
                                <span><i class="iconfont">&#xe766;</i></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <a href="javascript:;" class="close iconfont" @click="picker()">&#xe695;</a>
            <div class="picker-bar" @click="picker()">
                <i class="iconfont">&#xe647;</i>
            </div>
        </div>
    </div>
</template>

<script>
    import {
        getApplyThemeItemData,
        saveApplyThemeItemData,
        delApplyThemeItemData,
        getGoodsList, updateGoods
    } from '@/service/getData';
    import config from '@/config/config';

    export default {
        data() {
            return {
                theme_item_data:null,
                imageWebServer: config.ossImgPath,
                goodsItem: [],
                lower_goods: [],
                isPicker: false,
                pickerMenusType: 2,
                classifyList: [
                    {
                        value: '-1',
                        label: '全部'
                    }
                ],
                classify: '-1',
                pageSize: '20'
            }
        },
        props: ['themeItem', 'showLoading'],
        methods: {
            picker: function () {
                this.isPicker = !this.isPicker;
                this.$emit('picker', this.isPicker);
                if (this.isPicker) {
                    this.pickerMenusTab(2);
                }
            },
            // 菜单切换
            pickerMenusTab: function (v) {
                this.pickerMenusType = v;
                this.getGoodsData(3, v);
            },
            // 上架下架 ( 添加商品楼层数据,删除商品楼层数据 )
            updateGoodsAjax (index,type, id) {
                updateGoods({type: type, ids: id}).then((res) => {
                    const param = res.data.data;
                    if (param.status == 1000) {
                        this.$Message.success("操作成功");
                        if('up' == type){
                            this.goodsItem.unshift(this.lower_goods[index]);
                            this.lower_goods.splice(index,1);
                        }else{
                            this.lower_goods.unshift(this.goodsItem[index]);
                            this.goodsItem.splice(index,1);
                        }
                    }
                })
            },
            // 获取商品楼层数据
            getThemeItemData: function () {
                getApplyThemeItemData(this.themeItem.theme_item_id).then(res => {
                    const re = res.data.data;
                    if (re.data.length > 0) {
                        this.theme_item_data = re.data[0];
                        this.classify = re.data[0].itemDataLike;
                        this.pageSize = re.data[0].itemDataValue;
                        this.getGoodsData(1);
                    }
                });
            },
            // 添加商品楼层数据
            updateThemeItemData (){
                [this.theme_item_data.itemDataLike,this.theme_item_data.itemDataValue,this.theme_item_data.themeItemId] = [this.classify,this.pageSize,this.themeItem.theme_item_id];
                saveApplyThemeItemData(this.theme_item_data).then(res =>{
                    if(res.data.data.success){
                        this.$Message.success('设置成功');
                        this.getGoodsData(1);
                    }else{
                        this.$Message.error(res.data.data.message);
                    }
                })
            },
            // 获取商品数据 (商品状态，商品类型，商品名称)
            getGoodsData(goods_state, goods_type, goods_name) {
                this.$emit('load', true);
                let pageSize = -1;
                if (1 == goods_state) {
                    pageSize = this.pageSize;
                }
                getGoodsList({
                    pageSize: pageSize,
                    deleteStatus: false,
                    goodsState: goods_state,
                    goodsType: goods_type || '',
                    goodsName: goods_name || ''
                }).then(res => {
                    if (0 == res.data.data.code) {
                        const re = res.data.data.data.data;
                        if (1 != goods_state) {
                            this.lower_goods = re;
                        } else {
                            this.goodsItem = re;
                        }
                    } else {
                        this.$Message.error(res.data.data.message);
                    }
                    this.$emit('load', false);
                })
            },
            setTop (index,goods_id,is_top){
                updateGoods({type:'top',ids:goods_id,value:is_top}).then(res =>{
                    console.log(res);
                    if(res.data.data.success){
                        this.$Message.success('设置成功');
                        this.goodsItem[index].top = is_top;
                        if(is_top){
                            this.goodsItem[index].topTime = new Date().getTime();
                        }else{
                            this.goodsItem[index].topTime = null;
                        }
                        this.goodsItem.sort(function(a, b){
                            const a_top = a.top ? 1 : 0;
                            const b_top = b.top ? 1 : 0;
                            const a_topTime = a.topTime ? a.topTime : 0;
                            const b_topTime = b.topTime ? b.topTime : 0;
                            return b_top === a_top ? b_topTime - a_topTime : b_top - a_top;
                        });
                    }else{
                        this.$Message.error(res.data.data.message);
                    }
                })
            }
        },
        created() {
            this.getThemeItemData();
        },
        watch: {
            goodsItem: function (val, oldVal) {
                var data = [];
                val.forEach(v =>{
                    const goodsImg = v.goodsMainPhoto.path + v.goodsMainPhoto.name;
                    data.push({id:this.theme_item_data.id,goodsId:v.id,goodsImg:goodsImg,goodsName:v.goodsName,goodsPrice:v.exchangeRules.exchangeIntegralPrice})
                });
                this.$emit('select-data', data);
            }
        }

    }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style lang="scss" scoped>
    @import "../../style/interface.css";
    .editor-wrapper.item .s-item.s-top .s-con:after {
        content: "\7F6E\9876";
        position: absolute;
        display: inline-block;
        top: 0;
        right: 0;
        font-size: 14px;
        color: #fff;
        line-height: 24px;
        background-color: #29b6b0;
        padding: 0 8px;
        border-bottom-left-radius: 5px;
    }
</style>
