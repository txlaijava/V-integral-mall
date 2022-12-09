/**
 * Created by gfj on 2018/5/17.
 */
//头部导航信息集合
export const dataView = {
  headTxt: "已有账号",
  headUrl: 'http://www.rblcmall.com',
  headLogin: {txt: '登录',url: 'login'},
  signupTileTxt: '创建你的账号',
  inputData: {
    email: {placeholder: '登录用的邮箱地址，请确保可用',Verify:{NULL_ERROR:'此项不能为空',FORMAT_ERROR:'请输入正确的邮箱地址'}},
    pwd: {placeholder: '你的密码，大于6位且包含字母和数字',Verify: {NULL_ERROR: '此项不能为空',FORMAT_ERROR:'密码必须包含字母和数字',MIN_ERROR:'不能小于6位数',MAX_ERROR:'不能大于30位数'}},
    name: {placeholder: '你的真实姓名',Verify: {NULL_ERROR: '此项不能为空'}},
    company: {placeholder: '你的公司名称',Verify: {NULL_ERROR: '请输入密码'}},
    apply: {placeholder: '你的应用名称',Verify: {NULL_ERROR: '请输入密码'}},
    telephone: {placeholder: '你的联系电话',Verify: {NULL_ERROR: '此项不能为空',FORMAT_ERROR:'请输入正确的电话号码'}},
    code: {placeholder: '短信验证码',sendCodeTxt: '获取验证码',waitSecondsTxt: 's后重新发送',Verify: {CODE_ERROR: ''}},
    agreement: {oneTxt: '我已阅读并同意',twoTxt: '《红商城服务使用协议》'},
  },
  btnSignupTxt: '创 建 账 号',
  errorData: {SERVICE_ERROR:'服务器开了会小差，请稍后再试',SIGNUP_MORE_ERROR:'该邮箱已被注册！',MOBILENO_MORE_ERROR:'该手机号码已被绑定！',SERVICE_CODE_ERROR: '获取验证码失败，请稍后再试',CODE_ERROR: '验证码错误！'},
  successData: {}
}
