<template>
  <div class="container">
    <section class="floor-top" v-if="!unlogin">
      <template>
        <ul class="flex">
            <li>
              <div class="unlogin-text">兑换前请先登录</div>
            </li>
            <li><router-link to="" class="unlogin-btn t-center">立即登录</router-link></li>
        </ul>
      </template>
    </section>
    <section v-for="(item,index) in themeItem" :class="'floor-'+item.theme_item_type">
      <ul class="flex" v-if="'top' == item.theme_item_type">
        <template v-if="unlogin">
          <li class="flex1">
            <a href="javascript:void(0)" class="credits flex" @click="toDetails">
              <i class="i-credits iconfont">&#xe773;</i>
              <span>积分<span class="number t-red" id="db-credits-num" v-text="myCredits"></span></span>
            </a>
          </li>
          <li class="flex1">
            <router-link to="record" class="records flex">
              <i class="i-credits iconfont">&#xe774;</i>
              <span class="note">兑换记录</span>
            </router-link>
          </li>
        </template>
      </ul>

      <app-banner heigth="2.2rem" v-else-if="'banner' == item.theme_item_type" :bannerType="'home'" :themeItemId="item.theme_item_id"
                  @is-loading="isLoading" @up-dom="upDom"></app-banner>

      <app-showCase v-else-if="'showcase' == item.theme_item_type" :themeItemId="item.theme_item_id"
                    @is-loading="isLoading" @up-dom="upDom"></app-showCase>

      <app-submenu v-else-if="'double_icon' == item.theme_item_type" :themeItemId="item.theme_item_id"
                   @is-loading="isLoading" @up-dom="upDom"></app-submenu>

      <app-single-icon v-else-if="'single_icon' == item.theme_item_type" :themeItemId="item.theme_item_id"
                       @is-loading="isLoading" @up-dom="upDom"></app-single-icon>

      <div v-else-if="'goods' == item.theme_item_type">
        <div class="floor-item-title">
          <span class="title-txt">精选推荐</span>
          <span class="title-txt-y t-red"> POPULAR</span>
        </div>
        <app-gridItemList :themeItemId="item.theme_item_id" @is-loading="isLoading" @up-dom="upDom"
                          v-on:showLo="showLo"></app-gridItemList>
        <p class="no-more" v-if="showLoad">正在加载中</p>
        <p class="no-more" v-else>已经没有更多啦！</p>
      </div>

    </section>
    <transition name="app-loading">
      <app-loading v-show="loading" style="z-index: 1;"></app-loading>
    </transition>
  </div>
</template>

<script>
  import appBanner from '@/components/app/banner';
  import appSubmenu from '@/components/app/submenu';
  import appGridItemList from '@/components/app/gridItemList';
  import appSingleIcon from '@/components/app/single_icon';
  import appShowCase from '@/components/app/showcase';
  import loading from '@/components/app/loading'
  import {getApplyThemeItem,getLoginInfo,getApply} from '@/service/getAppData';
  import * as SYSTEM from '@/api/system';
  import config from '@/config/config';

  // vuex状态管理
  import { mapActions } from 'vuex';
  export default {
    data () {
      return {
          imageWebServer:config.ossImgPath,
          themeItem:[],
          moduleSize: 1,
          loading:true,
          integralDetails:null,
          showLoad:true,

      }
    },
    computed: {
      unlogin () {
          return this.$store.state.appUser.appLoginStatus;
      },
      myCredits () {
          return this.$store.state.appUser.appUserData.credits;
      }
    },
    methods: {
        ...mapActions(['getAppUserData','setAppUserInfo']),
        getActivityBannerUrl: function () {
            getApplyThemeItem().then((res) => {
                this.themeItem = res.data.data.data.applyThemeItem;
            })
        },
        upDom:function (v) {
            const index = this.themeItem.findIndex(obj => obj.theme_item_id === v);
            this.themeItem.splice(index,1);
            this.moduleSize --;
        },
        isLoading(v){
            const size = this.themeItem.length;
            this.moduleSize += v;
            if(size == this.moduleSize){
                this.loading = false;
            }
        },
        toDetails(){
            if(this.integralDetails){
                return;
            }
            getApply().then(res =>{
                if(res.data.data.success){
                    this.integralDetails = res.data.data.data;
                    if(this.integralDetails.integralDetailedLink){
                        window.location.href = this.integralDetails.integralDetailedLink;
                    }
                }
            })
        },
        showLo(){
            this.showLoad = false;
        }
    },
    created () {
        this.getActivityBannerUrl();
        getLoginInfo().then(res => {
            if(200 == res.status && SYSTEM.CODE_IS_OK == res.data.code && SYSTEM.SUCCSSS == res.data.data.success){
                const re = res.data.data.data;
                this.getAppUserData(re);
                this.setAppUserInfo(re);
            }
        });
    },
    components: {
      'app-submenu': appSubmenu,
      'app-banner': appBanner,
      'app-gridItemList': appGridItemList,
      'app-single-icon':appSingleIcon,
      'app-showCase': appShowCase,
      'app-loading': loading
    }
  }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style rel="stylesheet/scss" lang="scss" scoped>
  .app-loading-enter-active, .app-loading-leave-active {
    transition: opacity .3s
  }
  .app-loading-enter, .app-loading-leave-active {
    opacity: 0
  }
  .floor-top {
    width: 100%;
    height: 0.88rem;
    background-color: #fff;
    ul {
      align-items: center;
      height: 0.88rem;
      .unlogin-text {
        font-size: 0.28rem;
        margin-left: 0.3rem;
      }
      .unlogin-btn {
        width: 1.5rem;
        height: 0.6rem;
        line-height: 0.6rem;
        font-size: 0.28rem;
        background: #ff484a;
        color: #fff;
        border-radius: 0.1rem;
        display: block;
        margin-right: 0.3rem;
      }
    }
    .credits {
      border-right: 1px solid #efefef;
    }
    .credits, .records {
      line-height: 0.88rem;
      justify-content: center;
      font-size: 0.26rem;
      color: #333;
      .iconfont {
        font-size: 0.36rem;
        margin-right: 0.1rem;
        color: #333;
      }

    }

  }

  .floor-banner {
    width: 100%;
    overflow: hidden;
    margin: 0 auto;
  }

  .floor-icon {
    margin-bottom: 0.3rem;
  }

  .floor-goods {
    margin-bottom: .5rem;
    .floor-item-title {
      padding: 0 0.3rem;
      margin-bottom: 0.2rem;
      margin-top: 0.2rem;
      .title-txt {
        font-size: 0.34rem;
        font-weight: 600;
      }
      .title-txt-y {
        font-weight: 400;
        font-size: 0.34rem;
      }
    }
  }
  .floor-showcase {
    width: 100%;
    margin: .2rem 0;
    position: relative;
    min-height: 2rem;
  }
</style>
