package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import com.shopping.base.foundation.util.Base64;
import com.shopping.base.utils.CommUtils;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户应用
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_ac_apply")
@ToString(exclude = {"applyIconAcc", "ac", "theme"})
public class IntegralAcApply extends IdEntity {

    /**
     * appKey : 应用Key
     */
    private String appKey;

    /**
     * appSecre : 应用Secret
     */
    private String appSecret;

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
     * themeName : 主题颜色
     */
    private String themeColor = "#D0021B";

    /**
     * themeBgColor : 主题RGBA颜色
     */
    private String themeColorRgba = "208,2,27,1";

    /**
     * 上门自提地址
     */
    private String takeSelfAddr;

    /**
     * 微信小程序Appid
     */
    private String wxappAppid;

    /**
     * 微信支付Uid
     */
    private String wxPayUid;

    /**
     * 微信支付密钥
     */
    private String wxPaySecretKey;

    /**
     * applyIconAcc : 应用图标
     */
    @OneToOne(cascade = {javax.persistence.CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private IntegralAccessory applyIconAcc;

    /**
     * account : 账户对象
     */
    @ManyToOne(cascade = {javax.persistence.CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private IntegralAccount ac;

    /**
     * theme : 应用主题
     */
    @OneToOne(cascade = {javax.persistence.CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private IntegralTheme theme;

    public IntegralAcApply() {

    }

    public IntegralAcApply(BigDecimal integralExchangeRate, String integralUnit, String applyName, String shoppingName,
                           int applyType, IntegralAccessory applyIconAcc, IntegralAccount account, IntegralTheme theme) {
        String appKey = Base64.encode((CommUtils.null2String(12) + CommUtils.formatLongDate(new Date())).getBytes());
        String appSecret = Base64.encode(appKey.getBytes());
        this.addTime = new Date();
        this.deleteStatus = false;
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.integralExchangeRate = integralExchangeRate;
        this.integralUnit = integralUnit;
        this.applyName = applyName;
        this.shoppingName = shoppingName;
        this.applyType = applyType;
        this.applyIconAcc = applyIconAcc;
        this.ac = account;
        this.theme = theme;
    }
}
