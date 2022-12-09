package com.shopping.integral.view;

import com.shopping.base.foundation.view.BeanView;
import com.shopping.integral.dao.model.IntegralAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 类描述：视图使用的实体
 *
 * @EqualsAndHashCode :此注解会生成equals(Object other) 和 hashCode()方法。
 *              参考 http://blog.csdn.net/zhanlanmg/article/details/50392266
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralAccountView extends BeanView<IntegralAccount> {

    private Long id;

    /**
     * 联系电话
     */
    private String acTelephone;

    /**
     * 登录密码
     */
    private String acPassword;

    /**
     * 账户真实姓名
     */
    private String acName;

    /**
     * 账户昵称
     */
    private String acUnickName;

    /**
     * 账户邮箱
     */
    private String acMail;

    /**
     * 账户级别:主账户为1 , 下属账户的级别为2
     */
    private int acLevel;

    /**
     * 账户所属公司
     */
    private String acCompany;

    /**
     * 账号余额
     */
    private BigDecimal acAmount = new BigDecimal(0);

    /**
     * 账户收入余额
     */
    private BigDecimal acIncome = new BigDecimal(0);

    /**
     * 冻结金额
     */
    private BigDecimal acFreezingAmount = new BigDecimal(0);

    /**
     * 剩余提现次数
     */
    private int acTakeOutCount = 3;

    /**
     * 剩余提现金额
     */
    private BigDecimal acTakeOutMoney = new BigDecimal(99999);

    /**
     * 当前应用编号
     */
    private Long acApplyId;

    /**
     * 当前应用主题编号
     */
    private Long acApplyThemeId;

    /**
     * 账户备注（用户子账号）
     */
    private String acRemark;
}
