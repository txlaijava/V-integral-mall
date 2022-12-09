package com.shopping.integral.util;

import com.shopping.base.utils.Utils;

import java.math.BigDecimal;

/**
 * BigDecimal 类型的工具类
 */
public class BigDecimalUtils {

    /**
     * BigDecimal 类型和 0 进行比较
     *
     * @param compare 需要比较的值
     * @return
     */
    public static Boolean compareToZero(BigDecimal compare) {
        Boolean isBol;
        if (Utils.isEmpty(compare)) {
            compare = new BigDecimal(0);
        }
        int cop = compare.compareTo(new BigDecimal(0));
        isBol = cop <= 0 ? false : true;
        return isBol;
    }
}
