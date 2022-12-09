<template>
  <div class="main">
    <div class="page-title"><h2> 售后订单 </h2></div>
    <div class="page-content">
      <form class="bottomline">
        <div class="form-filter-wrap">
          <div class="form-combination">
            <v-selection :selections="accountNumber"></v-selection>
            <input type="text" class="form-control" placeholder="请输入订单号"></div>
          <div class="date-range">
            <v-date-picker placeholder="起始时间"></v-date-picker>
            <span class="range-split">-</span>
            <v-date-picker placeholder="结束时间"></v-date-picker>
          </div>
          <a href="javascript:;" class="btn btn-default btn-lg btn-filter">筛选</a>
          <div class="more-choose-wrap" v-show="isShowmore">
            <input type="text" class="form-control" placeholder="用户id"> <input
            type="text" class="form-control" placeholder="兑换项名称"></div>
        </div>
        <div class="btn-side-wrap">
          <a href="javascript:;" class="btn btn-more">
            <span v-show="isShowmore" @click="isShowmore=false,isShowcurrent=true"> 简单条件 <i
              class="iconfont icon-dropUp"></i> </span>
            <span v-show="isShowcurrent" @click="isShowmore = true,isShowcurrent=false"> 更多条件 <i
              class="iconfont icon-dropDown"></i> </span>
          </a>
        </div>
      </form>
      <ul class="table-header column6">
        <li class=""><span>兑换项</span></li>
        <li><span>支付金额</span></li>
        <li><span>退款金额</span></li>
        <li><span>用户备注</span></li>
        <li><span>售后状态</span></li>
        <li><span>操作</span></li>
      </ul>
      <v-noresult></v-noresult>
    </div>
  </div>
</template>

<script>
  import {getOrderLists} from '@/service/getData';

  export default{
    data(){
      return {
        isShow: false,
        isShowmore: false,
        isShowcurrent: true,
        accountNumber: [{
          label: '开发者订单号',
          value: 0
        }, {
          label: '平台订单号',
          value: 1
        }]
      }
    },
    methods: {
      getOrderLists: function () {
        getOrderLists().then((response) => {
          this.orderList = response.data.ordersearch.orderList;
        });
      }
    },
    created() {
      this.getOrderLists();
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/order.css";
</style>
