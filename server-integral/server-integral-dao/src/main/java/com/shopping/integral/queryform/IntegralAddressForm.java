package com.shopping.integral.queryform;

import lombok.Data;

/**
 * 类描述：IntegralAddressForm 封装表单提交参数
 *
 * @author：GuoFuJun
 * @date：2018年07月11日 10:02:26.
 */
@Data
public class IntegralAddressForm {

    /**
     * id : 收货地址编号
     */
    private Long id;

    /**
     * mobile : 联系电话
     */
    private String mobile;

    /**
     * area : 收货地址
     */
    private String area;

    /**
     * areaInfo : 详细收货地址
     */
    private String areaInfo;

    /**
     * trueName : 收货人姓名
     */
    private String trueName;
}
