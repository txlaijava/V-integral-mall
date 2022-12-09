package com.shopping.integral.convert;

import com.shopping.integral.constant.GoodsCons;

/**
 * 类描述：商品需要转换的字段
 *
 * @author：GuoFuJun
 * @date：2018年7月11日 上午17:18:46
 */
public class IntegralGoodsConvert {

    private IntegralGoodsConvert(){

    }

    /**
     * 把对应商品类型转换为中文描述
     * @param goodsType 类型
     * @return
     */
    public static String goodsTypeConvert(int goodsType){
        switch (goodsType){
            case GoodsCons.MATERIAL_OBJECT:
                return "实物";
            case GoodsCons.VIRTUAL_GOODS:
                return "虚拟商品";
            default:
        }
        return "未知类型";
    }

    /**
     * 把对应商品类型转换对应的状态码：（用于下单时调用开发者平台）
     * @param goodsType 类型
     * @return
     */
    public static String goodsTypeCodeConvert(int goodsType){
        switch (goodsType){
            case GoodsCons.MATERIAL_OBJECT:
                return "object";
            case GoodsCons.VIRTUAL_GOODS:
                return "virtual";
            default:
        }
        return "未知类型";
    }
}
