<template>
  <div class="page-login-v3 layout-full">
    <div class="page animsition vertical-align text-center" data-animsition-in="fade-in" data-animsition-out="fade-out"
         style="animation-duration: 800ms; opacity: 1;">
        <div class="page-content vertical-align-middle">
            <div class="panel">
                <div class="panel-body">
                    <div class="brand" :class="[{'has-error':error.isError}]">
                        <img class="brand-img" src="../../../static/logo/logo512_theme.png" alt="MetInfo 用户中心" width="55">
                        <h2 class="brand-text font-size-20 margin-top-20">{{dataView.forgotPwdTileTxt}}</h2>
                        <small class="help-block" v-if="error.isError" v-text="error.msg"></small>
                    </div>
                    <form method="post" class="met-form-validation fv-form fv-form-bootstrap" novalidate="novalidate" autocomplete="off" v-if="!isUpPwdShow">
                        <div class="form-suite form-material floating" :class="[{'has-success':telephone.isSuccess||telephone.telephone!=''},{'has-error':telephone.isError}]">
                            <input type="text" class="form-control empty"  v-model="telephone.telephone" @blur="inputVerify('telephone')">
                            <label class="floating-label">{{dataView.inputData.telephone.placeholder}}</label>
                            <small class="help-block" v-if="telephone.isError" v-text="telephone.error"></small>
                        </div>
                        <div class="form-suite form-material floating" :class="[{'has-success':code!=''}]">
                            <div class="input-suite">
                                <div class="form-control-wrap margin-left-0 margin-right-10">
                                    <input type="text" class="form-control empty" name="code" v-model="code" @blur="inputVerify('code')">
                                    <label class="floating-label">{{dataView.inputData.code.placeholder}}</label>
                                </div>
                                <span class="input-suite-btn">
                                    <button type="button" class="btn btn-default btn-block " style="font-size: 12px;" :disabled="!telephone.isSuccess"  v-if="!waitSeconds" @click="valiAc()">{{dataView.inputData.code.sendCodeTxt}}</button>
                                    <button type="button" class="btn btn-default btn-block " style="font-size: 12px;" disabled v-if="waitSeconds">{{waitSeconds}}{{dataView.inputData.code.waitSecondsTxt}}</button>
                                </span>
                            </div>
                            <small class="help-block" style="display: none;"></small>
                        </div>
                        <button type="button" class="btn btn-primary btn-block btn-lg margin-top-40" :disabled='subDisabled' @click="reset(false)">{{dataView.btnTxt}}</button>
                    </form>

                  <form method="post" class="met-form-validation fv-form fv-form-bootstrap" novalidate="novalidate" autocomplete="off" v-if="isUpPwdShow">
                    <div class="form-suite form-material floating" :class="[{'has-success':pass.isSuccess||pass.newPass!=''},{'has-error':pass.isError}]">
                      <input type="password" class="form-control empty"  v-model="pass.newPass" @blur="inputVerify('newPass')">
                      <label class="floating-label">{{dataView.inputData.pwd.placeholder}}</label>
                      <small class="help-block" v-if="pass.isError" v-text="pass.error"></small>
                    </div>
                    <div class="form-suite form-material floating" :class="[{'has-success':twoPass.isSuccess||twoPass.newPass!=''},{'has-error':twoPass.isError}]">
                      <input type="password" class="form-control empty"  v-model="twoPass.newPass" @blur="inputVerify('twoPwd')">
                      <label class="floating-label">{{dataView.inputData.twoPwd.placeholder}}</label>
                      <small class="help-block" v-if="twoPass.isError" v-text="twoPass.error" style="color: #f96868;"></small>
                    </div>
                    <button type="button" class="btn btn-primary btn-block btn-lg margin-top-40" :disabled='subDisabled' @click="reset(true)">确 认 重 置 密 码</button>
                  </form>

                    <ol class="breadcrumb margin-bottom-0 padding-0">
                        <li><router-link to="/admin/login">返回登录</router-link></li>
                    </ol>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
  import {getSMSCode,getValiAc,getValidCode,setAcPwd} from '@/service/getData';
  import * as SYSTEM from '@/api/system';
  import { dataView } from "@/api/localJson/forgotPwdView"

  // 最大等待秒数
  const MAX_WAIT_SECONDS = 60;
  export default {
    data (){
      return {
        dataView: dataView,
        telephone: {telephone: '',error: '',isError: false,isSuccess: false},
        pass: {newPass: '',error: '',isError: false,isSuccess: false},
        twoPass: {newPass: '',error: '',isError: false,isSuccess: false},
        code:'',
        error: {isError: false,msg: ''},
        subDisabled:true,
        // 我的定时器
        myInterval: null,
        // 验证码等待时间
        waitSeconds: 0,
        isUpPwdShow: false
      }
    },
    directives:{
      focus:{
        inserted(el){
          el.focus()
        }
      }
    },
    methods: {
      inputVerify(name){
          console.log("11232");
        if('newPass' == name){
          var validator = {
            reg: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/ ,
            rules: 'required',min: 6,max: 12, value: this.pass.newPass,
            getMessage: {
              required:{msg: this.dataView.inputData.pwd.Verify.NULL_ERROR},
              reg: {msg: this.dataView.inputData.pwd.Verify.FORMAT_ERROR},
              min: {msg: this.dataView.inputData.pwd.Verify.MIN_ERROR},
              max: {msg: this.dataView.inputData.pwd.Verify.MAX_ERROR}
            }
          }
          this.extend(this.pass,validator);
        }

        if('twoPwd' == name){
          var validator = {
            reg: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/ ,
            rules: 'required|contrast',min: 6,max: 12, value: this.twoPass.newPass,
            getMessage: {
              required:{msg: this.dataView.inputData.twoPwd.Verify.NULL_ERROR},
              reg: {msg: this.dataView.inputData.twoPwd.Verify.FORMAT_ERROR},
              min: {msg: this.dataView.inputData.twoPwd.Verify.MIN_ERROR},
              max: {msg: this.dataView.inputData.twoPwd.Verify.MAX_ERROR},
              contrast: {msg: this.dataView.inputData.twoPwd.Verify.NOT_EQ_ERROR,object:this.pass}
            }
          };
          if(this.twoPass.isSuccess){
            this.subDisabled = false;
          }
          this.extend(this.twoPass,validator);
        }
        if('telephone' == name){
          var validator = {
            reg: /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/ ,
            rules: 'required|eq', value: this.telephone.telephone,
            getMessage: {required:{msg: this.dataView.inputData.telephone.Verify.NULL_ERROR},reg: {msg: this.dataView.inputData.telephone.Verify.FORMAT_ERROR}}
          }
          this.extend(this.telephone,validator);
        }

        if(('newPass' == name || 'twoPwd' == name)){
          if(this.pass.isSuccess && this.twoPass.isSuccess){
            this.subDisabled = false;
          }else{
            this.subDisabled = true;
          }
        }else{
          if('' != this.code && this.telephone.isSuccess){
            this.subDisabled = false;
          }else{
            this.subDisabled = true;
          }
        }
      },
      extend(obj,validator) {
        var reg = validator.reg;
        if(validator.rules.indexOf('required') >= 0 && (typeof(validator.value) == "undefined" || validator.value.replace(/\s+/g,"").length <= 0)){
          obj.error = validator.getMessage.required.msg
          obj.isError = true;
          obj.isSuccess = false;
          return obj;
        }else{
          obj.error = '';
          obj.isError = false;
          obj.isSuccess = true
        }
        if(validator.min != void 0 && validator.value.replace(/\s+/g,"").length < validator.min){
          obj.error = validator.getMessage.min.msg
          obj.isError = true;
          obj.isSuccess = false;
          return obj;
        }else{
          obj.error = '';
          obj.isError = false;
          obj.isSuccess = true
        }

        if(validator.max != void 0 && validator.value.replace(/\s+/g,"").length > validator.max){
          obj.error = validator.getMessage.max.msg
          obj.isError = true;
          obj.isSuccess = false;
          return obj;
        }else{
          obj.error = '';
          obj.isError = false;
          obj.isSuccess = true
        }

        if((validator.reg != void 0) && '' != validator.reg && !reg.test(validator.value)){
          obj.error = validator.getMessage.reg.msg
          obj.isError = true;
          obj.isSuccess = false;
          return obj;
        }else{
          obj.error = '';
          obj.isError = false;
          obj.isSuccess = true
        }

        if(validator.rules.indexOf('contrast') >= 0 && validator.value != validator.getMessage.contrast.object.newPass){
          obj.error = validator.getMessage.contrast.msg;
          obj.isError = true;
          obj.isSuccess = false;

          var obj2 = validator.getMessage.contrast.object;
          obj2.error = validator.getMessage.contrast.msg;
          obj2.isError = true;
          obj2.isSuccess = false;
          return obj;
        }

        return obj;
      },
      reset(resetPwd){
        if(!this.subDisabled){
          if(resetPwd){
            var params = {valiType: 'acTelephone',mobileNo: this.telephone.telephone,pwd: this.twoPass.newPass};
            setAcPwd(params).then(re => {
              if (SYSTEM.CODE_IS_ERROR == re.data.code) {
                this.error.isError = true;
                this.error.msg = this.dataView.errorData.SERVICE_ERROR;
                this.subDisabled = true;
                return;
              }
              var res = re.data.data;
              if (SYSTEM.SUCCSSS == res.success && SYSTEM.CODE_IS_OK == res.code) {
                this.error.isError = false;
                this.error.msg = '';
                this.$router.push('login');
              } else {
                this.error.isError = true;
                this.error.msg = this.dataView.errorData.UP_PWD_ERROR;
              }
            });
          }else {
            var params = {mobileNo: this.telephone.telephone,codeNo: this.code};
            getValidCode(params).then(re => {
              if (SYSTEM.CODE_IS_ERROR == re.data.code) {
                this.error.isError = true;
                this.error.msg = this.dataView.errorData.SERVICE_ERROR;
                this.subDisabled = true;
                return;
              }
              var res = re.data.data;
              if (SYSTEM.SUCCSSS == res.success && SYSTEM.CODE_IS_OK == res.code) {
                this.error.isError = false;
                this.error.msg = '';
                this.isUpPwdShow = true;
                this.subDisabled = true;
              } else {
                this.error.isError = true;
                this.error.msg = this.dataView.errorData.CODE_ERROR;
              }
            });
          }
        }
      },
      valiAc(){
        // 验证账号是否存在用户
        var params = {valiType: 'acTelephone',mobileNo: this.telephone.telephone};
        getValiAc(params).then(re =>{
          if(SYSTEM.CODE_IS_ERROR == re.data.code){
            this.error.isError = true;
            this.error.msg = this.dataView.errorData.SERVICE_ERROR;
            this.telephone.isSuccess = false;
            this.subDisabled = true;
            return;
          }
          var res = re.data.data;
          if(SYSTEM.SUCCSSS == res.success && SYSTEM.CODE_IS_OK == res.code){
            this.error.isError = false;
            this.error.msg = '';
            this.telephone.isSuccess = true;
            this.subDisabled = false;
            this.sendCode()
          }else if(SYSTEM.CODE_NOAC_ERROR == res.code){
            this.error.isError = true;
            this.error.msg = this.dataView.errorData.SERVICE_NOAC_ERROR;
            this.telephone.isSuccess = false;
            this.subDisabled = true;
            return;
          }else{
            this.error.isError = true;
            this.error.msg = this.dataView.errorData.SERVICE_CODE_ERROR;
            this.telephone.isSuccess = false;
            this.subDisabled = true;
            return;
          }
        });
      },
      sendCode(){
        if(this.telephone.isSuccess){
          // 获取验证码请求
          let params = {mobileNo: this.telephone.telephone};
          getSMSCode(params).then(re =>{
            this.countDown(false);
            var res = re.data.data;
            if(SYSTEM.CODE_IS_ERROR == re.data.code){
              this.error.isError = true;
              this.error.msg = this.dataView.errorData.SERVICE_ERROR;
              this.countDown(true);
              return;
            }
            if(SYSTEM.SUCCSSS == res.success && SYSTEM.CODE_IS_OK == res.code){
              this.error.isError = false;
              this.error.msg = '';
            }else{
              this.error.isError = true;
              this.error.msg = this.dataView.errorData.SERVICE_CODE_ERROR;
              this.countDown(true);
              return;
            }
          })
        }
      },
      countDown(reset){
        if(reset){
          // 我的定时器
          this.myInterval&&clearInterval(this.myInterval);
          setTimeout(()=>{
            this.myInterval = null;
            // 验证码等待时间
            this.waitSeconds =  0;
          });
        }else{
          // 倒计时
          this.waitSeconds = MAX_WAIT_SECONDS;
          this.myInterval = setInterval(() => {
            this.waitSeconds--;
            if(this.waitSeconds == 0){
              clearInterval(this.myInterval)
            }
          },1000);
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "../../style/login_2";
</style>
