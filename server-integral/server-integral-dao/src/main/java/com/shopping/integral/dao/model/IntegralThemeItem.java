package com.shopping.integral.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 商城主题模块
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_theme_item")
@ToString(exclude={"theme"})
public class IntegralThemeItem extends IdEntity{

    /**
     * themeItemIocn : 模块图标
     */
    private String themeItemIcon;

    /**
     * themeItemName : 模块名称
     */
    private String themeItemName;

    /**
     * themeItemType : 模块类型
     */
    private String themeItemType;

    /**
     * themeItemUrl : 模块路径
     */
    private String themeItemUrl;

    /**
     * themeItemState : 模块状态
     */
    private String themeItemState;

    /**
     * sequence : 排序
     */
    private int sequence;

    /**
     * theme : 对应的主题
     */
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private IntegralTheme theme;
}
