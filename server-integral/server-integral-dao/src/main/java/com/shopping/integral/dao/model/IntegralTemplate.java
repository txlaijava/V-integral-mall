package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 菜单
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_template")
public class IntegralTemplate extends IdEntity {

    private String info;

    /**
     * 模板类型
     */
    private String type;

    /**
     * title : 模板标题
     */
    private String title;

    @Lob
    @Column(columnDefinition = "LongText")
    private String content;
    private String mark;

    /**
     * open : 打开模板
     */
    private boolean open;

    /**
     * codeId : 短信平台编号
     */
    private String codeId;
}
