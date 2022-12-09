package com.shopping.integral.service;

import com.shopping.integral.base.BaseResult;

/**
 * 类描述：ILonginService 接口
 * 登录业务处理
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
public interface ILonginService {

    public BaseResult getlLoginValidation(String acMail, String acPassword);
}
