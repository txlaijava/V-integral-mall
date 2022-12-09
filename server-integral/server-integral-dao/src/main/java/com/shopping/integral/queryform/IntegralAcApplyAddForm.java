package com.shopping.integral.queryform;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 类描述：IntegralAddressForm 封装表单提交参数
 *
 * @author：GuoFuJun
 * @date：2018年07月11日 10:02:26.
 */
@Data
public class IntegralAcApplyAddForm {

    private Long id;

    /**
     * themeName : 主题颜色
     */
    private String themeColor;

    /**
     * themeBgColor : 主题RGBA颜色
     */
    private String themeColorRgba;

    /**
     * themeId : 应用主题编号
     */
    private Long themeId;

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
    private String integralInsufficientTxt;

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
     * applyIconId : 应用图标编号
     */
    private Long applyIconId;

    /**
     * 用于页面提交判断需要过滤拷贝的字段
     * 1、修改应用颜色
     */
    private int editorType;

    /**
     * 上门自提地址
     */
    private String takeSelfAddr;
}
