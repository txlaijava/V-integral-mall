package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 应用主题下的模板数据
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_ac_apply_theme_item_data")
@ToString(exclude={"themeItem","acApply","itemDataGoods","goodsSpecs","goodsIconPhoto"})
public class IntegralAcApplyThemeItemData extends IdEntity{

    /**
     * themeItem : 主题模板
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralThemeItem themeItem;

    /**
     * acApply : 所属应用
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralAcApply acApply;

    /**
     * itemDataGoods : 模板商品
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralGoods itemDataGoods;

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
