package com.shopping.integral.service;

import com.shopping.base.domain.user.User;
import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.form.PaginationForm;
import com.shopping.base.foundation.result.PaginationResult;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralGoods;
import com.shopping.integral.dao.model.IntegralOrder;
import com.shopping.integral.dao.model.IntegralOrderGoods;

import java.util.List;
import java.util.Map;


/**
* 类描述：IntegralOrderGoodsService 接口
*
* @author：GuoFuJun
* @date：2018年05月03日 21:21:24.
*/
public interface IIntegralOrderGoodsService extends IBaseService<IntegralOrderGoods,Long> {

    /**
     * 保存兑换订单商品
     * @param integralOrder     兑换订单
     * @param integralGoods     兑换商品
     * @param orderGoodsSpecInfo    兑换商品规格信息
     * @return
     */
    BaseResult setOrderGoods(IntegralOrder integralOrder, IntegralGoods integralGoods,String orderGoodsSpecInfo);

    PaginationResult<IntegralOrderGoods> query(PaginationForm pageForm);

    List<Map<String,Object>> queryBySqlReturnMap(String sql, Map<String,Object> params);

    List<IntegralOrderGoods> queryBySql(String sql, Map<String,Object> params);
}