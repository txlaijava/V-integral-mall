package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 积分商品兑换规则
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_exchange_rules")
public class IntegralExchangeRules extends IdEntity {

    /**
     * goodsPrice : 市面价值
     */
    private BigDecimal goodsPrice;
    /**
     * goodsPriceUnit : 市面价值单位 (0：元，1：积分)
     */
    private int goodsPriceUnit;

    /**
     * exchangeIntegralPrice : 兑换价格：积分
     */
    private BigDecimal exchangeIntegralPrice;

    /**
     * exchangePrice : 兑换价格：现金
     */
    private BigDecimal exchangePrice;

    /**
     * exchangePriceType : 兑换价格类型（1：纯积分，2：积分加现金）
     */
    private int exchangePriceType;

    /**
     * freightPrice : 运费价格
     */
    private BigDecimal freightPrice = new BigDecimal(0);

    /**
     * charge_price : 平台收费价格
     */
    private BigDecimal chargePrice;

    /**
     * freightWay : 运费价格计算方式（0：包邮，1：固定运费，2：运费模板）
     */
    private int freightWay;

    /**
     * userExchangeLimit : 用户兑换限制
     */
    private Integer userExchangeLimit;

    /**
     * userExchangeLimitUnit : 用户兑换限制单位（0：永远，1：每天，2：兑换周期（次/N天））
     */
    private Integer userExchangeLimitUnit;

    /**
     * additionalExchangeLimit : 额外兑换限制（0：关闭额外限制，1：开启额外限制）
     */
    private Boolean additionalExchangeLimit;

    /**
     * dailyExchangeLimit : 每日兑换限制（additionalExchangeLimit）为开启的试生效
     */
    private Integer dailyExchangeLimit;

    /**
     * exchangeStartTime : 兑换日期限制，开始时间（additionalExchangeLimit）为开启的试生效
     */
    private Date exchangeStartTime;

    /**
     * exchangeEndTime : 兑换日期限制，结束时间（additionalExchangeLimit）为开启的试生效
     */
    private Date exchangeEndTime;

    /**
     * 积分商品外键
     */
    /*@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "exchangeRules", cascade = {CascadeType.REFRESH})
    private IntegralGoods goods;*/

    private Long goodsId;


    public IntegralExchangeRules(){

    }

    public IntegralExchangeRules(BigDecimal goodsPrice,int goodsPriceUnit,BigDecimal exchangeIntegralPrice,BigDecimal exchangePrice,int exchangePriceType,BigDecimal freightPrice,
                                 int freightWay,int userExchangeLimit,int userExchangeLimitUnit,Boolean additionalExchangeLimit,int dailyExchangeLimit,Date exchangeStartTime,
                                 Date exchangeEndTime){
        this.addTime = new Date();
        this.deleteStatus = false;
        this.goodsPrice = goodsPrice;
        this.goodsPriceUnit = goodsPriceUnit;
        this.exchangeIntegralPrice = exchangeIntegralPrice;
        this.exchangePrice = exchangePrice;
        this.exchangePriceType = exchangePriceType;
        this.freightPrice = freightPrice;
        this.freightWay = freightWay;
        this.userExchangeLimit = userExchangeLimit;
        this.userExchangeLimitUnit = userExchangeLimitUnit;
        this.additionalExchangeLimit = additionalExchangeLimit;
        this.dailyExchangeLimit = dailyExchangeLimit;
        this.exchangeStartTime = exchangeStartTime;
        this.exchangeEndTime = exchangeEndTime;
    }
}
