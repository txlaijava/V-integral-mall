

/**
 * 全局code代码
 */
export const SUCCSSS = true;

// 接口编号 => 成功
export const CODE_IS_OK = 0;

// 接口编号 => 异常
export const CODE_IS_ERROR = 5000;


/**
 * 短信接口 Code 码
 * @type {number}
 */
// 接收短信的手机号码为空
export const CODE_MOBILENO_NULL_ERROR = 1003;
// 待验证的验证码为空
export const CODE_CODE_NULL_ERROR = 1004;
// 对应手机号码没有发送验证码
export const CODE_CACHE_NULL_ERROR = 4000;
// 验证码错误
export const CODE_NOTEQ_ERROR = 4001;


/**
 * 注册账号 Code 码
 * @type {number}
 */
// 该邮箱已有人注册
export const CODE_SIGNUP_MORE_ERROR = 4002;

// 该手机号码已被绑定
export const CODE_MOBILENO_MORE_ERROR = 40023;


/**
 * 验证账号
 * @type {number}
 */
// 没有找到对应账户
export const CODE_NOAC_ERROR = 2000;


