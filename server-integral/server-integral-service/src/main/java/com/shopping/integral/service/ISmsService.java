package com.shopping.integral.service;

import com.shopping.integral.base.BaseResult;

import java.util.Map;

/**
 * 类描述：ISmsService 接口
 * 短信业务接口
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
public interface ISmsService {

    /**
     * 发送验证码接口
     * @param mobileNo  接收验证码手机号码
     * @param templateCode  短信模板的编号
     * @return  发送记录
     */
    BaseResult setSendVerCodeSms(String mobileNo, String templateCode);


    /**
     * 验证短信验证码接口
     * @param mobileNo  接收到验证码手机号码
     * @param codeNo  验证码
     * @return
     */
    BaseResult getValiCode(String mobileNo, String codeNo);

    /**
     * 阿里大鱼短信接口
     * @param paramMap  短信内容
     * @param mobile    接收短信手机号
     * @param mark      短信模板编号
     * @return
     * @throws Exception
     */
    Boolean getSmsTemplateAndSendAlidayu(Map<String, Object> paramMap, String mobile, String mark) throws Exception;
}
