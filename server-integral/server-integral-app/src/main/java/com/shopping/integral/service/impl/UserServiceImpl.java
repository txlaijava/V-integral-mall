package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.utils.CommUtils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.integral.constant.RedisCons;
import com.shopping.integral.dao.IntegralUserDAO;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.service.IUserService;
import com.shopping.integral.util.BeanUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<IntegralUser, Long> implements IUserService {

    @Autowired
    IntegralUserDAO integralUserDAO;

    @Autowired
    RedisService redisService;

    @Override
    public IntegralUser autoLogin(long appid,String appkey, String uid, Long credits,String channel,String  openid, String remark) {
        Map param = new HashMap<>();
        param.put("app_key", appkey);
        param.put("uid", uid);
        IntegralUser user = this.integralUserDAO.findOneByMap(param);
        if (user == null) {
            user = new IntegralUser();
            user.setAdd_time(new Date());
            user.setAc_apply_id(appid);
            user.setApp_key(appkey);
            user.setUid(uid);
            user.setCredits(credits);
            user.setLogin_count(1);
            user.setLogin_time(new Date());
            user.setOpenid(openid);
            user.setRemark(remark);
            this.integralUserDAO.save(user);
        } else {
            //更新商城用户的积分数
            user.setCredits(credits);
            user.setLogin_count(user.getLogin_count() + 1);
            user.setLogin_time(new Date());
            user.setOpenid(openid);
            user.setRemark(remark);
            this.integralUserDAO.update(user);
        }
        log.info("更新用户积分，数量：{}", credits);
        try {
            redisService.hmset(RedisCons.INTEGRAL_APP_USER + CommUtils.null2String(user.getId()), BeanUtil.transBean2Map(user));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return user;
    }
}