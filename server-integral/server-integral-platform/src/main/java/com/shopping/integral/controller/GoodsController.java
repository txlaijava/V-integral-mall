package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.foundation.result.PaginationResult;
import com.shopping.base.foundation.result.QueryResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralExchangeRules;
import com.shopping.integral.dao.model.IntegralGoods;
import com.shopping.integral.form.IntegralExchangeRulesForm;
import com.shopping.integral.queryform.IntegralGoodsAddForm;
import com.shopping.integral.queryform.IntegralGoodsQueryForm;
import com.shopping.integral.service.IIntegralGoodsService;
import com.shopping.integral.token.authorization.annotation.CurrentIntegralAcApply;
import com.shopping.integral.util.ParamUtils;
import com.shopping.integral.view.IntegralGoodsView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：商品操作
 *
 * @author：GuoFuJun
 * @date：2018年7月2日 上午11:13:46
 */
@Log4j2
@Api(value = "商品操作", tags = {"goods"})
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IIntegralGoodsService integralGoodsService;

    @ApiOperation(value = "保存实物商品", tags = {"goods"}, notes = "")
    @PostMapping(value = "/saveObjectGoods")
    public ActionResult<Map> saveObjectGoods(HttpServletRequest request, @CurrentIntegralAcApply IntegralAcApply integralAcApply, IntegralGoodsAddForm goodsAddForm, IntegralExchangeRulesForm exchangeRulesForm) {
        try {
            Map map = new HashMap();
            //判断是否登录
            if (integralAcApply == null) {
                map.put("status", 2001);
                map.put("message", "未登录");
                return ActionResult.ok(map);
            }
            IntegralGoods integralGoods = new IntegralGoods();
            IntegralExchangeRules exchangeRules = new IntegralExchangeRules();
            //转换form
            BeanUtils.copyProperties(goodsAddForm, integralGoods);
            BeanUtils.copyProperties(exchangeRulesForm, exchangeRules);
            Map params = ParamUtils.getparams(request);
            map = this.integralGoodsService.setObjectGoods(params, integralAcApply, goodsAddForm, exchangeRulesForm);
            return ActionResult.ok(map);
        } catch (Exception e) {
            log.error("保存实物商品异常", e);
        }
        return ActionResult.error("服务器异常");
    }

    /**
     *
     * @param integralAcApply
     * @param type  up:上架,down:下架,del:删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "修改商品", tags = {"goods"}, notes = "")
    @PutMapping(value = "/update_goods")
    public ActionResult<BaseResult> updateGoods(@CurrentIntegralAcApply IntegralAcApply integralAcApply, String type, String ids, String value) {
        try {
            if (Utils.isNotEmpty(integralAcApply)) {
                BaseResult baseResult = integralGoodsService.updateGoods(type, ids, value);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("修改商品异常", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取商品信息去编辑", tags = {"goods"}, notes = "")
    @GetMapping(value = "/goods_info")
    public ActionResult<BaseResult> getGoodsInfo(@CurrentIntegralAcApply IntegralAcApply integralAcApply, String id, String goodsState) {
        try {
            Map map = new HashedMap();

            BaseResult baseResult = this.integralGoodsService.getGoodsDetails(integralAcApply.getId(), CommUtils.null2Long(id), CommUtils.null2Int(goodsState));

            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("保存实物商品异常", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取商品分页列表", tags = {"goods"}, notes = "")
    @RequestMapping("/get_goods_list_page")
    public QueryResult goodsListPage(@CurrentIntegralAcApply IntegralAcApply integralAcApply, IntegralGoodsQueryForm queryForm) {
        try {
            IntegralGoodsQueryForm pageForm = queryForm;
            pageForm.setDeleteStatus(false);
            pageForm.setAcApplyId(integralAcApply.getId());

            //关联vip表进行查询
            PaginationResult<IntegralGoods> goodsList = this.integralGoodsService.query(pageForm);
            return goodsList.getView(IntegralGoodsView.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "获取商品列表", tags = {"goods"}, notes = "")
    @GetMapping(value = "/get_goods_list")
    public ActionResult<BaseResult> getGoodsList(@CurrentIntegralAcApply IntegralAcApply integralAcApply,IntegralGoodsQueryForm integralGoodsQueryForm) {
        if (Utils.isNotEmpty(integralAcApply)) {
            integralGoodsQueryForm.setAcApplyId(integralAcApply.getId());
            BaseResult baseResult = integralGoodsService.getGoodsList(integralGoodsQueryForm);
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }

}
