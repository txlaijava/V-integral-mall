package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.IIntegralUserService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 类描述：IntegralUserService 实现
*
* @author：GuoFuJun
* @date：2018年07月11日 18:07:33.
*/
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralUserServiceImpl extends BaseServiceImpl<IntegralUser,Long> implements IIntegralUserService {


    @Override
    public BaseResult updateUserCredits(Long userId, Long credits) {
        try{
            if(Utils.isEmpty(userId)){
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_USER_ID_NULL_ERROR);
            }

            if(Utils.isEmpty(credits)){
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_CREDITS_NULL_ERROR);
            }

            IntegralUser user = super.getObjById(userId);
            if(Utils.isEmpty(user)){
                return BaseResult.ERROR(ResultErrorCodeEnum.EXCHANGE_USER_NULL_ERROR);
            }
            user.setCredits(credits);
            super.update(user);
            return BaseResult.SUCCESS("更新成功！",user.getCredits());
        }catch (Exception e){
            log.error("更新用户积分异常！",e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }
}