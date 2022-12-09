<template>
  <div class="main">
    <div class="page-title"><h2> 接口配置 </h2>
      <ul class="nav-tabs">
        <li>
          <router-link to="setting" active-class="active">
            接口配置
          </router-link>
        </li>
        <li>
          <router-link to="exception" active-class="active">
            异常监控
          </router-link>
        </li>
        <!--<li><a href="#!/interface/setting" class="active"> 接口配置 </a></li>-->
        <!--<li><a href="#!/interface/exception"> 异常监控 </a></li>-->
      </ul>
    </div>
    <keep-alive>
      <router-view></router-view>
    </keep-alive>
  </div>
</template>

<script>

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../style/configure";
</style>
