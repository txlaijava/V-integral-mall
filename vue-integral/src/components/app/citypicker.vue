<template>
  <div class="container" v-if="isShowCitypick">
    <div class="msk"></div>
    <div class="citypicker">
      <div class="header">
        <p>配送至</p>
        <i class="iconfont t-999" @click="closeMyself">&#xe695;</i>
      </div>
      <v-distpicker style="width: 100%;" type="mobile" province="请选择" @selected="onSelected" v-if="cityPicker.length < 1"></v-distpicker>
      <v-distpicker style="width: 100%;" type="mobile" :province="cityPicker[0]" :city="cityPicker[1]" :area="cityPicker[2]" @selected="onSelected" v-else></v-distpicker>
    </div>
  </div>
</template>

<script>
  import VDistpicker from 'v-distpicker'

  export default{
    props: ['isShowCitypick','cityPicker'],
    methods: {
      onSelected(data){
          this.$emit('onSelected',data);
      },
      closeMyself(){
          this.$emit('on-close');
      }
    },
    components: {
      'v-distpicker': VDistpicker
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .container {
    position: fixed;
    left: 0;
    bottom: 0;
    width: 100vw;
    height: 100vh;
    .citypicker {
      position: fixed;
      left: 0;
      bottom: 0;
      width: 100%;
      height: 7rem;
      background-color: #fff;
      .header {
        background-color: #f5f5f5;
        text-align: center;
        height: 0.9rem;
        position: relative;
        border-bottom: 1px solid #f6f6f6;
        p {
          line-height: 0.9rem;
        }
        .iconfont {
          position: absolute;
          top: 0.3rem;
          right: 0.3rem;
          font-size: 0.36rem;
        }
      }

    }
  }
</style>
