package com.shopping.integral.service;

import com.shopping.integral.base.BaseResult;

/**
 * 类描述：IMailService 接口
 * 邮箱业务接口
 *
 * @author：GuoFuJun
 * @date：2018年07月18日 21:21:24.
 */
public interface IMailService {


    /**
     * 发送 AppSecre
     *
     * @param acMail 接收邮件账号邮箱
     * @param type   发送邮箱类型
     * @return
     */
    BaseResult sendAppSecretMail(String acMail, String type) throws Exception;
}
