package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * 账户接口设置
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_Interface_setting")
public class IntegralInterfaceSetting extends IdEntity {

    /**
     * interfaceName : 接口名称
     */
    private String interfaceName;

    /**
     * interfaceLink : 接口连接
     */
    private String interfaceLink;

    /**
     * remark : 备注
     */
    private String remark;

    /**
     * acApply : 所属应用
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private IntegralAcApply acApply;

    public IntegralInterfaceSetting(){

    }

    public IntegralInterfaceSetting(String interfaceName,IntegralAcApply acApply){
        this.addTime = new Date();
        this.deleteStatus = false;
        this.interfaceName = interfaceName;
        this.acApply = acApply;
    }
}
