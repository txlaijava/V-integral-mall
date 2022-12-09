<template>
    <div class="login-page">
        <div class="header-wrap">
            <a class="rblc-logo" :href="dataView.headUrl"></a>
            <div class="right-side"> {{dataView.headTxt}}，
                <router-link :to="dataView.headSingnup.url" :class="'txt-success'">{{dataView.headSingnup.txt}}
                </router-link>
            </div>
        </div>
        <div class="page-title-wrap">
            <span class="title-txt">{{dataView.childAcLoginTileTxt}}</span>
            <div class="error-tip-wrap" v-if="error.isError"><span class="error-txt" v-text="error.msg"></span></div>
        </div>
        <div class="form-wrap login">
            <p class="input-wrap">
                <input type="text" class="input-default email valid touched dirty"
                       :placeholder="dataView.inputData.email.placeholder"
                       v-model="acMail.mail" @blur="inputVerify('mail')" v-focus>
                <i class="iconfont icon-mailbox"></i>
                <span class="validate-txt" :class="[{'valid-txt': acMail.isSuccess},{'invalid-txt': acMail.isError}]"
                      v-if="acMail.isError || acMail.isSuccess">
          <i class="iconfont" :class="[{'icon-checked2':acMail.isSuccess },{'icon-close2':acMail.isError}]"></i>
          <span v-if="acMail.isError" v-text="acMail.error"></span>
        </span>
            </p>
            <p class="input-wrap">
                <input type="password" class="input-default password valid touched dirty"
                       :placeholder="dataView.inputData.pwd.placeholder"
                       v-model="acPassword.password" @keyup.enter="inputVerify('password')">
                <i class="iconfont icon-password"></i>
                <span class="validate-txt invalid-txt" v-if="acPassword.isError">
          <i class="iconfont" :class="{'icon-close2':acPassword.isError}"></i>
          <span v-text="acPassword.error"></span>
        </span>
            </p>
            <p class="helper-wrap">
        <span class="btn-checkbox" :class="[{'active': btnCheckbox}]" @click="btnCheckboxCk(btnCheckbox)">
          <span class="iconfont icon_checked"></span>
        </span>
                <span>{{dataView.btnCheckboxTxt}}</span>
                <router-link :to="dataView.btnPrimaryAc.url" :class="'forgetpassword-txt txt-success'">
                    {{dataView.btnPrimaryAc.txt}}
                </router-link>
            <p>
                <button class="btn-l btn-primary btn-lg" @click="loginSubmit()">{{dataView.btnLoginTxt}}</button>
            </p>
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
    @import "../../style/login";
</style>
