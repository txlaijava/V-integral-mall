<template>
  <div class="login-page">
    <div class="header-wrap">
      <a class="rblc-logo" :href="dataView.headUrl"></a>
      <div class="right-side">{{dataView.headTxt}}，
        <router-link :to="dataView.headLogin.url" :class="'txt-success'">{{dataView.headLogin.txt}}</router-link></div>
    </div>
    <div class="page-title-wrap signup">
      <span class="title-txt">{{dataView.signupTileTxt}}</span>
      <p class="error-tip-wrap" v-if="error.isError"> <span class="error-txt" v-text="error.msg"></span> </p>
    </div>
    <div class="form-wrap" :class="{'signup':!error.isError}">
      <p class="input-wrap">
        <input type="text" class="input-default email valid touched dirty" :placeholder="dataView.inputData.email.placeholder" v-model="email.mail" @blur="inputVerify('email')">
        <i class="iconfont icon-mailbox"></i>
        <span class="validate-txt" :class="[{'valid-txt': email.isSuccess},{'invalid-txt': email.isError}]" v-if="email.isError || email.isSuccess">
          <i class="iconfont" :class="[{'icon-checked2':email.isSuccess },{'icon-close2':email.isError}]"></i>
          <span v-if="email.isError" v-text="email.error"></span>
        </span>
      </p>
      <p class="input-wrap">
        <input type="password" class="input-default password valid touched dirty" :placeholder="dataView.inputData.pwd.placeholder" v-model="pass.newPass" @blur="inputVerify('newPass')">
        <i class="iconfont icon-password"></i>
        <span class="validate-txt" :class="[{'valid-txt': pass.isSuccess},{'invalid-txt': pass.isError}]" v-if="pass.isError || pass.isSuccess">
          <i class="iconfont" :class="[{'icon-checked2':pass.isSuccess },{'icon-close2':pass.isError}]"></i>
          <span v-if="pass.isError" v-text="pass.error"></span>
        </span>
      </p>
      <p class="input-wrap">
        <input type="text" class="input-default name valid touched dirty" :placeholder="dataView.inputData.name.placeholder" v-model="acName.name" @blur="inputVerify('name')">
        <i class="iconfont icon-author"></i>
        <span class="validate-txt" :class="[{'valid-txt': acName.isSuccess},{'invalid-txt': acName.isError}]" v-if="acName.isError || acName.isSuccess">
          <i class="iconfont" :class="[{'icon-checked2':acName.isSuccess },{'icon-close2':acName.isError}]"></i>
          <span v-if="acName.isError" v-text="acName.error"></span>
        </span>
      </p>
      <p class="input-wrap">
        <input type="text" class="input-default company valid touched dirty" :placeholder="dataView.inputData.company.placeholder" v-model="company.companyName" @blur="inputVerify('company')">
        <i class="iconfont icon-building"></i>
        <span class="validate-txt" :class="[{'valid-txt': company.isSuccess},{'invalid-txt': company.isError}]" v-if="company.isError || company.isSuccess">
          <i class="iconfont" :class="[{'icon-checked2':company.isSuccess },{'icon-close2':company.isError}]"></i>
          <span v-if="company.isError" v-text="company.error"></span>
        </span>
      </p>
      <p class="input-wrap">
        <input type="text" class="input-default apply valid touched dirty" :placeholder="dataView.inputData.apply.placeholder" v-model="apply.applyName" @blur="inputVerify('apply')">
        <i class="iconfont icon-appliance"></i>
        <span class="validate-txt" :class="[{'valid-txt': apply.isSuccess},{'invalid-txt': apply.isError}]" v-if="apply.isError || apply.isSuccess">
          <i class="iconfont" :class="[{'icon-checked2':apply.isSuccess },{'icon-close2':apply.isError}]"></i>
          <span v-if="apply.isError" v-text="apply.error"></span>
        </span>
      </p>
      <p class="input-wrap">
        <input type="text" class="input-default telephone valid touched dirty" :placeholder="dataView.inputData.telephone.placeholder" v-model="telephone.telephone" @blur="inputVerify('telephone')">
        <i class="iconfont icon-phone2"></i>
        <span class="validate-txt" :class="[{'valid-txt': telephone.isSuccess},{'invalid-txt': telephone.isError}]" v-if="telephone.isError || telephone.isSuccess">
          <i class="iconfont" :class="[{'icon-checked2':telephone.isSuccess },{'icon-close2':telephone.isError}]"></i>
          <span v-if="telephone.isError" v-text="telephone.error"></span>
        </span>
      </p>
      <div class="input-wrap vertify-code">
        <input class="input-default code valid touched dirty" v-model="code" type="text" :placeholder="dataView.inputData.code.placeholder" @blur="inputVerify('code')">
        <i class="iconfont icon-message"></i>
        <a href="javascript:;" class="btn-l btn-warning" :disabled="!telephone.isSuccess" v-if="!waitSeconds" @click="sendCode()">{{dataView.inputData.code.sendCodeTxt}}</a>

        <a href="javascript:;" class="btn-l btn-warning" disabled v-if="waitSeconds">{{waitSeconds}}{{dataView.inputData.code.waitSecondsTxt}}</a>

      </div>
      <p>
        <span class="btn-checkbox" :class="{'active':btnCheckbox}" @click="btnCheckboxCk(btnCheckbox)"><span class="iconfont icon_checked"></span></span>
        <span>{{dataView.inputData.agreement.oneTxt}}<a class="txt-success" href="" target="_blank">{{dataView.inputData.agreement.twoTxt}}</a></span>
        <input class="agree" type="hidden" value="0">
      </p>
      <p>
        <a href="javascript:;" class="btn-l btn-primary btn-lg" :disabled='subDisabled' @click="signupSubmit()">{{dataView.btnSignupTxt}}</a></p>
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
        acName: {name: '',error: '',isError: false,isSuccess: false},
        company: {companyName: '',error: '',isError: false,isSuccess: false},
        apply: {applyName: '',error: '',isError: false,isSuccess: false},
        telephone: {telephone: '',error: '',isError: false,isSuccess: false},
        error: {isError: false,msg: ''},
        code:'',
        btnCheckbox: false,
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
          if('' != this.code && this.email.isSuccess && this.pass.isSuccess && this.acName.isSuccess
                && this.company.isSuccess && this.apply.isSuccess && this.telephone.isSuccess && this.btnCheckbox){
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
              let params = {acMail: this.email.mail , acPassword: this.pass.newPass , acName:this.acName.name , authCode: this.code,
                acCompany: this.company.companyName , applyName:this.apply.applyName , acTelephone: this.telephone.telephone};
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

<!-- 限定作用域"scoped" 不要误写成scope -->
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/login";
</style>
