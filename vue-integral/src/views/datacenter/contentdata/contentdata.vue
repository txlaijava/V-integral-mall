<template>
    <div>
        <div class="page-title">
            <h2 v-text="'内容数据'"></h2>
        </div>
  <div class="page-content contentdata">
    <ul class="type-tabs cf">
      <li class="type-item" @click="isShowGoods"><a href="javascript:;" :class="showGoods? 'active':''">商品明细</a></li>
      <li class="type-item" @click="isShowActivity"><a href="javascript:;" :class="!showGoods? 'active':''">活动明细</a></li>
    </ul>
    <div :class=" showGoods ? 'contentdata-goods':'contentdata-activity' ">
      <div class="bottomline posr mt20">
        <div class="date-range fl mr10">
          <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
          <span class="range-split">-</span>
          <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
        </div>
        <input type="text" class="form-control title-control" v-model="pageData.param.goodsName" placeholder="商品标题">
        <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getData">筛选</a>
        <!--<a class="download fr" href="javascript:;"><i class="iconfont icon-leadingOut"></i>导出当前报表</a>-->
      </div>
      <table class="mt20">
        <thead>
        <tr class="tr-header">
          <th v-text="showGoods?'商品标题':'活动标题' "></th>
          <th @click="sort('pv')"><span v-text="showGoods?'PV':'' "></span></th>
          <th @click="sort('uv')"><span v-text="showGoods?'累计UV':'' "></span></th>
          <th @click="sort('integral')"><span v-text="showGoods?'消耗积分':'参与人数' "></span></th>
          <th @click="sort('money')"><span v-text="showGoods?'消耗金额':'中奖人数' "></span></th>
          <th @click="sort('orderNum')"><span v-text="showGoods?'下单数':'活动订单' "></span></th>
          <th v-if="!showGoods"><span v-text="'消耗积分'"></span></th>
        </tr>
        </thead>
        <tbody>
        <tr class="tr-items" v-for="(item,index) of pageData.contentdataLists">
          <td v-text="item.commodityTitle"></td>
          <td v-text="item.PV"></td>
          <td v-text="item.UV"></td>
          <td v-text="item.consumptionIntegral"></td>
          <td v-text="item.consumptionAmount"></td>
          <td v-text="item.numberOfOrdersCompleted"></td>
          <td v-if="!showGoods" v-text="item.numberOfOrdersCompleted"></td>
        </tr>
        </tbody>
      </table>
      <div class="pagination">
        <div class="total-count">共{{pageData.contentdataLists.length}}条</div>
        <v-page :initCurrentPage="pageData.param.currentPage" :initTotalPages="pageData.param.totalPages" @click-page="page"></v-page>
      </div>
    </div>
    <transition name="loading">
      <v-loading v-show="pageData.param.dataLoading"></v-loading>
    </transition>
  </div>
    </div>
</template>

<script>
  import {getContentdata}  from '@/service/getData'
  import page from '@/components/base/page'
  export default{
    data() {
      return {
        showGoods: true,     //显示商品明细
        pageData:{
          param:{     //参数
            currentPage:1,
            totalPages: '',
            pageSize:10,
            beginTime:'',           //开始时间
            endTime:'',             //结束时间
            orderGoodsName:'',           //商品名称
            dataLoading:false,           //加载中显示状态
            sortType:'',                  //排序类型
            sortTypeValue:true,              //为true则降序
            num :0,
          },
          contentdataLists: {}
        },
      }
    },
    //时间选中事件
    updateDataVal:function(name,value){
      this.pageData.param[name] = value;
    },
    methods: {
        //获取数据方法
        getData: function () {
            this.pageData.param.dataLoading = true;
            getContentdata(this.pageData.param).then((response) => {
                this.pageData.param.dataLoading = false;
                var res = response.data.data;
                this.pageData.contentdataLists = res;
                this.pageData.param.totalPages = res[0].total_pages;
            });
        },

      // 点击商品明细
      isShowGoods() {
        this.showGoods = true;
        this.getData();
      },
      // 点击活动明细
      isShowActivity() {
        this.showGoods = false;
        this.pageData.contentdataLists='';
        this.pageData.param.totalPages='';
      },
      //分页跳转方法
      page:function(currentPage){
        this.pageData.param.currentPage = currentPage;
        this.getData();
      },
      //时间选中事件
      updateDataVal:function(name,value){
        this.pageData.param[name] = value;
      },
      //排序方式
        sort:function(value){
            if(value != this.pageData.param.sortType || value == ''){
                this.pageData.param.sortTypeValue = true
            }else{
                this.pageData.param.sortTypeValue = false
            }
            if(value==this.pageData.param.sortType){//主要判断同一个排序方式点击三次sortTypeValue状态会一直false
                this.pageData.param.num=this.pageData.param.num+1;
                if(this.pageData.param.num==2){
                    this.pageData.param.sortTypeValue = true
                    this.pageData.param.num = 0
                }
            }
         this.pageData.param.sortType = value;

         this.getData();
      },
    },
    mounted () {
      this.getData();
    },
    components: {
      'v-page':page
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../style/datacenter.css";
</style>
