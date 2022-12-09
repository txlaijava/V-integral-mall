package com.shopping.integral.dao.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 类描述：商品分类报表
 *
 * @author：GuoFuJun
 * @date：2018年08月07日 16:01:49.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_goods_category")
public class IntegralGoodsCategory {

    private static final long serialVersionUID = 7060381435224185276L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    /**
     * acApply : 所属应用
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralAcApply acApply;

    /**
     * categoryName : '分类名称'
     */
    private String categoryName;

    /**
     * categoryIcon : 分类图标
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralAccessory categoryIcon;

    /**
     * categoryBannerShow : 是否开启类别banner显示
     */
    private Boolean categoryBannerShow;

    /**
     * categoryBanner : 分类 banner 图片
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralAccessory categoryBanner;
}
