<template>
  <div class="login-page">
    <div class="header-wrap">
      <a class="rblc-logo" :href="dataView.headUrl"></a>
      <div class="right-side"> {{dataView.headTxt}}，
        <router-link :to="dataView.headSingnup.url" :class="'txt-success'">{{dataView.headSingnup.txt}}</router-link>
      </div>
    </div>
    <div class="page-title-wrap">
      <span class="title-txt">{{dataView.forgotPwdTileTxt}}</span>
      <div class="error-tip-wrap" v-if="error.isError"> <span class="error-txt" v-text="error.msg"></span> </div>
    </div>
    <div class="form-wrap forget-password" v-if="!isUpPwdShow">
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
        <a href="javascript:;" class="btn-l btn-warning" :disabled="!telephone.isSuccess" v-if="!waitSeconds" @click="valiAc()">{{dataView.inputData.code.sendCodeTxt}}</a>

        <a href="javascript:;" class="btn-l btn-warning" disabled v-if="waitSeconds">{{waitSeconds}}{{dataView.inputData.code.waitSecondsTxt}}</a>
      </div>
      <p>
        <button class="btn-l btn-primary btn-lg" :disabled='subDisabled' @click="reset(false)">{{dataView.btnTxt}}</button>
      </p>
      <p>
        <router-link :to="dataView.btnReturn.url" :class="'btn-return v-link-active'">
          <i class="iconfont icon-return"></i><span>{{dataView.btnReturn.txt}}</span>
        </router-link>
      </p>
    </div>


    <div class="form-wrap forget-password reset-password" v-if="isUpPwdShow">
      <p class="email-title">为账号手机号<span class="txt-success">{{telephone.telephone}}</span>重置密码</p>
      <p class="input-wrap">
        <input type="password" class="input-default password valid touched dirty" :placeholder="dataView.inputData.pwd.placeholder" v-model="pass.newPass" @blur="inputVerify('newPass')">
        <i class="iconfont icon-password"></i>
        <span class="validate-txt" :class="[{'valid-txt': pass.isSuccess},{'invalid-txt': pass.isError}]" v-if="pass.isError || pass.isSuccess">
          <i class="iconfont" :class="[{'icon-checked2':pass.isSuccess },{'icon-close2':pass.isError}]"></i>
          <span v-if="pass.isError" v-text="pass.error"></span>
        </span>
      </p>
      <p class="input-wrap">
        <input type="password" class="input-default password valid touched dirty" :placeholder="dataView.inputData.twoPwd.placeholder" v-model="twoPass.newPass" @blur="inputVerify('twoPwd')">
        <i class="iconfont icon-password"></i>
        <span class="validate-txt" :class="[{'valid-txt': twoPass.isSuccess},{'invalid-txt': twoPass.isError}]" v-if="twoPass.isError || twoPass.isSuccess">
          <i class="iconfont" :class="[{'icon-checked2':twoPass.isSuccess },{'icon-close2':twoPass.isError}]"></i>
          <span v-if="twoPass.isError" v-text="twoPass.error"></span>
        </span>
      </p>
      <p>
        <button class="btn-l btn-primary btn-lg" :disabled='subDisabled' @click="reset(true)">确 认 重 置 密 码</button>
      </p>
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

<!-- 限定作用域"scoped" 不要误写成scope -->
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/login";
</style>
