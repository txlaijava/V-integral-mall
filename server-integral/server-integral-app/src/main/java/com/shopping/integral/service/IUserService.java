package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.dao.model.IntegralUser;

public interface IUserService extends IBaseService<IntegralUser,Long> {

	/**
	 * 登陆接口
	 * @param appid
	 * @param appkey
	 * @param uid
	 * @param credits
	 * @param channel	渠道
	 * @param remark 备注
	 * @return
	 */
	IntegralUser autoLogin(long appid,String appkey,String uid,Long credits,String channel,String  openid,String remark);

}
