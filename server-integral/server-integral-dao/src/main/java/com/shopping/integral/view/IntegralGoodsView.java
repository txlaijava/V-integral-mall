package com.shopping.integral.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralGoods;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class IntegralGoodsView extends BeanView<IntegralGoods> {

    public Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date addTime;

    public boolean deleteStatus;

    /**
     * goodsState : 商品状态（1：发布，2：未上架，3：下架）
     */
    private int goodsState;

    /**
     * goodsName : 积分商品名称
     */
    private String goodsName;

    /**
     * goodsCode : 商品代号
     */
    private String goodsCode;

    /**
     * goodsInventory : 剩余库存
     */
    private int goodsInventory = 0;

    /**
     * goodsType : 商品类型（1：实物，2：虚拟商品）
     */
    private int goodsType;

    /**
     * 是否支持上门自提(0:不支持，1：支持)
     */
    private Boolean isSend;

    /**
     * autoLowerShelvesTime : 自动下架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date autoLowerShelvesTime;
    /**
     * audit_status 审核开启状态
     */
    private Boolean auditStatus;

    /**
     * goodsDetails : 商品详情说明
     */
    private String goodsDetails;

    /**
     * stayDeliveryTxt : 待发货文案
     */
    private String stayDeliveryTxt;

    /**
     * exchangeSuccessTxt : 兑换成功文案
     */
    private String exchangeSuccessTxt;

    /**
     * exchangeButTxt : 自定义兑换按钮文案
     */
    private String exchangeButTxt;

    /**
     * cornerMarkTxt : 角标文字
     */
    private String cornerMarkTxt;

    /**
     * cornerMarkBagColor : 角标背景色
     */
    private String cornerMarkBagColor;

    /**
     * 上门自提地址
     */
    private String takeSelfAddr;

    /**
     * sequence: 排序
     */
    private Integer sequence;

    /**
     * top: 置顶
     */
    private Boolean top;

    /**
     * topTime : 置顶时间
     */
    private Date topTime;

    /**
     * gearType: 档位类型
     */
    private Integer gearType;

    /**
     * gearValue: 档位值
     */
    private String gearValue;

    /**
     * exchangeRules : 兑换规则
     */
    private IntegralExchangeRulesView exchangeRules;

    /**
     * goodsMainPhoto : 商品主图
     */
    private IntegralAccessoryView goodsMainPhoto;

    /**
     * goodsIconPhoto : 商品图标
     */
    private IntegralAccessoryView goodsIconPhoto;

    /**
     * goodsPhotos : 商品详情图
     */
    private List<IntegralAccessoryView> goodsPhotos = new ArrayList();

    /**
     * category : 商品类别
     */
    private IntegralGoodsCategoryView category;

    //private List<IntegralGoodsSpec> goodsSpecs;
}
