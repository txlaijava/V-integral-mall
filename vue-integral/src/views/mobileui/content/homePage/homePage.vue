<template>
    <div class="main">
        <div class="page-title">
            <h2>页面内容配置</h2>
            <div class="page-content" id="page-content">
                <div class="content-wrapper content-homepage" :class="{'active':picker}" id="content-wrapper">
                    <div class="phone-wrapper">
                        <div class="phone-header">
                            <span class="circle"></span>
                            <span class="line"></span>
                        </div>
                        <div class="phone-content">
                            <div class="preview-list">
                                <div class="preview-item" v-for="(item,index) in applyModule" :key="item.id"
                                     @click="opNewEditorWrapper(index,item.theme_item_type)"
                                     v-if="item.theme_item_state == 0">
                                    <div
                                        :class="item.theme_item_type == 'double_icon' || item.theme_item_type == 'single_icon' ? 'icon-item' : item.theme_item_type+'-item'">
                                        <div class="cover" :class="{'active':itemActive == index}"
                                             v-if="!editorData[item.id] || editorData[item.id].length <=0 || itemActive != index">
                                            <img :src="imageWebServer + item.theme_item_icon">
                                            <p class="floor-title">
                                                <span>{{item.theme_item_name}}</span>
                                            </p>
                                        </div>

                                        <!-- 广告图展示 -->
                                        <img :src="imageWebServer + editorData[item.id][0].itemDataImg"
                                             v-else-if="editorType == 'banner'">

                                        <!-- 图标展示 -->
                                        <div class="iconitem"
                                             v-else-if="editorType == 'single_icon' || editorType == 'double_icon'"
                                             v-for="(icon,i) in editorData[item.id].slice(0,4)">
                                            <img :src="imageWebServer + icon.itemDataImg">
                                            <p>{{icon.itemDataName}}</p>
                                        </div>

                                        <!-- 商品展示 -->
                                        <div class="itemitem" v-else-if="editorType == 'goods'"
                                             v-for="goodsItem in editorData[item.id]">
                                            <div>
                                                <img :src="imageWebServer + goodsItem.goodsImg">
                                            </div>
                                            <div>
                                                <p>{{goodsItem.goodsName}}</p>
                                                <p>{{goodsItem.goodsPrice}}积分</p>
                                            </div>
                                            <a href="javascript:;">兑换</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="phone-footer">
                            <i class="iconfont" style="font-size: 12px;">&#xe769;</i>实际效果以手机界面为准,
                            <a class="link-normal" target="_blank" href="">手机预览</a>
                        </div>
                    </div>

                    <v-banner v-if="editorType == 'banner'" :show-loading="loading" @load="upLoading"
                              :theme-item="applyModule[itemActive]" @picker="showPicker"
                              @select-data="upApplyModule"></v-banner>
                    <v-icon v-else-if="editorType == 'single_icon' || editorType == 'double_icon'"
                            :show-loading="loading" @load="upLoading" :theme-item="applyModule[itemActive]"
                            @picker="showPicker" @select-data="upApplyModule"></v-icon>
                    <v-showcase v-else-if="editorType == 'showcase'" :show-loading="loading" @load="upLoading"
                                :theme-item="applyModule[itemActive]" @picker="showPicker"></v-showcase>
                    <v-goods v-else-if="editorType == 'goods'" :show-loading="loading" @load="upLoading"
                             :theme-item="applyModule[itemActive]" @picker="showPicker"
                             @select-data="upApplyModule"></v-goods>
                </div>
            </div>
        </div>

        <transition name="loading">
            <v-loading v-show="loading"></v-loading>
        </transition>
    </div>
</template>

<script>
    import {getMobileuiFloor} from '@/service/getData';
    import editorBanner from '@/components/content/banner';
    import editorIcon from '@/components/content/icon';
    import editorShowcase from '@/components/content/showcase';
    import editorGoods from '@/components/content/goods';

    import * as SYSTEM from '@/api/system';
    import config from '@/config/config';

    export default {
        data() {
            return {
                imageWebServer: config.ossImgPath,
                applyModule: [],
                itemActive: 0,
                editorType: '',
                loading: true,
                picker: false,
                editorData: {},
            }
        },
        components: {
            'v-banner': editorBanner,
            'v-icon': editorIcon,
            'v-showcase': editorShowcase,
            'v-goods': editorGoods
        }
        , methods: {
            getMobileuiFloor: function () {
                const params = {themeItemState: '0'};
                getMobileuiFloor(params).then(res => {
                    let re = res.data.data;
                    this.applyModule = re.data.applyThemeItem;
                    this.editorType = this.applyModule[0].theme_item_type;
                })
            },
            opNewEditorWrapper: function (i, type) {
                if (this.itemActive == i) {
                    return;
                }
                this.loading = true;
                this.itemActive = i;
                this.editorType = type;
            },
            showPicker: function (v) {
                this.picker = v;
            },
            upApplyModule: function (v) {
                this.editorData[this.applyModule[this.itemActive].id] = v;
                // 这段代码为了触发 对象发生改变 页面重新渲染事件
                this.editorData = Object.assign({}, this.editorData);
            },
            upLoading: function (v) {
                this.loading = v;
            }
        }, created() {
            this.getMobileuiFloor();
        },
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "../../../../style/interface.css";

    .preview-list .preview-item {
        height: auto;
        padding-bottom: 0;
    }

    .itemitem img {
        width: 97px !important;
        height: 61px !important;
        display: block !important;
    }
</style>
