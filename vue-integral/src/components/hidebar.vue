<template>
  <div class="hidebar">
    <a href="javascript:void(0)" class="arrow" @click="hideBar()">
      <i class="iconfont icon-arrowRight"></i>
    </a>
  </div>
  <!--<div class="hidebar "> <a href="javascript:void(0)" class="arrow"> <i class="iconfont"></i>  </a> </div>-->
</template>
<script>

  export default{
    data(){
      return {
        isShow: false,
      }
    },
    methods: {
      hideBar: function () {
          this.isShow = !this.isShow;
          this.$emit('hide-bar', this.isShow);
      }
    },
    created() {
    }
  }
</script>


<style rel="stylesheet/scss" lang="scss" scoped>

  .hidebar {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 258px;
    z-index: 2;
    width: 0;
    border-left: 1px solid #dfdfdf;
    height: 100%;
    transition: -webkit-transform 1s ease;
    transition: transform 1s ease;
    transition: transform 1s ease,-webkit-transform 1s ease;
    -webkit-transform: translate(0);
    transform: translate(0);
    .arrow {
      position: absolute;
      top: 50%;
      left: -11px;
      margin-top: -18px;
      width: 10px;
      background: #dfdfdf;
      height: 36px;
      line-height: 36px;
      border-radius: 2px 0 0 2px;
      color: #9b9b9b;
      &:hover {
        background-color: #888;
      }
      .iconfont {
        display: block;
        font-size: 12px;
        -webkit-transform: scale(.6);
        transform: scale(.6);
      }
    }
    &.hidebarHide {
      -webkit-transform: translate(-178px);
      transform: translate(-178px);
      .arrow {
        -webkit-animation: hidebar-arrow 1s ease forwards;
        animation: hidebar-arrow 1s ease forwards;
      }
    }
  }

  @keyframes hidebar-arrow {
    to {
      left: 0;
      border-radius: 0 2px 2px 0
    }
  }

  @-webkit-keyframes hidebar-arrow {
    to {
      left: 0;
      border-radius: 0 2px 2px 0
    }
  }

  @media (max-width: 1280px) {
    .hidebar {
      left: 258px;
      &.hidebarHide {
        -webkit-transform: translate(-178px) !important;
        transform: translate(-178px) !important;
      }
    }
  }

</style>
