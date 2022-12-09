package com.shopping.integral.view;

import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralAcApply;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

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
public class IntegralAcApplyView extends BeanView<IntegralAcApply> {

    /**
     * id : 应用编号
     */
    private Long id;

    /**
     * appKey : 应用Key
     */
    private String appKey;

    /**
     * integralExchangeRate : 积分汇率
     */
    private BigDecimal integralExchangeRate;

    /**
     * integralUnit : 积分单位
     */
    private String integralUnit;

    /**
     * applyName : 应用名称
     */
    private String applyName;

    /**
     * integralDetailedLink : 积分明细链接
     */
    private String integralDetailedLink;

    /**
     * integralInsufficientTxt : 用户积分不足的时，详情兑换按钮文案。
     */
    private String integralInsufficientTxt = "去赚取更多积分";

    /**
     * integralInsufficientLink : 用户积分不足时，可以跳转至此行链接页面。
     */
    private String integralInsufficientLink;

    /**
     * shoppingName : 商城标题
     */
    private String shoppingName;

    /**
     * applyType : 应用类别
     */
    private int applyType;

    /**
     * themeName : 主题名称
     */
    private String themeColor;

    /**
     * themeBgColor : 主题颜色
     */
    private String themeColorRgba;

    /**
     * applyIconAcc : 应用图标
     */
    private IntegralAccessoryView applyIconAcc;

    /**
     * themeId 当前应用主题编号
     */
    private Long themeId;

    /**
     * 上门自提地址
     */
    private String takeSelfAddr;

}
