<template>
    <div class="main">
        <div class="page-title">
            <h2>页面内容配置</h2>
            <ul class="nav-tabs">
                <li>
                    <router-link to="homePage" active-class="active">
                        手机首页
                    </router-link>
                </li>
                <li>
                    <router-link to="category" active-class="active">
                        自定义分类
                    </router-link>
                </li>
            </ul>
        </div>
        <router-view></router-view>
    </div>
</template>

<script>
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "../../../style/interface.css";
</style>
