package com.shopping.integral.queryform;

import com.shopping.base.foundation.data.annotation.Condition;
import com.shopping.base.foundation.data.annotation.MatchMode;
import com.shopping.base.foundation.form.PaginationForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * IntegralGoods 查询条件
 *
 * @author：GuoFuJun
 * @date：2018年7月2日 上午11:13:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralGoodsQueryForm extends PaginationForm {

    @Condition(path="deleteStatus", match= MatchMode.EQ, type= Boolean.class)
    private Boolean deleteStatus;

    /**
     * 查询 mobile like
     */
    @Condition(path="goodsName", match= MatchMode.CONTAINS, type= String.class)
    private String goodsName;

    @Condition(path="goodsType", match= MatchMode.EQ, type= Integer.class)
    private Integer goodsType;

    @Condition(path="goodsState", match= MatchMode.EQ, type= Integer.class)
    private Integer goodsState;

    @Condition(path="isSend", match= MatchMode.EQ, type= Boolean.class)
    private Boolean isSend;

    @Condition(path="exchangeRules.exchangePriceType", match= MatchMode.EQ, type= Integer.class)
    private Integer exchangePriceType;

    @Condition(path="category.id", match= MatchMode.EQ, type= Long.class)
    private Long categoryId;

    @Condition(path="exchangeRules.exchangeIntegralPrice", match= MatchMode.GE, type= BigDecimal.class)
    private BigDecimal startExchangeIntegralPrice;

    @Condition(path="exchangeRules.exchangeIntegralPrice", match= MatchMode.LE, type= BigDecimal.class)
    private BigDecimal endExchangeIntegralPrice;

    /**
     * 查询mobile等于1
     */
    @Condition(path="acApply.id", match= MatchMode.EQ, type= Long.class)
    private Long acApplyId;

    public IntegralGoodsQueryForm(){
        this.setOrderBy("top desc,topTime desc,sequence,addTime desc");
    }
}
