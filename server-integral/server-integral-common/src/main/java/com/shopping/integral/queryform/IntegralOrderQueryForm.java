package com.shopping.integral.queryform;

import com.shopping.base.foundation.data.annotation.Condition;
import com.shopping.base.foundation.data.annotation.MatchMode;
import com.shopping.base.foundation.form.PaginationForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * IntegralOrderQueryForm 订单查询条件
 *
 * @author：GuoFuJun
 * @date：2018年7月2日 上午11:13:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralOrderQueryForm extends PaginationForm {

    /**
     * 兑换开始时间
     */
    @Condition(path="addTime", match= MatchMode.GE, type= Date.class,minTime = true)
    private Date beginAddTime;

    /**
     * 兑换结束时间
     */
    @Condition(path="addTime", match= MatchMode.LE, type= Date.class,maxTime = true)
    private Date endAddTime;

    /**
     * 完成开始时间
     */
    @Condition(path="completeTime", match= MatchMode.GE, type= Date.class,minTime = true)
    private Date beginCompleteTime;

    /**
     * 完成结束时间
     */
    @Condition(path="completeTime", match= MatchMode.LE, type= Date.class,maxTime = true)
    private Date endCompleteTime;

    @Condition(path="orderTotalIntegralPrice", match= MatchMode.GE, type= BigDecimal.class)
    private BigDecimal minPrice;

    @Condition(path="orderTotalIntegralPrice", match= MatchMode.LE, type= BigDecimal.class)
    private BigDecimal maxPrice;

    /**
     * oid : 订单号
     */
    @Condition(path="oid", match= MatchMode.EQ, type= String.class)
    private String oid;

    /**
     * bizId : 开发者平台订单号
     */
    @Condition(path="bizId", match= MatchMode.EQ, type= String.class)
    private String bizId;

    /**
     * 用户编号相等
     */
    @Condition(path="orderUserId", match= MatchMode.EQ, type= Long.class)
    private Long orderUserId;


    /**
     * 订单状态
     */
    @Condition(path="orderState", match= MatchMode.EQ, type= Integer.class)
    private Integer orderState;

    @Condition(path="addrTelephone", match= MatchMode.EQ, type= String.class)
    private String addrTelephone;

    /**
     * 商品类型
     */
    @Condition(path="orderGoods.orderGoodsType", match= MatchMode.EQ, type= Integer.class)
    private Integer orderGoodsType;

    @Condition(path="orderGoods.orderGoodsName", match= MatchMode.CONTAINS, type= String.class)
    private String orderGoodsName;

    /**
     * 用户编号相等
     */
    @Condition(path="deleteStatus", match= MatchMode.EQ, type= Boolean.class)
    private Boolean deleteStatus = false;

    @Condition(path="acApply.id", match= MatchMode.EQ, type= Long.class)
    private Long acApplyId;

    @Condition(path="channel", match= MatchMode.EQ, type= String.class)
    private String channel;

    @Condition(path="integralUserRemark", match= MatchMode.CONTAINS, type= String.class)
    private String integralUserRemark;

    private String export;
}
