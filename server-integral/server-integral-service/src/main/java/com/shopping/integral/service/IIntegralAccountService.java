package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAccount;

import java.util.Map;


/**
 * 类描述：IntegralAccountService 接口
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:17:58.
 */
public interface IIntegralAccountService extends IBaseService<IntegralAccount, Long> {

    /**
     * 注册账号接口
     *
     * @param params acMail:  账户邮箱（不允许为空）
     *               acPassword:  账户密码（不允许为空）
     *               acName:  账户真实姓名（允许为空）
     *               acCompany:  账户所属公司（允许为空）
     *               applyName:  账户应用名称（不允许为空）
     *               acTelephone: 账户联系电话（不允许为空）
     *               acParentId:  上级账号（可不穿）
     *               说明：acParentId 不为空时为添加子账号
     * @return
     * @throws Exception
     */
    BaseResult setIntegralAccount(Map params) throws Exception;

    /**
     * 验证账号是否存在
     *
     * @param queryName 验证类型（'acTelephone':手机号码，'acMail':邮箱）
     * @param obj       账号
     * @return
     */
    BaseResult getValiAc(String queryName, String obj);


    /**
     * 修改账号密码
     *
     * @param queryName 验证类型（'acTelephone':手机号码，'acMail':邮箱）
     * @param obj       账号
     * @param pwd       新密码
     * @return
     */
    BaseResult setAcPwd(String queryName, String obj, String pwd);

    /**
     * 修改账号信息
     *
     * @param acId   修改的账号 ID
     * @param params 需要修改的信息 (map 中的 key、对应需要修改的字段名)
     * @param isView 是否需要展示到 View 层
     * @return
     */
    BaseResult upAccountInfo(Long acId, Map params, Boolean isView);

    /**
     * 切换账号应用
     *
     * @param integralAccount 账号对象
     * @param acApplyId       切换的应用编号
     * @return
     */
    BaseResult switchAcApply(IntegralAccount integralAccount, Long acApplyId);
}