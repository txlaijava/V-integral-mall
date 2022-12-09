<template>
  <aside id="app-sidebar" style="margin-top:70px;">
    <nav class="sidebar-nav">
      <ul>
        <li v-for="(item,index) in sidebar" @click.stop="active(item.id,item.name)">
            <router-link to="" class="nav-link"
                         :class="{ active: item.id == sidebarCurrentId }">
               <i class="iconfont" v-html="item.head_logo"></i> {{item.name}}
            </router-link>
        </li>
      </ul>
    </nav>
  </aside>
</template>

<script>
  export default{
      data(){
          return{
          }
      },
    props: ['sidebar','sidebarCurrentId'],
    methods: {
      active(currentId,name){
        this.$emit('current',currentId,name);
      }
    },
    created() {
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  #app-sidebar {
    width: 80px;
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    height: 100%;
    background-color: #333;
    z-index: 99;

    .nav-link {
      display: block;
      color: #fff !important;
      height: 70px;
      text-align: center;
      font-size: 12px;
      opacity: .6;
      .iconfont {
        font-size: 20px;
        display: block;
        padding: 12px 0 6px;
      }
      &.active {
        background-color: #5c5c5c;
        color: #fff !important;
        opacity: 1;
      }
    }
  }
</style>
