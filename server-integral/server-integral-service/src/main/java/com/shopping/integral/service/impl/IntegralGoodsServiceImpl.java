package com.shopping.integral.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.form.PaginationForm;
import com.shopping.base.foundation.result.PaginationResult;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.constant.GoodsCons;
import com.shopping.integral.dao.IntegralGoodsDAO;
import com.shopping.integral.dao.model.*;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.form.IntegralExchangeRulesForm;
import com.shopping.integral.queryform.IntegralGoodsAddForm;
import com.shopping.integral.queryform.IntegralGoodsQueryForm;
import com.shopping.integral.service.IIntegralGoodsService;
import com.shopping.integral.view.IntegralGoodsView;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 类描述：IntegralGoodsService 实现
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralGoodsServiceImpl extends BaseServiceImpl<IntegralGoods, Long> implements IIntegralGoodsService {

    @Autowired
    IntegralGoodsDAO integralGoodsDAO;

    @Autowired
    IntegralExchangeRulesServiceImpl integralExchangeRulesService;

    @Autowired
    IntegralAccessoryServiceImpl integralAccessoryService;

    @Autowired
    IntegralGoodsCategoryServiceImpl goodsCategoryService;

    @Override
    public Map setObjectGoods(Map params, IntegralAcApply acApply, IntegralGoodsAddForm integralGoodsForm, IntegralExchangeRulesForm exchangeRulesForm) {
        Map map = new HashedMap();
        try{
            String id = "id",type = "type",goodsType = "goodsType", autoLowerShelvesTime = "autoLowerShelvesTime",categoryId = "categoryId",
                    gearType = "gearType",gearValue = "gearValue",gearValueJson = "gearValueJsonList",
                    photoDetails="photoDetailsList",photoMini = "photoMiniList",photoIcon = "photoIconList",
                    freightPrice = "freightPrice", userExchangeLimit = "userExchangeLimit", dailyExchangeLimit = "dailyExchangeLimit",
                    exchangeStartTime = "exchangeStartTime", exchangeEndTime = "exchangeEndTime", exchangePriceType = "exchangePriceType";

            IntegralGoods integralGoods;
            IntegralExchangeRules integralExchangeRules;

            //修改
            if (CommUtils.isNotNull(params.get(id))) {
                integralGoods = this.integralGoodsDAO.findById(CommUtils.null2Long(params.get(id)));
                integralExchangeRules = integralGoods.getExchangeRules();
            } else {                                          //新增
                integralGoods = new IntegralGoods();
                integralGoods.setAddTime(new Date());
                integralGoods.setDeleteStatus(false);
                integralExchangeRules = new IntegralExchangeRules();
                integralExchangeRules.setAddTime(new Date());
                integralExchangeRules.setDeleteStatus(false);
            }
            /*将form页面提交来的数据复制到bean中*/
            BeanUtils.copyProperties(integralGoodsForm, integralGoods);
            BeanUtils.copyProperties(exchangeRulesForm, integralExchangeRules);

            integralGoods.setGoodsType(CommUtils.null2Int(params.get(goodsType)));
            integralGoods.setAutoLowerShelvesTime(CommUtils.formatDate(params.get(autoLowerShelvesTime).toString(),"yyyy-MM-dd"));
            //保存当前应用
            integralGoods.setAcApply(acApply);

            //保存商品分类
            if (CommUtils.isNotNull(params.get(categoryId))) {
                IntegralGoodsCategory category = this.goodsCategoryService.getObjById(CommUtils.null2Long(params.get(categoryId)));
                integralGoods.setCategory(category);
            } else {
                integralGoods.setCategory(null);
            }

            //保存商品档位信息
            if(CommUtils.isNotNull(params.get(gearType)) && integralGoods.getGoodsType() == GoodsCons.VIRTUAL_GOODS){
                integralGoods.setGearType(CommUtils.null2Int(params.get(gearType)));
                if(params.get(gearType).equals("1")){
                    //保存单个档位信息
                    integralGoods.setGearValue(params.get(gearValue).toString());
                }else if(params.get(gearType).equals("2")){
                    //保存json档位信息
                    integralGoods.setGearValue(params.get(gearValueJson).toString());
                }
            }

            //保存图片
            String JsonPhotoDetails = params.get(photoDetails).toString();
            String JsonPhotoMini = params.get(photoMini).toString();
            String JsonPhotoIcon = params.get(photoIcon).toString();
            List<Map> photoDetailsList = JSONArray.parseArray(JsonPhotoDetails, Map.class);
            Map photoMiniMap = JSONObject.parseObject(JsonPhotoMini, Map.class);
            Map photoIconMap = JSONObject.parseObject(JsonPhotoIcon, Map.class);
            integralGoods.setGoodsMainPhoto(this.integralAccessoryService.getObjById(CommUtils.null2Long(photoMiniMap.get("id"))));
            integralGoods.setGoodsIconPhoto(this.integralAccessoryService.getObjById(CommUtils.null2Long(photoIconMap.get("id"))));

            List<IntegralAccessory> listPhoto = new ArrayList<>();
            for (Map photo : photoDetailsList) {
                IntegralAccessory accPhoto = this.integralAccessoryService.getObjById(CommUtils.null2Long(photo.get("id")));
                listPhoto.add(accPhoto);
            }
            integralGoods.setGoodsPhotos(listPhoto);
            //不支持上门自提
            if(!integralGoods.getIsSend()){
                integralGoods.setTakeSelfAddr("");
            }

            if (params.get(type).equals("warehouse")) {
                //放入仓库
                integralGoods.setGoodsState(2);
            } else if (params.get(type).equals("shelves") || params.get(type).equals("edit")) {
                //上架或者保存
                integralGoods.setGoodsState(1);
            }
            //保存商品
            if (CommUtils.isNotNull(integralGoods.getId())) {
                this.integralGoodsDAO.update(integralGoods);
            } else {
                integralGoods.setTop(false);
                this.integralGoodsDAO.save(integralGoods);
            }

            if (CommUtils.null2Int(params.get(exchangePriceType)) == 1) {
                //纯积分时将现金，运费设为0
                integralExchangeRules.setExchangePrice(new BigDecimal(0));
            }
            if(integralExchangeRules.getFreightWay()==0){
                //包邮，邮费设为0
                integralExchangeRules.setFreightPrice(new BigDecimal(0));
            } else if (integralExchangeRules.getFreightWay() == 1) {
                integralExchangeRules.setFreightPrice(CommUtils.null2BigDecimal(params.get(freightPrice)));
            }
            //如果用户兑换限制为空就将兑换限制类型设为null
            if (CommUtils.isNotNull(params.get(userExchangeLimit))) {
                integralExchangeRules.setUserExchangeLimit(CommUtils.null2Int(params.get(userExchangeLimit)));

            } else {
                integralExchangeRules.setUserExchangeLimit(null);
                integralExchangeRules.setUserExchangeLimitUnit(null);
            }
            //每日兑换限制为开启并且每日兑换限制不为空才设置值
            if (CommUtils.isNotNull(params.get(dailyExchangeLimit)) && integralExchangeRules.getAdditionalExchangeLimit()) {
                integralExchangeRules.setDailyExchangeLimit(CommUtils.null2Int(params.get(dailyExchangeLimit)));
                integralExchangeRules.setExchangeStartTime(CommUtils.formatDate(params.get(exchangeStartTime).toString(), "yyyy-MM-dd"));
                integralExchangeRules.setExchangeEndTime(CommUtils.formatDate(params.get(exchangeEndTime).toString(), "yyyy-MM-dd"));
            } else {
                integralExchangeRules.setDailyExchangeLimit(null);
            }

            if (CommUtils.isNotNull(integralExchangeRules.getId())) {
                this.integralExchangeRulesService.update(integralExchangeRules);
            } else {
                integralExchangeRules.setGoodsId(integralGoods.getId());
                this.integralExchangeRulesService.save(integralExchangeRules);
            }
            integralGoods.setExchangeRules(integralExchangeRules);
            this.integralGoodsDAO.update(integralGoods);

            map.put("status", 1000);
            map.put("message", "保存成功");
            return map;
        } catch (Exception e) {
            log.error("保存实物商品异常", e);
        }
        map.put("status", 2001);
        map.put("message", "服务器异常");
        return null;
    }

    @Override
    public BaseResult getGoodsList(IntegralGoodsQueryForm integralGoodsQueryForm) {
        try {
            PaginationResult<IntegralGoods> item = integralGoodsDAO.paging(integralGoodsQueryForm);
            return BaseResult.SUCCESS("获取成功！", item.getView(IntegralGoodsView.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult getGoodsDetails(Long applyId, Long goodsId, int goodsState) throws Exception {

        if (Utils.isEmpty(applyId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
        }

        if (Utils.isEmpty(goodsId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_GOODS_ID_NULL_ERROR);
        }

        Map params = new HashMap(3);
        params.put("id", goodsId);
        params.put("acApply.id", applyId);
        params.put("goodsState", goodsState);
        params.put("deleteStatus", false);
        IntegralGoods goodsDetails = integralGoodsDAO.findOneByMap(params);
        if (Utils.isEmpty(goodsDetails)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.GOODS_NULL_ERROR);
        }

        IntegralGoodsView view = BeanViewUtils.getView(goodsDetails, IntegralGoodsView.class);

        return BaseResult.SUCCESS("获取数据成功！", view);
    }

    @Override
    public PaginationResult<IntegralGoods> query(PaginationForm pageForm) {
        return this.integralGoodsDAO.paging(pageForm);
    }

    @Override
    public BaseResult updateGoods(String type, String goodsIds, Object value) {
        try {

            String[] ids = goodsIds.split(",");
            for (String id : ids) {
                IntegralGoods goods = this.integralGoodsDAO.findById(CommUtils.null2Long(id));
                switch (type) {
                    case "up":
                        goods.setGoodsState(GoodsCons.GOODS_PUBLISH);
                        break;
                    case "down":
                        goods.setGoodsState(GoodsCons.GOODS_SOLD_OUT);
                        break;
                    case "del":
                        goods.setDeleteStatus(true);
                        break;
                    case "top":
                        goods.setTop(CommUtils.null2Boolean(value));
                        goods.setTopTime(CommUtils.null2Boolean(value) ? new Date() : null);
                        break;
                    case "sequence":
                        goods.setSequence(CommUtils.null2Int(value));
                        break;
                    default:
                        break;
                }
                integralGoodsDAO.update(goods);
            }
            return BaseResult.SUCCESS("修改成功！", "");
        } catch (Exception e) {
            log.error("批量修改商品状态异常", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult addGoodsInventory(Long applyId, Long goodsId, int inventory) {
        try {
            Map param = new HashedMap();
            param.put("id", goodsId);
            param.put("acApply.id", applyId);
            IntegralGoods goods = this.integralGoodsDAO.findOneByMap(param);
            if (Utils.isEmpty(goods)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.GOODS_NULL_ERROR);
            }

            goods.setGoodsInventory((goods.getGoodsInventory() + inventory));
            this.integralGoodsDAO.update(goods);

            return BaseResult.SUCCESS("添加库存成功！", goods.getGoodsInventory());
        } catch (Exception e) {
            log.error("添加商品库存异常", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult reduceGoodsInventory(Long applyId, Long goodsId, int inventory) {
        try {
            Map param = new HashedMap();
            param.put("id", goodsId);
            param.put("acApply.id", applyId);
            IntegralGoods goods = this.integralGoodsDAO.findOneByMap(param);
            if (Utils.isEmpty(goods)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.GOODS_NULL_ERROR);
            }

            goods.setGoodsInventory((goods.getGoodsInventory() - inventory));
            this.integralGoodsDAO.update(goods);

            return BaseResult.SUCCESS("减库存成功！", goods.getGoodsInventory());
        } catch (Exception e) {
            log.error("减少商品库存异常", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public List<IntegralGoods> queryBySql(String sql, Map<String, Object> params) {
        return (List<IntegralGoods>) this.integralGoodsDAO.queryByNativeSQL(sql, IntegralGoods.class, params);
    }
}