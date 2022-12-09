package com.shopping.integral.service;

import com.shopping.integral.base.BaseResult;
import com.shopping.integral.sdk.entity.CreditConsumeParams;
import com.shopping.integral.sdk.entity.CreditNotifyParams;
import com.shopping.integral.sdk.entity.VirtualParams;

public interface IHookService{

	/**
	 * 向开发者服务端发送扣除积分请求
	 * @param params 传值
	 * @return 请求结果
	 * @throws Exception
	 */
	BaseResult integralDeductionPost(CreditConsumeParams params) throws Exception;


	/**
	 * 虚拟商品充值请求
 	 * @param params
	 * @return
	 * @throws Exception
	 */
	BaseResult integralCouponPost(VirtualParams params) throws Exception;

	/**
	 * 向开发者服务器发送加积分请求
	 * @param params
	 * @return
	 * @throws Exception
	 */
	BaseResult integralAddPost(CreditConsumeParams params) throws Exception;

	/**
	 * 向开发者服务器发送订单通知信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	BaseResult integralHook(CreditNotifyParams params) throws Exception;

}
