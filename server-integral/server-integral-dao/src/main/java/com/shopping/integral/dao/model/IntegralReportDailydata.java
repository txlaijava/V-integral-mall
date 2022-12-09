package com.shopping.integral.dao.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 积分通知
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_report_dailydata")
public class IntegralReportDailydata{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Long id;

    //数据时间
    private Date report_date;

    //应用id
    @OneToOne(cascade = {javax.persistence.CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private IntegralAcApply app_apply;

    private int uv;

    private int pv;

    /**
     * 新增用户
     */
    private int add_user;

    //访客数
    private int visitor_user;

    //访客占比（登录积分商城但并未下单用户）
    private BigDecimal visitor_per;

    //人均积分
    private BigDecimal average_integral;

    //积分分布区间JSON数据
    private String dis_integral;

    //订单数
    private int order_count;

    //消耗积分
    private int used_integral;

    //消耗金额
    private BigDecimal used_money;

    //转化率
    private BigDecimal conversion_ratio;

}

