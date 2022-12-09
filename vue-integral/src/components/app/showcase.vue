<template>
    <div v-if="show">
      <router-link :to="{path:themeItemData.place1.itemDataLike}" class="main-showcase">
        <img v-lazy="themeItemData.place1.itemDataImg" alt="">
      </router-link>
      <div class="side-showcase">
        <router-link :to="{path:themeItemData.place2.itemDataLike}">
          <div class="top-showcase">
            <img v-lazy="themeItemData.place2.itemDataImg" alt="">
          </div>
        </router-link>
        <router-link :to="{path:themeItemData.place3.itemDataLike}">
          <div class="bottom-showcase">
            <img v-lazy="themeItemData.place3.itemDataImg" alt="">
          </div>
        </router-link>
      </div>
    </div>
</template>

<script>
    import {getApplyThemeItemData} from '@/service/getAppData';
    export default {
        data (){
            return {
                themeItemData:{place1:[],place2:[],place3:[]},
                show:false
            }
        },
        props: ['themeItemId','showLoading'],
        methods: {
          getThemeItemData: function () {
              const params = {themeItemId:this.themeItemId};
              getApplyThemeItemData(params).then(res =>{
                  const re = res.data.data.data;
                  this.show = re.length >= 3 ? true : false;
                  if(this.show){
                      re.forEach(v =>{
                          this.themeItemData[v.itemDataValue] = v;
                      })
                  }else{
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

<!-- 限定作用域"scoped" 不要误写成scope -->
<style rel="stylesheet/scss" lang="scss" scoped>

  .main-showcase {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    width: 3.66rem;
    height: 2.5rem;
    overflow: hidden;
    img {
      width: 3.66rem;
      height: 2.5rem;
      max-width: 3.66rem;
    }
  }
  a {
    display: block;
  }

  .side-showcase {
    padding-left: 3.8rem;
    overflow: hidden;
    .top-showcase {
      width: 3.74rem;
      height: 1.2rem;
      overflow: hidden;
      img {
        width: 3.74rem;
        height: 1.2rem;
        max-height: 1.2rem;
      }
    }
    .bottom-showcase {
      margin-top: .1rem;
      width: 3.74rem;
      height: 1.2rem;
      overflow: hidden;
      position: relative;
      img {
        margin-top: .01rem;
        width: 3.74rem;
        height: 1.2rem;
        max-height: 1.2rem;
      }
    }
  }
</style>
