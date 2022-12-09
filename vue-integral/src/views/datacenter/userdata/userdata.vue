<template>
    <div>
        <div class="page-title">
            <h2 v-text="'用户数据'"></h2>
        </div>
  <div class="page-content datacenter">
    <h3 class="reporttitle">新增用户</h3>
    <div class="reportfilter cf">
      <div class="date-range">
        <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.addUserBeginTime" @on-change="updateDataVal('addUserBeginTime',$event)" readonly></v-date-picker>
        <span class="range-split">-</span>
        <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.addUserEndTime" @on-change="updateDataVal('addUserEndTime',$event)" readonly></v-date-picker>
      </div>
      <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getAddUserData">筛选</a>
      <!--<a class="download fr" href="javascript:;">
        <i class="iconfont icon-leadingOut"></i>导出当前结果
      </a>-->
    </div>
    <ve-line :data="adduser" :settings="adduserSettings"></ve-line>
    <p class="reportline"></p>
    <h3 class="reporttitle">访客占比<span>（登录积分商城但并未下单用户）</span></h3>
    <div class="reportfilter cf">
      <div class="date-range">
        <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.visitorPerBeginTime" @on-change="updateDataVal('visitorPerBeginTime',$event)" readonly></v-date-picker>
        <span class="range-split">-</span>
        <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.visitorPerEndTime" @on-change="updateDataVal('visitorPerEndTime',$event)" readonly></v-date-picker>
      </div>
      <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getVisitorPerData">筛选</a>
      <!--<a class="download fr" href="javascript:;">
        <i class="iconfont icon-leadingOut"></i>导出当前结果
      </a>-->
    </div>
    <ve-line :data="userproportion" :settings="userproportionSettings"></ve-line>
    <p class="reportline"></p>
    <h3 class="reporttitle">人均积分</h3>
    <div class="reportfilter cf">
      <div class="date-range">
        <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.averageIntegralBeginTime" @on-change="updateDataVal('averageIntegralBeginTime',$event)" readonly></v-date-picker>
        <span class="range-split">-</span>
        <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.averageIntegralEndTime" @on-change="updateDataVal('averageIntegralEndTime',$event)" readonly></v-date-picker>
      </div>
      <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getAverageIntegralData">筛选</a>
      <!--<a class="download fr" href="javascript:;">
        <i class="iconfont icon-leadingOut"></i>导出当前结果
      </a>-->
    </div>
    <ve-line :data="preintegral" :settings="preintegralSettings"></ve-line>
    <p class="reportline"></p>
    <h3 class="reporttitle">用户积分分布区间</h3>
    <div class="reportfilter cf">
      <div class="date-range">
        <v-date-picker placeholder="时间" className="noBorder" :initValue="pageData.param.disIntegralTime" @on-change="updateDataVal('disIntegralTime',$event)" readonly></v-date-picker>
      </div>
      <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getDisIntegralData">筛选</a>
     <!-- <a class="download fr" href="javascript:;">
        <i class="iconfont icon-leadingOut"></i>导出当前结果
      </a>-->
    </div>
    <ve-histogram :data="usersection" :settings="usersectionSettings" :extend="usersectionExtend"></ve-histogram>
  </div>
    </div>
</template>

<script>
  import Vue from 'vue'
  import VeLine from 'v-charts/lib/line.common'
  import VeHistogram from 'v-charts/lib/histogram.common'
  import {getAddUserData}  from '@/service/getData'
  import {getVisitorPerData}  from '@/service/getData'
  import {getAverageIntegralData}  from '@/service/getData'
  import {getDisIntegralData}  from '@/service/getData'

  export default{
    data() {
      /*设置显示的指标维度*/
      this.adduserSettings = {              // 新增用户
        labelMap: {
          'adduser': '新增用户',
          'data': '日期'
        },
        yAxisName: ['单位：无']
      },
      this.userproportionSettings = {      // 访客占比
        labelMap: {
          'userproportion': '访客占比',
          'data': '日期'
        },
        yAxisName: ['单位：%']
      },
      this.preintegralSettings = {        // 人均积分
        labelMap: {
          'preintegral': '人均积分',
          'data': '日期'
        },
        yAxisName: ['单位：积分']
      },
      this.usersectionSettings = {        // 用户积分分布区间
        labelMap: {
          'usersection': '分布用户数',
          'data': '日期'
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
      return {
        // 新增用户
        adduser: {
          columns: ['data', 'adduser'],
          rows: []
        },
        // 访客占比
        userproportion: {
          columns: ['data', 'userproportion'],
          rows: []
        },
        // 人均积分
        preintegral: {
          columns: ['data', 'preintegral'],
          rows: []
        },
        // 用户积分分布区间
        usersection: {
          columns: ['data', 'usersection'],
          rows: []
        },
        pageData:{
          param:{     //参数
            addUserBeginTime:'',           //新增用户开始时间
            addUserEndTime:'',             //新增用户结束时间
            visitorPerBeginTime:'',           //访客占比开始时间
            visitorPerEndTime:'',             //访客占比结束时间
            averageIntegralBeginTime:'',           //人均积分开始时间
            averageIntegralEndTime:'',             //人均积分结束时间
            disIntegralTime:'',           //积分分布时间
            dataLoading:false,           //加载中显示状态
          },
        },
      }
    },
    methods: {
      //新增用户数据
      getAddUserData: function () {
        this.pageData.param.dataLoading = true;
        getAddUserData(this.pageData.param).then((res) => {
            console.log(res.data.data)
          this.pageData.param.dataLoading = false;
          this.pageData.param.addUserBeginTime = res.data.data.addUserBeginTime;
          this.pageData.param.addUserEndTime = res.data.data.addUserEndTime;
          this.adduser.rows = [];
          for (let i = 0; i < res.data.data.adduser.length; i++) {
            this.adduser.rows.push(res.data.data.adduser[i]);
          }
        });
      },
      //访客占比数据
      getVisitorPerData: function () {
        this.pageData.param.dataLoading = true;
        getVisitorPerData(this.pageData.param).then((res) => {
          this.pageData.param.dataLoading = false;
          this.pageData.param.visitorPerBeginTime = res.data.data.visitorPerBeginTime;
          this.pageData.param.visitorPerEndTime = res.data.data.visitorPerEndTime;
          this.userproportion.rows = [];
          for (let i = 0; i < res.data.data.userproportion.length; i++) {
            this.userproportion.rows.push(res.data.data.userproportion[i]);
          }
        });
      },
      //人均积分数据
      getAverageIntegralData: function () {
        this.pageData.param.dataLoading = true;
        getAverageIntegralData(this.pageData.param).then((res) => {
          this.pageData.param.dataLoading = false;
          this.pageData.param.averageIntegralBeginTime = res.data.data.averageIntegralBeginTime;
          this.pageData.param.averageIntegralEndTime = res.data.data.averageIntegralEndTime;
          this.preintegral.rows = [];
          for (let i = 0; i < res.data.data.preintegral.length; i++) {
            this.preintegral.rows.push(res.data.data.preintegral[i]);
          }
        });
      },
      //积分分布数据
      getDisIntegralData: function () {
        this.pageData.param.dataLoading = true;
        getDisIntegralData(this.pageData.param).then((res) => {
          this.pageData.param.dataLoading = false;
          this.pageData.param.disIntegralTime = res.data.data.disIntegralTime;
          this.usersection.rows = [];
          //for(var i=0;i<)
          for(var key in res.data.data.usersection){
            var ddd = {};
            var value = res.data.data.usersection[key];
            console.log(JSON.stringify(value));
            for(var p in value){
              ddd.data = p;
              ddd.usersection = value[p];
            }
            //ddd.data = key;
            //ddd.usersection = res.data.data.usersection[key]
            this.usersection.rows.push(ddd);
          }
        });
      },
      //时间选中事件
      updateDataVal:function(name,value){
        this.pageData.param[name] = value;
      },
    },
    mounted () {
      this.getAddUserData();
      this.getVisitorPerData();
      this.getAverageIntegralData();
      this.getDisIntegralData();
    },
    components: {
      VeLine,
      VeHistogram
    },
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../style/datacenter.css";
</style>
