package com.shopping.integral.dao.model;


import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_report_goods_dailydata")
public class IntegralReportGoodsDailydata {

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

    private String goods_name;

    private Long goods_id;

    //订单转化率
    private BigDecimal conversion_ratio;

    //消耗积分
    private int used_integral;

    //消耗金额
    private int used_money;

    //订单数
    private int order_count;

}
