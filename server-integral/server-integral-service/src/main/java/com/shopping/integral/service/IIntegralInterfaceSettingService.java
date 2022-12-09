package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralInterfaceSetting;

import java.util.Map;


/**
 * 类描述：IntegralInterfaceSettingService 接口
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
public interface IIntegralInterfaceSettingService extends IBaseService<IntegralInterfaceSetting, Long> {

    /**
     * 根据应用以及通知类型获取接口地址
     *
     * @param ac
     * @param interfaceName
     * @return
     * @throws Exception
     */
    String getByAcAndType(IntegralAcApply ac, String interfaceName) throws Exception;

    /**
     * 根据应用编号获取应用配置
     *
     * @param acApplyId 应用编号
     * @return
     */
    BaseResult getByApplyIdList(Long acApplyId) throws Exception;


    /**
     * 添加，修改应用配置
     *
     * @param acApplyId 应用编号
     * @param params    修改的内容
     * @return
     */
    BaseResult updateInterfaceSetting(Long acApplyId, Map<String,Object> params) throws Exception;

}