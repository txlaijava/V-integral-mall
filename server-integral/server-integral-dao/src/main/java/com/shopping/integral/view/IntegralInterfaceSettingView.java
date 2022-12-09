package com.shopping.integral.view;

import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralInterfaceSetting;
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
public class IntegralInterfaceSettingView  extends BeanView<IntegralInterfaceSetting> {

    private Long id;

    /**
     * interfaceName : 接口名称
     */
    private String interfaceName;

    /**
     * interfaceLink : 接口连接
     */
    private String interfaceLink;

    /**
     * remark : 备注
     */
    private String remark;
}
