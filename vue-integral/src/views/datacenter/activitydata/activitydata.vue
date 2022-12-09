<template>
    <div>
        <div class="page-title">
            <h2 v-text="'活跃数据'"></h2>
        </div>
<div class="page-content datacenter">
  <ul class="type-tabs cf">
    <li class="type-item" @click="tabsType('showOrdernum');">
        <a href="javascript:;" :class="{'active': showOrdernum }">订单数</a>
    </li>
    <li class="type-item" @click="tabsType('showConsumptionIntegral');">
        <a href="javascript:;" :class="{'active': showConsumptionIntegral }">消耗积分</a>
    </li>
    <li class="type-item" @click="tabsType('showConsumptionAmount');">
        <a href="javascript:;" :class="{'active': showConsumptionAmount }">消耗金额</a>
    </li>
    <li class="type-item" @click="tabsType('showOrderConversionRate');">
        <a href="javascript:;" :class="{'active': showOrderConversionRate }">订单转化率</a>
    </li>
  </ul>
  <template v-if="showOrdernum">
      <h3 class="reporttitle">新增订单数</h3>
      <div class="reportfilter cf">
          <div class="date-range">
              <v-date-picker placeholder="起始时间" className="noBorder" :initValue="activityData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
              <span class="range-split">-</span>
              <v-date-picker placeholder="结束时间" className="noBorder" :initValue="activityData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
          </div>
          <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="activityDataList">筛选</a>
          <!--<a class="download fr" href="javascript:;">
              <i class="iconfont icon-leadingOut"></i>导出当前结果
          </a>-->
      </div>
      <ve-line :data="Ordernum" :settings="OrdernumSettings"></ve-line>
      <div class="reportfilter cf">
          <h3 class="reporttitle">TOP订单数</h3>
          <!--<a class="download fr" href="javascript:;">
              <i class="iconfont icon-leadingOut"></i>导出当前结果
          </a>-->
      </div>
      <div class="reportchart">
          <!--商品top-->
          <div class="chart-line report-box">
              <ve-pie :data="goodsOrdernum" :settings="goodsOrdernumSettings" :colors="bgcolors"></ve-pie>
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
          <!--商品top   end-->

          <!--支付渠道占比top-->
          <div class="chart-line report-box">
              <ve-pie :data="channelRatio" :settings="channelRatioSettings" :colors="bgcolors"></ve-pie>
              <div>支付渠道占比TOP</div>
              <div class="text-list">
                  <template v-for="(item,index) in channel_data">
                      <div class="text-item" :title="item.channel" >
                          <span class="grade grade-pie grade" :class="'grade'+index">{{item.NO}}</span>
                          {{item.channel}}
                      </div>
                  </template>
              </div>
          </div>
          <!--支付渠道占比top   end-->
      </div>
  </template>
  <template v-else-if="showConsumptionIntegral">
      <h3 class="reporttitle">消耗积分数</h3>
      <div class="reportfilter cf">
          <div class="date-range">
              <v-date-picker placeholder="起始时间" className="noBorder" :initValue="activityData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
              <span class="range-split">-</span>
              <v-date-picker placeholder="结束时间" className="noBorder" :initValue="activityData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
          </div>
          <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="activityDataList">筛选</a>
          <!--<a class="download fr" href="javascript:;">
              <i class="iconfont icon-leadingOut"></i>导出当前结果
          </a>-->
      </div>
      <ve-line :data="consumptionIntegral" :settings="consumptionIntegralSettings"></ve-line>
      <div class="reportfilter cf">
          <h3 class="reporttitle">TOP积分消耗</h3>
          <!--<a class="download fr" href="javascript:;">
              <i class="iconfont icon-leadingOut"></i>导出当前结果
          </a>-->
      </div>
      <div class="reportchart">
          <div class="chart-line report-box">
              <ve-pie :data="goodsConsumptionIntegral" :settings="goodsConsumptionIntegralSettings" :colors="bgcolors"></ve-pie>
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
  </template>
  <template v-else-if="showConsumptionAmount">
      <h3 class="reporttitle">消耗金额</h3>
      <div class="reportfilter cf">
          <div class="date-range">
              <v-date-picker placeholder="起始时间" className="noBorder" :initValue="activityData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
              <span class="range-split">-</span>
              <v-date-picker placeholder="结束时间" className="noBorder" :initValue="activityData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
          </div>
          <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="activityDataList">筛选</a>
          <!--<a class="download fr" href="javascript:;">
              <i class="iconfont icon-leadingOut"></i>导出当前结果
          </a>-->
      </div>
      <ve-line :data="consumptionAmount" :settings="consumptionAmountSettings"></ve-line>
      <div class="reportfilter cf">
          <h3 class="reporttitle">TOP金额消耗</h3>
          <!--<a class="download fr" href="javascript:;">
              <i class="iconfont icon-leadingOut"></i>导出当前结果
          </a>-->
      </div>
      <div class="reportchart">
          <div class="chart-line report-box">
              <ve-pie :data="goodsConsumptionAmount" :settings="goodsConsumptionAmountSettings" :colors="bgcolors"></ve-pie>
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
  </template>
  <template v-else="showOrderConversionRate">
      <h3 class="reporttitle">日均转化率</h3>
      <div class="reportfilter cf">
          <div class="date-range">
              <v-date-picker placeholder="起始时间" className="noBorder" :initValue="activityData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
              <span class="range-split">-</span>
              <v-date-picker placeholder="结束时间" className="noBorder" :initValue="activityData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
          </div>
          <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="activityDataList">筛选</a>
          <!--<a class="download fr" href="javascript:;">
              <i class="iconfont icon-leadingOut"></i>导出当前结果
          </a>-->
      </div>
      <ve-line :data="orderConversionRate" :settings="orderConversionRateSettings"></ve-line>

          <h3 class="reporttitle">TOP转化率排行</h3>
          <div class="reportfilter cf">
              <div class="date-range">
                  <v-date-picker placeholder="起始时间" className="noBorder" :initValue="activityData.param.goodsBeginTime" @on-change="updateDataVal('goodsBeginTime',$event)" readonly></v-date-picker>
                  <span class="range-split">-</span>
                  <v-date-picker placeholder="结束时间" className="noBorder" :initValue="activityData.param.goodsEndTime" @on-change="updateDataVal('goodsEndTime',$event)" readonly></v-date-picker>
              </div>
              <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="goodsOrderConversionRateList">筛选</a>
              <!--<a class="download fr" href="javascript:;">
                  <i class="iconfont icon-leadingOut"></i>导出当前结果
              </a>-->
          </div>
      <ve-histogram :data="goodsOrderConversionRate" :settings="goodsOrderConversionRateSettings" :extend="usersectionExtend"></ve-histogram>
  </template>
    <transition name="loading">
        <v-loading v-show="activityData.param.dataLoading"></v-loading>
    </transition>
</div>
    </div>
</template>

<script>
    import Vue from 'vue'
    import VeLine from 'v-charts/lib/line.common'
    import VePie from 'v-charts/lib/pie.common'
    import VeHistogram from 'v-charts/lib/histogram.common'
    import {activityDataList}  from '@/service/getData'
    import {channelRatio}  from '@/service/getData'
 export default{
     data() {
         /* 设置显示的指标维度 */
         this.OrdernumSettings = {                 // 订单数
             labelMap: {
                 'data': '日期',
                 'order_count':'订单数',
             },
             yAxisName: ['单位：笔']
         },

             this.goodsOrdernumSettings = {    // TOP商品订单数
                 labelMap: {
                     'goodsName': '商品名称'
                 },
                 limitShowNum: 6,
                 label: {
                     normal: {
                         show: false,
                     }
                 },
             },
             this.channelRatioSettings = {    // TOP支付渠道占比
                 labelMap: {
                     'channel': '支付渠道'
                 },
                 limitShowNum: 5,
                 label: {
                     normal: {
                         show: false,
                     }
                 },
             },
             /* 设置显示的指标维度 */
             this.consumptionIntegralSettings = {                 // 消耗积分
                 labelMap: {
                     'data': '日期',
                     'used_integral':'消耗积分',
                 },
                 yAxisName: ['单位：积分']
             },

             this.goodsConsumptionIntegralSettings = {         // TOP商品消耗积分
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
             /* 设置显示的指标维度 */
             this.consumptionAmountSettings = {                 // 消耗金额
                 labelMap: {
                     'data': '日期',
                     'used_money':'消耗金额',
                 },
                 yAxisName: ['单位：元']
             },

             this.goodsConsumptionAmountSettings = {       // TOP商品消耗金额
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
             /* 设置显示的指标维度 */
             this.orderConversionRateSettings = {                 // 订单转化率
                 labelMap: {
                     'data': '日期',
                     'conversion_ratio':'转化率',
                 },
                 yAxisName: ['单位：%']
             },

             this.goodsOrderConversionRateSettings = {      // TOP商品订单转化率
                 labelMap: {
                     'conversion_ratio': '转化率',
                     'goodsName': '商品名称'
                 },
                 yAxisName: ['单位：无']
             },
             this.usersectionExtend = {
                 series (v) {
                     v.forEach(i => {
                         i.barWidth = 40
                     })
                     return v
                 },
             }
             /* 设置颜色 */
             this.bgcolors = ['#f26868','#febd60', '#51c69f','#669aee', '#8276d6','#6a6a6a'];
         return {
             // 新增订单
             Ordernum: {
                 columns: ['data', 'order_count'],
                 rows: []
             },
             // TOP商品订单数
             goodsOrdernum: {
                 columns: ['goodsName', 'order_count'],
                 rows: []
             },
             // TOP支付渠道占比
             channelRatio: {
                 columns: ['channel', 'num'],
                 rows: []
             },
             // 消耗积分
             consumptionIntegral: {
                 columns: ['data', 'used_integral'],
                 rows: []
             },
             // TOP商品消耗积分
             goodsConsumptionIntegral: {
                 columns: ['goodsName', 'used_integral'],
                 rows: []
             },
             // 消耗金额
             consumptionAmount: {
                 columns: ['data', 'used_money'],
                 rows: []
             },
             // TOP商品消耗金额
             goodsConsumptionAmount: {
                 columns: ['goodsName', 'used_money'],
                 rows: []
             },
             // 转化率
             orderConversionRate: {
                 columns: ['data', 'conversion_ratio'],
                 rows: []
             },
             // TOP商品转化率
             goodsOrderConversionRate: {
                 columns: ['goodsName', 'conversion_ratio'],
                 rows: []
             },
             goods_data:[],
             channel_data:[],
             showOrdernum: true,                              // 订单数
             showConsumptionIntegral: false,                  // 消耗积分
             showConsumptionAmount: false,                    // 消耗金额
             showOrderConversionRate: false,                  // 订单转化率
             activityData:{
                 param:{     //参数
                     beginTime:'',           //开始时间
                     endTime:'',             //结束时间
                     goodsBeginTime:'',     //商品转化率开始时间
                     goodsEndTime:'',      //商品转化率结束时间
                     active_type:'showOrdernum',         //查询条件,默认查询订单数
                 },
             },

         }
     },
     methods: {
         // 点击切换 标签
         tabsType(param) {
             if(param=='showOrdernum'){
                 this.channelRatioList();  //加载支付渠道占比
             }
             this.showOrdernum = false;
             this.showConsumptionIntegral = false;
             this.showConsumptionAmount = false;
             this.showOrderConversionRate = false;
             this[param] = true;
             this.activityData.param.active_type = param;
             this.activityData.param.beginTime = '';
             this.activityData.param.endTime = '';
             this.activityData.param.goodsBeginTime = '';
             this.activityData.param.goodsEndTime = '';
             this.activityDataList();
             if(this.activityData.param.active_type == "showOrderConversionRate"){//搜索条件为订单转化率的话,则调用转化率的方法加载数据
                 this.goodsOrderConversionRateList();
             }
         },
         //数据加载
         activityDataList: function () {
             var line_type = '';
             var pie_type = '';
             if(this.activityData.param.active_type == "showOrdernum"){
                 this.channelRatioList();  //加载支付渠道占比
                 line_type = this.Ordernum;
                 pie_type = this.goodsOrdernum;
             }else if(this.activityData.param.active_type == "showConsumptionIntegral"){
                 line_type = this.consumptionIntegral;
                 pie_type = this.goodsConsumptionIntegral;
             }else if(this.activityData.param.active_type == "showConsumptionAmount"){
                 line_type = this.consumptionAmount;
                 pie_type = this.goodsConsumptionAmount;
             }else if(this.activityData.param.active_type == "showOrderConversionRate"){
                 line_type = this.orderConversionRate;
                 //pie_type = this.goodsOrderConversionRate;
             }
             this.activityData.param.dataLoading = true;
             this.Ordernum.rows = [];//订单数
             this.goodsOrdernum.rows = [];//商品订单数
             this.consumptionIntegral.rows = [];//消耗积分
             this.goodsConsumptionIntegral.rows = [];//商品消耗积分
             this.consumptionAmount.rows = [];//消耗金额
             this.goodsConsumptionAmount.rows = [];//商品消耗金额
             this.orderConversionRate.rows = [];//订单转化率
             var that = this;
             activityDataList(this.activityData.param).then((res) => {
                 this.activityData.param.dataLoading = false;
                 console.log(res.data.data);
                 this.activityData.param.beginTime = res.data.data.beginTime;
                 this.activityData.param.endTime = res.data.data.endTime;
                 this.activityData.param.goodsBeginTime = res.data.data.goodsBeginTime;
                 this.activityData.param.goodsEndTime = res.data.data.goodsEndTime;
                 for (let i = 0; i < res.data.data.lineData.length; i++) {
                     line_type.rows.push(res.data.data.lineData[i]);
                 }
                 for (let i = 0; i < res.data.data.cakeData.length; i++) {
                     pie_type.rows.push(res.data.data.cakeData[i]);
                 }
                 /*if(this.activityData.param.active_type == "showOrderConversionRate"){//搜索条件为订单转化率的话,则调用转化率的方法加载数据
                     that.goodsOrderConversionRateList();
                 }else{

                 }*/

                 this.goods_data = res.data.data.cakeData
             });
         },
         //商品转化率数据单独加载
         goodsOrderConversionRateList: function () {
             //this.activityData.param.dataLoading = true;
             this.goodsOrderConversionRate.rows = [];//商品订单转化率
             activityDataList(this.activityData.param).then((res) => {
                 /*this.activityData.param.dataLoading = false;*/
                 console.log(res.data.data);
                 this.activityData.param.goodsBeginTime = res.data.data.goodsBeginTime;
                 this.activityData.param.goodsEndTime = res.data.data.goodsEndTime;
                 for (let i = 0; i < res.data.data.cakeData.length; i++) {
                     this.goodsOrderConversionRate.rows.push(res.data.data.cakeData[i]);
                 }
             });
         },
         //支付渠道占比数据单独加载
         channelRatioList: function () {
             channelRatio(this.activityData.param).then((res) => {
                 this.channelRatio.rows = [];//支付渠道占比
                 for (let i = 0; i < res.data.data.data.length; i++) {
                     this.channelRatio.rows.push(res.data.data.data[i]);
                 }
                 this.channel_data = res.data.data.data
             });
         },
         //时间选中事件
         updateDataVal:function(name,value){
             this.activityData.param[name] = value;
         },
     },
     mounted () {
         this.activityDataList();
     },
     components: {
         VeLine,
         VePie,
         VeHistogram
     },
 }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../style/datacenter.css";
</style>
