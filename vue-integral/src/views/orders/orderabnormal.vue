<template>
  <div class="main">
    <div class="page-title"><h2> 虚拟商品异常订单 </h2></div>
    <div class="page-content">
      <v-tip :status="true" className="ft14 mb20" v-show="isShowTip" @on-close="hideTip('isShowTip')">
        展示所有未成功的虚拟商品领奖订单，停留在处理中的状态，需要手动处理
      </v-tip>
      <form class="bottomline">
        <div class="form-filter-wrap">
          <input type="text" class="form-control" placeholder="平台订单号">
          <input type="text" class="form-control" placeholder="流水号">
          <input type="text" class="form-control" placeholder="用户id">
          <a href="javascript:;" class="btn btn-default btn-lg btn-filter">筛选</a></div>
      </form>
      <ul class="table-header column5">
        <li><span>创建时间</span></li>
        <li><span>订单来源</span></li>
        <li><span>兑换内容</span></li>
        <li><span>虚拟商品充值状态</span></li>
        <li><span>操作</span></li>
      </ul>
      <v-noresult></v-noresult>
      <div class="table-row column4 orderwaitship-tbl" v-for="(orderList,index) in orderList">
        <div class="row-header"><p class="order-info"><span>{{orderList.orderTime}}</span></p>
          <p class="order-info"> 平台订单号： <span>{{orderList.duibaOrderNumber}}</span></p>
          <p class="order-info"> 开发者订单号： <span>{{orderList.developerOrderNumber}}</span></p>
          <p class="order-info"> 用户ID： <span>{{orderList.userID}}</span></p></div>
        <ul class="row-items">
          <li class="td-img"><img class="sm-img" :src="orderList.commodityPic">
            <span class="txt">{{orderList.commodityName}}</span></li>
          <li class="text-left"><span>{{orderList.nameOfConsignee + '，' + orderList.telephoneOfConsignee + '，' + orderList.addrOfConsignee}}</span>
          </li>
          <li class="td-express"><select class="express-select">
            <option>圆通速递</option>
            <option>申通快递</option>
            <option>顺丰速运</option>
            <option>中通快递</option>
            <option>宅急送</option>
            <option>韵达快递</option>
            <option>天天快递</option>
            <option>百世汇通</option>
            <option>EMS经济快递</option>
            <option>EMS</option>
            <option>快捷快递</option>
            <option>优速快递</option>
            <option>国通快递</option>
            <option>全峰快递</option>
            <option>德邦物流</option>
            <option>信丰物流</option>
            <option>安能快递</option>
            <option>京东快递</option>
            <option>其它</option>
          </select>
            <div class="form-wrap"><input type="text" class="form-control" placeholder="请填写运单号"></div>
          </li>
          <li class="td-operation"><a href="javascript:;" class="btn btn-green mr10">确认发货</a> <a
            href="javascript:;" class="btn btn-default">取消订单</a></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  export default{
    data(){
      return {
        isShowTip: true,
      }
    },
    methods: {
      hideTip(param){
          this[param] = false
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/order.css";
</style>
