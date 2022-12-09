package com.shopping.integral.dao.model;

import com.shopping.base.domain.user.UserMenu;
import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_menu")
public class IntegralMenu extends IdEntity {

    /**
     * name : 菜单名称
     */
    private String name;

    /**
     * URL : 跳转路径
     */
    private String URL;

    /**
     * sorting ： 排序
     */
    private int sorting;

    /**
     * 头像标识
     */
    private String headLogo;

    /**
     * menu_type : 菜单类型 0后台 1商户
     */
    private int menuType;

    /**
     * menu : 上级菜单id 父级
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMenu menu;

    /**
     * grandpa : 上上级菜单id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grandpa_id")
    private UserMenu grandpa;

    /**
     * sub_button : 下级
     */
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserMenu> subButton;

    /**
     * 下下级
     */
    @OneToMany(mappedBy = "grandpa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserMenu> suen;

}
