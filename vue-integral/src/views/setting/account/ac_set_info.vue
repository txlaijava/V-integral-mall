<template>
  <div class="main">
    <div class="page-title">
      <h2>账户信息</h2></div>
    <div class="page-content">
      <div class="form-group bottomline">
        <label class="control-label">账户余额：</label>
        <p class="form-text">
          <span class="cost">{{userData.acAmount}}</span>元
          <a class="btn btn-default ml20" href="#!/account/recharge">在线充值</a>
          <a class="btn btn-default ml20" href="#!/account/cashdetail">查看明细</a></p>
      </div>
      <div class="form-group bottomline">
        <label class="control-label">我的收入：</label>
        <p class="form-text">
          <span class="cost">{{userData.acIncome}}</span>元
          <a class="btn btn-default ml20" href="#!/account/withdraw">申请提现</a>
          <a class="btn btn-default ml20" href="#!/account/income/detail">查看明细</a></p>
      </div>
      <!--<div class="form-group bottomline">
        <label class="control-label">开启余额提醒：</label>
        <div class="switch inline off">
          <label class="switch-label">
            <div class="switch-inner"></div>
            <div class="switch-switch"></div>
          </label>
        </div>
        <p class="switch-tip">
          <span>短信提醒余额不足</span></p>
        <div class="input-with-addon remindmoney-wrap"> <input type="text" class="form-control invalid untouched pristine" placeholder="需要提醒的金额" value=""> <div class="input-group-addon">元</div> </div>
      </div>-->
      <div class="form-group bottomline">
        <label class="control-label">我的姓名：</label>
        <input type="text" class="form-control untouched valid dirty modified" placeholder="请输入你的姓名" v-model="userData.acName" ></div>
      <div class="form-group bottomline">
        <label class="control-label">我的公司：</label>
        <input type="text" class="form-control untouched valid dirty modified" placeholder="请输入您公司的名称" v-model="userData.acCompany"></div>
      <div class="form-group bottomline">
        <label class="control-label">联系方式：</label>
        <p class="contact">
          <span>{{userData.acTelephone}}</span></p>
      </div>
      <div class="form-group">
        <a href="javascript:void(0)" @click="subSave()" class="btn btn-green btn-lg submit-btn">保存配置</a>
      </div>
    </div>
  </div>
</template>

<script>
    import {getStorage} from '@/api/utils';
    import * as SYSTEM from '@/api/system';
    import {putAccountInfo} from '@/service/getData';
    // vuex状态管理
    import { mapActions } from 'vuex';
    export default {
        data (){
            return {
                userData: JSON.parse(getStorage('loginInfo')) || ''
            }
        },
        methods: {
            ...mapActions(['upUserInfo']),
            subSave(){
                let params = {acId:this.userData.id,acName:this.userData.acName,acCompany:this.userData.acCompany};
                putAccountInfo(params).then(res =>{
                      if(SYSTEM.SUCCSSS == res.success && SYSTEM.CODE_IS_OK == res.code){
                          this.upUserInfo(res.data);
                          location.reload();
                      }
                  })
            }
        },
        created() {
            /*this.userData = JSON.parse(getSessionStorage('loginInfo');*/
        }
    }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style lang="scss" scoped>
  .control-label {
    width: 100px;
    font-weight: 400;
  }
  .cost {
    font-size: 18px;
    color: #ff9d60;
  }
  .remindmoney-wrap {
    margin-top: 10px;
  }
  .contact {
    padding-top: 10px;
    font-size: 14px;
    color: #888;
    span {
      color: #444;
      padding-right: 20px;
    }
  }
</style>
