package com.shopping.integral.form;

import lombok.Data;

/**
 * 类描述：IntegralGoodsCategoryForm 封装表单提交参数
 *
 * @author：GuoFuJun
 * @date：2018年08月07日 16:01:49.
 */
@Data
public class IntegralGoodsCategoryForm {

    private Long id;

    /**
     * acApplyId : 所属应用
     */
    private Long acApplyId;

    /**
     * categoryName : 分类名称
     */
    private String categoryName;

    /**
     * categoryIconId : 分类图标
     */
    private Long categoryIconId;

    /**
     * categoryBannerShow : 是否开启类别banner显示
     */
    private Boolean categoryBannerShow;

    /**
     * categoryBannerId : 分类 banner 图片
     */
    private Long categoryBannerId;
}
