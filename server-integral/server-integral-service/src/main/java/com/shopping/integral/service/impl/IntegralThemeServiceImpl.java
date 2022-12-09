package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralThemeDAO;
import com.shopping.integral.dao.model.IntegralTheme;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.IIntegralThemeService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 类描述：IntegralThemeService 实现
 *
 * @author：GuoFuJun
 * @date：2018年05月08日 15:25:03.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralThemeServiceImpl extends BaseServiceImpl<IntegralTheme, Long> implements IIntegralThemeService {

    @Autowired
    IntegralThemeDAO integralThemeDAO;

    @Override
    public BaseResult getThemeList() {
        try {
            StringBuffer sql = new StringBuffer("SELECT a.id , a.theme_name , a.is_default , b.ext , b.path , b.name");
            sql.append(" FROM integral_theme a LEFT JOIN integral_accessory b ON a.theme_preview_img_id = b.id WHERE a.delete_status = 0");
            List<Map<String, Object>> themeList = integralThemeDAO.queryByNativeSQL(sql.toString(), null);
            return BaseResult.SUCCESS("获取成功！", themeList);
        } catch (Exception e) {
            log.error("获取主题列表异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }
}