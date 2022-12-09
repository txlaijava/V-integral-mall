<template>
  <div class="container dialog" :class="className" :value="value" v-if="show">
    <div class="msk"></div>
    <div class="tip">
      <slot>
        empy
      </slot>
    </div>
  </div>
</template>

<script>
  export default{
    props: {
      value:{
        type: Boolean,
        default: true
      },
      className:{
        type: String,
        default: ''
      }
    },
    data(){
      return {
        show:false
      }
    },
    watch:{
      value(val) {
        this.show = val;
        if(val){
          this.hideDialog();//ok
        }
      },
      show(val) {
        this.$emit('input', val);

      }
    },
    mounted() {
      if (this.value) {
        this.show = true;
      }
    },
    methods: {
      hideDialog () {
        var that = this;
        setTimeout( function () {
          that.show = false;
        },2000)

      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .tip {
    min-width: 2rem;
    text-align: center;
    padding: 0.3rem;
    background-color: #fff;
    position: absolute;
    left: 50%;
    top: 47%;
    border-radius: 0.1rem;
    -webkit-animation: .5s fadeInUp forwards;
    animation: .5s fadeInUp forwards;
    justify-content: center;
    img {
      width: 0.3rem;
      height: 0.3rem;
      margin-right: 0.2rem;
      -webkit-animation: .5s rotate  linear infinite;
      animation: .5s rotate linear infinite;
      display: inline-block;
    }
  }
</style>
