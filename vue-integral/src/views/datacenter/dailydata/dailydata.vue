<template>
    <div>
        <div class="page-title">
            <h2 v-text="'日常数据'"></h2>
        </div>
  <div class="page-content daily-data">
    <div class="form-inline bottomline posr">
      <v-selection :selections="dataSelect" @selectCallBack="updateSelectVal($event)"></v-selection>
      <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getDailydata">筛选</a>
      <!--<a class="download fr" href="javascript:;"><i class="iconfont icon-leadingOut"></i>导出当前报表</a>-->
    </div>
    <table class="mt20">
      <thead>
      <tr class="tr-header">
        <th>日期</th>
        <th>PV</th>
        <th>UV</th>
        <th>新增用户</th>
        <th>访客数</th>
        <th>人均积分</th>
        <th>下单数</th>
        <th>完成订单数</th>
        <th>消耗积分</th>
        <th>订单转化率</th>
      </tr>
      </thead>
      <tbody>
      <tr class="tr-items" v-for="(item,index) of dailydataLists">
        <td v-text="yearMonth+item.h"></td>
        <td v-text="item.pv"></td>
        <td v-text="item.uv"></td>
        <td v-text="item.add_user"></td>
        <td v-text="item.visitor_user"></td>
        <td v-text="item.average_integral"></td>
        <td v-text="item.order_count"></td>
        <td v-text="item.order_count"></td>
        <td v-text="item.used_integral"></td>
        <td v-text="item.conversion_ratio"></td>
      </tr>
      <tr class="tr-items fw-b">
        <td v-text=" '汇总' "></td>
        <td v-text="PVSummary"></td>
        <td v-text="UVSummary"></td>
        <td v-text="adduserSummary"></td>
        <td v-text="summaryOfNumberOfVisitors"></td>
        <td v-text="integralSummary"></td>
        <td v-text="orderQuantitySummary"></td>
        <td v-text="summaryOfnumberOfOrdersCompleted"></td>
        <td v-text="consumptionIntegralSummary"></td>
        <td v-text="orderConversionRateSummary"></td>
      </tr>
      </tbody>
    </table>
      <transition name="loading">
          <v-loading v-show="pageData.param.dataLoading"></v-loading>
      </transition>
  </div>
    </div>
</template>

<script>
  import {getDailydata} from '@/service/getData'
  export default{
    data() {
      return {
        dataSelect: [{label: '', value: ''}
        ],
        dailydataLists: {},
        PVSummary:0,                        //PV汇总
        UVSummary: 0,                       //UV汇总
        adduserSummary:0,                   //新增用户汇总
        summaryOfNumberOfVisitors: 0,       //访客数
        integralSummary: '-',               // 人均积分汇总
        orderQuantitySummary: 0,            // 下单数汇总
        summaryOfnumberOfOrdersCompleted:0, // 完成订单数汇总
        consumptionIntegralSummary: 0,      // 消耗积分汇总
        orderConversionRateSummary: '-',    // 订单转化率汇总
        mydate: '',
        yearMonth:'',                            //年月
          pageData:{
              param:{     //参数
                  dataLoading:false,           //加载中显示状态
                  dailyTime:'',
              },
          },
      }
    },
    methods: {
      getDailydata() {
          this.pageData.param.dataLoading = true;
        getDailydata(this.pageData.param).then((res) => {
            this.pageData.param.dataLoading = false;
            console.log(res.data.data)
          this.dailydataLists = res.data.data.dailyData;
          this.yearMonth = res.data.data.yearMonth;
          this.total();
        })
      },
      getCurrentDate() {
        //创建现在的时间
        var data=new Date();
        //获取年
        var year=data.getFullYear();
        //获取月
        var mon=data.getMonth()+2;
//        console.log(typeof mon)
        var dataArry=new Array();
        for(var i=0;i<13;i++){
          mon=mon-1;
          if(mon<=0){
            year=year-1;
            mon=mon+12;
          }
          if(mon<10){
            mon="0"+mon;
          }
          this.dataSelect.push({
            label: dataArry[i]=year+"-"+mon,
            value: i
          })
        }
        console.log(this.dataSelect)
      },
      total(){
          //点击筛选清空汇总数据
          this.PVSummary=0;                        //PV汇总
          this.UVSummary= 0;                       //UV汇总
          this.adduserSummary=0;                   //新增用户汇总
          this.summaryOfNumberOfVisitors= 0;       //访客数
          this.integralSummary= '-';               // 人均积分汇总
          this.orderQuantitySummary= 0;            // 下单数汇总
          this.summaryOfnumberOfOrdersCompleted=0; // 完成订单数汇总
          this.consumptionIntegralSummary= 0;      // 消耗积分汇总
          //点击筛选清空汇总数据 end
        this.dailydataLists.map((item) => {
          this.PVSummary += item.pv;
          this.UVSummary += item.uv;
          this.adduserSummary += item.add_user;
          this.summaryOfNumberOfVisitors += item.visitor_user;
          this.orderQuantitySummary += item.order_count;
          this.summaryOfnumberOfOrdersCompleted += item.order_count;
          this.consumptionIntegralSummary += item.used_integral;
        })
      },
      //select选中事件
      updateSelectVal:function(value){
          //根据选择的时间的value值循环找name值
          for(var key in this.dataSelect){
              if(value == this.dataSelect[key].value ){
                  this.pageData.param.dailyTime = this.dataSelect[key].label;
                  break;
              }
          }
          console.log(value)
      },
    },
    created() {
      this.getDailydata();
      this.getCurrentDate();
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../style/datacenter.css";

  .page-padding {
    background: #fff;
    padding: 30px 28px;
    min-height: 900px;
    box-sizing: border-box;
    .page-title {
      padding: 0;
      border: none;
    }
  }

  .daily-data {
    .bottomline {
      padding-bottom: 20px;
    }
    .tr-items {
      td {
        padding-top: 20px;
        padding-bottom: 20px;
      }
    }
  }

</style>
