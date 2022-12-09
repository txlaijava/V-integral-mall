/**
 * Created by gfj on 2018/5/17.
 */
//头部导航信息集合
export const dataView = {
  headTxt: "没有账号",
  headUrl: 'http://www.rblcmall.com',
  headSingnup: {txt: '马上注册',url: 'signup'},
  forgotPwdTileTxt: '忘记密码',
  inputData: {
    email: {placeholder: '请输入注册邮箱',Verify:{NULL_ERROR:'此项不能为空',FORMAT_ERROR:'请输入正确的邮箱地址'}},
    telephone: {placeholder: '请输入注册手机号码',Verify: {NULL_ERROR: '此项不能为空',FORMAT_ERROR:'请输入正确的电话号码'}},
    code: {placeholder: '短信验证码',sendCodeTxt: '获取验证码',waitSecondsTxt: 's后重新发送',Verify: {CODE_ERROR: ''}},
    pwd: {placeholder: '你的密码，大于6位且包含字母和数字',Verify: {NULL_ERROR: '此项不能为空',FORMAT_ERROR:'密码必须包含字母和数字',MIN_ERROR:'不能小于6位数',MAX_ERROR:'不能大于12位数',NOT_EQ_ERROR: '密码不一致'}},
    twoPwd: {placeholder: '再次输入新密码',Verify: {NULL_ERROR: '此项不能为空',FORMAT_ERROR:'密码必须包含字母和数字',MIN_ERROR:'不能小于6位数',MAX_ERROR:'不能大于12位数',NOT_EQ_ERROR: '密码不一致'}},
  },
  btnTxt: '确 定',
  btnReturn: {txt: '返回登录',url:'login'},
  errorData: {SERVICE_ERROR:'服务器开了会小差，请稍后再试',SERVICE_NOAC_ERROR:'账户不存在！',SERVICE_CODE_ERROR: '获取验证码失败，请稍后再试',CODE_ERROR: '验证码错误！',UP_PWD_ERROR:"修改失败！请稍后再试"},
  successData: {}
}
