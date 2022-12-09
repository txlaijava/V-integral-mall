<template>
  <div class="page-login-v3 layout-full">
    <div class="page animsition vertical-align text-center" style="animation-duration: 800ms; opacity: 1;">
      <div class="page-content vertical-align-middle">
        <div class="panel">
          <div class="panel-body">
            <div class="brand">
              <img class="brand-img" src="../../../static/logo/logo512_theme.png" alt="MetInfo 用户中心" width="54"/>
              <h2 class="brand-text font-size-20">会员登录</h2>
            </div>
            <form method="post" class="met-form-validation fv-form fv-form-bootstrap" novalidate="novalidate" autocomplete="off">
              <div class="form-suite form-material floating" :class="[{'has-success':acMail.isSuccess},{'has-error':acMail.isError}]" >
                <input type="text" class="form-control empty" name="username" autocomplete="off" v-model="acMail.mail" @blur="inputVerify('mail')" v-focus />
                <label class="floating-label" >{{dataView.inputData.email.placeholder}}</label>
                <small class="help-block" v-if="acMail.isError" v-text="acMail.error"></small>
              </div>
              <div class="form-suite form-material floating" :class="[{'has-success':acPassword.password!=''},{'has-error':acPassword.error}]">
                <input type="password" class="form-control empty" name="password" autocomplete="off" v-model="acPassword.password"  @keyup.enter="inputVerify('password')" />
                <label class="floating-label">{{dataView.inputData.pwd.placeholder}}</label>
                <small class="help-block" v-if="acPassword.isError" v-text="acPassword.error"></small>
              </div>
              <button type="button" class="btn btn-primary btn-block btn-lg margin-top-40" @click="loginSubmit()">{{dataView.btnLoginTxt}}</button>
            </form>
            <ol class="breadcrumb margin-bottom-0 padding-0">
              <li><router-link :to="dataView.btnPrimaryAc.url" :class="'forgetpassword-txt txt-success'">{{dataView.btnPrimaryAc.txt}}</router-link></li>
            </ol>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {accountLogin} from '@/service/getData';
  import * as SYSTEM from '@/api/system';
  import {setStorage, getBooleanStorage, getStorage, removeStorage} from '@/api/utils';
  import {dataView} from "@/api/localJson/loginView"
  // vuex状态管理
  import {mapActions} from 'vuex'

  export default {
    data() {
      return {
        dataView: dataView,
        acMail: {mail: getStorage("REMEMBER_AC_MAIL") || '', error: '', isError: false, isSuccess: false},
        acPassword: {password: '', error: '', isError: false},
        error: {isError: false, msg: ''},
        btnCheckbox: getBooleanStorage("REMEMBER_AC_STORE"),
        isDisable: true
      }
    },
    directives: {
      focus: {
        inserted(el) {
          el.focus()
        }
      }
    },
    methods: {
      ...mapActions(['setUserInfo']),
      inputVerify: function (type) {
          console.log("fsfs");
        if ('mail' == type) {
          var regEmail = /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}$/;
          if (this.acMail.mail.replace(/\s+/g, "").length <= 0) {
            this.acMail.error = this.dataView.inputData.email.Verify.NULL_ERROR;
            this.acMail.isError = true;
            this.acMail.isSuccess = false;
            return;
          }
          const account = this.acMail.mail.split(":")[0];
          const mail = this.acMail.mail.split(":")[1];
          if (!regEmail.test(mail) || account.length <= 0) {
            this.acMail.error = this.dataView.inputData.email.Verify.NOT_LEGAL_ERROR;
            this.acMail.isError = true;
            this.acMail.isSuccess = false;
            return;
          } else {
            this.acMail.isSuccess = true;
          }
          this.acMail.isError = false;
        } else if ('password' == type) {
          if (this.acPassword.password.replace(/\s+/g, "").length <= 0) {
            this.acPassword.error = this.dataView.inputData.pwd.Verify.NULL_ERROR;
            this.acPassword.isError = true;
            this.acPassword.isSuccess = false;
            return;
          }
          this.acPassword.isSuccess = true;
        }
      },
      btnCheckboxCk: function (store) {
        this.btnCheckbox = !store;
        setStorage('REMEMBER_AC_STORE', this.btnCheckbox)
        if (!this.btnCheckbox) {
          removeStorage('REMEMBER_AC_MAIL')
        }
      },
      loginSubmit: function () {
          console.log("11");
        if (!this.acMail.isSuccess) {
          this.inputVerify('mail');
          return;
        }

        if (this.acPassword.isError) {
          this.inputVerify('password');
          return;
        }
        let params = {mail: this.acMail.mail, password: this.acPassword.password};
        if (!this.isDisable) {
          return
        }
        this.isDisable = false;
        accountLogin(params).then(re => {
          this.isDisable = true;
          let res = re.data.data;
          if (SYSTEM.CODE_IS_ERROR == re.data.code) {
            this.error.isError = true;
            this.error.msg = this.dataView.errorData.SERVICE_ERROR;
            return;
          }
          if (SYSTEM.SUCCSSS == res.success && SYSTEM.CODE_IS_OK == res.code) {
            this.setUserInfo(res.data);
            setStorage('Token', res.token);
            this.$router.push('index');
            if (this.btnCheckbox) {
              setStorage('REMEMBER_AC_MAIL', res.data.acMail)
            }
          } else if (-1 == res.code) {
            this.$router.push('index');
          } else {
            this.error.isError = true;
            this.error.msg = this.dataView.errorData.NOT_EQ_ERROR;
          }
        })
      }
    }
  }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/login_2";
</style>
