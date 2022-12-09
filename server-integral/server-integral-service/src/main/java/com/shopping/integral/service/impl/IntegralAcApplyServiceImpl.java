package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralAcApplyDAO;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralAccessory;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.dao.model.IntegralTheme;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.queryform.IntegralAcApplyAddForm;
import com.shopping.integral.service.*;
import com.shopping.integral.view.IntegralAcApplyView;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 类描述：IntegralAcApplyService 实现
 * 类编号: Code 401
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class IntegralAcApplyServiceImpl extends BaseServiceImpl<IntegralAcApply, Long> implements IIntegralAcApplyService {

    @Autowired
    IntegralAcApplyDAO integralAcApplyDAO;

    @Autowired
    IIntegralThemeService integralThemeService;

    @Autowired
    IIntegralAcApplyThemeItemService integralAcApplyThemeItemService;

    @Autowired
    IIntegralAccountService accountService;

    @Autowired
    IIntegralAccessoryService integralAccessoryService;

    @Override
    public BaseResult setIntegralAcApply(Map params, IntegralAccount integralAccount, IntegralAccessory applyIconAcc){

        try {
            if (Utils.isEmpty(params)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_NULL_ERROR);
            }

            if (Utils.isEmpty(integralAccount)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_ACCOMPANY_NULL_ERROR);
            }
            String applyName = "applyName";
            if (!params.containsKey(applyName) && Utils.isEmpty(params.get(applyName))) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLYNAME_NULL_ERROR);
            }

            BigDecimal integralExchangeRate = params.containsKey("integralExchangeRate") && Utils.isNotEmpty(params.get("integralExchangeRate"))
                    ? CommUtils.null2BigDecimal(params.get("integralExchangeRate")) : new BigDecimal(0);

            String integralUnit = params.containsKey("integralUnit") && Utils.isNotEmpty(params.get("integralUnit")) ? CommUtils.null2String(params.get("integralUnit")) : "积分";

            String shoppingName = params.containsKey("shoppingName") && Utils.isNotEmpty(params.get("shoppingName")) ? CommUtils.null2String(params.get("shoppingName")) : "";

            int applyType = params.containsKey("applyType") && Utils.isNotEmpty(params.get("applyType")) ? CommUtils.null2Int(params.get("applyType")) : 0;

            // 获取系统默认的主题
            IntegralTheme theme = integralThemeService.getOneObjByProperty("isDefault", true);

            IntegralAcApply integralAcApply = new IntegralAcApply(integralExchangeRate, integralUnit, CommUtils.null2String(params.get(applyName))
                    , shoppingName, applyType, applyIconAcc, integralAccount, theme);
            super.save(integralAcApply);

            // 把默认主题下面所有模板添加到当前应用下面
            BaseResult baseResult = integralAcApplyThemeItemService.saveApplyThemeItem(integralAcApply.getId(), theme);
            if (baseResult.getSuccess()) {
                return BaseResult.SUCCESS("创建成功！", integralAcApply.getId());
            }
        } catch (Exception e) {
            log.error("创建应用接口失败！", e);
        }

        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }


    @Override
    public BaseResult getAcApplyByAcId(Long accountId) throws Exception{
        if(Utils.isEmpty(accountId)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_AC_ID_NULL_ERROR);
        }

        IntegralAccount integralAccount = accountService.getObjById(accountId);
        if(Utils.isEmpty(integralAccount)){
            return BaseResult.ERROR(ResultErrorCodeEnum.ACCOUNT_NULL_ERROR);
        }

        List<IntegralAcApply> acApplyList = integralAccount.getIntegralAcApplies();
        List<IntegralAcApplyView>  acApplys = BeanViewUtils.getList(acApplyList, IntegralAcApplyView.class);
        Map<String,Object> refund = new HashedMap(2);
        refund.put("acApplyList",acApplys);
        refund.put("selectedApplyId",integralAccount.getAcApply().getId());

        return BaseResult.SUCCESS("获取成功！",refund);
    }

    @Override
    public BaseResult updateAcApply(IntegralAcApplyAddForm integralAcApplyAddForm) {

        try{
            if(Utils.isEmpty(integralAcApplyAddForm.getId())){
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
            }

            IntegralAcApply integralAcApply = super.getObjById(integralAcApplyAddForm.getId());

            if(Utils.isEmpty(integralAcApply)){
                return BaseResult.ERROR(ResultErrorCodeEnum.AC_APPLY_NULL_ERROR);
            }

            String[] ignoreProperties = null;
            switch (integralAcApplyAddForm.getEditorType()){
                case 1:
                    ignoreProperties = new String[3];
                    ignoreProperties[0] = "id";
                    if(Utils.isEmpty(integralAcApplyAddForm.getThemeColor()) || Utils.isEmpty(integralAcApplyAddForm.getThemeColorRgba())){
                        ignoreProperties[1] = "themeColor";
                        ignoreProperties[2] = "themeColorRgba";
                    }
                    break;
                case 2:
                    if(Utils.isNotEmpty(integralAcApplyAddForm.getThemeId())){
                        IntegralTheme integralTheme = integralThemeService.getObjById(integralAcApplyAddForm.getThemeId());
                        if(Utils.isEmpty(integralTheme)){
                            return BaseResult.ERROR(ResultErrorCodeEnum.THEME_NULL_ERROR);
                        }
                        integralAcApply.setTheme(integralTheme);
                    }
                    break;
                case 3:
                    ignoreProperties = new String[5];
                    if(Utils.isEmpty(integralAcApplyAddForm.getIntegralExchangeRate())){
                        return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_INTEGRALEXCHANGERATE_NULL_ERROR);
                    }

                    if(Utils.isEmpty(integralAcApplyAddForm.getIntegralUnit())){
                        return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_INTEGRALUNIT_NULL_ERROR);
                    }

                    if(Utils.isEmpty(integralAcApplyAddForm.getApplyName())){
                        return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLYNAME_NULL_ERROR);
                    }

                    if(Utils.isEmpty(integralAcApplyAddForm.getIntegralDetailedLink())){
                        ignoreProperties[0] = "integralDetailedLink";
                    }

                    if(Utils.isEmpty(integralAcApplyAddForm.getIntegralInsufficientTxt())){
                        ignoreProperties[1] = "integralInsufficientTxt";
                    }

                    if(Utils.isEmpty(integralAcApplyAddForm.getIntegralInsufficientLink())){
                        ignoreProperties[2] = "integralInsufficientLink";
                    }

                    if(Utils.isEmpty(integralAcApplyAddForm.getShoppingName())){
                        ignoreProperties[3] = "shoppingName";
                    }

                    if(Utils.isEmpty(integralAcApplyAddForm.getApplyType())){
                        ignoreProperties[4] = "applyType";
                    }

                    if(Utils.isNotEmpty(integralAcApplyAddForm.getApplyIconId())){
                        IntegralAccessory integralAccessory = integralAccessoryService.getObjById(integralAcApplyAddForm.getApplyIconId());
                        integralAcApply.setApplyIconAcc(integralAccessory);
                    }
                    break;
                default:
            }
            BeanUtils.copyProperties(integralAcApplyAddForm,integralAcApply,ignoreProperties);

            super.update(integralAcApply);
            IntegralAcApplyView integralAcApplyView = BeanViewUtils.getView(integralAcApply,IntegralAcApplyView.class);
            integralAcApplyView.setThemeId(integralAcApply.getTheme().getId());
            return BaseResult.SUCCESS("修改成功！",integralAcApplyView);
        }catch (Exception e){
            log.error("修改应用信息错误!",e);
        }

        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public List<IntegralAcApply> queryBySql(String sql,Map<String,Object> params){
        return (List<IntegralAcApply>)this.integralAcApplyDAO.queryByNativeSQL(sql,IntegralAcApply.class,params);
    }
}