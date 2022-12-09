package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.queryform.IntegralGoodsQueryForm;
import com.shopping.integral.service.IIntegralGoodsService;
import com.shopping.integral.service.VistDateService;
import com.shopping.integral.token.authorization.annotation.CurrentAppKey;
import com.shopping.integral.view.IntegralGoodsView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述：商品操作
 *
 * @author：GuoFuJun
 * @date：2018年7月2日 上午11:13:46
 */
@Log4j2
@Api(value = "商品操作", tags = {"goods"})
@RestController
@RequestMapping("/app/goods")
public class GoodsController {

    @Autowired
    IIntegralGoodsService integralGoodsService;

    @Autowired
    IndexController indexController;

    @Autowired
    VistDateService vistDateService;

    @ApiOperation(value = "获取商品详情", tags = {"goods"}, notes = "")
    @GetMapping(value = "/get_goods_details/{goodsId}")
    public ActionResult<BaseResult> getGoodsDetails(HttpServletRequest request, @CurrentAppKey String appKey, @PathVariable("goodsId") Long goodsId) {
        try {
            if (Utils.isNotEmpty(goodsId) && Utils.isNotEmpty(appKey)) {

                IntegralAcApply acApply = indexController.integralAcApply(appKey);

                BaseResult baseResult = integralGoodsService.getGoodsDetails(acApply.getId(), goodsId, 1);

                if (baseResult.getSuccess()) {
                    IntegralGoodsView view = (IntegralGoodsView) baseResult.getData();
                    //记录访问PV
                    vistDateService.VIST_PV(acApply.getAppKey());
                    //TODO 通过token判断访问这个商品计算是 UV 还是 PV
                    String token = request.getHeader("Token");
                    vistDateService.VIST_GOODS_PV(acApply.getAppKey(), goodsId + "", view.getGoodsName());
                    vistDateService.VIST_GOODS_UV(acApply.getAppKey(), goodsId + "", view.getGoodsName(), token);
                }
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("获取商品接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取商品列表", tags = {"goods"}, notes = "")
    @GetMapping(value = "/get_goods_list")
    public ActionResult<BaseResult> getGoodsList(@CurrentAppKey String appKey,IntegralGoodsQueryForm integralGoodsQueryForm,String currentPage,String pageSize,String categoryId) {
        IntegralAcApply acApply = indexController.integralAcApply(appKey);
        if (Utils.isNotEmpty(acApply)) {
            if(CommUtils.isNotNull(currentPage) && CommUtils.isNotNull(pageSize)){
                integralGoodsQueryForm.setCurrentPage(CommUtils.null2Int(currentPage));
                integralGoodsQueryForm.setPageSize(CommUtils.null2Int(pageSize));
            }
            if(CommUtils.isNotNull(categoryId)){
                integralGoodsQueryForm.setCategoryId(CommUtils.null2Long(categoryId));
            }
            // 默认需要显示商家商品
            integralGoodsQueryForm.setGoodsState(1);
            integralGoodsQueryForm.setAcApplyId(acApply.getId());
            BaseResult baseResult = integralGoodsService.getGoodsList(integralGoodsQueryForm);
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }
}
