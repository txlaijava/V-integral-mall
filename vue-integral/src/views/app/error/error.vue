<template>
    <div class="container">
        <div class="header">
            <div class="tipImg">
                <img src="../../../../static/500.png" alt="">
            </div>
            <p class="tipText t-center t-333" v-text="tipText"></p>
        </div>
        <div class="app_btn flex">
            <a class="bg--orange" href="javascript:void(0);" @click="$router.go(-1);">返回上一页</a>
            <router-link to="home" class="bg-theme">返回首页</router-link>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tipText: ''
            }
        },
        methods: {
            getTipText() {
                this.tipText = this.$route.params.message;
            }
        },
        created() {
            this.getTipText();
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .container {
        .header {
            margin-top: 1.5rem;
            .tipImg {
                width: 4.74rem;
                height: 2.6rem;
                margin: 0 auto;
                img {
                    width: 100%;
                    height: 100%;
                }
            }
        }
        .tipText {
            font-size: 0.3rem;
            margin-top: 0.4rem;
        }
        .app_btn {
            justify-content: space-around;
            margin: 1rem 0;
            padding: 0 0.6rem;
            a {
                width: 2.5rem;
                display: inline-block;
            }
        }
    }
</style>
