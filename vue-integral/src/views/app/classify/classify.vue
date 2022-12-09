<template>
    <div class="container">
        <div class="classify-image" v-if="category.categoryBannerShow">
            <img :src="category.categoryBanner.path + category.categoryBanner.name">
        </div>
        <app-itemList :type="params.type" :value="params.value" @is-loading="isLoading()"></app-itemList>

        <transition name="app-loading">
            <app-loading v-show="loading" style="z-index: 1;"></app-loading>
        </transition>
    </div>
</template>

<script>
    import appItemList from '@/components/app/itemList';
    import loading from '@/components/app/loading';

    import {getCategoryDetails} from '@/service/getAppData';

    export default {
        data() {
            return {
                loading: true,
                category: {categoryBannerShow: false, categoryBanner: null},
                params: {type: this.$route.query.type, value: this.$route.query.data}
            }
        },
        beforeRouteEnter(to, from, next) {
            if ('category' == to.query.type) {
                next(vm => vm.setData(to.query.data))
            } else {
                next();
            }
        },
        methods: {
            setData(data) {
                getCategoryDetails(data).then(res => {
                    if (res.data.data.success) {
                        this.category = res.data.data.data;
                    }
                })
            },
            isLoading (v){
                this.loading = v;
            }
        },
        components: {
            'app-itemList': appItemList,
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

    .classify-image {
        width: 100%;
        img {
            width: 100%;
        }
    }
</style>
