package com.shopping.integral.view;

import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralAccessory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类描述：视图使用的实体
 *
 * @EqualsAndHashCode :此注解会生成equals(Object other) 和 hashCode()方法。
 *              参考 http://blog.csdn.net/zhanlanmg/article/details/50392266
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralAccessoryView extends BeanView<IntegralAccessory> {


    private Long id;
    /**
     * name : 图片名称
     */
    private String name;

    /**
     * path : 图片路径
     */
    private String path;

    /**
     * ext : 图片类型
     */
    private String ext;
}
