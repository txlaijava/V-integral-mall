<template>
    <div>
        <div class="page-title">
            <h2 v-text="'流量数据'"></h2>
        </div>
  <div class="page-content datacenter">
    <h3 class="reporttitle">总流量数据</h3>
    <div class="reportfilter cf">
      <div class="date-range">
        <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
        <span class="range-split">-</span>
        <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
      </div>
      <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getFlowdata">筛选</a>
      <!--<a class="download fr" href="javascript:;">
        <i class="iconfont icon-leadingOut"></i>导出当前结果
      </a>-->
    </div>
    <ve-line :data="fullflow" :settings="fullflowSettings"></ve-line>
    <p class="reportline"></p>
    <h3 class="reporttitle">TOP流量数据</h3>
    <div class="reporttabs">
      <a href="javascript:;" class="btn" :class=" showPV ? 'btn-green-active':'btn-default' " @click="isShowPV">PV</a>
      <i class="line"></i>
      <a href="javascript:;" class="btn" :class=" !showPV ? 'btn-green-active':'btn-default' " @click="isShowUV">UV</a>
    </div>
    <div class="reportfilter cf">
      <template v-if="showPV">
        <div class="date-range">
          <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.goodsBeginTime" @on-change="updateDataVal('goodsBeginTime',$event)" readonly></v-date-picker>
          <span class="range-split">-</span>
          <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.goodsEndTime" @on-change="updateDataVal('goodsEndTime',$event)" readonly></v-date-picker>
        </div>
        <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getGoodsdata">筛选</a>
      </template>
      <template v-else>
        <a href="javascript:;"  :class="dateType ? 'link':'link-gray-d' " @click="goodsTime('date')">昨天</a>
        <i class="line"></i>
        <a href="javascript:;"  :class="monthType ? 'link':'link-gray-d' " @click="goodsTime('month')">本周累计</a>
        <i class="line"></i>
        <a href="javascript:;"  :class="yearType ? 'link':'link-gray-d' " @click="goodsTime('year')">本月累计</a>
      </template>
      <!--<a class="download fr" href="javascript:;">
        <i class="iconfont icon-leadingOut"></i>导出当前结果
      </a>-->
    </div>
    <div class="reportchart">
      <div class="chart-line report-box">
        <ve-pie :data="goodschart" :settings="goodschartSettings" :colors="bgcolors"></ve-pie>
        <div>商品TOP</div>
        <div class="text-list">
          <template v-for="(item,index) in goods_data">
            <div class="text-item" :title="item.goodsName" >
              <span class="grade grade-pie grade" :class="'grade'+index">{{item.NO}}</span>
              {{item.goodsName}}
            </div>
          </template>
        </div>
      </div>
    </div>

    <transition name="loading">
      <v-loading v-show="pageData.param.dataLoading"></v-loading>
    </transition>
  </div>
    </div>
</template>

<script>
  import Vue from 'vue'
  import VeLine from 'v-charts/lib/line.common'
  import VePie from 'v-charts/lib/pie.common'
  import {getFlowdata}  from '@/service/getData'
  // 引入jq
  import $ from "jquery";
  export default{
    data() {
        /* 设置显示的指标维度 */
        this.fullflowSettings = {                 // 总流量数据图
          labelMap: {
            'data': '日期'
          },
          yAxisName: ['单位：无']
        },
        this.goodschartSettings = {                 // 总流量数据图
          labelMap: {
            'goodsName': '商品名称'
          },
          limitShowNum: 5,
          label: {
            normal: {
              show: false,
            }
          },
        },
        /* 设置颜色 */
        this.bgcolors = ['#f26868','#febd60', '#51c69f','#669aee', '#8276d6','#6a6a6a']
      return {

        fullflow: {                             // 总流量数据图
          columns: ['data', 'PV', 'UV'],
          rows: []
        },
        goodschart: {                             // 商品流量数据图
          columns: ['goodsName', 'PV', 'UV'],
          rows: []
        },
        showPV: true,
        goods_data:[],
        dateType:true,
        monthType:false,
        yearType:false,
        pageData:{
          param:{     //参数
            beginTime:'',           //开始时间
            endTime:'',             //结束时间
            goodsBeginTime:'',     //商品流量数据查询
            goodsEndTime:'',     //商品流量数据查询
            dataLoading:false,           //加载中显示状态
            goods_type:'',             //商品流量数据查询选择的是pv还是uv
            goods_time_type:'date'    //商品流量数据查询昨日本周本月
          },
        },

      }
    },
    methods: {
      //总流量数据筛选
      getFlowdata: function () {
        this.pageData.param.dataLoading = true;
        this.fullflow.rows = [];
        getFlowdata(this.pageData.param).then((res) => {
          this.pageData.param.dataLoading = false;
          console.log(res.data.data);
          this.pageData.param.beginTime = res.data.data.beginTime;
          this.pageData.param.endTime = res.data.data.endTime;
          for (let i = 0; i < res.data.data.fullFlow.length; i++) {//总流量数据
            this.fullflow.rows.push(res.data.data.fullFlow[i]);
          }
        });
      },
      //商品流量数据筛选
      getGoodsdata: function () {
        this.pageData.param.dataLoading = true;
        this.goodschart.rows = [];
        getFlowdata(this.pageData.param).then((res) => {
          this.pageData.param.dataLoading = false;
          console.log(res.data.data);
          this.pageData.param.goodsBeginTime = res.data.data.goodsBeginTime;
          this.pageData.param.goodsEndTime = res.data.data.goodsEndTime;
          for (let i = 0; i < res.data.data.goodsChart.length; i++) {//商品流量数据
            this.goodschart.rows.push(res.data.data.goodsChart[i]);
          }
          this.goods_data = res.data.data.goodsChart
        });
      },
      // TOP流量显示U数据
      isShowPV() {
        this.goods_data = [];
        this.goodschart.rows = [];
        this.showPV = true;
        this.pageData.param.goods_type = 'pv';
        this.getGoodsdata();
      },
      // TOP流量显示UV数据
      isShowUV() {
        this.goods_data = [];
        this.goodschart.rows = [];
        this.showPV = false;
        this.pageData.param.goods_type = 'uv';
        this.getGoodsdata();
      },
      goodsTime(type) {
        this.pageData.param.goods_time_type = type;
        this.dateType = false;
        this.monthType = false;
        this.yearType = false;
        this[type+'Type'] = true;
        this.getGoodsdata();
      },
      //时间选中事件
      updateDataVal:function(name,value){
        this.pageData.param[name] = value;
      },
    },
    mounted () {
      this.pageData.param.goods_type = 'pv';
      this.getFlowdata();
      this.getGoodsdata();
    },
    components: {
      VeLine,
      VePie
    },

  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../style/datacenter.css";
</style>
