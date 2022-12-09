package com.shopping.integral.service;

import com.shopping.base.foundation.base.entity.SqlMap;
import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.dao.model.IntegralReportDailydata;

import java.util.List;
import java.util.Map;

/**
 * Created by yj on 2018/8/2.
 */
public abstract interface IntegralReportDailydataService extends IBaseService<IntegralReportDailydata, Long> {

    List<Map<String, Object>> queryBySqlReturnMap(Map<String, String> params, SqlMap sqlmap) throws Exception;

    /**
     * 查询流量数据
     *
     * @param isajax
     * @param map
     * @return
     */
    public List<Map<String, Object>> query(String isajax, Map map) throws Exception;

    /**
     * 新增用户查询
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> addUserQuery(Map map) throws Exception ;

    /**
     * 查询访客占比
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> visitorPerQuery(Map map) throws Exception ;

    /**
     * 查询人均积分
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> averageIntegralQuery(Map map) throws Exception ;

    /**
     * 查询积分分布
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> disIntegralQuery(Map map) throws Exception ;

    /**
     * 查询日常数据
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> dailyDataQuery(Map map) throws Exception ;

    /**
     * 查询活跃数据
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> activeDataQuery(Map map, boolean isAll) throws Exception ;

    /**
     * 查询首页今日数据
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> basicFactsDataQuery(Map<String, Object> params) throws Exception ;


    List<Map<String, Object>> querySQLPage(Map map, boolean sortTypeValue, int currentPage, Object... params) throws Exception;
}
