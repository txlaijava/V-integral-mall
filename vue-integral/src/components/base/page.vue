<!--
      ----------后台分页组件----------
声明组件：<v-page :initCurrentPage="currentPage" :initTotalPages="totalPages" @click-page="page"></v-page>
  参数:currentPage:1      当前选中页数
       totalPages:50      总页数
  方法：page:function(currentPage){
          currentPage为选择的页数
          //调用查询数据
      }
-->


<template>
  <ul class="pagination-list">
    <li><a href="javascript:void(0)" class="num iconfont icon-left" @click="prevOrNext(-1)"></a></li>
    <li v-for="(item, index) in pages" :key="index">
      <a href="javascript:void(0)" class="num " :class="{selected : item === currentPage}" @click="select(item)">{{item}}</a>
    </li>
    <li><a href="javascript:void(0)" class="num iconfont icon-right" @click="prevOrNext(1)"></a></li>
  </ul>
</template>

<script>
  export default {
    props: [
        'initCurrentPage','initTotalPages'
    ],
    data() {
      return {
        currentPage: 1,     //当前页数
        totalPages: 10,     //最大页数
      }
    },
    watch: {
      initCurrentPage:{
        handler: function (val, oldVal){
          this.currentPage = val;
        },
        immediate: true
      },
      initTotalPages:{
        handler: function (val, oldVal){
          this.totalPages = val;
        },
        immediate: true
      }
    },
    methods: {
      select(n) {
        if (n === this.currentPage) return
        if (typeof n === 'string') return
        this.currentPage = n;
        this.$emit("click-page",this.currentPage);
      },
      prevOrNext (n) {
        this.currentPage += n
        this.currentPage < 1
          ? this.currentPage = 1
          : this.currentPage > this.totalPages
          ? this.currentPage = this.totalPages
          : null;
        this.$emit("click-page",this.currentPage);
      }
    },computed:{
      pages() {
        const c = this.currentPage||1;
        const t = this.totalPages||0;
        var arr = [];
        if(t<=5){
          for(let i = 1; i <= t; i++) {
            arr.push(i)
          }
          return arr
        }
        if (c <= 2) return [1,2,3,'...',t]
        if (c >= t -1) return [1,'...', t -2, t -1, t]
        if (c === 3) return [1,2,3,4,'...',t]
        if (c === t -2) return [1,'...', t-3, t-2, t-1, t]
        return [1,'...', c-1, c, c + 1, '...', t]
      }
    }
  };
</script>
