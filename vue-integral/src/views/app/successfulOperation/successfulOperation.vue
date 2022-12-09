<template>
  <div class="container">
    <div class="header">
      <div class="tipImg">
        <img src="../../../../static/icon/successful.png" alt="">
      </div>
      <p class="tipText t-center t-333">操作成功</p>
    </div>
    <div class="btn flex">
      <router-link to="" class="bg--orange">返回上一页</router-link>
      <router-link to="home" class="bg-theme">返回首页</router-link>
    </div>
  </div>
</template>

<script>
  export default{
    data() {
      return {
        tipText: ''
      }
    },
    methods: {
      getTipText() {
        this.tipText = this.$route.query.id
      }
    },
    created() {
      this.getTipText();
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .container {
    .header{
      background: #fff;
      padding: 1.5rem 0 1rem;
      .tipImg{
        width: 1.2rem;
        height: 1.2rem;
        margin: 0 auto;
        img {
          width: 100%;
          height: 100%;
        }
    }

    }
    .tipText{
      font-size: 0.3rem;
      margin-top: 0.4rem;
    }
    .btn{
      justify-content: space-around;
      margin: 1rem 0;
      padding: 0 0.4rem;
      a{
        width: 2.5rem;
        display: inline-block;
      }
    }
  }
</style>
