<template>
  <div class="single-icon">
    <router-link :to="{path:item.itemDataLike}" class="icon-a" v-for="(item,index) in itemList" :key="index">
      <img v-lazy="imageWebServer + item.itemDataImg">
      <p>{{item.itemDataName}}</p>
    </router-link>
  </div>
</template>

<script>
  import {getApplyThemeItemData} from '@/service/getAppData';
  import * as SYSTEM from '@/api/system';
  import config from '@/config/config';

  export default{
    data () {
      return {
        imageWebServer:config.ossImgPath,
        itemList: {}
      }
    },
    props: ['themeItemId','showLoading'],
    methods: {
      getThemeItemData: function () {
        const params = {themeItemId:this.themeItemId};
        getApplyThemeItemData(params).then(res =>{
            const re = res.data.data.data;
            this.itemList = re;
            if(re.length < 1){
                this.$emit('up-dom',this.themeItemId);
            }
            this.$emit('is-loading',1)
        })
      }
    },
    created() {
      this.getThemeItemData();
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .single-icon {
    padding: 0.3rem 0;
    background: #FFF;
    width: 100%;
    margin-bottom: .1rem;
    overflow-y: hidden;
    height: 1.4rem;
    font-size: 0;
    white-space: nowrap;
    -webkit-overflow-scrolling: touch;
  }
  .single-icon .icon-a {
    display: inline-block;
    font-size: .55466667rem;
    color: #333;
    letter-spacing: 0;
    text-align: center;
    white-space: normal;
    vertical-align: top;
    width: 1.4rem;
    img {
      width: 0.86rem;
      height: 0.86rem;
      margin-bottom: .17rem;
      border-radius: 0.2rem;
    }
     p {
       font-size: 0.27rem;
       color: #333;
       letter-spacing: 0;
       line-height: 0.6;
    }
  }

</style>
