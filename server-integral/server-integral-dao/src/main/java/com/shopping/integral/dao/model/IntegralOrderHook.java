package com.shopping.integral.dao.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单消息推送表
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_order_hook")
public class IntegralOrderHook {

    private static final long serialVersionUID = 7060381435224185276L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Date add_time;

    /**
     * 开发者订单号
     */
    private String biz_id;

    /**
     * 积分平台订单号
     */
    private String oid;

    /**
     * 应用key
     */
    private String app_key;

    /**
     * 通知地址
     */
    private String hook_url;

    /**
     * 剩余通知次数
     */
    private int hook_num;

    /**
     * 推送状态  0 处理中  1 推送成功 2 推送失败  3 生成推送
     */
    private int hook_status;

    /**
     * 通知时间
     * 通知时间间隔为：2m、10m、20m、1h、2h、6h、15h。
     */
    private String hook_times;
}
