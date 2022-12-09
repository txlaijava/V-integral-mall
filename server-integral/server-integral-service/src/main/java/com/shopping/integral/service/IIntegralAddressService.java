package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAddress;
import com.shopping.integral.queryform.IntegralAddressForm;


/**
* 类描述：IntegralAddressService 接口
*
* @author：GuoFuJun
* @date：2018年07月10日 17:40:27.
*/
public interface IIntegralAddressService extends IBaseService<IntegralAddress,Long> {

    /**
     * 获取收货地址
     * @param userId    收货地址用户编号
     * @param addressId 收货地址编号
     * (用户编号 或 收货地址必传一个)
     * @return
     */
    BaseResult getIntegralAddress(Long userId,Long addressId);

    /**
     * 修改、保存收货地址
     * @param userId    收货地址用户编号
     * @param addressId 收货地址编号
     * @param integralAddressForm    修改的内容
     * (用户编号 或 收货地址必传一个)
     * @return
     */
    BaseResult saveIntegralAddress(Long userId, Long addressId, IntegralAddressForm integralAddressForm);
}