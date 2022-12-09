<template>
  <div class="tip" :class="className">
    <i class="iconfont close icon-close" @click="closeMyself" v-if="status"></i>
    <slot>empty</slot>
  </div>
</template>

<script>
  export default{
    props: {
      className: {
        type: String,
        default: ''
      },
      status: {
        type: Boolean,
        default: ''
      }
    },
    methods: {
      closeMyself () {
        this.$emit('on-close')
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .tip h3 {
    color: #177c77;
    font-size: 14px;
    margin: 5px 0;
  }
</style>
