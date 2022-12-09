<template>
  <div class="main datacenter-app">
    <!--<div class="page-title">
      <h2 v-text="pageTitle"></h2>
      <a href="javascript:;" class="iconfont titletip">&#xe770;
        <div class="titledesc">
          <p>1. 新增用户：首次访问积分商城的用户；</p>
          <p>2. 访客：访问积分商城但未产生订单的用户；</p>
          <p>3. 用户积分：用户每天首次访问积分商城时的账户积分；</p>
          <p>4. 订单转化率：相同周期内订单数与UV的比；</p>
        </div>
      </a>
    </div>-->
    <router-view></router-view>

  </div>
</template>

<script>
export default{
    data(){
        return {
          pageTitle: ''
        }
    },
  methods: {
    /*getPageTitle() {
      this.pageTitle = this.$route.query.name
      console.log(this.$route.query)
    }*/
  },
  created() {
      /*this.pageTitle = this.$route.query.name
      console.log(this.pageTitle)*/
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

    .page-title {
      padding: 0;
      h2 {
        font-size: 18px;
        font-weight: 700;
        display: inline-block;
      }
      .titletip {
        position: absolute;
        top: 42px;
        right: 0;
        font-size: 16px;
        color: #bbb;
        &:hover {
          .titledesc {
            display: block;
          }
        }
        .titledesc {
          display: none;
          position: absolute;
          z-index: 999;
          background: #fff;
          border: 1px solid #ccc;
          border-radius: 6px;
          top: 22px;
          right: -5px;
          color: #444;
          width: 354px;
          padding: 15px;
          font-size: 14px;
          font-family: Helvetica Neue,Helvetica,Hiragino Sans GB,Microsoft Yahei,STHeiTi,Arial,sans-serif;
          &:before {
            content: " ";
            position: absolute;
            border-color: transparent;
            border-style: solid;
            border-width: 0 4px 4px;
            border-bottom-color: #ccc;
            top: -5px;
            right: 8px;
          }
          &:after {
            content: " ";
            position: absolute;
            border-color: transparent;
            border-style: solid;
            border-width: 0 4px 4px;
            border-bottom-color: #fff;
            top: -4px;
            right: 8px;
          }
          p {
            margin-bottom: 8px;
          }
        }
      }
      .iconfont {

      }
    }
    .page-content {
      margin: 20px 0;
    }


</style>
