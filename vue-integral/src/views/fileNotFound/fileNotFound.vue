<template>
  <div class="container">
    <div class="header-wrap">
      <a class="rblc-logo"></a>
      <div class="right-side">
        <!--{{dataView.headTxt}}，-->
        <!--<router-link :to="dataView.headSingnup.url" :class="'txt-success'">{{dataView.headSingnup.txt}}</router-link>-->
      </div>
    </div>
    <div class="content">
      <img class="title" src="../../../static/PC404.png">
      <div class="flex">
        <img class="text" src="../../../static/PC404_text.png">
        <img class="return_btn" @click="goBack" src="../../../static/PC404_btn.png">
      </div>
    </div>
    <div class="footer">
      <img src="../../../static/PC404_tree.png">
    </div>
  </div>
</template>

<script>
export default{
    methods: {
      goBack() {
        this.$router.go(-1);//返回上一层
      }
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.container{
  width: 100vw;
  height: 100vh;
  min-width: 900px;
  background-color: #f1f4f8;
  .header-wrap {
    background: #fff;
    height: 78px;
    position: relative;
    font-size: 16px;
    .rblc-logo {
      display: block;
      position: absolute;
      top: 20px;
      left: 40px;
      width: 298px;
      height: 42px;
      background: url('../../../static/rblc_login_logo.png') no-repeat;
    }
    .right-side {
      position: absolute;
      right: 34px;
      top: 42px;
    }
  }
  .content{
    height: 400px;
    position: fixed;
    top: 26%;
    left: 50%;
    transform: translateX(-50%);
    padding: 0;
    .title{
      width: 610px;
    }
    .flex{
      display: flex;
      justify-content: center;
      align-items: flex-end;
      margin-top: 36px;
      .text{
        width: 438px;
        margin-right: 25px;
      }
      .return_btn{
        width: 168px;
        cursor: pointer;
      }
    }
  }
  .footer{
    width: 100%;
    img{
      position: fixed;
      left: 0;
      bottom: 0;
      width: 100%;
      padding: 0 195px;
      box-sizing: border-box;
    }
  }
}
</style>
