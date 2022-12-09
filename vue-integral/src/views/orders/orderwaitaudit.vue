<template>
  <div class="main">
    <div class="page-title"><h2> 待审核订单 </h2></div>
    <div class="page-content">
      <form class="bottomline mb20">
        <div class="form-filter-wrap">
          <v-selection :selections="typeOfMerchandise" v-model="pageData.param.orderGoodsType" @selectCallBack="updateSelectVal('orderGoodsType',$event)"></v-selection>
          <div class="date-range">
            <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
            <span class="range-split">-</span>
            <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
          </div>
          <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getData">筛选</a>
          <div class="more-choose-wrap"  v-show="isShowmore" style="width:80%;">
            <div class="money-input-wrap">
              <input type="text" class="form-control" placeholder="￥" v-model="pageData.param.minAmount">
              <span class="t-bar">-</span>
              <input type="text" class="form-control" placeholder="￥" v-model="pageData.param.maxAmount">
            </div>
            <input type="text" class="form-control" placeholder="用户id" v-model="pageData.param.orderUserId">
          </div>
        </div>
        <div class="btn-side-wrap">
          <a href="javascript:;" class="btn btn-more">
            <span v-show="isShowmore" @click="isShowmore=false,isShowcurrent=true"> 简单条件 <i class="iconfont icon-dropUp"></i> </span>
            <span v-show="isShowcurrent" @click="isShowmore = true,isShowcurrent=false"> 更多条件 <i class="iconfont icon-dropDown"></i> </span>
          </a>
        </div>
      </form>
      <ul class="table-header column4 orderwaitship-tbl-header">
        <li ><span>价格</span></li>
        <li ><span>兑换类型</span></li>
        <li class="td-express"><span>详情</span></li>
        <li class="td-operation"><span>操作</span></li>
      </ul>
      <v-noresult v-if="pageData.dataLists.length <= 0 "></v-noresult>
      <div class="table-row column4 orderwaitship-tbl" v-for="(orderList,index) in pageData.dataLists">
        <div class="row-header">
          <p class="order-info"><span>{{orderList.addTime}}</span></p>
          <p class="order-info"> 用户ID： <span>{{orderList.devUserId}}</span></p>
        </div>
        <ul class="row-items">
          <li >
            <span v-if="orderList.orderGoods.orderGoodsPrice>0">{{orderList.orderGoods.orderGoodsIntegralPrice}}积分+{{orderList.orderGoods.orderGoodsPrice}}元</span>
            <span v-else>{{orderList.orderGoods.orderGoodsIntegralPrice}}积分</span>
          </li>
          <li >
            <span v-if="orderList.orderGoods.orderGoodsPrice>0">积分+现金</span>
            <span v-else>纯积分</span>
          </li>
          <li class="td-img">
            <img class="sm-img" :src="imageWebServer+orderList.orderGoods.goods.goodsMainPhoto.path+orderList.orderGoods.goods.goodsMainPhoto.name">
            <span class="txt">{{orderList.orderGoods.orderGoodsName}}</span>
          </li>
          <li class="td-operation">
            <div >
              <a href="javascript:;" class="btn btn-green mr10" @click="audit(orderList.oid,'agree')">通过</a>
              <a href="javascript:;" class="btn btn-default" @click="auditOrderInfo.messageShow = true,auditOrderInfo.orderId = orderList.oid">拒绝</a>
            </div>
          </li>
        </ul>
      </div>
      <div class="pagination">
        <div class="total-count">共{{pageData.dataLists.length}}条</div>
        <v-page :initCurrentPage="pageData.param.currentPage" :initTotalPages="pageData.param.totalPages" @click-page="page"></v-page>
      </div>
    </div>

    <Modal v-model="auditOrderInfo.messageShow" @on-ok="confirmDisagree" @on-cancel="cancelDisagree">
      <p>确认拒绝此订单？</p>
    </Modal>

    <transition name="loading">
      <v-loading v-show="pageData.param.dataLoading"></v-loading>
    </transition>
  </div>
</template>

<script>
  import {getOrderPage,auditOrder} from '@/service/getData';
  import page from '@/components/base/page';
  import config from '@/config/config';

  export default{
    data(){
      return {
        imageWebServer:config.ossImgPath,
        isShow: false,
        isShowmore: false,
        isShowcurrent: true,
        typeOfMerchandise: [
          {label: '商品类型', value: ''},
          {label: '实物兑换', value: '1'},
          {label: '虚拟商品', value: '2'},
          {label: '优惠券', value: '3'}
        ],
        //分页信息
        pageData:{
          param:{     //参数
            currentPage:1,
            totalPages: 1,
            pageSize:10,
            orderGoodsType:'',            //商品类型
            beginTime:'',                 //开始时间
            endTime:'',                   //结束时间
            orderUserId:'',               //用户id
            minAmount:'',                 //最小金额
            maxAmount:'',                 //最大金额
            orderState:'20',              //订单状态
            dataLoading:false             //加载中显示状态
          },
          dataLists: {}                   //数据容器
        },
        auditOrderInfo:{
          messageShow:false,
          orderId:""
        }

      }
    },
    methods: {
      //获取数据方法
      getData: function () {
        this.pageData.param.dataLoading = true;
        getOrderPage(this.pageData.param).then((response) => {
          this.pageData.param.dataLoading = false;
          var res = response.data.data.data;
          this.pageData.dataLists = res.data;
          this.pageData.param.currentPage = res.currentPage;
          this.pageData.param.totalPages = res.pages;
        });
      },
      //分页跳转方法
      page:function(currentPage){
        this.pageData.param.currentPage = currentPage;
        this.getData();
      },
      //select选中事件
      updateSelectVal:function(name,value){
        this.pageData.param[name] = value;
      },
      //时间选中事件
      updateDataVal:function(name,value){
        this.pageData.param[name] = value;
      },
      cancelDisagree:function(){
        this.auditOrderInfo.messageShow = false;
        this.auditOrderInfo.orderId = "";
      },
      confirmDisagree:function(){
        this.audit(this.auditOrderInfo.orderId,"disagree");
        this.auditOrderInfo.messageShow = false;
        this.auditOrderInfo.orderId = "";
      },
      audit:function(orderId,status){
        auditOrder({orderId:orderId,status:status}).then((res) => {
          if(res.data.data.code == 0){
            this.$Message.success(res.data.data.message);
          }else{
            this.$Message.error("服务器异常");
          }
          this.getData();
        })
      }
    },
    created() {
      this.getData();
    },components: {
      'v-page':page
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/order.css";
</style>
