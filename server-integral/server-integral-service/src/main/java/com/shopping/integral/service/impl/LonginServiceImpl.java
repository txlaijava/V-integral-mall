package com.shopping.integral.service.impl;

import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Md5Encrypt;
import com.shopping.base.utils.Utils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.constant.RedisCons;
import com.shopping.integral.dao.IntegralAccountDAO;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.service.ILonginService;
import com.shopping.integral.token.authorization.manager.JwtTokenUtils;
import com.shopping.integral.token.config.Constants;
import com.shopping.integral.util.BeanUtil;
import com.shopping.integral.view.IntegralAccountView;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * 类描述：IntegralOrderService 登录，登出业务处理实现类
 * 类编号: Code 101
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class LonginServiceImpl implements ILonginService {

    @Autowired
    IntegralAccountDAO integralAccountDAO;

    @Autowired
    RedisService redisService;

    @Override
    public BaseResult getlLoginValidation(String acMail, String acPassword) {
        if(Utils.isEmpty(acMail)){
            return BaseResult.ERROR(101,"登录邮箱不能为空");
        }
        if(Utils.isEmpty(acPassword)){
            return BaseResult.ERROR(102,"登录密码不能为空");
        }

        IntegralAccount integralAccount = integralAccountDAO.findByPropertyOne("acMail", acMail);
        if(Utils.isEmpty(integralAccount)){
            return BaseResult.ERROR(103,"未找到对应用户");
        }
        String md5AcPassword = Md5Encrypt.md5(acPassword);
        if(!StringUtils.equalsIgnoreCase(md5AcPassword,integralAccount.getAcPassword())){
            return BaseResult.ERROR(104,"输入的密码有误！");
        }
        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        long refreshPeriodTime = Constants.JWT_TTL;
        String Token = JwtTokenUtils.createJWT(CommUtils.null2String(integralAccount.getId()), integralAccount.getAcMail(), Constants.JWT_TTL >> 1);
        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
        redisService.set(RedisCons.JWT_SESSION_PLATFORM + integralAccount.getId(), Token, refreshPeriodTime);

        IntegralAccountView view = BeanViewUtils.getView(integralAccount,IntegralAccountView.class);
        if(Utils.isNotEmpty(integralAccount.getAcParent())){
            view.setAcApplyId(integralAccount.getAcParent().getAcApply().getId());
            view.setAcApplyThemeId(integralAccount.getAcParent().getAcApply().getTheme().getId());
        }else{
            view.setAcApplyId(integralAccount.getAcApply().getId());
            view.setAcApplyThemeId(integralAccount.getAcApply().getTheme().getId());
        }
        try {
            redisService.hmset(RedisCons.INTEGRAL_PLATFORM_ACCOUNT + CommUtils.null2String(integralAccount.getId()), BeanUtil.transBean2Map(integralAccount), Constants.JWT_TTL);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return BaseResult.SUCCESS(Token,"登录成功！",view);
    }
}
