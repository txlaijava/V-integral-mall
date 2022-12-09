package com.shopping.integral.queryform;

import lombok.Data;


@Data
public class IntegralGoodsAddForm {

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
	 * 是否支持上门自提(0:不支持，1：支持)
	 */
	private Boolean isSend;

	/**
	 * autoLowerShelvesTime : 自动下架时间
	 */
	//private Date autoLowerShelvesTime;

	/**
	 * audit_status 审核开启状态
	 */
	private Boolean auditStatus;

	/**
	 * goodsDetails : 商品详情说明
	 */
	private String goodsDetails;

	/**
	 * stayDeliveryTxt : 待发货文案
	 */
	private String stayDeliveryTxt;

	/**
	 * exchangeSuccessTxt : 兑换成功文案
	 */
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


}
