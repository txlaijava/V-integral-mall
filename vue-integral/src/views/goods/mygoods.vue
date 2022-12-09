<template>
  <div class="main">
    <div class="page-title"><h2> 我的商品 </h2></div>
    <div class="page-content">
      <div class="form-inline goods-filter bottomline">
        <v-selection :selections="typeOfMerchandise"  v-model="pageData.param.goodsType" @selectCallBack="updateSelectVal('goodsType',$event)"></v-selection>
        <v-selection :selections="status"  v-model="pageData.param.goodsState" @selectCallBack="updateSelectVal('goodsState',$event)"></v-selection>
        <input type="text" class="form-control" placeholder="商品标题" style="width: 158px;" v-model="pageData.param.goodsName">
        <!--<v-selection :selections="allGoods"></v-selection>-->
        <v-selection :selections="priceType"  v-model="pageData.param.exchangePriceType" @selectCallBack="updateSelectVal('exchangePriceType',$event)"></v-selection>
        <a href="javascript:void(0)" class="btn btn-default btn-lg btn-filter" @click="getGoods">筛选</a>
        <a href="javascript:;" class="warning fr c-green" @click="showDialog('isShowWarning')" v-show="false"><span class="iconfont icon-remind"></span> 缺货提醒 </a>
      </div>
      <table class="table-goods table-goods-list">
        <thead>
        <tr class="tr-header">
          <th class="goods-check"></th>
          <th class="th-coupon"> 商品</th>
          <th class="th-status"> 状态</th>
          <th class="th-price"> 收费</th>
          <th class="th-outdate"> 过期时间</th>
          <th class="th-remaind"> 库存</th>
          <th> 操作</th>
        </tr>
        </thead>
        <tbody>
        <tr class="tr-items" v-for="(goodList,index) in pageData.goodLists">
          <td>
            <label class="checkbox-control">
              <input type="checkbox" :value="goodList.id" v-model="checkedList">
              <i class="icon " :class="{'iconfont':checkedList.indexOf(goodList.id)>-1}"><span :class="{'icon_checked':checkedList.indexOf(goodList.id)>-1}"></span></i>
            </label>
          </td>
          <td>
            <div class="goods-info">
              <img :src="imageWebServer+goodList.goodsMainPhoto.path+goodList.goodsMainPhoto.name" >
              <h4 >{{goodList.goodsName}}</h4>
              <p class="goods-credit" v-if="goodList.exchangeRules.exchangePriceType==1">{{goodList.exchangeRules.exchangeIntegralPrice}}积分</p>
              <p class="goods-credit" v-else>{{goodList.exchangeRules.exchangeIntegralPrice}}+{{goodList.exchangeRules.exchangePrice}}元</p>
                <template v-if="goodList.goodsType==1"><div class="logo">实物</div></template>
                <template v-if="goodList.goodsType==2"><div class="logo">虚拟</div></template>
            </div>
          </td>
          <td>
            <p class="goods-credit" v-if="goodList.goodsInventory<=0">下架</p>
            <p class="goods-credit" v-else-if="goodList.goodsState==2">未上架</p>
            <p class="goods-credit" v-else-if="goodList.goodsState==3">下架</p>
            <p class="goods-credit" v-else-if="goodList.goodsState==1">上架</p>
          </td>
          <td v-if="goodList.exchangeRules.chargePrice"> {{goodList.exchangeRules.chargePrice}}元</td>
          <td v-else>免费</td>
          <td v-if="goodList.autoLowerShelvesTime"> {{goodList.autoLowerShelvesTime}}</td>
          <td v-else> 无</td>
          <td v-if="goodList.goodsInventory>0"> {{goodList.goodsInventory}}</td>
          <td class="c-orange" v-else>缺货</td>
          <td class="td-actions">
            <div style="position: relative;">

              <span @mouseenter="enterActions('addCommodity'+goodList.id)" @mouseleave="leave" v-if="goodList.goodsState==2 || goodList.goodsState==3">
                  <a href="javascript:void(0)" class="iconfont icon-addCommodity" @click="updateGoodsAjax('up',goodList.id)"></a>
                </span>
              <div class="tooltip top addCommodity" role="tooltip" v-show=" isShow=='addCommodity'+goodList.id ? true : false" >
                <div class="tooltip-arrow"></div>
                <div class="tooltip-inner">上架</div>
              </div>

              <span @mouseenter="enterActions('unloadCommodity'+goodList.id)" @mouseleave="leave" v-if="goodList.goodsState==1">
                  <a href="javascript:void(0)" class="iconfont icon-unloadCommodity" @click="updateGoodsAjax('down',goodList.id)"></a>
                </span>
              <div class="tooltip top unloadCommodity" role="tooltip" v-show=" isShow=='unloadCommodity'+goodList.id ? true : false" >
                <div class="tooltip-arrow"></div>
                <div class="tooltip-inner">下架</div>
              </div>
              <span @mouseenter="enterActions('edit'+goodList.id)" @mouseleave="leave">
                  <router-link :to="{name:'object',params:{id:goodList.id,goodsState:goodList.goodsState}}" class="iconfont icon-edit" ></router-link>
                </span>
              <div class="tooltip top edit" role="tooltip" v-show=" isShow=='edit'+goodList.id ? true : false" >
                <div class="tooltip-arrow"></div>
                <div class="tooltip-inner">编辑</div>
              </div>
              <span @mouseenter="enterActions('phone2'+goodList.id)" @mouseleave="leave">
                  <a href="javascript:void(0)" class="iconfont icon-phone2" ></a>
                </span>
              <div class="tooltip top phone2" role="tooltip" v-show=" isShow=='phone2'+goodList.id ? true : false" >
                <div class="tooltip-arrow"></div>
                <div class="tooltip-inner">手机预览</div>
              </div>
              <span @mouseenter="enterActions('link'+goodList.id)" @mouseleave="leave">
                  <a href="javascript:void(0)" class="iconfont icon-link" ></a>
                </span>
              <div class="tooltip top link" role="tooltip" v-show=" isShow=='link'+goodList.id ? true : false" >
                <div class="tooltip-arrow"></div>
                <div class="tooltip-inner">链接</div>
              </div>
              <span @mouseenter="enterActions('delete'+goodList.id)" @mouseleave="leave">
                  <a href="javascript:void(0)" class="iconfont icon-delete" @click="showDel('isDelShow',goodList.id)"></a>
                </span>
              <div class="tooltip top delete" role="tooltip" v-show=" isShow=='delete'+goodList.id ? true : false" >
                <div class="tooltip-arrow" ></div>
                <div class="tooltip-inner">删除</div>
              </div>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <div class="pagination">
        <div class="batch-action">
          <label class="checkbox-control" @click="checkAll($event)">
            <input type="checkbox" >
            <i class="icon" :class="{'iconfont':checkedList.length == pageData.goodLists.length}"><span :class="{'icon_checked':checkedList.length == pageData.goodLists.length}"></span></i>
          </label>
          <span>已选择{{checkedList.length}}/{{pageData.goodLists.length}}商品</span>
          <a href="javascript:void(0)" class="btn btn-default btn-batch " :class="{'disabled':checkedList.length<=0}" @click="allUpdateGoods('up');">
            <i class="iconfont icon-addCommodity"></i> 批量上架
          </a>
          <a href="javascript:void(0)" class="btn btn-default btn-batch btn-delete" :class="{'disabled':checkedList.length<=0}" @click="allUpdateGoods('del');">
            <i class="iconfont icon-delete"></i> 批量删除
          </a>
          <!--<a href="javascript:void(0)" class="btn btn-default btn-batch btn-copy disabled">
            <i class="iconfont icon-copy"></i> 批量复制
          </a>-->
        </div>
        <v-page :initCurrentPage="pageData.param.currentPage" :initTotalPages="pageData.param.totalPages" @click-page="page"></v-page>
      </div>
    </div>
    <v-dialog width="600px" modalTitle="缺货列表"  ref="dialog" :showFooter="true" :is-show="isShowWarning" @on-close="hideDialog('isShowWarning')">
      <div class="modal-body stock-modal-form">
        <div class="form-groups-chunk">
          <div class="form-group">
            <label class="control-label">优惠券/虚拟商品库存预警值：</label>
            <input type="text" class="form-control valid untouched dirty modified form-control-dirty"
                   placeholder="优惠券库存预警值">
          </div>
          <div class="form-group">
            <label class="control-label">实物库存预警值：</label>
            <input type="text" class="form-control valid untouched dirty modified form-control-dirty"
                   placeholder="实物/虚拟商品库存预警值">
          </div>
        </div>
        <div class="warning-table-container">
          <table class="table-warning">
            <thead>
            <tr class="tr-header">
              <th>商品</th>
              <th>剩余库存</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr class="tr-items">
              <td>富光儿童水杯380ml</td>
              <td class="td-remaind">0</td>
              <td class="td-options">
                <div class="p">
                  <template v-for="(dialogAction,index) in dialogActions">
                    <span @mouseenter="enterActions(dialogAction)" @mouseleave="leave">
                    <a href="javascript:void(0)" class="iconfont" :class="dialogAction.class"></a>
                    </span>
                      <div class="tooltip top" :class="dialogAction.tooltip_class" role="tooltip" v-show=" isShow==dialogAction ? true : false">
                        <div class="tooltip-arrow"></div>
                        <div class="tooltip-inner">{{dialogAction.tooltip_text}}</div>
                      </div>
                  </template>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </v-dialog>
    <v-dialog width="300px" modalTitle="删除操作"  ref="dialog" :showFooter="true" :is-show="isDelShow" @on-confirm="delConfirm('isDelShow')" @on-close="hideDialog('isDelShow')">
      <div class="modal-body stock-modal-form">
        <div class="form-groups-chunk" >确认删除该商品？</div>
      </div>
    </v-dialog>
    <transition name="loading">
      <v-loading v-show="pageData.param.dataLoading"></v-loading>
    </transition>
  </div>
</template>

<script>
  import {getGoods,updateGoods} from '@/service/getData';
  import * as SYSTEM from '@/api/system';
  import page from '@/components/base/page'
  import config from '@/config/config';
  export default{
    data(){
      return {
        imageWebServer:config.ossImgPath,
        isShow: false,
        maygoods: {},
        selectGroup: {},
        //分页信息
        pageData:{
          param:{     //参数
            currentPage:1,
            totalPages: 1,
            pageSize:10,
            goodsName:"",
            goodsType:'',
            goodsState:'',
            exchangePriceType:'',
            dataLoading:false
          },
          goodLists: {}       //数据容器
        },
        deleteGoodsId:'',
        isDelShow:false,
        checkedList:[],     //全选数组
        checked: "",
        selectList_text: "",
        menuList_ext: "",
        typeOfMerchandise: [
            {label: '商品类型', value: ''},
          {label: '实物类', value: '1'},
          {label: '虚拟类', value: '2'},
          {label: '优惠券', value: '3'}],
        status: [
            {label: '状态', value: ''},
          {label: '已发布', value: '1'},
          {label: '未上架', value: '2'},
          {label: '已下架', value: '3'}
          ],
        allGoods: [
            {label: '全部商品', value: ''},
          {label: '自有商品', value: '1'},
          {label: '兑吧商品', value: '2'},
          {label: '秒杀商品', value: '3'}],
        priceType: [
            {label: '商品价格类型', value: ''},
          {label: '含有人民币', value: '2'},
          {label: '只含有积分', value: '1'}
          ],
        isShowWarning: false,
        isShowAddGoods:false,
        dialogActions: [{
          class:'icon-unloadCommodity',
          tooltip_class: 'unloadCommodity',
          tooltip_text: '下架'
        },{
          class:'icon-replenishment',
          tooltip_class: 'replenishment',
          tooltip_text: '补货'
        }]
      }
    },
    watch:{
      checkedList:{
        handler: function (val, oldVal){
        },
        immediate: true
      }
    },
    methods: {
        //初始化加载数据方法
      getGoods: function () {
        this.pageData.param.dataLoading = true;
        getGoods(this.pageData.param).then((response) => {
            this.pageData.param.dataLoading = false;
            this.pageData.goodLists = response.data.data;
            this.pageData.param.currentPage = response.data.currentPage;
            this.pageData.param.totalPages = response.data.pages;
        });
      },
      //上架下架
      updateGoodsAjax:function(type,id){
        updateGoods({type:type,ids:id}).then((res) =>{
            var param = res.data.data;
            if(param.success){
                this.$Message.success("操作成功");
                this.getGoods();
            }else{
              this.$Message.error("服务器异常");
              this.getGoods();
            }
        })
      },
      allUpdateGoods:function(type){
          var ids = this.checkedList.join(',');
          this.updateGoodsAjax(type,ids);
          this.checkedList = [];
      },
      delConfirm:function(param){
          this.updateGoodsAjax('del',this.deleteGoodsId);
          this[param] = false;
          this.deleteGoodsId = "";
      },
      showDel:function(param,id){
          this[param] = true;
          this.deleteGoodsId = id;
      },
      // 点击全选事件
      checkAll(e){
        if(e.target.checked){
          this.pageData.goodLists.forEach((el,i)=>{
            // 数组里没有这一个value才push，防止重复push
            if(this.checkedList.indexOf(el.id)<0){
              this.checkedList.push(el.id);
            }
          });
        }else { // 全不选选则清空绑定的数组
          this.checkedList = [];
        }
      },
      //select选中事件
      updateSelectVal:function(name,value){
          this.pageData.param[name] = value;
      },
      enterActions(actionsList){
        this.isShow = actionsList;
      },
      leave(){
        this.isShow = false;
      },
      selectMenuList(menuList, menuListText){
        this.menuList_ext = menuListText;
        this.checked = menuList;
        this.isShow = false;
      },
      showDialog (param) {
        this[param] = true
      },
      hideDialog (param) {
        this[param] = false
      },
      //分页跳转方法
      page:function(currentPage){
          this.pageData.param.currentPage = currentPage;
          this.getGoods();
      }
    },
    created() {
      this.getGoods();
      this.enterActions();
      this.leave();
    },components: {
      'v-page':page
    },
    mounted() {
      //this.$refs.dialog.loadClient();
      const that = this;
      window.onresize = function temp() {
//        that.$refs.dialog.loadClient();
      };
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/addGoods.css";
</style>
