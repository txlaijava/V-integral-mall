package com.shopping.integral.queryform;

import com.shopping.base.foundation.data.annotation.Condition;
import com.shopping.base.foundation.data.annotation.MatchMode;
import com.shopping.base.foundation.form.PaginationForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * IntegralAccountIncome 查询条件
 *
 * @author：GuoFuJun
 * @date：2018年7月2日 上午11:13:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralAccountIncomeQueryForm extends PaginationForm {

    /**
     * 开始时间
     */
    @Condition(path="addTime", match= MatchMode.GE, type= Date.class,minTime = true)
    private Date beginAddTime;

    /**
     * 结束时间
     */
    @Condition(path="addTime", match= MatchMode.LE, type= Date.class,maxTime = true)
    private Date endAddTime;

    @Condition(path="order.oid", match= MatchMode.EQ, type= String.class)
    private String oid;


    @Condition(path="type", match= MatchMode.EQ, type= Integer.class)
    private Integer type;

    @Condition(path="acApply.id", match= MatchMode.EQ, type= Long.class)
    private Long acApplyId;

    @Condition(path="ac.id", match= MatchMode.EQ, type= Long.class)
    private Long acId;

}
