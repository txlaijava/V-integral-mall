package com.shopping.integral.dao.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangpenglin on 2018/8/23.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_account_income")
public class IntegralAccountIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Date addTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private IntegralAccount ac;

    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralAcApply acApply;

    /**
     * type:订单类型 1：提现申请  2：订单结算
     */
    private Integer type;

    /**
     * desc:描述
     */
    private String message;

    /**
     * orderId:平台订单号
     */
    @OneToOne(fetch = FetchType.LAZY)
    private IntegralOrder order;

    /**
     * devOrderId:开发者平台订单号
     */
    private String devOrderId;

    /**
     * amount:交易金额
     */
    private BigDecimal amount;

    /**
     * balance:交易后余额
     */
    private BigDecimal balance;

    public IntegralAccountIncome(){}

    public IntegralAccountIncome(Integer type,IntegralAcApply acApply,IntegralAccount ac,IntegralOrder order,String message,String devOrderId,BigDecimal amount,BigDecimal blance){
        this.addTime = new Date();
        this.type = type;
        this.acApply = acApply;
        this.ac = ac;
        this.order = order;
        this.message = message;
        this.devOrderId = devOrderId;
        this.amount = amount;
        this.balance = blance;
    }

}
