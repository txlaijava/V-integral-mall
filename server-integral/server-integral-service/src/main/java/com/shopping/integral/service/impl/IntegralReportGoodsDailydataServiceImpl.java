package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.integral.dao.IntegralReportGoodsDailydataDAO;
import com.shopping.integral.dao.model.IntegralReportGoodsDailydata;
import com.shopping.integral.service.IIntegralReportGoodsDailydataService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralReportGoodsDailydataServiceImpl extends BaseServiceImpl<IntegralReportGoodsDailydata,Long> implements IIntegralReportGoodsDailydataService {

    @Autowired
    IntegralReportGoodsDailydataDAO integralReportGoodsDailydataDAO;

    @Override
    public List<Map<String,Object>> queryBySqlReturnMap(Map<String,Object> params){

        String sql="";



        return this.integralReportGoodsDailydataDAO.queryByNativeSQL(sql,null);
    }
}
