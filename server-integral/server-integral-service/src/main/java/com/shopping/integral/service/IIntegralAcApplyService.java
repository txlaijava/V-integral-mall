package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralAccessory;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.queryform.IntegralAcApplyAddForm;

import java.util.List;
import java.util.Map;


/**
* 类描述：IntegralAcApplyService 接口
*
* @author：GuoFuJun
* @date：2018年05月03日 21:21:24.
*/
public interface IIntegralAcApplyService extends IBaseService<IntegralAcApply,Long> {

    /**
     * 创建应用接口
     * @param params
     *          applyName: 应用名称（不允许为空）
     *          integralExchangeRate: 积分汇率（账号注册时可不传)
     *          integralUnit: 积分单位（账号注册时可不传）
     *          shoppingName: 积分单位（可不传）
     *          applyType: 积分单位（账号注册时可不传）
     * @param integralAccount   应用对应的账户
     * @param applyIconAcc      应用图标（可以允许为空）
     * @return
     * @throws Exception
     */
    BaseResult setIntegralAcApply(Map params, IntegralAccount integralAccount, IntegralAccessory applyIconAcc);


    /**
     * 获取指定账号下的所有应用
     * @param accountId     账号编号
     * @return  包含当前用户选中的应用
     */
    BaseResult getAcApplyByAcId(Long accountId) throws Exception;


    BaseResult updateAcApply(IntegralAcApplyAddForm integralAcApplyAddForm);

    /**
     * SQL查询,返回实体bean List
     * @param sql
     * @param params
     * @return
     */
    List<IntegralAcApply> queryBySql(String sql, Map<String,Object> params);

}