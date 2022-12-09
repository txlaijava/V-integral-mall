package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralAccountDAO;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.ISubAccountService;
import com.shopping.integral.view.IntegralAccountView;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 类描述：SubAccountServiceImpl 实现
 *
 * @author：GuoFuJun
 * @date：2018年06月12日 14:17:58.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class SubAccountServiceImpl  extends BaseServiceImpl<IntegralAccount, Long> implements ISubAccountService {

    @Autowired
    IntegralAccountDAO integralAccountDAO;

    @Override
    public BaseResult getSubAccountList(Long parentId) {
        if(Utils.isEmpty(parentId)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_AC_PARENT_ID_NULL_ERROR);
        }

        StringBuffer sql = new StringBuffer("SELECT id , ac_mail , ac_company , ac_remark FROM integral_account a ");
        sql.append(" WHERE delete_status = 0 AND ac_parent_id =  "+parentId);
        List<Map<String,Object>> subAc = integralAccountDAO.queryByNativeSQL(sql.toString(),null);

        return BaseResult.SUCCESS("获取成功！",subAc);
    }

    @Override
    public BaseResult getSubAccount(Long subAcId) {

        if(Utils.isEmpty(subAcId)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_AC_ID_NULL_ERROR);
        }

        IntegralAccount integralAccount = super.getObjById(subAcId);
        if(Utils.isEmpty(integralAccount)){
            return BaseResult.ERROR(ResultErrorCodeEnum.ACCOUNT_NULL_ERROR);
        }

        try{
            IntegralAccountView view = BeanViewUtils.getView(integralAccount,IntegralAccountView.class);
            return BaseResult.SUCCESS("获取成功！",view);
        }catch (Exception e){
            log.error("账号信息转换异常！{}",e);
        }

        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult delSubAccount(Long parentId, Long subAcId) {

        try{
            if(Utils.isEmpty(parentId)){
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_AC_PARENT_ID_NULL_ERROR);
            }

            if(Utils.isEmpty(subAcId)){
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_AC_ID_NULL_ERROR);
            }

            IntegralAccount integralAccount = super.getObjById(subAcId);
            if(Utils.isEmpty(integralAccount)){
                return BaseResult.ERROR(ResultErrorCodeEnum.ACCOUNT_NULL_ERROR);
            }

            if(Utils.isEmpty(integralAccount.getAcParent())){
                return BaseResult.ERROR(ResultErrorCodeEnum.DEL_SUB_ACCOUNT_PARENT_NULL_ERROR);
            }

            if(!integralAccount.getAcParent().getId().equals(parentId)){
                return BaseResult.ERROR(ResultErrorCodeEnum.DEL_SUB_ACCOUNT_NOT_PARENT_ID_ERROR);
            }

            Boolean bol = integralAccountDAO.deleteById(subAcId);
            if(bol){
                return BaseResult.SUCCESS("删除成功",null);
            }
        }catch (Exception e){
            log.error("删除子账号异常！{}",e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }
}
