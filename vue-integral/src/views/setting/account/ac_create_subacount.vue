<template>
  <div class="main">
    <section class="acount-edit-wrap">
      <div class="page-title">
        <h2 class="has-sub-title">子账号管理
          <i class="iconfont">&#xe68d;</i>
          <span class="sub-title">添加子账号</span>
        </h2>
      </div>
      <div class="content-wrap">
        <div class="required-msg bottom-line">
          <div class="form-group">
            <div>
              <input type="text" class="form-control w300 invalid pristine touched"
                     v-model="email.mail"
                     placeholder="请输入4~8位英文字母或数字">
              <span class="main-acount">：{{parentName}}</span>
              <p class="invalid" v-if="email.isError"><i class="iconfont">&#xe6be;</i> {{email.error}} </p>
            </div>
            <label class="control-label label-required">登录名：</label>
          </div>
          <div class="password-wrap">
            <div class="form-group pass-wrap re-enter-pass">
              <input type="password" placeholder="请输入6-12位密码"
                     v-model="pass.newPass"
                     class="form-control w500 invalid pristine touched">
              <label class="control-label label-required">登录密码：</label>
              <span class="iconrepass" v-show="pass.isSuccess">
                <img src="//yun.duiba.com.cn/h5/static/static/tick.png">
              </span>
              <p class="invalid" v-if="pass.isError"> <i class="iconfont">&#xe6be;</i> {{pass.error}} </p>
            </div>
            <div class="form-group pass-wrap">
              <input type="password" placeholder="请输入6-12位密码"
                     v-model="twoPass.newPass"
                     class="form-control w500 invalid untouched pristine">
              <label class="control-label label-required">确认密码：</label>
              <span class="iconrepass" v-show="twoPass.isSuccess">
                <img src="//yun.duiba.com.cn/h5/static/static/tick.png">
              </span>
              <p class="invalid" v-if="twoPass.isError"> <i class="iconfont">&#xe6be;</i> {{twoPass.error}} </p>
            </div>
          </div>
        </div>
        <div class="remark-wrap bottom-line">
          <div class="form-group">
            <input type="text" class="form-control w300 valid untouched pristine"
                   v-model="companyName"
                   placeholder="选填最多8个字">
            <label class="control-label">运营者姓名：</label></div>
          <div class="form-group">
            <input type="text" class="form-control w300 valid untouched pristine"
                   v-model="acRemark"
                   placeholder="选填最多15个字">
            <label class="control-label">备注：</label></div>
        </div>
      </div>
      <div class="create-acount">
        <a class="btn btn-green btn-lg save-powers" @click="createSub()">
          <i class="iconhandle"></i>保存配置</a>
      </div>
    </section>
  </div>
</template>

<script>
    import {getStorage} from '@/api/utils';
    import { dataView } from "@/api/localJson/ac_create_subac";
    import {createSubAccount} from '@/service/getData';
    export default {
        data (){
            return {
                parentName: JSON.parse(getStorage('loginInfo')).acMail || '',
                email: {mail: '',error: '',isError: false},
                pass: {newPass: '',error: '',isError: false,isSuccess: false},
                twoPass: {newPass: '',error: '',isError: false,isSuccess: false},
                acRemark: '',
                companyName: ''
            }
        },
        methods: {
            inputVerify(name){
              if('email' == name){
                  let validator = {
                    reg: /^[0-9a-zA-Z]+$/ ,
                    rules: 'required',min: 4,max: 8, value: this.email.mail,
                    getMessage: {
                        required:{msg: dataView.inputData.email.Verify.NULL_ERROR},
                        reg: {msg: dataView.inputData.email.Verify.NOT_LEGAL_ERROR},
                        min: {msg: dataView.inputData.email.Verify.MIN_ERROR},
                        max: {msg: dataView.inputData.email.Verify.MAX_ERROR}
                    },
                  }
                  this.extend(this.email,validator)
              }

              if('newPass' == name){
                  let validator = {
                    reg: /^[0-9a-zA-Z]+$/ ,
                    rules: 'required|contrast',min: 6,max: 12, value: this.pass.newPass,
                    getMessage: {
                        required:{msg: dataView.inputData.pwd.Verify.NULL_ERROR},
                        reg: {msg: dataView.inputData.pwd.Verify.NOT_LEGAL_ERROR},
                        min: {msg: dataView.inputData.pwd.Verify.MIN_ERROR},
                        max: {msg: dataView.inputData.pwd.Verify.MAX_ERROR},
                        contrast: {msg: dataView.inputData.twoPwd.Verify.NOT_EQ_ERROR,object:this.twoPass}
                    },
                  }
                  this.extend(this.pass,validator)
              }

              if('twoPwd' == name){
                  let validator = {
                      reg: /^[0-9a-zA-Z]+$/ ,
                      rules: 'required|contrast',min: 6,max: 12, value: this.twoPass.newPass,
                      getMessage: {
                          required:{msg: dataView.inputData.twoPwd.Verify.NULL_ERROR},
                          reg: {msg: dataView.inputData.twoPwd.Verify.NOT_LEGAL_ERROR},
                          min: {msg: dataView.inputData.twoPwd.Verify.MIN_ERROR},
                          max: {msg: dataView.inputData.twoPwd.Verify.MAX_ERROR},
                          contrast: {msg: dataView.inputData.twoPwd.Verify.NOT_EQ_ERROR,object:this.pass}
                      }
                  };
                  this.extend(this.twoPass,validator);
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
                    obj.error = validator.getMessage.reg.msg;
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

                    let obj2 = validator.getMessage.contrast.object;
                    obj2.error = validator.getMessage.contrast.msg;
                    obj2.isError = true;
                    obj2.isSuccess = false;
                    return obj;
                }else if(validator.rules.indexOf('contrast') >= 0){
                    obj.error = '';
                    obj.isError = false;
                    obj.isSuccess = true;

                    let obj2 = validator.getMessage.contrast.object;
                    obj2.error = '';
                    obj2.isError = false;
                    obj2.isSuccess = true;
                }

                return obj;
              },
            createSub(){
                let params = {acMail: this.email.mail + ":"+this.parentName , acPassword: this.pass.newPass , acRemark: this.acRemark , acCompany: this.companyName};
                createSubAccount(params).then(res =>{
                    let re = res.data.data;
                    if(re.success){
                        this.$router.go(-1);
                    }
                });
            }
        },
        watch: {
            'email.mail'(val, oldVal){
                this.inputVerify('email');
            },
            'pass.newPass'(val, oldVal){
                this.inputVerify('newPass');
            },
            'twoPass.newPass'(val, oldVal){
                this.inputVerify('twoPwd');
            }
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
