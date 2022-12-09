package com.shopping.integral.service.impl;

import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.IIntegralAccountService;
import com.shopping.integral.service.IMailService;
import com.shopping.integral.util.MailUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

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
public class MailServiceImpl implements IMailService {

    @Autowired
    IIntegralAccountService integralAccountService;

    @Override
    public BaseResult sendAppSecretMail(String acMail,String type) throws Exception{

        if(Utils.isEmpty(acMail)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_EMAIL_NULL_ERROR);
        }

        IntegralAccount integralAccount = integralAccountService.getOneObjByProperty("acMail",acMail);

        if(Utils.isEmpty(integralAccount)){
            return BaseResult.ERROR(ResultErrorCodeEnum.ACCOUNT_NULL_ERROR);
        }

        String mailBody = "";
        if("AppSecret".equalsIgnoreCase(type)){
            mailBody = mailBodyTemplate(integralAccount.getAcName(),
                    integralAccount.getAcApply().getApplyName(),integralAccount.getAcApply().getAppSecret());
        }
        String content = MessageFormat.format(mailBody,"红商城积分商城");

        Boolean sendState =  MailUtil.sendMail(acMail, "红商城积分商城AppSecret码", content);
        if(!sendState){
            return BaseResult.ERROR(ResultErrorCodeEnum.MAIL_API_SEND_ERROR);
        }

        return BaseResult.SUCCESS("发送成功！",acMail);
    }


    private String mailBodyTemplate(String acName,String applyName,String appSecret){
        StringBuffer template = new StringBuffer("<div class=\"main\"><h2></h2><div>");
        template.append("<h3>尊敬的用户: " + acName + "</h3>");
        template.append("您好，您的App:" + applyName + " 的AppSecret为：" + appSecret + "<br>");
        template.append("请妥善保管好App的Secret码。泄露此Secret可能会给您带来风险。<br>");
        template.append("如遗失AppSecret，您需要联系商务人员来重新获取<br></div><div>此为系统邮件，请勿回复</div>");
        template.append("</div>");

        return template.toString();
    }
}
