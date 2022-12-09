<template>
    <div>
        <div class="header" :class="{ 'white-header': isInverse }">
            <div class="header-wrapper">
                <div class="page-container">
                    <router-link to="" class="logo"></router-link>
                    <div class="nav" data-v-1a9bb128="">
                        <router-link to="/" class="nav-link nav-link-active">{{selsctNav}}</router-link>
                        <router-link to="/" class="nav-link nav-link_2">产品<i class="iconfont icon-arrowDown"></i>
                            <ul class="childnav">
                                <li v-for="(item,index) in navs" @click="onhandleClick(index)">
                                    <router-link :to="item.link" class="product-nav-link">
                                        <p class="product-nav-title t-left">{{item.title}}</p>
                                        <p class="product-nav-subtit t-left">{{item.subtit}}</p>
                                    </router-link>
                                </li>
                            </ul>
                        </router-link>
                        <a href="http://doc.xxxxx.cn" target="_blank" class="nav-link">帮助手册</a>
                        <router-link to="/admin/signup" class="btn">注册</router-link>
                        <router-link to="/admin/login" class="btn btn-select">登录</router-link>
                    </div>
                </div>
            </div>
        </div>
        <keep-alive>
            <router-view></router-view>
        </keep-alive>
        <div class="footer">
            <div class="page-container">
                <p>产品接入：<span>10105188</span>&nbsp;&nbsp;&nbsp;QQ：<span>5681540</span>&nbsp;&nbsp;&nbsp;邮箱：<span>5681540@qq.com</span>
                </p>
                <p>Copyright © 2017 - 2020 红人网科技. <a href="http://www.miibeian.gov.cn/" target="_blank" style="color:#ddd">赣ICP备20003335号-1</a>
                </p>
                <p>
                    <img src="https://rblc.oss-cn-qingdao.aliyuncs.com/other/%E5%A4%87%E6%A1%88%E5%9B%BE%E6%A0%87.png" />
                    <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=36010302000360" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;"><img src="" style="float:left;"/><p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#939393;">赣公网安备 36010302000360号</p></a>
                </p>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                isInverse: false,
                active: '',
                selsctNav: '云猫 IOP',
                navs: [
                    {index: 0, title: '云猫 IOP', subtit: '企业级用户积分运营平台', link: 'home'},
                    {index: 1, title: '云猫 CloudMall', subtit: '综合体商城数字化运营服务平台', link: 'cloudMall'},
                    {index: 2, title: '云猫 PayMall', subtit: '综合体商城数字化支付解决方案', link: ''},
                    {index: 3, title: '云猫 MCT', subtit: '智能WIFI系统与客流分析统计', link: 'MCT'},
                    {index: 4, title: '云猫 连锁', subtit: '连锁门店解决方案', link: 'chainShop'},
                    {index: 5, title: '云猫 集采', subtit: '门店集采解决方案', link: 'wholesale'}
                ],
            }
        },
        methods: {
            handleScroll () {
                var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
                if (scrollTop >= 70) {
                    this.isInverse = true
                } else {
                    this.isInverse = false
                }
            },
            onhandleClick(index){
//                timer = setInterval(function () {
//                    let osTop = document.documentElement.scrollTop || document.body.scrollTop
//                    let ispeed = Math.floor(-osTop / 5)
//                    document.documentElement.scrollTop = document.body.scrollTop = osTop + ispeed
//                    this.isTop = true
//                    if (osTop === 0) {
//                        clearInterval(timer)
//                    }
//                }, 30)
                document.documentElement.scrollTop = document.body.scrollTop = 0;
                this.selsctNav = this.navs[index].title
            }
        },
        mounted () {
            window.addEventListener('scroll', this.handleScroll)
        },
        creatrd () {
            that.handleScroll()
        }

    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "../../style/website/website.css";

</style>
