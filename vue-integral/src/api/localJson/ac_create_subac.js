/**
 * Created by gfj on 2018/6/12.
 */
export const dataView = {
  inputData: {
    email: {
      Verify: {NULL_ERROR: '请输入登录名', NOT_LEGAL_ERROR: '请输入字母或数字', MIN_ERROR: '不能小于4位数', MAX_ERROR: '不能大于8位数'},
    },
    pwd: {Verify: {NULL_ERROR: '请输入密码', NOT_LEGAL_ERROR: '请输入字母或数字', MIN_ERROR: '不能小于6位数', MAX_ERROR: '不能大于12位数'}},
    twoPwd: {
      Verify: {
        NULL_ERROR: '请输入密码',
        NOT_LEGAL_ERROR: '请输入字母或数字',
        MIN_ERROR: '不能小于6位数',
        MAX_ERROR: '不能大于12位数',
        NOT_EQ_ERROR: '密码不一致'
      }
    },
    errorData: {SERVICE_ERROR: '服务器开了会小差，请稍后再试'},
    successData: {}
  }
};
