package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralAddressDAO;
import com.shopping.integral.dao.IntegralUserDAO;
import com.shopping.integral.dao.model.IntegralAddress;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.queryform.IntegralAddressForm;
import com.shopping.integral.service.IIntegralAddressService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：IntegralAddressService 实现
 *
 * @author：GuoFuJun
 * @date：2018年07月10日 17:40:27.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j2
public class IntegralAddressServiceImpl extends BaseServiceImpl<IntegralAddress, Long> implements IIntegralAddressService {


    @Autowired
    IntegralAddressDAO integralAddressDAO;

    @Autowired
    IntegralUserDAO integralUserDAO;

    @Override
    public BaseResult getIntegralAddress(Long userId, Long addressId) {

        if (Utils.isEmpty(userId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_USER_ID_NULL_ERROR);
        }

        Map params = new HashMap(2);
        if(Utils.isNotEmpty(addressId)){
            params.put("id", addressId);
        }
        params.put("user.id", userId);
        IntegralAddress integralAddress = integralAddressDAO.findOneByMap(params);

        if (Utils.isNotEmpty(integralAddress)) {
            return BaseResult.SUCCESS("获取成功！", integralAddress);
        }

        return BaseResult.ERROR(ResultErrorCodeEnum.ADDRESS_NULL_ERROR);
    }

    @Override
    public BaseResult saveIntegralAddress(Long userId, Long addressId, IntegralAddressForm integralAddressForm) {

        if(Utils.isEmpty(integralAddressForm)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_NULL_ERROR);
        }

        if(Utils.isEmpty(integralAddressForm.getMobile())){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_ADDRESS_MOBILE_NULL_ERROR);
        }

        if(Utils.isEmpty(integralAddressForm.getArea())){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_ADDRESS_AREA_NULL_ERROR);
        }

        if(Utils.isEmpty(integralAddressForm.getAreaInfo())){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_ADDRESS_AREAINFO_NULL_ERROR);
        }

        if(Utils.isEmpty(integralAddressForm.getTrueName())){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_ADDRESS_TRUE_NAME_NULL_ERROR);
        }

        IntegralAddress integralAddress;

        // 收货地址编号不为空时为修改，为空时为添加
        if(Utils.isNotEmpty(addressId)){
            BaseResult baseResult = getIntegralAddress(userId,addressId);
            if(!baseResult.getSuccess()){
                return baseResult;
            }
            integralAddress = (IntegralAddress) baseResult.getData();
        }else{
            IntegralUser integralUser = integralUserDAO.findById(userId);
            integralAddress = new IntegralAddress(integralUser);
        }

        BeanUtils.copyProperties(integralAddressForm,integralAddress,"id");
        integralAddressDAO.save(integralAddress);

        return BaseResult.SUCCESS("保存成功！",integralAddress);
    }
}