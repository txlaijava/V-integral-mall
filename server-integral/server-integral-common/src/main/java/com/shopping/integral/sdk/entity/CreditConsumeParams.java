package com.shopping.integral.sdk.entity;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreditConsumeParams {
    /**
     * success : 正常返回
     */
    private Boolean success;
    private String msg;
    private String appKey;
    private Date timestamp;//时间戳
    private Long credits;//消耗积分数
    private String orderNum = "";//积分商城订单号
    private String description = "";
    private String itemCode = "";//商品编码，非必须参数
    /**
     * alipay(支付宝), qb(Q币), coupon(优惠券), object(实物), phonebill(话费),
     * phoneflow(流量), virtual(虚拟商品),
     * littleGame（小游戏）,singleLottery(单品抽奖),
     * hdtool(活动抽奖),manualLottery(手动开奖),
     * ngameLottery(新游戏) ,betActivity(押注活动)
     */
    private String type = "";//类型：QB,Phonebill,Alipay,Coupon  所有类型不区分大小写
    private Integer facePrice = 0;//面值，分为单位
    private Integer actualPrice = 0;//实际扣款，分为单位
    private String alipay = "";//已废弃，请使用params
    private String phone = "";//已废弃，请使用params
    private String qq = "";//已废弃，请使用params
    private String uid = "";
    private boolean waitAudit = false;//是否等待审核， 如果返回true，表示此订单需要审核，审核通过后才会继续下去。 如果返回false表示此订单无须审核，会直接继续兑换流程
    private String ip = "";//用户兑换时使用的ip地址，有可能为空
    private String params = "";//参数，根据不同的type，有不同的含义，参见在线文档
    private String redirect = "";//设置成一个外部传进来的参数，方便运营灵活配置
    private String transfer = "";
    private String openid = "";     //用户openid

    public CreditConsumeParams() {

    }

    ;

    public CreditConsumeParams(String appKey, String uid, Long credits, String type, String orderNum, String description) {
        this.appKey = appKey;
        this.timestamp = new Date();
        this.uid = uid;
        this.credits = credits;
        this.type = type;
        this.orderNum = orderNum;
        this.description = description;
    }

    public Map<String, String> toRequestMap(String appSecret) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("credits", credits + "");
        map.put("description", description);
        map.put("uid", uid);
        map.put("appKey", appKey);
        map.put("waitAudit", waitAudit + "");
        map.put("appSecret", appSecret);
        map.put("timestamp", timestamp.getTime() + "");
        map.put("orderNum", orderNum);
        map.put("type", type);
        map.put("facePrice", facePrice + "");
        map.put("actualPrice", actualPrice + "");
        map.put("ip", ip);
        map.put("params", params);
        map.put("redirect", redirect);
        putIfNotEmpty(map, "itemCode", itemCode);
        putIfNotEmpty(map, "transfer", transfer);
        putIfNotEmpty(map, "qq", qq);
        putIfNotEmpty(map, "alipay", alipay);
        putIfNotEmpty(map, "phone", phone);
        putIfNotEmpty(map, "openid", openid);

        return map;
    }

    private void putIfNotEmpty(Map<String, String> map, String key, String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        map.put(key, value);
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public boolean isWaitAudit() {
        return waitAudit;
    }

    public void setWaitAudit(boolean waitAudit) {
        this.waitAudit = waitAudit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFacePrice() {
        return facePrice;
    }

    public void setFacePrice(Integer facePrice) {
        this.facePrice = facePrice;
    }

    public Integer getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
