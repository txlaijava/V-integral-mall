package com.shopping.integral.queryform;

import lombok.Data;

/**
 * 类描述：IntegralAddressForm 封装表单提交参数
 *
 * @author：GuoFuJun
 * @date：2018年07月11日 10:02:26.
 */
@Data
public class IntegralOrderAddForm {

    /**
     * goodsId :  商品编号
     */
    private Long goodsId;

    /**
     *  收货地址编号
     */
    private Long addressId;

    /**
     * leaveMessage : 用户留言
     */
    private String leaveMessage;

    /**
     * orderStateType : 订单类型：(1.配送订单,2.上门自提订单)
     */
    private int orderStateType;

    /**
     * orderGoodsCount : 订单商品数量
     */
    private int orderGoodsCount = 1;

    /**
     * gearCode 栏位标识符
     */
    private String gearCode;

    /**
     * channel 订单来源 WXAPP: 微信小程序,APP:红商城app ,WX:微信H5
     */
    private String channel;
}
