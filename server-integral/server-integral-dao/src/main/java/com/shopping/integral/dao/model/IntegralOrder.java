package com.shopping.integral.dao.model;

import com.shopping.base.utils.Utils;
import com.shopping.integral.constant.OrderCons;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 兑换订单
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_order")
@ToString(exclude={"acApply","addrArea","orderGoods"})
public class IntegralOrder {

    /**
     * oid : 订单号
     */
    @Id
    @Column(unique = true, nullable = false)
    private String oid;

    /**
     *  chargeId : 支付 charge_id (唯一)
     */
    private String chargeId;

    /**
     * bizId : 开发者平台订单号
     */
    private String bizId;

    /**
     * addTime : 兑换时间
     */
    private Date addTime;

    /**
     * payTime : 支付时间
     */
    private Date payTime;

    /**
     * payLock : 支付锁，防止支付是积分返还
     */
    private Boolean payLock;

    /**
     * channel:支付渠道(WXAPP: 微信小程序,APP:红商城app ,WX:微信H5)
     */
    private String channel;

    /**
     * completeTime : 订单完成时间
     */
    private Date completeTime;

    /**
     * deleteStatus : 删除状态（0：未删除，1：已删除）
     */
    private Boolean deleteStatus;

    /**
     * orderState : 订单状态 10:待付款,20:待审核,30:待发货,40:已发货,50:已完成,60:兑换失败,70:订单异常(常量类OrderCons)
     */
    private int orderState;

    /**
     * 订单类型：(1.配送订单,2.上门自提订单)
     */
    private int orderStateType;

    // <editor-fold desc="当订单类型为上门自提，需要核销状态">
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
    // </editor-fold>

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
     * cancelReason：订单取消理由
     */
    private String cancelReason;

    /**
     * acApply : 所属应用
     */
    @OneToOne( fetch = FetchType.EAGER)
    private IntegralAcApply acApply;

    /**
     * orderGoodss : 订单商品
     */
    @OneToOne(mappedBy = "order")
    private IntegralOrderGoods orderGoods;

    /**
     * 备注
     */
    private String integralUserRemark;

    public IntegralOrder(){

    }

    public IntegralOrder(String oid,int orderState, BigDecimal orderTotalIntegralPrice, BigDecimal orderTotalPrice,
                         int orderGoodsCount,Long orderUserId,String devUserId,BigDecimal logisticsPrice,String leaveMessage,
                         IntegralAcApply acApply, IntegralAddress addrArea,int orderStateType,String channel,String integralUserRemark) {
        this.oid = oid;
        this.addTime = new Date();
        this.deleteStatus = false;
        this.orderState = orderState;
        this.orderTotalIntegralPrice = orderTotalIntegralPrice;
        this.orderGoodsCount = orderGoodsCount;
        this.orderUserId = orderUserId;
        this.devUserId = devUserId;
        // 上门自提订单不需要运费
        if(OrderCons.SEND_TYPE_RECEIVE == orderStateType){
            logisticsPrice = new BigDecimal(0);
        }
        // 订单总现金需要加上运费
        this.orderTotalPrice = (orderTotalPrice.add(logisticsPrice));
        this.logisticsPrice = logisticsPrice;
        this.leaveMessage = leaveMessage;
        this.acApply = acApply;
        // 送货地址不为空
        if(Utils.isNotEmpty(addrArea)){
            this.addrTelephone = addrArea.getMobile();
            this.addrUserName = addrArea.getTrueName();
            this.addrAreaInfo = addrArea.getArea() + addrArea.getAreaInfo();
        }
        this.orderStateType = orderStateType;
        this.channel = channel;
        this.integralUserRemark = integralUserRemark;
    }
}
