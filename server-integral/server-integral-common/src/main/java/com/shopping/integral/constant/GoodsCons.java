package com.shopping.integral.constant;

/**
 * 类描述：商品常理
 *
 * @author：GuoFuJun
 * @date：2018年7月11日 上午16:45:46
 */
public class GoodsCons {

    // <editor-fold desc="商品类型常量（实物、虚拟商品）">
    /**
     * MATERIAL_OBJECT : 实物
     */
    public static final int MATERIAL_OBJECT = 1;

    /**
     * VIRTUAL_GOODS : 虚拟商品
     */
    public static final int VIRTUAL_GOODS = 2;
    // </editor-fold>

    // <editor-fold desc=" 商品状态（1：发布，2：未上架，3：下架）">
    /**
     * GOODS_PUBLISH : 发布
     */
    public static final int GOODS_PUBLISH = 1;

    /**
     * GOODS_WAREHOUSE : 未上架
     */
    public static final int GOODS_WAREHOUSE = 2;

    /**
     * GOODS_WAREHOUSE : 下架
     */
    public static final int GOODS_SOLD_OUT = 3;
    // </editor-fold>

    /**
     * GEAR_SINGLE : 单个档位
     */
    public static final Integer GEAR_SINGLE = 1;

    /**
     * GEAR_MORE : 多个档位
     */
    public static final Integer GEAR_MORE = 2;
}
