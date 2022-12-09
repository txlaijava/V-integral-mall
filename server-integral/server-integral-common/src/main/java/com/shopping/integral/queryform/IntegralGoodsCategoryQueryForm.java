package com.shopping.integral.queryform;

import com.shopping.base.foundation.data.annotation.Condition;
import com.shopping.base.foundation.data.annotation.MatchMode;
import com.shopping.base.foundation.form.PaginationForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IntegralGoodsCategory 查询条件
 *
 * @author：GuoFuJun
 * @date：2018年8月8日 上午9:13:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralGoodsCategoryQueryForm extends PaginationForm {

    /**
     * 查询mobile等于
     */
    @Condition(path = "acApply.id", match = MatchMode.EQ, type = Long.class)
    private Long acApplyId;
}
