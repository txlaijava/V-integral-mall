<template>
  <div class="main">
    <div class="page-title"><h2> 收入明细 </h2></div>
    <div class="page-content">
      <form class="bottomline mb20">
        <div class="form-filter-wrap">
          <div class="date-range">
            <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
            <span class="range-split">-</span>
            <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
          </div>
          <div class="form-combination">
            <input type="text" class="form-control" placeholder="订单号" v-model="pageData.param.oid">
          </div>
          <v-selection :selections="type" v-model="pageData.param.type" @selectCallBack="updateDataVal('type',$event)"></v-selection>
          <v-selection :selections="acApplyList" v-model="pageData.param.acApplyId" @selectCallBack="updateDataVal('acApplyId',$event)"></v-selection>
          <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getData">筛选</a>
        </div>
      </form>
      <table class="table-goods table-goods-list">
        <thead>
        <tr class="tr-header">
          <th class="goods-check"></th>
          <th class="th-coupon"> 订单日期</th>
          <th class="th-status"> 应用</th>
          <th class="th-price"> 类型</th>
          <th class="th-outdate"> 商品</th>
          <th class="th-outdate"> 订单号</th>
          <th class="th-price"> 金额</th>
          <th class="th-remaind"> 交易后金额</th>
        </tr>
        </thead>
        <tbody>
        <tr class="tr-items" v-for="(item,index) in pageData.dataLists">
          <td></td>
          <td>{{item.addTime}}</td>
          <td>{{item.acApply.applyName}}</td>
          <td v-if="item.type==1">提现申请</td>
          <td v-else>订单结算</td>
          <td>{{item.order.orderGoods.goods.goodsName}}</td>
          <td>{{item.order.oid}}</td>
          <td>{{item.amount}}</td>
          <td>{{item.balance}}</td>
        </tr>
        </tbody>
      </table>
      <v-noresult v-if="pageData.dataLists.length <= 0 "></v-noresult>
      <div class="pagination" v-else>
        <v-page :initCurrentPage="pageData.param.currentPage" :initTotalPages="pageData.param.totalPages" @click-page="page"></v-page>
      </div>
    </div>
    <transition name="loading">
      <v-loading v-show="pageData.param.dataLoading"></v-loading>
    </transition>
  </div>
</template>

<script>
  import {getIncomeData,getAcApplyList} from '@/service/getData';
  import * as SYSTEM from '@/api/system';
  import page from '@/components/base/page'
  import config from '@/config/config';

  export default{
    data(){
      return {
        imageWebServer:config.ossImgPath,
        //分页信息
        pageData:{
          dataLoading:false,
          param:{     //参数
            currentPage:1,
            totalPages: 1,
            pageSize:10,
            beginTime:"",           //开始时间
            endTime:"",             //结束时间
            oid:"",
            type:"",
            acApplyId:"",
          },
          dataLists: {}       //数据容器
        },
        type: [
          {label: '任意类型', value: ''},
          {label: '提现申请', value: '1'},
          {label: '订单结算', value: '2'},
        ],
        acApplyList: [
          {label: '任意应用', value: ''},
        ]
      }
    },
    watch:{
    },
    methods: {
        getData:function(){
            this.pageData.dataLoading = true;
            getIncomeData(this.pageData.param).then((res)=>{
              this.pageData.dataLoading = false;
              this.pageData.dataLists = res.data.data.data.data;
              this.pageData.param.currentPage = res.data.data.data.currentPage;
              this.pageData.param.totalPages = res.data.data.data.pages;
            })
        },
        getAcApplyList:function(){
          getAcApplyList().then((res)=>{
              console.log(res.data.data.data.acApplyList);
              var list = res.data.data.data.acApplyList;
            list.forEach(item =>{
                var acApply = {label:item.applyName,value:item.id,};
                this.acApplyList.push(acApply);
            })
          })
        },
        //时间和select选中事件
        updateDataVal:function(name,value){
          this.pageData.param[name] = value;
        },
        //分页跳转方法
        page:function(currentPage){
            this.pageData.param.currentPage = currentPage;
            this.getData();
        }
    },
    created() {
        this.getData();
        this.getAcApplyList();
    },components: {
      'v-page':page
    },
    mounted() {
      const that = this;
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../style/addGoods.css";
  @import "../../../style/order.css";
</style>
