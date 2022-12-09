package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 积分商品
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_goods")
@ToString(exclude={"exchangeRules","goodsMainPhoto","acApply","goodsSpecs","goodsIconPhoto"})
public class IntegralGoods extends IdEntity{

    /**
     * goodsState : 商品状态（1：发布，2：未上架，3：下架）
     */
    private int goodsState;

    /**
     * goodsName : 积分商品名称
     */
    private String goodsName;

    /**
     * goodsCode : 商品代号
     */
    private String goodsCode;

    /**
     * goodsInventory : 剩余库存
     */
    private int goodsInventory = 0;

    /**
     * goodsType : 商品类型（1：实物，2：虚拟商品）
     */
    private int goodsType;

    /**
     * 是否支持上门自提(0:不支持，1：支持)
     */
    private Boolean isSend;

    /**
     * autoLowerShelvesTime : 自动下架时间
     */
    private Date autoLowerShelvesTime;

    /**
     * audit_status 审核开启状态
     */
    private Boolean auditStatus;

    /**
     * goodsDetails : 商品详情说明
     */
    @Lob
    @Column(columnDefinition = "LongText")
    private String goodsDetails;

    /**
     * stayDeliveryTxt : 待发货文案
     */
    @Lob
    @Column(columnDefinition = "LongText")
    private String stayDeliveryTxt;

    /**
     * exchangeSuccessTxt : 兑换成功文案
     */
    @Lob
    @Column(columnDefinition = "LongText")
    private String exchangeSuccessTxt;

    /**
     * exchangeButTxt : 自定义兑换按钮文案
     */
    private String exchangeButTxt;

    /**
     * cornerMarkTxt : 角标文字
     */
    private String cornerMarkTxt;

    /**
     * cornerMarkBagColor : 角标背景色
     */
    private String cornerMarkBagColor;

    /**
     * 上门自提地址
     */
    private String takeSelfAddr;

    /**
     * sequence: 排序
     */
    private Integer sequence;

    /**
     * top: 置顶
     */
    private Boolean top;

    /**
     * topTime : 置顶时间
     */
    private Date topTime;

    /**
     * gearType: 档位类型
     */
    private Integer gearType;

    /**
     * gearValue: 档位值
     */
    private String gearValue;

    /**
     * category : 所属商品分类
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralGoodsCategory category;

    /**
     * acApply : 所属应用
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralAcApply acApply;

    /**
     * goodsMainPhoto : 商品图片（首页列表区使用的图片）
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralAccessory goodsMainPhoto;

    /**
     * goodsIconPhoto : 商品logo图片
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralAccessory goodsIconPhoto;

    /**
     * goodsPhotos : 商品详情图片中间表
     */
    @ManyToMany
    @JoinTable(name = "integral_goods_photo", joinColumns = {@javax.persistence.JoinColumn(name = "goods_id")}, inverseJoinColumns = {@javax.persistence.JoinColumn(name = "photo_id")})
    private List<IntegralAccessory> goodsPhotos = new ArrayList();


    /**
     * goodsSpecs : 商品规格
     */
    @OneToMany(mappedBy = "goods", cascade = { CascadeType.REMOVE },fetch=FetchType.EAGER)
    private List<IntegralGoodsSpec> goodsSpecs;

    /**
     * exchangeRules : 商品兑换规则
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne( fetch = FetchType.EAGER)
    private IntegralExchangeRules exchangeRules;

    public IntegralGoods(){

    }

    public IntegralGoods(String goodsName,String goodsCode,int goodsInventory,Date autoLowerShelvesTime,Boolean auditStatus,String goodsDetails, String stayDeliveryTxt,
                         String exchangeSuccessTxt,String exchangeButTxt,String cornerMarkTxt,String cornerMarkBagColor){
        this.addTime = new Date();
        this.deleteStatus = false;
        this.goodsName = goodsName;
        this.goodsCode = goodsCode;
        this.goodsInventory = goodsInventory;
        this.autoLowerShelvesTime = autoLowerShelvesTime;
        this.goodsDetails = goodsDetails;
        this.auditStatus = auditStatus;
        this.stayDeliveryTxt = stayDeliveryTxt;
        this.exchangeSuccessTxt = exchangeSuccessTxt;
        this.exchangeButTxt = exchangeButTxt;
        this.cornerMarkTxt = cornerMarkTxt;
        this.cornerMarkBagColor = cornerMarkBagColor;
    }


}
