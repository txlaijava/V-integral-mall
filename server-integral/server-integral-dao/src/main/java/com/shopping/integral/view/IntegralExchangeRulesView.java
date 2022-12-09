package com.shopping.integral.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralExchangeRules;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类描述：视图使用的实体
 *
 * @EqualsAndHashCode :此注解会生成equals(Object other) 和 hashCode()方法。
 *              参考 http://blog.csdn.net/zhanlanmg/article/details/50392266
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralExchangeRulesView extends BeanView<IntegralExchangeRules> {

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
     * charge_price : 平台收费价格
     */
    private BigDecimal chargePrice;

    /**
     * exchangePriceType : 兑换价格类型（1：纯积分，2：积分加现金）
     */
    private int exchangePriceType;

    /**
     * freightPrice : 运费价格
     */
    private BigDecimal freightPrice = new BigDecimal(0);

    /**
     * freightWay : 运费价格计算方式（0：包邮，1：固定运费，2：运费模板）
     */
    private int freightWay;

    /**
     * userExchangeLimit : 用户兑换限制
     */
    private Integer userExchangeLimit;

    /**
     * userExchangeLimitUnit : 用户兑换限制单位（0：永远，1：每天，2天）
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exchangeStartTime;

    /**
     * exchangeEndTime : 兑换日期限制，结束时间（additionalExchangeLimit）为开启的试生效
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exchangeEndTime;

    private Long goodsId;
}
