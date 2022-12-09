package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.dao.model.IntegralReportGoodsDailydata;

import java.util.List;
import java.util.Map;

/**
 * Created by yj on 2018/8/3.
 */
public abstract interface IIntegralReportGoodsDailydataService extends IBaseService<IntegralReportGoodsDailydata,Long> {

    List<Map<String,Object>> queryBySqlReturnMap(Map<String, Object> params);
}
