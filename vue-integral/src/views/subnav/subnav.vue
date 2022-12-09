<template>
  <div class="subnav" :class="{ 'subnav-hide' : isShow}" style="margin-top:70px;">
    <div class="subnav-desc">
      <h1>{{titleName}}管理</h1>
    </div>
    <nav class="subnav-link">
      <ul>
        <li v-for="(item,index) in subnavItem" :id="index" @click.stop="active(index)">
          <router-link v-if="item.URL!='/admin/index/addgoods'" :to="item.URL" :class="{ active: index == currentIndex }">
            <i class="iconfont" v-html="item.head_logo"></i> {{item.name}}
          </router-link>
          <a v-else @click="addgoods" :class="{ active: index == currentIndex }">
            <i class="iconfont" v-html="item.head_logo"></i> {{item.name}}
          </a>
         </li>
      </ul>
    </nav>
  </div>
</template>

<script>
  import {getSubnav} from '@/service/getData';
  import {setSessionStorage,getSessionStorage} from '@/api/utils';

  export default{
    data(){
      return{
        currentIndex: getSessionStorage('SUBNAV-CURRENT-INDEX') || 0
      }
    }
    ,props: ['subnavItem','isShow','titleName'],
    methods: {
        active(index){
            this.currentIndex = index;
            setSessionStorage('SUBNAV-CURRENT-INDEX',index);
        },
        addgoods () {
          this.$emit('add-goods')
        }

    },
    created() {
    },watch: {
      subnavItem(){
          this.currentIndex = 0;
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .subnav {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 80px;
    z-index: 1;
    width: 178px;
    background-color: #f2f2f2;
    transition: -webkit-transform 1s ease;
    transition: transform 1s ease;
    transition: transform 1s ease,-webkit-transform 1s ease;
    -webkit-transform: translate(0);
    transform: translate(0);
    .subnav-desc {
      margin: 39px 0 15px;
      padding: 0 10px 0 27px;
      h1 {
        font-size: 18px;
        font-weight: 700;
        margin-bottom: 8px;
        letter-spacing: 2px;
        color: #444;
      }
      p {
        font-size: 12px;
        line-height: 16px;
        color: #888;
      }
    }
    .subnav-link{
      a {
        color: #888;
        display: block;
        padding-left: 33px;
        height: 36px;
        line-height: 36px;
        font-size: 12px;
        &.active {
          background: #e0dfda;
          color: #444;
        }
        .iconfont {
          display: inline;
          font-size: 13px;
          padding-right: 6px;
        }
      }
    }
  }

  .subnav.subnav-hide {
    -webkit-transform: translate(-178px);
    transform: translate(-178px)
  }

  @media (max-width: 1280px) {
    .subnav {
      width: 178px
    }
    .subnav.subnav-hide {
      -webkit-transform: translate(-178px);
      transform: translate(-178px)
    }
    .subnav-desc {
      padding-left: 17px
    }
    .subnav-link a {
      padding-left: 23px
    }
  }
</style>
