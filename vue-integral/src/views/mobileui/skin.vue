<template>
  <div class="main">
    <div class="page-title"><h2> 界面皮肤配置 </h2></div>
    <div class="themeColor">
      <div class="colorpicker-wrapper">
        <div class="colorpicker-trigger" @click="showColorBox()">
          <span class="color-preview"
                v-bind:style="{'background-color':
                'rgba('+ chooseColors.rgba.r +','+ chooseColors.rgba.g +','+ chooseColors.rgba.b +','+ chooseColors.rgba.a +')'}"></span> <i class="iconfont">&#xe744;</i>
        </div>
        <div class="color-box-wrapper" v-show="colorIsShow">
          <Sketch v-model="colors" :class="'vue-color__sketch'"></Sketch>
          <div class="color-options">
            <a href="javascript:;" class="btn btn-default" @click="showColorBox()">取消</a>
            <a href="javascript:;" class="btn btn-green" @click="chooseColor()">确定</a>
          </div>
        </div>
      </div>
      <div class="theme-color-desc">
        <p class="title"> 页面主题色：
          <label style="color: rgb(17, 195, 188);">{{chooseColors.hex}}</label>
        </p>
        <p class="ft-desc">主题色为积分商城界面的按钮、链接、积分值的颜色。</p>
      </div>
    </div>
    <div class="theme-list-wrap cf">
      <div class="theme-list" v-for="(themeList,index) in themeLists">
        <div class="skin-box" v-if="themeList.id == acApplyThemeId">
          <div class="active">
            <div class="check-box"><i class="check"></i>已应用</div>
          </div>
          <div class="img-box">
            <img :src="imageWebServer + themeList.path + themeList.name">
          </div>
        </div>
        <div class="skin-box" v-else>
          <div class="active active-hover" style="display: none;">
            <div class="btn-group">
              <a class="left-btn" target="_blank">
                <i class="magnifying iconfont">&#xe772;</i>  预览皮肤
              </a>
              <div class="right-btn" @click="applyTheme(themeList.id)"><i class="magnifying iconfont">&#xe73d;</i> 应用皮肤 </div>
            </div>
          </div>
          <div class="img-box"><img :src="imageWebServer + themeList.path + themeList.name"></div>
        </div>
        <div class="skin-des"><span>{{themeList.theme_name}}</span></div>
      </div>
    </div>
    <transition name="loading">
        <v-loading v-show="loading"></v-loading>
    </transition>
  </div>
</template>

<script>
  import {getMobileuiSkin,updateAcApply,getAcApply} from '@/service/getData';
  import {getStorage,setStorage} from '@/api/utils';
  import * as SYSTEM from '@/api/system';
  import config from '@/config/config';
  import { Sketch } from 'vue-color';

  export default{
    data(){
      return {
        imageWebServer:config.ossImgPath,
        acApplyId: '',
        acApplyThemeId: '',
        isShow: false,
        colorIsShow:false,
        themeLists: null,
        colors: {
          hex: '#D0021B',
          hsl: {
            h: 352.71844660194176,
            s: 0.980952380952381,
            l: 0.4117647058823529,
            a: 1
          },
          hsv: {
            h: 352.71844660194176,
            s: 0.9903846153846154,
            v: 0.8156862745098039,
            a: 1
          },
          rgba: {
            r: 208,
            g: 2,
            b: 27,
            a: 1
          },
          a: 1
        },
        chooseColors:{hex:'',rgba: {}},
        loading:true
      }
    },
    components: {
      Sketch
    },
    methods: {
      getMobileuiSkin: function () {
          getMobileuiSkin().then(res =>{
              let re = res.data.data;
              if(SYSTEM.SUCCSSS == re.success && SYSTEM.CODE_IS_OK == re.code){
                  this.themeLists = re.data;
                  this.loading = false;
              }
          });
      },
      showColorBox(){
          this.colorIsShow = !this.colorIsShow;
      },
      chooseColor: function(){
          [this.chooseColors.hex,this.chooseColors.rgba] = [this.colors.hex,this.colors.rgba];
          let rgba = this.colors.rgba['r'] + ',' + this.colors.rgba['g'] + ',' + this.colors.rgba['b'] + ',' + this.colors.rgba['a'];
          updateAcApply(this.acApplyId,{themeColorRgba:rgba,themeColor:this.colors.hex,editorType:1}).then(res =>{
              if(SYSTEM.SUCCSSS == res.data.data.success){
                  this.$Message.error("修改成功");
                  this.showColorBox();
              }
          });
      },
      applyTheme (themeId){
          updateAcApply(this.acApplyId,{themeId:themeId,editorType:2}).then(res =>{
              if(SYSTEM.SUCCSSS == res.data.data.success){
                  this.$Message.error("修改成功");
                  const re = res.data.data.data;
                  let loginInfo = JSON.parse(getStorage('loginInfo'));
                  loginInfo.acApplyThemeId = re.themeId;
                  this.acApplyThemeId = re.themeId;
                  setStorage('loginInfo', JSON.stringify(loginInfo));
              }
          })
      },
      getApplyData (){
          getAcApply().then(res =>{
              if(SYSTEM.SUCCSSS == res.data.data.success){
                  const re = res.data.data.data;
                  this.acApplyId = re.id;
                  this.acApplyThemeId = re.themeId;
                  const rgba = re.themeColorRgba.split(',');
                  const rgb = {r: rgba[0],g: rgba[1],b: rgba[2],a: rgba[3]};
                  [this.chooseColors.hex,this.chooseColors.rgba] = [re.themeColor,rgb];
              }
          })
      }
    },
    created() {
      this.getMobileuiSkin();
      this.getApplyData();
    },
    watch:{
    }
  }

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .main {
    .themeColor {
      margin-top: 20px;
      .colorpicker-wrapper {
        position: relative;
        display: inline-block;
        margin-right: 10px;
        .color-box-wrapper {
          position: absolute;
          top: 50px;
          left: 0;
          width: 240px;
          padding: 10px;
          background-color: #fff;
          border: 1px solid #ccc;
          border-radius: 4px;
          border-top-left-radius: 0;
          z-index: 5;
          .color-options {
            margin-top: 10px;
            text-align: right;
          }

          .vue-color__sketch {
            padding: 0;
            border-radius: 0;
            box-shadow: none;
            width: 240px;
          }
        }
        .colorpicker-trigger {
          border-radius: 2px;
          border: 1px solid #ccc;
          padding: 5px;
          cursor: pointer;
          background-color: #fff;
          -webkit-user-select: none;
          -moz-user-select: none;
          -ms-user-select: none;
          user-select: none;
          position: relative;
          z-index: 2;
          .color-preview {
            display: inline-block;
            width: 40px;
            height: 40px;
            border-radius: 1px;
            vertical-align: middle;
          }
          .iconfont {
            display: inline-block;
            height: 40px;
            color: #888;
            font-size: 12px;
            line-height: 40px;
            padding: 0 5px;
            vertical-align: middle;
          }
        }
      }
      .theme-color-desc {
        display: inline-block;
        vertical-align: middle;
        p {
          line-height: 24px;
          font-size: 14px;
          &.title {
            color: #444;
          }
          &.ft-desc {
            color: #888;
          }
        }
      }
    }
    .theme-list-wrap {
      margin-bottom: 20px;
      .theme-list {
        width: 280px;
        margin-right: 20px;
        float: left;
        margin-top: 20px;
        .skin-box {
          position: relative;
          height: 420px;
          background: #fff;
          border: 1px solid #eee;
          overflow: hidden;
          .active {
            position: absolute;
            width: 100%;
            height: 100%;
            overflow: hidden;
            z-index: 1;
            .check-box {
              position: absolute;
              top: -60px;
              left: 50%;
              -webkit-transform: translateX(-50%);
              transform: translateX(-50%);
              width: 100%;
              height: 60px;
              background: rgba(102, 204, 204, .95);
              text-align: center;
              line-height: 60px;
              color: #fff;
              -webkit-animation: checkbox 1s ease forwards;
              animation: checkbox 1s ease forwards;
              .check {
                font-family: handle;
                font-size: 15px;
                font-style: normal;
                color: #fff;
                background: none;
                box-shadow: none;
              }
            }
            .btn-group {
              position: absolute;
              left: 50%;
              width: 100%;
              height: 34px;
              margin-top: -51px;
              padding: 14px 0;
              background: hsla(0, 0%, 100%, .95);
              -webkit-transform: translate3d(-50%, 0, 0);
              transform: translate3d(-50%, 0, 0);
              border: 1px solid #eee;
              z-index: 1;
              .left-btn {
                cursor: pointer;
                display: inline-block;
                font-size: 12px;
                color: #444;
                background: #fff;
                width: 96px;
                height: 34px;
                text-align: center;
                line-height: 34px;
                border-radius: 4px;
                border: 1px solid #bbb;
                margin-left: 27px;
                .magnifying {
                  font-family: handle;
                  font-size: 12px;
                  margin-right: 5px;
                  font-style: normal;
                  color: #444;
                }
              }
              .left-btn:hover {
                color: #29b6b0;
                border: 1px solid #29b6b0;
                .magnifying{
                  color: #29b6b0;
                }
              }
              .right-btn {
                cursor: pointer;
                display: inline-block;
                font-size: 12px;
                color: #444;
                background: #fff;
                width: 96px;
                height: 34px;
                text-align: center;
                line-height: 34px;
                border-radius: 4px;
                border: 1px solid #bbb;
                margin-left: 14px;
                .magnifying {
                  font-family: handle;
                  font-size: 12px;
                  margin-right: 5px;
                  color: #444;
                  font-style: normal;
                }
              }
              .right-btn:hover {
                color: #29b6b0;
                border: 1px solid #29b6b0;
                .magnifying{
                  color: #29b6b0;
                }
              }
            }
          }
          .img-box {
            position: relative;
            height: 360px;
            width: 240px;
            padding: 20px;
            overflow: hidden;
            background-color: #fff;
            box-sizing: initial;
            img {
              position: absolute;
              width: 240px;
              height: auto;
            }
          }
          &:hover{
            .active-hover{
              display: block !important;
            }
            .btn-group {
              -webkit-animation: btngroup 1s linear forwards;
              animation: btngroup 1s linear forwards
            }
            .img-box{
              img{
                -webkit-animation-delay: 1s;
                animation-delay: 1s;
                -webkit-animation: imgmove 2s linear forwards;
                animation: imgmove 2s linear forwards;
              }
            }
          }
        }
        .skin-des {
          margin-top: 10px;
          text-align: center;
          color: #888;
          font-size: 12px;
        }
      }
      &.cf{
        &:after, &:before {
          content: " ";
          display: table;
          clear: both;
        }
      }
    }

  }

  @-webkit-keyframes btngroup {
    0% {
      -webkit-transform:translate3d(-50%,0,0);
      transform:translate3d(-50%,0,0)
    }
    to {
      -webkit-transform:translate3d(-50%,51px,0);
      transform:translate3d(-50%,51px,0)
    }
  }@keyframes btngroup {
     0% {
       -webkit-transform:translate3d(-50%,0,0);
       transform:translate3d(-50%,0,0)
     }
     to {
       -webkit-transform:translate3d(-50%,51px,0);
       transform:translate3d(-50%,51px,0)
     }
   }

  @-webkit-keyframes imgmove {
    0% {
      top:0;
      -webkit-transform:translateZ(0);
      transform:translateZ(0)
    }
    to {
      -webkit-transform:translate3d(0,-100%,0);
      transform:translate3d(0,-100%,0);
      top:100%
    }
  }@keyframes imgmove {
     0% {
       top:0;
       -webkit-transform:translateZ(0);
       transform:translateZ(0)
     }
     to {
       -webkit-transform:translate3d(0,-100%,0);
       transform:translate3d(0,-100%,0);
       top:100%
     }
   }

  @-webkit-keyframes checkbox {
    0% {
      -webkit-transform:translate3d(-50%,0,0);
      transform:translate3d(-50%,0,0)
    }
    to {
      -webkit-transform:translate3d(-50%,60px,0);
      transform:translate3d(-50%,60px,0)
    }
  }@keyframes checkbox {
     0% {
       -webkit-transform:translate3d(-50%,0,0);
       transform:translate3d(-50%,0,0)
     }
     to {
       -webkit-transform:translate3d(-50%,60px,0);
       transform:translate3d(-50%,60px,0)
     }
   }
</style>
