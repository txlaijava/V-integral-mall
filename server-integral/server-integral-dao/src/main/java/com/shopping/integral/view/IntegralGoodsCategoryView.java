package com.shopping.integral.view;

import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralGoodsCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class IntegralGoodsCategoryView extends BeanView<IntegralGoodsCategory> {

    private Long id;

    /**
     * acApply : 所属应用
     */
    private IntegralAcApplyView acApply;

    /**
     * categoryName : 分类名称
     */
    private String categoryName;

    /**
     * categoryIcon : 分类图标
     */
    private IntegralAccessoryView categoryIcon;

    /**
     * categoryBannerShow : 是否开启类别banner显示
     */
    private Boolean categoryBannerShow;

    /**
     * categoryBanner : 分类 banner 图片
     */
    private IntegralAccessoryView categoryBanner;
}
