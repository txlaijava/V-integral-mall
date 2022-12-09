package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * 商城主题
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_theme")
@ToString(exclude={"themeItems","themePreviewImg"})
public class IntegralTheme extends IdEntity{

    /**
     * themeName : 主题名称
     */
    private String themeName;

    /**
     * isDefault : 是否默认主题
     */
    private Boolean isDefault;

    /**
     * themePreviewImg : 主图预览图片
     */
    @OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private IntegralAccessory themePreviewImg;

    /**
     * themeItems : 主题模块
     */
    @OneToMany(mappedBy = "theme", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<IntegralThemeItem> themeItems;
}
