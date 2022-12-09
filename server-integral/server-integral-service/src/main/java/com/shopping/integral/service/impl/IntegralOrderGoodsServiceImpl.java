package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.form.PaginationForm;
import com.shopping.base.foundation.result.PaginationResult;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralOrderGoodsDAO;
import com.shopping.integral.dao.model.IntegralGoods;
import com.shopping.integral.dao.model.IntegralOrder;
import com.shopping.integral.dao.model.IntegralOrderGoods;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.IIntegralOrderGoodsService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 类描述：IntegralOrderGoodsService 实现
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralOrderGoodsServiceImpl extends BaseServiceImpl<IntegralOrderGoods, Long> implements IIntegralOrderGoodsService {

    @Autowired
    IntegralOrderGoodsDAO integralOrderGoodsDAO;

    @Autowired
    IntegralGoodsServiceImpl integralGoodsService;

    @Override
    public BaseResult setOrderGoods(IntegralOrder integralOrder, IntegralGoods integralGoods, String orderGoodsSpecInfo) {

        if (Utils.isEmpty(integralOrder)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.INTEGRAL_ORDER_NULL_ERROR);
        }

        if (Utils.isEmpty(integralGoods)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.EXCHANGE_USER_NULL_ERROR);
        }
        IntegralGoods goods = this.integralGoodsService.getObjById(integralGoods.getId());

        IntegralOrderGoods orderGoods = new IntegralOrderGoods(goods, integralOrder, orderGoodsSpecInfo);
        super.save(orderGoods);
        BaseResult baseResult = BaseResult.SUCCESS("保存订单商品成功！",orderGoods.getId());
        return baseResult;
    }

    @Override
    public PaginationResult<IntegralOrderGoods> query(PaginationForm pageForm) {
        return this.integralOrderGoodsDAO.paging(pageForm);
    }

    @Override
    public List<Map<String,Object>> queryBySqlReturnMap(String sql, Map<String,Object> params){
        return this.integralOrderGoodsDAO.queryByNativeSQL(sql,params);
    }

    @Override
    public List<IntegralOrderGoods> queryBySql(String sql,Map<String,Object> params){
        return (List<IntegralOrderGoods>)this.integralOrderGoodsDAO.queryByNativeSQL(sql,IntegralOrderGoods.class,params);
    }
}