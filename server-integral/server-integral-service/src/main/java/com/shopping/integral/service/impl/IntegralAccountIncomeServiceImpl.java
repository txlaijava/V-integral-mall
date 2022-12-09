package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.result.PaginationResult;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralAccountIncomeDAO;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.dao.model.IntegralAccountIncome;
import com.shopping.integral.dao.model.IntegralOrder;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.queryform.IntegralAccountIncomeQueryForm;
import com.shopping.integral.service.IIntegralAccountIncomeService;
import com.shopping.integral.service.IIntegralAccountService;
import com.shopping.integral.service.IIntegralOrderService;
import com.shopping.integral.view.IntegralAccountIncomeView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by wangpenglin on 2018/8/23.
 */
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class IntegralAccountIncomeServiceImpl extends BaseServiceImpl<IntegralAccountIncome, Long> implements IIntegralAccountIncomeService {

    @Autowired
    private IIntegralOrderService iIntegralOrderService;

    @Autowired
    private IIntegralAccountService iIntegralAccountService;

    @Autowired
    private IntegralAccountIncomeDAO accountIncomeDAO;

    @Override
    public void adAccountIncome(Integer type, String desc, String oid, BigDecimal amount) throws Exception {
        //获取订单信息
        IntegralOrder integralOrder = this.iIntegralOrderService.getOneObjByProperty("oid", oid);
        if(integralOrder == null){
            return;
        }
        IntegralAccount account = integralOrder.getAcApply().getAc();

        //修改账户余额
        BigDecimal num = new BigDecimal(0);
        if (type == 1) {
            //1：提现申请
            num = account.getAcIncome().subtract(amount);
        } else if (type == 2) {
            //2：订单结算
            num = account.getAcIncome().add(amount);
        }
        account.setAcIncome(num);
        this.iIntegralAccountService.update(account);

        //保存收入账户记录
        IntegralAccountIncome accountIncome = new IntegralAccountIncome(type, integralOrder.getAcApply(), integralOrder.getAcApply().getAc(), integralOrder, desc,
                integralOrder.getBizId(), amount, account.getAcIncome());

        this.accountIncomeDAO.save(accountIncome);
    }

    @Override
    public BaseResult getIncomeList(IntegralAccountIncomeQueryForm incomeQueryForm) {
        try {
            PaginationResult<IntegralAccountIncome> item = accountIncomeDAO.paging(incomeQueryForm);
            return BaseResult.SUCCESS("获取成功！", item.getView(IntegralAccountIncomeView.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }
}
