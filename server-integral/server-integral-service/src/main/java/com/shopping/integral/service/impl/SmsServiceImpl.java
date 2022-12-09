package com.shopping.integral.service.impl;

import com.alibaba.fastjson.JSON;
import com.shopping.base.utils.Md5Encrypt;
import com.shopping.base.utils.StringUtils;
import com.shopping.base.utils.Utils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.framework.sms.core.DaYuSms;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralTemplateDAO;
import com.shopping.integral.dao.model.IntegralTemplate;
import com.shopping.integral.service.ISmsService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 类描述：SmsServiceImpl 接口
 * 短信业务处理
 * 类编号: Code 201
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class SmsServiceImpl implements ISmsService {

    @Autowired
    IntegralTemplateDAO integralTemplateDAO;

    @Autowired
    RedisService redisService;

    @Autowired
    DaYuSms daYuSms;

    @Override
    public BaseResult setSendVerCodeSms(String mobileNo, String templateCode) {

        if (Utils.isEmpty(mobileNo)) {
            return BaseResult.ERROR(201,"接收短信号码不允许为空！");
        }

        if (Utils.isEmpty(templateCode)) {
            return BaseResult.ERROR(202,"发送短信模板编号不允许为空！");
        }

        //生成4位随机手机验证码
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String authCode = (int) ((Math.random() * 9 + 1) * 1000) + "";
            System.out.println("手机验证码 ================> " + authCode + " <================");
            // 验证码需要加密处理返回到视图层
            paramMap.put("authCode", authCode);
            boolean flag = getSmsTemplateAndSendAlidayu(paramMap, mobileNo, templateCode);
            paramMap.put("flag", flag);
            if(flag){
                String code = Md5Encrypt.md5(authCode);
                redisService.set(mobileNo,code,180);
                log.info("Redis成功存储验证码==手机号:"+mobileNo+",验证码:"+Md5Encrypt.md5(authCode)+",过期时间:180s");
                return BaseResult.SUCCESS("短信发送成功！",null);
            }else{
                return BaseResult.ERROR(203,"短信发送失败！");
            }
        } catch (Exception e) {
            log.error("发送验证码短信异常！", e);
        }
        return BaseResult.ERROR(500,"短信发送失败！，接口异常");
    }

    @Override
    public BaseResult getValiCode(String mobileNo, String codeNo){

        if (Utils.isEmpty(mobileNo)) {
            return BaseResult.ERROR(201,"接收到短信的手机号码不允许为空！");
        }

        if (Utils.isEmpty(codeNo)) {
            return BaseResult.ERROR(202,"待验证的验证码不允许为空！");
        }

        try{
            String code = redisService.get(mobileNo);
            if(Utils.isEmpty(code)){
                return BaseResult.ERROR(203,"对应手机号码没有发送验证码！");
            }
            code = code.replace("\"", "");
            if(!StringUtils.endsWithIgnoreCase(Md5Encrypt.md5(codeNo),code)){
                return BaseResult.ERROR(204,"验证失败！该验证码错误！");
            }

            return BaseResult.SUCCESS("验证成功！该验证码正确。",codeNo);
        }catch (Exception e){
            log.error("验证短信验证码异常！", e);
        }
        return BaseResult.ERROR(500,"验证失败！，接口异常");
    }

    @Override
    public Boolean getSmsTemplateAndSendAlidayu(Map<String, Object> paramMap, String mobile, String mark) throws Exception {
        boolean flag = false;
        //获取模板
        IntegralTemplate template = integralTemplateDAO.findByPropertyOne("mark", mark);
        //判断模板状态为“开启”
        if (Utils.isNotEmpty(template) && (template.isOpen())) {
            String content = template.getContent();
            //获取Map对象里的键值
            Iterator it = paramMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = "\\$!\\{" + entry.getKey().toString() + "\\}";
                content = content.replaceAll(key, entry.getValue().toString());
            }
            //发送
            flag = daYuSms.sendSms(template.getCodeId(), JSON.toJSONString(paramMap), mobile);
            if (flag) {
                log.info("阿里大于短信方法1发送至" + mobile + ",短信模板mark:" + mark + ",内容:" + content + ",短信");
            }
        }
        return flag;
    }
}
