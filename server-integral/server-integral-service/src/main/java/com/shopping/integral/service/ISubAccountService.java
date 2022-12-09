package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAccount;

/**
 * 类描述：ISubAccountService 接口
 *
 * @author：GuoFuJun
 * @date：2018年06月12日 14:17:58.
 */
public interface ISubAccountService  extends IBaseService<IntegralAccount,Long> {

    /**
     * 获取子账号集合
     * @param parentId  父级 ID
     * @return
     */
    BaseResult getSubAccountList(Long parentId);

    /**
     * 获取对应子账号信息
     * @param subAcId  子账号 ID
     * @return
     */
    BaseResult getSubAccount(Long subAcId);

    /**
     * 删除对应子账号
     * @param subAcId  子账号 ID
     * @return
     */
    BaseResult delSubAccount(Long parentId,Long subAcId);
}
