<template>
  <div class="main">
    <div class="page-title"><h2> 接口配置 </h2>
      <ul class="nav-tabs">
        <li>
          <router-link to="level" active-class="active">
            会员等级
          </router-link>
        </li>
        <li>
          <router-link to="shareconfig" active-class="active">
            分享配置
          </router-link>
        </li>
        <li>
          <router-link to="expresstemplate" active-class="active">
            运费模版
          </router-link>
        </li>
        <li>
          <router-link to="appbulletin" active-class="active">
            设置公告
          </router-link>
        </li>
      </ul>
    </div>
    <div class="page-content setting-more">
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </div>

  </div>
</template>

<script>

</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
