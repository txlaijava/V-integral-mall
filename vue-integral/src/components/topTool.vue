<template>
  <div class="top-tool-wrap">
    <div class="toptool-wrap">

      <!-- 应用 -->
      <div class="topbar-wrap">
        <!-- 当前选择的应用 -->
        <div class="app-logo">
          <a href="javascript:void(0);" class="btn-open">
            <img :src="topBarWrap.selected.pic_url" class="logo">
          </a>
        </div>

        <!-- 应用列表 -->
        <div class="sidebar-topbar-layout">
          <div class="logo-space-wrap" @click="dropDownListShow()">
            <div class="logo-space"><span class="appname" :title="topBarWrap.selected.txt">{{topBarWrap.selected.txt}}</span></div>
          </div>
          <div class="dropdown-list" v-show="!isShow" style="z-index:1000;">
            <ul class="app-list">
              <li v-for=" (item,index) in topBarWrap.navItem"  @click.stop="tabs(item.id)">
                <a href="javascript:void(0)">
                  <img :src="item.pic_url">
                  <span class="nameShow" :title="item.txt">{{item.txt}}</span>
                </a>
              </li>
            </ul>
            <div class="btn-add-app" @click="showModal(true)"><i class="iconfont icon-add"></i><span>添加应用</span></div>
          </div>
        </div>
        <i class="iconfont icon-fold icon-dropDown" @click="dropDownListShow()"></i>
      </div>

      <!-- 头部菜单 -->
      <ul class="menu-wrap">
        <li v-for="(item,index) in menuWrap" :class="{ checked: index == currentIndex }"  @click=" currentIndex=index "><span>{{item.name}}</span></li>
      </ul>

      <!-- 设置、消息、退出 操作-->
      <nav class="botbar-nav">
        <ul>
          <li v-for=" (item,index) in botBarNav" @click="acSetting(item.navChild,index)"  :id="index">
            <router-link to="" class="nav-link">
              {{item.navTxt}}
            </router-link>
          </li>
        </ul>
      </nav>

      <!-- 添加应用-->
      <div role="dialog" class="letter-modal modal fade" style="display: none;">
        <div class="modal-dialog" role="document" style="width: 580px;">
          <div class="modal-content">
            <div class="modal-header">
              <i class="iconfont close"></i>
              <a href="javascript:;" class="link active"> 消息列表 </a>
            </div>
            <div class="modal-body"><p class="no-letter">暂时没有消息</p>
              <ul class="letter-list"></ul>
            </div>
            <div class="modal-footer"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="cover-layer" v-show="!isShow" @click="dropDownListShow()"></div>
    <v-addapp-modal v-show="modalShow" @close-modal="showModal()"></v-addapp-modal>
  </div>
</template>

<script>
  import {getToolbar,getAcApplyList,switchApply} from '@/service/getData';
  import {botBarNav,topBarWrap} from '@/api/localJson/topToolView';
  import {getSessionStorage,removeStorage} from '@/api/utils';
  import addappModal from '@/components/modal/addapp_modal';
  import {getStorage,setStorage} from '@/api/utils';
  import * as SYSTEM from '@/api/system';
  import config from '@/config/config';
  // vuex状态管理
  import { mapActions } from 'vuex'
  // 引入jq
  import $ from "jquery";
  export default{
    data(){
      return {
        imageWebServer:config.ossImgPath,
        isShow: false,
        topBarWrap: {selected:{},navItem:[]},
        botBarNav: botBarNav,
        currentIndex: 0,
        active:false,
        modalShow:false
      }
    },
    props: ['menuWrap'],
    methods: {
      ...mapActions(['setSignOut']),
      dropDownListShow: function () {
          this.isShow = !this.isShow;
      },
      tabs (applyId){
          switchApply(applyId).then(res =>{
              const re = res.data.data;
              if(SYSTEM.SUCCSSS == re.success){
                  let loginInfo = JSON.parse(getStorage('loginInfo'));
                  loginInfo.acApplyId = applyId;
                  setStorage('loginInfo', JSON.stringify(loginInfo));
                  location.reload();
              }
          })
      },
      acSetting(navChild,index){
          this.currentIndex = index;
          if(index == 2){
              const ref = this.botBarNav[index].navLink;
              removeStorage('Token');
              this.setSignOut();
              this.$router.push({ name: ref});
          }else{
              /*$('#'+index).find('a').addClass('active');*/
              this.$emit('current',-1,'帐户',navChild);
          }
      },
      getApplyItem (){
          getAcApplyList().then(res =>{
              const re = res.data.data.data;
              re.acApplyList.forEach(v =>{
                  let icon = 'static/default_appicon.png';
                  if(v.applyIconAcc){
                      icon = this.imageWebServer + v.applyIconAcc.path + v.applyIconAcc.name;
                  }
                  const txt = v.applyName;
                  if(re.selectedApplyId == v.id){
                      this.topBarWrap.selected = {id:v.id,pic_url:icon,txt:txt}
                  }else{
                      this.topBarWrap.navItem.push({id:v.id,pic_url:icon,txt:txt})
                  }
              })
          });
      },
      showModal (v){
          this.modalShow = v;
          if(!v) {
            this.isShow = true;
          }
      }
    },
    created() {
        this.dropDownListShow();
        this.getApplyItem();
        var selected = getSessionStorage('SIDEBAR-CURRENT-ID') || 0;
        if(-1 == selected){
            this.active = true;
        }
    },
    components: {
      'v-addapp-modal': addappModal
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .top-tool-wrap {
    z-index: 100;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    .toptool-wrap {
      position: relative;
      width: 100%;
      min-width: 700px;
      height: 70px;
      background: #fff;
      box-shadow: 0 2px 6px 0 rgba(0, 0, 0, .08);
      .topbar-wrap {
        position: absolute;
        left: 0;
        top: 0;
        padding-right: 20px;
        height: 100%;
        .app-logo {
          position: relative;
          float: left;
          width: 40px;
          height: 40px;
          margin: 15px 12px 15px 20px;
          .btn-open:hover .logo {
            -webkit-transform: scale(1.2);
            transform: scale(1.2);
          }
          .btn-open {
            position: absolute;
            height: 40px;
            width: 40px;
            z-index: 99;
            left: 0;
            top: 0;
            .logo{
              display: block;
              width: 100%;
              height: 100%;
              border-radius: 10px;
              transition: .2s ease;
            }
          }
        }

        .sidebar-topbar-layout {
          float: left;
          position: relative;
          z-index: 2;
          .logo-space-wrap {
            position: relative;
            .logo-space {
              line-height: 70px;
              cursor: pointer;
              .appname {
                font-size: 14px;
                color: #333;
              }
            }
          }
          .dropdown-list {
            font-size: 12px;
            position: absolute;
            top: 70px;
            left: -72px;
            width: 120px;
            background-color: hsla(0,0%,100%,.97);
            border-radius: 0 0 4px 0;
            padding-bottom: 5px;
            box-shadow: 0 15px 30px rgba(0,0,0,.1);
            -webkit-transform: translate3d(0,-2px,0);
            transform: translate3d(0,-2px,0);
            .app-list {
              width: 120px;
              margin: 0 auto;
              li {
                padding: 11px 0 10px 16px;
                a {
                  color: #444;
                }
                img {
                  width: 25px;
                  height: 25px;
                  margin-right: 5px;
                  overflow: hidden;
                  border-radius: 50%;
                  vertical-align: middle;
                }
                img+span {
                  display: inline-block;
                  max-width: 62px;
                  vertical-align: middle;
                  opacity: .9;
                }
              }
            }
            .btn-add-app {
              cursor: pointer;
              border-top: 1px solid #ccc;
              width: 115px;
              margin: 0 auto;
              text-align: center;
              color: #444;
              height: 38px;
              line-height: 38px;
              opacity: .9;
            }
            .icon-add {
              margin-right: 5px;
              vertical-align: middle;
              font-size: 18px;
            }
            .icon-add+span {
              vertical-align: middle;
            }
          }
        }

      }
      .menu-wrap {
        position: absolute;
        left: 50%;
        -webkit-transform: translateX(-50%);
        transform: translateX(-50%);
        height: 100%;
        li {
          float: left;
          margin-left: 48px;
          position: relative;
          &:first-child {
            margin-left: 0
          }
          span {
            display: block;
            min-width: 80px;
            height: 100%;
            text-align: center;
            line-height: 70px;
            font-size: 14px;
            color: #333;
          }
          &.checked:after {
            content: "";
            display: block;
            width: 100%;
            height: 3px;
            background-color: #333;
            position: absolute;
            bottom: 0;
          }
          &.checked a {
            font-weight: 700;
          }
          &:not(.checked):hover a {
            font-weight: 700;
          }
        }
      }
      .botbar-nav {
        height: 100%;
        position: absolute;
        right: 0;
        bottom: 0;
        li {
          float: left;
          position: relative;
          border-left: 1px solid #f0f0f0;
          .nav-link  {
            display: block;
            height: 100%;
            padding: 0 25px;
            line-height: 70px;
            color: #666;
            opacity: 1;
            font-size: 14px;
            &.btn-letter {
              position: relative;
            }
          }
          .nav-link.active {
            background-color: #5c5c5c;
            color: #fff;
            opacity: 1;
          }
        }
      }
    }
  }
  .cover-layer{
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    display: block;
    outline: 0;
    opacity: 0;
  }
  .nav-link:hover {
    background: rgba(92,92,92,.2);
    opacity: 1;
  }
  .botbar-nav li:hover {
    background: transparent;
  }
  .botbar-nav:hover,.menu-wrap:hover,.topbar-wrap:hover,.cover-layer:hover{cursor: pointer;}
</style>
