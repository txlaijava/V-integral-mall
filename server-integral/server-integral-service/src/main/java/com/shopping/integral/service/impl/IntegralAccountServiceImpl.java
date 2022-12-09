package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Md5Encrypt;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralAccountDAO;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralAccountService;
import com.shopping.integral.service.ISmsService;
import com.shopping.integral.view.IntegralAccountView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 类描述：IntegralAccountService 实现
 * 类编号: Code 301
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:17:58.
 */
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class IntegralAccountServiceImpl extends BaseServiceImpl<IntegralAccount, Long> implements IIntegralAccountService {

    @Autowired
    ISmsService smsService;

    @Autowired
    IntegralAccountDAO integralAccountDAO;

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @Override
    public BaseResult setIntegralAccount(Map params) throws Exception {
        try {

            // 验证参数
            BaseResult baseResult = getValiParams(params);
            // 设置参数 key
            String acTelephone = "acTelephone", acMail = "acMail", acPassword = "acPassword", acName = "acName", acCompany = "acCompany", acParentId = "acParentId", acRemark = "acRemark";
            // 参数验证成功 才继续往下走
            if (!baseResult.getSuccess()) {
                return baseResult;
            }
            // <editor-fold desc="获取当前用户的上级账号">
            IntegralAccount acParent = null;
            if (params.containsKey(acParentId) && Utils.isNotEmpty(params.get(acParentId))) {
                Long parentId = CommUtils.null2Long(params.get(acParentId));
                acParent = integralAccountDAO.findById(parentId);
            }
            // </editor-fold>

            // <editor-fold desc="保存账号信息">
            IntegralAccount integralAccount = new IntegralAccount(CommUtils.null2String(params.get(acTelephone)), CommUtils.null2String(params.get(acPassword))
                    , CommUtils.null2String(params.get(acName)), CommUtils.null2String(params.get(acMail)), CommUtils.null2String(params.get(acCompany))
                    , CommUtils.null2String(params.get(acRemark)), acParent);
            integralAccountDAO.save(integralAccount);
            // </editor-fold>

            // 只有当注册账号为主账户是才需要创建应用
            if (Utils.isEmpty(acParent)) {

                // <editor-fold desc="创建应用">
                String applyNameKey = "applyName";
                if (!params.containsKey(applyNameKey) || Utils.isEmpty(params.get(applyNameKey))) {
                    return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLYNAME_NULL_ERROR);
                }
                String applyName = CommUtils.null2String(params.get(applyNameKey));
                params.clear();
                params.put("applyName", applyName);
                baseResult = integralAcApplyService.setIntegralAcApply(params, integralAccount, null);
                // </editor-fold>

                // <editor-fold desc="默认选择第一个应用">
                if(baseResult.getSuccess()){
                    Long applyId = CommUtils.null2Long(baseResult.getData());
                    IntegralAcApply integralAcApply = integralAcApplyService.getObjById(applyId);
                    integralAccount.setAcApply(integralAcApply);
                    integralAccountDAO.update(integralAccount);
                }
                // </editor-fold>
            }

            if (baseResult.getSuccess()) {
                // 设置哪些字段不返回至前端
                String[] ignoreFields = {"acPassword"};
                IntegralAccountView view = BeanViewUtils.getView(integralAccount, IntegralAccountView.class, ignoreFields);
                return BaseResult.SUCCESS("注册成功！", view);
            }
        } catch (Exception e) {
            log.error("注册账号接口异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    /**
     * 验证接口参数
     *
     * @param params 参数值
     * @return
     */
    BaseResult getValiParams(Map params) {
        if (Utils.isEmpty(params)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_NULL_ERROR);
        }
        String acMail = "acMail";
        if (!params.containsKey(acMail) || Utils.isEmpty(params.get(acMail))) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_EMAIL_NULL_ERROR);
        }

        IntegralAccount account = integralAccountDAO.findByPropertyOne("acMail", CommUtils.null2String(params.get(acMail)));
        if (Utils.isNotEmpty(account)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.VALID_EMAIL_MORE_ERROR);
        }

        String acPassword = "acPassword";
        if (!params.containsKey(acPassword) || Utils.isEmpty(params.get(acPassword))) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_PWD_NULL_ERROR);
        }

        String acParentId = "acParentId";
        if(!params.containsKey(acParentId) || Utils.isEmpty(params.get(acParentId))){

            String acTelephone = "acTelephone";
            if (!params.containsKey(acTelephone) || Utils.isEmpty(params.get(acTelephone))) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_MOBILE_NO_NULL_ERROR);
            }

            account = integralAccountDAO.findByPropertyOne("acTelephone", CommUtils.null2String(params.get(acTelephone)));
            if (Utils.isNotEmpty(account)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.VALID_MOBILENO_MORE_ERROR);
            }

            String authCode = "authCode";
            if (!params.containsKey(authCode) || Utils.isEmpty(params.get(authCode))) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_SMS_CODE_NULL_ERROR);
            } else {
                return smsService.getValiCode(CommUtils.null2String(params.get(acTelephone)), CommUtils.null2String(params.get(authCode)));
            }
        }
        return BaseResult.SUCCESS("验证成功",null);
    }

    @Override
    public BaseResult getValiAc(String queryName, String obj) {

        if (Utils.isEmpty(queryName)) {
            return BaseResult.ERROR(309, "参数异常，！查询的Name不能为空！");
        }

        if (Utils.isEmpty(obj)) {
            return BaseResult.ERROR(310, "参数异常，！账号号码不能为空！");
        }

        IntegralAccount account = integralAccountDAO.findByPropertyOne(queryName, obj);

        if (Utils.isNotEmpty(account)) {
            return BaseResult.SUCCESS("验证成功", obj);
        }

        return BaseResult.ERROR(ResultErrorCodeEnum.ACCOUNT_NULL_ERROR);
    }

    @Override
    public BaseResult setAcPwd(String queryName, String obj, String pwd) {

        BaseResult baseResult = getValiAc(queryName, obj);
        if (!baseResult.getSuccess()) {
            return baseResult;
        }

        if(Utils.isEmpty(pwd)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_PWD_NULL_ERROR);
        }

        IntegralAccount account = integralAccountDAO.findByPropertyOne(queryName, obj);
        if (Utils.isNotEmpty(account)) {
            String newPwd = Md5Encrypt.md5(pwd);
            account.setAcPassword(newPwd);
            return BaseResult.SUCCESS("修改成功！", obj);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult upAccountInfo(Long acId,Map params,Boolean isView) {

        if(Utils.isEmpty(acId)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_AC_ID_NULL_ERROR);
        }

        IntegralAccount integralAccount = super.getObjById(acId);
        if (Utils.isEmpty(integralAccount)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.ACCOUNT_NULL_ERROR);
        }

        String[] keys = {"acName","acCompany","acRemark"};

        if(params.containsKey(keys[0])){
            integralAccount.setAcName(CommUtils.null2String(params.get(keys[0])));
        }

        if(params.containsKey(keys[1])){
            integralAccount.setAcCompany(CommUtils.null2String(params.get(keys[1])));
        }

        if(params.containsKey(keys[2])){
            integralAccount.setAcRemark(CommUtils.null2String(params.get(keys[2])));
        }

        integralAccount = integralAccountDAO.update(integralAccount);
        if (Utils.isNotEmpty(integralAccount) && isView) {
            IntegralAccountView view = BeanViewUtils.getView(integralAccount, IntegralAccountView.class);
            return BaseResult.SUCCESS("修改成功！", view);
        } else if (Utils.isNotEmpty(integralAccount)) {
            return BaseResult.SUCCESS("修改成功！", integralAccount);
        }

        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult switchAcApply(IntegralAccount integralAccount, Long acApplyId) {

        try{
            if(Utils.isEmpty(integralAccount)){
                return BaseResult.ERROR(ResultErrorCodeEnum.ACCOUNT_NULL_ERROR);
            }

            if(Utils.isEmpty(acApplyId)){
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
            }

            IntegralAcApply integralAcApply = integralAcApplyService.getObjById(acApplyId);
            if(Utils.isEmpty(integralAcApply)){
                return BaseResult.ERROR(ResultErrorCodeEnum.AC_APPLY_NULL_ERROR);
            }

            integralAccount.setAcApply(integralAcApply);
            super.update(integralAccount);
            return BaseResult.SUCCESS("切换应用成功！",null);

        }catch (Exception e){
            log.error("切换应用接口异常!",e);
        }

        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }
}