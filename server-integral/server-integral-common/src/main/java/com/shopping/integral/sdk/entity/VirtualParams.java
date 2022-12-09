package com.shopping.integral.sdk.entity;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangyudong
 */
public class VirtualParams {

    private String appKey;//应用标示appKey
    private Date timestamp;//时间戳,当前时间毫秒值
    private Long credits;//积分
    private String supplierBizId = "";//充值流水号
    private String uid = "";//用户id
    private String description = "";//商品描述
    private String orderNum = "";//兑吧订单号
    private String account = "";//虚拟商品充值账号，非必须参数
    private String params = ""; //虚拟商品标示符

    public VirtualParams() {

    }

    public VirtualParams(String appKey, Date timestamp, Long credits, String uid, String description, String orderNum,String params) {
        this.appKey = appKey;
        this.timestamp = timestamp;
        this.credits = credits;
        this.uid = uid;
        this.description = description;
        this.orderNum = orderNum;
        this.params = params;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> toRequestMap(String appSecret) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("description", description);
        map.put("uid", uid);
        map.put("appKey", appKey);
        map.put("supplierBizId", supplierBizId);
        map.put("appSecret", appSecret);
        map.put("timestamp", timestamp.getTime() + "");
        map.put("orderNum", orderNum);
        map.put("params", params);
        return map;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSupplierBizId() {
        return supplierBizId;
    }

    public void setSupplierBizId(String supplierBizId) {
        this.supplierBizId = supplierBizId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }


}

