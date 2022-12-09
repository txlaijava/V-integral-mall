<template>
  <div class="switch" :class="{'on' : me_checked}" :value="value" @click="toggle">
    <label class="switch-label">
      <div class="switch-inner"></div>
      <div class="switch-switch"></div>
    </label>
  </div>
</template>

<script>
  export default{
    props: {
      value: {
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
        me_checked: this.value
      }
    },
    watch: {
      me_checked(val) {
        this.$emit('input', val);
      }
    },
    methods: {
      toggle() {
        this.me_checked = !this.me_checked;
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
