<template>
    <div id="app">
        <keep-alive>
            <router-view v-if="$route.meta.keepAlive"></router-view>
        </keep-alive>
        <router-view v-if="!$route.meta.keepAlive"></router-view>
    </div>
</template>

<script>
  export default {
  }
</script>

<style>
  #main {
    width: 100%;
    height: 100%;
  }
</style>
