<template>
    <div class="scroll">
        <div class="scroll-x">
            <ul class="lists flex">
                <li class="list" v-for="(item,index) in submenuItems" :key="item.id">
                    <a href="javascript:void(0)" class="flex" @click="go_to(item.itemDataType,item.itemDataValue)">
                        <img v-lazy="imageWebServer + item.itemDataImg">
                        <p>{{item.itemDataName}}</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import {getApplyThemeItemData} from '@/service/getAppData';
    import * as SYSTEM from '@/api/system';
    import config from '@/config/config';

    export default {
        data() {
            return {
                imageWebServer: config.ossImgPath,
                submenuItems: []
            }
        },
        props: ['themeItemId', 'showLoading'],
        methods: {
            getThemeItemData: function () {
                const params = {themeItemId: this.themeItemId};
                getApplyThemeItemData(params).then(res => {
                    const re = res.data.data.data;
                    console.log(re);
                    this.submenuItems = re;
                    if (re.length < 1) {
                        this.$emit('up-dom', this.themeItemId);
                    }
                    this.$emit('is-loading', 1)
                })
            },
            go_to(type,obj) {
                switch (type) {
                    case 'goods' :
                        this.$router.push({path : 'itemDetail/'+obj});
                        break;
                    default:
                        this.$router.push({name : 'classify',query: {type: type,data:obj}});
                        break;
                }
            }
        },
        created() {
            this.getThemeItemData();
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .scroll {
        background: #FFF;
        width: 100%;
        margin-bottom: .1rem;
        padding: 0.3rem 0 0.1rem;
        .lists {
            flex-wrap: wrap;
            justify-content: flex-start;
            .list {
                width: 25%;
                margin-bottom: 0.2rem;
                .flex {
                    flex-flow: column;
                    align-items: center;
                    justify-content: center;
                    img {
                        width: 0.92rem;
                        height: 0.92rem;
                        margin-bottom: 0.1rem;
                    }
                }
            }
        }
    }
</style>
