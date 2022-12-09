package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 积分商品规格
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_goods_spec")
public class IntegralGoodsSpec extends IdEntity {

    /**
     * goodsSpecCode : 规格标示符
     */
    private String goodsSpecCode;

    /**
     * goodsSpecName : 规格名称
     */
    private String goodsSpecName;

    /**
     * goodsSpecIntegralPrice : 规格积分价格
     */
    private BigDecimal goodsSpecIntegralPrice;

    /**
     * goodsSpecPrice : 规格现金价格
     */
    private BigDecimal goodsSpecPrice;

    /**
     * 商品对象
     */
    @OneToOne(cascade = {javax.persistence.CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private IntegralGoods goods;

}
