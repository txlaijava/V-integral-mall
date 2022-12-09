<template>
  <div class="swiper-container" :style="{height:height}">
      <van-swipe :autoplay="3000" >
          <van-swipe-item v-for="(img,index) in themeItem" :key="index">
              <img v-lazy="imageWebServer + img.itemDataImg" />
          </van-swipe-item>
      </van-swipe>
  </div>

</template>

<script>
  import {getApplyThemeItemData} from '@/service/getAppData';
  import config from '@/config/config';

  export default{
    props: ['themeItemId','showLoading','bannerType','imgList','height'],
    data () {
      return {
          imageWebServer:config.ossImgPath,
          themeItem:[]
      }
    },
    methods: {
        getThemeItemData: function () {
          const params = {themeItemId:this.themeItemId};
          getApplyThemeItemData(params).then(res =>{
              const re = res.data.data.data;
              this.themeItem = re;
              if(re.length < 1){
                  this.$emit('up-dom',this.themeItemId);
              }
              this.$emit('is-loading',1)
          })
        }
    },
    created() {
        if('home' == this.bannerType){
            this.getThemeItemData();
        }else{
        }
    },
    watch:{
      imgList(curVal,oldVal){
          if('goods' == this.bannerType){
            this.imgList.forEach(v =>{
                this.themeItem.push({itemDataImg:v.path + v.name})
            })
          }
      },
    }

  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .swiper-container {
    width: 100%;
      img {
          width: 100%;
          height: 100%;
      }
  }
    .van-swipe{
        height: 100%;
    }
</style>
