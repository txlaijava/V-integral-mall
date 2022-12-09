package com.shopping.integral.view;

import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralAcApplyThemeItemData;
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
public class IntegralAcApplyThemeItemDataView extends BeanView<IntegralAcApplyThemeItemData> {

    /**
     * id : 模块数据编号
     */
    private Long id;

    /**
     * itemDataGoods : 商品对象
     */
    private IntegralGoodsView itemDataGoods;

    /**
     * itemDataType : 类型
     */
    private String itemDataType;

    /**
     * itemDateName : 标题，描述
     */
    private String itemDataName;

    /**
     * itemDataLike : 链接
     */
    private String itemDataLike;

    /**
     * itemDataImg : 图片
     */
    private String itemDataImg;

    /**
     * itemDataValue : 功能数据
     */
    private String itemDataValue;

    /**
     * sequence : 排序
     */
    private int sequence;

}
