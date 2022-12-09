<template>
  <div class="page-content interface">
    <v-tip :status="true" className="ft14 maxwid" v-show="isShowTip" @on-close="hideTip('isShowTip')">
      请务必保持下面接口可用，否则你的积分商城将不能正常工作。带 * 选项位必填项，其余则为选填。
      为了实时监控由于您的服务器异常产生的异常订单，请在 <a class="link-d" href="#!/interface/exception">异常监控</a>，设置异常提醒方式。
    </v-tip>
    <div class="form-group bottomline padr80">
      <label class="control-label">免登录接口：</label>
      <input type="text" class="form-control form-control-dirty" :class="{'invalid':fail.login.error}" placeholder="https://"
             v-model="interfaceItem.login.interfaceLink">
      <p class="invalid" v-if="fail.login.error"> <i class="iconfont">&#xe629;</i> {{fail.login.errorInfo}} </p>
      <p class="form-group-tip"> 该接口开放给客户端，通过生成免登录URL与积分商城同步信息。</p>
    </div>
    <div class="form-group bottomline padr80">
      <label class="control-label label-required">积分消费：</label>
      <input type="text" class="form-control form-control-dirty" :class="{'invalid':fail.charge.error}" placeholder="http://"
             v-model="interfaceItem.charge.interfaceLink">
      <p class="invalid" v-if="fail.charge.error"> <i class="iconfont">&#xe629;</i> {{fail.charge.errorInfo}} </p>
      <p class="form-group-tip"> 该接口用来向您的服务器提交积分消费请求，查看</p>
    </div>
    <div class="form-group bottomline padr80">
      <label class="control-label label-required">通知结果：</label>
      <input type="text" class="form-control form-control-dirty" :class="{'invalid':fail.hook.error}" placeholder="http://"
             v-model="interfaceItem.hook.interfaceLink">
      <p class="invalid" v-if="fail.hook.error"> <i class="iconfont">&#xe629;</i> {{fail.hook.errorInfo}} </p>
      <p class="form-group-tip"> 该接口用来向您的服务器反馈兑换结果请求，查看</p>
    </div>
    <div class="form-group bottomline padr80">
      <label class="control-label">虚拟商品充值：</label>
      <input type="text" class="form-control form-control-dirty" :class="{'invalid':fail.xn_charge.error}" placeholder="http://"
             v-model="interfaceItem.xn_charge.interfaceLink">
      <p class="invalid" v-if="fail.xn_charge.error"> <i class="iconfont">&#xe629;</i> {{fail.xn_charge.errorInfo}} </p>
      <p class="form-group-tip"> 该接口用来向您的服务器发起虚拟商品充值请求，查看</p>
    </div>
    <div class="form-group bottomline padr80">
      <label class="control-label">加积分接口：</label>
      <input type="text" class="form-control form-control-dirty" :class="{'invalid':fail.add.error}" placeholder="http://"
             v-model="interfaceItem.add.interfaceLink">
      <p class="invalid" v-if="fail.add.error"> <i class="iconfont">&#xe629;</i> {{fail.add.errorInfo}} </p>
      <p class="form-group-tip"> 该接口用来向您的服务器提交加积分请求，查看</p>
    </div>
    <div class="form-group">
      <a href="javascript:;" class="btn btn-green btn-lg submit-btn" @click="upSetting()" :disabled="!isSuBtn">保存配置</a>
    </div>
    <transition name="loading">
      <v-loading v-show="loading"></v-loading>
    </transition>
  </div>
</template>

<script>
  import {getInterfaceSetting,updateInterfaceSetting} from '@/service/getData';
  import * as SYSTEM from '@/api/system';
  export default{
    data(){
      return {
        isShowTip: true,
        interfaceItem :{
            login : {id:'',interfaceName:'login',interfaceLink:''},
            charge : {id:'',interfaceName:'charge',interfaceLink:''},
            hook : {id:'',interfaceName:'hook',interfaceLink:''},
            xn_charge : {id:'',interfaceName:'xn_charge',interfaceLink:''},
            add : {id:'',interfaceName:'add',interfaceLink:''}
        },
        fail:{
            login:{error:false,success:true,errorInfo:''},
            charge:{error:false,success:false,errorInfo:''},
            hook:{error:false,success:false,errorInfo:''},
            xn_charge:{error:false,success:true,errorInfo:''},
            add:{error:false,success:true,errorInfo:''}
        },
        isSuBtn:false,
        loading:false,
      }
    },
    methods: {
      hideTip(param) {
          this[param] = false
      },
      // 获取应用接口设置数据
      interfaceSettingData () {
        this.loading = true;
          getInterfaceSetting().then(res =>{
              const re = res.data.data.data;
              if(re.length > 0){
                  re.forEach(v =>{
                      this.interfaceItem[v.interfaceName] = v;
                  })
              }
              this.loading = false;
          });
      },
      // 验证输入数据
      extend (val,failObj,validator){
          const reg = /^[a-zA-z]+:\/[^\s]*$/;
          val = val || '';

          if(validator){
              if(val.toString().replace(/\s+/g,"").length < 1){
                  [failObj.error,failObj.success,failObj.errorInfo] = [true,false,'不能为空'];
                  this.vali();
                  return failObj;
              }
          }

          if(!reg.test(val) && val.toString().replace(/\s+/g,"").length > 0){
              [failObj.error,failObj.success,failObj.errorInfo] = [true,false,'请输入正确的地址'];
              this.vali();
              return failObj;
          }
          [failObj.error,failObj.success,failObj.errorInfo] = [false,true,''];
          this.vali();
          return failObj;
      },
      // 验证提交
      vali () {
          if(this.fail.login.success && this.fail.charge.success && this.fail.hook.success &&
            this.fail.xn_charge.success && this.fail.add.success ){
              this.isSuBtn  = true;
          }else{
              this.isSuBtn  = false;
          }
      },
      // 提交修改
      upSetting (){
          if(!this.isSuBtn){
              return;
          }
          const params = {params:JSON.stringify(this.interfaceItem)};
          updateInterfaceSetting(params).then(res =>{
              console.log(res);
              if(SYSTEM.SUCCSSS == res.data.data.success){
                  this.$Message.error("修改成功");
                  location.reload();
              }
          })
      }
    },
    mounted () {
        this.interfaceSettingData();
    },
    watch: {
        'interfaceItem.login.interfaceLink' (val, oldVal) {
            this.extend(val,this.fail.login,false)
        },
        'interfaceItem.charge.interfaceLink' (val, oldVal) {
            this.extend(val,this.fail.charge,true)
        },
        'interfaceItem.hook.interfaceLink' (val, oldVal) {
            this.extend(val,this.fail.hook,true)
        },
        'interfaceItem.xn_charge.interfaceLink' (val, oldVal) {
            this.extend(val,this.fail.xn_charge,false)
        },
        'interfaceItem.add.interfaceLink' (val, oldVal) {
            this.extend(val,this.fail.add,false)
        }
    },
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../../style/configure";
</style>
