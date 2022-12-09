package com.shopping.integral.enums;

/**
 * 类描述：异常枚举类
 *
 * @author：GuoFuJun
 * @date：2018年05月08日 15:25:03.
 */
public enum ResultErrorCodeEnum {

    /**
     * 500，未知错误
     */
    UNKNOWN_ERROR(500,"未知错误！"),

    /**
     * 参数错误
     */
    PARM_NULL_ERROR(1000,"参数异常,请检查参数的信息！"),
    PARM_EMAIL_NULL_ERROR(1001,"参数异常,邮箱地址为空！"),
    PARM_PWD_NULL_ERROR(1002,"参数异常,密码为空！"),
    PARM_MOBILE_NO_NULL_ERROR(1003,"参数异常,手机号码为空！"),
    PARM_SMS_CODE_NULL_ERROR(1004,"参数异常,短信验证码为空！"),
    PARM_SMS_TEMPLATE_CODE_NULL_ERROR(1005,"参数异常,发送短信模板编号为空！"),
    PARM_ACNAME_NULL_ERROR(1006,"参数异常,账户真实姓名为空！"),
    PARM_ACCOMPANY_NULL_ERROR(1007,"参数异常,账户所属公司为空！"),
    PARM_APPLYNAME_NULL_ERROR(1008,"参数异常,应用名称为空！"),
    PARM_APPLY_AC_NULL_ERROR(1009,"参数异常,应用所属账户为空！"),
    PARM_AC_ID_NULL_ERROR(1010,"参数异常,账户编号为空！"),
    PARM_AC_PARENT_ID_NULL_ERROR(1011,"参数异常,父级账户编号为空！"),
    PARM_APPLY_ID_NULL_ERROR(1012,"参数异常,应用编号为空！"),
    PARM_THEME_ID_NULL_ERROR(1013,"参数异常,主题编号为空！"),
    PARM_THEME_ITEM_TYPE_NULL_ERROR(1014,"参数异常,主题模板类型为空！"),
    PARM_GOODS_ID_NULL_ERROR(1015,"参数异常,商品编号为空！"),
    PARM_USER_ID_NULL_ERROR(1016,"参数异常,用户编号为空！"),
    PARM_ADDRESS_ID_NULL_ERROR(1017,"参数异常,收货地址编号为空！"),
    PARM_ADDRESS_MOBILE_NULL_ERROR(1018,"参数异常,收货人联系电话为空！"),
    PARM_ADDRESS_AREA_NULL_ERROR(1019,"参数异常,收货地址为空！"),
    PARM_ADDRESS_AREAINFO_NULL_ERROR(1020,"参数异常,收货详细地址为空！"),
    PARM_ADDRESS_TRUE_NAME_NULL_ERROR(1021,"参数异常,收货人姓名为空！"),
    PARM_CREDITS_NULL_ERROR(1022,"参数异常,积分数量为空！"),
    PARM_APPLY_INTEGRALEXCHANGERATE_NULL_ERROR(1023,"参数异常,积分汇率为空！"),
    PARM_APPLY_INTEGRALUNIT_NULL_ERROR(1024,"参数异常,积分单位为空！"),
    PARM_INTEGR_CHARGE_NULL_ERROR(1025,"参数异常,积分消费接口链接为空！"),
    PARM_INTEGR_HOOK_NULL_ERROR(1026,"参数异常,回调接口链接为空！"),
    PARM_CATEGORY_NAME_NULL_ERROR(1027,"参数异常,分类名称为空！"),
    PARM_CATEGORY_ICON_NULL_ERROR(1028,"参数异常,分类图标为空！"),
    PARM_CATEGORY_BANNER_NULL_ERROR(1029,"参数异常,分类广告图为空！"),
    PARM_CATEGORY_ID_NULL_ERROR(1030,"参数异常,分类编号为空！"),
    PARM_GEAR_CODE_NULL_ERROR(1031,"参数异常,商品栏位标识符为空！"),
    PARM_GEAR_NULL_ERROR(1032,"参数异常,商品栏位为空！"),

    /**
     * 对象错误
     */
    ACCOUNT_NULL_ERROR(2000,"未找到用户账户对象！"),
    ACCOUNT_PWD_ERROR(2001,"账号密码错误！"),
    DEL_SUB_ACCOUNT_PARENT_NULL_ERROR(2002,"删除子账号错误，该账号不是子账号！"),
    DEL_SUB_ACCOUNT_NOT_PARENT_ID_ERROR(2003,"删除的子账号，不是对应账户下的子账号！"),
    UPDATA_SUB_ACCOUNT_NOT_PARENT_ID_ERROR(2004,"修改的子账号信息，不是对应账户下的子账号！"),
    THEME_NULL_ERROR(2005,"未找到主题对象！"),
    APP_USER_NOT_LOGIN_ERROR(2006,"APP用户未登录！"),
    GOODS_NULL_ERROR(2007,"未找到商品对象！"),
    EXCHANGE_USER_NULL_ERROR(2008,"未找到兑换商品用户对象！"),
    AC_APPLY_NULL_ERROR(2009,"未找到应用对象！"),
    INTEGRAL_ORDER_NULL_ERROR(2010,"未找到订单对象！"),
    ADDRESS_NULL_ERROR(2011,"未找到地址对象！"),
    CATEGORY_NULL_ERROR(2012,"未找到类别对象！"),

    /**
     * 短信接口错误,邮箱接口错误
     */
    SMS_API_ERROR(3000,"短信发送失败！，接口异常"),
    SMS_API_SEND_ERROR(3001,"短信发送失败！"),
    MAIL_API_SEND_ERROR(3002,"邮箱发送失败！"),


    /**
     * 验证错误
     */
    VALID_SMS_MOBILE_NO_ERROR(4000,"该手机号码没有发送验证码！"),
    VALID_SMS_CODE_ERROR(4001,"验证失败,验证码错误！"),
    VALID_EMAIL_MORE_ERROR(4002,"验证失败, 该邮箱已绑定账号！"),
    VALID_MOBILENO_MORE_ERROR(40023,"验证失败, 该手机号码已绑定账号！"),


    /**
     * 验证兑换错误
     */
    EXCHANGERULES_EXCEED_FOREVER_LIMIT(6000,"该用户已兑换过该商品--兑换限制为永久"),
    EXCHANGERULES_EXCEED_DAY_LIMIT(6001,"该用户已兑换过该商品--兑换限制为天"),
    EXCHANGERULES_EXCEED_CYCLE_DAY_LIMIT(6002,"该用户已兑换过该商品--兑换限制为天"),
    EXCHANGERULES_EXCEED_START_TIME(6003,"该商品兑换时间未开始！"),
    EXCHANGERULES_EXCEED_END_TIME(6004,"该商品兑换时间已过！"),
    EXCHANGERULES_EXCEED_LIMIT(6005,"该用户已兑换过该商品--额外兑换限制"),


    ORDER_GOODS_INVENTORY_INSUFFICIENT(7000,"该商品库存不足！"),
    ORDER_GOODS_STATE(7001,"该商品还未上架！") ,
    ORDER_CANCEL_STATE(7002,"该订单状态不能进行取消") ,
    ORDER_PAY_LOCK_STATE(7003,"该订单正在进行支付，不能继续取消操作") ,
    ORDER_VERIFY_STATE(7004,"该订单状态不能进行核销") ,
    ORDER_AUDIT_STATE(7005,"该订单状态不能进行审核") ,

    /**
     * 支付错误
     */
    PAY_ERROR(8000,"支付请求异常！"),
    PAY_USER_NULL_ERROR(8001,"支付用户为空！"),
    PAY_ORDER_NULL_ERROR(8002,"支付订单为空！"),
    PAY_CHANNEL_NULL_ERROR(8003,"支付渠道为空！"),
    PAY_NOT_INTEGRAL_ERROR(8004,"该订单还未支付积分！"),
    PAY_STATE_ERROR(8005,"订单已支付过！"),
    PAY_TIMEOUT_ERROR(8006,"订单支付超时！"),

    /**
     * 支付回调错误
     */
    PAY_HOOKS_SIGNATURE_ERROR(9000,"支付签名验证失败！"),
    PAY_HOOKS_REPEAT_ERROR(9001,"重复支付订单！"),
    PAY_HOOKS_AMOUNT_ERROR(9002,"支付金额与订单金额不符！"),
    PAY_HOOKS_ORDER_ERROR(9003,"找不到支付订单！");
    private Integer code;

    private String msg;

    ResultErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}