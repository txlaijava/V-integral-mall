<template>
  <div class="main">
    <div class="page-title"><h2> 订单查询 </h2>
      <div class="subhandle" v-show="false">
        <a class="btn-export" href="javascript:void(0);">
          <i class="iconfont icon-leadingOut"></i>
          <span class="txt">导出当前结果</span>
        </a>
      </div>
    </div>
    <div class="page-content">
      <v-tip :status="true" className="ft14 mb20" v-show="isShowTip" @on-close="hideTip('isShowTip')">
        订单查询速度较慢，请耐心等待，不要频繁点击搜索和刷新页面。
      </v-tip>
      <form class="bottomline">
        <div class="form-filter-wrap">
          <div class="form-combination">
            <v-selection :selections="accountNumber" v-model="pageData.param.orderType" @selectCallBack="updateSelectVal('orderType',$event)"></v-selection>
            <input type="text" class="form-control" placeholder="请输入订单号" v-model="pageData.param.orderId"></div>
          <div class="form-combination">
            <v-selection :selections="time" v-model="pageData.param.timeType" @selectCallBack="updateSelectVal('timeType',$event)"></v-selection>
            <div class="date-range">
              <v-date-picker placeholder="起始时间" className="noBorder" :initValue="pageData.param.beginTime" @on-change="updateDataVal('beginTime',$event)" readonly></v-date-picker>
              <span class="range-split">-</span>
              <v-date-picker placeholder="结束时间" className="noBorder" :initValue="pageData.param.endTime" @on-change="updateDataVal('endTime',$event)" readonly></v-date-picker>
            </div>
          </div>
          <a href="javascript:void(0);" class="btn btn-default btn-lg btn-filter" @click="getData">筛选</a>&nbsp;&nbsp;
          <a href="javascript:void(0);" class="btn btn-default btn-lg btn-filter" @click="clickExport">导出</a>
          <div class="more-choose-wrap" v-show="isShowmore">
            <input type="text" class="form-control" placeholder="用户id" v-model="pageData.param.orderUserId">
            <v-selection :selections="status" v-model="pageData.param.orderState" @selectCallBack="updateSelectVal('orderState',$event)"></v-selection>
            <v-selection :selections="typeOfMerchandise" v-model="pageData.param.orderGoodsType" @selectCallBack="updateSelectVal('orderGoodsType',$event)"></v-selection>
            <v-selection :selections="channel" v-model="pageData.param.channel" @selectCallBack="updateSelectVal('channel',$event)"></v-selection>
            <input type="text" class="form-control" placeholder="兑换内容（关键词精准匹配" v-model="pageData.param.orderGoodsName">
          </div>
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
      <ul class="table-header column7">
        <li class="th-order"><span>下单</span></li>
        <li class="th-finish"><span>完成</span></li>
        <li class="th-content"><span>兑换内容</span></li>
        <li class="th-charge"><span>价格</span></li>
        <li><span>成本</span></li>
        <li><span>收入</span></li>
        <li><span>资金状态</span></li>
        <li class="th-status"><span>订单状态</span></li>
      </ul>
      <v-noresult v-if="pageData.dataLists.length <= 0 "></v-noresult>
      <template v-for="(orderList,index) in pageData.dataLists">
        <div class="table-row column7">
          <div class="row-header">
            <p class="order-info"> 平台订单号： <span>{{orderList.oid}}</span></p>
            <p class="order-info"> 开发者订单号： <span>{{orderList.bizId}}</span></p>
            <p class="order-info"> 用户ID： <span>{{orderList.devUserId}}</span></p>
            <p class="order-info"> 支付渠道：
              <span v-if="orderList.channel=='APP'">APP</span>
              <span v-else-if="orderList.channel=='WX'">微信H5</span>
              <span v-else-if="orderList.channel=='WXAPP'">小程序</span>
              <span v-else>暂无</span>
            </p>
            <p class="order-info"> 开发者备注：
              <span v-if="orderList.integralUserRemark">{{orderList.integralUserRemark}}</span>
                <span v-else>暂无</span>
            </p>
          </div>
          <ul class="row-items">
            <li><span>{{orderList.addTime}}</span></li>
            <li>
              <span v-if="orderList.completeTime">{{orderList.completeTime}}</span>
              <span v-else>-</span>
            </li>
            <li class="td-content"><span>{{orderList.orderGoods.orderGoodsName}}</span></li>
            <li>
              <span v-if="orderList.orderGoods.orderGoodsPrice>0">{{orderList.orderGoods.orderGoodsIntegralPrice}}积分+{{orderList.orderGoods.orderGoodsPrice}}元</span>
              <span v-else>{{orderList.orderGoods.orderGoodsIntegralPrice}}积分</span>
            </li>
            <li>
              <span v-if="orderList.orderGoods.goods.exchangeRules.goodsPriceUnit==0">{{orderList.orderGoods.goods.exchangeRules.goodsPrice}}元</span>
              <span v-else>{{orderList.orderGoods.goods.exchangeRules.goodsPrice}}积分</span>
            </li>
            <li>
              <span v-if="orderList.orderGoods.orderGoodsPrice>0">{{orderList.orderGoods.orderGoodsIntegralPrice}}积分+{{orderList.orderGoods.orderGoodsPrice}}元</span>
              <span v-else>{{orderList.orderGoods.orderGoodsIntegralPrice}}积分</span>
            </li>
            <li>
              <span>无</span>
            </li>
            <li class="td-status">
              <span v-if="orderList.orderState==10">
                <span><i class='iconfont icon-inHand' style='color: #ff9d60;'></i></span>
                <label>待付款</label>
              </span>
              <span v-else-if="orderList.orderState==20">
                <span><i class='iconfont icon-inHand' style='color: #ff9d60;'></i></span>
                <label>待审核</label>
              </span>
              <span v-else-if="orderList.orderState==30">
                <span><i class='iconfont icon-inHand' style='color: #ff9d60;'></i></span>
                <label>待发货</label>
              </span>
              <span v-else-if="orderList.orderState==40">
                <span><i class='iconfont icon-finish' style='color: #29b6b0;'></i></span>
                <label>已发货</label>
              </span>
              <span v-else-if="orderList.orderState==50">
                <span><i class='iconfont icon-finish' style='color: #29b6b0;'></i></span>
                <label>已完成</label>
              </span>
              <span v-else-if="orderList.orderState==60">
                <span><i class='iconfont icon-fail' style='color: #d14224;'></i></span>
                <label>兑换失败</label>
              </span>
              <span v-else-if="orderList.orderState==70">
                <span><i class='iconfont icon-fail' style='color: #d14224;'></i></span>
                <label>平台取消</label>
              </span>
              <span v-else-if="orderList.orderState==90">
                <span><i class='iconfont icon-fail' style='color: #d14224;'></i></span>
                <label>审核拒绝</label>
              </span>
            </li>
          </ul>
          <div class="row-footer" style="padding:8px 16px 18px 16px;">
            <div class="order-logistics" v-show="index!=isShow && orderList.orderState != 70">
              <span>物流信息</span>
              <a class="iconfont icon-arrowRight_semiCircle" href="javascript:;" @click="openLogistics(index)"></a>
            </div>
            <div class="order-logistics" v-show="index==isShow && orderList.orderState != 70">
              <div v-if="orderList.orderStateType==1" style="display: inline-block;">
                <span class="company">快递公司：{{orderList.logisticsName}}</span>
                <span class="num">物流编号：{{orderList.logisticsCode}}</span>
                <a href="javascript:;" class="iconfont icon-edit" v-if="orderList.orderState==40" @click="showDialog('isShowModifyExpressMessage',orderList);"></a>
                <span>收货信息：{{orderList.addrUserName + '，' + orderList.addrTelephone + '，' + orderList.addrAreaInfo }} </span>
              </div>
              <div v-if="orderList.orderStateType==2" style="display: inline-block;">
                <span class="company">配送方式：上门自提</span>
                <span class="num">自提地点：{{orderList.orderGoods.goods.takeSelfAddr}}</span>
              </div>
              <a class="iconfont icon-arrowLeft_semiCircle active" href="javascript:;" @click="closeLogistics()"></a>
            </div>
            <div class="order-logistics" v-show="index!=isShow && orderList.orderState == 70">
              <span>取消原因:</span>
              <span class="num">{{orderList.cancelReason}}</span>
            </div>
          </div>
        </div>
      </template>
      <div class="pagination">
        <div class="total-count">共{{pageData.dataLists.length}}条</div>
        <v-page :initCurrentPage="pageData.param.currentPage" :initTotalPages="pageData.param.totalPages" @click-page="page"></v-page>
      </div>
    </div>
    <v-dialog className="express-modal" width="400px" modalTitle="修改物流信息"  ref="dialog" :showFooter="true" :is-show="isShowModifyExpressMessage" @on-confirm="logisticsConfirm('isShowModifyExpressMessage')" @on-close="hideDialog('isShowModifyExpressMessage')">
      <div class="modal-body">
        <v-tip :status="false" className="mb20" v-show="isShowTip" >
          您可以对快递公司，物流编号进行修改
        </v-tip>
        <p class="item">
          <label>快递公司：</label>
          <select class="express-select" v-model="logisticsInfo.logisticsName">
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
        </p>
        <p class="item">
          <label>物流编号：</label>
          <input type="text" class="form-control valid dirty modified touched" placeholder="请填写物流编号" v-model="logisticsInfo.logisticsCode">
        </p>
      </div>

    </v-dialog>
    <transition name="loading">
      <v-loading v-show="pageData.param.dataLoading"></v-loading>
    </transition>
  </div>
</template>

<script>
  import {getOrderPage} from '@/service/getData';
  import {updateOrderLogistics} from '@/service/getData';
  import page from '@/components/base/page';
  import {getStorage, setStorage} from '../../api/utils';
  import config from '@/config/config';
  export default{
    data(){
      return {
        isShow: '-1',
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
            orderState:'',               //订单状态
            orderGoodsType:'',           //商品类型
            orderGoodsName:'',           //商品名称
            channel:"",                 //支付渠道
            dataLoading:false,           //加载中显示状态
            export:'',                   //导出
          },
          dataLists: {}       //数据容器
        },
        logisticsInfo:{
          orderId:'',                   //选中的订单号
          logisticsName:'',             //选中的订单的物流公司
          logisticsCode:'',             //选中的订单的物流单号
        },
        productId: 0,
        isShowTip: true,
        isShowModifyExpressMessage: false,       //修改快递信息
        accountNumber: [
          {label: '开发者订单号', value: 0},
          {label: '平台订单号', value: 1},
          {label: '开发者备注', value: 2}
        ],
        time: [
          {label: '下单时间', value: 0},
          {label: '完成时间', value: 1}
        ],
        status: [
          {label: '订单状态', value: ''},
          {label: '待付款', value: '10'},
          {label: '待审核', value: '20'},
          {label: '待发货', value: '30'},
          {label: '已发货', value: '40'},
          {label: '已完成', value: '50'},
          {label: '兑换失败', value: '60'}
        ],
        typeOfMerchandise: [
          {label: '商品类型', value: ''},
          {label: '实物兑换', value: '1'},
          {label: '虚拟商品', value: '2'},
          {label: '优惠券', value: '3'}
        ],
        channel:[
          {label: '任意渠道', value: ''},
          {label: 'app', value: 'APP'},
          {label: '微信H5', value: 'WX'},
          {label: '小程序', value: 'WXAPP'},
        ]
      }
    },watch:{
      'pageData.param.beginTime':function(newVal,oldVal){
      }
    },
    methods: {
      hideTip (param) {
        this[param] = false
      },
      //select选中事件
      updateSelectVal:function(name,value){
        this.pageData.param[name] = value;
      },
      //获取数据方法
      getData: function () {
        this.pageData.param.dataLoading = true;
        getOrderPage(this.pageData.param).then((response) => {
          this.pageData.param.dataLoading = false;
          var res = response.data.data.data;
          console.log(res);
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
      //时间选中事件
      updateDataVal:function(name,value){
        this.pageData.param[name] = value;
      },
      //物流信息展开事件
      openLogistics:function(index){
          this.isShow = index;
      },
      //物流信息关闭事件
      closeLogistics:function(){
        this.isShow = "-1";
      },
      //修改物流弹出框显示
      showDialog (param,orderInfo) {
        this[param] = true;
        this.logisticsInfo.orderId = orderInfo.oid;
        this.logisticsInfo.logisticsCode = orderInfo.logisticsCode;
        this.logisticsInfo.logisticsName = orderInfo.logisticsName;
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
            this.$Message.error(response.data.data.message);
          }else{
            this.$Message.error("服务器异常");
          }
          this.getData();
          this.hideDialog(param);
        })
      },
    //导出
    clickExport(){
        this.pageData.param.export = 'export';
        var param = this.pageData.param;
        var orderType = param.orderType?param.orderType:'';
        var orderId = param.orderId?param.orderId:'';
        var timeType = param.timeType?param.timeType:'';
        var beginTime = param.beginTime?param.beginTime:'';
        var endTime = param.endTime?param.endTime:'';
        var orderUserId = param.orderUserId?param.orderUserId:'';
        var orderGoodsName = param.orderGoodsName?param.orderGoodsName:'';
        var channel = param.channel?param.channel:'';
        var orderState = param.orderState?param.orderState:'';
        var orderGoodsType = param.orderGoodsType?param.orderGoodsType:'';
        location.href =  config.domainPath + config.baseURL + "/orderManage/orderListExport?orderType="+orderType
            +"&orderId="+orderId+"&timeType="+timeType+"&beginTime="+beginTime+"&endTime="+endTime+"&orderUserId="
            +orderUserId+"&orderState="+orderState+"&orderGoodsType="+orderGoodsType+"&orderGoodsName="
            +orderGoodsName+"&channel="+channel+"&export=export&Token="+getStorage('Token');
    },
    },
    created() {
        this.pageData.param.orderState = this.$route.params.orderState;//获取上个页面传递的参数
      this.getData();
    },components: {
      'v-page':page
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/order.css";
</style>
