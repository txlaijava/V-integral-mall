<template>
  <div class="page-login-v3 layout-full">
    <div class="page animsition vertical-align text-center" data-animsition-in="fade-in" data-animsition-out="fade-out"
         style="animation-duration: 800ms; opacity: 1;">
        <div class="page-content vertical-align-middle">
            <div class="panel">
                <div class="panel-body">
                    <div class="brand" :class="[{'has-error':error.isError}]">
                        <img class="brand-img" src="../../../static/logo/logo512_theme.png" alt="MetInfo 用户中心"
                             width="54"/>
                        <h2 class="brand-text font-size-20 margin-top-20">{{dataView.signupTileTxt}}</h2>
                        <small class="help-block" v-if="error.isError" v-text="error.msg"></small>
                    </div>
                    <form method="post" class="met-form-validation fv-form fv-form-bootstrap" novalidate="novalidate">
                      <div class="form-suite form-material floating" :class="[{'has-success':email.isSuccess},{'has-error':email.isError}]">
                          <input type="text" class="form-control empty" v-model="email.mail" @blur="inputVerify('email')" />
                          <label class="floating-label">{{dataView.inputData.email.placeholder}}</label>
                          <small class="help-block" v-if="email.isError" v-text="email.error"></small>
                      </div>

                      <div class="form-suite form-material floating" :class="[{'has-success':pass.isSuccess},{'has-error':pass.isError}]">
                        <input type="password" class="form-control empty" v-model="pass.newPass" @blur="inputVerify('newPass')" />
                        <label class="floating-label">{{dataView.inputData.pwd.placeholder}}</label>
                        <small class="help-block" v-if="pass.isError" v-text="pass.error"></small>
                      </div>

                      <!--<div class="form-suite form-material floating" :class="[{'has-success':acName.isSuccess},{'has-error':acName.isError}]">
                        <input type="text" class="form-control empty" v-model="acName.name" @blur="inputVerify('name')" />
                        <label class="floating-label">{{dataView.inputData.company.placeholder}}</label>
                        <small class="help-block" v-if="acName.isError" v-text="acName.error"></small>
                      </div>

                      <div class="form-suite form-material floating" :class="[{'has-success':company.isSuccess},{'has-error':company.isError}]">
                        <input type="text" class="form-control empty" v-model="company.companyName" @blur="inputVerify('company')" />
                        <label class="floating-label">{{dataView.inputData.name.placeholder}}</label>
                        <small class="help-block" v-if="company.isError" v-text="company.error"></small>
                      </div>-->

                      <div class="form-suite form-material floating" :class="[{'has-success':apply.isSuccess},{'has-error':apply.isError}]">
                        <input type="text" class="form-control empty" v-model="apply.applyName" @blur="inputVerify('apply')" />
                        <label class="floating-label">{{dataView.inputData.apply.placeholder}}</label>
                        <small class="help-block" v-if="apply.isError" v-text="apply.error"></small>
                      </div>

                      <div class="form-suite form-material floating" :class="[{'has-success':telephone.isSuccess},{'has-error':telephone.isError}]">
                        <input type="text" class="form-control empty" v-model="telephone.telephone" @blur="inputVerify('telephone')" />
                        <label class="floating-label">{{dataView.inputData.telephone.placeholder}}</label>
                        <small class="help-block" v-if="telephone.isError" v-text="telephone.error"></small>
                      </div>

                      <div class="form-suite form-material floating" :class="[{'has-success':code!=''}]">
                        <div class="input-suite">
                          <div class="form-control-wrap margin-left-0 margin-right-10">
                            <input type="text" class="form-control empty" name="code" v-model="code" @blur="inputVerify('code')" maxlength="6">
                            <label class="floating-label">{{dataView.inputData.code.placeholder}}</label>
                          </div>
                          <span class="input-suite-btn">
                            <button type="button" class="btn btn-default btn-block " style="font-size: 12px;" :disabled="!telephone.isSuccess"  v-if="!waitSeconds" @click="sendCode()">{{dataView.inputData.code.sendCodeTxt}}</button>
                            <button type="button" class="btn btn-default btn-block " style="font-size: 12px;" disabled v-if="waitSeconds">{{waitSeconds}}{{dataView.inputData.code.waitSecondsTxt}}</button>
                          </span>
                        </div>
                      </div>

                      <button type="button" class="btn btn-primary btn-block btn-lg margin-top-40" :disabled='subDisabled' @click="signupSubmit()">{{dataView.btnSignupTxt}}</button>
                    </form>
                    <p>已经有账号了? 去<router-link to="/admin/login">登录</router-link></p>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
  import {singnupAc,getSMSCode} from '@/service/getData';
  import { dataView } from "@/api/localJson/signupView"
  import * as SYSTEM from '@/api/system';

  // 最大等待秒数
  const MAX_WAIT_SECONDS = 60;
  export default {
    data (){
      return {
        dataView: dataView,
        email: {mail: '',error: '',isError: false,isSuccess: false},
        pass: {newPass: '',error: '',isError: false,isSuccess: false},
        /*acName: {name: '',error: '',isError: false,isSuccess: false},
        company: {companyName: '',error: '',isError: false,isSuccess: false},*/
        apply: {applyName: '',error: '',isError: false,isSuccess: false},
        telephone: {telephone: '',error: '',isError: false,isSuccess: false},
        error: {isError: false,msg: ''},
        code:'',
        btnCheckbox: true,
        subDisabled:true,
        // 我的定时器
        myInterval: null,
        // 验证码等待时间
        waitSeconds: 0,
      }
    },
    methods: {
      inputVerify(name){
        if('email' == name){
          var validator = {
            reg: /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}$/ ,
            rules: 'required' , value: this.email.mail,
            getMessage: {required:{msg: this.dataView.inputData.email.Verify.NULL_ERROR},reg: {msg: this.dataView.inputData.email.Verify.FORMAT_ERROR}}
          }
          this.extend(this.email,validator)
        }
        if('newPass' == name){
          var validator = {
            reg: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,30}$/ ,
            rules: 'required',min: 6,max: 30, value: this.pass.newPass,
            getMessage: {required:{msg: this.dataView.inputData.pwd.Verify.NULL_ERROR},
              reg: {msg: this.dataView.inputData.pwd.Verify.FORMAT_ERROR},
              min: {msg: this.dataView.inputData.pwd.Verify.MIN_ERROR},max: {msg: this.dataView.inputData.pwd.Verify.MAX_ERROR}}
          }
          this.extend(this.pass,validator)
        }

        if('name' == name || 'company' == name || 'apply' == name){
          var value = '';
          let obj = null;
          switch (name){
            case 'name':
              value = this.acName.name;
              obj = this.acName;
              break;
            case 'company':
              value = this.company.companyName;
              obj = this.company;
              break;
            case 'apply':
              value = this.apply.applyName;
              obj = this.apply;
              break;
          }

          var validator = {
            rules: 'required', value: value,
            getMessage: {required:{msg: this.dataView.inputData.name.Verify.NULL_ERROR}}
          }
          this.extend(obj,validator)
        }

        if('telephone' == name){
          var validator = {
            reg: /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/ ,
            rules: 'required', value: this.telephone.telephone,
            getMessage: {required:{msg: this.dataView.inputData.telephone.Verify.NULL_ERROR},reg: {msg: this.dataView.inputData.telephone.Verify.FORMAT_ERROR}}
          }
          this.extend(this.telephone,validator)
        }
        if('' != this.code && this.email.isSuccess && this.pass.isSuccess && this.apply.isSuccess && this.telephone.isSuccess && this.btnCheckbox){
          this.subDisabled = false;
        }else{
          this.subDisabled = true;
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
        return obj;
      },
      btnCheckboxCk(store){
        this.btnCheckbox = !store;
        this.inputVerify('')
      },
      sendCode(){
        if(this.telephone.isSuccess){
          // 获取验证码请求
          let params = {mobileNo: this.telephone.telephone};
          getSMSCode(params).then(re =>{
            this.countDown(false);
            const res = re.data.data;
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
      signupSubmit(){
        if(!this.subDisabled){
          let params = {acMail: this.email.mail , acPassword: this.pass.newPass ,authCode: this.code,
            applyName:this.apply.applyName , acTelephone: this.telephone.telephone};
          singnupAc(params).then(re =>{
            if(SYSTEM.CODE_IS_ERROR == re.data.code){
              this.error.isError = true;
              this.error.msg = this.dataView.errorData.SERVICE_ERROR;
              return;
            }

            var res = re.data.data;
            if(SYSTEM.SUCCSSS == res.success && SYSTEM.CODE_IS_OK == res.code){
              this.error.isError = false;
              this.error.msg = '';
              this.$router.push('login');
            }else{
              this.error.isError = true;
              if(res.code == SYSTEM.CODE_MOBILENO_NULL_ERROR || res.code == SYSTEM.CODE_CODE_NULL_ERROR
                || res.code == SYSTEM.CODE_CACHE_NULL_ERROR || res.code == SYSTEM.CODE_NOTEQ_ERROR ){
                this.error.msg = this.dataView.errorData.CODE_ERROR;
                return
              }
              if(res.code == SYSTEM.CODE_SIGNUP_MORE_ERROR){
                this.error.msg = this.dataView.errorData.SIGNUP_MORE_ERROR;
                return
              }
              if(res.code == SYSTEM.CODE_MOBILENO_MORE_ERROR){
                this.error.msg = this.dataView.errorData.MOBILENO_MORE_ERROR;
                return
              }
            }
          });
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
