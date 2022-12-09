package com.shopping.integral.dao.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * 积分用户表
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_user")
public class IntegralUser{

    private static final long serialVersionUID = 7060381435224185276L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Date add_time;

    /**
     * 应用id
     */
    private long ac_apply_id;
    /**
     * 应用key
     */
    private String app_key;

    private String uid;

    /**
     * 积分数量
     */
    private Long credits;

    private Date login_time;

    private int login_count;

    /**
     * 用户微信openid
     */
    private String openid;

    /**
     * 备注
     */
    private String remark;
}
