<template>
  <div class="main">
    <section data-v-2c833abd="" class="acount-edit-wrap">
      <div data-v-2c833abd="" class="page-title">
        <h2 data-v-2c833abd="" class="has-sub-title">
          <span data-v-2c833abd="" class="sub-title">添加子账号</span>
        </h2>
      </div>
      <div data-v-2c833abd="" class="content-wrap">
        <div data-v-2c833abd="" class="required-msg bottom-line">
          <div data-v-2c833abd="" class="password-wrap">
            <div data-v-2c833abd="" class="form-group pass-wrap re-enter-pass">
              <input data-v-2c833abd="" type="password" placeholder="请输入6-12位密码"
                     v-model="pass.newPass"
                     class="form-control w500 invalid pristine touched">
              <label data-v-2c833abd="" class="control-label label-required">新密码：</label>
              <span class="iconrepass" v-show="pass.isSuccess">
                <img src="//yun.duiba.com.cn/h5/static/static/tick.png">
              </span>
              <p class="invalid" v-if="pass.isError"> <i class="iconfont">&#xe6be;</i> {{pass.error}} </p>
            </div>
            <div data-v-2c833abd="" class="form-group pass-wrap">
              <input data-v-2c833abd="" type="password" placeholder="请输入6-12位密码"
                     v-model="twoPass.newPass"
                     class="form-control w500 invalid untouched pristine">
              <label data-v-2c833abd="" class="control-label label-required">确认密码：</label>
              <span class="iconrepass" v-show="twoPass.isSuccess">
                <img src="//yun.duiba.com.cn/h5/static/static/tick.png">
              </span>
              <p class="invalid" v-if="twoPass.isError"> <i class="iconfont">&#xe6be;</i> {{twoPass.error}} </p>
            </div>
          </div>
        </div>
      </div>
      <div data-v-2c833abd="" class="create-acount">
        <a data-v-2c833abd="" class="btn btn-green btn-lg save-powers" @click="updateSub()">
          <i data-v-2c833abd="" class="iconhandle"></i>确认修改</a>
      </div>
    </section>
  </div>
</template>


<script>
    import { dataView } from "@/api/localJson/ac_create_subac";
    import {setAcPwd} from '@/service/getData';
    export default {
        data (){
            return {
                pass: {newPass: '',error: '',isError: false,isSuccess: false},
                twoPass: {newPass: '',error: '',isError: false,isSuccess: false}
            }
        },
        methods: {
            inputVerify: function(name){

              if('newPass' == name){
                let validator = {
                  reg: /^[0-9a-zA-Z]+$/ ,
                  rules: 'required',min: 6,max: 12, value: this.pass.newPass,
                  getMessage: {
                    required:{msg: dataView.inputData.pwd.Verify.NULL_ERROR},
                    reg: {msg: dataView.inputData.pwd.Verify.NOT_LEGAL_ERROR},
                    min: {msg: dataView.inputData.pwd.Verify.MIN_ERROR},
                    max: {msg: dataView.inputData.pwd.Verify.MAX_ERROR}
                  }
                };
                this.extend(this.pass,validator);
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
                [obj.error,obj.isError,obj.isSuccess] = [validator.getMessage.required.msg,true,false];
                return obj;
              }
              if(validator.min != void 0 && validator.value.replace(/\s+/g,"").length < validator.min){
                [obj.error,obj.isError,obj.isSuccess] = [validator.getMessage.min.msg,true,false];
                return obj;
              }

              if(validator.max != void 0 && validator.value.replace(/\s+/g,"").length > validator.max){
                [obj.error,obj.isError,obj.isSuccess] = [validator.getMessage.max.msg,true,false];
                return obj;
              }

              if((validator.reg != void 0) && '' != validator.reg && !reg.test(validator.value)){
                [obj.error,obj.isError,obj.isSuccess] = [validator.getMessage.reg.msg,true,false];
                return obj;
              }

              if(validator.rules.indexOf('contrast') >= 0 && validator.value != validator.getMessage.contrast.object.newPass){
                [obj.error,obj.isError,obj.isSuccess] = [validator.getMessage.contrast.msg,true,false];

                let obj2 = validator.getMessage.contrast.object;
                [obj2.error,obj2.isError,obj2.isSuccess] = [validator.getMessage.contrast.msg,true,false];
                return obj;
              }


              if(validator.rules.indexOf('contrast') >= 0){
                let obj2 = validator.getMessage.contrast.object;
                [obj2.error,obj2.isError,obj2.isSuccess] = ['',false,true];
              }

              [obj.error,obj.isError,obj.isSuccess] = ['',false,true];

              return obj;
            },
            updateSub: function(){
              if(this.twoPass.isSuccess && this.pass.isSuccess){
                  let mobileNo = JSON.parse(getStorage('loginInfo')).mobileNo;
                  setAcPwd({mobileNo:mobileNo,pwd:this.pass.newPass,valiType:'acTelephone'}).then(res =>{
                      let re = res.data.data;
                      if(SYSTEM.SUCCSSS == re.success && SYSTEM.CODE_IS_OK == re.code){
                      }
                  })
              }
            }
        },
        watch: {
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

</style>
