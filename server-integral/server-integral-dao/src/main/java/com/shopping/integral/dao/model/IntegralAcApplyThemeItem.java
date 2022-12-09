package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * 应用主题下的模板
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_ac_apply_theme_item")
public class IntegralAcApplyThemeItem extends IdEntity{

    /**
     * acApply : 所属应用
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private IntegralAcApply acApply;

    /**
     * themeItem : 主题模板
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private IntegralThemeItem themeItem;

    /**
     * sequence : 排序
     */
    private int sequence;

    public IntegralAcApplyThemeItem(){

    }

    public IntegralAcApplyThemeItem(IntegralAcApply acApply,IntegralThemeItem themeItem,int sequence){
        this.addTime = new Date();
        this.deleteStatus = false;
        this.acApply = acApply;
        this.themeItem = themeItem;
        this.sequence = sequence;
    }
}
