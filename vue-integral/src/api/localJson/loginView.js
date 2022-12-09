/**
 * Created by gfj on 2018/5/17.
 */
//头部导航信息集合
export const dataView = {
    headTxt: "没有账号",
    headUrl: 'http://www.rblcmall.com',
    headSingnup: {txt: '马上注册',url: 'signup'},
    loginTileTxt: '登录你的账号',
    childAcLoginTileTxt: '登录你的子账号',
    inputData: {
      email: {placeholder: '请输入你的邮箱地址',Verify:{NULL_ERROR:'邮箱地址不能为空',NOT_LEGAL_ERROR:'请输入正确的邮箱地址'}},
      pwd: {placeholder: '请输入你的密码',Verify: {NULL_ERROR: '请输入密码'}},
    },
    btnForgotPwd: {txt: '忘记密码？',url:'forgotpwd'},
    btnChildAc: {txt: '子账号登录',url:'subacountsigin'},
    btnPrimaryAc: {txt: '返回主账号登录',url:'login'},
    btnCheckboxTxt: '记住账号',
    btnLoginTxt: '登 录',
    errorData: {SERVICE_ERROR:'服务器开了会小差，请稍后再试',NOT_EQ_ERROR:'账号或者密码错误'},
    successData: {}
}
