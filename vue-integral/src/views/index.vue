<template>
  <div style="padding-top:70px; padding-left: 80px;">
    <v-topTool :menuWrap="menuItem.menuOn" @current="current"></v-topTool>
    <v-sidebar :sidebar="sidebarItem" :sidebarCurrentId="sidebarCurrentId"  @current="current"></v-sidebar>
    <div class="content" :class="{ 'content-big' : isShow}">
      <v-subnav :subnavItem="subnavItem" :isShow="isShow" :titleName="titleName" @add-goods="showDialog('isShowAddGoods')"></v-subnav>
      <!--main-->
      <router-view></router-view>
    </div>
    <v-hidebar :class="{ hidebarHide: isShow }"  @hide-bar="hideBar"></v-hidebar>
    <v-dialog className="add-goods-modal" width="410px" modalTitle="添加商品"  ref="dialog" :showFooter="false" :is-show="isShowAddGoods" @on-close="hideDialog('isShowAddGoods')">
      <div class="modal-body cf">
        <!--<a class="add-goods-btn v-link-active"  @click="jump('isShowAddGoods','coupon','3')" style="box-sizing:initial;">
          <i class="iconfont icon-coupon"></i>
          <span style="box-sizing:initial;">添加优惠券</span>
        </a>-->
        <a class="add-goods-btn"  @click="jump('isShowAddGoods','newObject','1')" style="box-sizing:initial;">
          <i class="iconfont icon-object"></i> <span style="box-sizing:initial;">添加实物</span>
        </a>
        <a class="add-goods-btn" @click="jump('isShowAddGoods','newVirtual','2')" style="box-sizing:initial;">
          <i class="iconfont icon-virtual"></i>
          <span class="virtual" style="box-sizing:initial;">添加虚拟商品</span>
        </a>
      </div>
    </v-dialog>
  </div>
</template>

<script>
  import Subnav from './subnav/subnav.vue';
  import {getMenuList} from '@/service/getData';
  import * as SYSTEM from '@/api/system';
  import {setSessionStorage,getSessionStorage,removeSessionStorage} from '@/api/utils';
  import {botBarNav} from '@/api/localJson/topToolView';
  // 引入jq
  import $ from "jquery";

  export default{
    data(){
      return {
        isShow: false,
        topCurrentId:null,
        sidebarCurrentId:null,
        menuItem:{},
        sidebarItem:{},
        subnavItem:{},
        titleName:'',
        isShowAddGoods:false
      }
    },
    components: {
      'v-subnav': Subnav
    },methods: {
      current: function (currentId,titleName,obj) {
          if(-1 != currentId){
            this.sidebarCurrentId = currentId;
            this.titleName = titleName;
            this.subnavItem = this.menuItem.menuThree[currentId];
            setSessionStorage('SIDEBAR-CURRENT-ID',currentId);
            removeSessionStorage('SUBNAV-CURRENT-INDEX');
            this.$router.push({ path: this.subnavItem[0].URL});
          }else{
            this.sidebarCurrentId = currentId;
            this.titleName = titleName;
            this.subnavItem = obj;
            setSessionStorage('SIDEBAR-CURRENT-ID',currentId);
            removeSessionStorage('SUBNAV-CURRENT-INDEX');
          }
      },hideBar(isShow){
          this.isShow = isShow;
      },
      showDialog (param) {
        this[param] = true
        //this.$refs.dialog.loadClient();
      },
      hideDialog (param) {
        this[param] = false
      },
      jump(param,name,type){
        this[param] = false;
        this.$router.push({ name: name,params: { type:type }});
      }
    },
    created() {
        getMenuList().then(re =>{
          let res = re.data.data;
          if(SYSTEM.SUCCSSS == res.success && SYSTEM.CODE_IS_OK == res.code){
              this.menuItem = res.data;
              this.topCurrentId = this.menuItem.menuOn[0].id;
              this.sidebarItem = this.menuItem.menuTow[this.topCurrentId];
              this.sidebarCurrentId = getSessionStorage('SIDEBAR-CURRENT-ID') || this.sidebarItem[0].id;
              if(-1 != this.sidebarCurrentId){
                this.titleName = this.sidebarItem[0].name;
                this.subnavItem = this.menuItem.menuThree[this.sidebarCurrentId];
                this.$router.push({ path: this.subnavItem[0].URL});
              }else{
                this.titleName = '帐户';
                this.subnavItem = botBarNav[0].navChild;
                this.$router.push({ path: this.subnavItem[0].URL});
              }
          }
        });
    }
  }
</script>


<style rel="stylesheet/scss" lang="scss">
  @import '../style/common.scss';
  @import "../style/goodsList.scss";
  @import "../style/goods.scss";
  @import "../style/configure.scss";
  .content.content-big {
    padding-left: 0;
  }
  .loading-enter-active, .loading-leave-active {
    transition: opacity .3s
  }
  .loading-enter, .loading-leave-active {
    opacity: 0
  }
</style>
