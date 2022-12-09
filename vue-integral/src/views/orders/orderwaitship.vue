<template>
  <div class="main">
    <div class="page-title"><h2> 待发货订单 </h2>
      <div class="subhandle">
        <a href="javascript:;" class="btn-batch" v-show="false"><i class="iconfont"></i><span class="txt">批量发货</span></a>
        <a class="btn-export" href="javascript:void(0)" v-show="false"><i class="iconfont icon-leadingOut"></i><span class="txt">导出当前结果</span></a></div>
    </div>
    <div class="page-content">
      <form class="bottomline">
        <div class="form-filter-wrap">
          <div class="form-combination">
            <v-selection :selections="accountNumber" v-model="pageData.param.orderType" @selectCallBack="updateSelectVal('orderType',$event)"></v-selection>
            <input type="text" class="form-control" placeholder="请输入订单号" v-model="pageData.param.orderId">
          </div>
          <div class="date-range">
            <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
            <span class="range-split">-</span>
            <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
          </div>
          <a href="javascript:;" class="btn btn-default btn-lg btn-filter" @click="getData">筛选</a>
          <div class="more-choose-wrap"  v-show="isShowmore">
            <input type="text" class="form-control" placeholder="用户id" v-model="pageData.param.orderUserId">
            <input type="text" class="form-control" placeholder="兑换内容（关键词精准匹配" v-model="pageData.param.orderGoodsName">
            <input type="text" class="form-control" placeholder="收货人电话" v-model="pageData.param.addrTelephone">
          </div>
        </div>
        <div class="btn-side-wrap">
          <a href="javascript:;" class="btn btn-more">
            <span v-show="isShowmore"  @click="isShowmore=false,isShowcurrent=true"> 简单条件 <i class="iconfont icon-dropUp"></i> </span>
            <span v-show="isShowcurrent" @click="isShowmore = true,isShowcurrent=false"> 更多条件 <i class="iconfont icon-dropDown"></i> </span>
          </a>
        </div>
      </form>
      <ul class="table-header column4 orderwaitship-tbl-header">
        <li><span>兑换内容</span></li>
        <li><span>收货信息</span></li>
        <li class="td-express"><span>物流信息</span></li>
        <li class="td-operation"><span>操作</span></li>
      </ul>
      <v-noresult v-if="pageData.dataLists.length <= 0 "></v-noresult>
      <div class="table-row column4 orderwaitship-tbl" v-for="(orderList,index) in pageData.dataLists">
        <div class="row-header"><p class="order-info"><span>{{orderList.addTime}}</span></p>
          <p class="order-info"> 平台订单号： <span>{{orderList.oid}}</span></p>
          <p class="order-info"> 开发者订单号： <span>{{orderList.bizId}}</span></p>
          <p class="order-info"> 用户ID： <span>{{orderList.devUserId}}</span></p></div>
        <ul class="row-items">
          <li class="td-img">
            <img class="sm-img" :src="imageWebServer+orderList.orderGoods.goods.goodsMainPhoto.path+orderList.orderGoods.goods.goodsMainPhoto.name">
            <span class="txt">{{orderList.orderGoods.orderGoodsName}}</span>
          </li>
          <li class="text-left">
            <span v-if="orderList.orderStateType==1">{{orderList.addrUserName + '，' + orderList.addrTelephone + '，' + orderList.addrAreaInfo}}</span>
            <span v-if="orderList.orderStateType==2">上门自提</span>
          </li>
          <li class="td-express" v-if="orderList.orderStateType==1">
            <select class="express-select" v-model="orderList.logisticsName" >
              <option value="圆通快递">圆通快递</option>
              <option value="申通快递">申通快递</option>
              <option value="顺丰速运">顺丰速运</option>
              <option value="中通快递">中通快递</option>
              <option value="宅急送">宅急送</option>
              <option value="韵达快递">韵达快递</option>
              <option value="天天快递">天天快递</option>
              <option value="百世汇通">百世汇通</option>
              <option value="EMS经济快递">EMS经济快递</option>
              <option value="EMS">EMS</option>
              <option value="快捷快递">快捷快递</option>
              <option value="优速快递">优速快递</option>
              <option value="国通快递">国通快递</option>
              <option value="全峰快递">全峰快递</option>
              <option value="德邦物流">德邦物流</option>
              <option value="信丰物流">信丰物流</option>
              <option value="安能快递">安能快递</option>
              <option value="京东快递">京东快递</option>
              <option value="其它">其它</option>
            </select>
            <div class="form-wrap"><input type="text" class="form-control" placeholder="请填写运单号" style="width: 180px;" v-model="orderList.logisticsCode"></div>
          </li>
          <li v-if="orderList.orderStateType==2">
            {{orderList.orderGoods.goods.takeSelfAddr}}
          </li>
          <li class="td-operation">
            <div >
              <a href="javascript:;" class="btn btn-green mr10" @click="showDialog('isShowModifyExpressMessage',orderList);">确认发货</a>
              <a href="javascript:;" class="btn btn-default" @click="cancelOrderInfo.show = true,cancelOrderInfo.oid = orderList.oid">取消订单</a>
            </div>

          </li>
        </ul>
      </div>
      <div class="pagination">
        <div class="total-count">共{{pageData.dataLists.length}}条</div>
        <v-page :initCurrentPage="pageData.param.currentPage" :initTotalPages="pageData.param.totalPages" @click-page="page"></v-page>
      </div>
    </div>

    <v-dialog className="express-modal" width="400px" modalTitle="确认发货"  ref="dialog" :showFooter="true" :is-show="isShowModifyExpressMessage" @on-confirm="logisticsConfirm('isShowModifyExpressMessage')" @on-close="hideDialog('isShowModifyExpressMessage')">
      <div class="modal-body">
        <v-tip :status="false" className="mb20" v-show="true" >
          请再次核对以下发货信息
        </v-tip>
          <template v-if="logisticsInfo.orderStateType=='' || logisticsInfo.orderStateType==1">
              <p class="item" style="height: 25px;" >
                  <label style="width: 100%;">快递公司：<span style="font-weight:bold;">{{logisticsInfo.logisticsName}}</span></label>
              </p>
              <p class="item" style="height: 25px;">
                  <label style="width: 100%;">物流编号：<span style="font-weight:bold;">{{logisticsInfo.logisticsCode}}</span></label>
              </p>
          </template>
          <template v-if="logisticsInfo.orderStateType==2">
              <p class="item" style="height: 25px;" >
                  <label style="width: 100%;">配送方式：<span style="font-weight:bold;">上门自提</span></label>
              </p>
              <p class="item" style="height: 25px;">
                  <label style="width: 100%;">自提地点：<span style="font-weight:bold;">{{logisticsInfo.takeSelfAddr}}</span></label>
              </p>
          </template>
        <p class="item" style="height: 20px;">
          <label style="width: 100%;">确认无误后点击发货</label>
        </p>
      </div>
    </v-dialog>

    <Modal v-model="cancelOrderInfo.show" :closable="false" title="取消订单" @on-ok="cancelSelectOrder" @on-cancel="canceOrderCance">
      <Input v-model="cancelOrderInfo.reason" type="textarea" placeholder="请填写取消订单的原因"></Input>
    </Modal>

    <transition name="loading">
      <v-loading v-show="pageData.param.dataLoading"></v-loading>
    </transition>
  </div>
</template>

<script>
  import {getOrderPage,updateOrderLogistics,cancelOrder} from '@/service/getData';
  import page from '@/components/base/page'
  import * as SYSTEM from '@/api/system';
  import config from '@/config/config';
  export default{
    data(){
      return {
        imageWebServer:config.ossImgPath,
        isShow: false,
        isShowmore: false,
        isShowcurrent: true,
        //分页信息
        pageData:{
          param:{     //参数
            currentPage:1,
            totalPages: 1,
            pageSize:10,
            orderType:0,            //订单查询类型
            orderId:'',             //订单号
            timeType:0,             //时间查询类型
            beginTime:'',           //开始时间
            endTime:'',             //结束时间
            orderUserId:'',              //用户id
            orderState:'30',             //订单状态
            orderGoodsType:'',           //商品类型
            orderGoodsName:'',           //商品名称
            addrTelephone:'',            //收货人电话
            dataLoading:false            //加载中显示状态
          },
          dataLists: {}       //数据容器
        },
        logisticsInfo:{
          orderId:'',                   //选中的订单号
          orderStateType:'',            //订单配送类型
          logisticsName:'',             //选中的订单的物流公司
          logisticsCode:'',             //选中的订单的物流单号
          takeSelfAddr:''               //自提地点
        },
        isShowModifyExpressMessage: false,       //修改快递信息
        accountNumber: [{
          label: '开发者订单号',
          value: 0
        }, {
          label: '平台订单号',
          value: 1
        }],
        cancelOrderInfo:{
            oid:'',
            show:false,
            reason:'',
        },
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
      //修改物流弹出框显示
      showDialog (param,orderInfo) {
        if(orderInfo.orderStateType == 1 && (orderInfo.logisticsCode==undefined || orderInfo.logisticsName==undefined || orderInfo.logisticsCode=="" || orderInfo.logisticsName == "")){
            this.$Message.error("物流公司和物流单号不能为空");
            return;
        }
        this[param] = true;
        this.logisticsInfo.orderId = orderInfo.oid;
        this.logisticsInfo.logisticsCode = orderInfo.logisticsCode;
        this.logisticsInfo.logisticsName = orderInfo.logisticsName;
        this.logisticsInfo.orderStateType = orderInfo.orderStateType;
        this.logisticsInfo.takeSelfAddr = orderInfo.orderGoods.goods.takeSelfAddr;
      },
      //修改物流弹出框隐藏
      hideDialog (param) {
        this[param] = false;
        this.logisticsInfo.orderId = '';
        this.logisticsInfo.logisticsCode = '';
        this.logisticsInfo.logisticsName = '';
      },
      //确认修改物流事件
      logisticsConfirm(param){
        updateOrderLogistics(this.logisticsInfo).then((response) =>{
          if(response.data.data.status!=undefined && response.data.data.status==1000){
            this.$Message.success(response.data.data.message);
          }else{
            this.$Message.error("服务器异常");
          }
          this.getData();
          this.hideDialog(param);
        })
      },
      //取消-取消订单操作
      canceOrderCance(){
        this.cancelOrderInfo.show = false;
        this.cancelOrderInfo.oid = '';
      },
      //取消订单
      cancelSelectOrder(){
        if(this.cancelOrderInfo.reason == ""){
          this.$Message.error("请输入取消原因");
          return
        }
        cancelOrder({orderId:this.cancelOrderInfo.oid,reason:this.cancelOrderInfo.reason}).then((response)=>{
          if(response.data.data.code == 0){
            this.$Message.success(response.data.data.message);
          }else{
            this.$Message.error("服务器异常");
          }
          this.canceOrderCance();
          this.getData();
        })

      },
      //select选中事件
      updateSelectVal:function(name,value){
        this.pageData.param[name] = value;
      },
      //时间选中事件
      updateDataVal:function(name,value){
        this.pageData.param[name] = value;
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
