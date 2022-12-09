package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 兑换订单商品
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_order_goods")
@ToString(exclude={"oid","orderGoods","orderGoodsMainPhoto"})
public class IntegralOrderGoods extends IdEntity {

    /**
     * orderGoodsType : 商品类型（1：实物，2：虚拟商品）
     */
    private int orderGoodsType;

    /**
     * orderGoodsName : 商品名称
     */
    private String orderGoodsName;

    /**
     * orderGoodsIntegralPrice : 商品积分价格
     */
    private BigDecimal orderGoodsIntegralPrice;

    /**
     * orderGoodsPrice : 商品现金价格
     */
    private BigDecimal orderGoodsPrice;

    /**
     * orderGoodsSpecInfo : 商品规格信息
     */
    private String orderGoodsSpecInfo;

    /**
     * oid : 订单
     */
    @OneToOne( fetch = FetchType.EAGER)
    private IntegralOrder order;

    /**
     * goods : 订单商品
     */
    @OneToOne( fetch = FetchType.EAGER)
    private IntegralGoods goods;

    /**
     * orderGoodsMainPhoto : 订单商品主图
     */
    @OneToOne(fetch = FetchType.EAGER)
    private IntegralAccessory orderGoodsIconPhoto;

    public IntegralOrderGoods(){

    }

    public IntegralOrderGoods(IntegralGoods integralGoods,IntegralOrder integralOrder,String orderGoodsSpecInfo){
        this.addTime = new Date();
        this.deleteStatus = false;
        this.orderGoodsType = integralGoods.getGoodsType();
        this.orderGoodsName = integralGoods.getGoodsName();
        this.orderGoodsIntegralPrice = integralGoods.getExchangeRules().getExchangeIntegralPrice();
        this.orderGoodsPrice = integralGoods.getExchangeRules().getExchangePrice();
        this.orderGoodsSpecInfo = orderGoodsSpecInfo;
        this.order = integralOrder;
        this.goods = integralGoods;
        this.orderGoodsIconPhoto = integralGoods.getGoodsIconPhoto();
    }
}
