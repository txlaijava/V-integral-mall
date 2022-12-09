package com.shopping.integral.view;

import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralOrderGoods;
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
public class IntegralOrderGoodsView extends BeanView<IntegralOrderGoods> {

    /**
     * orderGoodsType : 商品类型（1：实物，2：虚拟商品）
     */
    private int orderGoodsType;

    /**
     * orderGoodsName : 商品名称
     */
    private String orderGoodsName;

    /**
     * orderGoodsIntegralPrice : 商品积分价格
     */
    private BigDecimal orderGoodsIntegralPrice;

    /**
     * orderGoodsPrice : 商品现金价格
     */
    private BigDecimal orderGoodsPrice;

    /**
     * orderGoodsSpecInfo : 商品规格信息
     */
    private String orderGoodsSpecInfo;

    /**
     * orderGoodsMainPhoto : 订单商品主图
     */
    private IntegralAccessoryView orderGoodsIconPhoto;

    /**
     * IntegralGoods : 商品信息
     */
    private IntegralGoodsView goods;
}
