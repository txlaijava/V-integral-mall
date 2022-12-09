package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户收货地址
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_address")
@ToString(exclude = {"user"})
public class IntegralAddress extends IdEntity {

    /**
     * user : 用户对象
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralUser user;

    /**
     * mobile : 联系电话
     */
    private String mobile;

    /**
     * area : 收货地址
     */
    private String area;

    /**
     * areaInfo : 详细收货地址
     */
    private String areaInfo;

    /**
     * trueName : 收货人姓名
     */
    private String trueName;


    public IntegralAddress() {

    }

    public IntegralAddress(IntegralUser user) {
        this.addTime = new Date();
        this.deleteStatus = false;
        this.user = user;
    }
}
