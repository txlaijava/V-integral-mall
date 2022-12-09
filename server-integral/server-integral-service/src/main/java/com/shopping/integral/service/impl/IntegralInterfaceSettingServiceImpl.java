package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.constant.ConsConfig;
import com.shopping.integral.dao.IntegralInterfaceSettingDAO;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralInterfaceSetting;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralInterfaceSettingService;
import com.shopping.integral.view.IntegralInterfaceSettingView;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 类描述：IntegralInterfaceSettingService 实现
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralInterfaceSettingServiceImpl extends BaseServiceImpl<IntegralInterfaceSetting, Long> implements IIntegralInterfaceSettingService {

    @Autowired
    IntegralInterfaceSettingDAO integralInterfaceSettingDAO;

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @Override
    public String getByAcAndType(IntegralAcApply ac, String interfaceName) throws Exception {
        Map pram = new HashMap<>();
        pram.put("acApply", ac);
        pram.put("interfaceName", interfaceName);
        IntegralInterfaceSetting config = integralInterfaceSettingDAO.findOneByMap(pram);
        if (config != null) {
            return config.getInterfaceLink();
        } else {
            throw new Exception("找不到对应的开发者接口配置");
        }
    }


    @Override
    public BaseResult getByApplyIdList(Long acApplyId) throws Exception {

        if (Utils.isEmpty(acApplyId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
        }

        List<IntegralInterfaceSetting> interfaceSettingList = integralInterfaceSettingDAO.findByProperty("acApply.id", acApplyId);
        List<IntegralInterfaceSettingView> interfaceSettingViewList = BeanViewUtils.getList(interfaceSettingList, IntegralInterfaceSettingView.class);

        return BaseResult.SUCCESS("获取成功！", interfaceSettingViewList);
    }

    @Override
    public BaseResult updateInterfaceSetting(Long acApplyId, Map<String, Object> params) throws Exception {

        if (Utils.isEmpty(acApplyId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
        }
        IntegralAcApply integralAcApply = integralAcApplyService.getObjById(acApplyId);
        if (Utils.isEmpty(integralAcApply)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.AC_APPLY_NULL_ERROR);
        }
        if (Utils.isEmpty(params)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_NULL_ERROR);
        }
        Iterator<Map.Entry<String, Object>> entries = params.entrySet().iterator();
        IntegralInterfaceSetting interfaceSetting;
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            Map settingMap = (Map) entry.getValue();
            // 修改的接口链接
            String interfaceLink = CommUtils.null2String(settingMap.get("interfaceLink"));
            String key = "id";
            // 当接口设置对象中的 编号为时为添加接口设置
            if (Utils.isNotEmpty(settingMap.get(key))) {
                Long id = CommUtils.null2Long(settingMap.get(key));
                interfaceSetting = integralInterfaceSettingDAO.findById(id);
            } else {
                interfaceSetting = new IntegralInterfaceSetting(entry.getKey(),integralAcApply);
            }
            switch (entry.getKey()) {
                case ConsConfig.ITF_LOGIN:
                    if (Utils.isEmpty(settingMap.get(key))) {
                        interfaceSetting.setRemark("免登录接口");
                    }
                    break;
                case ConsConfig.ITF_CHARGE:
                    if (Utils.isEmpty(interfaceLink)) {
                        return BaseResult.ERROR(ResultErrorCodeEnum.PARM_INTEGR_CHARGE_NULL_ERROR);
                    }
                    if (Utils.isEmpty(settingMap.get(key))) {
                        interfaceSetting.setRemark("积分消费");
                    }
                    break;
                case ConsConfig.ITF_HOOK:
                    if (Utils.isEmpty(interfaceLink)) {
                        return BaseResult.ERROR(ResultErrorCodeEnum.PARM_INTEGR_HOOK_NULL_ERROR);
                    }
                    if (Utils.isEmpty(settingMap.get(key))) {
                        interfaceSetting.setRemark("通知结果");
                    }
                    break;
                case ConsConfig.ITF_XN_CHARGE:
                    if (Utils.isEmpty(settingMap.get(key))) {
                        interfaceSetting.setRemark("虚拟商品充值");
                    }
                    break;
                case ConsConfig.ITF_ADD:
                    if (Utils.isEmpty(settingMap.get(key))) {
                        interfaceSetting.setRemark("加积分接口");
                    }
                    break;
                default:
            }
            interfaceSetting.setInterfaceLink(interfaceLink);
            integralInterfaceSettingDAO.update(interfaceSetting);
        }
        return BaseResult.SUCCESS("保存成功！", null);
    }
}