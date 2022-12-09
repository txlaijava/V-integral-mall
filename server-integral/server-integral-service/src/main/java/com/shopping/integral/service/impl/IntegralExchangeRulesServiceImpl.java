package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.base.utils.date.DateUtil;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.constant.GoodsCons;
import com.shopping.integral.dao.IntegralGoodsDAO;
import com.shopping.integral.dao.model.IntegralExchangeRules;
import com.shopping.integral.dao.model.IntegralGoods;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.IIntegralExchangeRulesService;
import com.shopping.integral.service.IIntegralOrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 类描述：IntegralExchangeRulesService 实现
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class IntegralExchangeRulesServiceImpl extends BaseServiceImpl<IntegralExchangeRules, Long> implements IIntegralExchangeRulesService {

    /**
     * FOREVER_LIMIT : 兑换限永远
     */
    private static final int FOREVER_LIMIT = 0;
    /**
     * DAY_LIMIT : 兑换限制每天
     */
    private static final int DAY_LIMIT = 1;
    /**
     * CYCLE_DAY_LIMIT : 兑换限制周期天（例如：5天兑换一次）
     */
    private static final int CYCLE_DAY_LIMIT = 2;

    @Autowired
    IntegralGoodsDAO integralGoodsDAO;

    @Autowired
    IIntegralOrderService integralOrderService;

    @Override
    public BaseResult getValiExchangeRules(Long goodId, Long userId) {
        try {
            IntegralGoods integralGoods = integralGoodsDAO.findById(goodId);
            IntegralExchangeRules integralExchangeRules = integralGoods.getExchangeRules();
            int singular = 0;

            // 验证上架商品状态
            if (GoodsCons.GOODS_PUBLISH != integralGoods.getGoodsState()) {
                return BaseResult.ERROR(ResultErrorCodeEnum.ORDER_GOODS_STATE);
            }

            BaseResult baseResult = null;
            if(Utils.isNotEmpty(integralExchangeRules.getUserExchangeLimit())){
                ResultErrorCodeEnum errorCodeEnum = ResultErrorCodeEnum.UNKNOWN_ERROR;
                Integer exchangeUnit = integralExchangeRules.getUserExchangeLimitUnit();
                if(integralExchangeRules.getUserExchangeLimitUnit() == null){
                    exchangeUnit = 0;
                }
                switch (exchangeUnit) {
                    case FOREVER_LIMIT:
                        baseResult = integralOrderService.getByGoodsIdOrderCount(null, null, goodId, userId);
                        errorCodeEnum = ResultErrorCodeEnum.EXCHANGERULES_EXCEED_FOREVER_LIMIT;
                        break;
                    case DAY_LIMIT:
                        baseResult = integralOrderService.getByGoodsIdOrderCount(CommUtils.formatLongDate(new Date()), null, goodId, userId);
                        errorCodeEnum = ResultErrorCodeEnum.EXCHANGERULES_EXCEED_DAY_LIMIT;
                        break;
                    case CYCLE_DAY_LIMIT:
                        String endDate = CommUtils.formatLongDate(DateUtil.decDay(new Date(), integralExchangeRules.getUserExchangeLimitUnit()));
                        baseResult = integralOrderService.getByGoodsIdOrderCount(endDate, CommUtils.formatLongDate(new Date()), goodId, userId);
                        errorCodeEnum = ResultErrorCodeEnum.EXCHANGERULES_EXCEED_CYCLE_DAY_LIMIT;
                        break;
                    default:
                }
                if (!baseResult.getSuccess()) {
                    return baseResult;
                }
                singular = CommUtils.null2Int(baseResult.getData());
                if (singular >= integralExchangeRules.getUserExchangeLimit()) {
                    return BaseResult.ERROR(errorCodeEnum);
                }
            }

            // 判断额外的兑换限制
            if (integralExchangeRules.getAdditionalExchangeLimit()) {

                // 当额外天兑换限制不为空时 需要判断 数量兑换限制
                if (Utils.isNotEmpty(integralExchangeRules.getDailyExchangeLimit())) {
                    // 获取用户当天兑换的该商品的订单
                    baseResult = integralOrderService.getByGoodsIdOrderCount(CommUtils.formatLongDate(new Date()), null, goodId, userId);
                    if (!baseResult.getSuccess()) {
                        return baseResult;
                    }
                    singular = CommUtils.null2Int(baseResult.getData());
                    // 用户已兑换的商品订单数大于限制则不能进行兑换
                    if (singular >= integralExchangeRules.getDailyExchangeLimit()) {
                        return BaseResult.ERROR(ResultErrorCodeEnum.EXCHANGERULES_EXCEED_LIMIT);
                    }
                }

                Date currentDate = new Date();
                // 判断额外兑换限制，商品兑换的开始时间
                if (Utils.isNotEmpty(integralExchangeRules.getExchangeStartTime())
                        && currentDate.getTime() < integralExchangeRules.getExchangeStartTime().getTime()) {
                    return BaseResult.ERROR(ResultErrorCodeEnum.EXCHANGERULES_EXCEED_START_TIME);
                }
                // 判断额外兑换限制，商品兑换的结束时间
                if (Utils.isNotEmpty(integralExchangeRules.getExchangeEndTime())
                        && currentDate.getTime() >= integralExchangeRules.getExchangeEndTime().getTime()) {
                    return BaseResult.ERROR(ResultErrorCodeEnum.EXCHANGERULES_EXCEED_END_TIME);
                }
            }

            return BaseResult.SUCCESS("验证成功！", null);
        } catch (Exception e) {
            log.error("验证商品兑换限制异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }
}