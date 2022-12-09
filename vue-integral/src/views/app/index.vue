<template>
    <div>
        <keep-alive>
            <router-view v-if="$route.meta.keepAlive"></router-view>
        </keep-alive>
        <router-view v-if="!$route.meta.keepAlive"></router-view>
    </div>
</template>

<script>
  import '../../config/rem'

  export default{

  }
</script>

<style rel="stylesheet/css" lang="css">
@import "../../style/app/common.css";
</style>
