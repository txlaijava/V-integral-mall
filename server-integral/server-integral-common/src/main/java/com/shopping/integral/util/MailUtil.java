package com.shopping.integral.util;

import lombok.extern.log4j.Log4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.nio.charset.Charset;

/**
 * 类描述：MailUtil 邮件发送
 *
 * @author：GuoFuJun
 * @date：2018年07月15日 15:25:03.
 */
@Log4j
public class MailUtil {

    /**
     * @param toAddress   收件人邮箱
     * @param mailSubject 邮件主题
     * @param mailBody    邮件正文
     * @return
     */
    public static boolean sendMail(String toAddress, String mailSubject, String mailBody) {

        try {
            // Create the email message
            HtmlEmail email = new HtmlEmail();

            //email.setDebug(true);		// 将会打印一些log
            //email.setTLS(true);		// 是否TLS校验，，某些邮箱需要TLS安全校验，同理有SSL校验
            //email.setSSL(true);
            String mailHost = "smtp.exmail.qq.com";
            Boolean mailSSL = true;
            String mailPort = "465";

            email.setHostName(mailHost);

            if (mailSSL) {
                email.setSslSmtpPort(mailPort);
                email.setSSLOnConnect(true);
            } else {
                email.setSmtpPort(Integer.valueOf(mailPort));
            }
            String mailUsername = "weixin@17wpw.com";
            String mailPassword = "tgKzXMVdNDD5ucES";

            String mailSendNick = "《红商城积分商城》";

            email.setAuthenticator(new DefaultAuthenticator(mailUsername, mailPassword));
            email.setCharset(Charset.defaultCharset().name());

            email.setFrom(mailUsername, mailSendNick);
            email.addTo(toAddress);
            email.setSubject(mailSubject);
            email.setMsg(mailBody);

            //email.attach(attachment);	// add the attachment

            email.send();                // send the email
            return true;
        } catch (EmailException e) {
            log.error(e.getMessage(), e);

        }
        return false;
    }
}
