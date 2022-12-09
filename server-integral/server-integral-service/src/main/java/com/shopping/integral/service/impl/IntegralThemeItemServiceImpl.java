package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralThemeItemDAO;
import com.shopping.integral.dao.model.IntegralThemeItem;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.IIntegralThemeItemService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* 类描述：IntegralThemeItemService 实现
*
* @author：GuoFuJun
* @date：2018年05月08日 15:25:03.
*/
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralThemeItemServiceImpl extends BaseServiceImpl<IntegralThemeItem,Long> implements IIntegralThemeItemService {

    @Autowired
    IntegralThemeItemDAO integralThemeItemDAO;


    @Override
    public BaseResult getByThemeIdItems(Long themeId) {
        if(Utils.isEmpty(themeId)){
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_THEME_ID_NULL_ERROR);
        }

        List<IntegralThemeItem> itemList = integralThemeItemDAO.findByProperty("theme.id",themeId,"sequence");

        return BaseResult.SUCCESS("获取成功！", itemList);
    }
}