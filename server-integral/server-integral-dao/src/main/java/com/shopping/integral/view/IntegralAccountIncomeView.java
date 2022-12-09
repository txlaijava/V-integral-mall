package com.shopping.integral.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralAccountIncome;
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
public class IntegralAccountIncomeView extends BeanView<IntegralAccountIncome> {

    public Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date addTime;

    public IntegralAccountView ac;

    public IntegralAcApplyView acApply;

    public Integer type;

    public String message;

    public IntegralOrderView order;

    public String devOrderId;

    public BigDecimal amount;

    public BigDecimal balance;
}
