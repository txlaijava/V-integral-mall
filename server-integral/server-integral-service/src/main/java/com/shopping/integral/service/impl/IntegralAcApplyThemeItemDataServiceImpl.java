package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralAcApplyThemeItemDataDAO;
import com.shopping.integral.dao.IntegralGoodsDAO;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralAcApplyThemeItemData;
import com.shopping.integral.dao.model.IntegralGoods;
import com.shopping.integral.dao.model.IntegralThemeItem;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.form.IntegralAcApplyThemeItemDataForm;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralAcApplyThemeItemDataService;
import com.shopping.integral.service.IIntegralThemeItemService;
import com.shopping.integral.view.IntegralAcApplyThemeItemDataView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：IntegralAcApplyThemeItemDataService 实现
 *
 * @author：GuoFuJun
 * @date：2018年05月08日 15:25:03.
 */
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class IntegralAcApplyThemeItemDataServiceImpl extends BaseServiceImpl<IntegralAcApplyThemeItemData, Long> implements IIntegralAcApplyThemeItemDataService {

    @Autowired
    IntegralAcApplyThemeItemDataDAO integralAcApplyThemeItemDataDAO;

    @Autowired
    IIntegralThemeItemService integralThemeItemService;

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @Autowired
    IntegralGoodsDAO integralGoodsDAO;

    @Override
    public BaseResult getThemeItemDataByThemeItemId(Long themeItemId, Long applyId) {
        if (Utils.isEmpty(themeItemId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_THEME_ID_NULL_ERROR);
        }
        if (Utils.isEmpty(applyId)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
        }
        Map params = new HashMap(2);
        params.put("themeItem.id", themeItemId);
        params.put("acApply.id", applyId);
        List<IntegralAcApplyThemeItemData> themeItemData = integralAcApplyThemeItemDataDAO.findByMap(params, "sequence asc");

        List<IntegralAcApplyThemeItemDataView> itemData = BeanViewUtils.getList(themeItemData, IntegralAcApplyThemeItemDataView.class);

        return BaseResult.SUCCESS("获取成功", itemData);
    }

    @Override
    public BaseResult upThemeItemData(Long themeItemId, Long applyId, Long goodsId, IntegralAcApplyThemeItemDataForm integralAcApplyThemeItemDataForm) {
        try {
            if (Utils.isEmpty(themeItemId)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_THEME_ID_NULL_ERROR);
            }
            if (Utils.isEmpty(applyId)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
            }
            IntegralAcApplyThemeItemData integralAcApplyThemeItemData = new IntegralAcApplyThemeItemData();
            // 修改时可更换模板类型
            if (Utils.isEmpty(integralAcApplyThemeItemDataForm.getItemDataType())) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_THEME_ITEM_TYPE_NULL_ERROR);
            }
            // 数据编号不为空 则进行修改操作
            if (Utils.isNotEmpty(integralAcApplyThemeItemDataForm.getId())) {
                integralAcApplyThemeItemData = super.getObjById(integralAcApplyThemeItemDataForm.getId());
            } else {
                integralAcApplyThemeItemData.setAddTime(new Date());
                integralAcApplyThemeItemData.setDeleteStatus(false);
                // 获取模块主题
                IntegralThemeItem integralThemeItem = integralThemeItemService.getObjById(themeItemId);
                integralAcApplyThemeItemData.setThemeItem(integralThemeItem);
                // 获取主题所属应用
                IntegralAcApply integralAcApply = integralAcApplyService.getObjById(applyId);
                integralAcApplyThemeItemData.setAcApply(integralAcApply);
            }
            BeanUtils.copyProperties(integralAcApplyThemeItemDataForm, integralAcApplyThemeItemData, "id");
            // 添加商品数据
            if (Utils.isNotEmpty(integralAcApplyThemeItemDataForm.getItemDataGoodsId())) {
                IntegralGoods integralGoods = integralGoodsDAO.findById(integralAcApplyThemeItemDataForm.getItemDataGoodsId());
                integralAcApplyThemeItemData.setItemDataGoods(integralGoods);
            }
            super.save(integralAcApplyThemeItemData);
            return BaseResult.SUCCESS("主题数据保存成功！", integralAcApplyThemeItemData.getId());
        } catch (Exception e) {
            log.error("操作模板数据异常! {}", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }
}