package com.shopping.integral.form;

import com.shopping.integral.dao.model.IntegralThemeItem;
import lombok.Data;

/**
 * 编辑模板数据
 *
 * @author gfj
 * @date 2018/7/12.
 */
@Data
public class IntegralAcApplyThemeItemDataForm {

    /**
     * id : 模板数据编号
     */
    private Long id;

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
     * itemDataGoodsId : 商品编号
     */
    private Long itemDataGoodsId;

    /**
     * sequence : 排序
     */
    private int sequence;
}
