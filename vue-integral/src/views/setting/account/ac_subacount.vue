<template>
    <div class="main">
        <section class="subacount-wrap">
            <div class="page-title">
                <h2>子账户管理</h2></div>
            <div class="create-acount">
                <router-link class="btn btn-green btn-lg add-competition" to="createSubacount">
                    <i class="iconhandle"></i>新增子账号
                </router-link>
            </div>
            <div class="list-wrap">
                <table class="income-detail mt20">
                    <thead>
                    <tr class="tr-header">
                        <th style="width: 5%; text-align: center;">序号</th>
                        <th style="text-align: center;">登录名</th>
                        <th style="text-align: center;">运营者</th>
                        <th style="text-align: center;">备注</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="tr-items" v-for="(item,index) in subAccountItem">
                        <td>{{index + 1}}</td>
                        <td>{{item.ac_mail}}</td>
                        <td>{{item.ac_company}}</td>
                        <td>{{item.ac_remark}}</td>
                        <td class="td-actions">
                            <div class="td-count">
                <span>
                  <a href="javascript:void(0)" class="iconfont" @click="modal('password',index,item.ac_mail)"
                     v-on:mouseenter="tooltip(true,0)" v-on:mouseleave="tooltip(false,0)">&#xe738;</a>
                </span>
                                <div class="tooltip top" role="tooltip" style="display: none;">
                                    <div class="tooltip-arrow"></div>
                                    <div class="tooltip-inner">修改密码</div>
                                </div>
                                <span>
                  <router-link class="iconfont" :to="{name:'editor-sub-account',params:{subId:item.id}}"
                               v-on:mouseenter="tooltip(true,1)" v-on:mouseleave="tooltip(false,1)">
                    &#xe753;
                  </router-link>
                </span>
                                <div class="tooltip top" role="tooltip" style="display: none;">
                                    <div class="tooltip-arrow"></div>
                                    <div class="tooltip-inner">编辑</div>
                                </div>
                                <span>
                  <a href="javascript:void(0)" class="iconfont delete" @click.s="modal('delete',index,item.id)"
                     v-on:mouseenter="tooltip($event,true,2)" v-on:mouseleave="tooltip($event,false,2)">&#xe752;</a>
                </span>
                                <div class="tooltip top delete" role="tooltip" style="display: none;">
                                    <div class="tooltip-arrow"></div>
                                    <div class="tooltip-inner">删除</div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- 修改密码 -->
            <div role="dialog" class="password-edit-modal modal fade"
                 :class="[{'in' :passwordModal.bol },{'show' : passwordModal.bol},{'hide':!passwordModal.bol}]">
                <div class="modal-dialog" role="document" style="width: 600px; top: 279.5px; left: 540px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <i class="iconfont close" @click="modal('password')">&#xe695;</i>
                            <h4 class="modal-title">修改密码</h4></div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label class="control-label">新密码：</label>
                                <input type="text" class="form-control category-title-input invalid untouched pristine"
                                       v-model="pass.newPass"
                                       placeholder="请输入6-12位密码">
                                <p class="invalid" v-if="pass.isError"><i class="iconfont">&#xe6be;</i> {{pass.error}}
                                </p>
                            </div>
                            <div class="form-group">
                                <label class="control-label">确认新密码：</label>
                                <input type="text" class="form-control category-title-input invalid untouched pristine"
                                       v-model="twoPass.newPass"
                                       placeholder="请输入6-12位密码">
                                <p class="invalid" v-if="twoPass.isError"><i class="iconfont">&#xe6be;</i>
                                    {{twoPass.error}} </p>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-green btn-pd-lg mr15"
                               :class="{'disabled' : !twoPass.isSuccess || !pass.isSuccess}"
                               @click="upPwd(passwordModal.value.acMail,passwordModal.value.index)">确认</a>
                            <a class="btn btn-default btn-pd-lg" @click="modal('password')">取消</a></div>
                    </div>
                </div>
            </div>

            <!-- 删除提醒 -->
            <div role="dialog" class="modal fade delete"
                 :class="[{'in' :deleteModal.bol },{'show' : deleteModal.bol},{'hide':!deleteModal.bol}]">
                <div class="modal-dialog" role="document" style="width: 300px; top: 338.5px; left: 690px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <i class="iconfont close" @click="modal('delete')">&#xe695;</i>
                            <h4 class="modal-title">警告</h4>
                        </div>
                        <div class="modal-body">
                            <p>删除后无法恢复，是否确定删除？</p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-green btn-pd-lg mr15 btn-orange"
                               @click="delDataList(deleteModal.value.id,deleteModal.value.index)">删除</a>
                            <a class="btn btn-default btn-pd-lg" @click="modal('delete')">取消</a></div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<script>
    // 引入jq
    import $ from "jquery";
    import {getSubAccountList, delSubAccount, setAcChildPwd} from '@/service/getData';
    import * as SYSTEM from '@/api/system';
    import {dataView} from "@/api/localJson/ac_create_subac";

    export default {
        data() {
            return {
                subAccountItem: [],
                passwordModal: {bol: false, value: {index: '', acMail: ''}},
                deleteModal: {bol: false, value: {index: '', id: ''}},
                pass: {newPass: '', error: '', isError: false, isSuccess: false},
                twoPass: {newPass: '', error: '', isError: false, isSuccess: false}
            }
        },
        methods: {
            tooltip: function (event, hover, index) {
                if (hover) {
                    /*                    let top = $('tr:eq('+index+')').position().top;
                                        let left = $(event.target).position().left;
                                        console.log(top,left);
                                        $('.tooltip:eq('+index+')').show().css({
                                          "top":top,
                                          "left":left,
                                          'color':'#fff'
                                        });*/
                } else {
                    $('.tooltip').hide();
                }
            },
            modal(type, index, value) {
                if ('password' == type) {
                    [this.pass.newPass, this.pass.error, this.pass.isError, this.pass.isSuccess] = ['', '', false, false];
                    [this.pass.twoPass, this.twoPass.error, this.pass.twoPass, this.twoPass.isSuccess] = ['', '', false, false];
                    [this.passwordModal.bol, this.passwordModal.value.index, this.passwordModal.value.acMail] = [!this.passwordModal.bol, index, value];
                } else {
                    [this.deleteModal.bol, this.deleteModal.value.index, this.deleteModal.value.id] = [!this.deleteModal.bol, index, value];
                }
            },
            getDataList() {
                getSubAccountList().then(res => {
                    let re = res.data.data;
                    if (SYSTEM.SUCCSSS == re.success && SYSTEM.CODE_IS_OK == re.code) {

                        this.subAccountItem = re.data;
                    }
                });
            },
            delDataList: function (del_id, index) {
                delSubAccount({subAcId: del_id}).then(res => {
                    let re = res.data.data;
                    if (SYSTEM.SUCCSSS == re.success && SYSTEM.CODE_IS_OK == re.code) {
                        this.subAccountItem.splice(index, 1);
                        this.deleteModal.bol = false;
                    }
                })
            },
            upPwd: function (ac_mail, index) {

                if (this.twoPass.isSuccess && this.pass.isSuccess) {
                    setAcChildPwd({acMail: ac_mail, pwd: this.pass.newPass, valiType: 'acMail'}).then(res => {
                        let re = res.data.data;
                        if (SYSTEM.SUCCSSS == re.success && SYSTEM.CODE_IS_OK == re.code) {
                            this.passwordModal.bol = false;
                        }
                    })
                }
            },
            inputVerify: function (name) {

                if ('newPass' == name) {
                    let validator = {
                        reg: /^[0-9a-zA-Z]+$/,
                        rules: 'required' ,
                        min: 6 ,
                        max: 12 ,
                        value: this.pass.newPass,
                        getMessage: {
                            required: {msg: dataView.inputData.pwd.Verify.NULL_ERROR},
                            reg: {msg: dataView.inputData.pwd.Verify.NOT_LEGAL_ERROR},
                            min: {msg: dataView.inputData.pwd.Verify.MIN_ERROR},
                            max: {msg: dataView.inputData.pwd.Verify.MAX_ERROR},
                        }
                    };
                    this.extend(this.pass, validator);
                }

                if ('twoPwd' == name) {
                    let validator = {
                        reg: /^[0-9a-zA-Z]+$/,
                        rules: 'required|contrast', min: 6, max: 12, value: this.twoPass.newPass,
                        getMessage: {
                            required: {msg: dataView.inputData.twoPwd.Verify.NULL_ERROR},
                            reg: {msg: dataView.inputData.twoPwd.Verify.NOT_LEGAL_ERROR},
                            min: {msg: dataView.inputData.twoPwd.Verify.MIN_ERROR},
                            max: {msg: dataView.inputData.twoPwd.Verify.MAX_ERROR},
                            contrast: {msg: dataView.inputData.twoPwd.Verify.NOT_EQ_ERROR, object: this.pass}
                        }
                    };
                    this.extend(this.twoPass, validator);
                }
            },
            extend(obj, validator) {
                var reg = validator.reg;
                if (validator.rules.indexOf('required') >= 0 && (typeof(validator.value) == "undefined" || validator.value.replace(/\s+/g, "").length <= 0)) {
                    [obj.error, obj.isError, obj.isSuccess] = [validator.getMessage.required.msg, true, false];
                    return obj;
                }
                if (validator.min != void 0 && validator.value.replace(/\s+/g, "").length < validator.min) {
                    [obj.error, obj.isError, obj.isSuccess] = [validator.getMessage.min.msg, true, false];
                    return obj;
                }

                if (validator.max != void 0 && validator.value.replace(/\s+/g, "").length > validator.max) {
                    [obj.error, obj.isError, obj.isSuccess] = [validator.getMessage.max.msg, true, false];
                    return obj;
                }

                if ((validator.reg != void 0) && '' != validator.reg && !reg.test(validator.value)) {
                    [obj.error, obj.isError, obj.isSuccess] = [validator.getMessage.reg.msg, true, false];
                    return obj;
                }

                if (validator.rules.indexOf('contrast') >= 0 && validator.value != validator.getMessage.contrast.object.newPass) {
                    [obj.error, obj.isError, obj.isSuccess] = [validator.getMessage.contrast.msg, true, false];

                    let obj2 = validator.getMessage.contrast.object;
                    [obj2.error, obj2.isError, obj2.isSuccess] = [validator.getMessage.contrast.msg, true, false];
                    return obj;
                }


                if (validator.rules.indexOf('contrast') >= 0) {
                    let obj2 = validator.getMessage.contrast.object;
                    [obj2.error, obj2.isError, obj2.isSuccess] = ['', false, true];
                }

                [obj.error, obj.isError, obj.isSuccess] = ['', false, true];

                return obj;
            }
        },
        created: function () {
            this.getDataList()
        },
        watch: {
            'pass.newPass'(val, oldVal) {
                this.inputVerify('newPass');
            },
            'twoPass.newPass'(val, oldVal) {
                this.inputVerify('twoPwd');
            }
        }
    }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style lang="scss" scoped>
    .subacount-wrap .create-acount {
        float: right;
        margin: 10px 0;
    }

    .btn-lg {
        height: 32px;
        line-height: 32px;
        padding: 0 15px;
    }

    .subacount-wrap .tr-items td {
        text-align: center;
        padding-left: 5px;
        padding-right: 5px;
    }

    .td-actions .td-count {
        position: relative;
    }

    .tr-items .iconfont {
        color: #bbb;
        text-decoration: none;
    }

    .td-actions a:hover {
        color: #29b6b0;
    }

    .tr-items .iconfont.delete:hover {
        color: #ff795c;
    }

    .show {
        display: block !important;
    }

    .hide {
        display: none !important;
    }

    .modal-content .close {
        position: absolute;
        right: 10px;
        top: 8px;
        cursor: pointer;
        font-size: 18px;
        color: #d8d8d8;
        font-weight: 900;
    }

    .modal-content .close:hover {
        color: #444;
    }
</style>
