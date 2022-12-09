package com.shopping.integral.constant;

/**
 * 类描述：订单常量
 *
 * @author：GuoFuJun
 * @date：2018年7月11日 上午16:45:46
 */
public class OrderCons {

    // <editor-fold desc="订单状态常量（待付款、待审核、待发货、已发货、已完成、兑换失败）">
    /**
     * WAIT_PAY : 待付款
     */
    public static final int WAIT_PAY = 10;

    /**
     * WAIT_AUDIT : 待审核
     */
    public static final int WAIT_AUDIT = 20;

    /**
     * WAIT_SEND : 待发货,审核通过
     */
    public static final int WAIT_SEND = 30;

    /**
     * ALREADY_SEND : 已发货
     */
    public static final int ALREADY_SEND = 40;

    /**
     * 已完成
     */
    public static final int FINISH = 40;

    /**
     * OVER : 已完成
     */
    public static final int OVER = 50;

    /**
     * FAILURE : 兑换失败
     */
    public static final int FAILURE = 60;

    /**
     * FAILURE : 已取消
     */
    public static final int CANCEL = 70;

    /**
     * EXCEPTION : 订单异常
     */
    public static final int EXCEPTION = 80;

    /**
     * REJECT : 审核拒绝状态
     */
    public static final int REJECT = 90;

    /**
     * 组团中
     */
    public static final int WAIT_GROUP = 15;

    /**
     * 组团失败
     */
    public static final int GROUP_FAIL = 18;

    // </editor-fold>

    // <editor-fold desc="订单配送方式常量(物流发货、上门自提)">
    /**
     * SEND_TYPE_SEND : 送货上门
     */
    public static final int SEND_TYPE_SEND = 1;
    /**
     * SEND_TYPE_RECEIVE : 上门自提
     */
    public static final int SEND_TYPE_RECEIVE = 2;
    // </editor-fold>

    // 订单类型
    public static final int ORDER_TYPE_COMMON = 1;                            // 总订单/订单公共
    public static final int ORDER_TYPE_CHILD = 2;                             // 子订单
    public static final int ORDER_TYPE_REDSHOP = 3;                           // 红小店订单

    // 订单来源
    public static final int ORDER_FROM_WEIXIN = 1;                            // weixin
    public static final int ORDER_FROM_IOS = 2;                               // Ios
    public static final int ORDER_FROM_ANDROID = 3;                           // Android
    public static final int ORDER_FROM_PC = 4;                                // Pc
    public static final int ORDER_FROM_WXAPP = 5;                                // 微信小程序


    /**
     * 付款订单
     */
    public static final String ORDER_TYPE_PAYMENT = "4";

    public static final String ORDER_TYPE_HYG = "C";                          // 红云购订单
    public static final int ORDER_TYPE_AC_HB = 6;                             // 红宝充值

    // 订单分类
    public static final int ORDER_CATEGORY_COMMON = 1;                        // 常规订单
    public static final int ORDER_CATEGORY_COUPON = 2;                        // 团购券订单
    public static final int ORDER_CATEGORY_PACKAGE = 3;                       // 团购套餐订单
    public static final int ORDER_CATEGORY_RESTAURANT_DESPOKE = 4;            // 餐饮预约订单
    public static final int ORDER_CATEGORY_LEISURE = 5;                       // 休闲类订单
    public static final int ORDER_CATEGORY_TAKEAWAY = 6;                      // 外卖类订单
    public static final int ORDER_CATEGORY_REDPACKET = 7;                     // 红包订单
    public static final int ORDER_CATEGORY_RESERVATION = 8;                   // 订座订单
    public static final int ORDER_CATEGORY_SUBSCRIBE = 9;                     // 点餐订单
    public static final int ORDER_CATEGORY_Delta = 0;                         // 充值订单
    public static final int ORDER_CATEGORY_Delta_telephoneCharge = 11;        // 话费充值
    public static final int ORDER_CATEGORY_Delta_flowCharge = 12;             // 流量充值
    public static final int ORDER_CATEGORY_Ticket = 13;                       // 售票订单
    public static final int ORDER_CATEGORY_FIGHT_GROUP = 14;                  // 拼团订单
    public static final int ORDER_CATEGORY_MOVIE_TICKETS = 15;                // 电影票订单
    public static final int ORDER_CATEGORY_OFFLINE = 16;                      // 线下支付订单
    public static final int ORDER_CATEGORY_PARK_CAR = 17;                      // 停车订单

}
