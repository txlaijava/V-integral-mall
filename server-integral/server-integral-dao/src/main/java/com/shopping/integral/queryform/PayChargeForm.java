package com.shopping.integral.queryform;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 类描述：PayChargeForm 封装表单提交参数
 *
 * @author：GuoFuJun
 * @date：2018年07月11日 10:02:26.
 */
@Data
public class PayChargeForm{

    /**
     * orderId : 商户订单号
     */
    private String orderId;

    /**
     * amount : 支付金额。
     */
    private BigDecimal amount;

    /**
     * channel : 支付渠道
     */
    private String channel;

    /**
     * subject : 商品标题，该参数最长为 32 个 Unicode 字符。银联全渠道限制在 32 个字节；支付宝部分渠道不支持特殊字符。
     */
    private String subject;

    /**
     * externToken : 仅适用于支付宝 mapi 接口。开放平台返回的包含账户信息的 token（授权令牌，商户在一定时间内对支付宝某些服务的访问权限）。
     * 通过授权登录后获取的  alipay_open_id ，作为该参数的  value ，登录授权账户即会为支付账户，32 位字符串。
     */
    private String externToken;

    /**
     * successUrl : 支付成功的回调地址。
     */
    private String successUrl;

    /**
     * cancelUrl : 支付取消的回调地址。
     */
    private String cancelUrl;

    /**
     * productId : 商品 ID，1-32 位字符串。此 id 为二维码中包含的商品 ID，商户自行维护。
     */
    private String productId;


    private String rsaPrivatePath;

}
