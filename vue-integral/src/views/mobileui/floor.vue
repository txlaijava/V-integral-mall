<template>
    <div class="main">
        <div class="page-title"><h2>{{floor.pageTitle}}</h2></div>
        <div class="pagecontent">
            <div class="content-wrapper">
                <div class="phone-wrapper">
                    <div class="phone-header"><span class="circle"></span> <span class="line"></span>
                    </div>
                    <div class="phone-content">
                        <div class="preview-list" v-for="(item,index) in applyModule">
                            <div class="preview-item">
                                <div class="preview-body sign"
                                     :style="{ background: 'url('+ imageWebServer + item.theme_item_icon+')  no-repeat' }"></div>
                            </div>
                        </div>
                    </div>
                    <div class="phone-footer">
                        <div class="phone-footer-btn" @click="saveSub()">{{floor.phoneBtn}}</div>
                    </div>
                </div>
                <div class="select-wrapper">
                    <div class="heard">{{floor.selectHeader}}</div>
                    <div class="select-content">
                        <div class="cf">
                            <p class="top-desc">当前首页共有<span class="blue">{{applyModule.length}}</span>个楼层模块,拖动模块可以给楼层排序
                            </p>
                            <draggable element="ul" class="list-group select-module" v-model="applyModule"
                                       @end="onEnd()">
                                <li v-for="(item,index) in applyModule" :id="'select-module'+index" :key="index">
                                    <div class="card card-rotate">
                                        <div class="card-cover" style="display: none">
                                            <div class="move"><i class="iconfont icon-move"></i></div>
                                        </div>
                                        <div class="card-body sign"
                                             :style="{ background: 'url('+ imageWebServer + item.theme_item_icon+')  no-repeat' }"
                                             @mousedown="down(index)" @mouseup="up(index)"></div>

                                        <div class="card-footer">{{item.theme_item_name}}</div>
                                        <i class="icon-check icon-check-active iconfont icon-close_semiCircle"
                                           @click.stop="removeModule(index,item.theme_item_id)"></i>
                                    </div>
                                </li>
                                <li class="empty-floor disabled" v-if="applyModule.length < module.length">
                                    <div class="card empty-card"></div>
                                </li>
                            </draggable>
                        </div>
                        <div class="top-desc">
                            <div class="text-line">待选楼层模块</div>
                            <div class="line"></div>
                        </div>
                        <ul class="floor-module-list">
                            <li v-for="(item,index) in module" :id="(index+1)" v-if="!item.isChoose">
                                <div class="card card-noactive">
                                    <div class="card-body task-gray"
                                         :style="{ background: 'url('+imageWebServer + item.themeItemIcon+')  no-repeat' }"></div>
                                    <div class="card-footer type-name">{{item.themeItemName}}</div>
                                    <i class="icon-check icon-check-active iconfont icon-close_semiCircle"
                                       @click.stop="addModule(index)"></i>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <transition name="loading">
            <v-loading v-show="loading"></v-loading>
        </transition>
    </div>
</template>

<script>
    import {getMobileuiFloor, putApplyThemeItem} from '@/service/getData';
    // 引入jq
    import $ from "jquery";
    import draggable from 'vuedraggable';
    import config from '@/config/config';

    export default {
        data() {
            return {
                imageWebServer:config.ossImgPath,
                floor: {
                    "pageTitle": "首页楼层配置",
                    "phoneBtn": "保存设置",
                    "selectHeader": "选择楼层模块"
                },
                module: [],
                applyModule: [],
                loading: true
            }
        },
        components: {draggable},
        methods: {
            getMobileuiFloor: function () {
                getMobileuiFloor().then(res => {
                    let re = res.data.data;
                    this.applyModule = re.data.applyThemeItem;
                    re.data.themeItem.find(element => {
                        this.loading = false;
                        re.data.applyThemeItem.find(elements => {
                            if (element.id == elements.theme_item_id) {
                                element.isChoose = true;
                                return;
                            }
                        });
                    });
                    this.module = re.data.themeItem;
                })
            },
            down: function (event) {
                event = $('#select-module' + event);
                $('.list-group.select-module li').removeClass('sortable-chose');
                event.addClass('sortable-chose');
                event.find('.card-cover').show();
                event.attr('draggable', true);
            },
            up: function (event) {
                event = $('#select-module' + event);
                $('.list-group.select-module li').removeClass('sortable-chose');
                $(event).find('.card-cover').hide();
                event.attr('draggable', false);
            }, onEnd: function () {
                $('.card-cover').hide();
            }, removeModule: function (index, id) {
                this.applyModule.splice(index, 1);
                const i = this.module.findIndex(item => item.id === id);
                this.module[i].isChoose = false;
            }, addModule: function (index) {
                this.module[index].isChoose = true;
                const add_module = this.module[index];
                let obj = {
                    theme_item_id: add_module.id,
                    theme_item_icon: add_module.themeItemIcon,
                    theme_item_name: add_module.themeItemName
                };
                this.applyModule.push(obj);
            }, saveSub: function () {
                let save_module = [];
                let index = 0;
                this.applyModule.forEach(v => {
                    save_module.push({id: v.theme_item_id, sequence: index});
                    index++;
                });
                putApplyThemeItem({module: JSON.stringify(save_module)}).then(res => {
                    console.log(res.data.data);
                    if(0 == res.data.data.code){
                        this.$Message.success('楼层配置成功');
                    }
                })
            }
        },
        created() {
            this.getMobileuiFloor();
        },
        mounted() {
        }, watch: {}
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "../../style/interface.css";
</style>
