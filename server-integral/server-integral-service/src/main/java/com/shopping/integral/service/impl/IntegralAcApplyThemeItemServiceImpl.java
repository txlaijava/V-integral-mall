package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralAcApplyThemeItemDAO;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralAcApplyThemeItem;
import com.shopping.integral.dao.model.IntegralTheme;
import com.shopping.integral.dao.model.IntegralThemeItem;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.form.IntegralAcApplyThemeItemDataForm;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralAcApplyThemeItemDataService;
import com.shopping.integral.service.IIntegralAcApplyThemeItemService;
import com.shopping.integral.service.IIntegralThemeItemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 类描述：IntegralAcApplyThemeItemService 实现
*
* @author：GuoFuJun
* @date：2018年05月08日 15:25:03.
*/
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class IntegralAcApplyThemeItemServiceImpl extends BaseServiceImpl<IntegralAcApplyThemeItem,Long> implements IIntegralAcApplyThemeItemService {

    private final static String THEME_ITEM_TYPE = "goods";

    @Autowired
    IntegralAcApplyThemeItemDAO integralAcApplyThemeItemDAO;

    @Autowired
    IIntegralAcApplyThemeItemDataService integralAcApplyThemeItemDataService;

    @Autowired
    IIntegralThemeItemService integralThemeItemService;

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @Override
    public BaseResult getApplyThemeItem(Long applyId, Long themeId,int themeItemState) {

        if(Utils.isEmpty(applyId)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
        }

        if(Utils.isEmpty(themeId)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_THEME_ID_NULL_ERROR);
        }

        try{
            Map params = new HashMap(4);
            params.put("delete_status",false);
            params.put("ac_apply_id",applyId);
            params.put("theme_id",themeId);
            StringBuilder sql = new StringBuilder("SELECT a.id , a.theme_item_id , b.theme_item_name , b.theme_id , b.theme_item_icon , b.theme_item_url , b.theme_item_type , b.theme_item_state");
            sql.append(" FROM integral_ac_apply_theme_item a LEFT JOIN integral_theme_item b ON a.theme_item_id = b.id");
            sql.append(" WHERE a.delete_status = :delete_status AND b.delete_status = :delete_status");
            sql.append(" AND a.ac_apply_id = :ac_apply_id AND b.theme_id = :theme_id");

            if(-1 != themeItemState){
                sql.append(" AND b.theme_item_state = :theme_item_state ");
                params.put("theme_item_state",themeItemState);
            }

            sql.append(" ORDER BY a.sequence asc");
            List<Map<String,Object>> applyThemeItem = integralAcApplyThemeItemDAO.queryByNativeSQL(sql.toString(),params);

            Object themeItem = integralThemeItemService.getByThemeIdItems(themeId).getData();
            Map refund = new HashMap(2);
            refund.put("applyThemeItem",applyThemeItem);
            refund.put("themeItem",themeItem);

            return BaseResult.SUCCESS("成功！",refund);
        }catch (Exception e){
            log.error("获取应用下的主题模板异常！{}",e);
        }

        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult saveApplyThemeItem(Long applyId, IntegralTheme integralTheme) {

        if(Utils.isEmpty(applyId)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
        }

        if(Utils.isEmpty(integralTheme)){
            return BaseResult.ERROR(ResultErrorCodeEnum.THEME_NULL_ERROR);
        }

        try{
            IntegralAcApply acApply = integralAcApplyService.getObjById(applyId);
            integralTheme.getThemeItems().forEach(item ->{
                IntegralAcApplyThemeItem acApplyThemeItem = new IntegralAcApplyThemeItem(acApply,item,item.getSequence());
                super.save(acApplyThemeItem);
                // 如果添加的楼层类型为 goods 的话需要添加默认的数据
                if(THEME_ITEM_TYPE.equalsIgnoreCase(acApplyThemeItem.getThemeItem().getThemeItemType())){
                    IntegralAcApplyThemeItemDataForm itemDataForm = new IntegralAcApplyThemeItemDataForm();
                    itemDataForm.setItemDataName("商品列表");
                    itemDataForm.setItemDataType("item");
                    itemDataForm.setItemDataLike("-1");
                    itemDataForm.setItemDataValue("20");
                    integralAcApplyThemeItemDataService.upThemeItemData(acApplyThemeItem.getThemeItem().getId(),acApply.getId(),null,itemDataForm);
                }
            });
            return BaseResult.SUCCESS("成功！",null);
        }catch (Exception e){
            log.error("保存主题模板异常！",e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult upApplyThemeItem(Long applyId, List<Map> applyThemeItem) {

        if(Utils.isEmpty(applyId)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
        }
        Map params = new HashMap(1);
        params.put("ac_apply_id",applyId);
        integralAcApplyThemeItemDAO.updateByNativeSQL("DELETE FROM integral_ac_apply_theme_item where ac_apply_id = :ac_apply_id",params);
        applyThemeItem.forEach( item ->{
            Long themeItmId = CommUtils.null2Long(item.get("id"));
            try{
                IntegralAcApply acApply = integralAcApplyService.getObjById(applyId);
                IntegralThemeItem integralThemeItem = integralThemeItemService.getObjById(themeItmId);
                IntegralAcApplyThemeItem acApplyThemeItem = new IntegralAcApplyThemeItem(
                        acApply,integralThemeItem,CommUtils.null2Int(item.get("sequence"))
                );
                super.save(acApplyThemeItem);
            }catch (Exception e){
                log.error("保存主题模板异常！",e);
            }
        });
        return BaseResult.SUCCESS("保存成功！",null);
    }
}