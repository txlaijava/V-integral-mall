package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.form.PaginationForm;
import com.shopping.base.foundation.result.PaginationResult;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralGoods;
import com.shopping.integral.form.IntegralExchangeRulesForm;
import com.shopping.integral.queryform.IntegralGoodsAddForm;
import com.shopping.integral.queryform.IntegralGoodsQueryForm;

import java.util.List;
import java.util.Map;


/**
 * 类描述：IntegralGoodsService 接口
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
public interface IIntegralGoodsService extends IBaseService<IntegralGoods, Long> {


    /**
     * 获取商品列表
     *
     * @param integralGoodsQueryForm 查询条件
     * @return 商品列表
     */
    BaseResult getGoodsList(IntegralGoodsQueryForm integralGoodsQueryForm);


    /**
     * 获取商品详情
     *
     * @param applyId    应用编号
     * @param goodsId    商品编号
     * @param goodsState 发布状态
     * @return 商品详情
     */
    BaseResult getGoodsDetails(Long applyId, Long goodsId, int goodsState) throws Exception;

    /**
     * 编辑保存商品
     *
     * @param params
     * @param acApply
     * @return
     */
    Map setObjectGoods(Map params, IntegralAcApply acApply, IntegralGoodsAddForm integralGoodsForm, IntegralExchangeRulesForm exchangeRulesForm);

    /**
     * 分页查询商品信息
     *
     * @param pageForm
     * @return
     */
    PaginationResult<IntegralGoods> query(PaginationForm pageForm);

    /**
     * 批量修改商品状态（上架，下架，删除，排序，置顶）
     *
     * @param type     修改类型
     * @param goodsIds 商品编号
     * @param value    修改的值
     * @return
     */
    BaseResult updateGoods(String type, String goodsIds, Object value);

    /**
     * 添加库存
     *
     * @param applyId   应用编号
     * @param goodsId   商品编号
     * @param inventory 需要增加的值
     * @return 返回商品最新库存
     */
    BaseResult addGoodsInventory(Long applyId, Long goodsId, int inventory) throws Exception;

    /**
     * 减少库存
     *
     * @param applyId   应用编号
     * @param goodsId   商品编号
     * @param inventory 需要减少的值
     * @return 返回商品最新库存
     */
    BaseResult reduceGoodsInventory(Long applyId, Long goodsId, int inventory);

    /**
     * SQL查询,返回实体bean List
     *
     * @param sql
     * @param params
     * @return
     */
    List<IntegralGoods> queryBySql(String sql, Map<String, Object> params);
}