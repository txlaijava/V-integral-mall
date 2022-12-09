package com.shopping.integral.dao.model;

import com.shopping.base.foundation.base.entity.IdEntity;
import com.shopping.base.utils.Md5Encrypt;
import com.shopping.base.utils.Utils;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 账户
 *
 * @author gfj
 * @date 2018/4/28.
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_account")
@ToString(exclude={"acParent","integralAcApplies","acApply"})
public class IntegralAccount extends IdEntity {

    /**
     * 应用状态 0 正常  1 冻结
     */
    private int ac_status;

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
     * 账户备注（用户子账号）
     */
    private String acRemark;

    /**
     * 上级
     */
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private IntegralAccount acParent;

    /**
     * integralAcApplies : 账号应用
     */
    @OneToMany(mappedBy = "ac", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<IntegralAcApply> integralAcApplies;

    /**
     * acApply : 当前账号选择的应用
     */
    @OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private IntegralAcApply acApply;

    public IntegralAccount(){

    }

    public IntegralAccount(String acTelephone,String acPassword,String acName,String acMail,String acCompany,String acRemark,IntegralAccount acParent){
        String acUnickName = "";
        this.addTime = new Date();
        this.deleteStatus = false;
        this.acTelephone = acTelephone;
        this.acPassword = Md5Encrypt.md5(acPassword);
        this.acName = acName;
        if(Utils.isNotEmpty(acParent)){
            acUnickName = acPassword.substring(acPassword.length()-4,acPassword.length());
        }else{
            acUnickName = acTelephone.substring(acTelephone.length()-4,acTelephone.length());
        }
        this.acMail = acMail;
        this.acCompany = acCompany;
        this.acRemark = acRemark;
        this.acParent = acParent;
        this.acUnickName = "NO_"+acUnickName;
    }
}
