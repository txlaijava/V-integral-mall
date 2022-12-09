<template>
  <div class="main">
    <section class="acount-edit-wrap">
      <div class="page-title">
        <h2 class="has-sub-title">子账号管理
          <i class="iconfont">&#xe68d;</i>
          <span class="sub-title">编辑子账号</span>
        </h2>
      </div>
      <div class="content-wrap">
        <div class="required-msg bottom-line">
          <div class="form-group">
            <div>
              <span class="main-acount login-name">{{subAcData.acMail}}</span>
            </div>
            <label class="control-label label-required">登录名：</label>
          </div>
        </div>
        <div class="remark-wrap bottom-line">
          <div class="form-group">
            <input type="text" class="form-control w300 valid untouched pristine"
                   placeholder="选填最多8个字" v-model="subAcData.acCompany">
            <label class="control-label">运营者姓名：</label></div>
          <div class="form-group">
            <input type="text" class="form-control w300 valid untouched pristine"
                   placeholder="选填最多15个字" v-model="subAcData.acRemark">
            <label class="control-label">备注：</label></div>
        </div>
      </div>
      <div class="create-acount">
        <a class="btn btn-green btn-lg save-powers" @click="editorSub()">
          <i class="iconhandle"></i>保存配置</a>
      </div>
    </section>
  </div>
</template>

<script>
  import {getStorage} from '@/api/utils';
  import * as SYSTEM from '@/api/system';
  import { dataView } from "@/api/localJson/ac_create_subac";
  import {putAccountInfo,getSubAccount} from '@/service/getData';
  export default {
    data (){
      return {
          subAcData:{}
      }
    },
    methods: {
        editorSub: function(){
            let params = {acId:this.subAcData.id,acRemark:this.subAcData.acRemark,acCompany:this.subAcData.acCompany};
            putAccountInfo(params).then(res =>{
                let re = res.data.data;
                if(SYSTEM.SUCCSSS == re.success && SYSTEM.CODE_IS_OK == re.code){
                    this.$router.push({name: 'acSubacount'});
                }
            })
        },
        subAccount: function(subId){
            getSubAccount({subAcId:subId}).then(res =>{
                let re = res.data.data;
                if(SYSTEM.SUCCSSS == re.success && SYSTEM.CODE_IS_OK == re.code){
                    this.subAcData = re.data;
                }
            })
        }
    },
    created(){
        let subId = this.$route.params.subId;
        this.subAccount(subId);
    },
    watch: {
    }
  }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style lang="scss" scoped>
  a {
    text-decoration: none;
  }
  .page-title h2.has-sub-title {
    font-weight: 400;
  }
  .page-title .sub-title {
    font-weight: 700;
  }
  .content-wrap {
    margin-top: 30px;
    .bottom-line {
      margin-bottom: 20px;
      border-bottom: 1px dotted #ccc;
    }
    .w300 {
      width: 300px;
      display: inline-block;
    }
    .w500 {
      width: 500px;
      display: inline-block;
    }
    .authority-wrap {
      position: relative;
      padding: 20px;
      .item-authority:first-child {
        padding-left: 0;
      }
      .item-authority {
        text-align: center;
      }
      .clo-title .iconfont {
        color: #bbb;
        font-size: 12px;
      }
    }
    .label-title {
      width: 200px;
      font-size: 14px;
      text-align: left;
    }
  }

  .checkbox-control .icon.iconfont {
    background-color: #29b6b0;
    border-color: #29b6b0;
    color: #fff;
    span {
      display: block;
      transform: scale(.6);
      margin-top: 1px;
    }
  }
  .checkbox-control .icon:hover {
    border-color: #828282;
  }
  .tool-tip-wrap .tooltip-arrow {
    border-top-color: #fff!important;
  }
  .tool-tip-wrap .tooltip-inner {
    border: 1px solid #ccc;
    background: #fff;
    border-radius: 6px!important;
    color: #444;
    max-width: 200px;
    padding: 3px 8px;
    text-align: center;
    border-radius: 20px;
  }

</style>
