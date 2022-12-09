package com.shopping.integral.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：视图使用的实体
 *
 * @EqualsAndHashCode :此注解会生成equals(Object other) 和 hashCode()方法。
 * 参考 http://blog.csdn.net/zhanlanmg/article/details/50392266
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralOrderView extends BeanView<IntegralOrder> {


    /**
     * oid : 订单号
     */
    private String oid;

    /**
     * bizId : 开发者平台订单号
     */
    private String bizId;

    /**
     * addTime : 兑换时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addTime;

    /**
     * payTime : 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date payTime;

    /**
     * completeTime : 订单完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date completeTime;

    /**
     * deleteStatus : 删除状态（0：未删除，1：已删除）
     */
    private Boolean deleteStatus;

    /**
     * orderState : 订单状态
     */
    private int orderState;

    /**
     * 订单类型：(1.配送订单,2.上门自提订单)
     */
    private int orderStateType;

    /**
     * orderTotalIntegralPrice : 订单总积分价格
     */
    private BigDecimal orderTotalIntegralPrice;

    /**
     * orderTotalPrice : 订单总现金价格
     */
    private BigDecimal orderTotalPrice;

    /**
     * orderGoodsCount : 订单商品数
     */
    private int orderGoodsCount;

    /**
     * orderUserId : 兑换用户编号
     */
    private Long orderUserId;

    /**
     * devUserId : 开发者平台用户编号
     */
    private String devUserId;

    /**
     * addrUserName : 订单收货人姓名
     */
    private String addrUserName;

    /**
     * addrTelephone : 订单收货联系电话
     */
    private String addrTelephone;

    /**
     * addrAreaInfo : 收货地址
     */
    private String addrAreaInfo;

    /**
     * logisticsCode : 物流单号
     */
    private String logisticsCode;

    /**
     * logisticsName : 物流公司名称
     */
    private String logisticsName;

    /**
     * logisticsPrice : 运费
     */
    private BigDecimal logisticsPrice = new BigDecimal(0);

    /**
     * leaveMessage : 用户留言
     */
    private String leaveMessage;

    /**
     * verifyCode : 核销码
     */
    private String verifyCode;

    /**
     * verifyStatus : 核销状态
     */
    private Boolean verifyStatus;

    /**
     * verifyDate : 核销时间
     */
    private Date verifyDate;

    /**
     * channel:支付渠道(WXAPP: 微信小程序,APP:红商城app ,WX:微信H5)
     */
    private String channel;

    /**
     * 备注
     */
    private String integralUserRemark;


    /**
     * orderGoodss : 订单商品
     */
    private IntegralOrderGoodsView orderGoods;

    /**
     * acApply:应用
     */
    private IntegralAcApplyView acApply;
}
